package com.dokidoki.bid.api.controller;

import com.dokidoki.bid.api.service.RealtimeInterestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("analyze")
public class AnalyzeController {

    private final RealtimeInterestService realtimeInterestService;

    @GetMapping("realtime-interest")
    public ResponseEntity<?> getRealtimeInterest() {
        return ResponseEntity.ok(realtimeInterestService.getRealtimeInterestRanking());
    }
}
