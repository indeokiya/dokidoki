package com.dokidoki.auction.dto.response;

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
    private final Integer price_size;
    private final Integer cur_price;

    private final Long seller_id;
    private final String seller_name;

    private final List<String> auction_image_urls;
    private final List<CommentResponse> comments;
    private final List<LeaderboardHistoryResponse> leaderboard;

    public DetailAuctionIngResponse(DetailAuctionIngInterface detailAuctionIngInterface,
                                    List<String> auction_image_urls,
                                    List<CommentResponse> comments,
                                    List<LeaderboardHistoryResponse> leaderboard) {
        this.auction_title = detailAuctionIngInterface.getAuction_title();
        this.start_time = detailAuctionIngInterface.getStart_time();
        this.end_time = detailAuctionIngInterface.getEnd_time();
        this.product_name = detailAuctionIngInterface.getProduct_name();
        this.category_name = detailAuctionIngInterface.getCategory_name();
        this.description = detailAuctionIngInterface.getDescription();
        this.meeting_place = detailAuctionIngInterface.getMeeting_place();
        this.offer_price = detailAuctionIngInterface.getOffer_price();
        this.price_size = detailAuctionIngInterface.getPrice_size();
        this.cur_price = detailAuctionIngInterface.getHighest_price();
        this.seller_id = detailAuctionIngInterface.getSeller_id();
        this.seller_name = detailAuctionIngInterface.getSeller_name();

        this.auction_image_urls = auction_image_urls;
        this.comments = comments;
        this.leaderboard = leaderboard;
    }
}
