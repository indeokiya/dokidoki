package com.dokidoki.bid.api.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class CommentResp {
    private Long id;
    private Long member_id;
    private String member_profile;
    private String member_name;
    private String content;
    private LocalDateTime written_time;
    private LocalDateTime modified_time;
    private List<CommentResp> sub_comments;
}
