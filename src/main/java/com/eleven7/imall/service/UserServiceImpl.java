package com.eleven7.imall.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eleven7.imall.bean.Userinfo;
import com.eleven7.imall.dao.IUserinfoDao;

@Service
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private IUserinfoDao userinfoDao;
	
	public IUserinfoDao getUserinfoDao() {
		return userinfoDao;
	}

	public void setUserinfoDao(IUserinfoDao userinfoDao) {
		this.userinfoDao = userinfoDao;
	}

	public Userinfo getUserbyEmail(String email)
	{
		List<Userinfo> list = this.userinfoDao.findByProperty("email", email);
		if(CollectionUtils.isEmpty(list))
		{
			return null;
		}
		else
		{
			return list.get(0);
		}
	}
	public void saveOrUpdate(Userinfo ui)
	{
		this.userinfoDao.saveOrUpdate(ui);
	}
	
	public Userinfo getUserById(int userid)
	{
		return this.userinfoDao.get(userid);
	}

}
