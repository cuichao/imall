<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/reset.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/layout.css">
<style type="text/css">
html,body{ height:100%; }
</style>
<div class="manage-nav">
	<div>
	<h3>订单管理</h3>
	<ul>
	<li><a href="${imall_path}../trade/myorder" target="rightFrame">我的订单</a></li>
	<li><a href="${imall_path}../trade/payhistory" target="rightFrame">订单支付历史</a></li>
	</ul>
	</div>
	<div>
	<h3>我的商品</h3>
	<ul>
	<li><a href="${imall_path}../trade/product" target="rightFrame">已购商品</a></li>
	<li><a href="javascript:void(0);" target="rightFrame">我的收藏</a></li>
	</ul>
	</div>
	<div>
	<h3>帐户管理</h3>
	<ul>
	<li><a href="${imall_path}address/manage" target="rightFrame">收货地址薄</a></li>
	</ul>
	</div>
</div>