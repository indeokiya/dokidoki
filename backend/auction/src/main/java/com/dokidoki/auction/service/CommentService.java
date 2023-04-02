package com.dokidoki.auction.service;

import com.dokidoki.auction.domain.entity.AuctionEndEntity;
import com.dokidoki.auction.domain.entity.AuctionIngEntity;
import com.dokidoki.auction.domain.entity.CommentEntity;
import com.dokidoki.auction.domain.entity.MemberEntity;
import com.dokidoki.auction.domain.repository.AuctionEndRepository;
import com.dokidoki.auction.domain.repository.AuctionIngRepository;
import com.dokidoki.auction.domain.repository.CommentRepository;
import com.dokidoki.auction.domain.repository.MemberRepository;
import com.dokidoki.auction.dto.request.CommentReq;
import com.dokidoki.auction.dto.response.CommentResp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final AuctionIngRepository auctionIngRepository;
    private final AuctionEndRepository auctionEndRepository;

    @Transactional(readOnly = true)
    public List<CommentResp> readComment(Long auctionId) {
        // 경매 식별번호로 모든 댓글 검색
        List<CommentEntity> commentEntities = commentRepository.findCommentsByAuctionIdOrderByWrittenTime(auctionId);

        // Entity -> DTO 변환
        // 1. 댓글과 대댓글로 나누어 처리
        // 2. parent_id가 null인 댓글일 경우,
        //      1) commentResponses에 바로 add
        // 3. parent_id가 있는 대댓글일 경우,
        //      1) indexOf 변수를 사용해 commentResponses 에 저장된 부모 댓글의 인덱스를 구함
        //      2) 부모 댓글 객체로 접근한 뒤, 부모 댓글 sub_comments에 대댓글 추가

        // DTO 생성
        List<CommentResp> commentRespons = new ArrayList<>();

        // 부모 댓글의 인덱스를 관리할 변수
        Map<Long, Integer> indexOf = new HashMap<>();

        // 모든 comment 처리
        for (CommentEntity commentEntity : commentEntities) {
            // 댓글일 경우, commentResponses 에 삽입 후 indexOf 에 위치 저장
            if (commentEntity.getParentId() == null) {
                indexOf.put(commentEntity.getId(), commentRespons.size());
                commentRespons.add(new CommentResp(commentEntity));
            }
            // 대댓글일 경우, 부모 댓글에 본인 추가
            else {
                int parentIndex = indexOf.get(commentEntity.getParentId());
                commentRespons
                        .get(parentIndex)
                        .getSub_comments()
                        .add(new CommentResp(commentEntity));
            }
        }

        return commentRespons;
    }

    @Transactional
    public int createComment(Long memberId, Long auctionId, CommentReq commentReq) {
        // 존재하지 않는 경매 식별번호일 경우,
        if (!existsAuction(auctionId))
            return 1;

        // 존재하지 않는 사용자 식별번호일 경우,
        // MSA니까 사용자 서버에 사용자 객체 요청해야 할 듯?
        MemberEntity memberEntity = memberRepository.findById(memberId).orElse(null);
        if (memberEntity == null)
            return 2;

        // 댓글이 빈 문자열일 경우,
        String comment = commentReq.getContent();
        if (comment == null || comment.isBlank())
            return 3;
        // 댓글이 255자를 넘길 경우,
        else if (comment.length() > 255)
            return 4;

        // 부모 댓글이 설정되어 있으나, 존재하지 않는 댓글일 경우,
        Long parentId = commentReq.getParent_id();
        if (parentId != null) {
            CommentEntity parentCommentEntity = commentRepository.findById(parentId).orElse(null);
            if (parentCommentEntity == null)
                return 5;
        }

        CommentEntity newCommentEntity = CommentEntity.builder()
                .auctionId(auctionId)
                .memberEntity(memberEntity)
                .content(comment)
                .parentId(parentId)
                .build();
        commentRepository.save(newCommentEntity);
        return 0;
    }

    @Transactional
    public int updateComment(Long memberId, Long auctionId, Long commentId, String newComment) {
        // 존재하지 않는 경매 식별번호일 경우,
        if (!existsAuction(auctionId))
            return 1;

        CommentEntity commentEntity = commentRepository.findById(commentId).orElse(null);

        // 존재하지 않는 댓글일 경우
        if (commentEntity == null)
            return 5;
        // 요청자와 작성자가 일치하지 않을 경우
        if (!memberId.equals(commentEntity.getMemberEntity().getId()))
            return 6;
        // 새로운 댓글이 비어있을 경우
        if (newComment.isBlank())
            return 3;
        // 255자를 초과할 경우
        if (newComment.length() > 255)
            return 4;

        // 업데이트
        // 1. 기존 정보에 새로운 댓글로 교체한 객체 생성
        CommentEntity newCommentEntity = CommentEntity.builder()
                .id(commentEntity.getId())  // Update를 위해 PK도 기존대로 설정
                .auctionId(commentEntity.getAuctionId())
                .memberEntity(commentEntity.getMemberEntity())
                .content(newComment)
                .parentId(commentEntity.getParentId())
                .writtenTime(commentEntity.getWrittenTime())
                .build();

        // 2. 저장
        commentRepository.save(newCommentEntity);

        return 0;
    }

    @Transactional
    public int deleteComment(Long memberId, Long auctionId, Long commentId) {
        // 존재하지 않는 경매 식별번호일 경우,
        if (!existsAuction(auctionId))
            return 1;

        // {comment_id}를 갖는 댓글이 있는지 확인
        CommentEntity commentEntity = commentRepository.findById(commentId).orElse(null);
        if (commentEntity == null)
            return 5;

        // 요청자와 작성자가 일치하는지 확인
        if (!memberId.equals(commentEntity.getMemberEntity().getId()))
            return 6;

        // 댓글 삭제 (하위 댓글이 있을 수 있으므로 member와 content만 삭제)
        CommentEntity deletedCommentEntity = CommentEntity.builder()
                .id(commentEntity.getId())
                .auctionId(commentEntity.getAuctionId())
                .memberEntity(null)
                .content("")
                .parentId(commentEntity.getParentId())
                .writtenTime(commentEntity.getWrittenTime())
                .build();
        commentRepository.save(deletedCommentEntity);

        // 댓글 더미(댓글 + 대댓글)를 가져온 뒤 가장 최근 댓글부터 DB에서 삭제 처리 (더이상 하위 댓글이 없는 댓글은 삭제해도 되므로)
        Long parentId = commentEntity.getParentId() == null
                ? commentId
                : commentEntity.getParentId();
        List<CommentEntity> commentEntities = commentRepository
                .findAllByIdIsOrParentIdIsOrderByIdDesc(parentId, parentId);

        // 삭제할 댓글의 ID 리스트 구하기
        List<Long> idToRemove = new ArrayList<>();
        for (CommentEntity comment : commentEntities) {
            if (comment.getMemberEntity() != null)  // 유효한 댓글이 나타나면 더이상 DB에서 지우면 안 되므로 반복 종료
                break;
            idToRemove.add(comment.getId());
        }

        // 삭제
        commentRepository.deleteAllByIdInBatch(idToRemove);

        return 0;
    }

    private boolean existsAuction(Long auctionId) {
        // 존재하지 않는 경매 식별번호일 경우,
        if (auctionId == null)
            return false;
        // 진행중 경매 테이블 확인, 없으면 종료된 경매 테이블도 확인. 두 군데 모두 없으면 NO_AUCTION 반환
        AuctionIngEntity auctionIngEntity = auctionIngRepository.findById(auctionId).orElse(null);
        if (auctionIngEntity == null) {
            AuctionEndEntity auctionEndEntity = auctionEndRepository.findById(auctionId).orElse(null);
            return auctionEndEntity != null;
        }

        return true;
    }
}
