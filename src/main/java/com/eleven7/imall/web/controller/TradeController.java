package com.eleven7.imall.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eleven7.imall.bean.Address;
import com.eleven7.imall.bean.OrderDetail;
import com.eleven7.imall.bean.OrderPayment;
import com.eleven7.imall.bean.OrderStatus;
import com.eleven7.imall.bean.Ordering;
import com.eleven7.imall.bean.PayType;
import com.eleven7.imall.bean.Product;
import com.eleven7.imall.bean.ProductDetail;
import com.eleven7.imall.bean.SendType;
import com.eleven7.imall.bean.Userinfo;
import com.eleven7.imall.dao.base.PageBean;
import com.eleven7.imall.security.SpringSecurityUtils;
import com.eleven7.imall.service.IOrderService;
import com.eleven7.imall.service.IProductService;
import com.eleven7.imall.service.IUserService;
import com.eleven7.imall.web.dto.OrderDto;
import com.eleven7.imall.web.dto.ProductDetailDto;

@Controller
public class TradeController {
	
	@Autowired
	private IUserService userService;
	
	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	@Autowired
	private IProductService productService;
	
	public IProductService getProductService() {
		return productService;
	}

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
	
	@Autowired
	private IOrderService orderService;
	
	
	public IOrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(IOrderService orderService) {
		this.orderService = orderService;
	}
	
    //---------------------------普通用户访问----------------------------------------------------//
	@RequestMapping(value = "/mycar",method = RequestMethod.GET)
	public ModelAndView myShopCar(HttpServletRequest request)
	{
		ModelAndView view = new ModelAndView();
		view.setViewName("/trade/shopcar");
		List<ProductDetailDto> pddList = this.getProductDetailDtoListFromCookie(request);
		view.addObject("pdDtoList",pddList);
		return view;
	}
	@RequestMapping(value = "/trade/showorder",method = RequestMethod.GET)
	public ModelAndView showOrder(HttpServletRequest request)
	{
		ModelAndView view = new ModelAndView();
		view.setViewName("/trade/order");
		List<ProductDetailDto> pddList = this.getProductDetailDtoListFromCookie(request);
		double totalMoney = 0.0;
		for(ProductDetailDto pdd : pddList)
		{
			totalMoney += pdd.getProduct().getPrice()*pdd.getCount();
		}
		Userinfo ui = SpringSecurityUtils.getCurrentUser();
		List<Address> addressList = this.userService.listAddressByUserid(ui.getId());
		List<SendType> sendtypeList = this.productService.getAllSendType();
		view.addObject("totalMoney", totalMoney);
		view.addObject("pdDtoList",pddList);
		view.addObject("addressList", addressList);
		view.addObject("sendtypeList", sendtypeList);
		return view;
		
	}
	@RequestMapping(value = "/trade/saveorder",method = RequestMethod.POST)
	public ModelAndView saveOrder(@ModelAttribute("orderDto")OrderDto orderDto,HttpServletRequest request,HttpServletResponse response)
	{
		ModelAndView view = new ModelAndView();
		view.setViewName("/trade/pay");
		List<ProductDetailDto> pddList = this.getProductDetailDtoListFromCookie(request);
		List<ProductDetailDto> notEnoughList = new ArrayList<ProductDetailDto>();
		if(!isPdCountEnough(pddList,notEnoughList))
		{
			view.setViewName("/trade/error");
			view.addObject("pdDtoList", notEnoughList);
			return view;
		}
		Userinfo ui = SpringSecurityUtils.getCurrentUser();
		Ordering order = this.getOrderAndUpdateAddressByParams(orderDto, pddList, ui);
		this.orderService.saveOrUpdateOrder(order);
		this.orderService.saveOrderDetailList(this.getOrderdetailList(order, pddList));
		//更新 product detail count
		this.updateProductDetailCount(pddList);
		this.removeProductFromCookie(response);
		view.addObject("order", order);
		return view;
		
	}
	private void updateProductDetailCount(List<ProductDetailDto> pddList)
	{
		List<ProductDetail> pdList = new ArrayList<ProductDetail>();
		for(ProductDetailDto dto : pddList)
		{
			int leftCount = dto.getDetail().getCount() - dto.getCount();
			leftCount = leftCount > 0 ? leftCount : 0;
			dto.getDetail().setCount(leftCount);
			pdList.add(dto.getDetail());
		}
		this.productService.saveOrUpdateProductDetailList(pdList);
	}
	@RequestMapping(value = "/trade/{orderid}/order",method = RequestMethod.GET)
	public ModelAndView OrderDetails(@PathVariable("orderid") Integer orderid)
	{
		ModelAndView view = new ModelAndView();
		view.setViewName("/trade/orderdetails");
		Ordering order = this.orderService.getOrder(orderid);
		List<Ordering> orderList = new ArrayList<Ordering>();
		orderList.add(order);
		this.fillOrderInfo(orderList);
		view.addObject("order", order);
		return view;
	}
	
