package com.dokidoki.auction.controller;

import com.dokidoki.auction.dto.request.LeaderboardRequest;
import com.dokidoki.auction.dto.response.CommonResponse;
import com.dokidoki.auction.service.LeaderboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<CommonResponse<Void>> createLeaderboard(@RequestBody LeaderboardRequest leaderboardRequest) {
        int resultCode = leaderboardService.createLeaderboard(leaderboardRequest);
        if (resultCode != 0) {
            return new ResponseEntity<>(
                    CommonResponse.of(400, "리더보드 등록에 실패했습니다.", null),
                    HttpStatus.BAD_REQUEST
            );
        }

        return new ResponseEntity<>(
                CommonResponse.of(201, "리더보드가 등록되었습니다.", null),
                HttpStatus.CREATED
        );
    }
}
