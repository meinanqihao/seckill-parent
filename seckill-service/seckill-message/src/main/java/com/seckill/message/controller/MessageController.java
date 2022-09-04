package com.seckill.message.controller;
import com.seckill.message.config.NettyWebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/*****
 * http://localhost:18088/message/zs?msg=hello
 *  * @Author: http://www.itheima.com
 *  * @Description: com.seckill.websocket.controller.MessageController
 ****/
@RestController
@RequestMapping(value = "/message")
public class MessageController {

    @Autowired
    private NettyWebSocketServer nettyWebSocketServer;

    /****
     * 发送消息
     */
    @GetMapping(value = "/{userid}")
    public String send(@PathVariable(value = "userid")String userid,@RequestParam(value = "msg") String msg) throws IOException {
        nettyWebSocketServer.sendMessage(msg,userid);
        return "OK";
    }
}
