package com.dokidoki.auction.domain.repository;

import com.dokidoki.auction.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentsByAuctionIdOrderByWrittenTime(Long auction_id);

    @Modifying
    @Query("delete from Comment c where c.parentId=:parent_id")
    int deleteCommentsByParentId(@Param("parent_id") Long parent_id);
}
