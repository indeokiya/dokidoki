package com.dokidoki.auction.controller;

import com.dokidoki.auction.common.BaseResponseBody;
import com.dokidoki.auction.common.JWTUtil;
import com.dokidoki.auction.common.error.exception.InvalidValueException;
import com.dokidoki.auction.domain.entity.AuctionIngEntity;
import com.dokidoki.auction.dto.request.AuctionRegisterReq;
import com.dokidoki.auction.dto.request.AuctionUpdateReq;
import com.dokidoki.auction.dto.response.DetailAuctionEndResp;
import com.dokidoki.auction.dto.response.DetailAuctionIngResp;
import com.dokidoki.auction.dto.response.ProductResp;
import com.dokidoki.auction.service.AuctionService;
import com.dokidoki.auction.service.InterestService;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
//@CrossOrigin("*")
@RequestMapping("")
public class AuctionController {
    private final InterestService interestService;
    private final AuctionService auctionService;
    private final JWTUtil jwtUtil;

    // 카테고리 기준 제품 목록 조회
    @GetMapping("/products")
    @Operation(summary = "category의 id를 선택 시에 대한 요청 API", description = "카테고리에 해당 제품 목록으로 응답")
    public ResponseEntity<BaseResponseBody> getCategoryList(
            @RequestParam(defaultValue = "") String keyword) {

        log.debug(">>> keyword : {}", keyword);
        List<ProductResp> productList = auctionService.getProductList(keyword);

        return ResponseEntity.status(200).body(BaseResponseBody.of("제품 목록 조회 성공", productList));
    }

    /*
    경매 생성 및 수정 API
     */
    @PostMapping("/new")
    @Operation(summary = "경매 게시글 생성 API", description = "경매 게시글을 작성한다.")
    public ResponseEntity<?> createAuction(AuctionRegisterReq req, HttpServletRequest request) {
        log.debug("POST /auction request : {}", req);

        // 제목 검증
        if (req.getTitle().isBlank())
            return ResponseEntity.status(400).body(BaseResponseBody.of("제목을 입력해주세요."));
        // 시작가 검증
        if (req.getOffer_price() == null || req.getOffer_price() < 0)
            return ResponseEntity.status(400).body(BaseResponseBody.of("시작가를 0 이상 입력해주세요."));
        // 경매 단위 검증
        if (req.getPrice_size() == null || req.getPrice_size() < 1)
            return ResponseEntity.status(400).body(BaseResponseBody.of("경매 단위를 1 이상 입력해주세요."));
        // 종료 시간 검증
        if (req.getEnd_at() == null)
            return ResponseEntity.status(400).body(BaseResponseBody.of("종료 시간을 입력해주세요."));
        // 파일 타입 검증
        if (req.getFiles() != null) {  // 파일이 주어졌을 때만 검증
            for (MultipartFile multipartFile : req.getFiles())
                if (multipartFile.getContentType() == null || !multipartFile.getContentType().startsWith("image/"))
                    return ResponseEntity.status(400).body(BaseResponseBody.of("이미지 파일만 등록해주세요."));
        }

        Long sellerId = jwtUtil.getUserId(request);
        auctionService.createAuction(req, sellerId);

        return ResponseEntity.status(201).body(BaseResponseBody.of("경매 게시글이 작성되었습니다."));
    }

