package com.seckill.goods.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/****
 * @Author:www.itheima.com
 * @Description:Activity构建
 * @Date  19:13
 *****/
@Table(name="tb_activity")
public class Activity implements Serializable{

	@Id
	@Column(name = "id")
	private String id;//

    @Column(name = "name")
	private String name;//

    @Column(name = "status")
	private Integer status;//状态：1开启，2未开启

    @Column(name = "startdate")
	private Date startdate;//

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "begintime")
	private Date begintime;//开始时间，单位：时分秒

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "endtime")
	private Date endtime;//结束时间，单位：时分秒

    @Column(name = "total_time")
	private Float totalTime;//

	@Column(name = "is_del")
	private Integer isDel;//

	//时间ID
	@Column(name = "time_id")
	private Integer timeId;

	//时间信息
	private SeckillTime seckillTime;

	//所有时间列表
	private List<SeckillTime> seckillTimeList;

	@Override
	public String toString() {
		return "Activity{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", status=" + status +
				", startdate=" + startdate +
				", begintime=" + begintime +
				", endtime=" + endtime +
				", totalTime=" + totalTime +
				", isDel=" + isDel +
				", timeId=" + timeId +
				", seckillTime=" + seckillTime +
				", seckillTimeList=" + seckillTimeList +
				'}';
	}

	public List<SeckillTime> getSeckillTimeList() {
		return seckillTimeList;
	}

	public void setSeckillTimeList(List<SeckillTime> seckillTimeList) {
		this.seckillTimeList = seckillTimeList;
	}

	public Integer getTimeId() {
		return timeId;
	}

	public void setTimeId(Integer timeId) {
		this.timeId = timeId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getBegintime() {
		return begintime;
	}

	public void setBegintime(Date begintime) {
		this.begintime = begintime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public Float getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Float totalTime) {
		this.totalTime = totalTime;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public SeckillTime getSeckillTime() {
		return seckillTime;
	}

	public void setSeckillTime(SeckillTime seckillTime) {
		this.seckillTime = seckillTime;
	}
}
