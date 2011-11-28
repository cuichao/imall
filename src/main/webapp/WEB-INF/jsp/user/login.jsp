<%@ include file="/WEB-INF/jsp/global.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/user.js"></script>
</head>
<body>
	<%@ include file="../header.jsp"%>


	<div id="main">
		<div class="container clearfix">
			<div class="lr clearfix">
				<a class="login-adv fl" href="javascript:"><img
					src="${imall_path}images/login-img.png" width="447px"
					height="451px" alt="图片说明" title="图片说明" /> </a>
				<div class="login-con fl">
					<c:if test="${not empty param.login_error}">
						<font color="red"> 登陆失败: <%
							Object obj = session
										.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
								String message = null;
								if (obj instanceof org.springframework.security.core.userdetails.UsernameNotFoundException) {
									message = "用户名不存在";
								} else if (obj instanceof org.springframework.security.authentication.BadCredentialsException) {
									message = "密码不正确";
								} else if (obj instanceof org.springframework.security.authentication.DisabledException) {
									message = "帐户未激活，请登陆邮箱激活帐号！";
								} else {
									message = "您的帐户存在问题，请联系管理员！";
								}
								out.print(message);
						%> </font>
					</c:if>
					<form action="${imall_path}j_spring_security_check" method="post">
						<div>
							<label for="user_name" class=""><fmt:message
									key="login.email" /> </label> <input type="text" name="j_username"
								id="j_username" class="required isHadUser"
								value='<c:if test="${not empty param.login_error}"><c:out value="${SPRING_SECURITY_LAST_USERNAME}" escapeXml="false"/></c:if>'>
						</div>
						<div>
							<label for="user_pw" class=""><fmt:message
									key="login.password" /> </label> <input type="password"
								name="j_password" id="j_password" class="required text">
						</div>
						<%
							String isNeedCAPTCHA = (String) request.getSession().getAttribute(
									"needCAPTCHA");
							if (isNeedCAPTCHA != null) {
						%>
						<div>
							<label for="passcode" class="">验证码</label> <input type="text"
								id="captcha" name="captcha" onfocus="getCaptchaOnfocus();"
								class="required checkCode"> <img id="captcha_img"
								class="none"></img></span><span><a href="javascript:void(0);"
								onclick="getCaptcha();">看不清？</a> </span>
						</div>
						<%
							}
						%>
						<div>
							<input type="submit" value="登陆"
								onclick="return validateAccount();" class="btn login-btn">
							<div>
								<a href="${imall_path}user/register">还没有帐号？</a>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="../footer.jsp"%>
</body>
</html>