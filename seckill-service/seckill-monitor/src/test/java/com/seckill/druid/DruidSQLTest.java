package com.seckill.druid;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/*****
 * @Author: http://www.itheima.com
 * @Description: com.seckill.druid.DruidSQLTest
 ****/
public class DruidSQLTest {
    public static void main(String[] args) throws Exception{
        //1.准备URL
        String url = "jdbc:avatica:remote:url=http://192.168.211.142:8082/druid/v2/sql/avatica/";
        //2.创建链接
        //3.创建Statment
        //4.执行查询
        //5.解析数据
        //6.关闭资源
        Properties connectionProperties = new Properties();
        String sql = "SELECT * FROM itheima";
        try (Connection connection = DriverManager.getConnection(url, connectionProperties)) {
            try (
                    final Statement statement = connection.createStatement();
                    final ResultSet resultSet = statement.executeQuery(sql)
            ) {
                while (resultSet.next()) {
                    System.out.println(resultSet.getString("ip") + ":" + resultSet.getString("uri"));
                }
            }
        }
    }
}
