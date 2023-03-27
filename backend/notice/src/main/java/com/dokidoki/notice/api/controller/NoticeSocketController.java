package com.dokidoki.notice.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class NoticeSocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("ws/notice/{memberId}/success")
    public void sendSuccessNotice(@DestinationVariable long memberId) {

    }

    @MessageMapping("ws/notice/{memberId}/fail")
    public void sendFailureNotice(@DestinationVariable long memberId) {

    }

    @MessageMapping("ws/notice/{memberId}/complete")
    public void sendCompleteNotice(@DestinationVariable long memberId) {

    }

    @MessageMapping("ws/notice/{memberId}/outbid")
    public void sendOutBidNotice(@DestinationVariable long memberId) {

    }
}
