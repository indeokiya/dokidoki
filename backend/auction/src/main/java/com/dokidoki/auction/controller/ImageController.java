package com.dokidoki.auction.controller;

import com.dokidoki.auction.dto.request.AuctionImagesRequest;
import com.dokidoki.auction.dto.request.ProfileImageRequest;
import com.dokidoki.auction.dto.response.CommonResponse;
import com.dokidoki.auction.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
@Slf4j
public class ImageController {
    private final ImageService imageService;

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

        int statusCode = 201;
        String message = "제품 사진이 등록되었습니다.";
        List<String> auctionImageUrls = imageService.createAuctionImages(optionalAuctionImagesRequest.get());

        if (auctionImageUrls == null) {
            statusCode = 400;
            message = "제품 사진 등록에 실패했습니다.";
        }

        return new ResponseEntity<>(
                CommonResponse.of(statusCode, message, auctionImageUrls),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/profiles")
    public ResponseEntity<CommonResponse<String>> createProfileImage(Optional<ProfileImageRequest> optionalProfileImageRequest) {
        if (optionalProfileImageRequest.isEmpty()
                || optionalProfileImageRequest.get().getMember_id() == null
                || optionalProfileImageRequest.get().getFile() == null) {
            return new ResponseEntity<>(
                    CommonResponse.of(400, "요청받은 정보가 없습니다.", null),
                    HttpStatus.BAD_REQUEST
            );
        }

        int statusCode = 201;
        String message = "프로필 사진이 등록되었습니다.";
        String profileImageUrl = imageService.createProfileImage(optionalProfileImageRequest.get());

        if (profileImageUrl == null) {
            statusCode = 400;
            message = "프로필 사진 등록에 실패했습니다.";
        }

        return new ResponseEntity<>(
                CommonResponse.of(statusCode, message, profileImageUrl),
                HttpStatus.CREATED
        );
    }
}
