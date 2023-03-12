package com.dokidoki.auction.service;

import com.dokidoki.auction.domain.entity.Comment;
import com.dokidoki.auction.domain.entity.Member;
import com.dokidoki.auction.domain.repository.CommentRepository;
import com.dokidoki.auction.domain.repository.MemberRepository;
import com.dokidoki.auction.dto.request.CommentRequest;
import com.dokidoki.auction.dto.response.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    public List<CommentResponse> readComment(Long auction_id) {
        // 존재하지 않는 경매 식별번호일 경우,
        // ~ 미구현 ~

        // 경매 식별번호로 모든 댓글 검색
        List<Comment> comments = commentRepository.findCommentsByAuctionIdOrderByWrittenTime(auction_id);

        // Entity -> DTO 변환
        // 1. 댓글과 대댓글로 나누어 처리
        // 2. parent_id가 null인 댓글일 경우,
        //      1) commentResponses에 바로 add
        // 3. parent_id가 있는 대댓글일 경우,
        //      1) indexOf 변수를 사용해 commentResponses 에 저장된 부모 댓글의 인덱스를 구함
        //      2) 부모 댓글 객체로 접근한 뒤, 부모 댓글 sub_comments에 대댓글 추가

        // DTO 생성
        List<CommentResponse> commentResponses = new ArrayList<>();

        // 부모 댓글의 인덱스를 관리할 변수
        Map<Long, Integer> indexOf = new HashMap<>();

        // 모든 comment 처리
        for (int i = 0; i < comments.size(); i++) {
            Comment comment = comments.get(i);

            // 댓글일 경우, commentResponses 에 삽입 후 indexOf 에 위치 저장
            if (comment.getParentId() == null) {
                indexOf.put(comment.getId(), commentResponses.size());
                commentResponses.add(new CommentResponse(comment));
            }
            // 대댓글일 경우, 부모 댓글에 본인 추가
            else {
                int parentIndex = indexOf.get(comment.getParentId());
                commentResponses
                        .get(parentIndex)
                        .getSub_comments()
                        .add(new CommentResponse(comment));
            }
        }

        return commentResponses;
    }

    @Transactional
    public int createComment(CommentRequest commentRequest) {
        // 존재하지 않는 경매 식별번호일 경우,
        // ~ 미구현 ~

        // 존재하지 않는 사용자 식별번호일 경우,
        // MSA니까 사용자 서버에 사용자 객체 요청해야 할 듯?
        Optional<Member> optionalMember = memberRepository.findById(commentRequest.getMember_id());
        if (optionalMember.isEmpty()) {
            return 2;
        }
        Member member = optionalMember.get();

        // 댓글이 빈 문자열일 경우,
        if (commentRequest.getContent().isBlank()) {
            return 3;
        }
        // 댓글이 255자를 넘길 경우,
        else if (commentRequest.getContent().length() > 255) {
            return 4;
        }

        // 부모 댓글이 설정되어 있으나, 존재하지 않는 댓글일 경우,
        // ~ 미구현 ~

        Comment newComment = Comment.createComment(
                commentRequest.getAuction_id(),
                member,
                commentRequest.getContent(),
                commentRequest.getParent_id()
        );
        commentRepository.save(newComment);
        return 0;
    }

    @Transactional
    public int updateComment(Long id, CommentRequest commentRequest) {
        Optional<Comment> optionalComment = commentRepository.findById(id);

        // 존재하지 않는 댓글일 경우
        if (optionalComment.isEmpty()) {
            return 5;
        }

        // 업데이트
        Comment comment = optionalComment.get();
        comment.updateComment(commentRequest);

        return 0;
    }
}
