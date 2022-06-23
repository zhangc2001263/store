package com.zc.store.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimerAspect {

    @Around("execution(* com.zc.store.service.impl.*.*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        // 记录起始时间
        long start = System.currentTimeMillis();
        // 执行你的业务方法
        Object result = pjp.proceed();
        // 记录结束时间
        long end = System.currentTimeMillis();
        // 计算耗时
        System.err.println("耗时：" + (end - start) + "ms.");
        // 返回连接点方法的返回值
        return result;
    }
}
