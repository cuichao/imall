package com.eleven7.imall.bean;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "order_detail")
public class OrderDetail {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false)
	private int id;
	@Column
	private int orderid;
	@Column
	private int productid;
	@Column
	private double price;
	@Column
	private double totalpay;
	@Column
	private int num;
	@Column
	private int productdetailid;
	
	@Transient
	private ProductDetail productDetail;
	@Transient
	private Product product;
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public ProductDetail getProductDetail() {
		return productDetail;
	}
	public void setProductDetail(ProductDetail productDetail) {
		this.productDetail = productDetail;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOrderid() {
		return orderid;
	}
	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}
	public int getProductid() {
		return productid;
	}
	public void setProductid(int productid) {
		this.productid = productid;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getTotalpay() {
		return totalpay;
	}
	public void setTotalpay(double totalpay) {
		this.totalpay = totalpay;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getProductdetailid() {
		return productdetailid;
	}
	public void setProductdetailid(int productdetailid) {
		this.productdetailid = productdetailid;
	}
}
