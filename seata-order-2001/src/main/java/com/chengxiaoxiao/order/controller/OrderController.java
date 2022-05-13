package com.chengxiaoxiao.order.controller;

import com.chengxiaoxiao.order.domain.CommonResult;
import com.chengxiaoxiao.order.domain.Order;
import com.chengxiaoxiao.order.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * OrderController
 *
 * @Description:
 * @Author: Cheng XiaoXiao  (🍊 ^_^ ^_^)
 * @Date: 2021-02-27 14:23
 */
@RestController
public class OrderController {
    @Resource
    private OrderService orderService;
    @GetMapping("/order/create")
    public CommonResult createOrder(Order order){

        orderService.createOrder(order);
        return new CommonResult(200,"订单创建成功");
    }
}
