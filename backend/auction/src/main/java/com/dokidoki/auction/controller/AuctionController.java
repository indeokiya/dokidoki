package com.dokidoki.auction.controller;

import com.dokidoki.auction.common.BaseResponseBody;
import com.dokidoki.auction.common.JWTUtil;
import com.dokidoki.auction.domain.entity.AuctionIngEntity;
import com.dokidoki.auction.dto.request.AuctionRegisterReq;
import com.dokidoki.auction.dto.request.AuctionUpdateReq;
import com.dokidoki.auction.dto.response.CommonResponse;
import com.dokidoki.auction.dto.response.ProductResp;
import com.dokidoki.auction.service.AuctionService;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
public class AuctionController {

    private final AuctionService auctionService;
    private final JWTUtil jwtUtil;

//    private final KafkaAuctionProducer producer;

    // 카테고리 조회
//    @GetMapping("/products/{category_id}")
//    @Operation(summary = "category의 id를 선택 시에 대한 요청", description = "카테고리에 해당 제품 목록으로 응답")
//    public ResponseEntity<List<CategoryResp>> getCategoryList(@PathVariable("category_id") Long catId) {
//
//    }

    // 카테고리 기준 제품 목록 조회
    @GetMapping("/products/{category_id}")
    @Operation(summary = "category의 id를 선택 시에 대한 요청 API", description = "카테고리에 해당 제품 목록으로 응답")
    public ResponseEntity<?> getCategoryList(@PathVariable("category_id") Long catId) {

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
    public ResponseEntity<CommonResponse<Void>> createAuction(
            @RequestBody Optional<AuctionRegisterReq> auctionRegisterReqO,
            HttpServletRequest request) {
        log.debug("POST /auction request : {}", auctionRegisterReqO);

        if (auctionRegisterReqO.isEmpty()) {
            return new ResponseEntity<>(
                    CommonResponse.of(400, "요청받은 데이터가 없습니다.", null),
                    HttpStatus.BAD_REQUEST
            );
        }

        AuctionRegisterReq auctionRegisterReq = auctionRegisterReqO.get();
        Long sellerId = jwtUtil.getUserId(request);
        if (sellerId == null) {
            return new ResponseEntity<>(
                    CommonResponse.of(403, "토큰이 유효하지 않습니다.", null),
                    HttpStatus.FORBIDDEN
            );
        }

        String msg = auctionService.createAuction(auctionRegisterReq, sellerId);

        if (msg.equals("제품에 대한 정보가 존재하지 않습니다.")) {
            return new ResponseEntity<>(
                    CommonResponse.of(400, msg, null),
                    HttpStatus.NOT_FOUND
            );
        }

        // 성공적으로 등록되면 카프카에 auction.register 메시지 발행.
        // producer.sendAuctionRegister(new KafkaAuctionRegisterDTO(auctionRegisterReq));

        return new ResponseEntity<>(
                CommonResponse.of(201, msg, null),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/auctions")
    public ResponseEntity<BaseResponseBody> updateAuction(
            @RequestParam("auction_id") @ApiParam(value = "경매 id", required = true) Long auctionId,
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

}
