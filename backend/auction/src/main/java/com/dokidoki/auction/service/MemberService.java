package com.dokidoki.auction.service;

import com.dokidoki.auction.common.error.exception.InvalidValueException;
import com.dokidoki.auction.domain.entity.MemberEntity;
import com.dokidoki.auction.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 멤버id로 member 정보 조회
     * @param memberId
     * @return  멤버정보
     */
    public MemberEntity getMemberById(Long memberId) {

        // 사용자 유무 체크 (추후 필요한지 확인)
        Optional<MemberEntity> memberO = memberRepository.findById(memberId);
        if (memberO.isEmpty()) {
            throw new InvalidValueException("해당 회원이 존재하지 않습니다.");
        }

//        return memberRepository.findById(memberId).get();
        return memberO.get();
    }


}
