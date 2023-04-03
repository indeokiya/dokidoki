package com.dokidoki.bid.kafka.dto;

import com.dokidoki.bid.api.request.AuctionBidReq;
import com.dokidoki.bid.api.response.LeaderBoardMemberResp;
import com.dokidoki.bid.db.entity.AuctionRealtime;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
public class KafkaBidDTO {

    private Long beforeWinnerId;
    private Long memberId;
    private Long auctionId;
    private String name;
    private Long highestPrice;
    private String productName;
    private Long productId;
    private LocalDateTime bidTime;
    public KafkaBidDTO() {}

    public static KafkaBidDTO of(AuctionRealtime auctionRealtime, AuctionBidReq req, LeaderBoardMemberResp resp, Long memberId, Long beforeWinnerId) {
        KafkaBidDTO dto = KafkaBidDTO.builder()
                .beforeWinnerId(beforeWinnerId)
                .memberId(memberId)
                .auctionId(auctionRealtime.getAuctionId())
                .name(req.getName())
                .highestPrice(resp.getBidPrice())
                .productName(auctionRealtime.getProductName())
                .productId(auctionRealtime.getProductId())
                .bidTime(resp.getBidTime())
                .build();
        return dto;
    }

    public KafkaBidDTO(Long beforeWinnerId, Long memberId, Long auctionId, String name, Long highestPrice, String productName, Long productId, LocalDateTime bidTime) {
        this.beforeWinnerId = beforeWinnerId;
        this.memberId = memberId;
        this.auctionId = auctionId;
        this.name = name;
        this.highestPrice = highestPrice;
        this.productName = productName;
        this.productId = productId;
        this.bidTime = bidTime;
    }
}
