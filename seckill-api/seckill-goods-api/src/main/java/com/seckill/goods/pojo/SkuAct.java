package com.seckill.goods.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/****
 * @Author:www.itheima.com
 * @Description:SkuAct构建
 * @Date  19:13
 *****/
@Table(name="tb_sku_act")
public class SkuAct implements Serializable{

	@Id
    @Column(name = "sku_id")
	private String skuId;//

    @Column(name = "activity_id")
	private String activityId;//

	//是否删除
	@Column(name = "is_del")
	private Integer isDel;

	//Sku集合
	private List<Sku> skus;

	public List<Sku> getSkus() {
		return skus;
	}

	public void setSkus(List<Sku> skus) {
		this.skus = skus;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	//get方法
	public String getSkuId() {
		return skuId;
	}

	//set方法
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	//get方法
	public String getActivityId() {
		return activityId;
	}

	//set方法
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}


}
