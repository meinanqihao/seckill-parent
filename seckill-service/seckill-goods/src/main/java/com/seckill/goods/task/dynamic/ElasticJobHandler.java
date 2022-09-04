package com.seckill.goods.task.dynamic;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.internal.schedule.LiteJob;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*****
 * @Author: http://www.itheima.com
 * @Description: com.seckill.goods.task.dynamic.ElasticJobHandler
 ****/
@Component
public class ElasticJobHandler {

    @Autowired
    private ZookeeperRegistryCenter zookeeperRegistryCenter;

    /****
     * 动态添加定时任务
     */
    public void addTask(String jobName, String cron, Integer count,SimpleJob instance,String actid){
        //Builder--->构建定时任务
        LiteJobConfiguration.Builder builder = LiteJobConfiguration.newBuilder(
                //配置对象 SimpleJobConfiguration
                //1.定时任务配置信息
                //2.定时任务的字节码信息
                new SimpleJobConfiguration(JobCoreConfiguration.newBuilder(
                        //jobName
                        jobName,
                        //cron
                        cron,
                        //shardingTotalCount
                        count
                        )
                        //额外参数
                        .jobParameter(actid)
                        .build(),
                        //定时任务的字节码信息
                        instance.getClass().getName())
        );

        //构建定时任务配置信息
        LiteJobConfiguration jobConfiguration = builder.overwrite(true).build();

        //创建定时任务
        new SpringJobScheduler(instance,zookeeperRegistryCenter,jobConfiguration).init();
    }
}
