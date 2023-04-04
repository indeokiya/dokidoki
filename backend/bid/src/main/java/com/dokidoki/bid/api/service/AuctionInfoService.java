package com.dokidoki.bid.api.service;

import com.dokidoki.bid.api.response.*;
import com.dokidoki.bid.db.entity.AuctionRealtime;
import com.dokidoki.bid.db.repository.AuctionRealtimeBiddingRepository;
import com.dokidoki.bid.db.repository.AuctionRealtimeLeaderBoardRepository;
import com.dokidoki.bid.db.repository.AuctionRealtimeMemberRepository;
import com.dokidoki.bid.db.repository.AuctionRealtimeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.client.protocol.ScoredEntry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuctionInfoService {

    private final AuctionRealtimeBiddingRepository auctionRealtimeBiddingRepository;
    private final AuctionRealtimeRepository auctionRealtimeRepository;
    private final AuctionRealtimeLeaderBoardRepository auctionRealtimeLeaderBoardRepository;
    private final AuctionRealtimeMemberRepository auctionRealtimeMemberRepository;

    /**
     * 현재 입찰중인 경매 정보 가져오기. Controller 에서 접근하는 메서드.
     * @param memberId
     * @return
     */
    public Set<Long> auctionBiddingList(Long memberId) {
        return auctionRealtimeBiddingRepository.findById(memberId);
    }

    /**
     * 경매 초기 정보 실시간 정보를 가져오는 메서드. 컨트롤러에서 접근하는 메서드
     * 실시간 정보가 없을 때에는 리더보드 정보만을 제공하고,
     * 실시간 정보가 있을 때에는 highestPrice 와 priceSize 도 함께 보내준다.
     * @param auctionId
     * @return
     */
    public AuctionInitialInfoResp getInitialInfo(Long auctionId) {
        log.info("경매 상세 페이지에서 상세 정보 조회 요청. auctionId: {}", auctionId);

        // 1. 초기 리더보드 정보 가져오기
        List<LeaderBoardMemberResp> initialLeaderBoard = getInitialLeaderBoard(auctionId);

        List<MemberChartResp> initialMemberChartData = getInitialMemberChartData(auctionId);

        Optional<AuctionRealtime> auctionRealtimeO = auctionRealtimeRepository.findById(auctionId);

        // 2. 경매 정보가 없는 경우, 리더보드 정보만 보내기
        if (auctionRealtimeO.isEmpty()) {
            AuctionInitialInfoResp resp = AuctionInitialInfoResp.builder()
                    .leaderBoard(initialLeaderBoard)
                    .build();
            return resp;
        }

        // 3. 경매 정보가 있는 경우, 현재 최고가와 경매 단위도 같이 보내기
        AuctionInitialInfoResp resp = AuctionInitialInfoResp.builder()
                .highestPrice(auctionRealtimeO.get().getHighestPrice())
                .priceSize(auctionRealtimeO.get().getPriceSize())
                .leaderBoard(initialLeaderBoard)
                .memberChart(initialMemberChartData)
                .build();
        return resp;
    }

    /**
     * Redis 의 SortedSet 에 저장된 leaderboard 정보를 바탕으로
     * client 에게 보내줄 리더보드 정보를 가공하는 메서드.
     * @param auctionId 경매 ID
     * @return client 에게 보내줄 리더보드 정보
     */
    public List<LeaderBoardMemberResp> getInitialLeaderBoard(Long auctionId) {
        List<LeaderBoardMemberResp> list = new ArrayList<>();

        Collection<ScoredEntry<LeaderBoardMemberInfo>> leaderBoardMemberInfos = auctionRealtimeLeaderBoardRepository.getAll(auctionId);

        for (ScoredEntry<LeaderBoardMemberInfo> info: leaderBoardMemberInfos) {
            Long bidPrice = info.getScore().longValue();
            LeaderBoardMemberResp resp = LeaderBoardMemberResp.of(info.getValue(), bidPrice);
            list.add(resp);
        }
        log.info("initialLeaderBoard: {}", list);

        return list;
    }

    /**
     * 초기 차트정보 가져오기. 유저별 정보는 0번부터 시작하므로, 이중 리스트로 넘기기
     * @param auctionId
     * @return
     */
    public List<MemberChartResp> getInitialMemberChartData(Long auctionId) {
        List<MemberChartResp> resList = new ArrayList<>();
        Collection<ScoredEntry<LeaderBoardMemberInfo>> biddingInfos = auctionRealtimeLeaderBoardRepository.getAll(auctionId);
        int listSize = auctionRealtimeMemberRepository.getAll(auctionId).size();
        log.info("listSize: {}", listSize);
        for (int i = 0; i < listSize; i++) {
            MemberChartResp memberChartResp = MemberChartResp.builder()
                            .bidInfos(new ArrayList<>()).build();
            resList.add(memberChartResp);
        }

        for (ScoredEntry<LeaderBoardMemberInfo> info: biddingInfos) {
            Long bidPrice = info.getScore().longValue();
            LeaderBoardMemberInfo memberInfo = info.getValue();
            int bidNum = memberInfo.getBidNum().intValue();
            LocalDateTime bidTime = memberInfo.getBidTime();
            String name = memberInfo.getName();

            resList.get(bidNum).updateName(name);
            BidInfo bidInfo = BidInfo.builder().bidTime(bidTime).bidPrice(bidPrice).build();
            resList.get(bidNum).getBidInfos().add(bidInfo);
        }

        return resList;
    }
}
