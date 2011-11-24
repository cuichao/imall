<%@ include file="/WEB-INF/jsp/global.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<body>
	<%@ include file="../header.jsp"%>
	<div id="main">
		<div class="container clearfix">
			<!--aside begin-->
			<div class="aside">
				<a href="javascript:"><img src="${imall_path}images/aside.png"
					alt="图片说明" />
				</a>
			</div>
			<!--header end-->
			<!--content begin-->

			<div class="content fl">
				<!--product begin-->
				<div class="list product clearfix">
					<a class="list-img fl" href="javascript:"> <c:forEach
							items="${product.pdList}" var="detail" begin="0" end="0">
							<img src="${imall_path}${detail.picturePath}" alt="说明" title="说明"
								class="img_size" />

						</c:forEach> </a>
					<div class="list-content fl">
						<h3>
							<span class="product-price fr">￥${product.price}</span> <a
								href="javascript:">${product.name}</a><span>583,203 sold</span>
						</h3>
						<p>${product.shortDesc}</p>
						<a class="btn" href="javascript:">我要购买</a>
					</div>
				</div>
				<!--product end-->
				<div class="product-title">
					<span class="product-num fl">1</span>
					<h3>商品描述</h3>
				</div>
				<div class="product-detail">${product.description}</div>
				<div class="product-title">
					<span class="product-num fl">2</span>
					<h3>买家评论</h3>
				</div>
			</div>
			<!--content end-->
		</div>
	</div>

	<%@ include file="../footer.jsp"%>
</body>
</html>
