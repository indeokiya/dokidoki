package com.dokidoki.notice.api.controller;

import com.dokidoki.notice.api.service.NoticeService;
import com.dokidoki.notice.common.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
        return ResponseEntity.ok(noticeService.getNoticeList(memberId));
    }
}
