package com.dokidoki.bid.db.repository;

import com.dokidoki.bid.db.entity.AuctionRealtime;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.redisson.api.RLiveObject;
import org.redisson.api.RLiveObjectService;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    @DisplayName("CRU 테스트")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class CRU_테스트 {

        @BeforeAll
        public void 준비() {
            auctionRealtimeRepository.deleteAll();
            System.out.println("delete");
        }
        
        @Nested
        @DisplayName("CRU 테스트")
        class CREATE_READ_테스트 {

            @Test
            public void 실시간_경매_CREATE() {

                AuctionRealtime auctionRealtime = AuctionRealtime
                        .builder().auctionId(auctionId).highestPrice(highestPrice).priceSize(priceSize).build();
                AuctionRealtime auctionRealtime1 = AuctionRealtime
                        .builder().auctionId(auctionId + 2).highestPrice(highestPrice).priceSize(priceSize).build();

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

                int changedHighestPrice = 10_000;

                // 수정 과정
                AuctionRealtime updateAuctionRealtime = auctionRealtimeRepository.findById(auctionId).get();
                updateAuctionRealtime.setHighestPrice(changedHighestPrice);
                auctionRealtimeRepository.save(updateAuctionRealtime);

                // 확인 과정
                AuctionRealtime getAuctionRealtime = auctionRealtimeRepository.findById(auctionId).get();
                assertEquals(changedHighestPrice, getAuctionRealtime.getHighestPrice());
                System.out.println(getAuctionRealtime);
            }
            
        }



    }

}