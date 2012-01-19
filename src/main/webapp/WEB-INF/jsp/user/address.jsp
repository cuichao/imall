<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="/WEB-INF/jsp/global.jsp"%>
<c:set var="address_size" value="${fn:length(addressList)}"/>
<c:set var="index" value="0"/>
<div id="show_address_div">
	<div id="old_address_div" <c:if test="${address_size == 0}">class="none"</c:if>>
		<c:forEach items="${addressList}" var="address">
		<c:set var="index" value="${index + 1}"/>
			<div>
				<input type="hidden" id="address_${address.id}" name="address_radios" <c:if test="${index == 1}">checked</c:if>>
				<ul id="span_address_${address.id}">
				<li>姓名：${address.accepter}</li>
				<li>地址：${address.address}</li>
				<li>邮编：${address.mailcode}</li>
				<li>手机：${address.phone}</li>
				<li>固话：${address.telephone}</li>
				
				</ul>
			</div>
		</c:forEach>
		<ul>
		<li>
		<input class="btn" type="button" value="确认收货地址" onclick="confirmAddress();">&nbsp;
		<input class="btn" type="button" value="取消" onclick="cancelAddress();">&nbsp;
		<c:if test="${address_size > 0}">
		<input class="btn" type="button" value="使用新地址" onclick='$("#new_address_div").show();'>
		</c:if>
		</li>
		</ul>
	</div>
	
	<div id="new_address_div" <c:if test="${address_size > 0}">class="none"</c:if>>
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