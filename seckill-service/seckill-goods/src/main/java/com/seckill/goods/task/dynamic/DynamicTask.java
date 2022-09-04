package com.seckill.goods.task.dynamic;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.seckill.goods.config.SpringUtil;
import com.seckill.goods.dao.SkuActMapper;
import com.seckill.goods.dao.SkuMapper;
import com.seckill.goods.pojo.Sku;
import com.seckill.goods.pojo.SkuAct;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/*****
 * @Author: http://www.itheima.com
 * @Description: com.seckill.goods.task.dynamic.DynamicTask
 ****/
public class DynamicTask implements SimpleJob {

    //任务信息
    @Override
    public void execute(ShardingContext shardingContext) {
        SkuActMapper skuActMapper =SpringUtil.getBean(SkuActMapper.class);
        SkuMapper skuMapper =SpringUtil.getBean(SkuMapper.class);

        //1.活动ID传递过来
        String actid = shardingContext.getJobParameter();
        SkuAct skuAct = new SkuAct();
        skuAct.setActivityId(actid);
        //2.根据活动ID查询活动对应的商品结合
        List<SkuAct> skuActs = skuActMapper.select(skuAct);

        //3.更新对应商品状态实现删除指定的详情页--status=2--1
        List<String> ids = new ArrayList<String>();
        for (SkuAct act : skuActs) {
            ids.add(act.getSkuId());
        }

        //更新状态  2->1
        Example example = new Example(Sku.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id",ids);
        criteria.andEqualTo("status","2");

        Sku sku = new Sku();
        sku.setStatus("1");
        skuMapper.updateByExampleSelective(sku,example);
    }

}
