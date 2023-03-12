package com.dokidoki.auction.domain.repository;

import com.dokidoki.auction.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentsByAuctionIdOrderByWrittenTime(Long auction_id);
}
