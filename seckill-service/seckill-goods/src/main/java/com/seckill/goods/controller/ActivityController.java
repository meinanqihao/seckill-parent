package com.seckill.goods.controller;

import com.github.pagehelper.PageInfo;
import com.seckill.goods.pojo.Activity;
import com.seckill.goods.service.ActivityService;
import com.seckill.util.Result;
import com.seckill.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;

/****
 * @Author:www.itheima.com
 * @Description:
 * @Date  0:18
 *****/

@RestController
@RequestMapping("/activity")
//@CrossOrigin
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    /***
     * Activity上线/下线
     */
    @PutMapping(value = "/status/{id}/{isup}" )
    public Result<PageInfo> findPage(@PathVariable(value = "id")String id,@PathVariable(value = "isup")int isup){
        //上线下线
        activityService.isUp(id,isup);
        return new Result(true, StatusCode.OK,"操作成功");
    }

    /***
     * Activity分页条件搜索实现
     * @param activity
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false)  Activity activity, @PathVariable  int page, @PathVariable  int size){
        //调用ActivityService实现分页条件查询Activity
        activity.setIsDel(1);   //删除：1未删除，2已删除
        PageInfo<Activity> pageInfo = activityService.findPage(activity, page, size);
        return new Result(true, StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * Activity分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size){
        //调用ActivityService实现分页查询Activity
        PageInfo<Activity> pageInfo = activityService.findPage(page, size);
        return new Result<PageInfo>(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * 多条件搜索品牌数据
     * @param activity
     * @return
     */
    @PostMapping(value = "/search" )
    public Result<List<Activity>> findList(@RequestBody(required = false)  Activity activity){
        //调用ActivityService实现条件查询Activity
        List<Activity> list = activityService.findList(activity);
        return new Result<List<Activity>>(true,StatusCode.OK,"查询成功",list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable String id){
        //调用ActivityService实现根据主键删除
        activityService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 修改Activity数据
     * @param activity
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    public Result update(@RequestBody  Activity activity,@PathVariable String id){
        //设置主键值
        activity.setId(id);
        //调用ActivityService实现修改Activity
        activityService.update(activity);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /***
     * 新增Activity数据
     * @param activity
     * @return
     */
    @PostMapping
    public Result add(@RequestBody   Activity activity){
        //调用ActivityService实现添加Activity
        activityService.add(activity);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /***
     * 根据ID查询Activity数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Activity> findById(@PathVariable String id){
        //调用ActivityService实现根据主键查询Activity
        Activity activity = activityService.findById(id);
        return new Result<Activity>(true,StatusCode.OK,"查询成功",activity);
    }

    /***
     * 查询Activity全部数据
     * @return
     */
    @GetMapping
    public Result<List<Activity>> findAll(){
        //调用ActivityService实现查询所有Activity
        List<Activity> list = activityService.findAll();
        return new Result<List<Activity>>(true, StatusCode.OK,"查询成功",list) ;
    }


    /***
     * 时间接口
     */
    @GetMapping(value = "/times")
    public Result<List<Activity>> times(){
        List<Activity> times = activityService.times();
        for (Activity time : times) {
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println(sd.format(time.getBegintime()));
        }
        return new Result<List<Activity>>(true, StatusCode.OK,"查询成功",times) ;
    }
}


