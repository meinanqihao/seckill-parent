package com.seckill.goods.service;

import com.github.pagehelper.PageInfo;
import com.seckill.goods.pojo.Sku;

import java.util.List;

/****
 * @Author:www.itheima.com
 * @Description:Sku业务层接口
 * @Date  0:16
 *****/
public interface SkuService {

    /****
     * 库存递减
     */
    int dcount(String id,Integer count);


    /****
     * 热点商品隔离
     */
    void hotIsolation(String id);

    /****
     * 查询总数量
     */
    Integer count();

    /****
     * 分页查询集合列表
     * @param page
     * @param size
     */
    List<Sku> list(Integer page,Integer size);


    /***
     * Sku多条件分页查询
     * @param sku
     * @param page
     * @param size
     * @return
     */
    PageInfo<Sku> findPage(Sku sku, int page, int size);

    /***
     * Sku分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<Sku> findPage(int page, int size);

    /***
     * Sku多条件搜索方法
     * @param sku
     * @return
     */
    List<Sku> findList(Sku sku);

    /***
     * 删除Sku
     * @param id
     */
    void delete(String id);

    /***
     * 修改Sku数据
     * @param sku
     */
    void update(Sku sku);

    /***
     * 新增Sku
     * @param sku
     */
    void add(Sku sku);

    /**
     * 根据ID查询Sku
     * @param id
     * @return
     */
     Sku findById(String id);

    /***
     * 查询所有Sku
     * @return
     */
    List<Sku> findAll();

    /***
     * 锁定商品
     * @param id
     */
    void lock(String id);

    /**
     * 解锁商品
     * @param id
     */
    void unlock(String id);

    /***
     * 根据活动ID查询商品列表
     * @param id
     * @param page
     * @param size
     * @return
     */
    PageInfo<Sku> findSkuByActivityId(String id, Integer page, Integer size);


    /***
     * 数据归零
     * @param id
     */
    void zero(String id);

    /***
     * 所有秒杀商品更新成普通商品
     */
    void modifySku();
}
