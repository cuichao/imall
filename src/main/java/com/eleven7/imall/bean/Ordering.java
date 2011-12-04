package com.eleven7.imall.bean;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ordering")
public class Ordering {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false)
	private int id;
	@Column
	private int userid;
	@Column
	private int addressid;//保存下来，与下面toAddress信息冗余，考虑日后可能有删除address的功能
	@Column(length = 255)
	private String toAddress;
	@Column(length = 30)
	private String accepter;
	@Column
	private int sendType;
	@Enumerated(value = EnumType.ORDINAL)
	private TimeType timeType = TimeType.WEEK_1_TO_5;
	@Enumerated(value = EnumType.ORDINAL)
	private PayType payType = PayType.PAY_ONLINE;
	@Column
	private double carriageCost;
	@Column
	private double totalpay;
	@Column
	private Date createtime = new Date();
	@Column
	private Date updatetime;
	@Column
	@Enumerated(value = EnumType.ORDINAL)
	private OrderStatus status = OrderStatus.prePay;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public double getTotalpay() {
		return totalpay;
	}
	public void setTotalpay(double totalpay) {
		this.totalpay = totalpay;
	}
	public String getToAddress() {
		return toAddress;
	}
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}
	public String getAccepter() {
		return accepter;
	}
	public void setAccepter(String accepter) {
		this.accepter = accepter;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	
	public int getAddressid() {
		return addressid;
	}
	public void setAddressid(int addressid) {
		this.addressid = addressid;
	}
	public int getSendType() {
		return sendType;
	}
	public void setSendType(int sendType) {
		this.sendType = sendType;
	}
	public TimeType getTimeType() {
		return timeType;
	}
	public void setTimeType(TimeType timeType) {
		this.timeType = timeType;
	}
	public PayType getPayType() {
		return payType;
	}
	public void setPayType(PayType payType) {
		this.payType = payType;
	}
	public double getCarriageCost() {
		return carriageCost;
	}
	public void setCarriageCost(double carriageCost) {
		this.carriageCost = carriageCost;
	}

}
