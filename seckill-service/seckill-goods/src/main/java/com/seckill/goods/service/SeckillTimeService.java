package com.seckill.goods.service;

import com.github.pagehelper.PageInfo;
import com.seckill.goods.pojo.SeckillTime;

import java.util.List;

/****
 * @Author:www.itheima.com
 * @Description:SeckillTime业务层接口
 * @Date  0:16
 *****/
public interface SeckillTimeService {

    /***
     * SeckillTime多条件分页查询
     * @param seckillTime
     * @param page
     * @param size
     * @return
     */
    PageInfo<SeckillTime> findPage(SeckillTime seckillTime, int page, int size);

    /***
     * SeckillTime分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<SeckillTime> findPage(int page, int size);

    /***
     * SeckillTime多条件搜索方法
     * @param seckillTime
     * @return
     */
    List<SeckillTime> findList(SeckillTime seckillTime);

    /***
     * 删除SeckillTime
     * @param id
     */
    void delete(Integer id);

    /***
     * 修改SeckillTime数据
     * @param seckillTime
     */
    void update(SeckillTime seckillTime);

    /***
     * 新增SeckillTime
     * @param seckillTime
     */
    void add(SeckillTime seckillTime);

    /**
     * 根据ID查询SeckillTime
     * @param id
     * @return
     */
     SeckillTime findById(Integer id);

    /***
     * 查询所有SeckillTime
     * @return
     */
    List<SeckillTime> findAll();

    /***
     * 审核
     * @param id
     * @param type
     */
    void audit(Integer id, Integer type);

    /***
     * 查询有效时间
     * @return
     */
    List<SeckillTime> findAllValidTimes();

    /***
     * 删除所有时间段
     */
    void deleteAll();

    /***
     * 批量增加时间段
     * @param seckillTimes
     */
    void addTimes(List<SeckillTime> seckillTimes);
}
