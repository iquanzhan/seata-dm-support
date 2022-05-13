package com.chengxiaoxiao.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chengxiaoxiao.order.domain.CommonResult;
import com.chengxiaoxiao.order.domain.Order;
import com.chengxiaoxiao.order.mapper.OrderMapper;
import com.chengxiaoxiao.order.service.OrderService;
import com.chengxiaoxiao.order.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * OrderServiceImpl
 *
 * @Description:
 * @Author: Cheng XiaoXiao  (🍊 ^_^ ^_^)
 * @Date: 2021-02-27 13:55
 */
@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    StorageService storageService;

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public void createOrder(Order order) {
        log.info("------------开始新建订单------------");
        order.setUserId("001");
        order.setCount(2);
        this.baseMapper.insert(order);
        log.info("------------开始减库存------------");
        CommonResult decrease = storageService.decrease(order.getProductId(), order.getCount());


        log.info("------------修改订单状态------------");
        order.setStatus(1);
        this.baseMapper.updateById(order);
    }
}
