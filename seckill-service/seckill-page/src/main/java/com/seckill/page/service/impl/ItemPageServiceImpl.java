package com.seckill.page.service.impl;

import com.seckill.page.process.BaseProcess;
import com.seckill.page.service.ItemPageService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;

/*****
 * @Author: http://www.itheima.com
 * @Description: com.seckill.page.service.impl.ItemPageServiceImpl
 ****/
@Service
public class ItemPageServiceImpl extends BaseProcess implements ItemPageService {

    /****
     * 生成静态页
     * @param dataMap
     * @throws Exception
     */
    @Override
    public void createHtml(Map<String, Object> dataMap) throws Exception {
        dataMap.put("title","华为Mate40 Pro");
        dataMap.put("category","手机");

        super.writePage(dataMap);
    }

    /***
     * 删除
     * @param htmlName
     * @throws Exception
     */
    @Override
    public void delete(String htmlName) throws Exception {
        // 路径+fileName
        String fileInfo = super.getFilepath()+htmlName;

        File file = new File(fileInfo);
        if(file.exists()){
            file.delete();
        }
    }

}
