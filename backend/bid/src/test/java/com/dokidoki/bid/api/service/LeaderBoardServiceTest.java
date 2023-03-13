package com.dokidoki.bid.api.service;

import com.dokidoki.bid.db.entity.AuctionRealtime;
import com.dokidoki.bid.db.repository.AuctionRealtimeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings({"NonAsciiCharacters"})
@DisplayName("LeaderBoardService 클래스")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class LeaderBoardServiceTest {

    @Autowired LeaderBoardService leaderBoardService;
    @Autowired AuctionRealtimeRepository auctionRealtimeRepository;

    static int auctionId = 60_000;

    @BeforeEach
    public void 준비() {
        auctionRealtimeRepository.deleteAll();
        AuctionRealtime auctionRealtime = new AuctionRealtime(auctionId, 7_000_000, 10_000, 60*60*24);
        auctionRealtimeRepository.save(auctionRealtime);
    }

    @Nested
    @DisplayName("bid 메서드")
    class bid_메서드_테스트 {

        @Test
        public void 입찰실패_없는대상() {

        }

        @Test
        public void 입찰성공_새_사용자() {

        }

        @Test
        public void 입찰성공_이미_입찰한_사용자_갱신() {

        }

        @Test
        public void 제한수_유지() {

        }
    }
    
    @Nested
    @DisplayName("updatePriceSize 메서드")
    class updatePriceSize_메서드_테스트 {

        @Nested
        @DisplayName("수정할 대상이 주어질 때")
        class 수정할_대상_제공 {

            @BeforeEach
            public void 준비() {
                // 수정할 대상이 필요하겠지?
            }
            
            @Test
            @DisplayName("없는 경매면 에러를 내고,")
            public void 입찰단위_수정_실패_없는대상() {

            }

            @Test
            @DisplayName("경매 게시글 작성자가 아니면 에러를 내고,")
            public void 입찰단위_수정_실패_잘못된_접근() {
                
            }
            
            @Test
            @DisplayName("올바르게 접근하면 제대로 수정된다.")
            public void 입찰단위_수정_성공() {

            }

        }

        

    }

}