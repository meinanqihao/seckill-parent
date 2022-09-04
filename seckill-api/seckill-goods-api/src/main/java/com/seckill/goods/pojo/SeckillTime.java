package com.seckill.goods.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/****
 * @Author:www.itheima.com
 * @Description:SeckillTime构建
 * @Date  19:13
 *****/
@Table(name="tb_seckill_time")
public class SeckillTime implements Serializable{

	@Id
    @Column(name = "id")
	private Integer id;//

    @Column(name = "name")
	private String name;//秒杀分类名字,双十一秒杀，每日时段秒杀等

	//时间格式化
	@JsonFormat(pattern = "HH:mm")
    @Column(name = "starttime")
	private Date starttime;//开始时间

	//时间格式化
	@JsonFormat(pattern = "HH:mm")
	@Column(name = "endtime")
	private Date endtime;

    @Column(name = "total_time")
	private Float totalTime;//秒杀时长,按小时计算

    @Column(name = "status")
	private Integer status;//秒杀分类，1：启用，2：不启用

	@Column(name = "sort")
	private Integer sort;

	@Override
	public String toString() {
		return "SeckillTime{" +
				"id=" + id +
				", name='" + name + '\'' +
				", starttime=" + starttime +
				", endtime=" + endtime +
				", totalTime=" + totalTime +
				", status=" + status +
				'}';
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	//get方法
	public Integer getId() {
		return id;
	}

	//set方法
	public void setId(Integer id) {
		this.id = id;
	}
	//get方法
	public String getName() {
		return name;
	}

	//set方法
	public void setName(String name) {
		this.name = name;
	}
	//get方法
	public Date getStarttime() {
		return starttime;
	}

	//set方法
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Float getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Float totalTime) {
		this.totalTime = totalTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public static void main(String[] args) throws ParseException {
		String str = "12:00";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
		Date parse = simpleDateFormat.parse(str);
		System.out.println(parse);
	}
}
