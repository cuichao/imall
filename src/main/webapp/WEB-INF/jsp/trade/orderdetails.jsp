<%@ include file="/WEB-INF/jsp/global.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>订单详情</title>
</head>
<body class="bg-c-base">
	<div id="main">
		<div id="show_address_div">
	   <!-- begin 收货人信息 -->
	   
		<div>
			<h3>收货人信息：</h3>
			<div> 
				<ul>
					<li>姓名：<span>${order.accepter}</span></li>
					<li>地址：<span>${order.toAddress}</span></li>
					<li>邮编：<span> </span></li>			
					<li>手机：<span> </span></li>
					<li>固话：<span> </span></li>
				</ul>
			</div>
		</div>
		<hr class="hr12" />
		<!-- end 收货人信息 -->
		<!-- begin 送货方式 -->
		<div>
			<h3>送货方式 :</h3>		
				<div>
					<c:if test="${order.sendType == 1}">
					   普通快递送货上门
					 </c:if> <c:if test="${order.sendType == 2}">
					   加急快递送货上门
					 </c:if> &nbsp;<c:if test="${order.timeType ==  'WEEK_1_TO_5' }">
					  周一到周五
					 </c:if> <c:if test="${order.timeType ==  'NO_TIME_LIMIT' }">
					  无时间限制
					 </c:if> <c:if test="${order.timeType ==  'WEEKDAY_AND_OTHER'}">
					  周六日以及法定假日
					 </c:if>
				</div>
		</div>
		<hr class="hr12" />
		<!-- end 送货方式 -->
		<!-- begin 付款方式 -->
		<div>
			<h3>付款方式 :</h3>
			<div>
			<c:if test="${order.payType == 'PAY_ONLINE' }">
			 网上支付
			 </c:if> <c:if test="${order.payType == 'PAY_AFTER_PRODUCT_ARRIVED'}">
			 货到付款
			 </c:if>
			</div>
		</div>
		<!-- end 付款方式 -->
		<!-- begin 商品清单-->
		<hr class="hr12" />
		<h3>商品清单：</h3>
		<div class="order-productlist">
			<c:set var="dto_size" value="${fn:length(order.odList)}" />
			<table id="shopcarTable">
				<tr class="order-th">
					<th style="width: 50%">商品</th>
					<th style="width: 10%">单价</th>
					<th class="order-product" style="width: 10%">数量</th>
				</tr>
				<c:if test="${dto_size > 0 }">
					<c:forEach items="${order.odList}" var="dto">
						<tr>
							<td>
								<!--product begin-->
									<a class="fl" target="_blank"
										href="${imall_path}product/${dto.product.id}/show"> <img
										src="${imall_path}${dto.productDetail.picturePath}" alt="说明"
										title="说明" class="img_small_size" /> </a>
									<div class="list-content fl order-product-title">
											<a target="_blank" href="${imall_path}product/${dto.product.id}/show">${dto.product.name}</a>
									</div>
								<!--product end--></td>
							<td>￥<span name="price" id="${dto.productDetail.id}_price"
								value="${dto.product.price}">${dto.product.price}</span>
							</td>
							<td class="order-product"><span id="${dto.productDetail.id}_count">${dto.num}</span>
							</td>
						</tr>
					</c:forEach>
					<tr>
						<td colspan="3" class="order-info">运费：￥<span id="carriage_cost">${order.carriageCost}</span>
						</td>
					</tr>
					<tr>
						<td colspan="3" class="order-info">支付总计：￥<span id="totalmoney">${order.totalpay}</span>
						</td>
					</tr>
				</c:if>
			</table>
		</div>
		<!-- end 商品清单-->
	</div>
	</div>
</body>
</html>