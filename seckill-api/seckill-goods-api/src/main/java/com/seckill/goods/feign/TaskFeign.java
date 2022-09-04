package com.seckill.goods.feign;
import com.seckill.goods.pojo.Sku;
import com.seckill.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/*****
 * @Author: http://www.itheima.com
 * @Project: seckill
 * @Description: com.seckill.goods.feign.SkuFeign
 ****/
@FeignClient(value = "seckill-goods")
public interface TaskFeign {

    /****
     * 触发定时任务
     * String jobName,Long time,String actid
     */
    @GetMapping(value = "/task")
    Result<Sku> addTask(@RequestParam(value = "jobName")String jobName,
                       @RequestParam(value = "time")Date time,
                       @RequestParam(value = "actid")String actid);

}
