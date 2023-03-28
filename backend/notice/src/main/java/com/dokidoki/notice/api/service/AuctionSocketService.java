package com.dokidoki.notice.api.service;

import com.dokidoki.notice.api.response.SocketBidInfoResp;
import com.dokidoki.notice.api.response.SocketPriceSizeResp;
import com.dokidoki.notice.kafka.dto.KafkaAuctionUpdateDTO;
import com.dokidoki.notice.kafka.dto.KafkaBidDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuctionSocketService {

    private final SimpMessageSendingOperations messagingTemplate;

    public void auctionBid(KafkaBidDTO dto) {
        log.info("received KafkaBidDTO: {}", dto);
        long auctionId = dto.getAuctionId();
        SocketBidInfoResp resp = SocketBidInfoResp.from(dto);
        messagingTemplate.convertAndSend("ws/auctions/"+auctionId+"/bid", resp);

    }

    public void auctionUpdate(KafkaAuctionUpdateDTO dto) {
        log.info("received KafkaAuctionUpdateDTO: {}", dto);
        long auctionId = dto.getAuctionId();
        SocketPriceSizeResp resp = SocketPriceSizeResp.from(dto);
        messagingTemplate.convertAndSend("ws/auctions/"+auctionId+"/price-size", resp);

    }




}
