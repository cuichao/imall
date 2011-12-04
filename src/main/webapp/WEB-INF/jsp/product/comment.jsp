<%@ include file="/WEB-INF/jsp/global.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<form action="${imall_path}product/${productid}/savecomment" method="POST">
	<table>
		<tr>
			<td>内容：</td>
			<td><textarea id="comment" name="comment"></textarea>
			</td>
		</tr>
		<tr>
			<td><label for="passcode" class="">验证码</label></td>
			<td><input type="text" id="captcha" name="captcha"
				onfocus="getCaptchaOnfocus();" class="required checkCode"> <span><img
					id="captcha_img" class="none"></img> </span><span><a
					href="javascript:void(0);" onclick="getCaptcha();">看不清？</a> </span></td>
		</tr>
		<tr>
			<td></td>
			<td><input type="submit" value="发布"
				onclick="return checkSubmitComment();">
			</td>
		</tr>
	</table>
</form>