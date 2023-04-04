package com.dokidoki.notice.db.repository;

import com.dokidoki.notice.common.annotation.RTransactional;
import com.dokidoki.notice.common.codes.RealTimeConstants;
import com.dokidoki.notice.db.entity.AuctionRealtime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
@Slf4j
public class AuctionRealtimeRepositoryImpl implements AuctionRealtimeRepository {

    private String keyPrefix = RealTimeConstants.mapKey;
    private String expireKeyPrefix = RealTimeConstants.expireKey;
    private RedissonClient redisson;
    private RMap<Long, AuctionRealtime> map;

    @Autowired
    public void setAuctionRealtimeRepositoryImpl(RedissonClient redisson) {
        this.redisson = redisson;
        this.keyPrefix = RealTimeConstants.mapKey;
        this.map = redisson.getMap(keyPrefix);
    }

    private String getExpireKey(Long auctionId) {
        StringBuilder sb = new StringBuilder();
        sb.append(expireKeyPrefix).append(":").append(auctionId);
        return sb.toString();
    }

    @Override
    public Optional<AuctionRealtime> findById(Long auctionId) {
        AuctionRealtime auctionRealtime = map.get(auctionId);
        if (auctionRealtime == null) {
            return Optional.empty();
        } else {
            return Optional.of(auctionRealtime);
        }
    }

    @Override
    public boolean isExpired(Long auctionId) {
        RBucket bucket = redisson.getBucket(getExpireKey(auctionId));
        if (bucket.get() == null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void delete(Long auctionId) {
        RBucket bucket = redisson.getBucket(getExpireKey(auctionId));
        bucket.delete();
        AuctionRealtime auctionRealtime = findById(auctionId).get();
    }

    @Override
    @RTransactional
    public boolean deleteAll() {
        return map.delete();
    }

    /**
     * Bucket 으로 저장해둔 key 가 expire 되었는지를 확인하는 listener
     * @return
     */
}
