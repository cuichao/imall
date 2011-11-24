package com.eleven7.imall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eleven7.imall.bean.Product;
import com.eleven7.imall.bean.ProductDetail;
import com.eleven7.imall.dao.IProductDao;
import com.eleven7.imall.dao.IProductDetailDao;
import com.eleven7.imall.dao.base.PageBean;

@Service
public class ProductServiceImpl implements IProductService{
	
	@Autowired
	private IProductDao productDao;
	
	@Autowired
	private IProductDetailDao productdetailDao;
	
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
	public void saveProductDetailList(List<ProductDetail> list)
	{
		this.productdetailDao.saveOrUpdateAll(list);
	}
	public List<ProductDetail> getProudctDetailList(Integer productid)
	{
		return this.productdetailDao.findByProperty("productid", productid);
	}

}
