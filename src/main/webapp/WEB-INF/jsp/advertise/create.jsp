<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="/WEB-INF/jsp/global.jsp"%>
<html>
<head>
<style type="text/css">
#createTable td {
	padding: 3px;
}
</style>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/advertise.js"></script>
</head>
<body>
	<div id="main" class="admin-main">
		<div class="clearfix" style="padding:0 12px; ">
			<div style="padding: 0 6px;">
				<h2 style="text-align: center; font-size: 25px; line-height: 38px;">添加广告</h2>
				<form method="post" action="createSubmit"
					enctype="multipart/form-data">
					<table id="createTable">
						<tr>
							<td>广告url</td>
							<td><input type="text" id="url1" name="url1">(如果是本平台商品，可直接输入商品ID)
							</td>
						</tr>
						<tr>
							<td>广告图片</td>
							<td><input type="file" id="pic1" name="pic1">
							</td>
						</tr>
						<tr>
						<td>广告类型</td>
						<td>
						  <select id="type1" name="type1">
						<option value="1">
						首页大	
						</option>
						<option value="2">
						首页小
						</option>
						<option value="3" selected>
						普通
						</option>
					</select>
						</td>
						</tr>
						<tr>
						<td><input type="button" value="增加记录"
							onclick="addPicTr();"> <input type="hidden"
							id="pic_index" name="pic_index" value="1"> <input
							type="submit" value="提交" onclick="return checkSubmit();">
						</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
</body>
</html>