package com.dokidoki.auction.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // AuctionIng, AuctionEd 두 개의 테이블을 참조해야 하므로 매핑 X
    @Column(name = "auction_id")
    private Long auctionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String content;

    @CreationTimestamp
    @Column(name = "written_time")
    private Timestamp writtenTime;

    @Column(name = "parent_id")
    private Long parentId;

    public static Comment createComment(Long auction_id, Member member, String content, Long parent_id) {
        Comment newComment = new Comment();
        newComment.auctionId = auction_id;
        newComment.member = member;
        newComment.content = content;
        newComment.parentId = parent_id;
        return newComment;
    }
}
