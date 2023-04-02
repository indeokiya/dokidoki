package com.dokidoki.notice.api.controller;

import com.dokidoki.notice.api.service.NoticeService;
import com.dokidoki.notice.common.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.RouterFunctionDsl;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
@Slf4j
public class NoticeController {

    private final NoticeService noticeService;

    /**
     * 알림 내역 전체 가져오기
     * @return
     */
    @GetMapping("/")
    public ResponseEntity<?> getNotices(HttpServletRequest request) {
        long memberId = JWTUtil.getUserId(request);
        log.info("알림내역 조회. memberId: {}", memberId);
        return ResponseEntity.ok(noticeService.getAllNotice(memberId));
    }

    @PutMapping("/{noticeId}/read")
    public ResponseEntity<?> setRead(HttpServletRequest request, @PathVariable long noticeId) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status_code", "200");
        resultMap.put("message", "읽음_표시_성공");
        long memberId = JWTUtil.getUserId(request);
        log.info("읽음 처리. memberId: {}", memberId);
        noticeService.setIsRead(memberId, noticeId, true);
        return ResponseEntity.ok(resultMap);
    }

    @PutMapping("/{noticeId}/un-read")
    public ResponseEntity<?> setUnRead(HttpServletRequest request, @PathVariable long noticeId) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status_code", "200");
        resultMap.put("message", "읽음_취소_성공");
        long memberId = JWTUtil.getUserId(request);
        log.info("읽음 취소 처리. memberId: {}", memberId);
        noticeService.setIsRead(memberId, noticeId, false);
        return ResponseEntity.ok(resultMap);
    }
}
