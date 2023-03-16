package com.dokidoki.auction.controller;

import com.dokidoki.auction.dto.request.AuctionImagesRequest;
import com.dokidoki.auction.dto.request.ProfileImageRequest;
import com.dokidoki.auction.dto.response.AuctionImageResponse;
import com.dokidoki.auction.dto.response.CommonResponse;
import com.dokidoki.auction.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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
        if (optionalAuctionImagesRequest.isEmpty()
                || optionalAuctionImagesRequest.get().getAuction_id() == null
                || optionalAuctionImagesRequest.get().getFiles() == null) {
            return new ResponseEntity<>(
                    CommonResponse.of(400, "요청받은 정보가 없습니다.", null),
                    HttpStatus.BAD_REQUEST
            );
        }

        // 제품 사진 등록
        List<String> auctionImageUrls = imageService.createAuctionImages(optionalAuctionImagesRequest.get());

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

    /*
        사용자 프로필 사진 관련 API
     */
    @GetMapping("/profiles/{member_id}")
    public ResponseEntity<CommonResponse<String>> readProfileImage(@PathVariable Long member_id) {
        String url = imageService.readProfileImage(member_id);

        // 프로필 조회에 실패했을 경우,
        if (url == null) {
            return new ResponseEntity<>(
                    CommonResponse.of(400, "사용자가 없거나 프로필을 가져오는 데 실패했습니다.", null),
                    HttpStatus.BAD_REQUEST
            );
        }

        return new ResponseEntity<>(
                CommonResponse.of(200, "성공", url),
                HttpStatus.OK
        );
    }

    @PutMapping("/profiles")
    public ResponseEntity<CommonResponse<String>> createProfileImage(Optional<ProfileImageRequest> optionalProfileImageRequest) {
        if (optionalProfileImageRequest.isEmpty()
                || optionalProfileImageRequest.get().getMember_id() == null
                || optionalProfileImageRequest.get().getFile() == null) {
            return new ResponseEntity<>(
                    CommonResponse.of(400, "요청받은 정보가 없습니다.", null),
                    HttpStatus.BAD_REQUEST
            );
        }

        // 프로필 사진 등록
        String profileImageUrl = imageService.createProfileImage(optionalProfileImageRequest.get());

        // 프로필 사진 등록에 실패했을 경우,
        if (profileImageUrl == null) {
            return new ResponseEntity<>(
                    CommonResponse.of(400, "프로필 사진 등록에 실패했습니다.", null),
                    HttpStatus.BAD_REQUEST
            );
        }

        return new ResponseEntity<>(
                CommonResponse.of(201, "프로필 사진이 등록되었습니다.", profileImageUrl),
                HttpStatus.CREATED
        );
    }
}
