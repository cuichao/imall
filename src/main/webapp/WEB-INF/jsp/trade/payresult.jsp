<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="/WEB-INF/jsp/global.jsp"%>
<html>
<head>
<title>支付订单</title>
</head>
<body>
<%@ include file="../header.jsp"%>
<div id="main">
<c:if test="${payresult == 0 }">
恭喜你，支付成功了，等着拿货吧！
</c:if>
<c:if test="${payresult == 1 }">
对不起，支付失败了，请重新尝试一次付款吧！
</c:if>
</div>
<%@ include file="../footer.jsp"%>
</body>
</html>