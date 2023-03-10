package com.dokidoki.auction.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "auction_id")
//    private Auction auction;
//
//    @ManyToOne
//    @JoinColumn(name = "member_id")
//    private Member member;

    private String content;

    private Timestamp written_time;

    private Long parent_id;

    public static Comment createComment(String content, Long parent_id) {
        Comment newComment = new Comment();
//        newComment.auction = auction;
//        newComment.member = member;
        newComment.content = content;
        newComment.parent_id = parent_id;
        newComment.written_time = Timestamp.valueOf(LocalDateTime.now());
        return newComment;
    }
}
