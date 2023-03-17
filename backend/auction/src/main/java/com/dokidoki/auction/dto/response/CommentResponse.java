package com.dokidoki.auction.dto.response;

import com.dokidoki.auction.domain.entity.Comment;
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

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.member_id = comment.getMember().getId();
        this.content = comment.getContent();
        this.written_time = comment.getWrittenTime();
        this.sub_comments = new ArrayList<>();
    }
}
