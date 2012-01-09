package com.eleven7.imall.service;

import java.util.List;

import com.eleven7.imall.bean.Product;
import com.eleven7.imall.dao.base.PageBean;

public interface ILuceneService {
	
	public boolean buildProductIndex(Product p);
	
	public List<Product> queryProduct(String query,PageBean pb);

}
