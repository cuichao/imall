<%@ include file="/WEB-INF/jsp/global.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/user.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/jquery.form.js"></script>
</head>
<body>
<div id="show_address_div">

	<div id="old_address_div">
	<b>现有收货地址：</b>
		<c:forEach items="${addressList}" var="address">
		<c:set var="index" value="${index + 1}"/>
			<div id="div_address_${address.id}">
				<ul>
				<li>姓名：<span id="span_accepter_${address.id}">${address.accepter}</span></li>
				<li>地址：<span id="span_address_${address.id}">${address.address}</span></li>
				<li>邮编：<span id="span_mailcode_${address.id}">${address.mailcode}</span></li>			
				<li>手机：<span id="span_phone_${address.id}">${address.phone}</span></li>
				<li>固话：<span id="span_telephone_${address.id}">${address.telephone}</span></li>
				</ul>
			<div id="button_div_${address.id}">
			<input type="button" value="修改" onclick="modifyAddress(${address.id});">
			<input type="button" value="删除" onclick="deleteAddress('${address.id}');">
			</div>
			</div>
		</c:forEach>
	</div>
	
	<div id="new_address_div">
	<b>新增收货地址：</b>
		<form id="addressForm" action="${imall_path}user/address/createSubmit"
			method="POST">
			<ul>
			<li>姓名：<input type="text" id="accepter" name="accepter"></li>
			<li>地址：<input type="text" id="address" name="address"></li>
			<li>邮编：<input type="text" id="mailcode" name="mailcode"></li>
			<li>手机：<input type="text" id="phone" name="phone"></li>
			<li>固话：<input type="text" id="telephone" name="telephone"></li>
			<hr class="hr12" />
			<li><input class="btn" type="button" value="保存" onclick="saveAddress();"></li>
			</ul>
		</form>
	</div>
	
</div>
</body>
</html>