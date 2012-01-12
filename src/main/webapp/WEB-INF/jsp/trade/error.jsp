<%@ include file="/WEB-INF/jsp/global.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<body>
	<%@ include file="../header.jsp"%>
	<div id="main">
		<c:if test="${ error != null }">
			<span class="red">${error}</span>
		</c:if>
		<c:if test="${ pdDtoList != null }">
			<span class="red">抱歉，以下商品库存不足了，请调整购买数量，或等待IMALL工作人员处理商品库存，谢谢！</span>
			<!-- begin 商品清单-->
			<h2>
				<b>商品清单：</b>
			</h2>
			<div class="order-productlist">
				<c:set var="dto_size" value="${fn:length(pdDtoList)}" />
				<table id="shopcarTable">
					<tr class="order-th">
						<th style="width: 50%">商品</th>
						<th style="width: 10%">单价</th>
						<th class="order-product" style="width: 10%">数量</th>
					</tr>
					<c:if test="${dto_size > 0 }">
						<c:forEach items="${pdDtoList}" var="dto">
							<tr>
								<td>
									<!--product begin--> <a class="list-img fl"
									href="${imall_path}product/${dto.product.id}/show"> <img
										src="${imall_path}${dto.detail.picturePath}" alt="说明"
										title="说明" class="img_small_size" /> </a>
									<div class="list-content fl order-product-title">
										<h3>
											<a href="${imall_path}product/${dto.product.id}/show">${dto.product.name}</a>
										</h3>
									</div> <!--product end-->
								</td>
								<td>￥<span name="price" id="${dto.detail.id}_price"
									value="${dto.product.price}">${dto.product.price}</span></td>
								<td class="order-product"><span id="${dto.detail.id}_count">${dto.count}</span>
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</table>
			</div>
		</c:if>
	</div>
	<%@ include file="../footer.jsp"%>
</body>
</html>