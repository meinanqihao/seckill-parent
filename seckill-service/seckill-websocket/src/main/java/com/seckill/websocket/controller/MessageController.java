package com.seckill.websocket.controller;

import com.seckill.websocket.config.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/*****
 * http://localhost:18088/message/zs?msg=hello
 * @Author: http://www.itheima.com
 * @Description: com.seckill.websocket.controller.MessageController
 ****/
@RestController
@RequestMapping(value = "/message")
public class MessageController {

    @Autowired
    private WebSocketServer webSocketServer;

    /****
     * 发送消息
     */
    @GetMapping(value = "/{userid}")
    public String send(@PathVariable(value = "userid")String userid, String msg) throws IOException {
        webSocketServer.sendMessage(msg,userid);
        return "OK";
    }
}
