package com.dokidoki.auction.controller;

import com.dokidoki.auction.common.BaseResponseBody;
import com.dokidoki.auction.common.JWTUtil;
import com.dokidoki.auction.dto.request.CommentRequest;
import com.dokidoki.auction.dto.request.PutCommentRequest;
import com.dokidoki.auction.dto.response.CommentResponse;
import com.dokidoki.auction.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final JWTUtil jwtUtil;

    // 토큰 없을 때 반환하는 Response
    private final ResponseEntity<BaseResponseBody> NOT_VALID_TOKEN_RESPONSE = ResponseEntity
            .status(403)
            .body(BaseResponseBody.of("토큰이 유효하지 않습니다."));

    @GetMapping("/{auction_id}")
    public ResponseEntity<BaseResponseBody> readComment(@PathVariable Long auction_id) {
        List<CommentResponse> comments = commentService.readComment(auction_id);
        return ResponseEntity
                .status(200)
                .body(BaseResponseBody.of("댓글 조회 성공", comments));
    }

    @PostMapping("")
    public ResponseEntity<BaseResponseBody> createComment(
            @RequestBody Optional<CommentRequest> optionalCommentRequest,
            HttpServletRequest request) {
        CommentRequest commentRequest = optionalCommentRequest.orElse(null);

        // 요청자 확인
        Long memberId = jwtUtil.getUserId(request);
        if (memberId == null)
            return NOT_VALID_TOKEN_RESPONSE;

        // Request Body가 없을 경우,
        if (commentRequest == null)
            return ResponseEntity
                    .status(400)
                    .body(BaseResponseBody.of("요청받은 정보가 없습니다."));

        // 댓글 등록 및 결과 반환
        int resultCode = commentService.createComment(memberId, commentRequest);

        // 유효성 검증, 오류가 존재하면 오류 메시지가 포함된 Response 객체 반환
        ResponseEntity<BaseResponseBody> errorResponse = checkResultCode(resultCode);
        if (errorResponse != null)
            return errorResponse;

        return ResponseEntity
                .status(201)
                .body(BaseResponseBody.of("댓글이 등록되었습니다."));
    }

    @PutMapping("/{comment_id}")
    public ResponseEntity<BaseResponseBody> updateComment(
            @PathVariable Long comment_id,
            @RequestBody Optional<PutCommentRequest> optionalPutCommentRequest,
            HttpServletRequest request) {
        PutCommentRequest putCommentRequest = optionalPutCommentRequest.orElse(null);

        // 요청자 확인
        Long memberId = jwtUtil.getUserId(request);
        if (memberId == null)
            return NOT_VALID_TOKEN_RESPONSE;

        // Request Body가 없을 경우,
        if (putCommentRequest == null)
            return ResponseEntity
                    .status(400)
                    .body(BaseResponseBody.of("요청받은 정보가 없습니다."));

        // 댓글 수정
        ResponseEntity<BaseResponseBody> errRes = checkResultCode(
                commentService.updateComment(memberId, comment_id, putCommentRequest)
        );
        if (errRes != null)
            return errRes;

        return ResponseEntity
                .status(201)
                .body(BaseResponseBody.of("댓글이 수정되었습니다."));
    }

    @DeleteMapping("/{comment_id}")
    public ResponseEntity<BaseResponseBody> deleteComment(
            @PathVariable Long comment_id,
            HttpServletRequest request) {
        // 요청자 확인
        Long memberId = jwtUtil.getUserId(request);
        if (memberId == null)
            return NOT_VALID_TOKEN_RESPONSE;

        int resultCode = commentService.deleteComment(memberId, comment_id);

        // 유효성 검증, 오류가 존재하면 오류 메시지가 포함된 Response 객체 반환
        ResponseEntity<BaseResponseBody> errorResponse = checkResultCode(resultCode);
        if (errorResponse != null)
            return errorResponse;

        return ResponseEntity
                .status(200)
                .body(BaseResponseBody.of("댓글이 삭제되었습니다."));
    }

    private ResponseEntity<BaseResponseBody> checkResultCode(int resultCode) {
        // 댓글 관련 오류코드
        final int NO_AUCTION = 1;  // 경매 식별번호 없을 경우
        final int NO_MEMBER = 2;  // 사용자 식별번호 없을 경우
        final int BLANK_CONTENT = 3;  // 공백 문자열일 경우
        final int MAX_LENGTH_EXCEEDED = 4;  // 최대 길이를 초과할 경우
        final int NO_COMMENT = 5;  // 부모 댓글 식별번호가 없을 경우
        final int NOT_EQUAL = 6;

        switch (resultCode) {
            case NO_AUCTION:
                return ResponseEntity
                        .status(400)
                        .body(BaseResponseBody.of("존재하지 않는 경매입니다."));
            case NO_MEMBER:
                return ResponseEntity
                        .status(400)
                        .body(BaseResponseBody.of("존재하지 않는 사용자입니다."));
            case BLANK_CONTENT:
                return ResponseEntity
                        .status(400)
                        .body(BaseResponseBody.of("댓글의 내용이 업습니다."));
            case MAX_LENGTH_EXCEEDED:
                return ResponseEntity
                        .status(400)
                        .body(BaseResponseBody.of("최대 길이를 초과하였습니다."));
            case NO_COMMENT:
                return ResponseEntity
                        .status(400)
                        .body(BaseResponseBody.of("존재하지 않는 댓글입니다."));
            case NOT_EQUAL:
                return ResponseEntity
                        .status(403)
                        .body(BaseResponseBody.of("댓글 작성자가 아닙니다."));
        }
        return null;
    }
}
