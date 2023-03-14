package com.dokidoki.auction.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.dokidoki.auction.domain.entity.AuctionImage;
import com.dokidoki.auction.domain.entity.Member;
import com.dokidoki.auction.domain.entity.ProfileImage;
import com.dokidoki.auction.domain.repository.AuctionImageRepository;
import com.dokidoki.auction.domain.repository.ProfileImageRepository;
import com.dokidoki.auction.domain.repository.MemberRepository;
import com.dokidoki.auction.dto.request.AuctionImagesRequest;
import com.dokidoki.auction.dto.request.ProfileImageRequest;
import com.dokidoki.auction.dto.response.AuctionImageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Component
@Service
public class ImageService {
    private final MemberRepository memberRepository;
    private final ProfileImageRepository profileImageRepository;
    private final AuctionImageRepository auctionImageRepository;
    private final AmazonS3 amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public AuctionImageResponse readAuctionImages(Long auction_id) {
        // AuctionImage Entity 검색
        List<AuctionImage> auctionImages = auctionImageRepository.findAuctionImagesByAuctionId(auction_id);

        // Image URL 추출
        List<String> auctionImageUrls = new ArrayList<>();
        auctionImages.forEach(auctionImage -> {
            auctionImageUrls.add(auctionImage.getImageUrl());
        });

        return new AuctionImageResponse(auction_id, auctionImageUrls);
    }

    @Transactional
    public List<String> createAuctionImages(AuctionImagesRequest auctionImagesRequest) {
        // 파일 업로드 및 URL 획득
        List<String> auctionImagesUrls = uploadImages(auctionImagesRequest.getFiles(), "auctions");

        // 객체 생성 및 저장
        auctionImagesUrls.forEach(auctionImagesUrl -> {
            AuctionImage auctionImage = AuctionImage.createAuctionImage(
                    auctionImagesRequest.getAuction_id(),
                    auctionImagesUrl
            );
            auctionImageRepository.save(auctionImage);
        });

        return auctionImagesUrls;
    }

    @Transactional
    public String createProfileImage(ProfileImageRequest profileImageRequest) {
        // 파일 업로드 및 URL 획득
        String profileImageUrl = uploadImage(profileImageRequest.getFile(), "profiles");
        if (profileImageUrl == null)
            return null;

        // 사용자 존재 여부 확인
        Optional<Member> optionalMember = memberRepository.findById(profileImageRequest.getMember_id());
        if (optionalMember.isEmpty())
            return null;

        // 객체 생성 및 저장
        ProfileImage profileImage = ProfileImage.createProfileImage(optionalMember.get(), profileImageUrl);
        profileImageRepository.save(profileImage);

        return profileImageUrl;
    }

    public List<String> uploadImages(MultipartFile[] multipartFiles, String dirName) {
        List<String> urls = new ArrayList<>();

        Arrays.stream(multipartFiles).forEach(multipartFile -> {
            urls.add(uploadImage(multipartFile, dirName));
        });

        return urls;
    }

    public String uploadImage(MultipartFile multipartFile, String dirName) {
        // 사진 파일이 아닐 경우 종료
        if (!multipartFile.getContentType().startsWith("image/")) {
            return null;
        }

        // S3 업로드 할 파일 이름 생성
        String s3FileName = dirName + "/" + UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

        try {
            // MultipartFile -> File 변환
            File convertFile = convert(multipartFile)
                    .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 변환 실패"));

            // S3 업로드 및 URL 획득
            String url = putS3(convertFile, s3FileName);

            // 로컬 파일 삭제
            removeTempFile(convertFile);

            return url;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(
                new PutObjectRequest(
                        bucket, fileName, uploadFile
                ).withCannedAcl(CannedAccessControlList.PublicRead)
        );
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeTempFile(File targetFile) {
        if (!targetFile.delete()) {
            log.warn("파일이 삭제되지 않았습니다.");
        }
    }

    private Optional<File> convert(MultipartFile multipartFile) throws IOException {
        // 로컬 파일 생성 및 반환
        File convertFile = new File(UUID.randomUUID().toString());
        if(convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(multipartFile.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }
}
