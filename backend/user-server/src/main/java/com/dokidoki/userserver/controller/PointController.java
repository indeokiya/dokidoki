package com.dokidoki.userserver.controller;

import com.dokidoki.userserver.dto.response.SuperRichRes;
import com.dokidoki.userserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/points")
@RequiredArgsConstructor
public class PointController {

    private final UserService userService;
    @GetMapping("/super-rich")
    public ResponseEntity<List<SuperRichRes>> getSuperRichList(@RequestParam(defaultValue = "0") Integer page,
                                              @RequestParam(defaultValue = "10") Integer size){
        List<SuperRichRes> superRichList = userService.getSuperRichList(page, size);
        return ResponseEntity.ok(superRichList);
    }
}
