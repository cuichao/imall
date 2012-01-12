<%@ include file="/WEB-INF/jsp/global.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/create_product.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/product.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/ckeditor/ckeditor.js"></script>
<style type="text/css">
#createTable td{ padding:1px; }
</style>
</head>
<body>
	<div id="main" style="background:#fff; min-height:100%; ">
			<div style="padding:0 6px;">
				<h2 style="text-align:center; font-size:25px; line-height:38px; ">创建商品</h2>
				<form method="post" action="createSubmit" enctype="multipart/form-data">
				<table id="createTable" style="width:99%; ">
				<tr>
				<td>名称</td>
				<td><input type="text" id="name" name="name"></td>
				</tr>
				<tr>
				<td>简短描述</td>
				<td><textarea id="short_desc" name="short_desc"></textarea></td>
				</tr>
				<tr>
				<td>商品故事</td>
				<td><textarea id="desc" name="desc"></textarea></td>
				</tr>
				<tr>
				<td>价格</td>
				<td><input type="text" id="price" name="price"></td>
				</tr>
				<tr>
				<td colspan="2" style="text-align:right; ">
				<input type="button" value="增加商品详情" onclick="addPicTr();">
				<input type="hidden" id="pic_index" name="pic_index" value="0">
				<input type="submit" value="创建" onclick="return checkSubmit();">
				</td>
				</tr>
				</table>
				</form>
			</div>
	</div>
</body>
</html>