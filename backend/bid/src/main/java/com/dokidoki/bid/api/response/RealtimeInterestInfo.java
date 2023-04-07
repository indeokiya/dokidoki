package com.dokidoki.bid.api.response;

import com.dokidoki.bid.db.entity.AuctionRealtime;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RealtimeInterestInfo {
    private Long auctionId; // 경매 ID
    private Long highestPrice; // 현재 최고 입찰가
    private Long cnt; // 조회 횟수 or 입찰 횟수
    private String auctionTitle; // 경매 제목
    private String productName; // 경매 물품
    private String categoryName; // 경매 물품 카테고리

    public static RealtimeInterestInfo from(AuctionRealtime auctionRealtime, DetailAuctionIngResp auctionInfo, Long cnt) {
        RealtimeInterestInfo info = RealtimeInterestInfo.builder()
                .auctionId(auctionRealtime.getAuctionId())
                .highestPrice(auctionRealtime.getHighestPrice())
                .cnt(cnt)
                .auctionTitle(auctionInfo.data.getAuction_title())
                .productName(auctionInfo.data.getProduct_name())
                .categoryName(auctionInfo.data.getCategory_name())
                .build();
        return info;
    }
}
