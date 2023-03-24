package com.dokidoki.bid.common.annotation.aspect;

import com.dokidoki.bid.common.annotation.RealTimeLock;
import com.dokidoki.bid.common.codes.LockInfo;
import com.dokidoki.bid.common.error.exception.BusinessException;
import com.dokidoki.bid.common.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RFuture;
import org.redisson.api.RLock;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;


/**
 * 분산 락 처리를 하는 AOP
 */

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RealTimeLockAspect {

    private final RedissonClient redisson;
    private RLock lock;

    @Autowired
    public void setRealTimeLockAspect() {
        lock = redisson.getLock(LockInfo.REALTIME.getLockName());
    }

    @Around("@annotation(realTimeLock)")
    public Object realtimeLock(ProceedingJoinPoint pjp, RealTimeLock realTimeLock) throws Throwable {
        log.info("start realtime lock");

        // [1] REALTIME 락 가져오도록 시도하기
        boolean res = lock.tryLock(LockInfo.REALTIME.getWaitTime(), LockInfo.REALTIME.getUnlockTime(), LockInfo.REALTIME.getTimeUnit());
        if (res) {
            try {
                // [2] 로직 실행
                log.info("realtime lock 이 걸린 채로 로직 실행");
                Object proceed = pjp.proceed();
                return proceed;
            } finally {
                log.info("realtime lock 해제");
                lock.unlock();
            }
        } else {
            throw new BusinessException("realtime Lock 을 얻는 데 실패했습니다.", ErrorCode.FAILURE_GET_REALTIME_LOCK);
        }

    }


}
