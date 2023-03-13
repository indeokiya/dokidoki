package com.dokidoki.auction.service;

import com.dokidoki.auction.domain.entity.AuctionIng;
import com.dokidoki.auction.domain.entity.Category;
import com.dokidoki.auction.domain.entity.Product;
import com.dokidoki.auction.domain.repository.AuctionIngRepository;
import com.dokidoki.auction.domain.repository.CategoryRepository;
import com.dokidoki.auction.domain.repository.ProductRepository;
import com.dokidoki.auction.dto.request.AuctionRegisterReq;
import com.dokidoki.auction.dto.response.CategoryResp;
import com.dokidoki.auction.dto.response.ProductResp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
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
    public List<ProductResp> getProductList(long catId) {
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
    public String createAuction(AuctionRegisterReq auctionRegisterReq) {
        Optional<Product> productO = productRepository.findById(auctionRegisterReq.getProductId());

        if (productO.isEmpty()) {
            return "no contents with id";
        }

        Product product = productO.get();

        AuctionIng auctionIng = AuctionIng.builder()
                .sellerId(1L)
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
}
