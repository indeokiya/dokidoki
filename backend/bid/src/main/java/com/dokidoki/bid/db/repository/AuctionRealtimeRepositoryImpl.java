package com.dokidoki.bid.db.repository;

import com.dokidoki.bid.api.response.LeaderBoardMemberInfo;
import com.dokidoki.bid.common.annotation.RTransactional;
import com.dokidoki.bid.common.codes.RealTimeConstants;
import com.dokidoki.bid.db.entity.AuctionRealtime;
import com.dokidoki.bid.kafka.dto.KafkaAuctionEndDTO;
import com.dokidoki.bid.kafka.service.KafkaBidProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.ObjectListener;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.redisson.api.map.event.EntryEvent;
import org.redisson.api.map.event.EntryExpiredListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
@Slf4j
public class AuctionRealtimeRepositoryImpl implements AuctionRealtimeRepository {

    private String key = RealTimeConstants.mapKey;
    private RMapCache<Long, AuctionRealtime> map;
    private final KafkaBidProducer kafkaBidProducer;
    private final AuctionRealtimeLeaderBoardRepository auctionRealtimeLeaderBoardRepository;

    @Autowired
    public void setAuctionRealtimeRepositoryImpl(RedissonClient redisson) {
        this.key = RealTimeConstants.mapKey;
        this.map = redisson.getMapCache(key);
        map.addListener(getExpiredListener());

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
    @RTransactional
    public void save(AuctionRealtime auctionRealtime) {
        map.put(auctionRealtime.getAuctionId(), auctionRealtime);
    }

    @Override
    @RTransactional
    public void save(AuctionRealtime auctionRealtime, long ttl, TimeUnit timeUnit) {
        map.put(auctionRealtime.getAuctionId(), auctionRealtime, ttl, timeUnit);
    }

    @Override
    @RTransactional
    public boolean deleteAll() {
        return map.delete();
    }


    /**
     * Redis 에 저장된 auctionRealtime 이 파기되는 순간 작동하는 메서드
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

}
