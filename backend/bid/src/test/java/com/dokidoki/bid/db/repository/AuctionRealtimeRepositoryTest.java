package com.dokidoki.bid.db.repository;

import com.dokidoki.bid.db.entity.AuctionRealtime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.redisson.api.RLiveObject;
import org.redisson.api.RLiveObjectService;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AuctionRealtimeRepository 클래스")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class AuctionRealtimeRepositoryTest {

    @Autowired AuctionRealtimeRepository auctionRealtimeRepository;
    @Autowired RedissonClient redisson;

    static long auctionId = 1_000;
    static int highestPrice = 7_000_000;
    static int priceSize = 5_000;

    @Nested
    class CRU_테스트 {

        @BeforeEach
        public void 준비() {
            auctionRealtimeRepository.deleteAll();
        }

        @Test
        public void 실시간_경매_CREATE() {
            AuctionRealtime auctionRealtime = AuctionRealtime.of(auctionId, highestPrice, priceSize);
            AuctionRealtime auctionRealtime1 = AuctionRealtime.of(auctionId + 2, highestPrice, priceSize);

            auctionRealtimeRepository.save(auctionRealtime);
            auctionRealtimeRepository.save(auctionRealtime1);

            AuctionRealtime getAuctionRealtime = auctionRealtimeRepository.findById(auctionId).get();
            assertEquals(auctionId, getAuctionRealtime.getAuctionId());
            assertEquals(highestPrice, getAuctionRealtime.getHighestPrice());
            assertEquals(priceSize, getAuctionRealtime.getPriceSize());

            System.out.println(getAuctionRealtime);
        }

        @Test
        public void 실시간_경매_UPDATE() {
            RLiveObjectService liveObjectService = redisson.getLiveObjectService();

            AuctionRealtime auctionRealtime = AuctionRealtime.of(auctionId, highestPrice, priceSize);
            auctionRealtimeRepository.save(auctionRealtime);

            AuctionRealtime updateAuctionRealtime = auctionRealtimeRepository.findById(auctionId).get();

            RLiveObject rLiveObject = liveObjectService.asLiveObject(updateAuctionRealtime);
            System.out.println(rLiveObject.getCodec());
            System.out.println(rLiveObject.getName());
            System.out.println(rLiveObject.getClass());
            Object liveObjectId = rLiveObject.getLiveObjectId();
            System.out.println(liveObjectId);

            updateAuctionRealtime.setHighestPrice(10_000);
            redisson.getBucket(rLiveObject.getName()).set(updateAuctionRealtime);
//            redisson.shutdown();


//            auctionRealtimeRepository.save(auctionRealtime);
            AuctionRealtime getAuctionRealtime2 = auctionRealtimeRepository.findById(auctionId).get();
            System.out.println(getAuctionRealtime2);

        }

    }

}