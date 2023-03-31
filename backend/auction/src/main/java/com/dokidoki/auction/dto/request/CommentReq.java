package com.dokidoki.auction.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentReq {
    private Long auction_id;
    private Long member_id;
    private String content;
    private Long parent_id;
}
