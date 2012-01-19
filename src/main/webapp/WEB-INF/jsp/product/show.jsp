<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="/WEB-INF/jsp/global.jsp"%>
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
			<a id="advertise_a" href="javascript:void(0);"><img id="advertise_img" class="aside_size"
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
								href="javascript:">${product.name}</a>
						<c:if test="${product_count == 0}">
							<span style="color:red;">缺货</span>
						</c:if>
						</h3>
						<p>${product.shortDesc}</p>
						<c:set  var="detail_size" value="${fn:length(product.pdList)}"/>
						<div id="selectId">
							<div class="product-color">
							<c:if test="${detail_size > 1}">
							请选择颜色：<span id="detail_desc" class="red"></span>
								<c:forEach items="${product.pdList}" var="detail">
									<img class="product-show-tinyimg" src="${imall_path}${detail.picturePath}" width="25" height="25" alt="说明"
										title="说明" style="vertical-align:middle" id="${detail.id}"
										onclick="selectProductDetail('${detail.id}');" />
									<input type="hidden" id="count_${detail.id}" value="${detail.count}">
								</c:forEach>
								<input type="hidden" name="select_pd" id="select_pd" value="" />
							</c:if>
							<c:if test="${detail_size == 1}">
								<c:forEach items="${product.pdList}" var="detail" begin="0"
									end="0">
									<input type="hidden" name="select_pd" id="select_pd"
										value="${detail.id}" />
									<input type="hidden" id="count_${detail.id}" value="${detail.count}">
								</c:forEach>
							</c:if>  
							</div>
							<div>
							请选择数量：
							<c:if test="${product_count == 0}">
							<input type="text" id="count" name="count" value="1" size="3" disabled/>
							<input class="btn disabled" type="button" value="我要购买" disabled>
                            </c:if>
                            <c:if test="${product_count != 0}">
                             <input type="text" id="count" name="count" value="1" size="3"/> 
                             <a class="btn" href="${imall_path}mycar" onclick="return addProduct2Cookie();">我要购买</a>
							</c:if>
							</div>
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
				<div class="product-comment">
					<div class="product-comment-wrap">
					<c:forEach items="${commentList}" var="comment">
					<div class="product-comment-list">
					<div class="comment-content"> ${comment.comment}</div>
					<div class="product-comment-user">${comment.user.nickname} ,发表于 <fmt:formatDate pattern= "yyyy-MM-dd HH:mm" value="${comment.createtime}"/></div>
					</div>
					</c:forEach>
					
					</div>
					
					<div id="new_comment_div"></div>
					<a href="javascript:" class="btn" id="product-coment-btn" onclick="newComment('${product.id}');">写评论</a>	
				</div>		
				
			</div>
			<!--content end-->
		</div>
	</div>

	<%@ include file="../footer.jsp"%>
</body>
<script>
var adUrl = new Array();
var adPic = new Array();
<c:set var="idx" value="0"/>
<c:forEach items="${advertiseList}" var="ad">
	adUrl[${idx}]="${ad.url}";
	adPic[${idx}]="${ad.picturePath}";
	<c:set var="idx" value="${idx + 1}"/>
</c:forEach>
var index = 0;
var count = adUrl.length;
if(count == 0)
{
	$("#advertise_img").attr("src","${imall_path}images/aside.png");
}
else
{
	index ++;
	$("#advertise_img").attr("src",basePath + "/" + adPic[0]);
	$("#advertise_a").attr("href",adUrl[0]);
	setInterval("changeAdvertisement()",3000);
		
};
function changeAdvertisement()
{
	if(count > 0)
	{
		if(index >= count)
		{
			index = 0;
		}
		$("#advertise_img").attr("src",basePath + "/" + adPic[index]);
		$("#advertise_a").attr("href",adUrl[index]);
		index ++;
		
	}
}
$(".product-show-tinyimg").click(function(){
	$(".product-show-tinyimg").removeClass("on");
	$(this).addClass("on");
	
});
</script>
</html>
