package com.dokidoki.bid.api.service;

import com.dokidoki.bid.api.request.AuctionBidReq;
import com.dokidoki.bid.api.request.AuctionUpdatePriceSizeReq;
import com.dokidoki.bid.api.response.AuctionInitialInfoResp;
import com.dokidoki.bid.api.response.LeaderBoardMemberInfo;
import com.dokidoki.bid.api.response.LeaderBoardMemberResp;
import com.dokidoki.bid.common.codes.RealTimeConstants;
import com.dokidoki.bid.common.error.exception.BusinessException;
import com.dokidoki.bid.common.error.exception.ErrorCode;
import com.dokidoki.bid.common.error.exception.InvalidValueException;
import com.dokidoki.bid.db.entity.AuctionRealtime;
import com.dokidoki.bid.db.repository.AuctionRealtimeLeaderBoardRepository;
import com.dokidoki.bid.db.repository.AuctionRealtimeRepository;
import com.dokidoki.bid.kafka.dto.KafkaAuctionRegisterDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.redisson.api.RedissonClient;
import org.redisson.client.protocol.ScoredEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings({"NonAsciiCharacters"})
@DisplayName("LeaderBoardService 클래스")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class BiddingServiceTest {

    @Autowired BiddingService biddingService;
    @Autowired AuctionInfoService auctionInfoService;
    @Autowired AuctionRealtimeRepository auctionRealtimeRepository;
    @Autowired AuctionRealtimeLeaderBoardRepository auctionRealtimeLeaderBoardRepository;
    @Autowired RedissonClient redisson;

    static long sellerId = 20_000;
    static long[] memberIds = {30_000, 30_001};
    final static long auctionId = 70_000;
    final static long highestPrice = 7_000_000;
    final static long priceSize = 10_000;
    final static String[] names = {"사용자0", "사용자1"};
    final static String[] emails = {"user0@gmail.com", "user1@gmail.com"};

    @BeforeEach
    public void 준비() {
        // 기존 DB 내용 삭제
        auctionRealtimeRepository.deleteAll();

        // 경매 등록
        KafkaAuctionRegisterDTO dto = KafkaAuctionRegisterDTO.builder()
                .auctionId(auctionId).offerPrice(highestPrice).priceSize(priceSize)
                .ttl(20L).sellerId(sellerId)
                .productName("싸트북").productId(1L).build();
        biddingService.registerAuctionInfo(dto);

        System.out.println("sellerId: "+ sellerId);
        System.out.println(Arrays.toString(memberIds));

    }

    @Test
    @DisplayName("경매 초기화 등록 정보 확인")
    public void 경매_초기화_등록_정보_확인() {
        AuctionRealtime auctionRealtime = auctionRealtimeRepository.findById(auctionId).get();
        System.out.println(auctionRealtime);
        System.out.println(auctionInfoService.getInitialLeaderBoard(auctionId));

    }


    @Nested
    @DisplayName("bid 메서드")
    class bid_메서드_테스트 {

        AuctionBidReq[] reqs = new AuctionBidReq[2];

        @BeforeEach
        public void 실시간_경매_초기화() {
            for (int i = 0; i < 2; i ++) {
                reqs[i] = AuctionBidReq.builder()
                        .name(names[i])
                        .currentHighestPrice(highestPrice + priceSize * i)
                        .currentPriceSize(priceSize).build();
            }
        }

        @Nested
        @DisplayName("입찰 할 때")
        class 입찰자_제공 {

            AuctionBidReq wrongHighestPriceReq = AuctionBidReq.builder()
                    .name(names[0])
                    .currentHighestPrice(highestPrice + 2 * priceSize)
                    .currentPriceSize(priceSize).build();

            AuctionBidReq wrongPriceSizeReq = AuctionBidReq.builder()
                    .name(names[0])
                    .currentHighestPrice(highestPrice)
                    .currentPriceSize(priceSize * 2).build();

            @BeforeEach
            public void 준비() {
                auctionRealtimeRepository.deleteAll();
                AuctionRealtime auctionRealtime = AuctionRealtime.builder()
                        .auctionId(auctionId).highestPrice(highestPrice).priceSize(priceSize).build();

                auctionRealtimeRepository.save(auctionRealtime);

                auctionRealtimeLeaderBoardRepository.deleteAll(auctionId);


            }

            @Test
            @DisplayName("없는 경매면 에러를 낸다.")
            public void 입찰실패_없는_경매() {
                assertThrows(InvalidValueException.class, () -> biddingService.bid(auctionId + 2, reqs[0], sellerId));
            }

            @Test
            @DisplayName("경매 단위가 일치하지 않으면 Business 에러를 낸다.")
            public void 입찰실패_경매단위_불일치() {
                BusinessException exception = assertThrows(BusinessException.class, () -> {
                    biddingService.bid(auctionId, wrongPriceSizeReq, sellerId);
                });

                assertEquals(ErrorCode.DIFFERENT_PRICE_SIZE, exception.getErrorCode());

            }

            @Test
            @DisplayName("현재 가격이 일치하지 않으면 Business 에러를 낸다.")
            public void 입찰실패_현재가격_불일치() {
                BusinessException exception = assertThrows(BusinessException.class, () -> {
                    biddingService.bid(auctionId, wrongHighestPriceReq, sellerId);
                });

                assertEquals(ErrorCode.DIFFERENT_HIGHEST_PRICE, exception.getErrorCode());
            }

            @Test
            @DisplayName("모든 조건을 통과하면 성공적으로 입찰이 된다.")
            public void 입찰성공() throws InterruptedException {
                biddingService.bid(auctionId, reqs[0], memberIds[0]);
                AuctionRealtime auctionRealtime = auctionRealtimeRepository.findById(auctionId).get();

                // auctionRealtime 값 갱신 확인
                assertEquals(highestPrice + priceSize, auctionRealtime.getHighestPrice());

                // 랭킹 갱신 확인
                AuctionInitialInfoResp initialInfo = auctionInfoService.getInitialInfo(auctionId);
                List<LeaderBoardMemberResp> leaderBoard = initialInfo.getLeaderBoard();
                System.out.println(leaderBoard);
                assertEquals(1, leaderBoard.size());

                Collection<ScoredEntry<LeaderBoardMemberInfo>> leaderBoardMemberInfos = auctionRealtimeLeaderBoardRepository.getAll(auctionId);

                assertEquals(1, leaderBoardMemberInfos.size());

                for (ScoredEntry<LeaderBoardMemberInfo> info: leaderBoardMemberInfos) {
                    int bidPrice = info.getScore().intValue();
                    assertEquals(highestPrice + priceSize, bidPrice);
                    assertEquals(memberIds[0], info.getValue().getMemberId());
                    System.out.println(info);
                }
            }

            @Test
            @DisplayName("새로운 입찰이 일어나면, 가장 위의 정보가 그 사람의 입찰 정보로 갱신된다.")
            public void 입찰성공_사용자_갱신() throws InterruptedException {
                biddingService.bid(auctionId, reqs[0], memberIds[0]);
                biddingService.bid(auctionId, reqs[1], memberIds[1]);

                System.out.println(auctionInfoService.getInitialInfo(auctionId).getLeaderBoard());

                // 랭킹 갱신 확인
                Collection<ScoredEntry<LeaderBoardMemberInfo>> leaderBoardMemberInfos = auctionRealtimeLeaderBoardRepository.getAll(auctionId);

                for (ScoredEntry<LeaderBoardMemberInfo> info: leaderBoardMemberInfos) {
                    int bidPrice = info.getScore().intValue();
                    assertEquals(highestPrice + priceSize * 2 , bidPrice);
                    assertEquals( memberIds[1], info.getValue().getMemberId());
                    System.out.println(info);
                    break;
                }
            }

            @Test
            @DisplayName("제한된 수 이상으로 입찰될 경우, 그 개수만큼 최근 입찰된 결과만 sorted Set 에 남는다.")
            public void 제한수_유지() throws InterruptedException {
                int limit = RealTimeConstants.leaderboardLimit;

                for (int i = 0; i < limit + 2; i++) {
                    AuctionBidReq req = AuctionBidReq.builder()
                            .name(names[0])
                            .currentHighestPrice(highestPrice + i * priceSize)
                            .currentPriceSize(priceSize).build();
                    biddingService.bid(auctionId, req, memberIds[0]);
                }
                Collection<ScoredEntry<LeaderBoardMemberInfo>> leaderboardInfos = auctionRealtimeLeaderBoardRepository.getAll(auctionId);
                System.out.println(auctionRealtimeLeaderBoardRepository.getWinner(auctionId));
                System.out.println(auctionInfoService.getInitialInfo(auctionId));
                assertEquals(limit, leaderboardInfos.size());
                System.out.println(limit);
                System.out.println(leaderboardInfos.size());

            }
        }
    }

    @Nested
    @DisplayName("updatePriceSize 메서드")
    class updatePriceSize_메서드_테스트 {

        AuctionUpdatePriceSizeReq req;

        @BeforeEach
        public void 준비() {
            req = AuctionUpdatePriceSizeReq.builder()
                    .priceSize(5_000L).build();
        }


        @Nested
        @DisplayName("수정할 대상이 주어질 때")
        class 수정할_대상_제공 {

            @Test
            @DisplayName("없는 경매면 에러를 낸다.")
            public void 입찰단위_수정_실패_없는대상() {
//                assertThrows(InvalidValueException.class, () -> biddingService.updatePriceSize(auctionId + 20, req, sellerId));
            }

            @Test
            @DisplayName("경매 게시글 작성자가 아니면 에러를 낸다.")
            public void 입찰단위_수정_실패_잘못된_접근() {
                BusinessException exception = assertThrows(BusinessException.class, () -> {
//                    biddingService.updatePriceSize(auctionId, req, sellerId + 30);
                });
                assertEquals(ErrorCode.BUSINESS_EXCEPTION_ERROR, exception.getErrorCode());
            }

            @Test
            @DisplayName("올바르게 접근하면 제대로 수정된다.")
            public void 입찰단위_수정_성공() {
                System.out.println("auctionId:"+ auctionId);
//                biddingService.updatePriceSize(auctionId, req, sellerId);
                AuctionRealtime auctionRealtime = auctionRealtimeRepository.findById(auctionId).get();
                assertEquals(req.getPriceSize(), auctionRealtime.getPriceSize());
            }
        }
    }
}