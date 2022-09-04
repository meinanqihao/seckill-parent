package com.seckill.goods.service;

import com.github.pagehelper.PageInfo;
import com.seckill.goods.pojo.Activity;
import com.seckill.goods.pojo.Sku;
import com.seckill.goods.pojo.SkuAct;

import java.util.List;

/****
 * @Author:www.itheima.com
 * @Description:SkuAct业务层接口
 * @Date  0:16
 *****/
public interface SkuActService {

    /***
     * SkuAct多条件分页查询
     * @param skuAct
     * @param page
     * @param size
     * @return
     */
    PageInfo<SkuAct> findPage(SkuAct skuAct, int page, int size);

    /***
     * SkuAct分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<SkuAct> findPage(int page, int size);

    /***
     * SkuAct多条件搜索方法
     * @param skuAct
     * @return
     */
    List<SkuAct> findList(SkuAct skuAct);

    /***
     * 删除SkuAct
     * @param id
     */
    void delete(String id);

    /***
     * 修改SkuAct数据
     * @param skuAct
     */
    void update(SkuAct skuAct);

    /***
     * 新增SkuAct
     * @param skuAct
     */
    void add(SkuAct skuAct);

    /**
     * 根据ID查询SkuAct
     * @param id
     * @return
     */
     SkuAct findById(String id);

    /***
     * 查询所有SkuAct
     * @return
     */
    List<SkuAct> findAll();

    /***
     * 删除所有商品和活动的关系
     */
    void deleteAll();

    /***
     * 增加活动下的商品
     * @param activity
     * @param skuList
     */
    void addList(Activity activity, List<Sku> skuList) throws Exception;
}
