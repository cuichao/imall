<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="/WEB-INF/jsp/global.jsp"%>
<html><body>
<%@ include file="../header.jsp"%>
<div id="main">
	<c:if test="${result !=null}">
	<c:out value="${result}"/>
	</c:if>
	<c:if test="${error != null}">
	<c:out value="${error}"/>
	</c:if>
</div>
<%@ include file="../footer.jsp"%>
</body></html>
