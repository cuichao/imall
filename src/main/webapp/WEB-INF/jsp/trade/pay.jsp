<%@ include file="/WEB-INF/jsp/global.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>支付订单</title>
</head>
<body>
<%@ include file="../header.jsp"%>
<div id="main">
	<div class="container clearfix">
	<div class="pay-successful">
	订单${order.id}已提交，您总共需要支付<span class="red">￥${order.totalpay}</span>
	<a href="">查看订单</a>
	</div>
	</div>
</div>
<%@ include file="../footer.jsp"%>
</body>
</html>