package com.dokidoki.auction.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "auction_image")
@Table(name = "auction_image")
@Getter
@NoArgsConstructor
public class AuctionImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "auction_id")
    private Long auctionId;

    @Column(name = "image_url")
    private String imageUrl;

    public static AuctionImageEntity createAuctionImage(Long auctionId, String imageUrl) {
        AuctionImageEntity auctionImageEntity = new AuctionImageEntity();
        auctionImageEntity.auctionId = auctionId;
        auctionImageEntity.imageUrl = imageUrl;
        return auctionImageEntity;
    }
}
