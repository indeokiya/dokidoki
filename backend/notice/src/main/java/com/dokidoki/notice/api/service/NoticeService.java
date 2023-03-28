package com.dokidoki.notice.api.service;

import com.dokidoki.notice.api.response.NoticeCompleteResp;
import com.dokidoki.notice.api.response.NoticeFailResp;
import com.dokidoki.notice.api.response.NoticeOutBidResp;
import com.dokidoki.notice.db.entity.AuctionRealtime;
import com.dokidoki.notice.db.repository.AuctionRealtimeLeaderBoardRepository;
import com.dokidoki.notice.db.repository.AuctionRealtimeMemberRepository;
import com.dokidoki.notice.api.response.NoticeSuccessResp;
import com.dokidoki.notice.kafka.dto.KafkaAuctionEndDTO;
import com.dokidoki.notice.kafka.dto.KafkaBidDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

    private final AuctionRealtimeMemberRepository auctionRealtimeMemberRepository;
    private final AuctionRealtimeLeaderBoardRepository auctionRealtimeLeaderBoardRepository;
    private final SimpMessageSendingOperations messagingTemplate;

    /**
     * 경매 성공한 한 명에게 알림 발송
     * @param dto
     */
    public void auctionSuccess(KafkaAuctionEndDTO dto) {
        log.info("received kafkaAuctionEndDTO: {}", dto);
        NoticeSuccessResp resp = NoticeSuccessResp.of(dto);
        long memberId = auctionRealtimeLeaderBoardRepository.getWinner(dto.getAuctionId()).getMemberId();
        messagingTemplate.convertAndSend("ws/notice/"+memberId+"/success", resp);
    }

    /**
     * 경매 실패한 모두에게 알림 발송
     * @param dto
     */
    public void auctionFail(KafkaAuctionEndDTO dto) {
        log.info("received kafkaAuctionEndDTO: {}", dto);
        long auctionId = dto.getAuctionId();
        long winnerId = auctionRealtimeLeaderBoardRepository.getWinner(auctionId).getMemberId();
        Set<Map.Entry<Long, Integer>> entries = auctionRealtimeMemberRepository.getAll(auctionId);
        for(Map.Entry<Long, Integer> entry: entries) {
            long memberId = entry.getKey().longValue();
            int myFinalPrice = entry.getValue();
            if ( memberId == winnerId) {
                continue;
            }
            NoticeFailResp resp = NoticeFailResp.of(dto, myFinalPrice);
            messagingTemplate.convertAndSend("ws/notice/"+memberId+"/fail", resp);
        }
    }

    /**
     * 판매자에게 판매 성공했다는 알림 발송
     * @param dto
     */
    public void auctionComplete(KafkaAuctionEndDTO dto) {
        log.info("received kafkaAuctionEndDTO: {}", dto);
        long sellerId = dto.getSellerId();
        NoticeCompleteResp resp = NoticeCompleteResp.of(dto);
        messagingTemplate.convertAndSend("ws/notice/"+sellerId+"/complete", resp);

    }

    /**
     * 입찰 강탈되었다는 알림 발송
     * @param dto
     */
    public void auctionOutBid(KafkaBidDTO dto) {
        log.info("received kafkaBidDTO: {}", dto);
        long memberId = dto.getMemberId();
        long beforeWinnerId = dto.getBeforeWinnerId();
        if (memberId == beforeWinnerId) {
            return;
        }
        NoticeOutBidResp resp = NoticeOutBidResp.of(dto);
        messagingTemplate.convertAndSend("ws/notice/"+beforeWinnerId+"/outbid", resp);


    }
}
