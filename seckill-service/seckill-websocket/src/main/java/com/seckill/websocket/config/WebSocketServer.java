package com.seckill.websocket.config;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*****
 * @Author: http://www.itheima.com
 * @Description: com.seckill.websocket.config.WebSocketServer
 ****/
@Component
@ServerEndpoint(value = "/ws/{userid}")
public class WebSocketServer {

    /****
     * Map，存储用户会话,key用当前的userid
     */
    private static Map<String,Session> sessionMap = new HashMap<String,Session>();

    /****
     * Map，存储Session的ID和userid的映射关系
     */
    private static Map<String,String> idsMap = new HashMap<String,String>();

    /****
     * 1.建立连接后调用该方法
     *  注意：
     *      当前会话属于长连接类型（有状态），因此不能持久化对象到DB中
     */
    @OnOpen
    public void onOpen(@PathParam("userid")String userid, Session session){
        //根据userid获取会话Session
        Session userSession = sessionMap.get(userid);
        if(userSession!=null){
            //根据SessionID和userid移除可能存在的冗余数据
            idsMap.remove(userSession.getId());
            sessionMap.remove(userid);
        }

        //存储用户会话信息
        sessionMap.put(userid,session);
        //存储会话的id和userid的映射关系
        idsMap.put(session.getId(),userid);
    }


    /****
     * 2.关闭链接
     */
    @OnClose
    public void onClose(Session session){
        //根据Session的ID从idsMap中取出userid
        String userid = idsMap.get(session.getId());
        //从sessionMap中移除userid对应的会话
        sessionMap.remove(userid);
        //移除idsMap中Session的ID对应的userid信息
        idsMap.remove(session.getId());
    }


    /****
     * 3.异常处理
     */
    @OnError
    public void onError(Session session,Throwable throwable){
        System.out.println("用户："+idsMap.get(session.getId())+",通信发生了异常,"+throwable.getMessage());
    }

    /****
     * 4.接收客户端消息
     */
    @OnMessage
    public void onMessage(String message,Session session) throws IOException {
        System.out.println("用户："+idsMap.get(session.getId())+",接收到的信息是："+message);

        //回复消息
        session.getBasicRemote().sendText("收到的消息："+message);
    }

    /***
     * 5.消息发送
     */
    public void sendMessage(String message,String userid) throws IOException {
        //根据userid获取会话对象
        Session session = sessionMap.get(userid);

        if(session!=null){
            //给指定会话发送消息
            session.getBasicRemote().sendText(message);
        }
    }

}
