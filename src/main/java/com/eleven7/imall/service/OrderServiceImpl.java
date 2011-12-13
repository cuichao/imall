package com.eleven7.imall.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eleven7.imall.bean.Ordering;
import com.eleven7.imall.bean.OrderDetail;
import com.eleven7.imall.bean.OrderStatus;
import com.eleven7.imall.dao.IOrderDao;
import com.eleven7.imall.dao.IOrderDetailDao;
import com.eleven7.imall.dao.base.PageBean;
@Service
public class OrderServiceImpl implements IOrderService{
	
	private final String SINGLE_ORDER_STATUS_SQL = "select o from Ordering o where o.status = %d";
	private final String TWO_ORDER_STATUS_SQL = "select o from Ordering o where o.status = %d or o.status = %d";
	@Autowired
	private IOrderDao orderDao;
	@Autowired
	private IOrderDetailDao orderDetailDao;
	
	public IOrderDao getOrderDao() {
		return orderDao;
	}
	public void setOrderDao(IOrderDao orderDao) {
		this.orderDao = orderDao;
	}
	public IOrderDetailDao getOrderDetailDao() {
		return orderDetailDao;
	}
	public void setOrderDetailDao(IOrderDetailDao orderDetailDao) {
		this.orderDetailDao = orderDetailDao;
	}
	
	public void saveOrUpdateOrder(Ordering order)
	{
		this.orderDao.saveOrUpdate(order);
	}
	public void saveOrderDetail(OrderDetail od)
	{
		this.orderDetailDao.saveOrUpdate(od);
	}
	public void saveOrderDetailList(List<OrderDetail> odList)
	{
		if(CollectionUtils.isNotEmpty(odList))
		{
			this.orderDetailDao.saveOrUpdateAll(odList);
		}
	}
	public List<Ordering> getToSendOrderList(PageBean pb)
	{
		String hql = String.format(TWO_ORDER_STATUS_SQL, OrderStatus.postPay.ordinal(),OrderStatus.preSend.ordinal());
		return this.orderDao.find(pb, hql);
	}
	public List<Ordering> getCanceledOrderList(PageBean pb)
	{
		String hql = String.format(SINGLE_ORDER_STATUS_SQL, OrderStatus.canceled.ordinal());
		return this.orderDao.find(pb, hql);
	}
	public List<Ordering> getSendingOrderList(PageBean pb)
	{
		String hql = String.format(SINGLE_ORDER_STATUS_SQL, OrderStatus.sending.ordinal());
		return this.orderDao.find(pb, hql);
		
	}
	public List<Ordering> getToFinishOrderList(PageBean pb)
	{
		String hql = String.format(TWO_ORDER_STATUS_SQL, OrderStatus.arrived.ordinal(),OrderStatus.payment_arrived.ordinal());
		return this.orderDao.find(pb, hql);
	}
	public List<OrderDetail> getOrderDetailList(Integer orderid)
	{
		return this.orderDetailDao.findByProperty("orderid", orderid);
	}
	public Ordering getOrder(Integer oid)
	{
		return this.orderDao.get(oid);
	}

}
