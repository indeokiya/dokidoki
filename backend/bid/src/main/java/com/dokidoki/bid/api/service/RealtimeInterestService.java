package com.dokidoki.bid.api.service;

import com.dokidoki.bid.api.response.RealtimeInterestInfo;
import com.dokidoki.bid.api.response.RealtimeInterestResp;
import com.dokidoki.bid.common.annotation.RTransactional;
import com.dokidoki.bid.common.error.exception.InvalidValueException;
import com.dokidoki.bid.db.entity.enumtype.InterestType;
import com.dokidoki.bid.db.repository.AnalyzeRealtimeInterestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RealtimeInterestService {

    private final AnalyzeRealtimeInterestRepository analyzeRealtimeInterestRepository;

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

    public RealtimeInterestResp getRealtimeInterestRanking() {
        List<RealtimeInterestInfo> bidList = analyzeRealtimeInterestRepository.findAllByType(InterestType.bid);
        List<RealtimeInterestInfo> clickList = analyzeRealtimeInterestRepository.findAllByType(InterestType.click);

        List<RealtimeInterestInfo> sortedBidList = bidList.stream().sorted(Comparator.comparing(RealtimeInterestInfo::getCnt).reversed()).collect(Collectors.toList());
        List<RealtimeInterestInfo> sortedClickList = clickList.stream().sorted(Comparator.comparing(RealtimeInterestInfo::getCnt).reversed()).collect(Collectors.toList());

        RealtimeInterestResp resp = RealtimeInterestResp.builder()
                .bid(sortedBidList)
                .click(sortedClickList)
                .build();
        return resp;
    }

}
