package com.eleven7.imall.web.controller;

import javax.servlet.http.HttpServletRequest;

public class CaptchaUtils {
	
	
	public static boolean checkCaptcha(String captcha,HttpServletRequest request)
	{
		String sessionCode = (String)request.getSession().getAttribute("captcha");
		if(sessionCode == null || !sessionCode.equals(captcha))
		{
			return false;
		}
		return true;
	}

}
