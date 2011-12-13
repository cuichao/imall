package com.eleven7.imall.web.dto;


public class OrderDto {
	
	private int address_id;
	private int send_type;
	private int time_type;
	private double carriage;
	private int pay_type;
	
	public int getAddress_id() {
		return address_id;
	}
	public void setAddress_id(int address_id) {
		this.address_id = address_id;
	}
	public int getSend_type() {
		return send_type;
	}
	public void setSend_type(int send_type) {
		this.send_type = send_type;
	}
	public double getCarriage() {
		return carriage;
	}
	public void setCarriage(double carriage) {
		this.carriage = carriage;
	}
	public int getTime_type() {
		return time_type;
	}
	public void setTime_type(int time_type) {
		this.time_type = time_type;
	}
	public int getPay_type() {
		return pay_type;
	}
	public void setPay_type(int pay_type) {
		this.pay_type = pay_type;
	}
	
	

}
