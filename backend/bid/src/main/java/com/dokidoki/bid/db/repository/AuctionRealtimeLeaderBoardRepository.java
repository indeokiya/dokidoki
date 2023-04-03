package com.dokidoki.bid.db.repository;

import com.dokidoki.bid.api.response.LeaderBoardMemberInfo;
import com.dokidoki.bid.common.codes.RealTimeConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.quota.ClientQuotaAlteration;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.protocol.ScoredEntry;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Slf4j
public class AuctionRealtimeLeaderBoardRepository {

    private final RedissonClient redisson;
    private final String keyPrefix = RealTimeConstants.leaderboardKey;
    private int limit = RealTimeConstants.leaderboardLimit;

    /**
     * auctionId로 Redis 에 leaderboard 를 저장할 키를 생성하는 메서드
     * @param auctionId 경매 ID
     * @return Redis 에 leaderboard 를 저장할 키
     */
    private String getKey(Long auctionId) {
        StringBuilder sb = new StringBuilder();
        sb.append(keyPrefix).append(":").append(auctionId);
        return sb.toString();
    }

    public Optional<LeaderBoardMemberInfo> getWinner(Long auctionId) {
        RScoredSortedSet<LeaderBoardMemberInfo> scoredSortedSet = redisson.getScoredSortedSet(getKey(auctionId));
        LeaderBoardMemberInfo last = scoredSortedSet.last();
        log.info("score: {}", scoredSortedSet.getScore(last));
        if (last == null) {
            return Optional.empty();
        } else {
            return  Optional.of(last);
        }
    }

    public Collection<ScoredEntry<LeaderBoardMemberInfo>> getAll(Long auctionId) {
        RScoredSortedSet<LeaderBoardMemberInfo> scoredSortedSet = redisson.getScoredSortedSet(getKey(auctionId));
        return scoredSortedSet.entryRangeReversed(0, -1);
    }

    public void save(Long bidPrice, LeaderBoardMemberInfo memberInfo, Long auctionId) {
        RScoredSortedSet<LeaderBoardMemberInfo> scoredSortedSet = redisson.getScoredSortedSet(getKey(auctionId));
        scoredSortedSet.add(bidPrice, memberInfo);
    }

    public void removeOutOfRange(Long auctionId) {
        RScoredSortedSet<LeaderBoardMemberInfo> scoredSortedSet = redisson.getScoredSortedSet(getKey(auctionId));
        scoredSortedSet.removeRangeByRank(-limit -1, -limit -1);
    }

    public void deleteAll(Long auctionId) {
        RScoredSortedSet<LeaderBoardMemberInfo> scoredSortedSet = redisson.getScoredSortedSet(getKey(auctionId));
        scoredSortedSet.delete();
    }


}
