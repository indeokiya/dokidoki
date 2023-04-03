package com.dokidoki.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "auction_end")
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class AuctionEndEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY, targetEntity = MemberEntity.class)
    @JoinColumn(name = "seller_id")
    @OnDelete(action= OnDeleteAction.CASCADE)
    private MemberEntity seller;

    @Column(name = "seller_id", insertable = false, updatable = false)
    private Long sellerId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = MemberEntity.class)
    @JoinColumn(name = "buyer_id")
    private MemberEntity buyer;

    @Column(name = "buyer_id", insertable = false, updatable = false)
    private Long buyerId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = ProductEntity.class)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @Column(name = "product_id", insertable = false, updatable = false)
    private Long productId;

    @Column(name = "start_time")
    private LocalDateTime startTime;        // 경매 시작 시간

    @Column(name = "end_time")
    private LocalDateTime endTime;        // 경매 종료 시간

    private String title;               // 제목

    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;         // 제품설명

    @Column(name = "offer_price")
    private Long offerPrice;         // 시작가

    @Column(name = "final_price")
    private Long finalPrice;         // 낙찰금액
}
