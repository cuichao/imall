package com.eleven7.imall.service;

import java.util.List;

import com.eleven7.imall.bean.Product;
import com.eleven7.imall.bean.ProductComment;
import com.eleven7.imall.bean.ProductDetail;
import com.eleven7.imall.bean.SendType;
import com.eleven7.imall.dao.base.PageBean;

public interface IProductService {
	
	public void saveProduct(Product p);
	public Product getProduct(Integer pid);
	public List<Product> getList(PageBean pb);
	public void saveOrUpdateProductDetailList(List<ProductDetail> list);
	public List<ProductDetail> getProudctDetailList(Integer productid);
	public ProductDetail getProductDetail(Integer pd_id);
	
	public void saveComment(ProductComment pc);
	public List<ProductComment> getCommentList(Integer pid);
	
	public List<SendType> getAllSendType();

}
