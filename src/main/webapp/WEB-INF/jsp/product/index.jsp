<%@ include file="/WEB-INF/jsp/global.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
</head>
<body>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<div id="main">
		<div class="container clearfix"> 
			<div class="banner"> 
				<a id="advertise_big_a" href="javascript:void(0);"><img id="advertise_big_img" width="924px" height="456px" alt="图片说明" title="图片说明" /></a>
			</div>
			<a id="advertise_small_a" class="adv" href="javascript:void(0);">
				<img id="advertise_small_img" width="307px" height="198px" alt="图片说明" title="图片说明" />
			</a>
			<div class=""> 
			</div>
		</div>
	</div>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>
</body>
<script>
var adBigUrl = new Array();
var adBigPic = new Array();
<c:set var="idx" value="0"/>
<c:forEach items="${advertiseBigList}" var="ad">
	adBigUrl[${idx}]="${ad.url}";
	adBigPic[${idx}]="${ad.picturePath}";
	<c:set var="idx" value="${idx + 1}"/>
</c:forEach>
var index = 0;
var count = adBigUrl.length;
if(count == 0)
{
	$("#advertise_big_img").attr("src","${imall_path}images/banner.png");
}
else
{
	index ++;
	$("#advertise_big_img").attr("src",basePath + "/" + adBigPic[0]);
	$("#advertise_big_a").attr("href",adBigUrl[0]);
	setInterval("changeBigAdvertisement()",3000);
		
};

function changeBigAdvertisement()
{
	if(count > 0)
	{
		if(index >= count)
		{
			index = 0;
		}
		$("#advertise_big_img").attr("src",basePath + "/" + adBigPic[index]);
		$("#advertise_big_a").attr("href",adBigUrl[index]);
		index ++;
		
	}
}

var adSmallUrl = new Array();
var adSmallPic = new Array();
<c:set var="cnt" value="0"/>
<c:forEach items="${advertiseSmallList}" var="ad">
	adSmallUrl[${cnt}]="${ad.url}";
	adSmallPic[${cnt}]="${ad.picturePath}";
	<c:set var="cnt" value="${cnt + 1}"/>
</c:forEach>
var small_index = 0;
var small_count = adSmallUrl.length;
if(small_count == 0)
{
	$("#advertise_small_img").attr("src","${imall_path}images/adv.png");
}
else
{
	small_index ++;
	$("#advertise_small_img").attr("src",basePath + "/" + adSmallPic[0]);
	$("#advertise_small_a").attr("href",adSmallUrl[0]);
	setInterval("changeSmallAdvertisement()",3000);
		
};
function changeSmallAdvertisement()
{
	if(small_count > 0)
	{
		if(small_index >= small_count)
		{
			small_index = 0;
		}
		$("#advertise_small_img").attr("src",basePath + "/" + adSmallPic[small_index]);
		$("#advertise_small_a").attr("href",adSmallUrl[small_index]);
		small_index ++;
		
	}
}
</script>
</html>