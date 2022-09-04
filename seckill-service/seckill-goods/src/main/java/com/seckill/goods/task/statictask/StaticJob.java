package com.seckill.goods.task.statictask;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.elasticjob.lite.annotation.ElasticSimpleJob;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/*****
 * @Author: http://www.itheima.com
 * @Description: com.seckill.goods.task.statictask.StaticJob
 ****/
@ElasticSimpleJob(
    //指定命名空间
    jobName = "${elaticjob.zookeeper.namespace}",
    //分片个数
    shardingTotalCount = 1,
    //任务执行周期
    cron = "0/5 * * * * ?"
)
//@Component
public class StaticJob implements SimpleJob{


    /****
     * 定时任务
     * @param shardingContext
     */
    @Override
    public void execute(ShardingContext shardingContext) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        System.out.println(simpleDateFormat.format(new Date()));
    }
}
