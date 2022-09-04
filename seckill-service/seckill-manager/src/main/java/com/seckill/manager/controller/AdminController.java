package com.seckill.manager.controller;
import com.seckill.manager.pojo.Admin;
import com.seckill.manager.service.AdminService;
import com.github.pagehelper.PageInfo;
import com.seckill.util.JwtTokenUtil;
import com.seckill.util.Result;
import com.seckill.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/****
 * @Author:www.itheima.com
 * @Description:
 * @Date  0:18
 *****/
@RestController
@RequestMapping("/admin")
//@CrossOrigin
public class AdminController {

    @Autowired
    private AdminService adminService;


    /***
     * 根据ID查询User数据
     * @return
     */
    @PostMapping("/login")
    public Result<Admin> findById(@RequestBody Map<String,String> dataMap) throws Exception {
        //调用UserService实现根据主键查询User
        Admin admin = adminService.findByName(dataMap.get("username"));
        if(admin==null){
            return new Result<Admin>(false,StatusCode.ERROR,"账号不存在");
        }

        if(!admin.getPassword().equals(dataMap.get("password"))){
            return new Result<Admin>(false,StatusCode.ERROR,"密码错误");
        }

        //登录成功，生成令牌
        Map<String,Object> payload = new HashMap<String,Object>();
        payload.put("admin",admin.getLoginName());

        //生成令牌
        String jwt = JwtTokenUtil.generateTokenAdmin(UUID.randomUUID().toString(),payload, 900000000L);
        return new Result<Admin>(true,StatusCode.OK,"登录成功",jwt);
    }

}
