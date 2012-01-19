package com.eleven7.imall.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eleven7.imall.bean.OrderDetail;
import com.eleven7.imall.bean.OrderPayment;
import com.eleven7.imall.bean.OrderStatus;
import com.eleven7.imall.bean.Ordering;
import com.eleven7.imall.bean.ProductDetail;
import com.eleven7.imall.dao.IOrderDao;
import com.eleven7.imall.dao.IOrderDetailDao;
import com.eleven7.imall.dao.IOrderPaymentDao;
import com.eleven7.imall.dao.IProductDetailDao;
import com.eleven7.imall.dao.base.PageBean;
@Service
public class OrderServiceImpl implements IOrderService{
	
	private final String SINGLE_ORDER_STATUS_SQL = "select o from Ordering o where o.status = %d";
	private final String TWO_ORDER_STATUS_SQL = "select o from Ordering o where o.status = %d or o.status = %d";
	@Autowired
	private IOrderDao orderDao;
	@Autowired
	private IOrderDetailDao orderDetailDao;
	@Autowired
	private IOrderPaymentDao orderPaymentDao;
	@Autowired
	private IProductDetailDao productDetailDao;
	
	public IProductDetailDao getProductDetailDao() {
		return productDetailDao;
	}
	public void setProductDetailDao(IProductDetailDao productDetailDao) {
		this.productDetailDao = productDetailDao;
	}
	public IOrderPaymentDao getOrderPaymentDao() {
		return orderPaymentDao;
	}
	public void setOrderPaymentDao(IOrderPaymentDao orderPaymentDao) {
		this.orderPaymentDao = orderPaymentDao;
	}
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
	public List<Ordering> getFinishedOrderList(PageBean pb)
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
	public List<Ordering> getOrderListByUser(Integer userid,PageBean pb)
	{
		return this.orderDao.findByProperty(pb,"userid" ,userid);
	}
	
	public void saveOrUpdateOrderPayment(OrderPayment op)
	{
	 this.orderPaymentDao.saveOrUpdate(op);
	}
	public List<OrderPayment> getOrderPaymentListByUser(Integer userid,PageBean pb)
	{
		return this.orderPaymentDao.findByProperty(pb, "userid", userid);
	}
	public List<OrderDetail> getOrderDetailListByUser(Integer userid,PageBean pb)
	{
		pb.addDescOrder("od.id");
		String hql = "select od from OrderDetail od ,Ordering o where o.userid = ? and o.id = od.orderid";
		return this.orderDetailDao.find(pb, hql, userid);
	}
	public int cancelOrder(Integer orderId)//0表示cancel成功，1表示失败
	{
		Ordering order = this.orderDao.get(orderId);
		OrderStatus status = order.getStatus();
		if( status == OrderStatus.prePay || status == OrderStatus.preSend)
		{
			order.setStatus(OrderStatus.canceled);
			order.setUpdatetime(new Date());
		}
		else
		{
			return 1;//不能取消了
		}
		this.orderDao.saveOrUpdate(order);
		
		List<OrderDetail> odList = this.getOrderDetailList(orderId);
		List<ProductDetail> pdList = new ArrayList<ProductDetail>();
		for(OrderDetail od : odList)
		{
			ProductDetail pd = this.productDetailDao.get(od.getProductdetailid());
			pd.setCount(pd.getCount() + od.getNum());
			pdList.add(pd);
		}
		this.productDetailDao.saveOrUpdateAll(pdList);
		return 0;
	}

}
