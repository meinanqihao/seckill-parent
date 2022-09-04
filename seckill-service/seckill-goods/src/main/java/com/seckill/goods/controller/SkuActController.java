package com.seckill.goods.controller;

import com.github.pagehelper.PageInfo;
import com.seckill.goods.pojo.SkuAct;
import com.seckill.goods.service.SkuActService;
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
@RequestMapping("/skuAct")
//@CrossOrigin
public class SkuActController {

    @Autowired
    private SkuActService skuActService;

    /***
     * SkuAct分页条件搜索实现
     * @param skuAct
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false)  SkuAct skuAct, @PathVariable  int page, @PathVariable  int size){
        //调用SkuActService实现分页条件查询SkuAct
        PageInfo<SkuAct> pageInfo = skuActService.findPage(skuAct, page, size);
        return new Result(true, StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * SkuAct分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size){
        //调用SkuActService实现分页查询SkuAct
        PageInfo<SkuAct> pageInfo = skuActService.findPage(page, size);
        return new Result<PageInfo>(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * 多条件搜索品牌数据
     * @param skuAct
     * @return
     */
    @PostMapping(value = "/search" )
    public Result<List<SkuAct>> findList(@RequestBody(required = false)  SkuAct skuAct){
        //调用SkuActService实现条件查询SkuAct
        List<SkuAct> list = skuActService.findList(skuAct);
        return new Result<List<SkuAct>>(true,StatusCode.OK,"查询成功",list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable String id){
        //调用SkuActService实现根据主键删除
        skuActService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 修改SkuAct数据
     * @param skuAct
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    public Result update(@RequestBody  SkuAct skuAct,@PathVariable String id){
        //设置主键值
        skuAct.setSkuId(id);
        //调用SkuActService实现修改SkuAct
        skuActService.update(skuAct);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /***
     * 新增SkuAct数据
     * @param skuAct
     * @return
     */
    @PostMapping
    public Result add(@RequestBody   SkuAct skuAct){
        //调用SkuActService实现添加SkuAct
        skuActService.add(skuAct);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /***
     * 根据ID查询SkuAct数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<SkuAct> findById(@PathVariable String id){
        //调用SkuActService实现根据主键查询SkuAct
        SkuAct skuAct = skuActService.findById(id);
        return new Result<SkuAct>(true,StatusCode.OK,"查询成功",skuAct);
    }

    /***
     * 查询SkuAct全部数据
     * @return
     */
    @GetMapping
    public Result<List<SkuAct>> findAll(){
        //调用SkuActService实现查询所有SkuAct
        List<SkuAct> list = skuActService.findAll();
        return new Result<List<SkuAct>>(true, StatusCode.OK,"查询成功",list) ;
    }
}
