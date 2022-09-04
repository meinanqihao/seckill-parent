package com.seckill.goods.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seckill.goods.dao.ActivityMapper;
import com.seckill.goods.dao.SkuActMapper;
import com.seckill.goods.dao.SkuMapper;
import com.seckill.goods.pojo.Activity;
import com.seckill.goods.pojo.Sku;
import com.seckill.goods.pojo.SkuAct;
import com.seckill.goods.service.SkuService;
import com.seckill.util.StatusCode;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.types.RedisClientInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/****
 * @Author:www.itheima.com
 * @Description:Sku业务层接口实现类
 * @Date  0:16
 *****/
@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private SkuActMapper skuActMapper;

    /***
     * 库存递减
     * @param id
     * @param count
     * @return
     */
    @Override
    public int dcount(String id, Integer count) {
        //1.调用Dao实现递减
        int dcount = skuMapper.dcount(id, count);
        //2.递减失败
        if(dcount==0){
            //查询
            Sku sku = skuMapper.selectByPrimaryKey(id);
            //2.1递减失败原因->库存不足->405
            if(sku.getSeckillNum()<count){
                return StatusCode.DECOUNT_NUM;
            }else if (sku.getIslock()==2){
                //2.2递减失败原因->变成热点->205
                return StatusCode.DECOUNT_HOT;
            }
            return 0;
        }
        return StatusCode.DECOUNT_OK;
    }

    /****
     * 热点商品隔离
     * @param id
     */
    @Override
    public void hotIsolation(String id) {
        String key ="SKU_"+id;
        Boolean bo = redisTemplate.hasKey(key);
        if(bo){
            return;
        }

        //1.修改islock=2，锁定->编程热点商品
        Sku sku = new Sku();
        sku.setIslock(2);   //锁定=热点

        Example example = new Example(Sku.class);
        Example.Criteria criteria = example.createCriteria();
        //islock=1,id=id
        //status=2
        criteria.andEqualTo("islock","1");
        criteria.andEqualTo("id",id);
        criteria.andEqualTo("status",2);
        //2.修改成功->查询商品数据
        int count = skuMapper.updateByExampleSelective(sku,example);

        //3.将商品数据存入到Redis缓存->隔离
        if(count>0){
            //1.查询商品
            Sku newSku = skuMapper.selectByPrimaryKey(id);
            //2.将商品数据存入到Redis缓存
            Map<String,Object> dataMap = new HashMap<String,Object>();
            dataMap.put("num",newSku.getSeckillNum());

            Map<String,Object> dataInfo = new HashMap<String,Object>();
            dataInfo.put("name",newSku.getName());
            dataInfo.put("price",newSku.getPrice());
            dataInfo.put("id",id);
            dataMap.put("iteminfo",dataInfo);
            redisTemplate.boundHashOps(key).putAll(dataMap);
        }
    }

    /***
     * 查询总数
     * @return
     */
    @Override
    public Integer count() {
        Example example = new Example(Sku.class);
        Example.Criteria criteria = example.createCriteria();

        //1.商品状态  stauts=2
        //criteria.andEqualTo("status","2");
        //2.商品秒杀数量 seckillNum>0
        //criteria.andGreaterThan("seckillNum",0);
        //3.seckillEnd>now()
        //criteria.andGreaterThan("seckillEnd",new Date());
        return skuMapper.selectCountByExample(example);
    }

    /****
     * 分页列表查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public List<Sku> list(Integer page, Integer size) {
        //1.分页-PageHelper
        PageHelper.startPage(page,size);

        //2.分页集合查询
        Example example = new Example(Sku.class);
        Example.Criteria criteria = example.createCriteria();
        //1.商品状态  stauts=2
        //criteria.andEqualTo("status","2");
        //2.商品秒杀数量 seckillNum>0
        //criteria.andGreaterThan("seckillNum",0);
        //3.seckillEnd>now()
        //criteria.andGreaterThan("seckillEnd",new Date());
        return skuMapper.selectByExample(example);
    }

    /**
     * Sku条件+分页查询
     * @param sku 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<Sku> findPage(Sku sku, int page, int size){
        //分页
        PageHelper.startPage(page,size);
        //搜索条件构建
        Example example = createExampleSp(sku);
        //执行搜索
        return new PageInfo<Sku>(skuMapper.selectByExample(example));
    }

    /**
     * Sku分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Sku> findPage(int page, int size){
        //静态分页
        PageHelper.startPage(page,size);
        //分页查询
        return new PageInfo<Sku>(skuMapper.selectAll());
    }

    /**
     * Sku条件查询
     * @param sku
     * @return
     */
    @Override
    public List<Sku> findList(Sku sku){
        //构建查询条件
        Example example = createExample(sku);
        //根据构建的条件查询数据
        return skuMapper.selectByExample(example);
    }


    /**
     * Sku构建查询对象
     * @param sku
     * @return
     */
    public Example createExample(Sku sku){
        Example example=new Example(Sku.class);
        Example.Criteria criteria = example.createCriteria();
        if(sku!=null){
            // 商品id
            if(!StringUtils.isEmpty(sku.getId())){
                    criteria.andEqualTo("id",sku.getId());
            }
            // SKU名称
            if(!StringUtils.isEmpty(sku.getName())){
                    criteria.andLike("name","%"+sku.getName()+"%");
            }
            // 价格（分）
            if(!StringUtils.isEmpty(sku.getPrice())){
                    criteria.andEqualTo("price",sku.getPrice());
            }
            // 单位，分
            if(!StringUtils.isEmpty(sku.getSeckillPrice())){
                    criteria.andEqualTo("seckillPrice",sku.getSeckillPrice());
            }
            // 库存数量
            if(!StringUtils.isEmpty(sku.getNum())){
                    criteria.andEqualTo("num",sku.getNum());
            }
            // 库存预警数量
            if(!StringUtils.isEmpty(sku.getAlertNum())){
                    criteria.andEqualTo("alertNum",sku.getAlertNum());
            }
            // 商品图片
            if(!StringUtils.isEmpty(sku.getImage())){
                    criteria.andEqualTo("image",sku.getImage());
            }
            // 商品图片列表
            if(!StringUtils.isEmpty(sku.getImages())){
                    criteria.andEqualTo("images",sku.getImages());
            }
            // 创建时间
            if(!StringUtils.isEmpty(sku.getCreateTime())){
                    criteria.andEqualTo("createTime",sku.getCreateTime());
            }
            // 更新时间
            if(!StringUtils.isEmpty(sku.getUpdateTime())){
                    criteria.andEqualTo("updateTime",sku.getUpdateTime());
            }
            // SPUID
            if(!StringUtils.isEmpty(sku.getSpuId())){
                    criteria.andEqualTo("spuId",sku.getSpuId());
            }
            // 类目ID
            if(!StringUtils.isEmpty(sku.getCategory1Id())){
                    criteria.andEqualTo("category1Id",sku.getCategory1Id());
            }
            // 
            if(!StringUtils.isEmpty(sku.getCategory2Id())){
                    criteria.andEqualTo("category2Id",sku.getCategory2Id());
            }
            // 
            if(!StringUtils.isEmpty(sku.getCategory3Id())){
                    criteria.andEqualTo("category3Id",sku.getCategory3Id());
            }
            // 
            if(!StringUtils.isEmpty(sku.getCategory1Name())){
                    criteria.andEqualTo("category1Name",sku.getCategory1Name());
            }
            // 
            if(!StringUtils.isEmpty(sku.getCategory2Name())){
                    criteria.andEqualTo("category2Name",sku.getCategory2Name());
            }
            // 类目名称
            if(!StringUtils.isEmpty(sku.getCategory3Name())){
                    criteria.andEqualTo("category3Name",sku.getCategory3Name());
            }
            // 
            if(!StringUtils.isEmpty(sku.getBrandId())){
                    criteria.andEqualTo("brandId",sku.getBrandId());
            }
            // 品牌名称
            if(!StringUtils.isEmpty(sku.getBrandName())){
                    criteria.andEqualTo("brandName",sku.getBrandName());
            }
            // 规格
            if(!StringUtils.isEmpty(sku.getSpec())){
                    criteria.andEqualTo("spec",sku.getSpec());
            }
            // 销量
            if(!StringUtils.isEmpty(sku.getSaleNum())){
                    criteria.andEqualTo("saleNum",sku.getSaleNum());
            }
            // 评论数
            if(!StringUtils.isEmpty(sku.getCommentNum())){
                    criteria.andEqualTo("commentNum",sku.getCommentNum());
            }
            // 商品状态 1-正常，2-下架，3-删除
            if(!StringUtils.isEmpty(sku.getStatus())){
                    criteria.andEqualTo("status",sku.getStatus());
            }

            // 秒杀开始时间
            if(!StringUtils.isEmpty(sku.getSeckillBegin())){
                criteria.andGreaterThanOrEqualTo("seckillBegin",sku.getSeckillBegin());
            }
            // 秒杀结束时间
            if(!StringUtils.isEmpty(sku.getSeckillEnd())){
                criteria.andLessThanOrEqualTo("seckillEnd",sku.getSeckillEnd());
            }
        }
        return example;
    }


    /**
     * Sku构建查询对象
     * @param sku
     * @return
     */
    public Example createExampleSp(Sku sku){
        Example example=new Example(Sku.class);
        Example.Criteria criteria = example.createCriteria();
        if(sku!=null){
            // 商品id
            if(!StringUtils.isEmpty(sku.getId())){
                criteria.andEqualTo("id",sku.getId());
            }
            // SKU名称
            if(!StringUtils.isEmpty(sku.getName())){
                criteria.andLike("name","%"+sku.getName()+"%");
            }
            // 价格（分）
            if(!StringUtils.isEmpty(sku.getPrice())){
                criteria.andEqualTo("price",sku.getPrice());
            }
            // 单位，分
            if(!StringUtils.isEmpty(sku.getSeckillPrice())){
                criteria.andEqualTo("seckillPrice",sku.getSeckillPrice());
            }
            // 库存数量
            if(!StringUtils.isEmpty(sku.getNum())){
                criteria.andEqualTo("num",sku.getNum());
            }
            // 库存预警数量
            if(!StringUtils.isEmpty(sku.getAlertNum())){
                criteria.andEqualTo("alertNum",sku.getAlertNum());
            }
            // 商品图片
            if(!StringUtils.isEmpty(sku.getImage())){
                criteria.andEqualTo("image",sku.getImage());
            }
            // 商品图片列表
            if(!StringUtils.isEmpty(sku.getImages())){
                criteria.andEqualTo("images",sku.getImages());
            }
            // 创建时间
            if(!StringUtils.isEmpty(sku.getCreateTime())){
                criteria.andEqualTo("createTime",sku.getCreateTime());
            }
            // 更新时间
            if(!StringUtils.isEmpty(sku.getUpdateTime())){
                criteria.andEqualTo("updateTime",sku.getUpdateTime());
            }
            // SPUID
            if(!StringUtils.isEmpty(sku.getSpuId())){
                criteria.andEqualTo("spuId",sku.getSpuId());
            }
            // 类目ID
            if(!StringUtils.isEmpty(sku.getCategory1Id())){
                criteria.andEqualTo("brandId",sku.getCategory1Id());
            }
            //
            if(!StringUtils.isEmpty(sku.getCategory2Id())){
                criteria.andEqualTo("category2Id",sku.getCategory2Id());
            }
            //
            if(!StringUtils.isEmpty(sku.getCategory3Id())){
                criteria.andEqualTo("category3Id",sku.getCategory3Id());
            }
            //
            if(!StringUtils.isEmpty(sku.getCategory1Name())){
                criteria.andEqualTo("category1Name",sku.getCategory1Name());
            }
            //
            if(!StringUtils.isEmpty(sku.getCategory2Name())){
                criteria.andEqualTo("category2Name",sku.getCategory2Name());
            }
            // 类目名称
            if(!StringUtils.isEmpty(sku.getCategory3Name())){
                criteria.andEqualTo("category3Name",sku.getCategory3Name());
            }
            //
            if(!StringUtils.isEmpty(sku.getBrandId())){
                criteria.andEqualTo("category1Id",sku.getBrandId());
            }
            // 品牌名称
            if(!StringUtils.isEmpty(sku.getBrandName())){
                criteria.andEqualTo("brandName",sku.getBrandName());
            }
            // 规格
            if(!StringUtils.isEmpty(sku.getSpec())){
                criteria.andEqualTo("spec",sku.getSpec());
            }
            // 销量
            if(!StringUtils.isEmpty(sku.getSaleNum())){
                criteria.andEqualTo("saleNum",sku.getSaleNum());
            }
            // 评论数
            if(!StringUtils.isEmpty(sku.getCommentNum())){
                criteria.andEqualTo("commentNum",sku.getCommentNum());
            }
            // 商品状态 1-正常，2-下架，3-删除
            if(!StringUtils.isEmpty(sku.getStatus())){
                criteria.andEqualTo("status",sku.getStatus());
            }

            // 秒杀开始时间
            if(!StringUtils.isEmpty(sku.getSeckillBegin())){
                criteria.andGreaterThanOrEqualTo("seckillBegin",sku.getSeckillBegin());
            }
            // 秒杀结束时间
            if(!StringUtils.isEmpty(sku.getSeckillEnd())){
                criteria.andLessThanOrEqualTo("seckillEnd",sku.getSeckillEnd());
            }
        }
        return example;
    }

    @Autowired
    private ActivityMapper activityMapper;

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(String id){
        //查询商品信息
        Sku currentsku = skuMapper.selectByPrimaryKey(id);

        if(currentsku!=null){
            //查询活动信息
            Activity activity = new Activity();
            activity.setBegintime(currentsku.getSeckillBegin());
            Activity currentActivity = activityMapper.selectOne(activity);
            //skuMapper.deleteByPrimaryKey(id);
            Sku sku = new Sku();
            sku.setId(id);
            sku.setStatus("1"); //非秒杀商品
            sku.setIslock(1);   //未锁定
            int count = skuMapper.updateByPrimaryKeySelective(sku);
            if(count>0){
                //移除关联
                SkuAct skuAct = new SkuAct();
                skuAct.setActivityId(currentActivity.getId());
                skuAct.setSkuId(currentsku.getId());
                skuActMapper.delete(skuAct);
            }
        }
    }

    /**
     * 修改Sku
     * @param sku
     */
    @Override
    public void update(Sku sku){
        skuMapper.updateByPrimaryKeySelective(sku);
    }

    /**
     * 增加Sku
     * @param sku
     */
    @Override
    public void add(Sku sku){
        skuMapper.insertSelective(sku);
    }

    /**
     * 根据ID查询Sku
     * @param id
     * @return
     */
    @Override
    public Sku findById(String id){
        return  skuMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询Sku全部数据
     * @return
     */
    @Override
    public List<Sku> findAll() {
        return skuMapper.selectAll();
    }

    /***
     * 锁定商品
     * @param id
     */
    @Override
    public void lock(String id) {
        skuMapper.lock(id);
    }

    /***
     * 解锁商品
     * @param id
     */
    @Override
    public void unlock(String id) {
        skuMapper.unlock(id);
    }

    @Autowired
    private RedisTemplate redisTemplate;


    /***
     * 根据活动ID查询
     * @param id
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Sku> findSkuByActivityId(String id, Integer page, Integer size) {
        //分页
        PageHelper.startPage(page,size);

        //查询
        List<Sku> skuList = skuMapper.findSkuByActivityId(id);
        return new PageInfo<Sku>(skuList);
    }



    /***
     * 提取所有ID
     * @param skuList
     * @return
     */
    public List<String> getIds(List<Sku> skuList){
        List<String> ids = new ArrayList<String>();

        for (Sku sku : skuList) {
            ids.add(sku.getId());
        }
        return ids;
    }

    /***
     * 归零设置
     * @param id
     */
    @Override
    public void zero(String id) {
        Sku sku = new Sku();
        sku.setId(id);
        sku.setSeckillNum(0);
        skuMapper.updateByPrimaryKeySelective(sku);
    }

    /***
     * 商品更新成非秒杀商品，未锁定商品
     */
    @Override
    public void modifySku() {
        //修改状态
        skuMapper.modifySku();
    }
}
