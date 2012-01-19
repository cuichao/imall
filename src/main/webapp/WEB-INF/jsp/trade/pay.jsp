<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="/WEB-INF/jsp/global.jsp"%>
<html>
<head>
<title>支付订单</title>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/order.js"></script>
</head>
<body>
<%@ include file="../header.jsp"%>
<div id="main">
	<div class="container clearfix">
	<div class="pay-successful">
	订单${order.id}已提交，您总共需要支付<span class="red">￥${order.totalpay}</span>
	<c:if test="${order.payType == 'PAY_ONLINE' }">
	您选择的方式为在线支付，请从下面选择网上银行进行支付！
	</c:if>
	<input type="hidden" id ="orderid" value="${order.id}">
	<div>
	<img id="CMB" src="${imall_path}images/pic_bank_zs.gif" onclick="selectBank2Pay(this);"></img>
	<img id="CCB" src="${imall_path}images/pic_bank_js.gif" onclick="selectBank2Pay(this);"></img>
	<img id="ICBCB2C" src="${imall_path}images/pic_bank_gs.gif" onclick="selectBank2Pay(this);"></img>
	<img id="COMM" src="${imall_path}images/pic_bank_jt.gif" onclick="selectBank2Pay(this);"></img>
	<img id="BOCB2C" src="${imall_path}images/pic_bank_zg.gif" onclick="selectBank2Pay(this);"></img>
	</div>
	</div>
	</div>
</div>
<%@ include file="../footer.jsp"%>
</body>
</html>