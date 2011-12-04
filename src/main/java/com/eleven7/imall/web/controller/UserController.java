package com.eleven7.imall.web.controller;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

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

	@RequestMapping(value="settings",method = RequestMethod.GET)
	public ModelAndView getUserSettings()
	{
		String email = SpringSecurityUtils.getCurrentUserName();
		Userinfo ui = this.userService.getUserbyEmail(email);
		ModelAndView view = new ModelAndView();
		view.setViewName("/user/settings");
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
		Userinfo ui = this.userService.getUserbyEmail(SpringSecurityUtils.getCurrentUserName());
		List<Address> addressList = this.userService.listAddressByUserid(ui.getId());
		view.addObject("addressList", addressList);
		return view;
	}
	@RequestMapping(value="address/createSubmit",method = RequestMethod.POST)
	@ResponseBody
	public Address createAddress(@ModelAttribute("address")Address address)
	{
		Userinfo ui = this.userService.getUserbyEmail(SpringSecurityUtils.getCurrentUserName());
		address.setUserid(ui.getId());
		this.userService.saveAddress(address);
		return address;
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
				view.addObject("error","您的用户已经注册了，请到邮箱查收激活邮件以开启IMALL网帐号");
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
	
	private void sendActiveCodeToMail(Userinfo ui,HttpServletRequest request)
	{
		String activeUrl = request.getScheme()+"://" + request.getServerName()+":"+request.getServerPort() + request.getContextPath()+"/user/active/" + ui.getId() + "/" + MD5.getMD5(Constant.MD5_PREFIX + ui.getId()+"_"+ui.getEmail());
		MailTemplate mt = MailTemplateUtils.getTemplate4ActiveCode(ui, activeUrl);
		MailHelp.sendMail(mt);
	}
	
}
