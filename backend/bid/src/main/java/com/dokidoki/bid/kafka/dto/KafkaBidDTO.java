package com.dokidoki.bid.kafka.dto;

import com.dokidoki.bid.api.request.AuctionBidReq;
import com.dokidoki.bid.api.response.LeaderBoardMemberResp;
import com.dokidoki.bid.db.entity.AuctionRealtime;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class KafkaBidDTO {

    private long beforeWinnerId;
    private long memberId;
    private long auctionId;
    private String name;
    private int highestPrice;
    private String productName;
    private long productId;
    private LocalDateTime bidTime;
    public KafkaBidDTO() {}

    public static KafkaBidDTO of(AuctionRealtime auctionRealtime, AuctionBidReq req, LeaderBoardMemberResp resp, long memberId, long beforeWinnerId) {
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

    public KafkaBidDTO(long beforeWinnerId, long memberId, long auctionId, String name, int highestPrice, String productName, long productId, LocalDateTime bidTime) {
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
