//package com.dokidoki.bid.db.repository;
//
//import com.dokidoki.bid.common.annotation.RTransactional;
//import com.dokidoki.bid.common.codes.RealTimeConstants;
//import com.dokidoki.bid.db.entity.AuctionRealtime;
//import com.dokidoki.bid.kafka.dto.KafkaAuctionEndDTO;
//import com.dokidoki.bid.kafka.service.KafkaBidProducer;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.redisson.api.*;
//import org.redisson.api.map.event.EntryEvent;
//import org.redisson.api.map.event.EntryExpiredListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.Optional;
//import java.util.concurrent.TimeUnit;
//
//@RequiredArgsConstructor
//@Component
//@Slf4j
//public class AuctionRealtimeRepositoryBucketImpl implements AuctionRealtimeRepository {
//
//    private final String keyPrefix = RealTimeConstants.mapKey;
//    private final KafkaBidProducer kafkaBidProducer;
//    private final RedissonClient redisson;
//
//    private RBucket<AuctionRealtime> getBucket(long auctionId) {
//        StringBuilder sb = new StringBuilder();
//        sb.append(keyPrefix).append(":").append(auctionId);
//        String key = sb.toString();
//        return redisson.getBucket(key);
//    }
//
//    @Override
//    public Optional<AuctionRealtime> findById(long auctionId) {
//        RBucket<AuctionRealtime> bucket = getBucket(auctionId);
//        AuctionRealtime auctionRealtime = bucket.get();
//        if (auctionRealtime == null) {
//            return Optional.empty();
//        } else {
//            return Optional.of(auctionRealtime);
//        }
//    }
//
//    @Override
//    @RTransactional
//    public void save(AuctionRealtime auctionRealtime) {
//        RBucket<AuctionRealtime> bucket = getBucket(auctionRealtime.getAuctionId());
//        bucket.set(auctionRealtime);
//    }
//
//    @Override
//    @RTransactional
//    public void save(AuctionRealtime auctionRealtime, long ttl, TimeUnit timeUnit) {
//        RBucket<AuctionRealtime> bucket = getBucket(auctionRealtime.getAuctionId());
//        bucket.set(auctionRealtime, ttl, timeUnit);
//        bucket.addListener(getExpiredObjectListener());
//    }
//
//    @Override
//    @RTransactional
//    public boolean deleteAll() {
//
////        return map.delete();
//        return false;
//    }
//
//
//    /**
//     * Redis 에 저장된 auctionRealtime 이 파기되는 순간 작동하는 메서드
//     * @return
//     */
//    private ExpiredObjectListener getExpiredObjectListener() {
//        ExpiredObjectListener expiredObjectListener = new ExpiredObjectListener() {
//            @Override
//            public void onExpired(String name) {
//
//            }
//        };
//        return expiredObjectListener;
//    }
//
//
//}
