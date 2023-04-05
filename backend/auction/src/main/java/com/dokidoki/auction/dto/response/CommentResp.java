package com.dokidoki.auction.dto.response;

import com.dokidoki.auction.domain.entity.CommentEntity;
import com.dokidoki.auction.domain.entity.MemberEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Builder
    public CommentResp(Long id, Long member_id, String member_profile, String member_name, String content,
                       LocalDateTime written_time, LocalDateTime modified_time, List<CommentResp> sub_comments) {
        this.id = id;
        this.member_id = member_id;
        this.member_profile = member_profile;
        this.member_name = member_name;
        this.content = content;
        this.written_time = written_time;
        this.modified_time = modified_time;
        this.sub_comments = sub_comments;
    }
}
