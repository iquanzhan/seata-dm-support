package com.chengxiaoxiao.order.config;

import com.chengxiaoxiao.order.domain.CommonResult;
import io.seata.core.exception.TransactionException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

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
public class FeignClientAspect {


    @AfterReturning(value = "@within(org.springframework.cloud.openfeign.FeignClient) *(..)",returning ="returnVal" )
    public void before(JoinPoint joinPoint,Object returnVal) throws TransactionException {
        if(returnVal instanceof CommonResult){
            CommonResult returnVal1 = (CommonResult) returnVal;
            if (returnVal1.getCode() != 200) {
                log.error("全局事务异常处理，异常信息：{}", returnVal1.getMessage());
                throw new RuntimeException(returnVal1.getMessage());
            }
        }
    }
}
