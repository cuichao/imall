package com.eleven7.imall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eleven7.imall.bean.Product;
import com.eleven7.imall.bean.ProductComment;
import com.eleven7.imall.bean.ProductDetail;
import com.eleven7.imall.bean.SendType;
import com.eleven7.imall.dao.IProductCommentDao;
import com.eleven7.imall.dao.IProductDao;
import com.eleven7.imall.dao.IProductDetailDao;
import com.eleven7.imall.dao.ISendTypeDao;
import com.eleven7.imall.dao.base.PageBean;

@Service
public class ProductServiceImpl implements IProductService{
	
	@Autowired
	private IProductDao productDao;
	
	@Autowired
	private IProductDetailDao productdetailDao;
	
	@Autowired
	private IProductCommentDao productCommentDao;
	
	@Autowired
	private ISendTypeDao sendTypeDao;
	
	
	public ISendTypeDao getSendTypeDao() {
		return sendTypeDao;
	}

	public void setSendTypeDao(ISendTypeDao sendTypeDao) {
		this.sendTypeDao = sendTypeDao;
	}

	public IProductDao getProductDao() {
		return productDao;
	}

	public void setProductDao(IProductDao productDao) {
		this.productDao = productDao;
	}

	public IProductDetailDao getProductdetailDao() {
		return productdetailDao;
	}

	public void setProductdetailDao(IProductDetailDao productdetailDao) {
		this.productdetailDao = productdetailDao;
	}
	public IProductCommentDao getProductCommentDao() {
		return productCommentDao;
	}

	public void setProductCommentDao(IProductCommentDao productCommentDao) {
		this.productCommentDao = productCommentDao;
	}
	

	public void saveProduct(Product p)
	{
		this.productDao.save(p);
	}
	
	public Product getProduct(Integer pid)
	{
		return this.productDao.get(pid);
	}
	public List<Product> getList(PageBean pb)
	{
		return this.productDao.findAll(pb);
	}
	public void saveOrUpdateProductDetailList(List<ProductDetail> list)
	{
		this.productdetailDao.saveOrUpdateAll(list);
	}
	public List<ProductDetail> getProudctDetailList(Integer productid)
	{
		return this.productdetailDao.findByProperty("productid", productid);
	}
	public ProductDetail getProductDetail(Integer pd_id)
	{
		return this.productdetailDao.get(pd_id);
	}
	
	public void saveComment(ProductComment pc)
	{
		this.productCommentDao.save(pc);
	}
	public List<ProductComment> getCommentList(Integer pid)
	{
		PageBean pb = new PageBean();
		pb.setShowAll(true);
		pb.addDescOrder("createtime");
		return this.productCommentDao.findByProperty(pb, "productid", pid);
	}
	public List<SendType> getAllSendType()
	{
		return this.sendTypeDao.findAll();
	}

}
