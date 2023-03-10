package com.dokidoki.auction.controller;

import com.dokidoki.auction.dto.request.CommentRequest;
import com.dokidoki.auction.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("")
    public ResponseEntity<String> createComment(Optional<CommentRequest> commentRequest) {
        // Request Body가 없을 경우,
        if (commentRequest.isEmpty()) {
            return new ResponseEntity<>("요청받은 정보가 없습니다.", HttpStatus.BAD_REQUEST);
        }

        CommentRequest comment = commentRequest.get();

        // 경매 식별번호가 없을 경우,
        if (comment.getAuction_id() == null) {
            return new ResponseEntity<>("경매 식별번호가 없습니다.", HttpStatus.BAD_REQUEST);
        }
        // 사용자 식별번호가 없을 경우,
        if (comment.getMember_id() == null) {
            return new ResponseEntity<>("사용자 식별번호가 없습니다.", HttpStatus.BAD_REQUEST);
        }
        // 댓글이 빈 문자열일 경우,
        if (comment.getContent() == null || comment.getContent().equals("")) {
            return new ResponseEntity<>("댓글 내용이 없습니다.", HttpStatus.BAD_REQUEST);
        }

        // 댓글 등록 및 결과 반환
        String resultMsg = commentService.createComment(comment);
        return new ResponseEntity<>(resultMsg, HttpStatus.OK);
    }
}
