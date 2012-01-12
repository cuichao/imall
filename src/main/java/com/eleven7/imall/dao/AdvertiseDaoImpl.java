package com.eleven7.imall.dao;

import org.springframework.stereotype.Repository;

import com.eleven7.imall.bean.Advertise;
import com.eleven7.imall.dao.base.GenericDaoImpl;

@Repository
public class AdvertiseDaoImpl extends GenericDaoImpl<Advertise, Integer> implements IAdvertiseDao{

	public AdvertiseDaoImpl()
	{
		super(Advertise.class);
	}
}
