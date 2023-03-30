package com.dokidoki.auction.service;

import com.dokidoki.auction.domain.entity.AuctionEndEntity;
import com.dokidoki.auction.domain.entity.AuctionIngEntity;
import com.dokidoki.auction.domain.repository.AuctionEndRepository;
import com.dokidoki.auction.domain.repository.AuctionIngRepository;
import com.dokidoki.auction.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyInfoService {
    private final AuctionEndRepository auctionEndRepository;
    private final AuctionIngRepository auctionIngRepository;
    private final AuctionListService auctionListService;

    /*
    판매중인 경매 목록 조회
     */
    @Transactional(readOnly = true)
    public PaginationResponse readAllMySellingAuction(Long memberId, Pageable pageable) {
        // 데이터 조회
        Page<AuctionIngEntity> auctionIngEntities = auctionIngRepository
                .findAllMySellingAuction(memberId, pageable);

        // Response DTO 변환
        return auctionListService.convertToDTOWithImages(memberId, auctionIngEntities);
    }

    /*
    입찰중인 경매 목록 조회
     */
    @Transactional(readOnly = true)
    public PaginationResponse readAllMyBiddingAuction(Long memberId, Pageable pageable) {
        // 데이터 조회
        Page<AuctionIngEntity> auctionIngEntities = auctionIngRepository
                .findAllMyBiddingAuction(memberId, pageable);

        // Response DTO 변환
        return auctionListService.convertToDTOWithImages(memberId, auctionIngEntities);
    }

    /*
    입찰중인 경매 목록 조회
     */
    @Transactional(readOnly = true)
    public PaginationResponse readAllMyInterestingAuction(Long memberId, Pageable pageable) {
        // 데이터 조회
        Page<AuctionIngEntity> auctionIngEntities = auctionIngRepository
                .findAllMyInterestingAuction(memberId, pageable);

        // Response DTO 변환
        return auctionListService.convertToDTOWithImages(memberId, auctionIngEntities);
    }

    /*
    구매내역 조회
     */
    @Transactional(readOnly = true)
    public MyHistoryResponse readAllMyPurchases(Long memberId, Pageable pageable) {
        Page<AuctionEndEntity> auctionEndEntities = auctionEndRepository
                .findAllMyPurchases(memberId, pageable);

        // ResultSet -> DTO
        List<MyHistoryInfo> myHistoryInfos = new ArrayList<>();
        for (AuctionEndEntity auctionEndEntity : auctionEndEntities) {
            myHistoryInfos.add(new MyHistoryInfo(auctionEndEntity));
        }

        // Response DTO
        return new MyHistoryResponse(
                myHistoryInfos,
                auctionEndEntities.isLast()
        );
    }

    /*
    판매내역 조회
     */
    @Transactional(readOnly = true)
    public MyHistoryResponse readAllMySales(Long memberId, Pageable pageable) {
        Page<AuctionEndEntity> auctionEndEntities = auctionEndRepository
                .findAllMySales(memberId, pageable);

        // ResultSet -> DTO
        List<MyHistoryInfo> myHistoryInfos = new ArrayList<>();
        for (AuctionEndEntity auctionEndEntity : auctionEndEntities) {
            myHistoryInfos.add(new MyHistoryInfo(auctionEndEntity));
        }

        // Response DTO
        return new MyHistoryResponse(
                myHistoryInfos,
                auctionEndEntities.isLast()
        );
    }

    /*
    총 구매가 조회
     */
    public Long readMyTotalOfPurchases(Long memberId) {
        Long total = auctionEndRepository.getMyTotalOfPurchases(memberId);
        if (total == null)
            return 0L;
        return total;
    }

    /*
    총 판매가 조회
     */
    public Long readMyTotalOfSales(Long memberId) {
        Long total = auctionEndRepository.getMyTotalOfSales(memberId);
        if (total == null)
            return 0L;
        return total;
    }
}
