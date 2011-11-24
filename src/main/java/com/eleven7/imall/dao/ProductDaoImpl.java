package com.eleven7.imall.dao;

import org.springframework.stereotype.Repository;

import com.eleven7.imall.bean.Product;
import com.eleven7.imall.dao.base.GenericDaoImpl;

@Repository
public class ProductDaoImpl extends GenericDaoImpl<Product, Integer> implements IProductDao {
	
	public ProductDaoImpl()
	{
		super(Product.class);
	}
	

}
