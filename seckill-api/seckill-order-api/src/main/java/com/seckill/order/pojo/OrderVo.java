package com.seckill.order.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.seckill.goods.pojo.Sku;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/****
 * @Author:www.itheima.com
 * @Description:Order构建
 * @Date  19:13
 *****/
public class OrderVo implements Serializable{

	private String id;//订单id

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createTime;//订单创建时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
