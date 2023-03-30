package com.dokidoki.auction.controller;

import com.dokidoki.auction.common.BaseResponseBody;
import com.dokidoki.auction.common.JWTUtil;
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

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequestMapping("/lists")
@RequiredArgsConstructor
public class AuctionListController {
    private final AuctionListService auctionListService;
    private final JWTUtil jwtUtil;

    /*
    메인 페이지 : 종료된 경매 목록 가져오기
     */
    @GetMapping("/end")
    public ResponseEntity<BaseResponseBody> readAuctionEndList(
            @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        // 데이터 조회
        PaginationResponse paginationResponse = auctionListService
                .readAuctionEndList(page, size);

        return ResponseEntity
                .status(200)
                .body(BaseResponseBody.of("종료된 경매 목록 조회 성공", paginationResponse));
    }

    /*
    메인 페이지 : 진행중인 경매 목록 가져오기
     */
    @GetMapping("/in-progress")
    public ResponseEntity<BaseResponseBody> readAuctionList(
            @RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "0") Long category_id,
            @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size,
            HttpServletRequest request) {
        // 토큰 member id 확인
        Long memberId = jwtUtil.getUserId(request);

        // 데이터 조회
        PaginationResponse paginationResponse;
        String msg = "";

        // 검색어, 카테고리 모두 비어있다면 전체 검색
        if (keyword.equals("") && category_id == 0) {
            paginationResponse = auctionListService
                    .readAuctionIngList(memberId, page, size);
            msg = "진행중인 경매 목록 조회 성공";
        } else {
            paginationResponse = auctionListService
                    .searchAuctionIngList(memberId, keyword, category_id, PageRequest.of(page, size));
            msg = "진행중인 경매 목록 검색 성공";
        }

        return ResponseEntity
                .status(200)
                .body(BaseResponseBody.of(msg, paginationResponse));
    }

    /*
    메인 페이지 : 마감임박 경매 목록 가져오기
     */
    @GetMapping("/deadline")
    public ResponseEntity<BaseResponseBody> readSimpleAuctionDeadline(
            @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size,
            HttpServletRequest request) {
            // 토큰 member id 확인
            Long memberId = jwtUtil.getUserId(request);

            // 데이터 조회
            PaginationResponse paginationResponse = auctionListService
                    .readSimpleAuctionDeadline(memberId, PageRequest.of(page, size));

            return ResponseEntity
                    .status(200)
                    .body(BaseResponseBody.of("마감임박 경매 목록 조회 성공", paginationResponse));
    }
}
