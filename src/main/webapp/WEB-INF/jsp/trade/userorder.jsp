<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="/WEB-INF/jsp/global.jsp"%>
<html>
<head>
<title>我的订单</title>
</head>
<body class="bg-c-base">
	<div>
		<table class="base-table">
			<tr>
				<th>订单号</th>
				<th>收货人</th>
				<th>送货方式</th>
				<th>送货时间</th>
				<th>总付款额</th>
				<th>订单状态</th>
				<th>下单时间</th>
				<th>支付方式</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${orderList}" var="order">
				<tr>
					<td><a href="${imall_path}trade/${order.id}/order">${order.id}</a>
					</td>
					<td>${order.accepter}</td>
					<td><c:if test="${order.sendType == 1}">
	   普通快递送货上门
	 </c:if> <c:if test="${order.sendType == 2}">
	   加急快递送货上门
	 </c:if></td>
					<td><c:if test="${order.timeType ==  'WEEK_1_TO_5' }">
	  周一到周五
	 </c:if> <c:if test="${order.timeType ==  'NO_TIME_LIMIT' }">
	  无时间限制
	 </c:if> <c:if test="${order.timeType ==  'WEEKDAY_AND_OTHER'}">
	  周六日以及法定假日
	 </c:if></td>
					<td>￥${order.totalpay}</td>
					<td id="td_${order.id}_order_status"><c:if
							test="${order.status  == 'prePay'}">
	      等待付款
	   </c:if> <c:if test="${order.status  == 'postPay'}">
	   已付款，等待发货
	 </c:if> <c:if test="${order.status  == 'sending'}">
	   送货中
	   </c:if> <c:if test="${order.status  == 'arrived'}">
	    货已送到
	   </c:if> <c:if test="${order.status  == 'canceled'}">
	   已取消
	   </c:if> <c:if test="${order.status  == 'preSend'}">
	     等待发货（货到付款）
	  </c:if> <c:if test="${order.status  == 'payment_arrived'}">
	   货已送到
	   </c:if></td>
					<td>${order.createtime}</td>
					<td><c:if test="${order.payType == 'PAY_ONLINE' }">
	 网上支付
	 </c:if> <c:if test="${order.payType == 'PAY_AFTER_PRODUCT_ARRIVED'}">
	 货到付款
	 </c:if></td>
					<td>
					<c:if test="${ order.status == 'preSend' or order.status  == 'prePay' }">
						<a href="${imall_path}trade/${order.id}/cancel">取消</a>
					</c:if>
					</td>
				</tr>
			</c:forEach>		
		</table>
		<div class="page fr">
		 <%@ include file="../page.jsp" %>
		</div>
	</div>
<input type="hidden" name="page" id="page" value="${page.page}" />
</html>