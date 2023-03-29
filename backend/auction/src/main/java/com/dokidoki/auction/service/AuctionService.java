package com.dokidoki.auction.service;

import com.dokidoki.auction.common.error.exception.ErrorCode;
import com.dokidoki.auction.common.error.exception.InvalidValueException;
import com.dokidoki.auction.domain.entity.*;
import com.dokidoki.auction.domain.repository.*;
import com.dokidoki.auction.dto.request.AuctionRegisterReq;
import com.dokidoki.auction.dto.request.AuctionUpdateReq;
import com.dokidoki.auction.dto.response.*;
import com.dokidoki.auction.kafka.dto.KafkaAuctionRegisterDTO;
import com.dokidoki.auction.kafka.dto.KafkaAuctionUpdateDTO;
import com.dokidoki.auction.kafka.service.KafkaAuctionProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuctionService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final AuctionIngRepository auctionIngRepository;
    private final AuctionEndRepository auctionEndRepository;
    private final MemberRepository memberRepository;
    private final ImageService imageService;
    private final CommentService commentService;
    private final LeaderboardService leaderboardService;
    private final KafkaAuctionProducer producer;

    // 카테고리 목록 조회
    @Transactional
    public List<CategoryResp> getCategoryList() {
        List<CategoryEntity> categories = categoryRepository.findAll();
        List<CategoryResp> categoryList = new ArrayList<>();

        for (CategoryEntity categoryEntity : categories) {
            CategoryResp categoryResp = CategoryResp.builder()
                    .id(categoryEntity.getId())
                    .categoryName(categoryEntity.getCategoryName())
                    .build();

            categoryList.add(categoryResp);
        }

        return categoryList;
    }

    // 총 거래금액 조회
    @Transactional(readOnly = true)
    public Long getTotalPrice() {
        Long totalPrice = auctionEndRepository.getTotalPrice();
        return totalPrice != null ? totalPrice : 0;
    }

    // 카테고리 기준 제품 목록 조회
    @Transactional(readOnly = true)
    public List<ProductResp> getProductList(String keyword) {
        List<ProductEntity> productEntities = productRepository.findByKeyword(keyword);
        List<ProductResp> productList = new ArrayList<>();

        for (ProductEntity productEntity : productEntities) {
            ProductResp productResp = ProductResp.builder()
                    .product_id(productEntity.getId())
                    .name(productEntity.getCategoryEntity().getCategoryName() + " - " + productEntity.getName())
                    .imgUrl(productEntity.getImgUrl())
                    .saleCnt(productEntity.getSaleCnt())
                    .build();
            productList.add(productResp);
        }

        return productList;
    }

    // 제품 등록시 판매 빈도 증가

    // 진행중인 경매 상세정보 조회
    @Transactional(readOnly = true)
    public DetailAuctionIngResponse readAuctionIng(Long auctionId) {
        // 완료된 경매 정보
        DetailAuctionIngInterface detailAuctionIngInterface = auctionIngRepository
                .findAuctionIngEntityById(auctionId);

        // 존재하지 않는다면 null 반환
        if (detailAuctionIngInterface == null)
            return null;

        // 경매 제품 사진 URL 구하기
        List<String> auctionImageUrls = imageService.readAuctionImages(auctionId).getImage_urls();

        // 댓글 구하기
        List<CommentResponse> commentResponses = commentService.readComment(auctionId);

        // 입찰 내역 구하기
        List<LeaderboardHistoryResponse> leaderboardHistoryResponses = leaderboardService.readLeaderboard(auctionId);

        return new DetailAuctionIngResponse(
                detailAuctionIngInterface,
                auctionImageUrls,
                commentResponses,
                leaderboardHistoryResponses
        );
    }

    // 완료된 경매 상세정보 조회
    @Transactional(readOnly = true)
    public DetailAuctionEndResponse readAuctionEnd(Long auction_id) {
        // 완료된 경매 정보
        DetailAuctionEndInterface detailAuctionEndInterface = auctionEndRepository
                .findDetailAuctionEndEntityById(auction_id);

        // 존재하지 않는다면 null 반환
        if (detailAuctionEndInterface == null)
            return null;

        // 경매 제품 사진 URL 구하기
        List<String> auctionImageUrls = imageService.readAuctionImages(auction_id).getImage_urls();

        // 댓글 구하기
        List<CommentResponse> commentResponses = commentService.readComment(auction_id);

        // 입찰 내역 구하기
        List<LeaderboardHistoryResponse> leaderboardHistoryResponses = leaderboardService.readLeaderboard(auction_id);

        return new DetailAuctionEndResponse(
                detailAuctionEndInterface,
                auctionImageUrls,
                commentResponses,
                leaderboardHistoryResponses
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
        
        // TTL 구하기
        Duration duration = Duration.between(LocalDateTime.now(ZoneId.of("Asia/Seoul")), req.getEnd_at());

        long ttl = duration.toMinutes();

        // 성공적으로 등록되면 카프카에 auction.register 메시지 발행.
        producer.sendAuctionRegister(new KafkaAuctionRegisterDTO(req, auctionId, ttl, sellerId, req.getProduct_id(), productEntity.getName()));
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
}
