<%@ include file="/WEB-INF/jsp/global.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<body>
<%@ include file="../header.jsp"%>
<div>
<table>
<tr><td>邮箱</td><td>${user.email}</td></tr>
<tr><td>昵称</td><td>${user.nickname}</td></tr>
<tr><td>电话</td><td>${user.phone}</td></tr>
</table>
</div>
<%@ include file="../footer.jsp"%>
</body>
</html>