package com.dokidoki.bid.db;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;


@SuppressWarnings({"NonAsciiCharacters"})
@DisplayName("LeaderBoardService 클래스")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RedissonActivateTest {

    @Test
    public void Redisson_작동확인() throws IOException {

        Config config = Config.fromYAML(new File("src/main/resources/redisson.yaml"));
        RedissonClient redissonClient = Redisson.create(config);

        String key = "myKey";
        String value = "Hello Redis!";

        redissonClient.getBucket(key).set(value);
        String retreivedValue = (String) redissonClient.getBucket(key).get();

        assertEquals(value, retreivedValue);
        System.out.println(retreivedValue);




    }

}
