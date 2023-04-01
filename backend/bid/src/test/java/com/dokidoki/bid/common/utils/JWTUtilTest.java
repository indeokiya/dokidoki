package com.dokidoki.bid.common.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("분산락 테스트 클래스")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class JWTUtilTest {

    @Test
    public void 토큰발급() {
        System.out.println(JWTUtil.getAccessToken(1250006L));
    }

}