package com.dokidoki.auction.domain.repository;

import com.dokidoki.auction.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
