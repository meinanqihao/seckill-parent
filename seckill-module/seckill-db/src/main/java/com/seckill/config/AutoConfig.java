package com.seckill.config;

import com.seckill.util.IdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*****
 * @Author: http://www.itheima.com
 * @Project: seckill
 * @Description: com.seckill.config.AutoConfig


 ****/
@Configuration
public class AutoConfig {

    /***
     * 创建IdWorker
     * @return
     */
    @Bean
    public IdWorker idWorker(){
        return new IdWorker();
    }

}
