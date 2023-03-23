package com.dokidoki.auction.service;

import com.dokidoki.auction.common.error.exception.InvalidValueException;
import com.dokidoki.auction.domain.entity.*;
import com.dokidoki.auction.domain.repository.*;
import com.dokidoki.auction.dto.request.AuctionRegisterReq;
import com.dokidoki.auction.dto.request.AuctionUpdateReq;
import com.dokidoki.auction.dto.response.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    // 카테고리 기준 제품 목록 조회
    @Transactional
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
    public String createAuction(AuctionRegisterReq auctionRegisterReq, Long sellerId) {
        Optional<ProductEntity> productO = productRepository.findById(auctionRegisterReq.getProductId());

        if (productO.isEmpty()) {
            return "제품에 대한 정보가 존재하지 않습니다.";
        }

        // 요청자 객체 획득
        MemberEntity requestMemberEntity = memberRepository.findById(sellerId).orElse(null);
        if (requestMemberEntity == null)
            return null;

        ProductEntity productEntity = productO.get();

        AuctionIngEntity auctionIngEntity = AuctionIngEntity.builder()
                .seller(requestMemberEntity)
                .productEntity(productEntity)
                .title(auctionRegisterReq.getTitle())
                .description(auctionRegisterReq.getDescription())
                .offerPrice(auctionRegisterReq.getOfferPrice())
                .priceSize(auctionRegisterReq.getPriceSize())
                .endAt(auctionRegisterReq.getEndAt())
                .meetingPlace(auctionRegisterReq.getMeetingPlace())
                .build();

        auctionIngRepository.save(auctionIngEntity);
        return "경매 게시글이 작성되었습니다.";
    }

    @Transactional
    public AuctionIngEntity updateAuction(Long sellerId, Long auctionId, AuctionUpdateReq auctionUpdateReq) {

        AuctionIngEntity auction = auctionIngRepository.findById(auctionId).orElse(null);

        // 요청자와 판매자가 동일한 경우에만 update 수행
        if (auction != null && sellerId.equals(auction.getSeller().getId())) {
            auction.update(auctionUpdateReq);
            return auction;
        }

        return null;
    }

    /**
     * 경매 id에 해당하는 진행중인 경매 정보 조회
     * @param   auctionId
     * @return 진행중인 경매 정보
     */
    @Transactional
    public AuctionIngEntity getAuctioningById(Long auctionId) {

        AuctionIngEntity auction = auctionIngRepository.findById(auctionId)
                .orElse(null);
        if (auction == null)
            throw new InvalidValueException("진행중인 경매가 존재하지 않습니다.");

        return auction;
    }
}
