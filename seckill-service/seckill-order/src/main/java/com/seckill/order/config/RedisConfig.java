package com.seckill.order.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.IOException;

/*****
 * @Author: http://www.itheima.com
 * @Project: seckill
 * @Description: com.seckill.goods.config.RedissonConfig
 ****/
@Configuration
public class RedisConfig {

    /****
     * RedissonClient
     */
    @Bean
    public RedissonClient redisClient() throws IOException {
        //1.加载配置文件
        ClassPathResource resource = new ClassPathResource("redisson.yml");
        //2.解析配置文件
        Config config = Config.fromYAML(resource.getInputStream());
        //3.创建RedissonClient
        return Redisson.create(config);
    }


    /*****
     * RedissonConnectionFactory
     */
    @Bean
    public RedissonConnectionFactory redissonConnectionFactory(RedissonClient redisClient){
        return new RedissonConnectionFactory(redisClient);
    }


    /***
     * 模板操作对象序列化设置
     * @param redissonConnectionFactory
     * @return
     */
    @Bean("redisTemplate")
    public RedisTemplate getRedisTemplate(RedisConnectionFactory redissonConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redissonConnectionFactory);
        redisTemplate.setValueSerializer(valueSerializer());
        redisTemplate.setKeySerializer(keySerializer());
        redisTemplate.setHashKeySerializer(keySerializer());
        redisTemplate.setHashValueSerializer(valueSerializer());
        return redisTemplate;
    }

    /****
     * 序列化设置
     * @return
     */
    @Bean
    public StringRedisSerializer keySerializer() {
        return new StringRedisSerializer();
    }

    /****
     * 序列化设置
     * @return
     */
    @Bean
    public RedisSerializer valueSerializer() {
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        return jackson2JsonRedisSerializer;
    }
}