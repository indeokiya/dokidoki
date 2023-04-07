package com.dokidoki.auction.service;

import com.dokidoki.auction.common.error.exception.ErrorCode;
import com.dokidoki.auction.common.error.exception.InvalidValueException;
import com.dokidoki.auction.domain.entity.*;
import com.dokidoki.auction.domain.repository.*;
import com.dokidoki.auction.dto.request.AuctionRegisterReq;
import com.dokidoki.auction.dto.request.AuctionUpdateReq;
import com.dokidoki.auction.dto.response.*;
import com.dokidoki.auction.enumtype.TradeType;
import com.dokidoki.auction.kafka.dto.KafkaAuctionRegisterDTO;
import com.dokidoki.auction.kafka.dto.KafkaAuctionUpdateDTO;
import com.dokidoki.auction.kafka.service.KafkaAuctionProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuctionService {
    private final ProductRepository productRepository;
    private final AuctionIngRepository auctionIngRepository;
    private final AuctionEndRepository auctionEndRepository;
    private final MemberRepository memberRepository;
    private final ImageService imageService;
    private final CommentService commentService;
    private final LeaderboardService leaderboardService;
    private final InterestRepository interestRepository;
    private final KafkaAuctionProducer producer;

    private final TreasuryRepository treasuryRepository;

    private final RestTemplate restTemplate;

    private final int COMMISION_PERCENT = 5;

    @Value("${api.server.uri.notice}")
    private String NOTICE_SERVER_URI;

    // 카테고리 기준 제품 목록 조회
    @Transactional(readOnly = true)
    public List<ProductResp> getProductList(String keyword) {
        List<ProductEntity> productEntities = productRepository
                .findByKeyword(keyword, PageRequest.of(0, 10));
        List<ProductResp> productList = new ArrayList<>();

        for (ProductEntity productEntity : productEntities) {
            ProductResp productResp = ProductResp.builder()
                    .product_id(productEntity.getId())
                    .name(productEntity.getCategoryEntity().getCategoryName() + " - " + productEntity.getName())
                    .product_name(productEntity.getName())
                    .build();
            productList.add(productResp);
        }

        return productList;
    }

    // 진행중인 경매 상세정보 조회
    @Transactional(readOnly = true)
    public DetailAuctionIngResp readAuctionIng(Long memberId, Long auctionId) {
        // 진행중 경매 정보
        AuctionIngEntity auctionIngEntity = auctionIngRepository.findAuctionIngEntityByIdOrderById(auctionId);
        if (auctionIngEntity == null)
            return null;

        // 경매 제품 사진 URL 구하기
        List<String> auctionImageUrls = imageService.readAuctionImages(auctionId).getImage_urls();

        // 댓글 구하기
        List<CommentResp> commentRespons = commentService.readComment(auctionId);

        // 찜꽁 경매 여부 구하기
        InterestEntity interestEntity = interestRepository.findByMemberEntity_IdAndAuctionIngEntity_Id(memberId, auctionId);

        return new DetailAuctionIngResp(
                auctionIngEntity,
                auctionImageUrls,
                commentRespons,
                interestEntity != null
        );
    }

    // 완료된 경매 상세정보 조회
    @Transactional(readOnly = true)
    public DetailAuctionEndResp readAuctionEnd(Long auction_id) {
        // 완료된 경매 정보
        AuctionEndEntity auctionEndEntity = auctionEndRepository
                .findAuctionEndEntityById(auction_id);

        // 존재하지 않는다면 null 반환
        if (auctionEndEntity == null)
            return null;

        // 경매 제품 사진 URL 구하기
        List<String> auctionImageUrls = imageService.readAuctionImages(auction_id).getImage_urls();

        // 댓글 구하기
        List<CommentResp> commentRespons = commentService.readComment(auction_id);

        // 입찰 내역 구하기
        List<LeaderboardHistoryResp> leaderboardHistoryRespons = leaderboardService.readLeaderboard(auction_id);

        return new DetailAuctionEndResp(
                auctionEndEntity,
                auctionImageUrls,
                commentRespons,
                leaderboardHistoryRespons
        );
    }

    @Transactional
    public void createAuction(AuctionRegisterReq req, Long sellerId) {
        if (sellerId == null) {
            throw new InvalidValueException("토큰이 유효하지 않습니다.", ErrorCode.INVALID_INPUT_USER);
        }
        
        // 제품 정보 가져오기
        Optional<ProductEntity> productO = productRepository.findById(req.getProduct_id());

        if (productO.isEmpty()) {
            throw new InvalidValueException("제품에 대한 정보가 존재하지 않습니다.", ErrorCode.BUSINESS_EXCEPTION_ERROR);
        }

        // 요청자 객체 획득
        MemberEntity requestMemberEntity = memberRepository.findById(sellerId).orElse(null);
        if (requestMemberEntity == null)
            throw new InvalidValueException("유저 정보가 존재하지 않습니다. 유효하지 않은 접근입니다.");

        ProductEntity productEntity = productO.get();

        AuctionIngEntity auctionIngEntity = AuctionIngEntity.builder()
                .seller(requestMemberEntity)
                .productEntity(productEntity)
                .title(req.getTitle())
                .description(req.getDescription())
                .offerPrice(req.getOffer_price())
                .priceSize(req.getPrice_size())
                .endAt(req.getEnd_at())
                .meetingPlace(req.getMeeting_place())
                .build();
        
        // 경매 등록하기
        long auctionId = auctionIngRepository.save(auctionIngEntity).getId();

        // 이미지 등록
        if (req.getFiles() != null && req.getFiles().length > 0) {
            imageService.createAuctionImages(
                    auctionIngEntity.getId(),
                    req.getFiles()
            );
        }

        // 성공적으로 등록되면 카프카에 auction.register 메시지 발행.
        producer.sendAuctionRegister(new KafkaAuctionRegisterDTO(req, auctionId, 0L, sellerId, req.getProduct_id(), productEntity.getName()));
    }

    @Transactional
    public AuctionIngEntity updateAuction(Long sellerId, Long auctionId, AuctionUpdateReq auctionUpdateReq) {

        AuctionIngEntity auction = auctionIngRepository.findById(auctionId).orElse(null);

        // 요청자와 판매자가 동일한 경우에만 update 수행
        if (auction != null && sellerId.equals(auction.getSeller().getId())) {
            auction.update(auctionUpdateReq);
            KafkaAuctionUpdateDTO dto = KafkaAuctionUpdateDTO.of(auctionUpdateReq, auctionId);
            producer.sendAuctionUpdate(dto);
            return auction;
        }

        return null;
    }

    /*
    카프카에서 호출될 이벤트
        1. 입찰 시 최고가 갱신을 위한 메서드
        2. 경매 종료 시 '경매중' 테이블에서 '경매 종료' 테이블로 옮기기 위한 메서드
     */
    @Transactional
    public void updateHighestPrice(Long auctionId, Long highestPrice) {
        log.info("updateHighestPrice >> start method");
        AuctionIngEntity auctionIngEntity = auctionIngRepository.findById(auctionId).orElse(null);
        if (auctionIngEntity == null) {
            log.error("updateHighestPrice >> 존재하지 않는 경매입니다.");
            return;
        }

        log.info("updateHighestPrice >> check updating the highest price");
        // 최고가 갱신
        if (auctionIngEntity.getHighestPrice() == null || highestPrice > auctionIngEntity.getHighestPrice()) {
            log.info("updateHighestPrice >> create new entity");
            AuctionIngEntity newAuctionIngEntity = AuctionIngEntity.builder()
                    .id(auctionIngEntity.getId())
                    .seller(auctionIngEntity.getSeller())
                    .title(auctionIngEntity.getTitle())
                    .description(auctionIngEntity.getDescription())
                    .productEntity(auctionIngEntity.getProductEntity())
                    .offerPrice(auctionIngEntity.getOfferPrice())
                    .priceSize(auctionIngEntity.getPriceSize())
                    .startTime(auctionIngEntity.getStartTime())
                    .endAt(auctionIngEntity.getEndAt())
                    .meetingPlace(auctionIngEntity.getMeetingPlace())
                    .highestPrice(highestPrice)
                    .build();
            auctionIngRepository.save(newAuctionIngEntity);
            log.info("updateHighestPrice >> save entity");
        }
    }

    @Transactional
    public void auctionEndEvent(Long auctionId, Long buyerId) {
        log.info("auctionEndEvent >> start method");
        AuctionIngEntity auctionIngEntity = auctionIngRepository.findById(auctionId).orElse(null);

        if (auctionIngEntity == null) {
            log.error("auctionEndEvent >> 존재하지 않는 경매입니다.");
            return;
        }
        log.info("auctionEndEvent >> find buyer entity");

        // 구매자가 없을 수 있으므로 buyerId가 주어졌을 경우에만 사용자 검색
        MemberEntity buyer = buyerId != null
                ? memberRepository.findById(buyerId).orElse(null)
                : null;

        boolean tradeSuccess = true;


        // 구매자가 존재한다면
        if(buyer != null){
            // 경매 종료시 최고가
            Long finalPrice = auctionIngEntity.getHighestPrice();

            Long commision = finalPrice * COMMISION_PERCENT / 100; // 수수료

            // 물건 이름
            String productName = auctionIngEntity.getProductEntity().getName();

            // 판매자
            MemberEntity seller = auctionIngEntity.getSeller();

            // 돈을 지불할 능력이 없으면
            if(buyer.getPoint() < finalPrice){
                tradeSuccess = false;
                // 징벌

                // 수수료도 낼 돈이 없으면
                if (buyer.getPoint() < commision){
                    log.info("한달 정지");
                    MemberEntity updatedBuyer = MemberEntity.builder()
                            .point(buyer.getPoint())
                            .id(buyer.getId())
                            .picture(buyer.getPicture())
                            .name(buyer.getName())
                            .sub(buyer.getSub())
                            .providerType(buyer.getProviderType())
                            .email(buyer.getEmail())
                            .EndTimeOfSuspension(LocalDateTime.now().plusMonths(1)) // 한달 정지
                            .build();
                    memberRepository.save(updatedBuyer);

                    // 소켓 요청
                    List<UpdatePointSocketRes> updatePointSocketResList = new ArrayList<>();

                    updatePointSocketResList.add(
                            UpdatePointSocketRes.builder()
                                    .user_id(updatedBuyer.getId())
                                    .type(TradeType.PRISONER)
                                    .message("수수료를 지불할 수 없어 한달간 이용이 정지됩니다." +
                                            "\n 정지 해제 날짜 : " + updatedBuyer.getEndTimeOfSuspension())
                                    .tradeSuccess(false)
                                    .build()
                    );

                    updatePointSocketResList.add(
                            UpdatePointSocketRes.builder()
                                    .type(TradeType.SELLER)
                                    .productName(productName)
                                    .tradeSuccess(false)
                                    .message("최고가 입찰자가 정지 처리 당했습니다.")
                                    .user_id(seller.getId())
                                    .build()
                    );

                    sendPointUpdateRequest(updatePointSocketResList);
                }else{ // 수수료 지불
                    log.info("돈 없는 놈");
                    MemberEntity updatedBuyer = MemberEntity.builder()
                            .point(buyer.getPoint() - commision)  // 돈 감소
                            .id(buyer.getId())
                            .picture(buyer.getPicture())
                            .name(buyer.getName())
                            .sub(buyer.getSub())
                            .providerType(buyer.getProviderType())
                            .email(buyer.getEmail())
                            .EndTimeOfSuspension(buyer.getEndTimeOfSuspension())
                            .build();

                    MemberEntity updatedSeller  = MemberEntity.builder()
                            .point((seller.getPoint() + commision))  // 수수료 획득
                            .id(seller.getId())
                            .picture(seller.getPicture())
                            .name(seller.getName())
                            .sub(seller.getSub())
                            .providerType(seller.getProviderType())
                            .email(seller.getEmail())
                            .build();

                    List<MemberEntity> memberEntityList = new ArrayList<>();
                    memberEntityList.add(updatedBuyer);
                    memberEntityList.add(updatedSeller);

                    memberRepository.saveAll(memberEntityList);

                    // 소켓 요청
                    List<UpdatePointSocketRes> updatePointSocketResList = new ArrayList<>();
                    updatePointSocketResList.add(
                            UpdatePointSocketRes.builder()
                                    .point(updatedBuyer.getPoint())
                                    .user_id(updatedBuyer.getId())
                                    .tradeSuccess(false)
                                    .type(TradeType.PENALTY)
                                    .earnedPoint(-commision)
                                    .productName(productName)
                                    .message("물건을 구매할 포인트가 부족해 수수료가 차감됩니다.")
                                    .build()
                    );

                    updatePointSocketResList.add(
                            UpdatePointSocketRes.builder()
                                    .point(updatedSeller.getPoint())
                                    .user_id(updatedSeller.getId())
                                    .tradeSuccess(false)
                                    .type(TradeType.SELLER)
                                    .earnedPoint(commision)
                                    .productName(productName)
                                    .message("최고가 입찰자가 물건을 구매할 포인트가 부족해 수수료를 획득합니다.")
                                    .build()
                    );

                    sendPointUpdateRequest(updatePointSocketResList);
                }
            }else{

                log.info("합리적인 거래 우하하");

                Long sellerGetMoney = finalPrice - commision; // 수수료 제외 지급 금액

                MemberEntity updatedSeller  = MemberEntity.builder()
                        .point((seller.getPoint() + sellerGetMoney))  // 수수료 제외 돈 추가
                        .id(seller.getId())
                        .picture(seller.getPicture())
                        .name(seller.getName())
                        .sub(seller.getSub())
                        .providerType(seller.getProviderType())
                        .email(seller.getEmail())
                        .build();

                MemberEntity updatedBuyer = MemberEntity.builder()
                        .point(buyer.getPoint() - finalPrice)  // 돈 감소
                        .id(buyer.getId())
                        .picture(buyer.getPicture())
                        .name(buyer.getName())
                        .sub(buyer.getSub())
                        .providerType(buyer.getProviderType())
                        .email(buyer.getEmail())
                        .build();

                TreasuryEntity treasury = TreasuryEntity.builder()
                        .member(seller)
                        .money(commision)
                        .build();

                // 국고에 돈 저장
                treasuryRepository.save(treasury);

                List<MemberEntity> memberEntityList = new ArrayList<>();
                memberEntityList.add(updatedBuyer);
                memberEntityList.add(updatedSeller);

                memberRepository.saveAll(memberEntityList);

                // 소켓 요청
                List<UpdatePointSocketRes> updatePointSocketResList = new ArrayList<>();
                updatePointSocketResList.add(
                        UpdatePointSocketRes.builder()
                                .point(updatedBuyer.getPoint())
                                .user_id(updatedBuyer.getId())
                                .earnedPoint(-finalPrice)
                                .type(TradeType.BUYER)
                                .tradeSuccess(true)
                                .productName(productName)
                                .message("현명한 소비였습니다! :)")
                                .build()
                );
                updatePointSocketResList.add(
                        UpdatePointSocketRes.builder()
                                .point(updatedSeller.getPoint())
                                .user_id(updatedSeller.getId())
                                .productName(productName)
                                .type(TradeType.SELLER)
                                .earnedPoint(sellerGetMoney)
                                .tradeSuccess(true)
                                .message("합리적인 판매였습니다! :)")
                                .build()
                );
                sendPointUpdateRequest(updatePointSocketResList);
            }
        }

        // 경매완료 데이터 삽입
        LocalDateTime endTime = LocalDateTime.now();
        if (endTime.isAfter(auctionIngEntity.getEndAt()))
            endTime = auctionIngEntity.getEndAt();

        log.info("auctionEndEvent >> create new entity");
        AuctionEndEntity auctionEndEntity = AuctionEndEntity.createAuctionEnd(
                auctionIngEntity.getId(),
                auctionIngEntity.getSeller(),
                tradeSuccess ? buyer : null,    // 거래가 성공하면 구매자를 넣기
                auctionIngEntity.getProductEntity(),
                auctionIngEntity.getStartTime(),
                endTime,
                auctionIngEntity.getTitle(),
                auctionIngEntity.getOfferPrice(),
                auctionIngEntity.getHighestPrice() != null
                        ? auctionIngEntity.getHighestPrice()
                        : auctionIngEntity.getOfferPrice(),  // 최고 입찰가가 없다면 입찰 자체가 없던 것이었으므로 시작가 넣어줌
                auctionIngEntity.getDescription()
        );
        auctionEndRepository.save(auctionEndEntity);
        log.info("auctionEndEvent >> save entity");

        // 경매중 데이터 삭제
        auctionIngRepository.delete(auctionIngEntity);
        log.info("auctionEndEvent >> delete auction-ing entity");

        // 판매 빈도 증가
        if(buyer != null && tradeSuccess)
            productRepository.updateSaleCount(auctionEndEntity.getProduct().getId());
        log.info("auctionEndEvent >> increase product's sale count");
    }

    /**
     * 경매 id에 해당하는 진행중인 경매 정보 조회
     * @param   auctionId
     * @return 진행중인 경매 정보
     */
    @Transactional(readOnly = true)
    public AuctionIngEntity getAuctioningById(Long auctionId) {

        AuctionIngEntity auction = auctionIngRepository.findById(auctionId)
                .orElse(null);
        if (auction == null)
            throw new InvalidValueException("진행중인 경매가 존재하지 않습니다.");

        return auction;
    }

    // Point update 소켓 요청
    private void sendPointUpdateRequest(List<UpdatePointSocketRes> updatePointSocketResList){
        log.info("socket path : " + NOTICE_SERVER_URI + "/points/realtime");
        URI uri = UriComponentsBuilder.fromUriString(NOTICE_SERVER_URI + "/points/realtime").build().toUri();

        HttpEntity<List<UpdatePointSocketRes>> httpEntity = new HttpEntity<>(updatePointSocketResList);

        restTemplate.postForEntity(uri, httpEntity, Void.class);
    }
}
