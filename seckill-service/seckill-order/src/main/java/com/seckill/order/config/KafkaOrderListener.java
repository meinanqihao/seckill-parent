package com.seckill.order.config;

import com.alibaba.fastjson.JSON;
import com.seckill.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/*****
 * @Author: http://www.itheima.com
 * @Description: com.seckill.order.config.KafkaOrderListener
 ****/
@Component
public class KafkaOrderListener {

    @Autowired
    private OrderService orderService;

    /****
     * 监听消息创建订单流程
     */
    @KafkaListener(topics = "neworder")
    public void addhotOrder(String message){
        try {
            Map<String,String> ordermap = JSON.parseObject(message,Map.class);
            //下单
            orderService.hotAdd(ordermap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
