package com.seckill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

/*****
 * @Author: http://www.itheima.com
 * @Project: seckill
 * @Description: com.seckill.GoodsApplication


 ****/
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.seckill.goods.feign","com.seckill.message.feign"})
@MapperScan(basePackages = "com.seckill.order.dao")
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
    }

}



