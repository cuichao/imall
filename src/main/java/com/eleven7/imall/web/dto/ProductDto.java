package com.eleven7.imall.web.dto;

import com.eleven7.imall.bean.Product;

public class ProductDto {
	
	private String name;
	private String desc;
	private double price;
	private String short_desc;
	
	public String getShort_desc() {
		return short_desc;
	}
	public void setShort_desc(String short_desc) {
		this.short_desc = short_desc;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	public Product getProductByDto()
	{
		Product p = new Product();
		p.setDescription(desc);
		p.setName(name);
		p.setPrice(price);
		p.setShortDesc(short_desc);
		return p;
	}
	

}
