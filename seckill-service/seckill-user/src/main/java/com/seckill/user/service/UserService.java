package com.seckill.user.service;

import com.github.pagehelper.PageInfo;
import com.seckill.user.pojo.User;

import java.util.List;

/****
 * @Author:www.itheima.com
 * @Description:User业务层接口
 * @Date  0:16
 *****/
public interface UserService {

    /***
     * 用户登录
     */
    User findById(String username);

    /***
     * User多条件分页查询
     * @param user
     * @param page
     * @param size
     * @return
     */
    PageInfo<User> findPage(User user, int page, int size);

}
