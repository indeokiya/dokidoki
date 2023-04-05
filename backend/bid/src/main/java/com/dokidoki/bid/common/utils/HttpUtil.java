package com.dokidoki.bid.common.utils;

import com.dokidoki.bid.api.response.DetailAuctionIngData;
import com.dokidoki.bid.api.response.DetailAuctionIngResp;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


@Component
@RequiredArgsConstructor
public class HttpUtil {

    @Bean
    private RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public DetailAuctionIngResp getAuctionInfo(Long auctionId) {
        String token = JWTUtil.getAccessToken(0L);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = new StringBuilder().append("https://j8a202.p.ssafy.io/api/auctions/in-progress/").append(auctionId).toString();

        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(url)
                .build(true);

        try {
            ResponseEntity<DetailAuctionIngResp> response = restTemplate()
                    .exchange(uriComponents.toString(), HttpMethod.GET, entity, DetailAuctionIngResp.class);

            System.out.println("Response status code: " + response.getStatusCodeValue());
            System.out.println("Response body: " + response.getBody());
            return response.getBody();
        } catch (HttpClientErrorException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("auction 서버 응답을 받지 못했습니다. 다시 시도해주세요");
        }
    }
}
