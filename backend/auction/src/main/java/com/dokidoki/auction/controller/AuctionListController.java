package com.dokidoki.auction.controller;

import com.dokidoki.auction.common.BaseResponseBody;
import com.dokidoki.auction.dto.response.PaginationResponse;
import com.dokidoki.auction.service.AuctionListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/auctions/lists")
@RequiredArgsConstructor
public class AuctionListController {
    private final AuctionListService auctionListService;

    /*
    메인 페이지 : 종료된 경매 목록 가져오기
     */
    @GetMapping("/end")
    public ResponseEntity<BaseResponseBody> readSimpleAuctionEnds(
            @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        // 데이터 조회
        PaginationResponse paginationResponse = auctionListService
                .readSimpleAuctionEnd(PageRequest.of(page, size));

        return ResponseEntity
                .status(200)
                .body(BaseResponseBody.of("종료된 경매 목록 조회 성공", paginationResponse));
    }

    /*
    메인 페이지 : 진행중인 경매 목록 가져오기
     */
    @GetMapping("/in-progress")
    public ResponseEntity<BaseResponseBody> readSimpleAuctionIng(
            @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        // 데이터 조회
        PaginationResponse paginationResponse = auctionListService
                .readSimpleAuctionIng(PageRequest.of(page, size));

        return ResponseEntity
                .status(200)
                .body(BaseResponseBody.of("진행중인 경매 목록 조회 성공", paginationResponse));
    }

    /*
    메인 페이지 : 마감임박 경매 목록 가져오기
     */
    @GetMapping("/deadline")
    public ResponseEntity<BaseResponseBody> readSimpleAuctionDeadline(
            @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
            // 데이터 조회
            PaginationResponse paginationResponse = auctionListService
                    .readSimpleAuctionDeadline(PageRequest.of(page, size));

            return ResponseEntity
                    .status(200)
                    .body(BaseResponseBody.of("마감임박 경매 목록 조회 성공", paginationResponse));
    }

    /*
    메인 페이지 : 키워드 및 카테고리 검색
     */
    @GetMapping("/search")
    public ResponseEntity<BaseResponseBody> searchSimpleAuctionIng(
            @RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "0") Long category_id,
            @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        PaginationResponse paginationResponse = auctionListService
                .searchSimpleAuctionIng(keyword, category_id, PageRequest.of(page, size));

        return ResponseEntity
                .status(200)
                .body(BaseResponseBody.of("경매 목록 검색 성공", paginationResponse));
    }
}
