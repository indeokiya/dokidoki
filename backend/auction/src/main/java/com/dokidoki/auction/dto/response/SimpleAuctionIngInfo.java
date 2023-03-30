package com.dokidoki.auction.dto.response;

import com.dokidoki.auction.domain.entity.AuctionIngEntity;
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
    private final Integer price_size;

    private final LocalDateTime start_time;
    private final LocalDateTime end_time;
    private final Long remain_hours;
    private final Long remain_minutes;
    private final Long remain_seconds;

    private final Boolean is_my_interest;
    private final Boolean is_my_auction;

    private String auction_image_url;

    public SimpleAuctionIngInfo(
            AuctionIngEntity auctionIngEntity,
            String auction_image_url,
            Set<Long> interestsOfUser,
            Set<Long> salesOfUser) {
        this.auction_id = auctionIngEntity.getId();  // 경매 번호
        this.auction_title = auctionIngEntity.getTitle();  // 경매 제목
        this.product_name = auctionIngEntity.getProductEntity().getName();  // 제품명
        this.category_name = auctionIngEntity.getProductEntity().getCategoryEntity().getCategoryName();  // 분류명
        this.meeting_place = auctionIngEntity.getMeetingPlace();  // 거래 장소
        this.offer_price = auctionIngEntity.getOfferPrice();  // 시작가
        this.price_size = auctionIngEntity.getPriceSize();  // 경매 단위

        // 최고가
        Integer cur_price = auctionIngEntity.getHighestPrice();
        if (cur_price == null)
            cur_price = this.offer_price;
        this.cur_price = cur_price;

        // 특정 인물의 관심 경매 목록과 판매중인 목록을 가져 와 '내 관심' '내 물건' 설정
        this.is_my_interest = interestsOfUser.contains(this.auction_id);
        this.is_my_auction = salesOfUser.contains(auctionIngEntity.getSeller().getId());

        this.auction_image_url = auction_image_url;

        this.start_time = auctionIngEntity.getStartTime();
        this.end_time = auctionIngEntity.getEndAt();

        // 남은 시간 계산
        long seconds = 0L;
        try {
            seconds = ChronoUnit.SECONDS.between(
                    LocalDateTime.now(), auctionIngEntity.getEndAt()
            );
        } catch (Exception e) {
            log.error("SimpleAuctionIngInfo > 끝나는 시간이 존재하지 않습니다.");
        }

        this.remain_hours = seconds / 3600;
        seconds %= 3600;
        this.remain_minutes = seconds / 60;
        seconds %= 60;
        this.remain_seconds = seconds;
    }
}