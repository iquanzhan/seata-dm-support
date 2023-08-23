package com.chengxiaoxiao.storage.controller;

import com.chengxiaoxiao.storage.domain.CommonResult;
import com.chengxiaoxiao.storage.domain.Storage;
import com.chengxiaoxiao.storage.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
public class StorageController {
    @Resource
    private StorageService storageService;

    /**
     * 扣减库存
     *
     * @param productId 商品ID
     * @param count     数量
     * @return 结果信息
     */
    @PostMapping("/storage/decrease")
    @GlobalTransactional(rollbackFor = Exception.class)
    CommonResult<?> decrease(@RequestParam("productId") String productId, @RequestParam("count") Integer count) {
        //模拟异常，全局回滚
        //int i = 10/0;
        Storage storage = new Storage();
        storage.setId(null);
        storage.setProductId(productId);
        storage.setTotal(1);
        storage.setUsed(1);
        storage.setResidue(1);
        storageService.save(storage);
        return new CommonResult<>(200, "成功");
    }
}
