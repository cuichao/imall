<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="/WEB-INF/jsp/global.jsp"%>
<html>
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/product.js"></script>
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
							<a class="btn" href="${imall_path}product/${product.id}/show">详情</a>
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
</script>
</html>
