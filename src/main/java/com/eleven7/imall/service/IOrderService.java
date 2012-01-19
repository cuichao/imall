package com.eleven7.imall.service;

import java.util.List;

import com.eleven7.imall.bean.OrderDetail;
import com.eleven7.imall.bean.OrderPayment;
import com.eleven7.imall.bean.Ordering;
import com.eleven7.imall.dao.base.PageBean;

public interface IOrderService {
	
	public Ordering getOrder(Integer oid);
	public void saveOrUpdateOrder(Ordering order);
	public void saveOrderDetail(OrderDetail od);
	public void saveOrderDetailList(List<OrderDetail> odList);
	public List<OrderDetail> getOrderDetailList(Integer orderid);
	
	public List<Ordering> getToSendOrderList(PageBean pb);
	public List<Ordering> getCanceledOrderList(PageBean pb);
	public List<Ordering> getSendingOrderList(PageBean pb);
	public List<Ordering> getFinishedOrderList(PageBean pb);
	public List<Ordering> getOrderListByUser(Integer userid,PageBean pb);
	
	//order payment
	public void saveOrUpdateOrderPayment(OrderPayment op);
	public List<OrderPayment> getOrderPaymentListByUser(Integer userid,PageBean pb);
	
	public List<OrderDetail> getOrderDetailListByUser(Integer userid,PageBean pb);
	
	//这个方法要求线程安全以及事务,待考虑
	public int cancelOrder(Integer orderId);//0表示cancel成功，1表示失败
	
	
	
}
