package com.dokidoki.auction.dto.response;

import com.dokidoki.auction.domain.entity.CommentEntity;
import com.dokidoki.auction.domain.entity.MemberEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class CommentResp {
    private final Long id;
    private final Long member_id;
    private final String member_profile;
    private final String member_name;
    private final String content;
    private final LocalDateTime written_time;
    private final LocalDateTime modified_time;
    private final List<CommentResp> sub_comments;

    public CommentResp(CommentEntity commentEntity) {
        Long memberId = -1L;
        String memberProfile = null;
        String memberName = "";
        String content = "삭제된 댓글입니다.";
        MemberEntity memberEntity = commentEntity.getMemberEntity();;
        if (memberEntity != null) {
            memberId = memberEntity.getId();
            memberName = memberEntity.getName();
            memberProfile = memberEntity.getPicture();
            content = commentEntity.getContent();
        }

        this.id = commentEntity.getId();
        this.member_id = memberId;
        this.member_profile = memberProfile;
        this.member_name = memberName;
        this.content = content;
        this.written_time = commentEntity.getWrittenTime();
        this.modified_time = commentEntity.getModifiedTime();
        this.sub_comments = new ArrayList<>();
    }
}
