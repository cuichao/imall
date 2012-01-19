<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/global.jsp"%>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
</head>
<body>
	<div id="main">
		<div class="container clearfix">
			<div id="banner" class="banner">
				<ul>
					<c:set var="big_size" value="${fn:length(advertiseBigList)}" />				
					<c:if test="${big_size > 1}">
						<c:set var="idx" value="0" />
						<c:forEach items="${advertiseBigList}" var="ad">
							<c:if test="${idx == 0}">
								<li class="on">1</li>
							</c:if>
							<c:if test="${idx != 0}">
								<li>${idx + 1}</li>
							</c:if>
							<c:set var="idx" value="${idx + 1}" />
						</c:forEach>
					</c:if>
				</ul>
				<div id="banner_list">
					<c:if test="${big_size == 0}">
						<a id="advertise_big_a" href="javascript:void(0);"><img
							src="${imall_path}images/banner.png" width="924px" height="456px"
							des="description" alt="图片说明" title="图片说明" />
						</a>
					</c:if>
					<c:if test="${big_size > 0}">
						<c:set var="idx" value="0" />
						<c:forEach items="${advertiseBigList}" var="ad">
							<c:if test="${idx == 0}">
								<a id="advertise_big_a" href="${ad.url}"><img
									src="${imall_path}${ad.picturePath}" width="924px"
									height="456px" des="description" alt="图片说明" title="图片说明" />
								</a>
							</c:if>
							<c:if test="${idx != 0}">
								<a id="advertise_big_a" class="hide" href="${ad.url }"><img
									src="${imall_path}${ad.picturePath}" width="924px"
									height="456px" des="description" alt="图片说明" title="图片说明" />
								</a>
							</c:if>
							<c:set var="idx" value="${idx + 1}" />
						</c:forEach>
					</c:if>
				</div>
			</div>
			<div class="banner"></div>
			<a id="advertise_small_a" class="adv" href="javascript:void(0);">
				<img id="advertise_small_img" width="573px" height="197px"
				alt="图片说明" title="图片说明" /> </a>
			<div class="index-infolist">
				<h2>新闻-News</h2>
				<ul>				
				</ul>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/jsp/footer.jsp"%>
</body>
<script type="text/javascript">
//图片轮播功能
(function(){
	$.bSlide = {
		init : function(slides, tabs, slidesInfo){
			if(!slides || !tabs){
				return;
			}
			$.bSlide.slides = slides;                        //轮播内容
			$.bSlide.tabs = tabs;							   //按钮
			$.bSlide.slidesInfo = slidesInfo;            //文字说明
			$.bSlide.length = $.bSlide.slides.length;
			$.bSlide.current = 0;
			$.bSlide.slidesInfo.html($.bSlide.slides.eq($.bSlide.current).find("img").attr('des'));
			$.bSlide.tabs.each(function(index){
				$(this).click(function(){
					if($.bSlide.current == index){ return; }
					$.bSlide.hide($.bSlide.current);
					$.bSlide.show(index);
					$.bSlide.current = index;
				})
			});
			if($.bSlide.length > 1)
			{
				$.bSlide.t = setInterval($.bSlide.showAuto, 5000);
				$("#banner").hover(
					function(){
						clearInterval($.bSlide.t)
					}, 
					function(){
						$.bSlide.t = setInterval($.bSlide.showAuto, 5000);
				});
			}				
		},
		show : function(index){
			$.bSlide.slides.eq(index).fadeIn(1000);
			$.bSlide.slidesInfo.html($.bSlide.slides.eq(index).find("img").attr('des'));
			$.bSlide.tabs.eq(index).toggleClass("on");
		},
		hide : function(index){
			$.bSlide.slides.eq(index).fadeOut(1000);
			$.bSlide.tabs.eq(index).toggleClass("on");
		},
		showAuto : function(){
			$.bSlide.hide($.bSlide.current);
			if($.bSlide.current >= $.bSlide.length - 1) {
				$.bSlide.current = 0; 
			}else{
				$.bSlide.current++;
			}
			$.bSlide.show($.bSlide.current);
		}
	}
	$(function(){
		$.bSlide.init($("#banner_list a"), $("#banner ul li"), $("#banner_info"));
	})
})()
</script>
<script>
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
	setInterval("changeSmallAdvertisement()",5000);
		
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