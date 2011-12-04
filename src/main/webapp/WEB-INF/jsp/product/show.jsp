<%@ include file="/WEB-INF/jsp/global.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/product.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/user.js"></script>
</head>
<body>
	<%@ include file="../header.jsp"%>
	<div id="main">
		<div class="container clearfix">
			<!--aside begin-->
			<div class="aside">
				<a href="javascript:"><img src="${imall_path}images/aside.png"
					alt="图片说明" /> </a>
			</div>
			<!--header end-->
			<!--content begin-->

			<div class="content fl">
				<!--product begin-->
				<div class="list product clearfix">
					<a class="list-img fl" href="javascript:"> <c:forEach
							items="${product.pdList}" var="detail" begin="0" end="0">
							<img src="${imall_path}${detail.picturePath}" alt="说明" title="说明"
								class="img_normal_size" />

						</c:forEach> </a>
					<div class="list-content fl">
						<h3>
							<span class="product-price fr">￥${product.price}</span> <a
								href="javascript:">${product.name}</a><span>583,203 sold</span>
						</h3>
						<p>${product.shortDesc}</p>
						<c:set  var="detail_size" value="${fn:length(product.pdList)}"/>
						<div id="selectId">
							<c:if test="${detail_size > 1}">
							请选择颜色：
								<c:forEach items="${product.pdList}" var="detail">
									<img src="${imall_path}${detail.picturePath}" alt="说明"
										title="说明" class="img_small_size" id="${detail.id}"
										onclick="selectProductDetail('${detail.id}');" />
								</c:forEach>
								<input type="hidden" name="select_pd" id="select_pd" value="" />
							</c:if>
							<c:if test="${detail_size == 1}">
								<c:forEach items="${product.pdList}" var="detail" begin="0"
									end="0">
									<input type="hidden" name="select_pd" id="select_pd"
										value="${detail.id}" />
								</c:forEach>
							</c:if>

							请选择数量： <input type="text" id="count" name="count" value="1" /> <a
								class="btn" href="${imall_path}mycar"
								onclick="return addProduct2Cookie();">我要购买</a>
						</div>
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
				<div>
					<input type="button" value="写评论" onclick="newCommnet('${product.id}');">
					<div id="new_comment_div"></div>
				</div>		
				<div>
				<c:forEach items="${commentList}" var="comment">
				<div> ${comment.comment}</div>
				<div>by  ${comment.user.nickname} ,at  ${comment.createtime}</div>
				</c:forEach>
				</div>	
			</div>
			<!--content end-->
		</div>
	</div>

	<%@ include file="../footer.jsp"%>
</body>
</html>
