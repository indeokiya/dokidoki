package com.dokidoki.notice.api.service;

import com.dokidoki.notice.api.controller.WebSocketController;
import com.dokidoki.notice.api.response.SocketBidInfoResp;
import com.dokidoki.notice.api.response.SocketPriceSizeResp;
import com.dokidoki.notice.common.utils.PayloadUtil;
import com.dokidoki.notice.kafka.dto.KafkaAuctionUpdateDTO;
import com.dokidoki.notice.kafka.dto.KafkaBidDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuctionSocketService {

    private final WebSocketController webSocketController;
    private final PayloadUtil payloadUtil;

    public void auctionBid(KafkaBidDTO dto) {
        log.info("received KafkaBidDTO: {}", dto);
        Long auctionId = dto.getAuctionId();
        SocketBidInfoResp resp = SocketBidInfoResp.from(dto);
        webSocketController.sendAuctionInfo(auctionId, payloadUtil.getStringValue(resp));
    }

    public void auctionUpdate(KafkaAuctionUpdateDTO dto) {
        log.info("received KafkaAuctionUpdateDTO: {}", dto);
        Long auctionId = dto.getAuctionId();
        SocketPriceSizeResp resp = SocketPriceSizeResp.from(dto);
        webSocketController.sendAuctionInfo(auctionId, payloadUtil.getStringValue(resp));
    }






}
