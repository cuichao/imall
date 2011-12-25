<%@ include file="/WEB-INF/jsp/global.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.eleven7.imall.bean.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><c:if test="${orderType == 'toSendList'}">
待发货的订单
</c:if> <c:if test="${orderType == 'sendingList'}">
送货中的订单
</c:if> <c:if test="${orderType == 'finishList'}">
已完成的订单
</c:if> <c:if test="${orderType == 'canceledList'}">
已取消的订单
</c:if></title>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/order.js"></script>
</head>
<body>
	<div>
		<table style="width: 100%;" border="1">
			<tr>
				<th>订单号</th>
				<th>订单提交人</th>
				<th>收货人</th>
				<th>收货地址</th>
				<th>订单时间</th>
				<th>送货方式</th>
				<th>送货时间</th>
				<th>支付方式</th>
				<th>运费</th>
				<th>总付款额</th>
				<th>订单状态</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${orderList}" var="order">
				<tr>
					<td>${order.id}</td>
					<td><a href="${imall_path}user/settings">${order.user.nickname}</a>
					</td>

					<td>${order.accepter}</td>
					<td>${order.toAddress}</td>

					<td>${order.createtime}</td>
					<td><c:if test="${order.sendType == 1}">
	   普通快递送货上门
	 </c:if> <c:if test="${order.sendType == 2}">
	   加急快递送货上门
	 </c:if>
					</td>
					<td><c:if test="${order.timeType ==  'WEEK_1_TO_5' }">
	  周一到周五
	 </c:if> <c:if test="${order.timeType ==  'NO_TIME_LIMIT' }">
	  无时间限制
	 </c:if> <c:if test="${order.timeType ==  'WEEKDAY_AND_OTHER'}">
	  周六日以及法定假日
	 </c:if>
					</td>
					<td><c:if test="${order.payType == 'PAY_ONLINE' }">
	 网上支付
	 </c:if> <c:if test="${order.payType == 'PAY_AFTER_PRODUCT_ARRIVED'}">
	 货到付款
	 </c:if>
					</td>
					<td>￥${order.carriageCost }</td>
					<td>￥${order.totalpay}</td>
					<td id="td_${order.id}_order_status">
	   <c:if test="${order.status  == 'prePay'}">
	      等待付款
	   </c:if> 
	    <c:if test="${order.status  == 'postPay'}">
	   已付款，等待发货
	 </c:if> 
	    <c:if test="${order.status  == 'sending'}">
	   送货中
	   </c:if> 
	    <c:if test="${order.status  == 'arrived'}">
	    货已送到
	   </c:if> 
	    <c:if test="${order.status  == 'canceled'}">
	   已取消
	   </c:if> 
	   <c:if test="${order.status  == 'preSend'}">
	     等待发货（货到付款）
	  </c:if>
	  <c:if test="${order.status  == 'payment_arrived'}">
	    快递员已回款（货到付款）
	   </c:if> 
	  </td>
					<td>
					<c:if test="${order.status  == 'postPay' || order.status  == 'preSend'}">
					 <input type="button" value="发货"
						id="${order.id}_send_button" onclick="sendOrder('${order.id}');">
					</c:if>
					</td>
				</tr>
				<tr>
					<td colspan="12">
						<table border="1">
							<tr>
								<th>商品</th>
								<th>单价</th>
								<th>数量</th>
								<th>金额</th>
							</tr>
							<c:forEach items="${order.odList}" var="od">
								<tr>
									<td><a href="${imall_path}product/${od.productid}/show">
											<img src="${imall_path}${od.productDetail.picturePath}"
											alt="说明" title="说明" class="img_small_size" />
											${od.product.name} </a>
									</td>
									<td>${od.price}</td>
									<td>${od.num}</td>
									<td>${od.totalpay}</td>
								</tr>
							</c:forEach>
						</table></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>