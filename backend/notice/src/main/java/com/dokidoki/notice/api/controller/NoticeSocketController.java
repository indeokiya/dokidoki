package com.dokidoki.notice.api.controller;

import com.dokidoki.notice.api.response.NoticeCompleteResp;
import com.dokidoki.notice.api.response.NoticeFailResp;
import com.dokidoki.notice.api.response.NoticeOutBidResp;
import com.dokidoki.notice.api.response.NoticeSuccessResp;
import com.dokidoki.notice.common.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@Slf4j
public class NoticeSocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("ws/notice/{memberId}/success")
    public void sendSuccessNotice(@DestinationVariable long memberId, @Payload NoticeSuccessResp resp) {
        simpMessagingTemplate.convertAndSend("/topic/notice/"+memberId, resp);
    }

    @MessageMapping("ws/notice/{memberId}/fail")
    public void sendFailureNotice(@DestinationVariable long memberId, @Payload NoticeFailResp resp) {
        simpMessagingTemplate.convertAndSend("/topic/notice/"+memberId, resp);

    }

    @MessageMapping("ws/notice/{memberId}/complete")
    public void sendCompleteNotice(@DestinationVariable long memberId, @Payload NoticeCompleteResp resp) {
        simpMessagingTemplate.convertAndSend("/topic/notice/"+memberId, resp);
    }

    @MessageMapping("ws/notice/{memberId}/outbid")
    public void sendOutBidNotice(@DestinationVariable long memberId, @Payload NoticeOutBidResp resp) {
        simpMessagingTemplate.convertAndSend("/topic/notice/"+memberId, resp);
    }
}
