package com.dokidoki.auction.controller;

import com.dokidoki.auction.domain.entity.Product;
import com.dokidoki.auction.dto.request.AuctionRegisterReq;
import com.dokidoki.auction.dto.response.CategoryResp;
import com.dokidoki.auction.dto.response.CommonResponse;
import com.dokidoki.auction.dto.response.ProductResp;
import com.dokidoki.auction.service.AuctionService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AuctionController {

    private final AuctionService auctionService;

    // 카테고리 조회
//    @GetMapping("/products/{category_id}")
//    @Operation(summary = "category의 id를 선택 시에 대한 요청", description = "카테고리에 해당 제품 목록으로 응답")
//    public ResponseEntity<List<CategoryResp>> getCategoryList(@PathVariable("category_id") long catId) {
//
//        List<CategoryResp>
//    }

    // 카테고리 기준 제품 목록 조회
    @GetMapping("/products/{category_id}")
    @Operation(summary = "category의 id를 선택 시에 대한 요청 API", description = "카테고리에 해당 제품 목록으로 응답")
    public ResponseEntity<?> getCategoryList(@PathVariable("category_id") long catId) {

        log.debug(">>> categoryId : {}", catId);
        List<ProductResp> productList = auctionService.getProductList(catId);

        if (productList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("해당 카테고리에 해당하는 제품 목록이 없습니다.");
        }
        return ResponseEntity.ok(productList);
    }

    @PostMapping("/auctions")
    @Operation(summary = "경매 게시글 생성 API", description = "경매 게시글을 작성한다.")
    public ResponseEntity<CommonResponse<Void>> createAuction(@RequestBody Optional<AuctionRegisterReq> auctionRegisterReqO) {
        log.debug("POST /auction request : {}", auctionRegisterReqO);

        if (auctionRegisterReqO.isEmpty()) {
            return new ResponseEntity<>(
                    CommonResponse.of(400, "요청받은 데이터가 없습니다.", null),
                    HttpStatus.BAD_REQUEST
            );
        }

        AuctionRegisterReq auctionRegisterReq = auctionRegisterReqO.get();

        String msg = auctionService.createAuction(auctionRegisterReq);

        if (msg == "no contents with id") {
            return new ResponseEntity<>(
                    CommonResponse.of(400, msg, null),
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                CommonResponse.of(201, msg, null),
                HttpStatus.CREATED
        );
    }



}