	//---------------------------管理用户操作----------------------------------------------------//
	@RequestMapping(value = "/back/trade/tosendlist",method = RequestMethod.GET)
	public ModelAndView wait2SendOrderList()
	{
		PageBean pb = new PageBean();
		pb.setShowAll(true);
		List<Ordering> orderList = this.orderService.getToSendOrderList(pb);
		this.fillOrderInfo(orderList);
		
		ModelAndView view = new ModelAndView();
		view.setViewName("/admin/orderlist");
		view.addObject("orderList", orderList);
		view.addObject("orderType", "toSendList");
		return view;
		
	}
	@RequestMapping(value = "/back/trade/cancellist",method = RequestMethod.GET)
	public ModelAndView canceledList()
	{
		PageBean pb = new PageBean();
		pb.setShowAll(true);
		List<Ordering> orderList = this.orderService.getCanceledOrderList(pb);
		this.fillOrderInfo(orderList);
		ModelAndView view = new ModelAndView();
		view.setViewName("/admin/orderlist");
		view.addObject("orderList", orderList);
		view.addObject("orderType", "canceledList");
		return view;
		
	}
	@RequestMapping(value = "/back/trade/sendinglist",method = RequestMethod.GET)
	public ModelAndView sendingList()
	{
		PageBean pb = new PageBean();
		pb.setShowAll(true);
		List<Ordering> orderList = this.orderService.getSendingOrderList(pb);
		this.fillOrderInfo(orderList);
		ModelAndView view = new ModelAndView();
		view.setViewName("/admin/orderlist");
		view.addObject("orderList", orderList);
		view.addObject("orderType", "sendingList");
		return view;
		
	}
	@RequestMapping(value = "/back/trade/finishlist",method = RequestMethod.GET)
	public ModelAndView FinishedList()
	{
		PageBean pb = new PageBean();
		pb.setShowAll(true);
		List<Ordering> orderList = this.orderService.getFinishedOrderList(pb);
		this.fillOrderInfo(orderList);
		ModelAndView view = new ModelAndView();
		view.setViewName("/admin/orderlist");
		view.addObject("orderList", orderList);
		view.addObject("orderType", "finishList");
		return view;
		
	}
	@RequestMapping(value = "/back/trade/{orderId}/modifystatus",method = RequestMethod.GET)
	@ResponseBody
	public void modifyOrderStatus(@PathVariable("orderId") Integer orderId,
			@RequestParam(value = "status",required=true)OrderStatus status)
	{
		Ordering order = this.orderService.getOrder(orderId);
		order.setStatus(status);
		this.orderService.saveOrUpdateOrder(order);	
	}
	@RequestMapping(value = "/trade/myorder",method = RequestMethod.GET)
	public ModelAndView getOrderListByUser()
	{
		PageBean pb = new PageBean();
		pb.setShowAll(true);
		Userinfo ui = SpringSecurityUtils.getCurrentUser();
		List<Ordering> orderList = this.orderService.getOrderListByUser(ui.getId(), pb);
		ModelAndView view = new ModelAndView();
		view.setViewName("/trade/userorder");
		view.addObject("orderList", orderList);
		return view;
	}
	@RequestMapping(value = "/trade/payhistory",method = RequestMethod.GET)
	public ModelAndView getOrderPaymentListByUser()
	{
		PageBean pb = new PageBean();
		pb.setShowAll(true);
		pb.addDescOrder("createtime");
		Userinfo ui = SpringSecurityUtils.getCurrentUser();
		List<OrderPayment> opList = this.orderService.getOrderPaymentListByUser(ui.getId(), pb);
		ModelAndView view = new ModelAndView();
		view.setViewName("/trade/payhistorylist");
		view.addObject("paymentList", opList);
		return view;
	}
	@RequestMapping(value = "/trade/product",method = RequestMethod.GET)
	public ModelAndView getBuyedProductByUser()
	{
		PageBean pb = new PageBean();
		pb.setShowAll(true);
		Userinfo ui = SpringSecurityUtils.getCurrentUser();
		List<OrderDetail> odList = this.orderService.getOrderDetailListByUser(ui.getId(), pb);
		for(OrderDetail od : odList)
		{
			ProductDetail pd = this.productService.getProductDetail(od.getProductdetailid());
			Product product = this.productService.getProduct(od.getProductid());
			od.setProductDetail(pd);
			od.setProduct(product);
		}
		ModelAndView view = new ModelAndView();
		view.setViewName("/trade/productlist");
		view.addObject("orderdetailList", odList);
		return view;
	}
	
