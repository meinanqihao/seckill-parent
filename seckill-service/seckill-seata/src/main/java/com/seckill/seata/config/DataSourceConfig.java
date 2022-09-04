package com.seckill.seata.config;

import com.alibaba.druid.pool.DruidDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/*****
 * @Author: http://www.itheima.com
 * @Description: com.seckill.seata.config.DataSourceConfig
 ****/
@Configuration
public class DataSourceConfig {

    /****
     * 1.配置DataSource
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DruidDataSource dataSource(){
        return new DruidDataSource();
    }


    /***
     * 2.配置代理数据源
     */
    @Bean
    public DataSourceProxy dataSourceProxy(DruidDataSource dataSource){
        return new DataSourceProxy(dataSource);
    }


    /***
     * 3.持久层用的是MyBatis
     *   SqlSessionFactory需要注入DataSource，将注入的DataSource换成代理数据源
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSourceProxy dataSourceProxy) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSourceProxy);
        return sqlSessionFactoryBean.getObject();
    }
}
