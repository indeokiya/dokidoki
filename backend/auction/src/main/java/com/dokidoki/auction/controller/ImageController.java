package com.dokidoki.auction.controller;

import com.dokidoki.auction.dto.request.AuctionImagesRequest;
import com.dokidoki.auction.dto.response.AuctionImageResponse;
import com.dokidoki.auction.dto.response.CommonResponse;
import com.dokidoki.auction.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
@Slf4j
public class ImageController {
    private final ImageService imageService;

    /*
        경매 제품 사진 관련 API
     */
    @GetMapping("/auctions/{auction_id}")
    public ResponseEntity<CommonResponse<AuctionImageResponse>> readAuctionImages(@PathVariable Long auction_id) {
        // 경매 식별번호로, 등록된 제품 사진 검색
        AuctionImageResponse auctionImageResponse = imageService.readAuctionImages(auction_id);

        // 경매 제품 사진 조회에 실패했을 경우,
        if (auctionImageResponse == null) {
            return new ResponseEntity<>(
                    CommonResponse.of(400, "경매가 존재하지 않거나 사진을 가져오는 데 실패했습니다.", null),
                    HttpStatus.BAD_REQUEST
            );
        }

        return new ResponseEntity<>(
                CommonResponse.of(200, "성공", auctionImageResponse),
                HttpStatus.OK
        );
    }

    @PostMapping("/auctions")
    public ResponseEntity<CommonResponse<List<String>>> createAuctionImages(Optional<AuctionImagesRequest> optionalAuctionImagesRequest) {
        AuctionImagesRequest auctionImagesRequest = optionalAuctionImagesRequest.orElse(null);

        // 데이터 들어왔는지 확인
        if (auctionImagesRequest == null
                || auctionImagesRequest.getAuction_id() == null
                || auctionImagesRequest.getFiles() == null) {
            return new ResponseEntity<>(
                    CommonResponse.of(400, "요청받은 정보가 없습니다.", null),
                    HttpStatus.BAD_REQUEST
            );
        }

        // 제품 사진 등록
        List<String> auctionImageUrls = imageService.createAuctionImages(auctionImagesRequest);

        // 제품 사진 등록에 실패했을 경우,
        if (auctionImageUrls == null) {
            return new ResponseEntity<>(
                    CommonResponse.of(400, "제품 사진 등록에 실패했습니다.", null),
                    HttpStatus.BAD_REQUEST
            );
        }

        return new ResponseEntity<>(
                CommonResponse.of(201, "제품 사진이 등록되었습니다.", auctionImageUrls),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/auctions/{auction_id}")
    public ResponseEntity<CommonResponse<Void>> deleteProfileImages(@PathVariable Long auction_id) {
        imageService.deleteAuctionImages(auction_id);
        return new ResponseEntity<>(
                CommonResponse.of(200, "성공", null),
                HttpStatus.OK
        );
    }
}
