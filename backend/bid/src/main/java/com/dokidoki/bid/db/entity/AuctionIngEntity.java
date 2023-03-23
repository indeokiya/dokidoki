package com.dokidoki.bid.db.entity;

import lombok.*;

import javax.persistence.*;

import java.util.Date;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "auction_ing")
@Entity
public class AuctionIngEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 판매자id 수정필요
    @Column(name = "seller_id")
    private Long sellerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

    private String title;               // 제목

    private String description;         // 제품설명

    @Column(name = "offer_price")
    private Integer offerPrice;             // 시작 가격(호가)

    @Column(name = "price_size")
    private Integer priceSize;              // 경매 단위

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_at")
    private Date endAt;        // 경매 종료 시점

    @Column(name = "meeting_place")
    private String meetingPlace;        // 거래장소

    @Column(name = "highest_price")
    private Integer highestPrice;           // 현재 최고가

}
