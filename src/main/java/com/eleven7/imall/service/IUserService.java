package com.eleven7.imall.service;

import com.eleven7.imall.bean.Userinfo;

public interface IUserService {
	
	public Userinfo getUserbyEmail(String email);
	public void saveOrUpdate(Userinfo ui);
	public Userinfo getUserById(int userid);

}
