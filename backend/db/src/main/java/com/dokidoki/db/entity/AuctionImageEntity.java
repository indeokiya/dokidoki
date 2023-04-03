package com.dokidoki.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "auction_image")
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class AuctionImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "auction_id")
    private Long auctionId;

    @Column(name = "image_url", columnDefinition = "MEDIUMTEXT")
    private String imageUrl;
}
