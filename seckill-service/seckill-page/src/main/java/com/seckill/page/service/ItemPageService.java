package com.seckill.page.service;

import java.util.Map;

/*****
 * @Author: http://www.itheima.com
 * @Description: com.seckill.page.service.ItemPageService
 ****/
public interface ItemPageService {

    /***
     * 生成静态页
     * @throws Exception
     */
    void createHtml(Map<String,Object> dataMap) throws Exception;


    /***
     * 删除详情页
     */
    void delete(String htmlName) throws Exception;
}
