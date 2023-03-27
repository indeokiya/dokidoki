package com.dokidoki.auction.service;

import com.dokidoki.auction.domain.entity.AuctionImageEntity;
import com.dokidoki.auction.domain.repository.AuctionEndRepository;
import com.dokidoki.auction.domain.repository.AuctionIngRepository;
import com.dokidoki.auction.domain.repository.InterestRepository;
import com.dokidoki.auction.dto.response.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public PaginationResponse readSimpleAuctionEnd(Pageable pageable) {
        // 데이터 조회
        Page<SimpleAuctionEndInterface> simpleAuctionEndInterfaces = auctionEndRepository
                .findAllSimpleEndList(pageable);

        // 각 경매의 대표 이미지 가져오기
        List<Long> auctionIdList = new ArrayList<>();
        for (SimpleAuctionEndInterface simpleAuctionEndInterface : simpleAuctionEndInterfaces)
            auctionIdList.add(simpleAuctionEndInterface.getAuction_id());
        List<ImageInterface> imageInterfaces = imageService
                .readAuctionThumbnailImage(auctionIdList);

        // 데이터 조합
        List<SimpleAuctionEndInfo> simpleAuctionEndInfos = new ArrayList<>();
        int imageIdx = 0;
        for (int i = 0; i < simpleAuctionEndInterfaces.getContent().size(); i++) {
            SimpleAuctionEndInterface simpleAuctionEndInterface = simpleAuctionEndInterfaces.getContent().get(i);
            ImageInterface imageInterface = imageInterfaces.get(imageIdx);

            // 경매 번호가 다르다면 현재 경매에 해당하는 이미지가 아니므로 건너뛰기
            if (!imageInterface.getAuction_id().equals(simpleAuctionEndInterface.getAuction_id())) {
                simpleAuctionEndInfos.add(
                        new SimpleAuctionEndInfo(
                                simpleAuctionEndInterface,
                                null
                        )
                );
            } else {  // 이미지 Entity와 Auction End Entity의 경매 번호가 일치한다면 DTO에 이미지 삽입
                simpleAuctionEndInfos.add(
                        new SimpleAuctionEndInfo(
                                simpleAuctionEndInterface,
                                imageInterface.getImage_url()
                        )
                );
                imageIdx++;  // 다음 이미지로 넘어가기
            }
        }

        // Response DTO 생성 및 반환
        return PaginationResponse.of(
                simpleAuctionEndInfos,
                simpleAuctionEndInterfaces.isLast()
        );
    }

    /*
    진행중인 전체 경매 목록 조회
     */
    @Transactional(readOnly = true)
    public PaginationResponse readSimpleAuctionIng(Long memberId, Pageable pageable) {
        // 데이터 조회
        Page<SimpleAuctionIngInterface> simpleAuctionIngInterfaces = auctionIngRepository
                .findAllSimpleIngList(pageable);
        return convertToDTOWithImages(memberId, simpleAuctionIngInterfaces);
    }

    /*
    진행중인 마감임박 경매 목록 조회
     */
    @Transactional(readOnly = true)
    public PaginationResponse readSimpleAuctionDeadline(Long memberId, Pageable pageable) {
        // 데이터 조회
        Page<SimpleAuctionIngInterface> simpleAuctionIngInterfaces = auctionIngRepository
                .findAllSimpleDeadlineList(pageable);
        return convertToDTOWithImages(memberId, simpleAuctionIngInterfaces);
    }

    /*
    진행중인 경매 목록 검색
     */
    @Transactional(readOnly = true)
    public PaginationResponse searchSimpleAuctionIng(
            Long memberId, String keyword, Long categoryId, Pageable pageable) {
        // 불필요 문자 제거
        keyword = keyword.strip();

        // 데이터 조회, 카테고리 설정 여부에 따라 (키워드, 카테고리) 검색과 (키워드) 검색으로 분기
        Page<SimpleAuctionIngInterface> simpleAuctionIngInterfaces = null;
        if (categoryId == 0)
            simpleAuctionIngInterfaces = auctionIngRepository
                    .findAllSimpleIngListByKeyword(keyword, pageable);
        else
            simpleAuctionIngInterfaces = auctionIngRepository
                    .findAllSimpleIngListByKeywordANDCategoryId(keyword, categoryId, pageable);

        return convertToDTOWithImages(memberId, simpleAuctionIngInterfaces);
    }

    /*
    DB에서 조회한 진행중인 경매 목록 데이터에 제품 이미지를 추가하며 DTO로 변환
     */
    public PaginationResponse convertToDTOWithImages(
            Long memberId,
            Page<SimpleAuctionIngInterface> simpleAuctionIngInterfaces) {
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
        for (SimpleAuctionIngInterface simpleAuctionIngInterface : simpleAuctionIngInterfaces)
            auctionIdList.add(simpleAuctionIngInterface.getAuction_id());
        List<ImageInterface> imageInterfaces = imageService
                .readAuctionThumbnailImage(auctionIdList);

        // 데이터 조합
        List<SimpleAuctionIngInfo> simpleAuctionIngInfos = new ArrayList<>();
        int imageIdx = 0;
        for (int i = 0; i < simpleAuctionIngInterfaces.getContent().size(); i++) {
            SimpleAuctionIngInterface simpleAuctionIngInterface = simpleAuctionIngInterfaces.getContent().get(i);
            ImageInterface imageInterface = imageInterfaces.get(imageIdx);

            // Auction Id가 다르면 사진이 없는 경매이므로 이미지 URL로 null 전달
            if (imageInterface.getAuction_id().equals(simpleAuctionIngInterface.getAuction_id())) {
                // Response DTO 담기
                simpleAuctionIngInfos.add(
                        new SimpleAuctionIngInfo(
                                simpleAuctionIngInterface,
                                null,
                                interestsOfUser,
                                salesOfUser
                        )
                );
            } else {
                // Response DTO 담기
                simpleAuctionIngInfos.add(
                        new SimpleAuctionIngInfo(
                                simpleAuctionIngInterface,
                                imageInterface.getImage_url(),
                                interestsOfUser,
                                salesOfUser
                        )
                );
            }
        }

        // Response DTO 생성 및 반환
        return PaginationResponse.of(
                simpleAuctionIngInfos,
                simpleAuctionIngInterfaces.isLast()
        );
    }
}
