<%@ include file="/WEB-INF/jsp/global.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/create_product.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/product.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/ckeditor/ckeditor.js"></script>
</head>
<h2>创建商品</h2>
<form method="post" action="createSubmit" enctype="multipart/form-data">
<table id="createTable">
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
</table>
<table>
<tr>
<td>
<input type="button" value="增加商品详情" onclick="addPicTr();">
<input type="hidden" id="pic_index" name="pic_index" value="0">
</td>
<td><input type="submit" value="创建" onclick="return checkSubmit();"></td>
</tr>
</table>
</form>
</html>