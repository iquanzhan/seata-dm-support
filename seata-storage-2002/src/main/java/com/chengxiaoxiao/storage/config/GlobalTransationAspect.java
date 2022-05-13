package com.chengxiaoxiao.storage.config;

import io.seata.core.context.RootContext;
import io.seata.core.exception.TransactionException;
import io.seata.tm.api.GlobalTransaction;
import io.seata.tm.api.GlobalTransactionContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 全局事务异常处理
 *
 * @Description:
 * @Author: Cheng XiaoXiao  (🍊 ^_^ ^_^)
 * @Date: 2021-02-27 16:13
 */
@Slf4j
@Component
@Aspect
public class GlobalTransationAspect {


    @Before("execution(* com.chengxiaoxiao.storage.controller.*.*(..))")
    public void before(JoinPoint joinPoint) throws TransactionException {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        log.info("拦截到需要分布式事务的方法," + method.getName());
        // 此处可用redis或者定时任务来获取一个key判断是否需要关闭分布式事务
        GlobalTransaction tx = GlobalTransactionContext.getCurrentOrCreate();
        tx.begin(300000, "test-client");
        log.info("创建分布式事务完毕" + tx.getXid());
    }

    @AfterThrowing(throwing = "e", pointcut = "execution(* com.chengxiaoxiao.storage.controller.*.*(..))")
    public void doRecoveryActions(Throwable e) throws TransactionException {
        log.info("方法执行异常:{}", e.getMessage());
        if (RootContext.getXID()!=null&&!"".equals(RootContext.getXID())) {
            GlobalTransactionContext.reload(RootContext.getXID()).rollback();
        }
    }
}
