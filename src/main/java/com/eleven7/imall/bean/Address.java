package com.eleven7.imall.bean;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "address")
public class Address {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false)
	private int id;
	@Column
	private int userid;
	@Column(length = 30)
	private String accepter;
	@Column(length = 255)
	private String address;
	@Column(length = 10)
	private String mailcode;
	@Column(length = 30)
	private String phone;//手机
	@Column(length = 30)
	private String telephone;//座机
	@Column
	private Date createtime = new Date();
	@Column
	private Date updatetime;
	@Column
	private Date last_access_time;
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
	public String getAccepter() {
		return accepter;
	}
	public void setAccepter(String accepter) {
		this.accepter = accepter;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMailcode() {
		return mailcode;
	}
	public void setMailcode(String mailcode) {
		this.mailcode = mailcode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public Date getLast_access_time() {
		return last_access_time;
	}
	public void setLast_access_time(Date last_access_time) {
		this.last_access_time = last_access_time;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	

}
