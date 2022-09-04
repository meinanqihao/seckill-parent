package com.seckill.page.process;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/*****
 * @Author: http://www.itheima.com
 * @Description: com.seckill.page.process.BaseProcess
 ****/
public class BaseProcess {

    @Autowired
    private Configuration configuration;

    @Value("${filepath}")
    private String filepath;

    public String getFilepath() {
        return filepath;
    }

    /****
     * Configuration：配置对象
     *      1、指定模板对象
     *      2、生成的文件路径在哪里
     *      3、指定数据模型
     */
    public void writePage(Map<String,Object> dataMap) throws Exception{
        //1、指定模板对象
        String templateName = dataMap.get("templateName").toString();
        Template template = configuration.getTemplate(templateName);
        //3、指定数据模型
        //融合过程
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, dataMap);

        //2、生成的文件路径在哪里
        String fileName = dataMap.get("filename").toString();   //获取文件名字
        String path = filepath+fileName;
        FileUtils.writeStringToFile(new File(path),content);
    }


    /****
     * Configuration：配置对象
     *      1、指定模板对象
     *      2、生成的文件路径在哪里
     *      3、指定数据模型
     */
    public void writePage1() throws Exception{
        //1、指定模板对象
        String templateName = "1.ftl";
        Template template = configuration.getTemplate(templateName);
        //3、指定数据模型
        Map<String,Object> dataMap = new HashMap<String,Object>();
        dataMap.put("title","华为P40");
        dataMap.put("category","手机");
        //融合过程
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, dataMap);

        //2、生成的文件路径在哪里
        String filepath = "D:/project/jgs/seckill-parent/pages/1.html";
        FileUtils.writeStringToFile(new File(filepath),content);
    }

}
