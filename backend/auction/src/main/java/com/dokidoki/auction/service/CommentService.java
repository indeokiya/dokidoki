package com.dokidoki.auction.service;

import com.dokidoki.auction.domain.entity.Comment;
import com.dokidoki.auction.domain.repository.CommentRepository;
import com.dokidoki.auction.dto.request.CommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public String createComment(CommentRequest commentRequest) {
        Comment newComment = Comment.createComment(commentRequest.getContent(), commentRequest.getParent_id());
        commentRepository.save(newComment);
        return "댓글 등록에 성공했습니다.";
    }
}
