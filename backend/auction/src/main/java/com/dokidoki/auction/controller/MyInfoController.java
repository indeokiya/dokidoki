package com.dokidoki.auction.controller;

import com.dokidoki.auction.common.BaseResponseBody;
import com.dokidoki.auction.common.JWTUtil;
import com.dokidoki.auction.dto.response.MyHistoryResp;
import com.dokidoki.auction.dto.response.PaginationResp;
import com.dokidoki.auction.service.MyInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/my-infos")
public class MyInfoController {
    private final MyInfoService myInfoService;
    private final JWTUtil jwtUtil;

    /*
    판매중인 경매 목록 조회
     */
    @GetMapping("/bade")
    public ResponseEntity<BaseResponseBody> readAllMySellingAuction(
            @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size,
            HttpServletRequest request) {
        // 요청자 확인
        Long memberId = jwtUtil.getUserId(request);
        if (memberId == null)
            return ResponseEntity.status(403).body(BaseResponseBody.of("토큰이 유효하지 않습니다."));

        // 데이터 조회 및 반환
        PaginationResp paginationResp = myInfoService
                .readAllMySellingAuction(memberId, PageRequest.of(page, size));
        return ResponseEntity.status(200).body(BaseResponseBody.of("판매중인 경매 목록 조회 성공", paginationResp));
    }

    /*
    입찰중인 경매 목록 조회
     */
    @GetMapping("/bids")
    public ResponseEntity<BaseResponseBody> readAllMyBiddingAuction(
            @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size,
            HttpServletRequest request) {
        // 요청자 확인
        Long memberId = jwtUtil.getUserId(request);
        if (memberId == null)
            return ResponseEntity.status(403).body(BaseResponseBody.of("토큰이 유효하지 않습니다."));

        // 토큰 파싱
        String authorizationHeader = request.getHeader("Authorization");
        String accessToken = authorizationHeader.substring(7);

        // 데이터 조회 및 반환
        PaginationResp paginationResp = myInfoService
                .readAllMyBiddingAuction(accessToken, memberId, PageRequest.of(page, size));
        return ResponseEntity.status(200).body(BaseResponseBody.of("입찰중인 경매 목록 조회 성공", paginationResp));
    }

    /*
    관심있는 경매 목록 조회
     */
    @GetMapping("/interests")
    public ResponseEntity<BaseResponseBody> readAllMyInterestingAuction(
            @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size,
            HttpServletRequest request) {
        // 요청자 확인
        Long memberId = jwtUtil.getUserId(request);
        if (memberId == null)
            return ResponseEntity.status(403).body(BaseResponseBody.of("토큰이 유효하지 않습니다."));

        // 데이터 조회 및 반환
        PaginationResp paginationResp = myInfoService
                .readAllMyInterestingAuction(memberId, PageRequest.of(page, size));
        return ResponseEntity.status(200).body(BaseResponseBody.of("관심있는 경매 목록 조회 성공", paginationResp));
    }

    /*
    구매내역 조회
     */
    @GetMapping("/purchases")
    public ResponseEntity<BaseResponseBody> readAllMyPurchases(
            @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size,
            HttpServletRequest request) {
        // 토큰 확인 및 요청자 파악
        Long memberId = jwtUtil.getUserId(request);
        if (memberId == null)
            return ResponseEntity.status(403).body(BaseResponseBody.of("토큰이 유효하지 않습니다."));

        // 데이터 조회
        MyHistoryResp myHistoryResp = myInfoService.readAllMyPurchases(memberId, PageRequest.of(page, size));
        return ResponseEntity.status(200).body(BaseResponseBody.of("구매내역 조회 성공", myHistoryResp));
    }

    /*
    판매내역 조회
     */
    @GetMapping("/sales")
    public ResponseEntity<BaseResponseBody> readAllMySales(
            @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size,
            HttpServletRequest request) {
        // 토큰 확인 및 요청자 파악
        Long memberId = jwtUtil.getUserId(request);
        if (memberId == null)
            return ResponseEntity.status(403).body(BaseResponseBody.of("토큰이 유효하지 않습니다."));

        // 데이터 조회
        MyHistoryResp myHistoryResp = myInfoService.readAllMySales(memberId, PageRequest.of(page, size));
        return ResponseEntity.status(200).body(BaseResponseBody.of("판매내역 조회 성공", myHistoryResp));
    }

    /*
    총 구매가 조회
     */
    @GetMapping("/total-expenses")
    public ResponseEntity<BaseResponseBody> getMyTotalOfPurchases(HttpServletRequest request) {
        // 토큰 확인 및 요청자 파악
        Long memberId = jwtUtil.getUserId(request);
        if (memberId == null)
            return ResponseEntity.status(403).body(BaseResponseBody.of("토큰이 유효하지 않습니다."));

        Long total = myInfoService.readMyTotalOfPurchases(memberId);
        return ResponseEntity.status(200).body(BaseResponseBody.of("총 구매가 조회 성공", total));
    }

    /*
    총 판매가 조회
     */
    @GetMapping("/total-profits")
    public ResponseEntity<BaseResponseBody> getMyTotalOfSales(HttpServletRequest request) {
        // 토큰 확인 및 요청자 파악
        Long memberId = jwtUtil.getUserId(request);
        if (memberId == null)
            return ResponseEntity.status(403).body(BaseResponseBody.of("토큰이 유효하지 않습니다."));

        Long total = myInfoService.readMyTotalOfSales(memberId);
        return ResponseEntity.status(200).body(BaseResponseBody.of("총 판매가 조회 성공", total));
    }
}
