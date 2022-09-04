package com.seckill.message.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.yeauty.annotation.*;
import org.yeauty.pojo.Session;

import java.util.HashMap;
import java.util.Map;

/*****
 * @Author: http://www.itheima.com
 * @Description: com.seckill.message.config.NettyWebSocketServer
 ****/
@Component
@ServerEndpoint(path = "/ws/{userid}",port = "${ws.port}",host = "${ws.host}")
public class NettyWebSocketServer {


    /****
     * 定义一个Map存储所有会话
     */
    private static Map<String,Session> sessionMap = new HashMap<String,Session>();

    @Autowired
    private RedisTemplate redisTemplate;

    /****
     * 1.建立连接
     */
    @OnOpen
    public void onOpen(@PathVariable(value = "userid") String userid, Session session){
        //获取Session的ID
        String id = session.channel().id().toString();

        //获取用户对应的会话对象
        Session userSession = sessionMap.get(userid);
        if(userSession!=null){
            //清理会话信息
            sessionMap.remove(userid);
            redisTemplate.boundHashOps("NettyWebSocketUser").delete(userSession.channel().id().toString());
        }

        //存储用户会话信息
        sessionMap.put(userid,session);
        //存储SessionID和userid的映射关系
        redisTemplate.boundHashOps("NettyWebSocketUser").put(id,userid);
    }


    /****
     * 2.关闭关闭
     */
    @OnClose
    public void onClose(Session session){
        //根据SessionID从Redis中查找userid
        String userid = redisTemplate.boundHashOps("NettyWebSocketUser").get(session.channel().id().toString()).toString();

        //根据userid删除SessionMap中的Session
        sessionMap.remove(userid);
        //删除Redis中userid的映射信息
        redisTemplate.boundHashOps("NettyWebSocketUser").delete(session.channel().id().toString());
    }


    /***
     * 3.发生异常
     */
    @OnError
    public void onError(Session session,Throwable throwable){
        Object userid = redisTemplate.boundHashOps("NettyWebSocketUser").get(session.channel().id().toString());
        System.out.println("用户ID"+userid+",通信发生异常！");
    }


    /****
     * 4.接收客户端发送的消息
     */
    @OnMessage
    public void onMessage(String message,Session session){
        Object userid = redisTemplate.boundHashOps("NettyWebSocketUser").get(session.channel().id().toString());
        System.out.println("用户ID"+userid+"发送的消息是："+message);

        //回复客户端
        session.sendText("您发送的消息是："+message);
    }


    /****
     * 5.主动给客户端发消息
     */
    public void sendMessage(String message,String userid){
        //获取用户会话
        Session session = sessionMap.get(userid);

        //发送消息
        if(session!=null){
            session.sendText(message);
        }
    }
}
