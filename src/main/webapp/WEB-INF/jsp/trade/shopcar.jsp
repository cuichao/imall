<%@ include file="/WEB-INF/jsp/global.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>我的购物车</title>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/shopcar.js"></script>
</head>
<body>
	<%@ include file="../header.jsp"%>

	<div id="main">
		<div class="container clearfix">
			<div class="cart clearfix">
				<h2>opena</h2>
				<h3>购物车</h3>

				<c:set var="dto_size" value="${fn:length(pdDtoList)}" />
				<table id="shopcarTable">
					<tr>
						<th class="cart-title">商品</th>
						<th class="cart-price">单价</th>
						<th class="cart-num">数量</th>
					</tr>
					<c:if test="${dto_size == 0 }">
						您还没有选择商品，<a href="${imall_path}product/list">去逛一下吧！</a>
					</c:if>
					<c:if test="${dto_size > 0 }">
						<c:forEach items="${pdDtoList}" var="dto">
							<tr>
								<td class="cart-title">
									<!--product begin-->
									<div>
										<a href="${imall_path}product/${dto.product.id}/show"> <img
											src="${imall_path}${dto.detail.picturePath}" alt="说明"
											title="说明" class="img_small_size" /> ${dto.product.name} </a>
									</div> <!--product end--></td>
								<td class="cart-price">￥<span name="price"
									id="${dto.detail.id}_price" value="${dto.product.price}">${dto.product.price}</span>
								</td>
								<td class="cart-num"><input type="text"
									id="${dto.detail.id}_count" value="${dto.count}"
									onblur="setCount('${dto.detail.id}_count');" size="3"><a
									href="javascript:addCount('${dto.detail.id}_count');">++</a>&nbsp;&nbsp;<a
									href="javascript:subCount('${dto.detail.id}_count')">--</a>
								</td>

							</tr>
						</c:forEach>
						<tr>
							<td class="cart-submit" colspan="3">商品总额总计：￥<span
								id="totalmoney"></span></td>
						</tr>
						<tr>
							<td id="bg_none" colspan="3" >
							<input class="btn fr" type="button"
								value="结算" onclick="saveProduct2Cookie();">
							</td>
						</tr>
					</c:if>
				</table>
			</div>
		</div>
	</div>
	<%@ include file="../footer.jsp"%>
</body>
</html>
</body>
</html>