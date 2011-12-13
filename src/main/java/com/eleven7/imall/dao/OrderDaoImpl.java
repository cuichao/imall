package com.eleven7.imall.dao;

import org.springframework.stereotype.Repository;

import com.eleven7.imall.bean.Ordering;
import com.eleven7.imall.dao.base.GenericDaoImpl;
@Repository
public class OrderDaoImpl extends GenericDaoImpl<Ordering, Integer> implements IOrderDao{

	public OrderDaoImpl()
	{
		super(Ordering.class);
	}
}
