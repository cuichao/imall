(function($, window, undefined) {
	
	$(document).ready(function() {
	});
	
	registerCheck = function(){
		 //check email
		if(!checkEmail())
		{
		  return false;	
		}
		var nickname = $.trim($("#nickname").val());
		if(nickname == "")
		{
			alert("昵称不能为空！");
			return false;
		}
		var p = $.trim($("#password").val());
		var p1 = $.trim($("#password1").val());
		if(p.length < 6 )
		{
			alert("密码最短为6位！");
			return false;
		}
		if(p != p1)
		{
			 alert("两次密码不一致!");
			 return false;
		}
		
//		 if(!checkCaptcha())
//		 {
//			 return false;
//		 }
		 return true;
		 
	};
	
	checkSubmit = function()
	{
		
		var name = $.trim($("#name").val());
		var price = $.trim($("#price").val());
		if(name =="")
		{
			alert("name is not empty");
			return false;
		}
		if(price == "")
		{
			alert("price is not empty");
			return false;
		}
		
		return true;	
	};
	
	getCaptcha = function()
	{  
		var codeUrl = basePath + "/captcha?t="+new Date().getTime();
		var img = $("#captcha_img");
		if(img.hasClass("none"))
		{
			img.removeClass("none");
		}
		img.attr("src",codeUrl);
	};
	getCaptchaOnfocus = function()
	{  
		var img = $("#captcha_img");
		if(img.hasClass("none"))
		{
			getCaptcha();
		}
		
	};
	
	checkCaptcha = function()
	{  
		var code = $.trim($("#captcha").val());
		if(code == "")
	    {	
			alert("请填写验证码！");
			return false;
	    }
		
		var checkUrl = basePath + "/captcha/check?captcha="+code;
		var result;
		$.ajax({ 
	          type : "get",  
	          url  :  checkUrl,
	          async : false,
	          success : function(data){	        	
	        	  if(data == "true")
	        	  {
	        		  result = true;
	        	  }  
	        	  else
	        	  {
	        		  alert("验证码不对！");
	        		  result = false;
	        	  }
	        	  
	        		  
	          }
	    });	 
		return result;
	};
	
	checkEmail = function(){
		var email = $.trim($("#email").val());
		if(email == "")
		{
			alert("邮箱不能为空！");
			return false;
		}
		var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		if(!myreg.test(email))
		{
			alert("请填入正确的邮箱！");
			$("#email").focus();
		    return false;
		}
		return true;
	};
	validateAccount = function()
	{
		var username = $.trim($("#j_username").val());
		var password = $.trim($("#j_password").val());
		if(username == "")
		{
			alert("邮箱不能为空！");
			return false;
		}
		if(password == "")
		{
			alert("密码不能为空！");
			return false;
		}
		var captcha = $("#captcha").val();
		if(captcha != undefined)
		{
			if(!checkCaptcha())
			 {
				 return false;
			 }
		}
		 return true;
		
	};
	
	
})(jQuery, window);