	private void fillOrderInfo(List<Ordering> orderList)
	{
		for(Ordering order : orderList)
		{
			Userinfo ui = this.userService.getUserById(order.getUserid());
			order.setUser(ui);
			
			List<OrderDetail> odList = this.orderService.getOrderDetailList(order.getId());
			for(OrderDetail od : odList)
			{
				ProductDetail pd = this.productService.getProductDetail(od.getProductdetailid());
				Product product = this.productService.getProduct(od.getProductid());
				od.setProductDetail(pd);
				od.setProduct(product);
			}
			order.setOdList(odList);
		}	
	}
	//私有方法
	private List<OrderDetail> getOrderdetailList(Ordering order,List<ProductDetailDto> pddList)
	{
		List<OrderDetail> odList = new ArrayList<OrderDetail>();
		
		for(ProductDetailDto pd : pddList)
		{
			OrderDetail od = new OrderDetail();
			od.setNum(pd.getCount());
			od.setOrderid(order.getId());
			od.setPrice(pd.getProduct().getPrice());
			od.setProductdetailid(pd.getDetail().getId());
			od.setProductid(pd.getProduct().getId());
			od.setTotalpay(pd.getProduct().getPrice()*pd.getCount());
			odList.add(od);
		}
		return odList;
		
	}
	private Ordering getOrderAndUpdateAddressByParams(OrderDto orderDto,List<ProductDetailDto> pddList,Userinfo ui)
	{
		Ordering order = new Ordering();
		//update address last_access_time
		Address address = this.userService.getAddress(orderDto.getAddress_id());
		address.setLast_access_time(new Date());
		this.userService.saveOrUpdateAddress(address);
		
		order.setAddressid(address.getId());
		order.setAccepter(address.getAccepter());
		order.setToAddress(address.getAddress()+"\t"+address.getMailcode()+"\t"+address.getPhone()+"\t"+address.getTelephone());
		order.setUserid(ui.getId());
		order.setCarriageCost(orderDto.getCarriage());
		order.setPayType(orderDto.getPay_type());
		order.setSendType(orderDto.getSend_type());
		order.setTimeType(orderDto.getTime_type());
		
		if(order.getPayType() == PayType.PAY_AFTER_PRODUCT_ARRIVED)
		{
			order.setStatus(OrderStatus.preSend);
		}
		double totalMoney = 0.0;
		for(ProductDetailDto pdd : pddList)
		{
			totalMoney += pdd.getProduct().getPrice()*pdd.getCount();
		}
		totalMoney += orderDto.getCarriage();
		order.setTotalpay(totalMoney);		
		return order;
	}
	private boolean isPdCountEnough(List<ProductDetailDto> pddList,List<ProductDetailDto> notEnoughList)
	{
		boolean bEnough = true;
		for(ProductDetailDto dto : pddList)
		{
			int limitCount = dto.getDetail().getCount();
			if(dto.getCount() > limitCount)
			{
				notEnoughList.add(dto);
				bEnough = false;
			}
		}
		return bEnough;
	}
	private List<ProductDetailDto> getProductDetailDtoListFromCookie(HttpServletRequest request)
	{
		List<ProductDetailDto> pddList = new ArrayList<ProductDetailDto>();
		Cookie[] cookieArray = request.getCookies();
		if(cookieArray!=null && cookieArray.length > 0)
		{
			for(int i=0;i<cookieArray.length;i++)
			{
				Cookie cookie = cookieArray[i];
				if("imall_pd_list".equals(cookie.getName()))
				{
					String[] kvArray = {};
					try {
						kvArray = URLDecoder.decode(cookie.getValue(),"UTF-8").split(";");
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					for(String kv : kvArray)
					{
						try {
							if(kv != "")
							{
								String item[] = kv.split("=");
								Integer pd_id = Integer.parseInt(item[0]);
								int count = Integer.parseInt(item[1]);
								ProductDetail pd = this.productService.getProductDetail(pd_id);
								Product p = this.productService.getProduct(pd.getProductid());
								pddList.add(new ProductDetailDto(p,pd,count));							
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					break;
				}	
			}
		}
		return pddList;
	}
	
	private void removeProductFromCookie(HttpServletResponse response)
	{
		Cookie cookie = new Cookie("imall_pd_list", "");
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	
	


}
