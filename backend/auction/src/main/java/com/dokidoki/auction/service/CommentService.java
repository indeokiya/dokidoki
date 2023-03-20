package com.dokidoki.auction.service;

import com.dokidoki.auction.domain.entity.CommentEntity;
import com.dokidoki.auction.domain.entity.MemberEntity;
import com.dokidoki.auction.domain.repository.CommentRepository;
import com.dokidoki.auction.domain.repository.MemberRepository;
import com.dokidoki.auction.dto.request.CommentRequest;
import com.dokidoki.auction.dto.request.PutCommentRequest;
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
        List<CommentEntity> commentEntities = commentRepository.findCommentsByAuctionIdOrderByWrittenTime(auction_id);

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
        for (CommentEntity commentEntity : commentEntities) {
            // 댓글일 경우, commentResponses 에 삽입 후 indexOf 에 위치 저장
            if (commentEntity.getParentId() == null) {
                indexOf.put(commentEntity.getId(), commentResponses.size());
                commentResponses.add(new CommentResponse(commentEntity));
            }
            // 대댓글일 경우, 부모 댓글에 본인 추가
            else {
                int parentIndex = indexOf.get(commentEntity.getParentId());
                commentResponses
                        .get(parentIndex)
                        .getSub_comments()
                        .add(new CommentResponse(commentEntity));
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
        Optional<MemberEntity> optionalMember = memberRepository.findById(commentRequest.getMember_id());
        if (optionalMember.isEmpty()) {
            return 2;
        }
        MemberEntity memberEntity = optionalMember.get();

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

        CommentEntity newCommentEntity = CommentEntity.createComment(
                null,  // INSERT의 경우 Auto Increment를 위해 null 설정
                commentRequest.getAuction_id(),
                memberEntity,
                commentRequest.getContent(),
                commentRequest.getParent_id()
        );
        commentRepository.save(newCommentEntity);
        return 0;
    }

    @Transactional
    public int updateComment(Long comment_id, PutCommentRequest commentRequest) {
        CommentEntity commentEntity = commentRepository.findById(comment_id).orElse(null);

        // 존재하지 않는 댓글일 경우
        if (commentEntity == null)
            return 5;
        // 새로운 댓글이 비어있을 경우
        if (commentRequest.getContent().isBlank())
            return 3;
        // 255자를 초과할 경우
        if (commentRequest.getContent().length() > 255)
            return 4;

        // 업데이트
        // 1. 기존 정보에 새로운 댓글로 교체한 객체 생성
        CommentEntity newCommentEntity = CommentEntity.createComment(
                commentEntity.getId(),  // Update를 위해 PK도 기존대로 설정
                commentEntity.getAuctionId(),
                commentEntity.getMemberEntity(),
                commentRequest.getContent(),
                commentEntity.getParentId()
        );
        // 2. 저장
        commentRepository.save(newCommentEntity);

        return 0;
    }

    @Transactional
    public int deleteComment(Long comment_id) {
        // {comment_id}를 갖는 댓글이 있는지 확인
        Optional<CommentEntity> optionalComment = commentRepository.findById(comment_id);
        if (optionalComment.isEmpty()) {
            return 5;
        }
        // 댓글 삭제
        commentRepository.delete(optionalComment.get());
        // 대댓글 모두 삭제
        commentRepository.deleteCommentsByParentId(optionalComment.get().getId());
        return 0;
    }
}
