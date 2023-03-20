package com.dokidoki.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "leaderboard")
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class LeaderBoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "auction_id")
    private Long auctionId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = MemberEntity.class)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @Column(name = "member_id", insertable = false, updatable = false)
    private Long memberId;

    @Column(name = "bid_price")
    private Integer bidPrice;

    @Column(name = "bid_time")
    private LocalDateTime bidTime;
}
