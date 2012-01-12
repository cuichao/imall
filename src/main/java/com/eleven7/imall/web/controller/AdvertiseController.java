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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.eleven7.imall.bean.Advertise;
import com.eleven7.imall.constant.Constant;
import com.eleven7.imall.service.IAdvertiseService;

@Controller
@RequestMapping(value = "/advertise")
public class AdvertiseController implements ServletContextAware {

	private static Log log = LogFactory.getLog(AdvertiseController.class);
	private ServletContext servletContext;

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@Autowired
	private IAdvertiseService advertiseService;

	public IAdvertiseService getAdvertiseService() {
		return advertiseService;
	}

	public void setAdvertiseService(IAdvertiseService advertiseService) {
		this.advertiseService = advertiseService;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String index() {
		return "advertise/create";
	}

	@RequestMapping(value = "/createSubmit", method = RequestMethod.POST)
	public String createSubmit(HttpServletRequest request) {

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		List<Advertise> adList = new ArrayList<Advertise>();

		for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
			MultipartFile picFile = entry.getValue();
			if (!picFile.isEmpty()) {
				String filename = picFile.getOriginalFilename();
				String index = entry.getKey().substring(3);// pic123
				String url = request.getParameter("url" + index);
				String type = request.getParameter("type" + index);
				try {
					Integer productId = Integer.parseInt(url);
					url = this.getProductUrl(request, productId);
				} catch (NumberFormatException e1) {
				}
				String path = this.getUploadAdvertisePath();
				String picname = new Date().getTime() + "_" + filename;
				File file = new File(path + picname);
				log.info("advertise filepath===" + file.getAbsolutePath());
				try {
					picFile.transferTo(file);
				} catch (Exception e) {
					e.printStackTrace();
				}
				Advertise ad = new Advertise();
				ad.setPicturePath(Constant.UPLOAD_ADVERTISE_PATH + "/"
						+ picname);
				ad.setUrl(url);
				ad.setType(Integer.parseInt(type));
				adList.add(ad);

			}
		}
		if (!CollectionUtils.isEmpty(adList)) {
			this.advertiseService.saveOrUpdateList(adList);
		}
		return "advertise/result";
	}

	private String getUploadAdvertisePath() {
		String path = this.servletContext.getRealPath("/"
				+ Constant.UPLOAD_ADVERTISE_PATH)
				+ "/";
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return path;
	}

	private String getProductUrl(HttpServletRequest request, Integer productId) {
		return request.getContextPath() + "/product/" + productId + "/show";
	}

}
