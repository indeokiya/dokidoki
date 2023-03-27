package com.dokidoki.notice.api.service;

import com.dokidoki.notice.db.entity.AuctionRealtime;
import com.dokidoki.notice.db.repository.AuctionRealtimeLeaderBoardRepository;
import com.dokidoki.notice.db.repository.AuctionRealtimeMemberRepository;
import com.dokidoki.notice.kafka.dto.alert.KafkaAlertSuccessDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

    private final AuctionRealtimeMemberRepository auctionRealtimeMemberRepository;
    private final AuctionRealtimeLeaderBoardRepository auctionRealtimeLeaderBoardRepository;

    /**
     * 경매 성공한 한 명에게 알림 발송
     * @param auctionRealtime
     */
    public void auctionSuccess(AuctionRealtime auctionRealtime) {
        KafkaAlertSuccessDTO dto = KafkaAlertSuccessDTO.of(auctionRealtime);

    }

    /**
     * 경매 실패한 모두에게 알림 발송
     * @param auctionRealtime
     */
    public void auctionFail(AuctionRealtime auctionRealtime) {

    }

    /**
     * 판매자에게 판매 성공했다는 알림 발송
     * @param auctionRealtime
     */
    public void auctionComplete(AuctionRealtime auctionRealtime) {

    }

    public void auctionOutBid() {

    }
}
