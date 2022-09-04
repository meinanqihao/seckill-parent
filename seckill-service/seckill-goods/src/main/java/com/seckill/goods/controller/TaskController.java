package com.seckill.goods.controller;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.seckill.goods.task.dynamic.DynamicTask;
import com.seckill.goods.task.dynamic.ElasticJobHandler;
import com.seckill.util.Result;
import com.seckill.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/*****
 * @Author: http://www.itheima.com
 * @Description: com.seckill.goods.controller.TaskController
 ****/
@RestController
@RequestMapping(value = "/task")
//@CrossOrigin
public class TaskController {

    @Autowired
    private ElasticJobHandler elasticJobHandler;

    /***
     *
     * 动态定时任务案例测试
     * jobName:任务的命名空间
     * time:执行时间-延时时间
     *
     * 活动ID
     */
    @GetMapping
    public Result task(String jobName,Date time,String actid){
        //动态定时任务
        elasticJobHandler.addTask(jobName, cron(time), 1,new DynamicTask(),actid);
        //elasticJobHandler.addTask(jobName, cron(new Date(System.currentTimeMillis()+10000)), 1,new DynamicTask(),actid);
        return new Result(true, StatusCode.OK,"执行成功！");
    }

    //将当前时间转成Cron表达式  ss mm HH dd MM ? yyyy  Date
    public String cron(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ss mm HH dd MM ? yyyy");
        return simpleDateFormat.format(date);
    }
}
