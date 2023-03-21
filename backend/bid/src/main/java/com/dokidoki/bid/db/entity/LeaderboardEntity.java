package com.dokidoki.bid.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

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
    private LocalDateTime bidTime;

    public static LeaderboardEntity createLeaderboard(Long auctionId, MemberEntity memberEntity, Integer bidPrice, LocalDateTime bidTime) {
        LeaderboardEntity leaderboardEntity = new LeaderboardEntity();
        leaderboardEntity.auctionId = auctionId;
        leaderboardEntity.memberEntity = memberEntity;
        leaderboardEntity.bidPrice = bidPrice;
        leaderboardEntity.bidTime = bidTime;
        return leaderboardEntity;
    }
}

