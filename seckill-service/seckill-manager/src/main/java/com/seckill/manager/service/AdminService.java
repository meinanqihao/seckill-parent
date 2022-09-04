package com.seckill.manager.service;
import com.seckill.manager.pojo.Admin;
import com.github.pagehelper.PageInfo;
import java.util.List;
/****
 * @Author:www.itheima.com
 * @Description:Admin业务层接口
 * @Date  0:16
 *****/
public interface AdminService {

    Admin findByName(String username);
}
