package com.seckill.monitor;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.elasticjob.lite.annotation.ElasticSimpleJob;
import com.seckill.goods.feign.SkuFeign;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/*****
 * @Author: http://www.itheima.com
 * @Description: com.seckill.monitor.MonitorTask
 ****/
@ElasticSimpleJob(
        jobName = "${monitortask}",
        cron = "1/5 * * * * ?",   //每5秒钟执行一次
        shardingTotalCount = 1
)
@Component
public class MonitorTask implements SimpleJob{

    @Autowired
    private SkuFeign skuFeign;

    //MonitorItemsAccess
    @Autowired
    private MonitorItemsAccess monitorItemsAccess;

    @SneakyThrows
    @Override
    public void execute(ShardingContext shardingContext) {
        List<String> ids = monitorItemsAccess.loadData();
        for (String id : ids) {
            System.out.println(id);
        }
        //调用
        if(ids!=null && ids.size()>0){
            skuFeign.hotIsolation(ids);
        }
    }
}
