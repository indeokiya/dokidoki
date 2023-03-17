package com.dokidoki.auction.service;

import com.dokidoki.auction.domain.entity.LeaderboardEntity;
import com.dokidoki.auction.domain.entity.Member;
import com.dokidoki.auction.domain.repository.LeaderboardRepository;
import com.dokidoki.auction.domain.repository.MemberRepository;
import com.dokidoki.auction.dto.request.LeaderboardRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LeaderboardService {
    private final LeaderboardRepository leaderboardRepository;
    private final MemberRepository memberRepository;

    public int createLeaderboard(LeaderboardRequest leaderboardRequest) {
        // 리더보드 입찰 내역 삽입
        leaderboardRequest.getHistories().forEach(history -> {
            // 입찰한 사용자 가져오기
            Member member = memberRepository
                    .findById(history.getMember_id())
                    .orElse(null);

            // 사용자가 존재할 경우에만 데이터 삽입
            if (member != null) {
                LeaderboardEntity leaderboardEntity = LeaderboardEntity.createLeaderboard(
                        leaderboardRequest.getAuction_id(),
                        member,
                        history.getBid_price(),
                        history.getBid_time()
                );
                leaderboardRepository.save(leaderboardEntity);
            }
        });

        return 0;
    }
}
