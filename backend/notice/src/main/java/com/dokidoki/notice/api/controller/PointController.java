package com.dokidoki.notice.api.controller;

import com.dokidoki.notice.api.request.UpdatePointSocketReq;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/points")
public class PointController {

    private final WebSocketController webSocketController;

    // 포인트를 업데이트 하라는 요청을 받아
    // 소켓을 구독 중인 사람에게 송신
    @PostMapping("/realtime")
    public ResponseEntity<?> updatePoint(@RequestBody List<UpdatePointSocketReq> socketReqList){
        webSocketController.sendUpdatedPointInfo(socketReqList);
        return ResponseEntity.ok().build();
    }
}
