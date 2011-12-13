package com.eleven7.imall.web.controller;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.eleven7.imall.bean.ProductDetail;
import com.eleven7.imall.constant.Constant;

@Controller
@RequestMapping(value = "/admin")
public class AdminController{
	
	@RequestMapping(value = "/index",method = RequestMethod.GET)
	public String index()
	{	
		return "admin/index";
	}
	@RequestMapping(value = "/left",method = RequestMethod.GET)
	public String left()
	{	
		return "admin/left";
	}
	
	
	

}
