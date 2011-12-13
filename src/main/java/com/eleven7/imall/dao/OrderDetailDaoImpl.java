package com.eleven7.imall.dao;

import org.springframework.stereotype.Repository;

import com.eleven7.imall.bean.Ordering;
import com.eleven7.imall.bean.OrderDetail;
import com.eleven7.imall.dao.base.GenericDaoImpl;
@Repository
public class OrderDetailDaoImpl extends GenericDaoImpl<OrderDetail, Integer> implements IOrderDetailDao{

	public OrderDetailDaoImpl()
	{
		super(OrderDetail.class);
	}
}
