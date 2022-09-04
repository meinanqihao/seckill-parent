package com.seckill.canal;

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
@CanalTable(value = "tb_sku")
@Component
public class SkuHandler implements EntryHandler<Sku> {

    @Autowired
    private SkuPageFeign skuPageFeign;

    /****
     * 增加
     * @param sku
     */
    @Override
    public void insert(Sku sku) {
        try {
            //增加的数据 status--->2：生成静态页
            if(sku.getStatus().equalsIgnoreCase("2")){
                //生成静态页
                skuPageFeign.html(sku);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * 修改监听
     * @param before
     * @param after
     */
    @Override
    public void update(Sku before, Sku after) {

        try {
            //before.status==1
            //after.status==2
            //生成静态页
            if(before.getStatus().equals("1") && after.getStatus().equals("2")){
                skuPageFeign.html(after);
            }else if(before.getStatus().equals("2") && after.getStatus().equals("1")){
                //before.status==2
                //after.status==1
                //删除静态页
                skuPageFeign.delHtml(after.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /***
     * 删除
     * @param sku
     */
    @Override
    public void delete(Sku sku) {
        //删除静态页
        skuPageFeign.delHtml(sku.getId());
    }
}
