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
<div class="container clearfix">	
	<!--header end-->
	<!--content begin-->
	<div class="content fl">
		<c:forEach items="${adList}" var="ad">
			<!--product begin-->
			<div class="list clearfix">
				<a class="fl" href="javascript:void(0);">									
						<img src="${imall_path}${ad.picturePath}" alt="说明"
							title="说明" class="img_normal_size"/>
				</a>
				<div class="list-content fl">
					<h3>	
					   <span class="product-price fr">
					   <c:if test="${ad.type == 1}">
					   	首页大
					   </c:if>
					   	<c:if test="${ad.type == 2}">
					   	首页小
					   </c:if>
					   	<c:if test="${ad.type == 3}">
					   	普通
					   </c:if>
					   </span> 
						<a>${ad.id}</a>
					</h3>
					<p>${ad.url}</p>
					<a class="btn" href="${imall_path}advertise/${ad.id}/delete">删除</a>
				</div>
			</div>
			<!--product end-->
		</c:forEach>
	</div>
	<!--content end-->
</div>
</div>
</body>
</html>