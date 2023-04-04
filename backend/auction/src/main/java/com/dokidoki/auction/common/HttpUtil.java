package com.dokidoki.auction.common;

import com.dokidoki.auction.dto.response.BiddingResp;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class HttpUtil {
    private final RestTemplate restTemplate;

    public BiddingResp getAuctionIdList(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl("https://j8a202.p.ssafy.io/api/bids/my-infos/bids")
                .build(true);

        try {
            ResponseEntity<Long[]> response = restTemplate
                    .exchange(uriComponents.toString(), HttpMethod.GET, entity, Long[].class);
            return BiddingResp.builder()
                    .auctionIdList(response.getBody())
                    .build();
        } catch (HttpClientErrorException e) {
            System.out.println(e.getMessage());
            return BiddingResp.builder()
                    .message(e.getResponseBodyAsString())
                    .build();
        }
    }
}
