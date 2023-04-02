package com.dokidoki.auction.domain.repository;

import com.dokidoki.auction.domain.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findCommentsByAuctionIdOrderByWrittenTime(Long auction_id);

    // 댓글 더미 조회
    List<CommentEntity> findAllByIdIsOrParentIdIsOrderByIdDesc(Long commentId, Long parentId);
}
