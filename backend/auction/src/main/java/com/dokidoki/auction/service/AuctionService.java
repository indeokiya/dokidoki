package com.dokidoki.auction.service;

import com.dokidoki.auction.domain.entity.AuctionIng;
import com.dokidoki.auction.domain.entity.Category;
import com.dokidoki.auction.domain.entity.Product;
import com.dokidoki.auction.domain.repository.AuctionIngRepository;
import com.dokidoki.auction.domain.repository.CategoryRepository;
import com.dokidoki.auction.domain.repository.ProductRepository;
import com.dokidoki.auction.dto.request.AuctionRegisterReq;
import com.dokidoki.auction.dto.request.AuctionUpdateReq;
import com.dokidoki.auction.dto.response.CategoryResp;
import com.dokidoki.auction.dto.response.ProductResp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuctionService {

    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    private final AuctionIngRepository auctionIngRepository;


    // 카테고리 목록 조회
    @Transactional
    public List<CategoryResp> getCategoryList() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryResp> categoryList = new ArrayList<>();

        for (Category category : categories) {
            CategoryResp categoryResp = CategoryResp.builder()
                    .id(category.getId())
                    .categoryName(category.getCategoryName())
                    .build();

            categoryList.add(categoryResp);
        }

        return categoryList;
    }

    // 카테고리 기준 제품 목록 조회
    @Transactional
    public List<ProductResp> getProductList(Long catId) {
        List<Product> products = productRepository.findByCategory_Id(catId);
        List<ProductResp> productList = new ArrayList<>();

        for (Product product : products) {
            ProductResp productResp = ProductResp.builder()
                    .productId(product.getId())
                    .name(product.getName())
                    .imgUrl(product.getImgUrl())
                    .saleCnt(product.getSaleCnt())
                    .build();
            productList.add(productResp);
        }

        return productList;
    }

    // 제품 등록시 판매 빈도 증가


    @Transactional
    public String createAuction(AuctionRegisterReq auctionRegisterReq, long sellerId) {
        Optional<Product> productO = productRepository.findById(auctionRegisterReq.getProductId());

        if (productO.isEmpty()) {
            return "제품에 대한 정보가 존재하지 않습니다.";
        }

        Product product = productO.get();

        AuctionIng auctionIng = AuctionIng.builder()
                .sellerId(sellerId)
                .product(product)
                .title(auctionRegisterReq.getTitle())
                .description(auctionRegisterReq.getDescription())
                .offerPrice(auctionRegisterReq.getOfferPrice())
                .priceSize(auctionRegisterReq.getPriceSize())
                .endAt(auctionRegisterReq.getEndAt())
                .meetingPlace(auctionRegisterReq.getMeetingPlace())
                .build();

        auctionIngRepository.save(auctionIng);
        return "경매 게시글이 작성되었습니다.";
    }

    @Transactional
    public AuctionIng updateAuction(long sellerId, long auctionId, AuctionUpdateReq auctionUpdateReq) throws Exception {

        Optional<AuctionIng> auctionO = auctionIngRepository.findById(auctionId);
        AuctionIng auction = auctionO.get();

        // 요청자와 판매자가 동일한 경우에만 update 수행
        if (auction.getSellerId() == sellerId)
            auction.update(auctionUpdateReq);
        return auction;
    }

    /**
     * 경매 id에 해당하는 진행중인 경매 정보 조회
     * @param id
     * @return
     * @throws NoSuchElementException
     */
    @Transactional
    public AuctionIng getAuctioningProduct(Long id) throws NoSuchElementException {
        AuctionIng auction = auctionIngRepository.findById(id).get();
        return auction;
    }
}
