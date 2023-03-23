package com.dokidoki.auction.controller;

import com.dokidoki.auction.common.BaseResponseBody;
import com.dokidoki.auction.dto.request.AuctionImagesRequest;
import com.dokidoki.auction.dto.response.AuctionImageResponse;
import com.dokidoki.auction.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/{auction_id}/images")
@RequiredArgsConstructor
@Slf4j
public class ImageController {
    private final ImageService imageService;

    /*
        경매 제품 사진 관련 API
     */
    @GetMapping("")
    public ResponseEntity<BaseResponseBody> readAuctionImages(@PathVariable Long auction_id) {
        // 경매 식별번호로, 등록된 제품 사진 검색
        AuctionImageResponse auctionImageResponse = imageService.readAuctionImages(auction_id);

        // 경매 제품 사진 조회에 실패했을 경우,
        if (auctionImageResponse == null)
            return ResponseEntity
                    .status(400)
                    .body(BaseResponseBody.of("경매가 존재하지 않거나 사진을 가져오는 데 실패했습니다."));

        return ResponseEntity.status(200).body(BaseResponseBody.of("성공", auctionImageResponse));
    }

    @PostMapping("")
    public ResponseEntity<BaseResponseBody> createAuctionImages(
            @PathVariable Long auction_id,
            Optional<AuctionImagesRequest> optionalAuctionImagesRequest) {
        AuctionImagesRequest auctionImagesRequest = optionalAuctionImagesRequest.orElse(null);

        // 데이터 들어왔는지 확인
        if (auctionImagesRequest == null
                || auction_id == null
                || auctionImagesRequest.getFiles() == null) {
            return ResponseEntity
                    .status(400)
                    .body(BaseResponseBody.of("요청받은 정보가 없습니다."));
        }

        // 제품 사진 등록
        List<String> auctionImageUrls = imageService.createAuctionImages(auction_id, auctionImagesRequest);

        // 제품 사진 등록에 실패했을 경우,
        if (auctionImageUrls == null)
            return ResponseEntity
                    .status(400)
                    .body(BaseResponseBody.of("제품 사진 등록에 실패했습니다."));

        return ResponseEntity
                .status(201)
                .body(BaseResponseBody.of("제품 사진이 등록되었습니다.", auctionImageUrls));
    }

    @DeleteMapping("")
    public ResponseEntity<BaseResponseBody> deleteProfileImages(@PathVariable Long auction_id) {
        imageService.deleteAuctionImages(auction_id);
        return ResponseEntity.status(200).body(BaseResponseBody.of("성공"));
    }
}
