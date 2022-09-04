package com.seckill.goods.dao;
import com.seckill.goods.pojo.Activity;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/****
 * @Author:www.itheima.com
 * @Description:Activity的Dao
 * @Date  0:12
 *****/
public interface ActivityMapper extends Mapper<Activity> {

    /***
     * 时间查询
     * @return
     */
    @Select("(SELECT * FROM tb_activity WHERE begintime<NOW() ORDER BY begintime DESC LIMIT 1) UNION (SELECT * FROM tb_activity WHERE begintime>=NOW() ORDER BY begintime ASC)  LIMIT 5")
    List<Activity> times();
}
