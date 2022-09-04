package com.seckill.monitor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/*****
 * @Author: http://www.itheima.com
 * @Description: com.seckill.monitor.MonitorItemsAccess
 ****/
@Component
public class MonitorItemsAccess {

    @Value("${druidurl}")
    private String druidurl;


    /*****
     * 从ApacheDruid中查询指定数据
     */
    public List<String> loadData() throws Exception{
        //2.创建链接配置
        Properties connectionProperties = new Properties();
        String sql = "SELECT COUNT(\"count\") AS \"ViewCount\",\"uri\" FROM \"itheima\" WHERE \"__time\" >= CURRENT_TIMESTAMP - INTERVAL '2' HOUR GROUP BY \"uri\" ORDER BY \"ViewCount\" DESC LIMIT ?";
        //3.获得链接对象
        Connection connection = DriverManager.getConnection(druidurl, connectionProperties);
        //4.Statment
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,5);

        //5.执行DruidSQL
        ResultSet resultSet = preparedStatement.executeQuery();

        //6.获取指定结果集
        List<String> ids = new ArrayList<String>();
        while (resultSet.next()){
            String uri = resultSet.getString("uri");
            System.out.println(resultSet.getString("ViewCount"));
            // /items/ 2020 .html
            uri=uri.replaceFirst("/items/","").replace(".html","");
            ids.add(uri);
        }

        //7.关闭资源
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return ids;
    }
}
