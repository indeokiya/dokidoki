package com.dokidoki.auction.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class MyHistoryResp {
    private final List<MyHistoryInfo> histories;
    private final Boolean is_last;

    public MyHistoryResp(List<MyHistoryInfo> histories, Boolean is_last) {
        this.histories = histories;
        this.is_last = is_last;
    }
}
