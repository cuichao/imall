<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/global.jsp"%>
<title>Error</title>
</head>
<body>
<c:if test="${ error != null }">
<span class="red">${error}</span>
</c:if>
</body>
</html>