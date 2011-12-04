package com.eleven7.imall.dao;

import org.springframework.stereotype.Repository;

import com.eleven7.imall.bean.SendType;
import com.eleven7.imall.bean.Userinfo;
import com.eleven7.imall.dao.base.GenericDaoImpl;

@Repository
public class SendTypeDaoImpl extends GenericDaoImpl<SendType, Integer> implements ISendTypeDao{

	public SendTypeDaoImpl()
	{
		super(SendType.class);
	}
}
