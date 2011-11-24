package com.eleven7.imall.dao;

import org.springframework.stereotype.Repository;

import com.eleven7.imall.bean.Product;
import com.eleven7.imall.bean.ProductDetail;
import com.eleven7.imall.dao.base.GenericDaoImpl;

@Repository
public class ProductDetailDaoImpl extends GenericDaoImpl<ProductDetail, Integer> implements IProductDetailDao {
	
	public ProductDetailDaoImpl()
	{
		super(ProductDetail.class);
	}
	

}
