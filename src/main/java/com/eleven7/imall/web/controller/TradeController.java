package com.eleven7.imall.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alipay.services.AlipayService;
import com.alipay.util.AlipayNotify;
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
import com.eleven7.imall.constant.Constant;
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

	// ---------------------------普通用户访问----------------------------------------------------//
	@RequestMapping(value = "/mycar", method = RequestMethod.GET)
	public ModelAndView myShopCar(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		view.setViewName("/trade/shopcar");
		List<ProductDetailDto> pddList = this
				.getProductDetailDtoListFromCookie(request);
		view.addObject("pdDtoList", pddList);
		return view;
	}

	@RequestMapping(value = "/trade/showorder", method = RequestMethod.GET)
	public ModelAndView showOrder(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		view.setViewName("/trade/order");
		List<ProductDetailDto> pddList = this
				.getProductDetailDtoListFromCookie(request);
		List<ProductDetailDto> notEnoughList = new ArrayList<ProductDetailDto>();
		if (!isPdCountEnough(pddList, notEnoughList)) {
			view.setViewName("/trade/error");
			view.addObject("pdDtoList", notEnoughList);
			return view;
		}
		double totalMoney = 0.0;
		for (ProductDetailDto pdd : pddList) {
			totalMoney += pdd.getProduct().getPrice() * pdd.getCount();
		}
		Userinfo ui = SpringSecurityUtils.getCurrentUser();
		List<Address> addressList = this.userService.listAddressByUserid(ui
				.getId());
		List<SendType> sendtypeList = this.productService.getAllSendType();
		view.addObject("totalMoney", totalMoney);
		view.addObject("pdDtoList", pddList);
		view.addObject("addressList", addressList);
		view.addObject("sendtypeList", sendtypeList);
		return view;

	}

	@RequestMapping(value = "/trade/saveorder", method = RequestMethod.POST)
	public ModelAndView saveOrder(
			@ModelAttribute("orderDto") OrderDto orderDto,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView();
		view.setViewName("/trade/pay");
		List<ProductDetailDto> pddList = this
				.getProductDetailDtoListFromCookie(request);
		if(CollectionUtils.isEmpty(pddList))
		{
			view.setViewName("/trade/error");
			view.addObject("error", "您还没有选择商品，请返回选择商品后再下订单：）");
			return view;
		}
		List<ProductDetailDto> notEnoughList = new ArrayList<ProductDetailDto>();
		if (!isPdCountEnough(pddList, notEnoughList)) {
			view.setViewName("/trade/error");
			view.addObject("pdDtoList", notEnoughList);
			return view;
		}
		Userinfo ui = SpringSecurityUtils.getCurrentUser();
		Ordering order = this.getOrderAndUpdateAddressByParams(orderDto,
				pddList, ui);
		this.orderService.saveOrUpdateOrder(order);
		this.orderService.saveOrderDetailList(this.getOrderdetailList(order,
				pddList));
		// 更新 product detail count
		this.updateProductDetailCount(pddList);
		this.removeProductFromCookie(response);
		view.addObject("order", order);
		return view;

	}
	@RequestMapping(value = "/trade/{orderId}/cancel", method = RequestMethod.GET)
	public ModelAndView cancelOrder(@PathVariable("orderId") Integer orderId) {
		ModelAndView view = new ModelAndView();
		int result = this.orderService.cancelOrder(orderId);
		if(result != 0)
		{
			view.setViewName("../../error");
			view.addObject("error", "订单已发货，不能取消。如需帮助，请联系管理员！");
			return view;
		}
		view.setViewName("redirect:/trade/myorder");
		return view;
	}

	private void updateProductDetailCount(List<ProductDetailDto> pddList) {
		List<ProductDetail> pdList = new ArrayList<ProductDetail>();
		for (ProductDetailDto dto : pddList) {
			int leftCount = dto.getDetail().getCount() - dto.getCount();
			leftCount = leftCount > 0 ? leftCount : 0;
			dto.getDetail().setCount(leftCount);
			pdList.add(dto.getDetail());
		}
		this.productService.saveOrUpdateProductDetailList(pdList);
	}

	@RequestMapping(value = "/trade/{orderid}/order", method = RequestMethod.GET)
	public ModelAndView OrderDetails(@PathVariable("orderid") Integer orderid) {
		ModelAndView view = new ModelAndView();
		view.setViewName("/trade/orderdetails");
		Ordering order = this.orderService.getOrder(orderid);
		List<Ordering> orderList = new ArrayList<Ordering>();
		orderList.add(order);
		this.fillOrderInfo(orderList);
		view.addObject("order", order);
		return view;
	}

	// ---------------------------管理用户操作----------------------------------------------------//
	@RequestMapping(value = "/back/trade/tosendlist", method = RequestMethod.GET)
	public ModelAndView wait2SendOrderList() {
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

	@RequestMapping(value = "/back/trade/cancellist", method = RequestMethod.GET)
	public ModelAndView canceledList() {
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

	@RequestMapping(value = "/back/trade/sendinglist", method = RequestMethod.GET)
	public ModelAndView sendingList() {
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

	@RequestMapping(value = "/back/trade/finishlist", method = RequestMethod.GET)
	public ModelAndView FinishedList() {
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

	@RequestMapping(value = "/back/trade/{orderId}/modifystatus", method = RequestMethod.GET)
	@ResponseBody
	public void modifyOrderStatus(@PathVariable("orderId") Integer orderId,
			@RequestParam(value = "status", required = true) OrderStatus status) {
		Ordering order = this.orderService.getOrder(orderId);
		order.setStatus(status);
		this.orderService.saveOrUpdateOrder(order);
	}

	@RequestMapping(value = "/trade/myorder", method = RequestMethod.GET)
	public ModelAndView getOrderListByUser(@RequestParam(value = "page",required=false)Integer page) {
		if(page == null)
		{
			page = 1;
		}
		PageBean pb = new PageBean();
		pb.setPage(page);	
		pb.addDescOrder("id");
		Userinfo ui = SpringSecurityUtils.getCurrentUser();
		List<Ordering> orderList = this.orderService.getOrderListByUser(
				ui.getId(), pb);
		ModelAndView view = new ModelAndView();
		view.setViewName("/trade/userorder");
		view.addObject("orderList", orderList);
		view.addObject("page", pb);
		return view;
	}

	@RequestMapping(value = "/trade/payhistory", method = RequestMethod.GET)
	public ModelAndView getOrderPaymentListByUser() {
		PageBean pb = new PageBean();
		pb.setShowAll(true);
		pb.addDescOrder("createtime");
		Userinfo ui = SpringSecurityUtils.getCurrentUser();
		List<OrderPayment> opList = this.orderService
				.getOrderPaymentListByUser(ui.getId(), pb);
		ModelAndView view = new ModelAndView();
		view.setViewName("/trade/payhistorylist");
		view.addObject("paymentList", opList);
		return view;
	}

	@RequestMapping(value = "/trade/product", method = RequestMethod.GET)
	public ModelAndView getBuyedProductByUser() {
		PageBean pb = new PageBean();
		pb.setShowAll(true);
		Userinfo ui = SpringSecurityUtils.getCurrentUser();
		List<OrderDetail> odList = this.orderService.getOrderDetailListByUser(
				ui.getId(), pb);
		for (OrderDetail od : odList) {
			ProductDetail pd = this.productService.getProductDetail(od
					.getProductdetailid());
			Product product = this.productService.getProduct(od.getProductid());
			od.setProductDetail(pd);
			od.setProduct(product);
		}
		ModelAndView view = new ModelAndView();
		view.setViewName("/trade/productlist");
		view.addObject("orderdetailList", odList);
		return view;
	}

	@RequestMapping(value = "/trade/{orderId}/pay", method = RequestMethod.POST)
	public ModelAndView payOrder(@PathVariable("orderId") Integer orderId,
			@RequestParam(value = "bank", required = true) String bank,HttpServletResponse response) {
		try {
			PrintWriter out = response.getWriter();
			String strHtmlPay = this.getAlipayHtml(orderId, bank);
			out.print(strHtmlPay);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/callback2pay", method = RequestMethod.GET)
	public ModelAndView paymentCallBack(HttpServletRequest request)
	{
		ModelAndView view = new ModelAndView();
		view.setViewName("/trade/payresult");
		String strOrderId = request.getParameter("");
		//获取支付宝GET过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		
		
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//

		String trade_no = request.getParameter("trade_no");				//支付宝交易号
		String order_no = request.getParameter("out_trade_no");	        //获取订单号
		String total_fee = request.getParameter("total_fee");	        //获取总金额
		String subject = request.getParameter("subject");//商品名称、订单名称
		String body = "";
		if(request.getParameter("body") != null){
			body = request.getParameter("body");//商品描述、订单备注、描述
		}
		String buyer_email = request.getParameter("buyer_email");		//买家支付宝账号
		String trade_status = request.getParameter("trade_status");		//交易状态
		
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		
		//计算得出通知验证结果
		boolean verify_result = AlipayNotify.verify(params);
		
		if(verify_result){//验证成功
			//////////////////////////////////////////////////////////////////////////////////////////
			//请在这里加上商户的业务逻辑程序代码

			//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
			
			if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
				//判断该笔订单是否在商户网站中已经做过处理（可参考“集成教程”中“3.4返回数据处理”）
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//如果有做过处理，不执行商户的业务程序
				Ordering order = this.orderService.getOrder(Integer.parseInt(strOrderId));
				if(order.getStatus() == OrderStatus.prePay)
				{
					order.setStatus(OrderStatus.postPay);
					this.orderService.saveOrUpdateOrder(order);
					OrderPayment op = new OrderPayment();
					op.setBank(request.getParameter("bank_seq_no"));
					op.setMoney(Double.parseDouble(total_fee));
					op.setOrderid(order.getId());
					op.setPayType(PayType.PAY_ONLINE);
					op.setUserid(order.getUserid());
					this.orderService.saveOrUpdateOrderPayment(op);
				}
				view.addObject("payresult", "0");
			}

			//////////////////////////////////////////////////////////////////////////////////////////
		}else{
				view.addObject("payresult", "1");
		}
		return view;
		
	}
	

	private void fillOrderInfo(List<Ordering> orderList) {
		for (Ordering order : orderList) {
			Userinfo ui = this.userService.getUserById(order.getUserid());
			order.setUser(ui);

			List<OrderDetail> odList = this.orderService
					.getOrderDetailList(order.getId());
			for (OrderDetail od : odList) {
				ProductDetail pd = this.productService.getProductDetail(od
						.getProductdetailid());
				Product product = this.productService.getProduct(od
						.getProductid());
				od.setProductDetail(pd);
				od.setProduct(product);
			}
			order.setOdList(odList);
		}
	}

	// 私有方法
	private List<OrderDetail> getOrderdetailList(Ordering order,
			List<ProductDetailDto> pddList) {
		List<OrderDetail> odList = new ArrayList<OrderDetail>();

		for (ProductDetailDto pd : pddList) {
			OrderDetail od = new OrderDetail();
			od.setNum(pd.getCount());
			od.setOrderid(order.getId());
			od.setPrice(pd.getProduct().getPrice());
			od.setProductdetailid(pd.getDetail().getId());
			od.setProductid(pd.getProduct().getId());
			od.setTotalpay(pd.getProduct().getPrice() * pd.getCount());
			odList.add(od);
		}
		return odList;

	}

	private Ordering getOrderAndUpdateAddressByParams(OrderDto orderDto,
			List<ProductDetailDto> pddList, Userinfo ui) {
		Ordering order = new Ordering();
		// update address last_access_time
		Address address = this.userService.getAddress(orderDto.getAddress_id());
		address.setLast_access_time(new Date());
		this.userService.saveOrUpdateAddress(address);

		order.setAddressid(address.getId());
		order.setAccepter(address.getAccepter());
		order.setToAddress(address.getAddress() + "\t" + address.getMailcode()
				+ "\t" + address.getPhone() + "\t" + address.getTelephone());
		order.setUserid(ui.getId());
		order.setCarriageCost(orderDto.getCarriage());
		order.setPayType(orderDto.getPay_type());
		order.setSendType(orderDto.getSend_type());
		order.setTimeType(orderDto.getTime_type());

		if (order.getPayType() == PayType.PAY_AFTER_PRODUCT_ARRIVED) {
			order.setStatus(OrderStatus.preSend);
		}
		double totalMoney = 0.0;
		for (ProductDetailDto pdd : pddList) {
			totalMoney += pdd.getProduct().getPrice() * pdd.getCount();
		}
		totalMoney += orderDto.getCarriage();
		order.setTotalpay(totalMoney);
		return order;
	}

	private boolean isPdCountEnough(List<ProductDetailDto> pddList,
			List<ProductDetailDto> notEnoughList) {
		boolean bEnough = true;
		for (ProductDetailDto dto : pddList) {
			int limitCount = dto.getDetail().getCount();
			if (dto.getCount() > limitCount) {
				notEnoughList.add(dto);
				bEnough = false;
			}
		}
		return bEnough;
	}

	private List<ProductDetailDto> getProductDetailDtoListFromCookie(
			HttpServletRequest request) {
		List<ProductDetailDto> pddList = new ArrayList<ProductDetailDto>();
		Cookie[] cookieArray = request.getCookies();
		if (cookieArray != null && cookieArray.length > 0) {
			for (int i = 0; i < cookieArray.length; i++) {
				Cookie cookie = cookieArray[i];
				if ("imall_pd_list".equals(cookie.getName())) {
					String[] kvArray = {};
					try {
						kvArray = URLDecoder.decode(cookie.getValue(), "UTF-8")
								.split(";");
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					for (String kv : kvArray) {
						try {
							if (kv != "") {
								String item[] = kv.split("=");
								Integer pd_id = Integer.parseInt(item[0]);
								int count = Integer.parseInt(item[1]);
								ProductDetail pd = this.productService
										.getProductDetail(pd_id);
								Product p = this.productService.getProduct(pd
										.getProductid());
								pddList.add(new ProductDetailDto(p, pd, count));
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

	private void removeProductFromCookie(HttpServletResponse response) {
		Cookie cookie = new Cookie("imall_pd_list", "");
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	private String getAlipayHtml(Integer orderId, String bank) {
		
		//必填参数//
		Ordering order = this.orderService.getOrder(orderId);
		List<OrderDetail> odList = this.orderService
				.getOrderDetailList(orderId);
		String subject = "";
		String details = "";
		for (OrderDetail od : odList) {
			subject += od.getId() + ",";
			details += od.getId() + ":" + od.getNum() + ",";
		}
		// 扩展功能参数——默认支付方式//
		// 默认支付方式，取值见“即时到帐接口”技术文档中的请求参数列表
		String paymethod = Constant.PayMethod.bankPay;
		// 默认网银代号，代号列表见“即时到帐接口”技术文档“附录”→“银行列表”
		String defaultbank = bank;
		
		// 扩展功能参数——防钓鱼//
		// 防钓鱼时间戳
		String anti_phishing_key = "";
		// 获取客户端的IP地址，建议：编写获取客户端IP地址的程序
		String exter_invoke_ip = "";
		// 注意：
		// 1.请慎重选择是否开启防钓鱼功能
		// 2.exter_invoke_ip、anti_phishing_key一旦被设置过，那么它们就会成为必填参数
		// 3.开启防钓鱼功能后，服务器、本机电脑必须支持远程XML解析，请配置好该环境。
		// 4.建议使用POST方式请求数据
		// 示例：
		// anti_phishing_key = AlipayService.query_timestamp(); //获取防钓鱼时间戳函数
		// exter_invoke_ip = "202.1.1.1";

		// 扩展功能参数——其他//

		// 自定义参数，可存放任何内容（除=、&等特殊字符外），不会显示在页面上
		String extra_common_param = "";
		// 默认买家支付宝账号
		String buyer_email = "";
		// 商品展示地址，要用http:// 格式的完整路径，不允许加?id=123这类自定义参数
		String show_url = "";

		// 扩展功能参数——分润(若要使用，请按照注释要求的格式赋值)//

		// 提成类型，该值为固定值：10，不需要修改
		String royalty_type = "";
		// 提成信息集
		String royalty_parameters = "";
		// 注意：
		// 与需要结合商户网站自身情况动态获取每笔交易的各分润收款账号、各分润金额、各分润说明。最多只能设置10条
		// 各分润金额的总和须小于等于total_fee
		// 提成信息集格式为：收款方Email_1^金额1^备注1|收款方Email_2^金额2^备注2
		// 示例：
		// royalty_type = "10"
		// royalty_parameters = "111@126.com^0.01^分润备注一|222@126.com^0.01^分润备注二"

		// ////////////////////////////////////////////////////////////////////////////////

		// 把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("payment_type", Constant.PAYTYPE_BUYING_PRODUCT);
		sParaTemp.put("out_trade_no", String.valueOf(orderId));
		sParaTemp.put("subject", subject);
		sParaTemp.put("body", details);
		sParaTemp.put("total_fee", String.valueOf(order.getTotalpay()));
		sParaTemp.put("show_url", show_url);
		sParaTemp.put("paymethod", paymethod);
		sParaTemp.put("defaultbank", defaultbank);
		sParaTemp.put("anti_phishing_key", anti_phishing_key);
		sParaTemp.put("exter_invoke_ip", exter_invoke_ip);
		sParaTemp.put("extra_common_param", extra_common_param);
		sParaTemp.put("buyer_email", buyer_email);
		sParaTemp.put("royalty_type", royalty_type);
		sParaTemp.put("royalty_parameters", royalty_parameters);

		// 构造函数，生成请求URL
		String sHtmlText = AlipayService.create_direct_pay_by_user(sParaTemp);
		return sHtmlText;
	}

}
