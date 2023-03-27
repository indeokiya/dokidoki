package com.dokidoki.bid.kafka.dto;

import com.dokidoki.bid.api.request.AuctionBidReq;
import com.dokidoki.bid.api.response.LeaderBoardMemberResp;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class KafkaBidDTO {

    private long beforeWinnerId;
    private long memberId;
    private long auctionId;
    private String name;
    private int highestPrice;
    private LocalDateTime bidTime;
    public KafkaBidDTO() {}

    public static KafkaBidDTO of(long auctionId, LeaderBoardMemberResp resp, AuctionBidReq req, long memberId, long beforeWinnerId) {
        KafkaBidDTO dto = KafkaBidDTO.builder()
                .beforeWinnerId(beforeWinnerId)
                .memberId(memberId)
                .auctionId(auctionId)
                .name(req.getName())
                .highestPrice(resp.getBidPrice())
                .bidTime(resp.getBidTime())
                .build();
        return dto;
    }

    @Builder
    public KafkaBidDTO(long memberId, long auctionId, String name, int highestPrice, LocalDateTime bidTime, long beforeWinnerId) {
        this.memberId = memberId;
        this.auctionId = auctionId;
        this.name = name;
        this.highestPrice = highestPrice;
        this.bidTime = bidTime;
        this.beforeWinnerId = beforeWinnerId;
    }
}
