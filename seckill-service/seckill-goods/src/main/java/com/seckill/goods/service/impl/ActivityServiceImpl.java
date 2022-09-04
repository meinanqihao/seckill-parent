package com.seckill.goods.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seckill.goods.dao.ActivityMapper;
import com.seckill.goods.dao.SeckillTimeMapper;
import com.seckill.goods.pojo.Activity;
import com.seckill.goods.pojo.SeckillTime;
import com.seckill.goods.service.ActivityService;
import com.seckill.util.IdWorker;
import com.seckill.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/****
 * @Author:www.itheima.com
 * @Description:Activity业务层接口实现类
 * @Date  0:16
 *****/
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private SeckillTimeMapper seckillTimeMapper;

    @Autowired
    private IdWorker idWorker;

    /**
     * Activity条件+分页查询
     * @param activity 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<Activity> findPage(Activity activity, int page, int size){
        //分页
        PageHelper.startPage(page,size);
        //搜索条件构建
        Example example = createExample(activity);
        //执行搜索
        List<Activity> activities = activityMapper.selectByExample(example);

        for (Activity act : activities) {
            //查询出所有活动时间
            SeckillTime seckillTime = seckillTimeMapper.selectByPrimaryKey(act.getTimeId());
            //查询出当前
            SeckillTime condtionSeckillTime = new SeckillTime();
            condtionSeckillTime.setStatus(1);
            List<SeckillTime> seckillTimes = seckillTimeMapper.select(condtionSeckillTime);
            act.setSeckillTime(seckillTime);
            act.setSeckillTimeList(seckillTimes);
        }
        return new PageInfo<Activity>(activities);
    }

    /**
     * Activity分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Activity> findPage(int page, int size){
        //静态分页
        PageHelper.startPage(page,size);
        //分页查询
        return new PageInfo<Activity>(activityMapper.selectAll());
    }

    /**
     * Activity条件查询
     * @param activity
     * @return
     */
    @Override
    public List<Activity> findList(Activity activity){
        //构建查询条件
        Example example = createExample(activity);
        //根据构建的条件查询数据
        List<Activity> activities = activityMapper.selectByExample(example);

        for (Activity act : activities) {
            //查询出所有活动时间
            SeckillTime seckillTime = seckillTimeMapper.selectByPrimaryKey(act.getTimeId());
            //查询出当前
            SeckillTime condtionSeckillTime = new SeckillTime();
            condtionSeckillTime.setStatus(1);
            List<SeckillTime> seckillTimes = seckillTimeMapper.select(condtionSeckillTime);
            act.setSeckillTime(seckillTime);
            act.setSeckillTimeList(seckillTimes);
        }
        return activities;
    }


    /**
     * Activity构建查询对象
     * @param activity
     * @return
     */
    public Example createExample(Activity activity){
        Example example=new Example(Activity.class);
        Example.Criteria criteria = example.createCriteria();
        if(activity!=null){
            // 
            if(!StringUtils.isEmpty(activity.getId())){
                    criteria.andEqualTo("id",activity.getId());
            }
            // 
            if(!StringUtils.isEmpty(activity.getName())){
                    criteria.andLike("name","%"+activity.getName()+"%");
            }
            // 状态：1开启，2未开启
            if(!StringUtils.isEmpty(activity.getStatus())){
                    criteria.andEqualTo("status",activity.getStatus());
            }
            // 
            if(!StringUtils.isEmpty(activity.getStartdate())){
                    criteria.andEqualTo("startdate",activity.getStartdate());
            }
            // 开始时间，单位：时分秒
            if(!StringUtils.isEmpty(activity.getBegintime())){
                    //criteria.andEqualTo("begintime",activity.getBegintime());
                    criteria.andGreaterThanOrEqualTo("begintime",activity.getBegintime());
            }
            // 结束时间，单位：时分秒
            if(!StringUtils.isEmpty(activity.getEndtime())){
                    //criteria.andEqualTo("endtime",activity.getEndtime());
                    criteria.andLessThanOrEqualTo("endtime",activity.getEndtime());
            }
            // 
            if(!StringUtils.isEmpty(activity.getTotalTime())){
                    criteria.andEqualTo("totalTime",activity.getTotalTime());
            }

            if(!StringUtils.isEmpty(activity.getIsDel())){
                criteria.andEqualTo("isDel",activity.getIsDel());
            }
        }
        return example;
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(String id){
        Activity activity = new Activity();
        activity.setIsDel(2);
        activity.setId(id);
        activityMapper.updateByPrimaryKeySelective(activity);
    }

    /**
     * 修改Activity
     * @param activity
     */
    @Override
    public void update(Activity activity){
        activity.setBegintime(TimeUtil.replaceDate(activity.getStartdate(),activity.getSeckillTime().getStarttime()));
        activity.setEndtime(TimeUtil.replaceDate(activity.getStartdate(),activity.getSeckillTime().getEndtime()));

        //如果结束时间<开始时间，则重新计算结束时间
        if(activity.getEndtime().getTime()<=activity.getBegintime().getTime()){
            activity.setEndtime(TimeUtil.replaceDate(TimeUtil.addDateHour(activity.getStartdate(),24),activity.getSeckillTime().getEndtime()));
        }
        activity.setTimeId(activity.getSeckillTime().getId());
        float times = TimeUtil.dif2hour(activity.getBegintime(), activity.getEndtime());
        activity.setTotalTime(times);
        //添加
        activityMapper.updateByPrimaryKeySelective(activity);
    }



    /**
     * 增加Activity
     * @param activity
     */
    @Override
    public void add(Activity activity){
        //查询当前活动对应的信息
        activity.setId("No"+idWorker.nextId());
        activity.setBegintime(TimeUtil.replaceDate(activity.getStartdate(),activity.getSeckillTime().getStarttime()));
        activity.setEndtime(TimeUtil.replaceDate(activity.getStartdate(),activity.getSeckillTime().getEndtime()));

        //如果结束时间<开始时间，则重新计算结束时间
        if(activity.getEndtime().getTime()<=activity.getBegintime().getTime()){
            activity.setEndtime(TimeUtil.replaceDate(TimeUtil.addDateHour(activity.getStartdate(),24),activity.getSeckillTime().getEndtime()));
        }
        activity.setTimeId(activity.getSeckillTime().getId());
        float times = TimeUtil.dif2hour(activity.getBegintime(), activity.getEndtime());
        activity.setTotalTime(times);
        //添加
        activityMapper.insertSelective(activity);

        //定时任务调度，将活动结束时间作为任务开始执行时间
        //elasticjobDynamicConfig.addDynamicTask(activity.getId(), ElasticjobDynamicConfig.date2cron(activity.getEndtime()),1,activity.getId(),new DynamicTask());
    }

    /**
     * 根据ID查询Activity
     * @param id
     * @return
     */
    @Override
    public Activity findById(String id){
        Activity activity =  activityMapper.selectByPrimaryKey(id);
        //查询出所有活动时间
        SeckillTime seckillTime = seckillTimeMapper.selectByPrimaryKey(activity.getTimeId());
        //查询出当前
        SeckillTime condtionSeckillTime = new SeckillTime();
        condtionSeckillTime.setStatus(1);
        List<SeckillTime> seckillTimes = seckillTimeMapper.select(condtionSeckillTime);
        activity.setSeckillTime(seckillTime);
        activity.setSeckillTimeList(seckillTimes);
        return activity;
    }

    /**
     * 查询Activity全部数据
     * @return
     */
    @Override
    public List<Activity> findAll() {
        return activityMapper.selectAll();
    }

    /***
     * 活动上线下线
     * @param id
     * @param isup
     */
    @Override
    public void isUp(String id, int isup) {
        Activity activity = new Activity();
        activity.setId(id);
        activity.setStatus(isup);
        activityMapper.updateByPrimaryKeySelective(activity);
    }

    @Override
    public List<Activity> times() {
        return activityMapper.times();
    }

    /***
     * 创建一个活动
     * @param seckillTime
     */
    @Override
    public Activity createActivity(SeckillTime seckillTime) {
        Activity activity = new Activity();
        activity.setId("No"+idWorker.nextId());
        activity.setStartdate(TimeUtil.getTimes(0));
        activity.setName(seckillTime.getName());
        activity.setStatus(1);
        //开始时间、结束时间
        activity.setBegintime(TimeUtil.replaceDate1(TimeUtil.getTimes(0),seckillTime.getStarttime()));
        activity.setEndtime(TimeUtil.replaceDate1(TimeUtil.getTimes(0),seckillTime.getEndtime()));

        //如果结束时间<开始时间，则重新计算结束时间
        if(activity.getEndtime().getTime()<=activity.getBegintime().getTime()){
            activity.setEndtime(TimeUtil.replaceDate1(TimeUtil.addDateHour(TimeUtil.getTimes(0),24),seckillTime.getEndtime()));
        }
        activity.setTimeId(seckillTime.getId());
        float times = TimeUtil.dif2hour(activity.getBegintime(), activity.getEndtime());
        activity.setTotalTime(times);
        activityMapper.insertSelective(activity);
        return activity;
    }

    /***
     * 删除所有活动
     */
    @Override
    public void deleteAll() {
        activityMapper.delete(null);
    }
}
