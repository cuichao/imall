<%@ include file="/WEB-INF/jsp/global.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>已购商品</title>
</head>
<body class="bg-c-base">
<div class="order-productlist">
			<c:set var="dto_size" value="${fn:length(orderdetailList)}" />
			<table id="shopcarTable">
				<tr class="order-th">
					<th style="width: 50%">商品</th>
					<th style="width: 10%">单价</th>
					<th>订单号</th>
				</tr>
				<c:if test="${dto_size > 0 }">
					<c:forEach items="${orderdetailList}" var="dto">
						<tr>
							<td>
								<!--product begin-->
									<a class="list-img fl"
										href="${imall_path}product/${dto.product.id}/show"> <img
										src="${imall_path}${dto.productDetail.picturePath}" alt="说明"
										title="说明" class="img_small_size" /> </a>
									<div class="list-content fl order-product-title">
										<h3>
											<a href="${imall_path}product/${dto.product.id}/show">${dto.product.name}</a>
										</h3>
									</div>
								<!--product end--></td>
							<td>￥<span name="price" id="${dto.productDetail.id}_price"
								value="${dto.product.price}">${dto.product.price}</span>
							</td>
							<td><a href="${imall_path}trade/${dto.orderid}/order">${dto.orderid}</a></span>
							</td>
						</tr>
					</c:forEach>
				</c:if>
			</table>
		</div>
</body>
</html>