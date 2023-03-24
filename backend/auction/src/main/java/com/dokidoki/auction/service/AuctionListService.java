package com.dokidoki.auction.service;

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

        // 데이터 조합
        List<SimpleAuctionEndInfo> simpleAuctionEndInfos = new ArrayList<>();
        for (SimpleAuctionEndInterface simpleAuctionEndInterface : simpleAuctionEndInterfaces) {
            // 경매 제품 사진 검색
            AuctionImageResponse auctionImageResponse = imageService
                    .readAuctionImages(simpleAuctionEndInterface.getAuction_id());

            // Response DTO 담기
            simpleAuctionEndInfos.add(
                    new SimpleAuctionEndInfo(
                            simpleAuctionEndInterface,
                            auctionImageResponse.getImage_urls()
                    )
            );
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
    public PaginationResponse readSimpleAuctionIng(Pageable pageable) {
        // 데이터 조회
        Page<SimpleAuctionIngInterface> simpleAuctionIngInterfaces = auctionIngRepository
                .findAllSimpleIngList(pageable);
        return convertToDTOWithImages(simpleAuctionIngInterfaces);
    }

    /*
    진행중인 마감임박 경매 목록 조회
     */
    @Transactional(readOnly = true)
    public PaginationResponse readSimpleAuctionDeadline(Pageable pageable) {
        // 데이터 조회
        Page<SimpleAuctionIngInterface> simpleAuctionIngInterfaces = auctionIngRepository
                .findAllSimpleDeadlineList(pageable);
        return convertToDTOWithImages(simpleAuctionIngInterfaces);
    }

    /*
    진행중인 경매 목록 검색
     */
    @Transactional(readOnly = true)
    public PaginationResponse searchSimpleAuctionIng(
            String keyword, Long categoryId, Pageable pageable) {
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

        return convertToDTOWithImages(simpleAuctionIngInterfaces);
    }

    /*
    DB에서 조회한 진행중인 경매 목록 데이터에 제품 이미지를 추가하며 DTO로 변환
     */
    public PaginationResponse convertToDTOWithImages(
            Page<SimpleAuctionIngInterface> simpleAuctionIngInterfaces) {
        // 관심있는 경매 ID 가져오기
        List<InterestMapping> interestMappings = interestRepository.findAllByMemberEntity_Id(2L);
        Set<Long> interestsOfUser = new HashSet<>();
        interestMappings.forEach(interestMapping -> {
            interestsOfUser.add(interestMapping.getAuctionIngEntity().getId());
        });

        // 판매중인 경매 ID 가져오기
        List<AuctionIngMapping> auctionIngMappings = auctionIngRepository.findAuctionIngEntityBySeller_Id(2L);
        Set<Long> salesOfUser = new HashSet<>();
        auctionIngMappings.forEach(auctionIngMapping -> {
            salesOfUser.add(auctionIngMapping.getSeller().getId());
        });

        // 데이터 조합
        List<SimpleAuctionIngInfo> simpleAuctionIngInfos = new ArrayList<>();
        for (SimpleAuctionIngInterface simpleAuctionIngInterface : simpleAuctionIngInterfaces) {
            // 경매 제품 사진 검색
            AuctionImageResponse auctionImageResponse = imageService
                    .readAuctionImages(simpleAuctionIngInterface.getAuction_id());

            // Response DTO 담기
            simpleAuctionIngInfos.add(
                    new SimpleAuctionIngInfo(
                            simpleAuctionIngInterface,
                            auctionImageResponse.getImage_urls(),
                            interestsOfUser,
                            salesOfUser
                    )
            );
        }

        // Response DTO 생성 및 반환
        return PaginationResponse.of(
                simpleAuctionIngInfos,
                simpleAuctionIngInterfaces.isLast()
        );
    }
}
