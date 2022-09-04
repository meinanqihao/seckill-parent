package com.seckill.message.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

/*****
 * @Author: http://www.itheima.com
 * @Description: com.seckill.message.feign.MessageFeign
 ****/
@FeignClient(value = "seckill-message")
public interface MessageFeign {

    /****
     * 发送消息
     */
    @GetMapping(value = "/message/{userid}")
    String send(@PathVariable(value = "userid")String userid,@RequestParam(value = "msg") String msg) throws IOException;
}
