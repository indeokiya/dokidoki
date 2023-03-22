package com.dokidoki.auction.service;

import com.dokidoki.auction.domain.repository.AuctionEndRepository;
import com.dokidoki.auction.dto.response.MyHistoryInfo;
import com.dokidoki.auction.dto.response.MyHistoryInfoInterface;
import com.dokidoki.auction.dto.response.MyHistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyInfoService {
    private final AuctionEndRepository auctionEndRepository;

    /*
    구매내역 조회
     */
    public MyHistoryResponse readAllMyPurchases(Long memberId, Pageable pageable) {
        Page<MyHistoryInfoInterface> myHistoryInfoInterfaces = auctionEndRepository
                .findAllMyPurchases(memberId, pageable);

        // ResultSet -> DTO
        List<MyHistoryInfo> myHistoryInfos = new ArrayList<>();
        for (MyHistoryInfoInterface myHistoryInfoInterface : myHistoryInfoInterfaces) {
            myHistoryInfos.add(new MyHistoryInfo(myHistoryInfoInterface));
        }

        // Response DTO
        return new MyHistoryResponse(
                myHistoryInfos,
                myHistoryInfoInterfaces.isLast()
        );
    }

    /*
    판매내역 조회
     */
    public MyHistoryResponse readAllMySales(Long memberId, Pageable pageable) {
        Page<MyHistoryInfoInterface> myHistoryInfoInterfaces = auctionEndRepository
                .findAllMySales(memberId, pageable);

        // ResultSet -> DTO
        List<MyHistoryInfo> myHistoryInfos = new ArrayList<>();
        for (MyHistoryInfoInterface myHistoryInfoInterface : myHistoryInfoInterfaces) {
            myHistoryInfos.add(new MyHistoryInfo(myHistoryInfoInterface));
        }

        // Response DTO
        return new MyHistoryResponse(
                myHistoryInfos,
                myHistoryInfoInterfaces.isLast()
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
