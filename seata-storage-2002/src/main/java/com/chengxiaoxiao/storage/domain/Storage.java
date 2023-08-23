package com.chengxiaoxiao.storage.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Storage
 *
 * @Description:
 * @Author: Cheng XiaoXiao  (🍊 ^_^ ^_^)
 * @Date: 2021-02-27 15:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("STORAGE")
public class Storage {
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;
    private String productId;
    private Integer total;
    private Integer used;
    private Integer residue;

}
