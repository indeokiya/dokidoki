package com.dokidoki.bid.api.service;

import com.dokidoki.bid.common.codes.LockInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings({"NonAsciiCharacters"})
@DisplayName("분산락 테스트 클래스")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DistributedLockBasicTest {

    @Autowired RedissonClient redisson;

    @Test
    public void distributedLock_test_success() throws InterruptedException {
        RLock lock = redisson.getLock(LockInfo.REALTIME.getLockName());

        System.out.println("start realtime lock");
        System.out.println("lock 걸기 시도");
        boolean res = lock.tryLock(LockInfo.REALTIME.getWaitTime(), LockInfo.REALTIME.getUnlockTime(), LockInfo.REALTIME.getTimeUnit());
        if (res) {
            try {
                System.out.println("로직 진행");
            } finally {
                lock.unlock();
            }
        } else {
            throw new RuntimeException("realtime Lock을 얻는 데 실패했습니다.");
        }
    }

    @Test
    public void distributedLock_test_error() throws InterruptedException {
        RLock lock = redisson.getLock(LockInfo.REALTIME.getLockName());

        System.out.println("start realtime lock");
        System.out.println("lock 걸기 시도");
        boolean res = lock.tryLock(LockInfo.REALTIME.getWaitTime(), LockInfo.REALTIME.getUnlockTime(), LockInfo.REALTIME.getTimeUnit());
        if (res) {
            try {
                System.out.println("로직 진행");
                throw new RuntimeException("에러 발생");
            } catch (RuntimeException e) {
                System.out.println("에러 캐치");
                throw e;
            } finally {
                System.out.println("락 해제");
                lock.unlock();
            }
        } else {
            throw new RuntimeException("realtime Lock을 얻는 데 실패했습니다.");
        }
    }

}
