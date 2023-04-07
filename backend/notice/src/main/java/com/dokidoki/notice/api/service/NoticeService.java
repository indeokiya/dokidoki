package com.dokidoki.notice.api.service;

import com.dokidoki.notice.api.controller.WebSocketController;
import com.dokidoki.notice.api.response.*;
import com.dokidoki.notice.common.utils.PayloadUtil;
import com.dokidoki.notice.db.repository.AuctionRealtimeLeaderBoardRepository;
import com.dokidoki.notice.db.repository.AuctionRealtimeMemberRepository;
import com.dokidoki.notice.db.repository.NoticeRepository;
import com.dokidoki.notice.kafka.dto.KafkaAuctionEndDTO;
import com.dokidoki.notice.kafka.dto.KafkaBidDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

    private final AuctionRealtimeMemberRepository auctionRealtimeMemberRepository;
    private final NoticeRepository noticeRepository;
    private final WebSocketController webSocketController;
    private final PayloadUtil payloadUtil;

    /**
     * 경매 성공한 한 명에게 알림 발송
     * @param dto
     */
    public void auctionSuccess(KafkaAuctionEndDTO dto) {
        log.info("received kafkaAuctionEndDTO: {}", dto);
        NoticeSuccessResp resp = NoticeSuccessResp.of(dto);
        Long buyerId = dto.getBuyerId();

        if (buyerId == -1) {
            return;
        }
        noticeRepository.save(buyerId, resp);
        webSocketController.sendAlert(buyerId, payloadUtil.getStringValue(resp));
    }

    /**
     * 경매 실패한 모두에게 알림 발송
     * @param dto
     */
    public void auctionFail(KafkaAuctionEndDTO dto) {
        log.info("received kafkaAuctionEndDTO: {}", dto);
        Long auctionId = dto.getAuctionId();
        Long buyerId = dto.getBuyerId();
        Set<Map.Entry<Long, Long[]>> entries = auctionRealtimeMemberRepository.getAll(auctionId);
        for(Map.Entry<Long, Long[]> entry: entries) {
            Long memberId = entry.getKey().longValue();
            Long myFinalPrice = entry.getValue()[0].longValue();
            if ( memberId.equals(buyerId)) {
                continue;
            }
            NoticeFailResp resp = NoticeFailResp.of(dto, myFinalPrice);
            noticeRepository.save(memberId, resp);
            webSocketController.sendAlert(memberId, payloadUtil.getStringValue(resp));
        }
    }

    /**
     * 판매자에게 판매 성공했다는 알림 발송
     * @param dto
     */
    public void auctionComplete(KafkaAuctionEndDTO dto) {
        log.info("received kafkaAuctionEndDTO: {}", dto);
        Long sellerId = dto.getSellerId();
        NoticeCompleteResp resp = NoticeCompleteResp.of(dto);
        noticeRepository.save(sellerId, resp);
        webSocketController.sendAlert(sellerId, payloadUtil.getStringValue(resp));

    }

    /**
     * 입찰 강탈되었다는 알림 발송
     * @param dto
     */
    public void auctionOutBid(KafkaBidDTO dto) {
        log.info("received kafkaBidDTO: {}", dto);
        Long memberId = dto.getMemberId();
        Long beforeWinnerId = dto.getBeforeWinnerId();
        if (beforeWinnerId.equals(-1L) || memberId.equals(beforeWinnerId)) {
            return;
        }
        NoticeOutBidResp resp = NoticeOutBidResp.of(dto);
        noticeRepository.save(beforeWinnerId, resp);
        webSocketController.sendAlert(beforeWinnerId, payloadUtil.getStringValue(resp));
    }

    /**
     * 해당 유저의 모든 알림 내역 가져오기
     * @param memberId
     * @return
     */
    public Map<Long, NoticeResp> getAllNotice(Long memberId) {
        return noticeRepository.getAll(memberId);
    }

    public void setRead(Long memberId, Long noticeId, boolean isRead) {
        noticeRepository.updateRead(memberId, noticeId, isRead);
    }
}
