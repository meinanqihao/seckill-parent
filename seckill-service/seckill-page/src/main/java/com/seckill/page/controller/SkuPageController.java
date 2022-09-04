package com.seckill.page.controller;

import com.seckill.goods.pojo.Sku;
import com.seckill.page.service.ItemPageService;
import com.seckill.util.Result;
import com.seckill.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/*****
 * @Author: http://www.itheima.com
 * @Description: com.seckill.page.controller.SkuPageController
 ****/
@RestController
@RequestMapping(value = "/page")
public class SkuPageController {

    @Autowired
    private ItemPageService itemPageService;

    /***
     * 生成静态页
     */
    @PostMapping(value = "/html")
    public Result createHtml(@RequestBody Sku sku) throws Exception {
        Map<String,Object> dataMap = new HashMap<String,Object>();
        dataMap.put("templateName","1.ftl");
        dataMap.put("filename",sku.getId()+".html");   //商品的  【id.html】

        //生成静态页
        itemPageService.createHtml(dataMap);
        return new Result(true, StatusCode.OK,"生成静态页成功！");
    }


    /***
     * 文件删除
     */
    @DeleteMapping(value = "/html/{id}")
    public Result delete(@PathVariable(value = "id")String id) throws Exception {
        //删除静态页
        itemPageService.delete(id+".html");
        return new Result(true, StatusCode.OK,"删除静态页成功！");
    }


}
