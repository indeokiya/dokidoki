package com.dokidoki.auction.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Table(name = "auction_ing")
@Entity
public class AuctionIng {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // 판매자id 수정필요
    @Column(name = "seller_id")
    private long sellerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private String title;               // 제목

    private String description;         // 제품설명

    @Column(name = "offer_price")
    private int offerPrice;             // 시작 가격(호가)

    @Column(name = "price_size")
    private int priceSize;              // 경매 단위

    @Column(name = "end_at")
    private LocalDateTime endAt;        // 경매 종료 시점

    @Column(name = "meeting_place")
    private String meetingPlace;        // 거래장소

    @Column(name = "highest_price")
    private int highestPrice;           // 현재 최고가
}
