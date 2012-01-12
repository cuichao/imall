<%@ include file="/WEB-INF/jsp/global.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
</head>
<body>
<%@ include file="../header.jsp"%>
<div id="main">
	<div class="container clearfix w">
		<iframe src="${imall_path}admin/left"  id="leftFrame" style="" name="leftFrame" scrolling="no" height="80%"></iframe>
		<iframe name="rightFrame" id="rightFrame" style="width:83%; "   height="80%"></iframe>
	</div>
</div>
<%@ include file="../footer.jsp"%>
</body>
</html>