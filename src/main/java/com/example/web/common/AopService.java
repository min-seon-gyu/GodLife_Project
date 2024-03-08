package com.example.web.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AopService {

    private final RedissonClient redissonClient;
    private final AopForTransaction aopForTransaction;
    private static final String REDISSON_LOCK = "LOCK:TEMP";

    @Around("@annotation(com.example.web.common.ExeTimer)")
    public void AssumeExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        StopWatch stopWatch = new StopWatch();

        stopWatch.start();
        joinPoint.proceed();
        stopWatch.stop();

        long totalTimeMillis = stopWatch.getTotalTimeMillis();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getName();

        log.info("실행 메서드: {}, 실행시간 = {}ms", methodName, totalTimeMillis);
    }

    @Around("@annotation(com.example.web.common.RedisRock)")
    public Object redissonLock(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RedisRock redisRock = method.getAnnotation(RedisRock.class);
        RLock rLock = redissonClient.getLock(REDISSON_LOCK);

        try {
            boolean available = rLock.tryLock(redisRock.waitTime(), redisRock.leaseTime(), redisRock.timeUnit());
            if (!available) {
                return false;
            }
            return aopForTransaction.proceed(joinPoint);
        } catch (InterruptedException  e) {
            throw new InterruptedException();
        } finally {
            try{
                rLock.unlock();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
