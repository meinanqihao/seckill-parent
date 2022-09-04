package com.seckill.goods.controller;

import com.github.pagehelper.PageInfo;
import com.seckill.goods.pojo.Sku;
import com.seckill.goods.service.SkuService;
import com.seckill.util.Result;
import com.seckill.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author:www.itheima.com
 * @Description:
 * @Date  0:18
 *****/

@RestController
@RequestMapping("/sku")
//@CrossOrigin
public class SkuController {

    @Autowired
    private SkuService skuService;



    /****
     * 删除指定商品
     * @param id
     * @return
     */
    @DeleteMapping(value = "/del/{id}")
    public Result<Sku> del(@PathVariable(value = "id")String id){
        skuService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功！");
    }

    /****
     * 库存递减
     */
    @PutMapping(value = "/dcount/{id}/{count}")
    public Result<Sku> dcount(@PathVariable(value = "id")String id,@PathVariable(value = "count")Integer count){
        //1.调用业务层实现递减
        int code = skuService.dcount(id, count);
        String message="";
        Sku sku = null;
        switch (code){
            case StatusCode.DECOUNT_OK:
                sku = skuService.findById(id);
                message="库存递减成功！";
                break;
            case StatusCode.DECOUNT_NUM:
                message="库存不足！";
                break;
            case StatusCode.DECOUNT_HOT:
                message="商品是热点商品！";
                break;
                default:
        }
        //3.根据状态码，响应不同的提示信息
        return  new Result<Sku>(true,code,message,sku);
    }

    /***
     * 热点商品隔离
     */
    @PostMapping(value = "/hot/isolation")
    public Result hotIsolation(@RequestParam List<String> ids){
        for (String id : ids) {
            skuService.hotIsolation(id);
        }
        return new Result(true,StatusCode.OK,"热点数据隔离成功！");
    }

    /****
     * 分页查询-查询总数量
     */
    @GetMapping(value = "/count")
    public Integer count(){
        return skuService.count();
    }


    /****
     * 分页查询集合列表
     */
    @GetMapping(value = "/list/{page}/{size}")
    public List<Sku> list(@PathVariable(value = "page")Integer page,@PathVariable(value = "size")Integer size){
        return skuService.list(page,size);
    }

    /***
     * 指定活动下的秒杀商品
     */
    @GetMapping(value = "/activity/{id}/{page}/{size}")
    public Result<PageInfo<Sku>> actList(@PathVariable(value = "id")String id,
                                     @PathVariable(value = "page")Integer page,
                                     @PathVariable(value = "size")Integer size){
        PageInfo<Sku> pageInfo = skuService.findSkuByActivityId(id,page,size);
        return new Result(true,StatusCode.OK,"查询成功！",pageInfo);
    }

    /***
     * 锁定商品
     */
    @GetMapping(value = "/lock/{id}")
    public Result lock(@PathVariable(value = "id") String id){
        //锁定商品
        skuService.lock(id);
        return new Result(true,StatusCode.OK,"锁定商品成功");
    }


    /***
     * 解锁商品
     */
    @GetMapping(value = "/unlock/{id}")
    public Result unlock(@PathVariable(value = "id") String id){
        //解锁商品
        skuService.unlock(id);
        return new Result(true,StatusCode.OK,"解锁商品成功");
    }

    /***
     * Sku分页条件搜索实现
     * @param sku
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false)  Sku sku, @PathVariable  int page, @PathVariable  int size){
//        sku.setName(sku.getName().replace("  "," "));
        //调用SkuService实现分页条件查询Sku
        sku.setStatus("1"); //普通商品
        PageInfo<Sku> pageInfo = skuService.findPage(sku, page, size);
        return new Result(true, StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * Sku分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size){
        //调用SkuService实现分页查询Sku
        PageInfo<Sku> pageInfo = skuService.findPage(page, size);
        return new Result<PageInfo>(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * 多条件搜索品牌数据
     * @param sku
     * @return
     */
    @PostMapping(value = "/search" )
    public Result<List<Sku>> findList(@RequestBody(required = false)  Sku sku){
        //调用SkuService实现条件查询Sku
        List<Sku> list = skuService.findList(sku);
        return new Result<List<Sku>>(true,StatusCode.OK,"查询成功",list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable String id){
        //调用SkuService实现根据主键删除
        skuService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 修改Sku数据
     * @param sku
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    public Result update(@RequestBody  Sku sku,@PathVariable String id){
        //时间操作
        //Date beginTime =new Date( sku.getStartDate().getTime()+ TimeUtil.dateFormatHHmm(sku.getStartTimestr()).getTime() );
        //sku.setSeckillBegin(beginTime);

        //N 小时之后，计算活动结束时间
        //Date endTime = TimeUtil.addDateHour(beginTime,sku.getLen());
        //sku.setSeckillEnd(endTime);

        //设置主键值
        sku.setId(id);
        //调用SkuService实现修改Sku
        skuService.update(sku);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /***
     * 新增Sku数据
     * @param sku
     * @return
     */
    @PostMapping
    public Result add(@RequestBody   Sku sku){
        //调用SkuService实现添加Sku
        skuService.add(sku);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /***
     * 根据ID查询Sku数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Sku> findById(@PathVariable String id){
        //调用SkuService实现根据主键查询Sku
        Sku sku = skuService.findById(id);
        return new Result<Sku>(true,StatusCode.OK,"查询成功",sku);
    }

    /***
     * 查询Sku全部数据
     * @return
     */
    @GetMapping
    public Result<List<Sku>> findAll(){
        //调用SkuService实现查询所有Sku
        List<Sku> list = skuService.findAll();
        return new Result<List<Sku>>(true, StatusCode.OK,"查询成功",list) ;
    }

    /***
     * 商品数据归0
     */
    @GetMapping(value = "/sku/zero/{id}")
    Result zero(@PathVariable(value = "id") String id){
        skuService.zero(id);
        return new Result(true, StatusCode.OK,"数据归零成功") ;
    }
}



