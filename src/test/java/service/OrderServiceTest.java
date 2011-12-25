package service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import base.BaseTest;

import com.eleven7.imall.bean.OrderDetail;
import com.eleven7.imall.dao.base.PageBean;
import com.eleven7.imall.service.IOrderService;

public class OrderServiceTest extends BaseTest{
	
	@Autowired
	private IOrderService orderService;

	public IOrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(IOrderService orderService) {
		this.orderService = orderService;
	}
	
	@Test
	public void test()
	{
		PageBean pb = new PageBean();
		pb.addDescOrder("od.id");
		List<OrderDetail> list = this.orderService.getOrderDetailListByUser(24, pb);
		System.out.println(list.size());
		
	}
	
	
	
	

}
