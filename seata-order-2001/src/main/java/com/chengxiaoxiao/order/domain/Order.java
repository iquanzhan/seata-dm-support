package com.chengxiaoxiao.order.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Order
 *
 * @Description:
 * @Author: Cheng XiaoXiao  (🍊 ^_^ ^_^)
 * @Date: 2021-02-27 13:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ORDER")
public class Order {
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 商品ID
     */
    private String productId;
    /**
     * 数量
     */
    private Integer count;
    /**
     * 金额
     */
    private Double money;
    /**
     * 订单状态
     */
    private Integer status;
}
