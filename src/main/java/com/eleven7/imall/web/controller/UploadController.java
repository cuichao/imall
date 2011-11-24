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
@RequestMapping(value = "/upload")
public class UploadController implements ServletContextAware {

	private static Log log = LogFactory.getLog(ProductController.class);

	private ServletContext servletContext;

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@RequestMapping(value = "/file", method = RequestMethod.POST)
	public void upload(HttpServletRequest request, HttpServletResponse response) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		MultipartFile mFile = fileMap.get("upload");
		List<ProductDetail> pdList = new ArrayList<ProductDetail>();
		// String type = request.getParameter("Type");//Picture or Flash
		if (!mFile.isEmpty()) {
			String filename = mFile.getOriginalFilename();
			String path = this.getUploadPath();
			String picname = new Date().getTime() + "_" + filename;
			File file = new File(path + picname);
			log.info("upload filepath===" + file.getAbsolutePath());
			try {
				mFile.transferTo(file);
				String fileUrl = this.servletContext.getContextPath() + "/"
						+ Constant.CKEDITOR_UPLOAD_PATH + "/" + picname;
				PrintWriter out = response.getWriter();
				// CKEditorFuncNum是回调时显示的位置，这个参数必须有
				String callback = request.getParameter("CKEditorFuncNum");
				out.println("<script type=\"text/javascript\">");
				out.println("window.parent.CKEDITOR.tools.callFunction("
						+ callback + ",'" + fileUrl + "',''" + ")");
				out.println("</script>");
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private String getUploadPath() {
		String path = this.servletContext.getRealPath("/"
				+ Constant.CKEDITOR_UPLOAD_PATH)
				+ "/";
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return path;
	}

}
