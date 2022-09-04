package com.seckill.canal;

import com.seckill.goods.feign.TaskFeign;
import com.seckill.goods.pojo.Activity;
import com.seckill.goods.pojo.Sku;
import com.seckill.page.feign.SkuPageFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.javatool.canal.client.annotation.CanalTable;
import top.javatool.canal.client.handler.EntryHandler;

/*****
 * @Author: http://www.itheima.com
 * @Description: com.seckill.canal.SkuHandler
 ****/
@CanalTable(value = "tb_activity")
@Component
public class ActivityHandler implements EntryHandler<Activity> {

    @Autowired
    private TaskFeign taskFeign;

    /****
     * 增加
     * @param activity
     */
    @Override
    public void insert(Activity activity) {

    }

    /***
     * 修改监听
     * @param before
     * @param after
     */
    @Override
    public void update(Activity before, Activity after) {
        //定时任务添加
        taskFeign.addTask(after.getId(),after.getEndtime(),after.getId());
    }

    /***
     * 删除
     * @param sku
     */
    @Override
    public void delete(Activity sku) {

    }
}
