package com.seckill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/*****
 * @Author: http://www.itheima.com
 * @Description: com.seckill.CanalApplication
 ****/
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.seckill.page.feign","com.seckill.goods.feign"})
public class CanalApplication {

    public static void main(String[] args) {
        SpringApplication.run(CanalApplication.class,args);
    }
}
