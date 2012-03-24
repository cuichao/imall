<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/jsp/global.jsp"%>
</head>
<style type="text/css">
	html,body{ height:100%; }
</style>
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