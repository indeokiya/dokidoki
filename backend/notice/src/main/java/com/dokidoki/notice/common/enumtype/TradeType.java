package com.dokidoki.notice.common.enumtype;

import lombok.Getter;

@Getter
public enum TradeType {
    // 이용 정지
    PRISONER,

    // 수수료 깎고 훈방
    PENALTY,

    // 구매자
    BUYER,
    // 판매자
    SELLER,


}