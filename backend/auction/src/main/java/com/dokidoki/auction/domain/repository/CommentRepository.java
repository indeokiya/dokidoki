package com.dokidoki.auction.domain.repository;

import com.dokidoki.auction.domain.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findCommentsByAuctionIdOrderByWrittenTime(Long auction_id);

    @Modifying
    @Query("delete from comment c where c.parentId=:parent_id")
    void deleteCommentsByParentId(@Param("parent_id") Long parent_id);
}
