<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="/WEB-INF/jsp/global.jsp"%>
<html>
<head>
<title>确认订单</title>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/order.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/jquery.form.js"></script>
</head>
<body>
	<%@ include file="../header.jsp"%>
	<div id="main">
		<div class="container clearfix">
		<form:form action="${imall_path}trade/saveorder" method="POST" modelAttribute="orderDto" class="order-ensure">
	   <!-- begin 收货人信息 -->
		<div>
			<c:set var="address_size" value="${fn:length(addressList)}" />
			<h2>
				<b>收货人信息:</b>
				<a id="a_address" class="order-modify" href="javascript:modifyAddress();">
				<c:if test="${address_size == 0 }">添加</c:if>
				<c:if test="${address_size > 0 }">修改</c:if></a>
			</h2>
			<div id="selectAddressDiv"></div>
			<div id="selected_address">
					<c:if test="${address_size > 0 }">
						<c:forEach items="${addressList}" var="address" begin="0" end="0">
							<input type="hidden" id="address_id" name="address_id" value="${address.id}">
						<ul>
		                	<li>姓名：${address.accepter}</li>                    
		                    <li>地址：${address.address}</li>
		                    <li>邮编：${address.mailcode}</li>
		                    <li>手机：${address.phone}</li>
		                    <li>固话：${address.telephone}</li>
	                    </ul>
						</c:forEach>
					</c:if>
					<c:if test="${address_size == 0 }">
					<input type="hidden" id="address_id" name="address_id" value="">
					请添加收货人信息！
					</c:if>
			</div>
		</div>
		<!-- end 收货人信息 -->
		<!-- begin 送货方式 -->
		<div>
			<h2>
				<b>送货方式 :</b>
				<a class="order-modify" href="javascript:modifySendtype();">修改</a>
			</h2>
			<c:forEach items="${sendtypeList}" var="sendtype" begin="0" end="0">
				<div id="selected_sendtype">${sendtype.desc} ${sendtype.price}元,周一到周五</div>
					<input type="hidden" name="send_type" id="send_type" value="${sendtype.id}">
					<input type="hidden" name="time_type" id="time_type" value="1"><!-- 周一到周五 -->
					<!-- 运费 -->
					<input type="hidden" name="carriage" id="carriage" value="${sendtype.price}">
				</c:forEach>
			
			<div id="select_sendtype_div" class="none">
				<c:set var="s_index" value="0" />
				<c:forEach items="${sendtypeList}" var="sendtype">
					<c:set var="s_index" value="${s_index + 1}" />
					<div>
						<input type="radio" id="sendtype_${sendtype.id}"
							name="sendtype_radio" <c:if test="${s_index == 1}">checked</c:if>><span
							id="span_st_${sendtype.id}" price="${sendtype.price}">${sendtype.desc} ${sendtype.price}元</span>
					</div>
				</c:forEach>
				<ul>
                  <li>
	                  <select id="select_timetype" name="select_timetype">
						<option value="0">
							无时间限制
						</option>
						<option value="1" selected>
							周一到周五
						</option>
						<option value="2">
							周六日以及法定假日
						</option>
					</select>
				  </li>
				  <hr class="hr12" />
                  <li>
                  	<input class="btn" type="button" value="确认送货方式" onclick="confirmSendtype();">
					<input class="btn" type="button" value="取消" onclick="cancelSendtype();"></li>
                 </ul>
				
				<div>
					
				</div>
			</div>
		</div>
		<!-- end 送货方式 -->
		<!-- begin 付款方式 -->
		<div>
			<h2>
				<b>付款方式 :</b>
				<a class="order-modify" href="javascript:modifyPayType();">修改</a>
			</h2>
			<ul>
               	<li id="selected_paytype">网上支付</li>
	        </ul>
			<input type="hidden" name="pay_type" id="pay_type" value="0"><!--0为 网上支付，1为货到付款 -->

			<div id="select_paytype_div" class="none">
				<input type="radio" id="paytype_0" name="paytype_radio" checked>
				<span id="span_pt_0">网上支付</span>	 您需要先拥有一张已开通网上支付功能的银行卡
				<div id="pic_bank_div">
				<img  src="${imall_path}images/pic_bank.gif" />
				</div>
				<input type="radio" id="paytype_1" name="paytype_radio">
				<span id="span_pt_1">货到付款</span> 您需要在收货时向送货员支付订单款项
				<hr class="hr12" />
				<div>
					<input class="btn" type="button" value="确认支付方式" onclick="confirmPaytype();">
					<input class="btn" type="button" value="取消" onclick="cancelPaytype();">
				</div>
			</div>
		</div>
		<!-- end 付款方式 -->
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
								<!--product begin-->
									<a class="fl"
										href="${imall_path}product/${dto.product.id}/show"> <img
										src="${imall_path}${dto.detail.picturePath}" alt="说明"
										title="说明" class="img_small_size" /> </a>
									<div class="list-content fl order-product-title">
										<h3>
											<a href="${imall_path}product/${dto.product.id}/show">${dto.product.name}</a>
										</h3>
									</div>
								<!--product end--></td>
							<td>￥<span name="price" id="${dto.detail.id}_price"
								value="${dto.product.price}">${dto.product.price}</span>
							</td>
							<td class="order-product"><span id="${dto.detail.id}_count">${dto.count}</span>
							</td>
						</tr>
					</c:forEach>
					<tr>
						<td colspan="3" class="order-info">商品总额总计：￥<span id="totalmoney">${totalMoney}</span>
						</td>
					</tr>
					<tr>
						<td colspan="3" class="order-info">运费：￥<span id="carriage_cost">0.00</span>
						</td>
					</tr>
					<tr>
						<td colspan="3" class="order-info">您需要支付：￥<span id="total_to_pay">0.00</span>
						</td>
					</tr>
					<tr>
						<td colspan="3" class="order-info"><input type="submit" value="提交定单" class="btn" onclick="return checkOrder();">
						</td>
					</tr>
				</c:if>
			</table>
		</div>
		<!-- end 商品清单-->
	</form:form>
	</div>
	</div>
	<%@ include file="../footer.jsp"%>
</body>
<script>
$(document).ready(function() {
	computerTotalPay();
});
</script>
</html>