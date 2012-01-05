<%@ include file="/WEB-INF/jsp/global.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>支付历史</title>
</head>
<body class="bg-c-base">
	<table  class="base-table">
		<tr>
			<th>时间</th>
			<th>金额</th>
			<th>支付方式</th>
			<th>订单号</th>
		</tr>
		<c:forEach items="${paymentList}" var="history">
			<tr>
				<td>${history.createtime }</td>
				<td>${history.money}</td>
				<td><c:if test="${history.payType == 'PAY_ONLINE' }">
	 网上支付
	 </c:if> <c:if test="${history.payType == 'PAY_AFTER_PRODUCT_ARRIVED'}">
	 货到付款
	 </c:if> <c:if test="${history.bank != null}">
	&nbsp;&nbsp;${history.bank}
	</c:if></td>
				<td><a href="${imall_path}trade/${history.orderid}/order">${history.orderid
						}</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>