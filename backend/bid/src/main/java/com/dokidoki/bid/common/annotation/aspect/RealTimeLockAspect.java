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
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;


/**
 * 분산 락 처리를 하는 AOP
 */

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RealTimeLockAspect {

    private final RedissonClient redisson;

    @Around("@annotation(realTimeLock)")
    public Object realtimeLock(ProceedingJoinPoint pjp, RealTimeLock realTimeLock) throws Throwable {
        log.info("start realtime lock");
        RLock lock = redisson.getLock(LockInfo.REALTIME.getLockName());

        try {
            // [1] REALTIME 락 가져오도록 시도하기
            boolean res = lock.tryLock(LockInfo.REALTIME.getWaitTime(), LockInfo.REALTIME.getUnlockTime(), LockInfo.REALTIME.getTimeUnit());
            // [2] 락을 못가져오면 예외 처리
            if (!res) {
                throw new BusinessException("realtime lock 을 얻는데 실패했습니다.", ErrorCode.FAILURE_GET_REALTIME_LOCK);
            }
            // [3] 락을 가져오면 프로세스를 진행하고
            Object proceed = pjp.proceed();
            // [4] 다음으로 넘어간다.
            return proceed;
        } catch (Exception e) {
            throw e;
        } finally {
            // [*] 로직이 끝나기 전엔 락 해제하기
            if (lock.isLocked()) {
                log.info("isLocked");
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();
                } else {
                    log.info("최근 스레드가 lock을 들고 있지 않음");
                }
            }
        }
    }

}
