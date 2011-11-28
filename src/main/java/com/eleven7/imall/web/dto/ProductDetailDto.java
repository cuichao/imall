package com.eleven7.imall.web.dto;

import com.eleven7.imall.bean.Product;
import com.eleven7.imall.bean.ProductDetail;

public class ProductDetailDto {
	
	private ProductDetail detail;
	private Product product;
	private int count;
	
	public ProductDetail getDetail() {
		return detail;
	}
	public void setDetail(ProductDetail detail) {
		this.detail = detail;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	public ProductDetailDto(){}
	public ProductDetailDto(Product p,ProductDetail pd,int count)
	{
		this.product = p;
		this.detail = pd;
		this.count = count;
	}

}
