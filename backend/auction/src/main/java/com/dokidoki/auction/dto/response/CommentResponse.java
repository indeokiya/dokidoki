package com.dokidoki.auction.dto.response;

import com.dokidoki.auction.domain.entity.CommentEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class CommentResponse {
    private final Long id;
    private final Long member_id;
    private final String content;
    private final Timestamp written_time;
    private final List<CommentResponse> sub_comments;

    public CommentResponse(CommentEntity commentEntity) {
        this.id = commentEntity.getId();
        this.member_id = commentEntity.getMemberEntity().getId();
        this.content = commentEntity.getContent();
        this.written_time = commentEntity.getWrittenTime();
        this.sub_comments = new ArrayList<>();
    }
}
