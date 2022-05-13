package com.chengxiaoxiao.order.config;

import com.chengxiaoxiao.order.domain.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * GlobalExceptionHandler
 *
 * @Description:
 * @Author: Cheng XiaoXiao  (🍊 ^_^ ^_^)
 * @Date: 2021-02-27 16:03
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理系统逻辑产生的异常
     *
     * @param e 异常对象
     * @return 异常信息JSON
     */
    @ExceptionHandler(value = Exception.class)
    public CommonResult handleGlobalException(Exception e) {
        log.error(e.getMessage(), e);
        return new CommonResult(400,e.getMessage());
    }
}