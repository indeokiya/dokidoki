package com.dokidoki.db.entity;

import com.dokidoki.auction.dto.request.AuctionUpdateReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_at")
    private Date endAt;        // 경매 종료 시점

    @Column(name = "meeting_place")
    private String meetingPlace;        // 거래장소

    @Column(name = "highest_price")
    private int highestPrice;           // 현재 최고가

    public void update(AuctionUpdateReq auctionUpdateReq) {
        this.title = auctionUpdateReq.getTitle();
        this.description = auctionUpdateReq.getDescription();
        this.priceSize = auctionUpdateReq.getPriceSize();
        this.meetingPlace = auctionUpdateReq.getMeetingPlace();
    }
}
