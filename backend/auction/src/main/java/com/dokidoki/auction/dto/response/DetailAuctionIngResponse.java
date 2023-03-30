package com.dokidoki.auction.dto.response;

import com.dokidoki.auction.domain.entity.AuctionIngEntity;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class DetailAuctionIngResponse {
    private final String auction_title;
    private final LocalDateTime start_time;
    private final LocalDateTime end_time;
    private final String product_name;
    private final String category_name;
    private final String meeting_place;
    private final String description;

    private final Integer offer_price;

    private final Long seller_id;
    private final String seller_name;

    private final List<String> auction_image_urls;
    private final List<CommentResponse> comments;
    private final Boolean is_my_interest;

    public DetailAuctionIngResponse(AuctionIngEntity auctionIngEntity,
                                    List<String> auction_image_urls,
                                    List<CommentResponse> comments,
                                    Boolean isMyInterest) {
        this.auction_title = auctionIngEntity.getTitle();
        this.start_time = auctionIngEntity.getStartTime();
        this.end_time = auctionIngEntity.getEndAt();
        this.product_name = auctionIngEntity.getProductEntity().getName();
        this.category_name = auctionIngEntity.getProductEntity().getCategoryEntity().getCategoryName();
        this.description = auctionIngEntity.getDescription();
        this.meeting_place = auctionIngEntity.getMeetingPlace();
        this.offer_price = auctionIngEntity.getOfferPrice();
        this.seller_id = auctionIngEntity.getSeller().getId();
        this.seller_name = auctionIngEntity.getSeller().getName();

        this.auction_image_urls = auction_image_urls;
        this.comments = comments;
        this.is_my_interest = isMyInterest;
    }
}
