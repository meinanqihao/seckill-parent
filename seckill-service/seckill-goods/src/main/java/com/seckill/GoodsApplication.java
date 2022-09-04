package com.seckill;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seckill.util.RedisConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import tk.mybatis.spring.annotation.MapperScan;

/*****
 * @Author: http://www.itheima.com
 * @Project: seckill
 * @Description: com.seckill.GoodsApplication
 ****/
@SpringBootApplication(exclude = ElasticsearchDataAutoConfiguration.class)
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.seckill.search.feign","com.seckill.page.feign"})
@MapperScan(basePackages = {"com.seckill.goods.dao"})
public class GoodsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoodsApplication.class,args);
    }

}


