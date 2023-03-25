package com.dokidoki.auction.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

@Getter
@RequiredArgsConstructor
@Slf4j
public class SimpleAuctionIngInfo {
    private final Long auction_id;

    private final String auction_title;
    private final String product_name;
    private final String category_name;
    private final String meeting_place;

    private final Integer offer_price;
    private final Integer cur_price;

    private final LocalDateTime start_time;
    private final Long remain_hours;
    private final Long remain_minutes;
    private final Long remain_seconds;

    private final Boolean is_my_interest;
    private final Boolean is_my_auction;

    private List<String> auction_image_urls;

    public SimpleAuctionIngInfo(
            SimpleAuctionIngInterface simpleAuctionIngInterface,
            List<String> auction_image_urls,
            Set<Long> interestsOfUser,
            Set<Long> salesOfUser) {
        this.auction_id = simpleAuctionIngInterface.getAuction_id();
        this.auction_title = simpleAuctionIngInterface.getAuction_title();
        this.product_name = simpleAuctionIngInterface.getProduct_name();
        this.category_name = simpleAuctionIngInterface.getCategory_name();
        this.meeting_place = simpleAuctionIngInterface.getMeeting_place();
        this.offer_price = simpleAuctionIngInterface.getOffer_price();
        this.cur_price = simpleAuctionIngInterface.getCur_price();

        // 특정 인물의 관심 경매 목록과 판매중인 목록을 가져 와 '내 관심' '내 물건' 설정
        this.is_my_interest = interestsOfUser.contains(this.auction_id);
        this.is_my_auction = salesOfUser.contains(simpleAuctionIngInterface.getSeller_id());

        this.auction_image_urls = auction_image_urls;

        // 남은 시간 계산
        long seconds = 0L;

        try {
            seconds = ChronoUnit.SECONDS.between(
                    LocalDateTime.now(), simpleAuctionIngInterface.getEnd_time()
            );
        } catch (Exception e) {
            log.error("SimpleAuctionIngInfo > 끝나는 시간이 존재하지 않습니다.");
        }

        this.start_time = simpleAuctionIngInterface.getStart_time();
        this.remain_hours = seconds / 3600;
        seconds %= 3600;
        this.remain_minutes = seconds / 60;
        seconds %= 60;
        this.remain_seconds = seconds;
    }
}