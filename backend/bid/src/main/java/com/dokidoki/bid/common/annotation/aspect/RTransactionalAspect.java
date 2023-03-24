package com.dokidoki.bid.common.annotation.aspect;

import com.dokidoki.bid.common.annotation.RTransactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RTransaction;
import org.redisson.api.RedissonClient;
import org.redisson.api.TransactionOptions;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RTransactionalAspect {

    private final RedissonClient redisson;

    @Around("@annotation(rTransactional)")
    public Object setRTransactional(ProceedingJoinPoint pjp, RTransactional rTransactional) throws Throwable {
        RTransaction transaction = redisson.createTransaction(TransactionOptions.defaults());
        try {
            // [1] 로직 실행
            Object proceed = pjp.proceed();
            // [2] - 1 Exception 이 발생하지 않는다면 commit
            transaction.commit();
            return proceed;
        } catch (Exception e) {
            // [2] - 2 Exception 이 발생한다면 rollback
            transaction.rollback();
            throw e;
        }
    }
}
