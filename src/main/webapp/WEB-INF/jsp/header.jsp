<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.eleven7.imall.security.*"%>
<%
 String nickname = SpringSecurityUtils.getCurrentNickName();
%>
<!--header begin-->
<div class="login"> 
	<div class="container">
	    <% if(nickname != null){
	    %>
	       	您好，<a href="${imall_path}user/settings"><%=nickname%></a>
	       	<a href="${imall_path}j_spring_security_logout">logout</a>
	    <%
	    }else{
	    %>
	       <a href="${imall_path}user/login">Login</a>
	    <%
	    }
	    %>
		<a href="">Help</a>
		
	</div>
</div>
<div id="header">
	<div class="container"> 
		<div class="header-nav"> 
			<div class="search">
				<input type="text" /><a href="#" class="search-btn"></a>
			</div>
			<a href="#">Index</a><a href="#">Products</a><a href="#">About</a>
		</div>
		<a href="#" class="logo"><img src="${imall_path}images/logo.png" alt="opena" title="opena" /></a>
	</div>
</div>
<!--header end-->