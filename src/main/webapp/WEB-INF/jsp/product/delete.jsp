<%@ include file="/WEB-INF/jsp/global.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/product.js"></script>
</head>
<body>
	<div id="main">
		<div class="container clearfix">
			<!--aside begin-->
			<div class="aside">
				<a id="advertise_a" href="javascript:void(0);"><img id="advertise_img" class="aside_size"
					alt="图片说明" /> </a>
			</div>
			<!--header end-->
			<!--content begin-->
			<div class="content fl">
				<c:forEach items="${productList}" var="product">
					<!--product begin-->
					<div class="list clearfix">
						<a class="list-img fl" href="${imall_path}product/${product.id}/show">
							<c:forEach items="${product.pdList}" var="detail" begin="0" end="0">							
								<img src="${imall_path}${detail.picturePath}" alt="说明"
									title="说明" class="img_normal_size" />
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
