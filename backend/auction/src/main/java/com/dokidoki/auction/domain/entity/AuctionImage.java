package com.dokidoki.auction.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class AuctionImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "auction_id")
    private Long auctionId;

    @Column(name = "image_url")
    private String imageUrl;

    public static AuctionImage createAuctionImage(Long auctionId, String imageUrl) {
        AuctionImage auctionImage = new AuctionImage();
        auctionImage.auctionId = auctionId;
        auctionImage.imageUrl = imageUrl;
        return auctionImage;
    }
}
