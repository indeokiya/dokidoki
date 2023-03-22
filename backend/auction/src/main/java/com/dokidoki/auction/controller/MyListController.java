package com.dokidoki.auction.controller;

import com.dokidoki.auction.common.JWTUtil;
import com.dokidoki.auction.dto.response.AuctionImageResponse;
import com.dokidoki.auction.dto.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/my-lists")
public class MyListController {

    private final JWTUtil jwtUtil;

//    @GetMapping("/bid")
//    public ResponseEntity<?> readAuctionImages(HttpServletRequest request) {
//
//        Long userId = jwtUtil.getUserId(request);
//
//
//    }
}
