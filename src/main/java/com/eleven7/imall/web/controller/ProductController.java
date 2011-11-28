package com.eleven7.imall.web.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.eleven7.imall.bean.Product;
import com.eleven7.imall.bean.ProductDetail;
import com.eleven7.imall.common.mail.MailHelp;
import com.eleven7.imall.constant.Constant;
import com.eleven7.imall.dao.base.PageBean;
import com.eleven7.imall.service.IProductService;
import com.eleven7.imall.web.dto.ProductDto;

@Controller
@RequestMapping(value = "/product")
public class ProductController implements ServletContextAware{
	
	private static Log log = LogFactory.getLog(ProductController.class);
	
	private ServletContext servletContext;

	 public void setServletContext(ServletContext servletContext) {
	  this.servletContext = servletContext;
	 }
	
	@Autowired
	private IProductService productService;
	
	public IProductService getProductService() {
		return productService;
	}

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	@RequestMapping(value = "/create",method = RequestMethod.GET)
	public String createProduct()
	{	
		return "product/create";
	}
	
	@RequestMapping(value = "/createSubmit",method = RequestMethod.POST)
	public String createSubmit(@ModelAttribute("productDto")ProductDto productDto,HttpServletRequest request)
	{
		Product p = productDto.getProductByDto();
		this.productService.saveProduct(p);
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
		Map<String,MultipartFile> fileMap = multipartRequest.getFileMap();
		List<ProductDetail> pdList = new ArrayList<ProductDetail>();
		for(Map.Entry<String, MultipartFile> entry : fileMap.entrySet())
		{
			MultipartFile picFile = entry.getValue();			
			if (!picFile.isEmpty()) {
				   String filename = picFile.getOriginalFilename();
				   String index = entry.getKey().substring(3);//pic123	
				   String path = this.getUploadPicturePath();
				   String picname = new Date().getTime() +"_" + filename;
				   File file = new File(path + picname);
				   log.info("filepath==="+ file.getAbsolutePath());
				   try {
					   picFile.transferTo(file);
				   } catch (Exception e) {
				    e.printStackTrace();
				   }
				   String color = request.getParameter("color"+ index);
				   int count = 0;
					try {
						count = Integer.parseInt(request.getParameter("count"+ index));
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				   ProductDetail pd = new ProductDetail();
				   pd.setColor(color);
				   pd.setPicturePath(Constant.UPLOAD_PIC_PATH + "/" + picname);
				   pd.setProductid(p.getId());
				   pd.setCount(count);
				   pdList.add(pd);
			}
		}
		if(CollectionUtils.isNotEmpty(pdList))
		{
			this.productService.saveProductDetailList(pdList);
		}
		return "redirect:/product/" + p.getId() + "/show";
	}
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public ModelAndView showList(@RequestParam(value = "page",required=false)Integer page)
	{
		if(page == null)
		{
			page = 1;
		}
		PageBean pb = new PageBean();
		pb.setPage(page);
		pb.setSize(PageBean.MIN_PAGESIZE);
		pb.addDescOrder("id");
		List<Product> pList = this.productService.getList(pb);
		for(Product p : pList)
		{
			List<ProductDetail> pdList = this.productService.getProudctDetailList(p.getId());
			p.setPdList(pdList);
		}
		
		ModelAndView view = new ModelAndView();
		view.setViewName("product/list");
		view.addObject("page", pb);
		view.addObject("productList",pList);
		return view;
	}
	
	@RequestMapping(value = "{productId}/show",method = RequestMethod.GET)
	public ModelAndView showProduct(@PathVariable("productId") Integer productId)
	{
		Product p = this.productService.getProduct(productId);
		List<ProductDetail> pdList = this.productService.getProudctDetailList(p.getId());
		p.setPdList(pdList);
		ModelAndView view = new ModelAndView();
		view.addObject("product",p);
		view.setViewName("product/show");
		return view;
	}
	
	
	private String getUploadPicturePath()
	{
		String path = this.servletContext.getRealPath("/" + Constant.UPLOAD_PIC_PATH) + "/"; 
		File dir = new File(path);
		if(!dir.exists())
		{
			dir.mkdirs();
		}
		return path;
	}
	private String getUploadVideoPath()
	{
		String path = this.servletContext.getRealPath("/" + Constant.UPLOAD_VIDEO_PATH) + "/"; 
		File dir = new File(path);
		if(!dir.exists())
		{
			dir.mkdirs();
		}
		return path;
	}
	
	

}
