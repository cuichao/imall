package com.eleven7.imall.web.dto;

import com.eleven7.imall.bean.Userinfo;
import com.eleven7.imall.common.MD5;

public class UserDto {
	
	private String email;
	private String password;
	private String phone;
	private String city;
	private String nickname;

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public Userinfo getUserinfoByUserDto()
	{
		Userinfo ui = new Userinfo();
		ui.setEmail(this.email);
		ui.setNickname(this.nickname);
		ui.setCity(this.city);
		ui.setPassword(MD5.getMD5(this.password));
		ui.setPhone(this.phone);
		return ui;
	}

}
