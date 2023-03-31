package com.dokidoki.auction.dto.response;

import com.dokidoki.auction.dto.db.MostSaleProductInterface;
import lombok.Getter;

@Getter
public class MostSaleProductContent {
    private final String product_name;
    private final Integer sale_cnt;

    public MostSaleProductContent(MostSaleProductInterface mostSaleProductInterface) {
        this.product_name = mostSaleProductInterface.getName();
        this.sale_cnt = mostSaleProductInterface.getSale_cnt();
    }
}
