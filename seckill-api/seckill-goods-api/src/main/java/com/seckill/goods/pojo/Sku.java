package com.seckill.goods.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.omg.CORBA.INTERNAL;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/****
 * @Author:www.itheima.com
 * @Description:Sku构建
 * @Date  19:13
 *****/
@Table(name="tb_sku")
public class Sku implements Serializable{

	@Id
    @Column(name = "id")
	private String id;//商品id

    @Column(name = "name")
	private String name;//SKU名称

    @Column(name = "price")
	private Integer price;//价格（分）

    @Column(name = "seckill_price")
	private Integer seckillPrice;//单位，分

    @Column(name = "num")
	private Integer num;//库存数量

    @Column(name = "alert_num")
	private Integer alertNum;//库存预警数量

    @Column(name = "image")
	private String image;//商品图片

    @Column(name = "images")
	private String images;//商品图片列表

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@Column(name = "create_time")
	private Date createTime;//创建时间

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "update_time")
	private Date updateTime;//更新时间

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@Column(name = "seckill_begin")
	private Date seckillBegin;//秒杀开始时间

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@Column(name = "seckill_end")
	private Date seckillEnd;//秒杀结束时间

    @Column(name = "spu_id")
	private String spuId;//SPUID

    @Column(name = "category1_id")
	private Integer category1Id;//类目ID

    @Column(name = "category2_id")
	private Integer category2Id;//

    @Column(name = "category3_id")
	private Integer category3Id;//

    @Column(name = "category1_name")
	private String category1Name;//

    @Column(name = "category2_name")
	private String category2Name;//

    @Column(name = "category3_name")
	private String category3Name;//类目名称

    @Column(name = "brand_id")
	private Integer brandId;//

    @Column(name = "brand_name")
	private String brandName;//品牌名称

    @Column(name = "spec")
	private String spec;//规格

    @Column(name = "sale_num")
	private Integer saleNum;//销量

    @Column(name = "comment_num")
	private Integer commentNum;//评论数

    @Column(name = "status")
	private String status;//商品状态 1-正常，2-下架，3-删除

	@Column(name = "islock")
	private Integer islock;//商品状态 1正常，2锁定

	//秒杀开始日期
	@Transient
	private Date startDate;

	//秒杀开始时间
	@Transient
	private String startTimestr;

	//持续时长
	@Transient
	private Integer len;

	//秒杀数量
	@Column(name = "seckill_num")
	private Integer seckillNum;

	//商品审核状态   1 已审核
	@Column(name = "audit")
	private Integer audit;

	//限购数量
	@Column(name = "count")
	private Integer count;

	//是否删除  1未删除，2已删除
	@Column(name="isdel")
	private Integer isdel;

	@Transient
	private Integer points;

	public Integer getPoints() {
		//计算
		//return (seckillNum*100) / saleNum;
		return 80;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Integer getIsdel() {
		return isdel;
	}

	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getAudit() {
		return audit;
	}

	public void setAudit(Integer audit) {
		this.audit = audit;
	}

	public Integer getSeckillNum() {
		return seckillNum;
	}

	public void setSeckillNum(Integer seckillNum) {
		this.seckillNum = seckillNum;
	}

	public Integer getLen() {
		return len;
	}

	public void setLen(Integer len) {
		this.len = len;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getStartTimestr() {
		return startTimestr;
	}

	public void setStartTimestr(String startTimestr) {
		this.startTimestr = startTimestr;
	}

	public Integer getIslock() {
		return islock;
	}

	public void setIslock(Integer islock) {
		this.islock = islock;
	}

	public Date getSeckillBegin() {
		return seckillBegin;
	}

	public void setSeckillBegin(Date seckillBegin) {
		this.seckillBegin = seckillBegin;
	}

	public Date getSeckillEnd() {
		return seckillEnd;
	}

	public void setSeckillEnd(Date seckillEnd) {
		this.seckillEnd = seckillEnd;
	}

	//get方法
	public String getId() {
		return id;
	}

	//set方法
	public void setId(String id) {
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
	public Integer getPrice() {
		return price;
	}

	//set方法
	public void setPrice(Integer price) {
		this.price = price;
	}
	//get方法
	public Integer getSeckillPrice() {
		return seckillPrice;
	}

	//set方法
	public void setSeckillPrice(Integer seckillPrice) {
		this.seckillPrice = seckillPrice;
	}
	//get方法
	public Integer getNum() {
		return num;
	}

	//set方法
	public void setNum(Integer num) {
		this.num = num;
	}
	//get方法
	public Integer getAlertNum() {
		return alertNum;
	}

	//set方法
	public void setAlertNum(Integer alertNum) {
		this.alertNum = alertNum;
	}
	//get方法
	public String getImage() {
		return image;
	}

	//set方法
	public void setImage(String image) {
		this.image = image;
	}
	//get方法
	public String getImages() {
		return images;
	}

	//set方法
	public void setImages(String images) {
		this.images = images;
	}
	//get方法
	public Date getCreateTime() {
		return createTime;
	}

	//set方法
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	//get方法
	public Date getUpdateTime() {
		return updateTime;
	}

	//set方法
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	//get方法
	public String getSpuId() {
		return spuId;
	}

	//set方法
	public void setSpuId(String spuId) {
		this.spuId = spuId;
	}
	//get方法
	public Integer getCategory1Id() {
		return category1Id;
	}

	//set方法
	public void setCategory1Id(Integer category1Id) {
		this.category1Id = category1Id;
	}
	//get方法
	public Integer getCategory2Id() {
		return category2Id;
	}

	//set方法
	public void setCategory2Id(Integer category2Id) {
		this.category2Id = category2Id;
	}
	//get方法
	public Integer getCategory3Id() {
		return category3Id;
	}

	//set方法
	public void setCategory3Id(Integer category3Id) {
		this.category3Id = category3Id;
	}
	//get方法
	public String getCategory1Name() {
		return category1Name;
	}

	//set方法
	public void setCategory1Name(String category1Name) {
		this.category1Name = category1Name;
	}
	//get方法
	public String getCategory2Name() {
		return category2Name;
	}

	//set方法
	public void setCategory2Name(String category2Name) {
		this.category2Name = category2Name;
	}
	//get方法
	public String getCategory3Name() {
		return category3Name;
	}

	//set方法
	public void setCategory3Name(String category3Name) {
		this.category3Name = category3Name;
	}
	//get方法
	public Integer getBrandId() {
		return brandId;
	}

	//set方法
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	//get方法
	public String getBrandName() {
		return brandName;
	}

	//set方法
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	//get方法
	public String getSpec() {
		return spec;
	}

	//set方法
	public void setSpec(String spec) {
		this.spec = spec;
	}
	//get方法
	public Integer getSaleNum() {
		return saleNum;
	}

	//set方法
	public void setSaleNum(Integer saleNum) {
		this.saleNum = saleNum;
	}
	//get方法
	public Integer getCommentNum() {
		return commentNum;
	}

	//set方法
	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}
	//get方法
	public String getStatus() {
		return status;
	}

	//set方法
	public void setStatus(String status) {
		this.status = status;
	}


}
