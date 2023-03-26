package com.dokidoki.bid.api.service;

import com.dokidoki.bid.db.entity.AuctionRealtime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlertService {


    public void auctionSuccess(AuctionRealtime auctionRealtime) {

    }

    public void auctionFail(AuctionRealtime auctionRealtime) {

    }

    public void auctionComplete(AuctionRealtime auctionRealtime) {

    }

    public void auctionOutBid() {

    }
}