    @PutMapping("/{auction_id}")
    public ResponseEntity<BaseResponseBody> updateAuction(
            @PathVariable("auction_id") @ApiParam(value = "경매 id", required = true) Long auctionId,
            @RequestBody @ApiParam(value = "경매 수정 정보", required = true) Optional<AuctionUpdateReq> auctionUpdateReqO,
            HttpServletRequest request) {

        if (auctionUpdateReqO.isEmpty()) {
            return ResponseEntity.status(400).body(BaseResponseBody.of("요청받은 데이터가 없습니다."));
        }

        Long sellerId = jwtUtil.getUserId(request);
        if (sellerId == null)
            return ResponseEntity.status(403).body(BaseResponseBody.of("토큰이 유효하지 않습니다."));

        try {
            if (auctionUpdateReqO.isPresent()) {
                AuctionUpdateReq auctionUpdateReq = auctionUpdateReqO.get();
                AuctionIngEntity auction = auctionService.updateAuction(sellerId, auctionId, auctionUpdateReq);
                if (!auction.equals(null)) {
                    return ResponseEntity.status(200).body(BaseResponseBody.of("경매 정보가 수정되었습니다."));
                }
            }
            return ResponseEntity.status(400).body(BaseResponseBody.of("해당하는 경매가 존재하지 않습니다."));
        } catch (Exception e) {
            return ResponseEntity.status(403).body(BaseResponseBody.of("오류가 발생했습니다", e));
        }

//        if (경매 단위 수정됐으면) {
//            producer.sendAuctionUpdate(~~~);
//        }
    }

    /*
    경매글 상세정보 조회 (진행중, 완료)
     */
    @GetMapping("/in-progress/{auction_id}")
    public ResponseEntity<BaseResponseBody> readAuctionIng(
            @PathVariable Long auction_id,
            HttpServletRequest request) {
        Long memberId = jwtUtil.getUserId(request);
        if (memberId == null)
            return ResponseEntity.status(400).body(BaseResponseBody.of("토큰이 유효하지 않습니다."));

        DetailAuctionIngResp detailAuctionIngResp = auctionService.readAuctionIng(memberId, auction_id);
        if (detailAuctionIngResp == null)
            return ResponseEntity.status(200).body(BaseResponseBody.of("정보가 없습니다."));
        return ResponseEntity
                .status(200)
                .body(BaseResponseBody.of("성공", detailAuctionIngResp));
    }

    @GetMapping("/end/{auction_id}")
    public ResponseEntity<BaseResponseBody> readAuctionEnd(@PathVariable Long auction_id) {
        DetailAuctionEndResp detailAuctionEndResp = auctionService.readAuctionEnd(auction_id);
        if (detailAuctionEndResp == null)
            return ResponseEntity.status(200).body(BaseResponseBody.of("정보가 없습니다."));
        return ResponseEntity
                .status(200)
                .body(BaseResponseBody.of("성공", detailAuctionEndResp));
    }

    /*
    찜꽁 관련 API
     */
    @PostMapping("/interests/{auction_id}")
    public ResponseEntity<BaseResponseBody> addInterest(
            @PathVariable("auction_id") @ApiParam(value = "경매 id", required = true) Long auctionId,
            HttpServletRequest request
    ) {
        Long buyerId = jwtUtil.getUserId(request);
        if (buyerId == null)
            return ResponseEntity.status(400).body(BaseResponseBody.of("토큰이 유효하지 않습니다."));

        if (interestService.addInterest(buyerId, auctionId))
            return ResponseEntity.status(201).body(BaseResponseBody.of("관심목록에 추가되었습니다."));
        else
            return ResponseEntity.status(400).body(BaseResponseBody.of("이미 관심목록에 존재합니다."));
    }

    @DeleteMapping("/interests/{auction_id}")
    public ResponseEntity<BaseResponseBody> deleteInterest(
            @PathVariable("auction_id") @ApiParam(value = "경매 id", required = true) Long auctionId,
            HttpServletRequest request) {
        Long buyerId = jwtUtil.getUserId(request);
        if (buyerId == null)
            return ResponseEntity.status(400).body(BaseResponseBody.of("토큰이 유효하지 않습니다."));

        if (interestService.deleteInterest(buyerId, auctionId))
            return ResponseEntity.status(200).body(BaseResponseBody.of("관심 목록에서 해제되었습니다."));
        else
            return ResponseEntity.status(400).body(BaseResponseBody.of("관심 목록에 존재하지 않습니다."));
    }

}
