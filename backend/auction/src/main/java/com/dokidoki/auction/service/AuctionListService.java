package com.dokidoki.auction.service;

import com.dokidoki.auction.domain.repository.AuctionEndRepository;
import com.dokidoki.auction.domain.repository.AuctionIngRepository;
import com.dokidoki.auction.dto.response.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuctionListService {
    private final AuctionEndRepository auctionEndRepository;
    private final AuctionIngRepository auctionIngRepository;
    private final ImageService imageService;

    /*
    종료된 경매 목록 조회
     */
    public PaginationResponse<List<SimpleAuctionEndInfo>> readSimpleAuctionEnd(Pageable pageable) {
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
        return new PaginationResponse<>(
                simpleAuctionEndInfos,
                simpleAuctionEndInterfaces.isLast()
        );
    }

    /*
    진행중인 전체 경매 목록 조회
     */
    public PaginationResponse<List<SimpleAuctionIngInfo>> readSimpleAuctionIng(Pageable pageable) {
        // 데이터 조회
        Page<SimpleAuctionIngInterface> simpleAuctionIngInterfaces = auctionIngRepository
                .findAllSimpleIngList(pageable);
        return convertToDTOWithImages(simpleAuctionIngInterfaces);
    }

    /*
    진행중인 마감임박 경매 목록 조회
     */
    public PaginationResponse<List<SimpleAuctionIngInfo>> readSimpleAuctionDeadline(Pageable pageable) {
        // 데이터 조회
        Page<SimpleAuctionIngInterface> simpleAuctionIngInterfaces = auctionIngRepository
                .findAllSimpleDeadlineList(pageable);
        return convertToDTOWithImages(simpleAuctionIngInterfaces);
    }

    /*
    진행중인 경매 목록 검색
     */
    public PaginationResponse<List<SimpleAuctionIngInfo>> searchSimpleAuctionIng(
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
    public PaginationResponse<List<SimpleAuctionIngInfo>> convertToDTOWithImages(
            Page<SimpleAuctionIngInterface> simpleAuctionIngInterfaces) {
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
                            auctionImageResponse.getImage_urls()
                    )
            );
        }

        // Response DTO 생성 및 반환
        return new PaginationResponse<>(
                simpleAuctionIngInfos,
                simpleAuctionIngInterfaces.isLast()
        );
    }
}
