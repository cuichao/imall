package com.eleven7.imall.dao;

import org.springframework.stereotype.Repository;

import com.eleven7.imall.bean.OrderPayment;
import com.eleven7.imall.dao.base.GenericDaoImpl;
@Repository
public class OrderPaymentDaoImpl extends GenericDaoImpl<OrderPayment, Integer> implements IOrderPaymentDao{

	public OrderPaymentDaoImpl()
	{
		super(OrderPayment.class);
	}
}
