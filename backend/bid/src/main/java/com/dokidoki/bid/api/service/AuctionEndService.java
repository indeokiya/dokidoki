package com.dokidoki.bid.api.service;

import com.dokidoki.bid.api.response.LeaderBoardMemberInfo;
import com.dokidoki.bid.db.entity.AuctionRealtime;
import com.dokidoki.bid.db.repository.AuctionRealtimeBiddingRepository;
import com.dokidoki.bid.db.repository.AuctionRealtimeLeaderBoardRepository;
import com.dokidoki.bid.kafka.dto.KafkaAuctionEndDTO;
import com.dokidoki.bid.kafka.service.KafkaBidProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuctionEndService {

    private final RedissonClient redisson;
    private final AuctionRealtimeLeaderBoardRepository auctionRealtimeLeaderBoardRepository;
    private final AuctionRealtimeBiddingRepository auctionRealtimeBiddingRepository;
    private final KafkaBidProducer kafkaBidProducer;

    public void auctionEnd(AuctionRealtime auctionRealtime, String expireKey) {

        log.info("auctionInfo expired. auctionRealtime: {}", auctionRealtime);

        long auctionId = auctionRealtime.getAuctionId();
        long sellerId = auctionRealtime.getSellerId();

        // 1. 현재 입찰 진행중인 목록에서 제거
        auctionRealtimeBiddingRepository.delete(sellerId, auctionId);

        // 2. 기간이 끝나면 Kafka 에 메시지 써서  (1) 알림 서버 (2) auction 서버 에 알리기
        KafkaAuctionEndDTO dto;
        Optional<LeaderBoardMemberInfo> winnerO = auctionRealtimeLeaderBoardRepository.getWinner(auctionRealtime.getAuctionId());
        if (winnerO.isEmpty()) {
            log.info("auctionId: {} 경매에서는 입찰자가 없습니다.", auctionId);
            dto = KafkaAuctionEndDTO.of(auctionRealtime, -1L);
        } else {
            dto = KafkaAuctionEndDTO.of(auctionRealtime, winnerO.get());
        }

        kafkaBidProducer.sendAuctionEnd(dto);

        // 3. listener 제거
        int listenerId = auctionRealtime.getListenerId();
        log.info("listener 제거. listenerId: {}", listenerId);
        RBucket bucket = redisson.getBucket(expireKey);
        bucket.removeListener(listenerId);

    }
}
