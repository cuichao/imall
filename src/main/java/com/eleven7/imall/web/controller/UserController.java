package com.eleven7.imall.web.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import com.eleven7.imall.bean.Address;
import com.eleven7.imall.bean.Userinfo;
import com.eleven7.imall.common.MD5;
import com.eleven7.imall.common.mail.MailHelp;
import com.eleven7.imall.common.mail.MailTemplate;
import com.eleven7.imall.common.mail.MailTemplateUtils;
import com.eleven7.imall.constant.Constant;
import com.eleven7.imall.security.SpringSecurityUtils;
import com.eleven7.imall.service.IUserService;
import com.eleven7.imall.web.dto.UserDto;


@Controller
@RequestMapping(value = "/user")
public class UserController implements ServletContextAware{
	
	private static Log log = LogFactory.getLog(ProductController.class);

	private ServletContext servletContext;

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	@Autowired
	private IUserService userService;
	
	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getUserInfo()
	{	
		return "/user/info";
	}
	@RequestMapping(value="/navigator",method = RequestMethod.GET)
	public ModelAndView userNavigator()
	{
		Userinfo ui = SpringSecurityUtils.getCurrentUser();
		ModelAndView view = new ModelAndView();
		view.setViewName("/user/navigator");
		view.addObject("user", ui);
		return view;
	}
	
