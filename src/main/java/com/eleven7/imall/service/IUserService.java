package com.eleven7.imall.service;

import java.util.Date;
import java.util.List;

import com.eleven7.imall.bean.Address;
import com.eleven7.imall.bean.Userinfo;

public interface IUserService {
	
	public Userinfo getUserbyEmail(String email);
	public void saveOrUpdate(Userinfo ui);
	public Userinfo getUserById(int userid);
	
	public void saveOrUpdateAddress(Address address);
	public List<Address> listAddressByUserid(Integer userid);
	public Address getAddress(Integer id);
	public void deleteAddress(Integer id);
	public List<Userinfo> listUnactiveUser(Date beforeDate);
	
	public void deleteUsers(List<Userinfo> uiList);

	

}
