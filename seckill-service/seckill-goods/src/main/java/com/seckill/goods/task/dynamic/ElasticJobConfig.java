package com.seckill.goods.task.dynamic;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/*****
 * @Author: http://www.itheima.com
 * @Description: com.seckill.goods.task.dynamic.ElasticJobConfig
 ****/
@Configuration
public class ElasticJobConfig {

    @Value("${zk}")
    private String serverlist;

    @Value("${namesp}")
    private String namesp;

    /*****
     * 加载配置
     */
    @Bean
    public ZookeeperConfiguration zookeeperConfiguration(){
        return new ZookeeperConfiguration(serverlist,namesp);
    }


    /***
     * 注册配置
     */
    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter registryCenter(ZookeeperConfiguration zookeeperConfiguration){
        return new ZookeeperRegistryCenter(zookeeperConfiguration);
    }


    /*****
     * 监听器
     */

}
