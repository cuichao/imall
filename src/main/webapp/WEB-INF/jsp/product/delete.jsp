<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="/WEB-INF/jsp/global.jsp"%>
<html>
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/product.js"></script>
</head>
<body>
	<div id="main" class="admin-main">
		<div class="container clearfix">	
			<!--header end-->
			<!--content begin-->
			<div class="content fl">
				<c:forEach items="${productList}" var="product">
					<!--product begin-->
					<div class="list clearfix">
						<a class="fl" href="${imall_path}product/${product.id}/show">
							<c:forEach items="${product.pdList}" var="detail" begin="0" end="0">							
								<img src="${imall_path}${detail.picturePath}" alt="说明"
									title="说明" class="img_normal_size"/>
							</c:forEach> 
						</a>
						<div class="list-content fl">
							<h3>
							    <span class="product-price fr">￥${product.price}</span> 
								<a href="${imall_path}product/${product.id}/show">${product.name}</a>
							</h3>
							<p>${product.shortDesc}</p>
							<a class="btn" href="${imall_path}product/${product.id}/delete">删除</a>
						</div>
					</div>
					<!--product end-->
				</c:forEach>
				<div class="page fr">
				 <%@ include file="../page.jsp" %>
				</div>
			</div>
			<!--content end-->
		</div>
	</div>
<input type="hidden" name="page" id="page" value="${page.page}" />
</body>
</html>
