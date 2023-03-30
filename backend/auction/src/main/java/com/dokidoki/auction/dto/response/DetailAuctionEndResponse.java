package com.dokidoki.auction.dto.response;

import com.dokidoki.auction.domain.entity.AuctionEndEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class DetailAuctionEndResponse {
    private final String auction_title;
    private final LocalDateTime start_time;
    private final LocalDateTime end_time;
    private final String product_name;
    private final String category_name;
    private final String description;
    private final Integer offer_price;
    private final Integer final_price;
    private final String seller_name;
    private final String buyer_name;

    private final List<String> auction_image_urls;
    private final List<CommentResponse> comments;
    private final List<LeaderboardHistoryResponse> leaderboard;

    public DetailAuctionEndResponse(AuctionEndEntity auctionEndEntity,
                                    List<String> auction_image_urls,
                                    List<CommentResponse> comments,
                                    List<LeaderboardHistoryResponse> leaderboard) {
        this.auction_title = auctionEndEntity.getTitle();
        this.start_time = auctionEndEntity.getStartTime();
        this.end_time = auctionEndEntity.getEndTime();
        this.product_name = auctionEndEntity.getProduct().getName();
        this.category_name = auctionEndEntity.getProduct().getCategoryEntity().getCategoryName();
        this.description = auctionEndEntity.getDescription();
        this.offer_price = auctionEndEntity.getOfferPrice();
        this.final_price = auctionEndEntity.getFinalPrice();
        this.seller_name = auctionEndEntity.getSeller().getName();
        this.buyer_name = auctionEndEntity.getBuyer().getName();

        this.auction_image_urls = auction_image_urls;
        this.comments = comments;
        this.leaderboard = leaderboard;
    }
}
