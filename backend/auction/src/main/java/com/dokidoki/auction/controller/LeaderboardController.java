package com.dokidoki.auction.controller;

import com.dokidoki.auction.common.BaseResponseBody;
import com.dokidoki.auction.dto.request.LeaderboardRequest;
import com.dokidoki.auction.service.LeaderboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/leaderboards")
public class LeaderboardController {
    private final LeaderboardService leaderboardService;

    @PostMapping("")
    public ResponseEntity<BaseResponseBody> createLeaderboard(@RequestBody LeaderboardRequest leaderboardRequest) {
        int resultCode = leaderboardService.createLeaderboard(leaderboardRequest);
        if (resultCode != 0)
            return ResponseEntity.status(400).body(BaseResponseBody.of("리더보드 등록에 실패했습니다."));

        return ResponseEntity.status(201).body(BaseResponseBody.of("리더보드가 등록되었습니다."));
    }
}
