package com.dokidoki.notice.api.service;

import com.dokidoki.notice.api.controller.AuctionSocketController;
import com.dokidoki.notice.api.response.SocketBidInfoResp;
import com.dokidoki.notice.api.response.SocketPriceSizeResp;
import com.dokidoki.notice.kafka.dto.KafkaAuctionUpdateDTO;
import com.dokidoki.notice.kafka.dto.KafkaBidDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuctionSocketService {

    private final SimpMessagingTemplate messagingTemplate;
    private final AuctionSocketController auctionSocketController;

    public void auctionBid(KafkaBidDTO dto) {
        log.info("received KafkaBidDTO: {}", dto);
        long auctionId = dto.getAuctionId();
        SocketBidInfoResp resp = SocketBidInfoResp.from(dto);
//        messagingTemplate.convertAndSend("ws/auctions/"+auctionId+"/bid", getStringValue(resp));
        auctionSocketController.getBid(auctionId, getStringValue(resp));
    }

    public void auctionUpdate(KafkaAuctionUpdateDTO dto) {
        log.info("received KafkaAuctionUpdateDTO: {}", dto);
        long auctionId = dto.getAuctionId();
        SocketPriceSizeResp resp = SocketPriceSizeResp.from(dto);
//        messagingTemplate.convertAndSend("ws/auctions/"+auctionId+"/price-size", getStringValue(resp));
        auctionSocketController.getPriceSize(auctionId, getStringValue(resp));
    }

    public String getStringValue(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        String s = null;
        try {
            s = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(s);
        return s;
    }





}
