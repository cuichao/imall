<%@ include file="/WEB-INF/jsp/global.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html><body>
<%@ include file="../header.jsp"%>

<c:if test="${result !=null}">
<c:out value="${result}"/>
</c:if>

<c:if test="${error != null}">
<c:out value="${error}"/>
</c:if>

<%@ include file="../footer.jsp"%>
</body></html>
