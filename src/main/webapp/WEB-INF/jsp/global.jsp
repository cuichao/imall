<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="imall_path" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/"/>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.cookie.js"></script>
<script>
var basePath ="<%=request.getContextPath()%>";
</script>
<style>
       /* * { padding:0; margin: 0;Verdana, Geneva, sans-serif; font-size:12px}*/
        .subhead span{cursor:pointer}
        .sub-subhead span{cursor:pointer}
</style>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/reset.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/base.css" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/layout.css">

<!-- 
默认情况下，fmt会利用messageSource找到国际化文件，但当要被国际化的文件位于WEB-INF外面时（比如本项目中的login.jsp）,这时fmt翻译不生效，必须利用fmt:setBundle手工指定一下才生效
 -->
<fmt:setBundle basename="globalization.messages"/>
