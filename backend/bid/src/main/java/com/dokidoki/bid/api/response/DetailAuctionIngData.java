package com.dokidoki.bid.api.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class DetailAuctionIngData {
    private String auction_title;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private String product_name;
    private String category_name;
    private String meeting_place;
    private String description;

    private Long offer_price;

    private Long seller_id;
    private String seller_name;

    private List<String> auction_image_urls;
    private List<CommentResp> comments;
    private Boolean is_my_interest;


}
