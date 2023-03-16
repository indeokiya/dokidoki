package com.dokidoki.auction.domain.entity;

import io.swagger.models.auth.In;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor
public class Leaderboard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "auction_id")
    private Long auctionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "bid_price")
    private Integer bidPrice;

    @Column(name = "bid_time")
    private Timestamp bidTime;

    public static Leaderboard createLeaderboard(Long auctionId, Member member, Integer bidPrice, Timestamp bidTime) {
        Leaderboard leaderboard = new Leaderboard();
        leaderboard.auctionId = auctionId;
        leaderboard.member = member;
        leaderboard.bidPrice = bidPrice;
        leaderboard.bidTime = bidTime;
        return leaderboard;
    }
}
