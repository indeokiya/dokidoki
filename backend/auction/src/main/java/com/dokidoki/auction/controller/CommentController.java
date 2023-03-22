package com.dokidoki.auction.controller;

import com.dokidoki.auction.common.JWTUtil;
import com.dokidoki.auction.dto.request.CommentRequest;
import com.dokidoki.auction.dto.request.PutCommentRequest;
import com.dokidoki.auction.dto.response.CommentResponse;
import com.dokidoki.auction.dto.response.CommonResponse;
import com.dokidoki.auction.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final ResponseEntity<CommonResponse<Void>> NOT_VALID_TOKEN_RESPONSE = new ResponseEntity<>(
            CommonResponse.of(403, "토큰이 유효하지 않습니다.", null),
            HttpStatus.FORBIDDEN
    );

    @GetMapping("/{auction_id}")
    public ResponseEntity<CommonResponse<List<CommentResponse>>> readComment(@PathVariable Long auction_id) {
        List<CommentResponse> comments = commentService.readComment(auction_id);
        return new ResponseEntity<>(
                CommonResponse.of(200, "댓글 조회 성공", comments),
                HttpStatus.OK
        );
    }

    @PostMapping("")
    public ResponseEntity<CommonResponse<Void>> createComment(
            @RequestBody Optional<CommentRequest> optionalCommentRequest,
            HttpServletRequest request) {
        CommentRequest commentRequest = optionalCommentRequest.orElse(null);

        // 요청자 확인
        Long memberId = jwtUtil.getUserId(request);
        if (memberId == null)
            return NOT_VALID_TOKEN_RESPONSE;

        // Request Body가 없을 경우,
        if (commentRequest == null) {
            return new ResponseEntity<>(
                    CommonResponse.of(400, "요청받은 정보가 없습니다.", null),
                    HttpStatus.BAD_REQUEST
            );
        }

        // 댓글 등록 및 결과 반환
        int resultCode = commentService.createComment(memberId, commentRequest);

        // 유효성 검증, 오류가 존재하면 오류 메시지가 포함된 Response 객체 반환
        ResponseEntity<CommonResponse<Void>> errorResponse = checkResultCode(resultCode);
        if (errorResponse != null) {
            return errorResponse;
        }

        return new ResponseEntity<>(
                CommonResponse.of(201, "댓글이 등록되었습니다.", null),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{comment_id}")
    public ResponseEntity<CommonResponse<Void>> updateComment(
            @PathVariable Long comment_id,
            @RequestBody Optional<PutCommentRequest> optionalPutCommentRequest,
            HttpServletRequest request) {
        PutCommentRequest putCommentRequest = optionalPutCommentRequest.orElse(null);

        // 요청자 확인
        Long memberId = jwtUtil.getUserId(request);
        if (memberId == null)
            return NOT_VALID_TOKEN_RESPONSE;

        // Request Body가 없을 경우,
        if (putCommentRequest == null) {
            return new ResponseEntity<>(
                    CommonResponse.of(400, "요청받은 정보가 없습니다.", null),
                    HttpStatus.BAD_REQUEST
            );
        }

        // 댓글 수정
        ResponseEntity<CommonResponse<Void>> errRes = checkResultCode(
                commentService.updateComment(memberId, comment_id, putCommentRequest)
        );
        if (errRes != null)
            return errRes;

        return new ResponseEntity<>(
                CommonResponse.of(201, "댓글이 수정되었습니다.", null),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{comment_id}")
    public ResponseEntity<CommonResponse<Void>> deleteComment(
            @PathVariable Long comment_id,
            HttpServletRequest request) {
        // 요청자 확인
        Long memberId = jwtUtil.getUserId(request);
        if (memberId == null)
            return NOT_VALID_TOKEN_RESPONSE;

        int resultCode = commentService.deleteComment(memberId, comment_id);

        // 유효성 검증, 오류가 존재하면 오류 메시지가 포함된 Response 객체 반환
        ResponseEntity<CommonResponse<Void>> errorResponse = checkResultCode(resultCode);
        if (errorResponse != null)
            return errorResponse;

        return new ResponseEntity<>(
                CommonResponse.of(200, "댓글이 삭제되었습니다.", null),
                HttpStatus.OK
        );
    }

    private ResponseEntity<CommonResponse<Void>> checkResultCode(int resultCode) {
        // 댓글 관련 오류코드
        final int NO_AUCTION = 1;  // 경매 식별번호 없을 경우
        final int NO_MEMBER = 2;  // 사용자 식별번호 없을 경우
        final int BLANK_CONTENT = 3;  // 공백 문자열일 경우
        final int MAX_LENGTH_EXCEEDED = 4;  // 최대 길이를 초과할 경우
        final int NO_COMMENT = 5;  // 부모 댓글 식별번호가 없을 경우
        final int NOT_EQUAL = 6;

        switch (resultCode) {
            case NO_AUCTION:
                return new ResponseEntity<>(
                        CommonResponse.of(400, "존재하지 않는 경매입니다.", null),
                        HttpStatus.BAD_REQUEST
                );
            case NO_MEMBER:
                return new ResponseEntity<>(
                        CommonResponse.of(400, "존재하지 않는 사용자입니다.", null),
                        HttpStatus.BAD_REQUEST
                );
            case BLANK_CONTENT:
                return new ResponseEntity<>(
                        CommonResponse.of(400, "댓글의 내용이 없습니다.", null),
                        HttpStatus.BAD_REQUEST
                );
            case MAX_LENGTH_EXCEEDED:
                return new ResponseEntity<>(
                        CommonResponse.of(400, "최대 길이를 초과하였습니다.", null),
                        HttpStatus.BAD_REQUEST
                );
            case NO_COMMENT:
                return new ResponseEntity<>(
                        CommonResponse.of(400, "존재하지 않는 댓글입니다.", null),
                        HttpStatus.BAD_REQUEST
                );
            case NOT_EQUAL:
                return new ResponseEntity<>(
                        CommonResponse.of(403, "댓글 작성자가 아닙니다.", null),
                        HttpStatus.FORBIDDEN
                );
        }
        return null;
    }
}
