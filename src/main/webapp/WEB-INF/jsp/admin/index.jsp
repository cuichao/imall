<%@ include file="/WEB-INF/jsp/global.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<body>
<%@ include file="../header.jsp"%>
<div id="main">
	<div class="container clearfix w">
		<iframe src="${imall_path}admin/left" frameborder="0"  id="leftFrame" name="leftFrame" scrolling="no" height="100%"></iframe>
		<iframe name="rightFrame" id="rightFrame" frameborder="0" style=" width:83%;" height="100%"></iframe>
	</div>
</div>
<%@ include file="../footer.jsp"%>
</body>
</html>