	@RequestMapping(value="register",method = RequestMethod.GET)
	public String register()
	{
		return "user/register";
	}
	@RequestMapping(value="login",method = RequestMethod.GET)
	public String login()
	{
		return "user/login";
	}
	@RequestMapping(value="address/new",method = RequestMethod.GET)
	public ModelAndView newAddress()
	{
		ModelAndView view = new ModelAndView();
		view.setViewName("/user/address");
		Userinfo ui = SpringSecurityUtils.getCurrentUser();
		List<Address> addressList = this.userService.listAddressByUserid(ui.getId());
		view.addObject("addressList", addressList);
		return view;
	}
	@RequestMapping(value="address/manage",method = RequestMethod.GET)
	public ModelAndView manageAddress()
	{
		ModelAndView view = new ModelAndView();
		view.setViewName("/user/manageaddress");
		Userinfo ui = SpringSecurityUtils.getCurrentUser();
		List<Address> addressList = this.userService.listAddressByUserid(ui.getId());
		view.addObject("addressList", addressList);
		return view;
	}
	@RequestMapping(value="address/{addressId}/delete",method = RequestMethod.GET)
	@ResponseBody
	public int deleteAddress(@PathVariable("addressId") Integer addressId)
	{
		Address address  = this.userService.getAddress(addressId);
		if(!canEditAddress(address))
		{
			return 1;
		}
		this.userService.deleteAddress(addressId);
		return 0;
		
	}
	private boolean canEditAddress(Address address)
	{
		Userinfo ui = SpringSecurityUtils.getCurrentUser();
		if(ui.getId() == address.getUserid() || SpringSecurityUtils.hasAnyRole("ADMIN_USER"))
		{
			return true;
		}
		return false;
	}
	@RequestMapping(value="address/createSubmit",method = RequestMethod.POST)
	@ResponseBody
	public Address createAddress(@ModelAttribute("address")Address address)
	{
		Userinfo ui = SpringSecurityUtils.getCurrentUser();
		address.setUserid(ui.getId());
		this.userService.saveOrUpdateAddress(address);
		return address;
	}
	@RequestMapping(value="address/{addressId}/update",method = RequestMethod.POST)
	@ResponseBody
	public Address updateAddress(@PathVariable("addressId") int addressId,@ModelAttribute("addressDto")Address addressDto)
	{
		Address  addr = this.userService.getAddress(addressId);		
		Userinfo ui = SpringSecurityUtils.getCurrentUser();
		if(canEditAddress(addr))
		{
			addr.setUpdatetime(new Date());
			addr.setAccepter(addressDto.getAccepter());
			addr.setAddress(addressDto.getAddress());
			addr.setMailcode(addressDto.getMailcode());
			addr.setPhone(addressDto.getPhone());
			addr.setTelephone(addressDto.getTelephone());
			this.userService.saveOrUpdateAddress(addr);
		}
		return addr;
	}
	@RequestMapping(value="registerSubmit",method = RequestMethod.POST)
	public ModelAndView registerSubmit(@ModelAttribute("userDto")UserDto userDto,
			@RequestParam(value = "captcha",required=true)String captcha,
			HttpServletRequest request)
	{
		ModelAndView view = new ModelAndView();
		if(!CaptchaUtils.checkCaptcha(captcha,request))
		{
			view.setViewName("../../error");
			view.addObject("error", "验证码不对");
			return view;
		}
		view.setViewName("/user/registerresult");
		Userinfo ui = userDto.getUserinfoByUserDto();
		Userinfo existUi = this.userService.getUserbyEmail(ui.getEmail());
		if(existUi != null)
		{
			if(!existUi.isActive())
			{
				view.addObject("error","您的用户已经注册过了，请在24小时内登陆邮箱激活IMALL网帐号");
			}
			else
			{
				view.addObject("error","您的用户已经注册过了,请直接登陆！");
			}
			return view;
			
		}
		this.userService.saveOrUpdate(ui);
		sendActiveCodeToMail(ui,request);
		view.addObject("result","恭喜您，注册成功了，请在24小时内登陆邮箱激活IMALL网帐号");
		return view;
	}
	@RequestMapping(value="/login_error",method = RequestMethod.GET)
	public ModelAndView handleLoginError(HttpServletRequest request)
	{
		ModelAndView view = new ModelAndView();
		view.setViewName("redirect:/user/login?login_error=1");
		Integer count = (Integer)request.getSession().getAttribute("login_error_count");
		if(count == null)
		{
			count = 1;
		}
		else
		{
		    count +=1;
		}
		request.getSession().setAttribute("login_error_count", count);
		if(count > Constant.LOGIN_COUNT_BEFORE_CAPTCHA)
		{
			request.getSession().setAttribute("needCAPTCHA", "1");
		}
		return view;
	}
	@RequestMapping(value="/checkRegister",method = RequestMethod.GET)
	@ResponseBody
	public int checkRegisterd(@RequestParam(value = "email",required=true)String email)
	{
		Userinfo existUi = this.userService.getUserbyEmail(email);
		if(existUi != null)
		{
			if(!existUi.isActive())
			{
				return 1;
			}
			else
			{
				return 2;
			}			
		}
		return 0;
		
	}
	@RequestMapping(value="/active/{userid}/{code}",method = RequestMethod.GET)
	public ModelAndView activeUser(@PathVariable("userid") Integer userid,@PathVariable("code") String code)
	{
		ModelAndView view = new ModelAndView();
		view.setViewName("/user/activeresult");
		Userinfo ui = this.userService.getUserById(userid);
		if(ui != null)
		{
			String local_code = MD5.getMD5(Constant.MD5_PREFIX + ui.getId()+"_"+ui.getEmail());
			if(code.equals(local_code))
			{
				ui.setActive(true);
				this.userService.saveOrUpdate(ui);
				view.addObject("result","0");
			}
			else
			{
				view.addObject("result","您激活的链接不正确，抱歉");
			}
		}
		else
		{
			view.addObject("result","您激活的用户不存在，抱歉");
		}
		
		return view;	
	}
	@RequestMapping(value="/delete/unactive",method = RequestMethod.GET)
	public ModelAndView deleteUnactiveUsers()
	{
		Date date = new Date();
		date.setTime(date.getTime() - Constant.MILLISECONDS_ONE_DAY);
		List<Userinfo> uiList = this.userService.listUnactiveUser(date);
		if(CollectionUtils.isNotEmpty(uiList))
		{
			this.userService.deleteUsers(uiList);
		}
		ModelAndView view = new ModelAndView();
		view.setViewName("../../result");
		view.addObject("result","删除成功！");
		return view;
	}
	
	private void sendActiveCodeToMail(Userinfo ui,HttpServletRequest request)
	{
		String activeUrl = request.getScheme()+"://" + request.getServerName()+":"+request.getServerPort() + request.getContextPath()+"/user/active/" + ui.getId() + "/" + MD5.getMD5(Constant.MD5_PREFIX + ui.getId()+"_"+ui.getEmail());
		MailTemplate mt = MailTemplateUtils.getTemplate4ActiveCode(ui, activeUrl);
		MailHelp.sendMail(mt);
	}
	
}
