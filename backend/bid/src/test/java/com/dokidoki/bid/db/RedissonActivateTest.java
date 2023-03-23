package com.dokidoki.bid.db;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


@SuppressWarnings({"NonAsciiCharacters"})
@DisplayName("LeaderBoardService 클래스")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RedissonActivateTest {

    @Autowired
    RedissonClient redissonClient;

    @Test
    public void Redisson_작동확인() {

        String key = "myKey";
        String value = "Hello Redis!";

        redissonClient.getBucket(key).set(value);
        String retreivedValue = (String) redissonClient.getBucket(key).get();

        assertEquals(value, retreivedValue);

    }

    @Test
    public void 테스트() {

    }

}
