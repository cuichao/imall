<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.eleven7.imall.security.*"%>
<%
	String nickname = SpringSecurityUtils.getCurrentNickName();
	boolean bAdmin = SpringSecurityUtils.hasAnyRole("ADMIN_USER");
%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/header.js"></script>
<!--header begin-->
<div class="login">
	<div class="container">
	    <% if(nickname != null){
	    %>
	       	您好，<a href="${imall_path}user"><%=nickname%></a>
	       	<a href="${imall_path}j_spring_security_logout">登出</a>
	       	<input type="hidden" name="cur_user" id="cur_user" value="<%=nickname%>">
	     <%
	     if(bAdmin)
	     {
	     %>
	     <a href="${imall_path}admin/index">后台管理</a>
	     <%
	     }
	     %>
	    <%
	    }else{
	    %>
	       <a href="${imall_path}user/login">登陆</a>
	       <input type="hidden" name="cur_user" id="cur_user" value="">
	       <a href="${imall_path}user/register">注册</a>
	    <%
	    }
	    %>
	    <a href="${imall_path}mycar">购物车</a>
		<a href="">帮助</a>
		
	</div>
</div>
<div id="header">
	<div class="container"> 
		<div class="header-nav"> 
			<div class="search">
				<input type="text" id="query_text" value="${query}" onkeydown="enter2Search(event);"/><a href='javascript:searchProduct();' class="search-btn"></a>
			</div>
			<!--  <a href="${imall_path}">首页</a><a href="${imall_path}product/list">产品</a><a href="${imall_path}about.jsp">关于</a>
			-->
		</div>
		<a href="${imall_path}" class="logo"><img src="${imall_path}images/logo.png" alt="opena" title="opena" /></a>
	</div>
</div>
<!--header end-->