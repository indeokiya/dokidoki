package com.dokidoki.bid.api.service;

import com.dokidoki.bid.api.response.DetailAuctionIngResp;
import com.dokidoki.bid.api.response.RealtimeInterestInfo;
import com.dokidoki.bid.api.response.RealtimeInterestResp;
import com.dokidoki.bid.common.annotation.RTransactional;
import com.dokidoki.bid.common.error.exception.InvalidValueException;
import com.dokidoki.bid.common.utils.HttpUtil;
import com.dokidoki.bid.db.entity.AuctionRealtime;
import com.dokidoki.bid.db.entity.enumtype.InterestType;
import com.dokidoki.bid.db.repository.AnalyzeRealtimeInterestRepository;
import com.dokidoki.bid.db.repository.AuctionRealtimeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RealtimeInterestService {

    private final AnalyzeRealtimeInterestRepository analyzeRealtimeInterestRepository;
    private final HttpUtil httpUtil;
    private final AuctionRealtimeRepository auctionRealtimeRepository;

    @RTransactional
    public void save(String kafkaInput) {
        log.info("kafkaInput: {}", kafkaInput);
        Long ttl = 30L;
        TimeUnit timeUnit = TimeUnit.SECONDS;
        Long auctionId;
        Long cnt;

        String[] inputSplit = kafkaInput.split(" ");
        switch(inputSplit[0]) {
            case "bid":
                auctionId = Long.parseLong(inputSplit[1]);
                cnt = Long.parseLong(inputSplit[2]);
                analyzeRealtimeInterestRepository.save(auctionId, cnt, InterestType.bid, ttl, timeUnit);
                break;
            case "click":
                auctionId = Long.parseLong(inputSplit[1]);
                cnt = Long.parseLong(inputSplit[2]);
                analyzeRealtimeInterestRepository.save(auctionId, cnt, InterestType.click, ttl, timeUnit);
                break;
            default:
                throw new InvalidValueException("알 수 없는 유형의 정보입니다.");
        }
    }

    private List<RealtimeInterestInfo> getSortedList(List<Long[]> repList) {
        List<Long[]> collect = repList.stream().sorted(((o1, o2) -> o2[1].compareTo(o1[1]))).collect(Collectors.toList());
        System.out.println(collect);
        List<RealtimeInterestInfo> resList = new ArrayList<>();
        int size = 0;
        for (int i = 0; i < collect.size(); i++) {
            if (size == 5) {
                break;
            }
            Long auctionId = collect.get(i)[0];
            Long cnt = collect.get(i)[1];
            DetailAuctionIngResp auctionInfo = httpUtil.getAuctionInfo(auctionId);
            if (auctionInfo == null) {
                continue;
            }
            AuctionRealtime auctionRealtime = auctionRealtimeRepository.findById(auctionId).get();
            RealtimeInterestInfo info = RealtimeInterestInfo.from(auctionRealtime, auctionInfo, cnt);
            resList.add(info);
            size++;
        }
        return resList;
    }

    public RealtimeInterestResp getRealtimeInterestRanking() {
        List<Long[]> bidRepList = analyzeRealtimeInterestRepository.findAllByType(InterestType.bid);
        List<Long[]> clickRepList = analyzeRealtimeInterestRepository.findAllByType(InterestType.click);
        
        List<RealtimeInterestInfo> bidList = getSortedList(bidRepList);
        List<RealtimeInterestInfo> clickList = getSortedList(clickRepList);

        RealtimeInterestResp resp = RealtimeInterestResp.builder()
                .bid(bidList)
                .click(clickList)
                .build();
        return resp;
    }

}
