package com.eleven7.imall.bean;

public enum OrderStatus {
	
	/*
	 * 网上支付的流程  :
	 * prePay --> postPay -->sending -->arrived
	 * 0 --> 1 --> 2 -->3
	 * 
	 * 货到付款的流程：
	 * preSend --> sending --> pay2postman --> payment_arrived
	 * 5-->2 -->6 -->7
	 */
		prePay,//等待付款 0
		postPay,//已付款，等待发货1
		sending,//送货中2
		arrived,//送货完成3
		canceled,//取消订单4
		
		preSend,//待发货5
		pay2postman,//快递员已收款6  ----这个状态可能用不上
		payment_arrived //快递员交款7
		
		
	
}
