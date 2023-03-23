package com.dokidoki.auction.controller;

import com.dokidoki.auction.common.BaseResponseBody;
import com.dokidoki.auction.dto.request.LeaderboardRequest;
import com.dokidoki.auction.service.LeaderboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/{auction_id}/leaderboards")
public class LeaderboardController {
    private final LeaderboardService leaderboardService;

    @PostMapping("")
    public ResponseEntity<BaseResponseBody> createLeaderboard(
            @PathVariable Long auction_id,
            @RequestBody LeaderboardRequest leaderboardRequest) {
        int resultCode = leaderboardService.createLeaderboard(auction_id, leaderboardRequest);
        if (resultCode != 0)
            return ResponseEntity.status(400).body(BaseResponseBody.of("리더보드 등록에 실패했습니다."));

        return ResponseEntity.status(201).body(BaseResponseBody.of("리더보드가 등록되었습니다."));
    }
}
