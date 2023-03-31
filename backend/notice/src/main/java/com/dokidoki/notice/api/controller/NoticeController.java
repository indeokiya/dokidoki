package com.dokidoki.notice.api.controller;

import com.dokidoki.notice.api.service.NoticeService;
import com.dokidoki.notice.common.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.RouterFunctionDsl;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class NoticeController {

    private final NoticeService noticeService;

    /**
     * 알림 내역 전체 가져오기
     * @return
     */
    @GetMapping("/")
    public ResponseEntity<?> getNotices(HttpServletRequest request) {
        long memberId = JWTUtil.getUserId(request);
        return ResponseEntity.ok(noticeService.getAllNotice(memberId));
    }

    @PutMapping("/{noticeId}/read")
    public ResponseEntity<?> setRead(HttpServletRequest request, @PathVariable long noticeId) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status_code", "200");
        resultMap.put("message", "읽음_표시_성공");
        long memberId = JWTUtil.getUserId(request);
        noticeService.setIsRead(memberId, noticeId, true);
        return ResponseEntity.ok(resultMap);
    }

    @PutMapping("/{noticeId}/un-read")
    public ResponseEntity<?> setUnRead(HttpServletRequest request, @PathVariable long noticeId) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status_code", "200");
        resultMap.put("message", "읽음_취소_성공");
        long memberId = JWTUtil.getUserId(request);
        noticeService.setIsRead(memberId, noticeId, false);
        return ResponseEntity.ok(resultMap);
    }
}
