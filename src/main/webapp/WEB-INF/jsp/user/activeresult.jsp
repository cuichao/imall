<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="/WEB-INF/jsp/global.jsp"%>
<html><body>
<%@ include file="../header.jsp"%>

<c:if test="${result == '0'}">
恭喜你，你激活成功啦！
<a href="${imall_path}user/login">登陆吧</a>
</c:if>

<c:if test="${result != '0'}">
<c:out value="${result}"/>
</c:if>

<%@ include file="../footer.jsp"%>
</body></html>
