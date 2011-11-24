<%@ include file="/WEB-INF/jsp/global.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/user.js"></script>
</head>
<body>
<form action="registerSubmit" method="post">
<table id="registerTable">
<tr>
<td>邮箱</td>
<td><input type="text" id="email" name="email"><span style="color: red;">*</span></td>
</tr>
<tr>
<td>昵称</td>
<td><input type="text" id="nickname" name="nickname"><span style="color: red;">*</span></td>
</tr>
<tr>
<td>密码</td>
<td><input type="password" id="password" name="password"><span style="color: red;">*</span></td>
</tr>
<tr>
<td>再次输入密码</td>
<td><input type="password" id="password1" name="password1"><span style="color: red;">*</span></td>
</tr>
<tr>
<td>城市</td>
<td><input type="text" id="city" name="city"></td>
</tr>
<tr>
<td>电话</td>
<td><input type="text" id="phone" name="phone"></td>
</tr>
<tr>
<td>验证码</td>
<td><input type="text" id="captcha" name="captcha" onfocus="getCaptchaOnfocus();">
<span><img id="captcha_img" class="none"></img></span><span><a href="javascript:void(0);" onclick="getCaptcha();">看不清？</a></span></td>
</tr>
<tr>
<td></td>
<td><input type="submit" value="注册" onclick="return registerCheck();"></td>
</tr>
</table>
</form>
</body>
</html>