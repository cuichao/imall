<%@ include file="/WEB-INF/jsp/global.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>确认订单</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/order.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.form.js"></script>
</head>
<body>
<%@ include file="../header.jsp"%>
<form>
<div>
<c:set var="address_size" value="${fn:length(addressList)}"/>
<h2><b>收货人信息：
[<a id="a_address" href="javascript:modifyAddress();">
<c:if test="${address_size == 0 }">
添加
</c:if>
<c:if test="${address_size > 0 }">
修改
</c:if>
</a>]
</b></h2>
<div id ="selectAddressDiv"></div>
<div id="selected_address">
<c:forEach items="${addressList}" var="address" begin="0" end="0">
<input type="hidden" id="address_id" name="address_id" value="${address.id}">				
<div>收货人：${address.accepter}</div>
<div>地址：${address.address}&nbsp;&nbsp;邮编：${address.mailcode}</div>
<div>手机：${address.phone} &nbsp;&nbsp;固定电话：${address.telephone}</div>
</c:forEach>
</div>
</div>
<div>
<h2><b>商品清单：</b></h2>
<c:set var="dto_size" value="${fn:length(pdDtoList)}"/>
<table id="shopcarTable">
<tr>
<th style="width:50%">商品</th>
<th style="width:10%">单价</th>
<th style="width:10%">数量</th>
</tr>
<c:if test="${dto_size > 0 }">
<c:forEach items="${pdDtoList}" var="dto">
<tr>
<td>
<!--product begin-->
<div>
	<a class="list-img fl" href="${imall_path}product/${dto.product.id}/show">							
			<img src="${imall_path}${dto.detail.picturePath}" alt="说明"
				title="说明" class="img_small_size" />
	</a>
	<div class="list-content fl">
		<h3>							 
			<a href="${imall_path}product/${dto.product.id}/show">${dto.product.name}</a>
		</h3>
	</div>
</div>
<!--product end-->
</td>
<td>￥<span name="price" id="${dto.detail.id}_price" value="${dto.product.price}">${dto.product.price}</span></td>
<td><span id="${dto.detail.id}_count">${dto.count}</span></td>
</tr>
</c:forEach>
<tr>
<td></td><td></td>
<td>商品总额总计：￥<span id="totalmoney">${totalMoney}</span></td>
</tr>
<tr>
<td></td><td></td><td><input type="submit" value="提交定单" class="btn"></td>
</tr>
</c:if>
</table>
</div>
</form>
<%@ include file="../footer.jsp"%>
</body>
</html>