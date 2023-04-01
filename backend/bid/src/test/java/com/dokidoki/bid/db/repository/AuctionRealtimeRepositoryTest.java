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

import java.util.concurrent.TimeUnit;

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


    @BeforeEach
    public void 준비() {
        auctionRealtimeRepository.deleteAll();
        System.out.println("delete");
    }

    @Nested
    @DisplayName("CRU 테스트")
    class CREATE_READ_테스트 {

        AuctionRealtime auctionRealtime = AuctionRealtime
                .builder().auctionId(auctionId).highestPrice(highestPrice).priceSize(priceSize).build();
        AuctionRealtime auctionRealtime1 = AuctionRealtime
                .builder().auctionId(auctionId + 1).highestPrice(highestPrice).priceSize(priceSize).build();
        AuctionRealtime auctionRealtime2 = AuctionRealtime
                .builder().auctionId(auctionId + 2).highestPrice(highestPrice).priceSize(priceSize).build();

        @Test
        @DisplayName("경매가 잘 등록되고")
        public void 실시간_경매_CREATE() {

            // given & when
            auctionRealtimeRepository.save(auctionRealtime);

            // then
            AuctionRealtime getAuctionRealtime = auctionRealtimeRepository.findById(auctionId).get();
            assertEquals(auctionId, getAuctionRealtime.getAuctionId());
            assertEquals(highestPrice, getAuctionRealtime.getHighestPrice());
            assertEquals(priceSize, getAuctionRealtime.getPriceSize());

            System.out.println(getAuctionRealtime);
        }

        @Test
        @DisplayName("최고가가 잘 갱신되고")
        public void 실시간_경매_UPDATE_최고가() {
            // given
            auctionRealtimeRepository.save(auctionRealtime);

            // when
            AuctionRealtime updateAuctionRealtime = auctionRealtimeRepository.findById(auctionId).get();
            int highestPrice = updateAuctionRealtime.getHighestPrice();
            int priceSize = updateAuctionRealtime.getPriceSize();

            updateAuctionRealtime.updateHighestPrice();
            auctionRealtimeRepository.save(updateAuctionRealtime);

            // then
            AuctionRealtime getAuctionRealtime = auctionRealtimeRepository.findById(auctionId).get();
            assertEquals(highestPrice + priceSize, getAuctionRealtime.getHighestPrice());
            System.out.println(getAuctionRealtime);
        }

        @Test
        @DisplayName("경매 단위가 잘 수정되고")
        public void 실시간_경매_UPDATE_경매_단위() {

            // given
            auctionRealtimeRepository.save(auctionRealtime);
            int cPriceSize = 10_000;

            // when
            AuctionRealtime updateAuctionRealtime = auctionRealtimeRepository.findById(auctionId).get();
            updateAuctionRealtime.updatePriceSize(cPriceSize);
            auctionRealtimeRepository.save(updateAuctionRealtime);

            // then
            AuctionRealtime getAuctionRealtime = auctionRealtimeRepository.findById(auctionId).get();
            assertEquals(cPriceSize, getAuctionRealtime.getPriceSize());
            System.out.println(getAuctionRealtime);

        }

        @Test
        @DisplayName("TTL 을 설정하면 그 이후 삭제되고")
        public void 실시간_경매_TTL() throws InterruptedException {
            // given
            int ttl = 3;
            auctionRealtimeRepository.save(auctionRealtime, ttl, TimeUnit.SECONDS);
            
            // when & then
            // 바로 확인하면 존재하지만
            assert(! auctionRealtimeRepository.isExpired(auctionId));
            assert(auctionRealtimeRepository.findById(auctionId).isPresent());

            Thread.sleep((ttl + 2) * 1000);

//            assert(auctionRealtimeRepository.findById(auctionId).isEmpty());
            assert(auctionRealtimeRepository.isExpired(auctionId));

        }

        @Test
        @DisplayName("deleteAll을 하면 모두 다 삭제 가능하다.")
        public void 실시간_경매_deleteAll() {
            // given
            auctionRealtimeRepository.save(auctionRealtime);
            auctionRealtimeRepository.save(auctionRealtime1);
            auctionRealtimeRepository.save(auctionRealtime2);

            // when
            auctionRealtimeRepository.deleteAll();

            // then
            assert(auctionRealtimeRepository.findById(auctionId).isEmpty());
            assert(auctionRealtimeRepository.findById(auctionId + 1).isEmpty());
            assert(auctionRealtimeRepository.findById(auctionId + 2).isEmpty());

        }

    }




}