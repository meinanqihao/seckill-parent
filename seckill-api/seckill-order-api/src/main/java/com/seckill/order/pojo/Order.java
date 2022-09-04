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
@Table(name="tb_order")
public class Order implements Serializable{

	@Id
    @Column(name = "id")
	private String id;//订单id

    @Column(name = "total_num")
	private Integer totalNum;//数量合计

    @Column(name = "pay_type")
	private String payType;//支付类型，1、在线支付、0 货到付款

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "create_time")
	private Date createTime;//订单创建时间

    @Column(name = "update_time")
	private Date updateTime;//订单更新时间

    @Column(name = "pay_time")
	private Date payTime;//付款时间

    @Column(name = "consign_time")
	private Date consignTime;//发货时间

    @Column(name = "end_time")
	private Date endTime;//交易完成时间

    @Column(name = "close_time")
	private Date closeTime;//交易关闭时间

    @Column(name = "receiver_contact")
	private String receiverContact;//收货人

    @Column(name = "receiver_mobile")
	private String receiverMobile;//收货人手机

    @Column(name = "receiver_address")
	private String receiverAddress;//收货人地址

    @Column(name = "transaction_id")
	private String transactionId;//交易流水号

    @Column(name = "order_status")
	private String orderStatus;//订单状态,0:未完成,1:已完成，2：已退货

    @Column(name = "pay_status")
	private String payStatus;//支付状态,0:未支付，1：已支付，2：支付失败

    @Column(name = "consign_status")
	private String consignStatus;//发货状态,0:未发货，1：已发货，2：已收货

    @Column(name = "is_delete")
	private String isDelete;//是否删除

    @Column(name = "sku_id")
	private String skuId;//

    @Column(name = "name")
	private String name;//

    @Column(name = "price")
	private Integer price;//

	//用户账号
	@Column(name = "username")
	private String username;//

	//商品
	private Sku sku;

	public Sku getSku() {
		return sku;
	}

	public void setSku(Sku sku) {
		this.sku = sku;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
	public Integer getTotalNum() {
		return totalNum;
	}

	//set方法
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
	//get方法
	public String getPayType() {
		return payType;
	}

	//set方法
	public void setPayType(String payType) {
		this.payType = payType;
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
	public Date getPayTime() {
		return payTime;
	}

	//set方法
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	//get方法
	public Date getConsignTime() {
		return consignTime;
	}

	//set方法
	public void setConsignTime(Date consignTime) {
		this.consignTime = consignTime;
	}
	//get方法
	public Date getEndTime() {
		return endTime;
	}

	//set方法
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	//get方法
	public Date getCloseTime() {
		return closeTime;
	}

	//set方法
	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}
	//get方法
	public String getReceiverContact() {
		return receiverContact;
	}

	//set方法
	public void setReceiverContact(String receiverContact) {
		this.receiverContact = receiverContact;
	}
	//get方法
	public String getReceiverMobile() {
		return receiverMobile;
	}

	//set方法
	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}
	//get方法
	public String getReceiverAddress() {
		return receiverAddress;
	}

	//set方法
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
	//get方法
	public String getTransactionId() {
		return transactionId;
	}

	//set方法
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	//get方法
	public String getOrderStatus() {
		return orderStatus;
	}

	//set方法
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	//get方法
	public String getPayStatus() {
		return payStatus;
	}

	//set方法
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	//get方法
	public String getConsignStatus() {
		return consignStatus;
	}

	//set方法
	public void setConsignStatus(String consignStatus) {
		this.consignStatus = consignStatus;
	}
	//get方法
	public String getIsDelete() {
		return isDelete;
	}

	//set方法
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
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


}
