package com.dokidoki.auction.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class AuctionEndResponse {
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

    public AuctionEndResponse(AuctionEndInterface auctionEndInterface,
                              List<String> auction_image_urls,
                              List<CommentResponse> comments,
                              List<LeaderboardHistoryResponse> leaderboard) {
        this.auction_title = auctionEndInterface.getAuction_title();
        this.start_time = auctionEndInterface.getStart_time();
        this.end_time = auctionEndInterface.getEnd_time();
        this.product_name = auctionEndInterface.getProduct_name();
        this.category_name = auctionEndInterface.getCategory_name();
        this.description = auctionEndInterface.getDescription();
        this.offer_price = auctionEndInterface.getOffer_price();
        this.final_price = auctionEndInterface.getFinal_price();
        this.seller_name = auctionEndInterface.getSeller_name();
        this.buyer_name = auctionEndInterface.getBuyer_name();

        this.auction_image_urls = auction_image_urls;
        this.comments = comments;
        this.leaderboard = leaderboard;
    }
}
