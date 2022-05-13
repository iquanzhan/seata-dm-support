package com.chengxiaoxiao.order;

import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * OrderApplication
 *
 * @Description:
 * @Author: Cheng XiaoXiao  (🍊 ^_^ ^_^)
 * @Date: 2021-02-27 13:48
 */
@Slf4j
@EnableFeignClients
@EnableDiscoveryClient
@EnableAutoDataSourceProxy
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@MapperScan("com.chengxiaoxiao.order.mapper")
public class OrderApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
        log.info("订单服务启动成功...");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(OrderApplication.class);
    }
}
