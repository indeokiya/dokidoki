package com.dokidoki.auction.service;

import com.dokidoki.auction.domain.entity.AuctionEndEntity;
import com.dokidoki.auction.domain.entity.AuctionIngEntity;
import com.dokidoki.auction.domain.repository.AuctionEndRepository;
import com.dokidoki.auction.domain.repository.AuctionIngRepository;
import com.dokidoki.auction.domain.repository.InterestRepository;
import com.dokidoki.auction.dto.response.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuctionListService {
    private final AuctionEndRepository auctionEndRepository;
    private final AuctionIngRepository auctionIngRepository;
    private final InterestRepository interestRepository;
    private final ImageService imageService;

    /*
    종료된 경매 목록 조회
     */
    @Transactional(readOnly = true)
    public PaginationResponse readAuctionEndList(Integer page, Integer size) {
        // 데이터 조회
        Page<AuctionEndEntity> auctionEndEntities = auctionEndRepository
                .findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));

        // 각 경매의 대표 이미지 가져오기
        List<Long> auctionIdList = new ArrayList<>();
        for (AuctionEndEntity auctionEndEntity : auctionEndEntities)
            auctionIdList.add(auctionEndEntity.getId());
        List<ImageInterface> imageInterfaces = imageService
                .readAuctionThumbnailImage(auctionIdList);

        // 데이터 조합
        List<SimpleAuctionEndInfo> simpleAuctionEndInfos = new ArrayList<>();
        int imageIdx = 0;
        for (int i = 0; i < auctionEndEntities.getContent().size(); i++) {
            AuctionEndEntity auctionEndEntity = auctionEndEntities.getContent().get(i);

            /*
            조회한 경매 목록과 각 경매의 이미지 조회
            e.g.)
                i: 경매1, 경매2, 경매3, 경매4, ...
                imageIdx : 사진1, 사진2, 사진4, ...
                위와 같이 데이터가 존재할 것을 대비해 imageIdx를 따로 생성하여 관리
             */
            // 이미지 정보 가져오기
            String imageUrl = null;  // 이미지 URL
            if (imageIdx < imageInterfaces.size()) {
                Long imageAuctionId = imageInterfaces.get(imageIdx).getAuction_id();
                // 조회한 경매와 이미지가 동일 제품이라면 imageUrl 설정
                if (imageAuctionId.equals(auctionEndEntity.getId())) {
                    imageUrl = imageInterfaces.get(imageIdx).getImage_url();
                    imageIdx++;  // 다음 이미지로 넘어가기
                }
            }

            // Response DTO 구성
            simpleAuctionEndInfos.add(
                    new SimpleAuctionEndInfo(
                            auctionEndEntity,
                            imageUrl
                    )
            );
        }

        // Response DTO 생성 및 반환
        return PaginationResponse.of(
                simpleAuctionEndInfos,
                auctionEndEntities.isLast()
        );
    }

    /*
    진행중인 전체 경매 목록 조회
     */
    @Transactional(readOnly = true)
    public PaginationResponse readAuctionIngList(Long memberId, Integer page, Integer size) {
        // 데이터 조회
        Page<AuctionIngEntity> auctionIngEntities = auctionIngRepository
                .findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));
        return convertToDTOWithImages(memberId, auctionIngEntities);
    }

    /*
    진행중인 마감임박 경매 목록 조회
     */
    @Transactional(readOnly = true)
    public PaginationResponse readSimpleAuctionDeadline(Long memberId, Pageable pageable) {
        // 데이터 조회
        Page<AuctionIngEntity> auctionIngEntities = auctionIngRepository
                .findAllSimpleDeadlineList(pageable);
        return convertToDTOWithImages(memberId, auctionIngEntities);
    }

    /*
    진행중인 경매 목록 검색
     */
    @Transactional(readOnly = true)
    public PaginationResponse searchAuctionIngList(
            Long memberId, String keyword, Long categoryId, Pageable pageable) {
        // 불필요 문자 제거
        keyword = keyword.strip();

        // 데이터 조회, 카테고리 설정 여부에 따라 (키워드, 카테고리) 검색과 (키워드) 검색으로 분기
        Page<AuctionIngEntity> auctionIngEntities = null;
        if (categoryId == 0)
            auctionIngEntities = auctionIngRepository
                    .findAllSimpleIngListByKeyword(keyword, pageable);
        else
            auctionIngEntities = auctionIngRepository
                    .findAllSimpleIngListByKeywordANDCategoryId(keyword, categoryId, pageable);

        return convertToDTOWithImages(memberId, auctionIngEntities);
    }

    /*
    DB에서 조회한 진행중인 경매 목록 데이터에 제품 이미지를 추가하며 DTO로 변환
     */
    public PaginationResponse convertToDTOWithImages(
            Long memberId,
            Page<AuctionIngEntity> auctionIngEntities) {
        // 관심있는 경매 ID 가져오기
        List<InterestMapping> interestMappings = interestRepository.findAllByMemberEntity_Id(memberId);
        Set<Long> interestsOfUser = new HashSet<>();
        interestMappings.forEach(interestMapping -> {
            interestsOfUser.add(interestMapping.getAuctionIngEntity().getId());
        });

        // 판매중인 경매 ID 가져오기
        List<AuctionIngMapping> auctionIngMappings = auctionIngRepository.findAuctionIngEntityBySeller_Id(memberId);
        Set<Long> salesOfUser = new HashSet<>();
        auctionIngMappings.forEach(auctionIngMapping -> {
            salesOfUser.add(auctionIngMapping.getSeller().getId());
        });

        // 각 경매의 대표 이미지 가져오기
        List<Long> auctionIdList = new ArrayList<>();
        for (AuctionIngEntity auctionIngEntity : auctionIngEntities)
            auctionIdList.add(auctionIngEntity.getId());
        List<ImageInterface> imageInterfaces = imageService
                .readAuctionThumbnailImage(auctionIdList);

        // 데이터 조합
        List<SimpleAuctionIngInfo> simpleAuctionIngInfos = new ArrayList<>();
        int imageIdx = 0;
        for (int i = 0; i < auctionIngEntities.getContent().size(); i++) {
            AuctionIngEntity auctionIngEntity = auctionIngEntities.getContent().get(i);

            // 경매번호 및 이미지 가져오기, readSimpleAuctionEnd와 동일
            String imageUrl = null;
            if (imageIdx < imageInterfaces.size()) {
                Long imageAuctionId = imageInterfaces.get(imageIdx).getAuction_id();
                if (imageAuctionId.equals(auctionIngEntity.getId())) {
                    imageUrl = imageInterfaces.get(imageIdx).getImage_url();
                    imageIdx++;
                }
            }

            // Response DTO 담기
            simpleAuctionIngInfos.add(
                    new SimpleAuctionIngInfo(
                            auctionIngEntity,
                            imageUrl,
                            interestsOfUser,
                            salesOfUser
                    )
            );
        }

        // Response DTO 생성 및 반환
        return PaginationResponse.of(
                simpleAuctionIngInfos,
                auctionIngEntities.isLast()
        );
    }
}
