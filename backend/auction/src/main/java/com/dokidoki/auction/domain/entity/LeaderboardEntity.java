package com.dokidoki.auction.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "leaderboard")
@Table(name = "leaderboard")
@Getter
@NoArgsConstructor
public class LeaderboardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "auction_id")
    private Long auctionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @Column(name = "bid_price")
    private Integer bidPrice;

    @Column(name = "bid_time")
    private Timestamp bidTime;

    public static LeaderboardEntity createLeaderboard(Long auctionId, MemberEntity memberEntity, Integer bidPrice, Timestamp bidTime) {
        LeaderboardEntity leaderboardEntity = new LeaderboardEntity();
        leaderboardEntity.auctionId = auctionId;
        leaderboardEntity.memberEntity = memberEntity;
        leaderboardEntity.bidPrice = bidPrice;
        leaderboardEntity.bidTime = bidTime;
        return leaderboardEntity;
    }
}
