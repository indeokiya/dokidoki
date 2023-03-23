package com.dokidoki.bid.db.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "comment")
@Table(name = "comment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // AuctionIng, AuctionEd 두 개의 테이블을 참조해야 하므로 매핑 X
    @Column(name = "auction_id")
    private Long auctionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    private String content;

    @CreationTimestamp
    @Column(name = "written_time")
    private LocalDateTime writtenTime;

    @Column(name = "parent_id")
    private Long parentId;

    public static CommentEntity createComment(Long id, Long auction_id, MemberEntity memberEntity, String content, Long parent_id) {
        CommentEntity newCommentEntity = new CommentEntity();
        newCommentEntity.id = id;
        newCommentEntity.auctionId = auction_id;
        newCommentEntity.memberEntity = memberEntity;
        newCommentEntity.content = content;
        newCommentEntity.parentId = parent_id;
        return newCommentEntity;
    }
}
