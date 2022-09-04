package com.seckill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.yeauty.standard.ServerEndpointExporter;

/*****
 * @Author: http://www.itheima.com
 * @Description: com.seckill.NettyWebSocketApplication
 ****/
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class NettyWebSocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyWebSocketApplication.class,args);
    }

    /***
     * 创建ServerEndpointExport对象
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}
