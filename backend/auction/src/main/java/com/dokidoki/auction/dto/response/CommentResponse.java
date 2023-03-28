package com.dokidoki.auction.dto.response;

import com.dokidoki.auction.domain.entity.CommentEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class CommentResponse {
    private final Long id;
    private final Long member_id;
    private final String member_profile;
    private final String member_name;
    private final String content;
    private final LocalDateTime written_time;
    private final LocalDateTime modified_time;
    private final List<CommentResponse> sub_comments;

    public CommentResponse(CommentEntity commentEntity) {
        this.id = commentEntity.getId();
        this.member_id = commentEntity.getMemberEntity().getId();
        this.member_profile = commentEntity.getMemberEntity().getPicture();
        this.member_name = commentEntity.getMemberEntity().getName();
        this.content = commentEntity.getContent();
        this.written_time = commentEntity.getWrittenTime();
        this.modified_time = commentEntity.getModifiedTime();
        this.sub_comments = new ArrayList<>();
    }
}
