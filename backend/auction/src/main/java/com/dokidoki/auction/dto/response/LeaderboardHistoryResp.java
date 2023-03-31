package com.dokidoki.auction.dto.response;

import com.dokidoki.auction.domain.entity.LeaderboardEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

// 입찰 내역
@Getter
@RequiredArgsConstructor
public class LeaderboardHistoryResp {
    private final Long member_id;
    private final String member_name;
    private final Integer bid_price;
    private final LocalDateTime bid_time;

    public LeaderboardHistoryResp(LeaderboardEntity leaderboard) {
        this.member_id = leaderboard.getMemberEntity().getId();
        this.member_name = leaderboard.getMemberEntity().getName();
        this.bid_price = leaderboard.getBidPrice();
        this.bid_time = leaderboard.getBidTime();
    }
}
