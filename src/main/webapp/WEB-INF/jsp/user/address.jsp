<%@ include file="/WEB-INF/jsp/global.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<c:set var="address_size" value="${fn:length(addressList)}"/>
<c:set var="index" value="0"/>
<div id="show_address_div">
	<div id="old_address_div" <c:if test="${address_size == 0}">class="none"</c:if>>
		<c:forEach items="${addressList}" var="address">
		<c:set var="index" value="${index + 1}"/>
			<div>
				<input type="radio" id="address_${address.id}" name="address_radios" <c:if test="${index == 1}">checked</c:if>>
				<span id="span_address_${address.id}">${address.accepter} ,${address.address},${address.mailcode} ,${address.phone} ,${address.telephone}</span>
			</div>
		</c:forEach>
		<input type="button" value="确认收货地址" onclick="confirmAddress();">
		<input type="button" value="取消" onclick="cancelAddress();">
	</div>
	<c:if test="${address_size > 0}">
		<div><input type="button" value="使用新地址" onclick='$("#new_address_div").show();'></div>
	</c:if>
	<div id="new_address_div" <c:if test="${address_size > 0}">class="none"</c:if>>
		<form id="addressForm" action="${imall_path}user/address/createSubmit"
			method="POST">
			<table>
				<tr>
					<td>收货人：</td>
					<td><input type="text" id="accepter" name="accepter">
					</td>
				</tr>
				<tr>
					<td>地址：</td>
					<td><input type="text" id="address" name="address">
					</td>
				</tr>
				<tr>
					<td>邮编：</td>
					<td><input type="text" id="mailcode" name="mailcode">
					</td>
				</tr>
				<tr>
					<td>手机：</td>
					<td><input type="text" id="phone" name="phone">
					</td>
				</tr>
				<tr>
					<td>固话：</td>
					<td><input type="text" id="telephone" name="telephone">
					</td>
				</tr>
				<tr>
					<td></td>
					<td><input type="button" value="保存" onclick="saveAddress();">
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>