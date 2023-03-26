package com.dokidoki.bid.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AlertSocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("ws/alert/{memberId}/success")
    public void sendSuccessAlarm(@DestinationVariable long memberId) {

    }

    @MessageMapping("ws/alert/{memberId}/fail")
    public void sendFailureAlarm(@DestinationVariable long memberId) {

    }

    @MessageMapping("ws/alert/{memberId}/complete")
    public void sendCompleteAlarm(@DestinationVariable long memberId) {

    }

    @MessageMapping("ws/alert/{memberId}/outbid")
    public void sendOutBidAlarm(@DestinationVariable long memberId) {

    }
}
