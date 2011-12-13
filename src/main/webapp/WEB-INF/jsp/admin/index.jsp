<%@ include file="/WEB-INF/jsp/global.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
</head>
<body>
<%@ include file="../header.jsp"%>
<div>
<iframe src="${imall_path}admin/left"  id="leftFrame" name="leftFrame" width="19%" height="80%"></iframe>
<iframe name="rightFrame" id="rightFrame" width="79%" height="80%"></iframe>
</div>
<%@ include file="../footer.jsp"%>
</body>
</html>