package com.chengxiaoxiao.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chengxiaoxiao.order.domain.Order;

/**
 * 订单服务
 *
 * @Description:
 * @Author: Cheng XiaoXiao  (🍊 ^_^ ^_^)
 * @Date: 2021-02-27 13:55
 */
public interface OrderService extends IService<Order> {
    void createOrder(Order order);
}
