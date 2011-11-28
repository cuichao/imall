package com.eleven7.imall.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.eleven7.imall.bean.Address;
import com.eleven7.imall.bean.Product;
import com.eleven7.imall.bean.ProductDetail;
import com.eleven7.imall.bean.Userinfo;
import com.eleven7.imall.security.SpringSecurityUtils;
import com.eleven7.imall.service.IProductService;
import com.eleven7.imall.service.IUserService;
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
	
	@RequestMapping(value = "/mycar",method = RequestMethod.GET)
	public ModelAndView myShopCar(HttpServletRequest request)
	{
		ModelAndView view = new ModelAndView();
		view.setViewName("/trade/shopcar");
		List<ProductDetailDto> pddList = this.getProductDetailDtoListFromCookie(request);
		view.addObject("pdDtoList",pddList);
		return view;
	}
	@RequestMapping(value = "/showorder",method = RequestMethod.GET)
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
		Userinfo ui = this.userService.getUserbyEmail(SpringSecurityUtils.getCurrentUserName());
		List<Address> addressList = this.userService.listAddressByUserid(ui.getId());
		view.addObject("totalMoney", totalMoney);
		view.addObject("pdDtoList",pddList);
		view.addObject("addressList", addressList);
		return view;
		
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


}
