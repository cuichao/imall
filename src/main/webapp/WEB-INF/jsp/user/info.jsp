<%@ include file="/WEB-INF/jsp/global.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
<%@ include file="../header.jsp"%>
<div id="main">
	<div class="container clearfix">
		<iframe src="${imall_path}user/navigator" frameborder="0"  id="leftFrame" name="leftFrame" scrolling="no" height="100%"></iframe>
		<iframe name="rightFrame" id="rightFrame" frameborder="0" height="100%"></iframe>
	</div>
</div>
<%@ include file="../footer.jsp"%>
</body>
</html>