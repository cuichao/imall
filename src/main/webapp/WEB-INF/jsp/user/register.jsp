<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="/WEB-INF/jsp/global.jsp"%>
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
					src="${imall_path}images/login-img.png" width="447px" height="451px" alt="图片说明"
					title="图片说明" />
				</a>
				<div id="registerTable" class="regist-con fl">
					<form action="registerSubmit" method="post">
						<div>
							<label for="user_name" class="">邮箱</label> <input type="text"
								id="email" name="email" class="required isHadUser" onblur="checkEmailRegister();"><span
								style="color: red;">*<span id="email_error"></span></span>
						</div>
						<div>
							<label for="user_name" class="">昵称</label> <input type="text"
								id="nickname" name="nickname"><span style="color: red;">*</span>
						</div>
						<div>
							<label for="user_pw" class="">密码</label> <input
								type="password" id="password" name="password"
								class="required text"><span style="color: red;">*</span>
						</div>
						<div>
							<label for="user_pw1" class="user_pw1">密码确认</label> <input
								type="password" id="password1" name="password1"
								class="required text"><span style="color: red;">*</span>
						</div>
						<div>

							<label for="city" class="">城市</label> <input type="text"
								id="city" name="city">
						</div>
						<div>
							<label for="city" class="">电话</label> <input type="text"
								id="phone" name="phone">
						</div>
						<div>

							<label for="passcode" class="">验证码</label> <input type="text"
								id="captcha" name="captcha" onfocus="getCaptchaOnfocus();"
								class="required checkCode"> <span><img
								id="captcha_img" class="none"></img> </span><span><a
								href="javascript:void(0);" onclick="getCaptcha();">看不清？</a> </span>

						</div>
						<div>
							<input type="submit" value="注册" onclick="return registerCheck();"
								class="btn login-btn">
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<%@ include file="../footer.jsp"%>
</body>
</html>