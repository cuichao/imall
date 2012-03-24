<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
<%@ include file="/WEB-INF/jsp/global.jsp"%>
</head>
<html>
<style type="text/css">
	html,body{ height:100%; }
</style>
<body>
<%@ include file="../header.jsp"%>
<div id="main">
	<div class="container clearfix" style="text-align:center; font: 18px/145px arial; ">
	<c:if test="${result !=null}">
	<c:out value="${result}"/>
	</c:if>
	<c:if test="${error != null}">
	<c:out value="${error}"/>
	</c:if>
	</div>
</div>
<%@ include file="../footer.jsp"%>
</body></html>
