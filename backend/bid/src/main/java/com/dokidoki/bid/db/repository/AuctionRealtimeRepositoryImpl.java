package com.dokidoki.bid.db.repository;

import com.dokidoki.bid.api.response.LeaderBoardMemberInfo;
import com.dokidoki.bid.common.annotation.RTransactional;
import com.dokidoki.bid.common.codes.RealTimeConstants;
import com.dokidoki.bid.common.error.exception.InvalidValueException;
import com.dokidoki.bid.db.entity.AuctionRealtime;
import com.dokidoki.bid.kafka.dto.KafkaAuctionEndDTO;
import com.dokidoki.bid.kafka.service.KafkaBidProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.RedissonObject;
import org.redisson.api.*;
import org.redisson.api.listener.PatternMessageListener;
import org.redisson.api.map.event.EntryEvent;
import org.redisson.api.map.event.EntryExpiredListener;
import org.redisson.client.RedisPubSubListener;
import org.redisson.client.protocol.pubsub.PubSubType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
@Slf4j
public class AuctionRealtimeRepositoryImpl implements AuctionRealtimeRepository {

    private String key = RealTimeConstants.mapKey;
    private String expireKey = RealTimeConstants.expireKey;
    private RedissonClient redisson;
    private RMap<Long, AuctionRealtime> map;
    private final KafkaBidProducer kafkaBidProducer;
    private final AuctionRealtimeLeaderBoardRepository auctionRealtimeLeaderBoardRepository;

    @Autowired
    public void setAuctionRealtimeRepositoryImpl(RedissonClient redisson) {
        this.redisson = redisson;
        this.key = RealTimeConstants.mapKey;
        this.map = redisson.getMap(key);
//        map.addListener(getExpiredListener());
    }

    private String getExpireKey(long auctionId) {
        StringBuilder sb = new StringBuilder();
        sb.append(expireKey).append(":").append(auctionId);
        return sb.toString();
    }

    @Override
    public Optional<AuctionRealtime> findById(long auctionId) {
        AuctionRealtime auctionRealtime = map.get(auctionId);
        if (auctionRealtime == null) {
            return Optional.empty();
        } else {
            return Optional.of(auctionRealtime);
        }
    }

    @Override
    public boolean isExpired(long auctionId) {
        RBucket bucket = redisson.getBucket(getExpireKey(auctionId));
        if (bucket.get() == null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    @RTransactional
    public void save(AuctionRealtime auctionRealtime) {
        map.put(auctionRealtime.getAuctionId(), auctionRealtime);
    }

    @Override
    @RTransactional
    public void save(AuctionRealtime auctionRealtime, long ttl, TimeUnit timeUnit) {
        long auctionId = auctionRealtime.getAuctionId();
        RBucket<Long> bucket = redisson.getBucket(getExpireKey(auctionId));
        int listenerId = bucket.addListener(getExpiredObjectListener());
        bucket.set(auctionId, ttl, timeUnit);
        auctionRealtime.setListenerId(listenerId);

        map.put(auctionRealtime.getAuctionId(), auctionRealtime);
    }

    @Override
    @RTransactional
    public boolean deleteAll() {
        return map.delete();
    }


    /**
     * Redis 에 저장된 auctionRealtime 이 파기되는 순간 작동하는 메서드 ( RMapCache 로 구현할 때 사용)
     * @return
     */
    private EntryExpiredListener<Long, AuctionRealtime> getExpiredListener() {
        EntryExpiredListener<Long, AuctionRealtime> expiredListener = new EntryExpiredListener<Long, AuctionRealtime>() {
            @Override
            public void onExpired(EntryEvent<Long, AuctionRealtime> event) {
                long auctionId = event.getKey();
                AuctionRealtime auctionRealtime = event.getValue();
                log.info("auctionInfo expired. auctionId: {}, auctionRealtime: {}", auctionId, auctionRealtime);

                LeaderBoardMemberInfo winner = auctionRealtimeLeaderBoardRepository.getWinner(auctionRealtime.getAuctionId()).get();

                // 1. 기간이 끝나면 Kafka 에 메시지 써서  (1) 알림 서버 (2) auction 서버 에 알리기
                KafkaAuctionEndDTO dto = KafkaAuctionEndDTO.of(auctionRealtime, winner);
                kafkaBidProducer.sendAuctionEnd(dto);



            }
        };

        return expiredListener;
    }

    /**
     * Bucket 으로 저장해둔 key 가 expire 되었는지를 확인하는 listener
     * @return
     */
    private ExpiredObjectListener getExpiredObjectListener() {
        ExpiredObjectListener expiredObjectListener = new ExpiredObjectListener() {
            @Override
            public void onExpired(String name) {
                long auctionId  = Long.parseLong(name.split(":")[1]);
                Optional<AuctionRealtime> auctionRealtimeO = findById(auctionId);
                if (auctionRealtimeO.isEmpty()) {
                    throw new InvalidValueException("로직상 오류가 생겼습니다. 이미 종료된 경매입니다.");
                }
                AuctionRealtime auctionRealtime = auctionRealtimeO.get();

                log.info("auctionInfo expired. auctionId: {}, auctionRealtime: {}", auctionId, auctionRealtime);

                // 1. 기간이 끝나면 Kafka 에 메시지 써서  (1) 알림 서버 (2) auction 서버 에 알리기
                KafkaAuctionEndDTO dto;
                Optional<LeaderBoardMemberInfo> winnerO = auctionRealtimeLeaderBoardRepository.getWinner(auctionRealtime.getAuctionId());
                if (winnerO.isEmpty()) {
                    log.info("auctionId: {} 경매에서는 입찰자가 없습니다.", auctionId);
                    dto = KafkaAuctionEndDTO.of(auctionRealtime, -1L);
                } else {
                    dto = KafkaAuctionEndDTO.of(auctionRealtime, winnerO.get());
                }

                kafkaBidProducer.sendAuctionEnd(dto);

                // 2. listener 제거
                int listenerId = auctionRealtime.getListenerId();
                log.info("listener 제거. listenerId: {}", listenerId);
                RBucket bucket = redisson.getBucket(getExpireKey(auctionId));
                bucket.removeListener(listenerId);

            }
        };
        return expiredObjectListener;
    }

}
