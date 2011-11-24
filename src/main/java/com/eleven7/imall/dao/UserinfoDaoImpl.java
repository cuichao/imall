package com.eleven7.imall.dao;

import org.springframework.stereotype.Repository;

import com.eleven7.imall.bean.Userinfo;
import com.eleven7.imall.dao.base.GenericDaoImpl;

@Repository
public class UserinfoDaoImpl extends GenericDaoImpl<Userinfo, Integer> implements IUserinfoDao{

	public UserinfoDaoImpl()
	{
		super(Userinfo.class);
	}
}
