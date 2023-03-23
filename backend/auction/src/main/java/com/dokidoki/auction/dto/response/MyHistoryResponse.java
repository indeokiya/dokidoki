package com.dokidoki.auction.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class MyHistoryResponse {
    private final List<MyHistoryInfo> histories;
    private final Boolean is_last;

    public MyHistoryResponse(List<MyHistoryInfo> histories, Boolean is_last) {
        this.histories = histories;
        this.is_last = is_last;
    }
}
