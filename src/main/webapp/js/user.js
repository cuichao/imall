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
	deleteAddress = function(addressId)
	{
		if(!confirm("您确定要删除此地址吗？"))
		{
			return false;
		}
		$.ajax({ 
	          type : "get",  
	          url  :  basePath +"/user/address/" + addressId +"/delete",
	          success : function(data){	        	
	        	  if(data == 0)
	        	  {
	        		  $("#div_address_"+addressId).remove();        
	        		  
	        	  }  
	        	  else
	        	  {
	        		  alert("您没权限执行此操作");
	        	  }
	        	  
	        		  
	          }
	    });	
	};
	saveAddress = function()
	{
		var options = {
				  dataType: 'json',
		          success: function(data){
		        	  var div=$("<div id='div_address_"+ data.id + "'></div>");
		        	  var ul = $("<ul></ul>");
		        	  var li= $("<li></li>");
		        	  li.append("姓名:");
		        	  li.append("<span id='span_accepter_" + data.id + "'>" + data.accepter +"</span>");
		        	  ul.append(li);
		        	  li= $("<li></li>");
		        	  li.append("地址:");
		        	  li.append("<span id='span_address_" + data.id + "'>" + data.address +"</span>");
		        	  ul.append(li);
		        	  li= $("<li></li>");
		        	  li.append("邮编:");
		        	  li.append("<span id='span_mailcode_" + data.id + "'>" + data.mailcode +"</span>");
		        	  ul.append(li);
		        	  li= $("<li></li>");
		        	  li.append("手机:");
		        	  li.append("<span id='span_phone_" + data.id + "'>" + data.phone +"</span>");
		        	  ul.append(li);
		        	  li= $("<li></li>");
		        	  li.append("固话:");		        	  
		        	  li.append("<span id='span_telephone_" + data.id + "'>" + data.telephone +"</span>");
		        	  ul.append(li);
		        	  div.append(ul);
		        	  var button_div = $("<div id='button_div_"+data.id + "'></div>");
		        	  button_div.append("<input type='button' value='修改' onclick='modifyAddress(" + data.id + ")'>");
		        	  button_div.append("<input type='button' value='删除' onclick='deleteAddress(" + data.id + ");'>");
		        	  div.append(button_div);
		        	  $("#old_address_div").append(div);
		        	  emptyAddressText();
		        	  
		          }	   	  
		      };
	    $("#addressForm").ajaxSubmit(options);
	};
	modifyAddress = function(addressId)
	{
		$("#button_div_"+addressId).hide();
		var action= basePath + "/user/address/" + addressId+"/update";
		var formid = "modifyAddressForm_"+addressId;
		var modify_div = $("<div id='modify_"+ addressId + "'></div>");
		modify_div.append("<h3>修改收货地址：</h3>");
		var form = $('<form id="'+ formid + '" action="' + action +'" method="POST"></form>');
		var ul = $("<ul></ul>");
		ul.append("<li><input type='hidden' name='id' value='" + addressId + "'></li>");
		var accepter = $("#span_accepter_"+addressId).html();
		ul.append("<li>姓名:<input type='text' name='accepter' value='" + accepter + "'></li>");
		var address = $("#span_address_"+addressId).html();
		ul.append("<li>地址:<input type='text' name='address' value='" + address + "'></li>");
		var mailcode = $("#span_mailcode_"+addressId).html();
		ul.append("<li>邮编:<input type='text' name='mailcode' value='" + mailcode + "'></li>");
		var phone = $("#span_phone_"+addressId).html();
		ul.append("<li>手机:<input type='text' name='phone' value='" + phone + "'></li>");
		var telephone = $("#span_telephone_"+addressId).html();
		ul.append("<li>固话:<input type='text' name='telephone' value='" + telephone + "'></li>");
		ul.append("<div class='address-btn'><input class='btn' type='button' value='修改' onclick='modifyAddressSubmit(" + addressId +");'>&nbsp;&nbsp;<input class='btn' type='button' value='取消' onclick='cancelModifyAddress(" + addressId +");'></div>");
		form.append(ul);
		modify_div.append(form);
		
		$("#div_address_"+addressId).append(modify_div);
	};
	emptyAddressText = function()
	{
		$("#accepter").val("");
		$("#address").val("");
		$("#mailcode").val("");
		$("#phone").val("");
		$("#telephone").val("");	
	};
	modifyAddressSubmit = function(addressId)
	{
		var formid = "modifyAddressForm_"+addressId;
		var options = {
				  dataType: 'json',
		          success: function(data){
		        	  var div=$("#div_address_"+addressId);
		        	  div.empty();
		        	  var ul = $("<ul></ul>");
		        	  var li= $("<li></li>");
		        	  li.append("姓名:");
		        	  li.append("<span id='span_accepter_" + data.id + "'>" + data.accepter +"</span>");
		        	  ul.append(li);
		        	  li= $("<li></li>");
		        	  li.append("地址:");
		        	  li.append("<span id='span_address_" + data.id + "'>" + data.address +"</span>");
		        	  ul.append(li);
		        	  li= $("<li></li>");
		        	  li.append("邮编:");
		        	  li.append("<span id='span_mailcode_" + data.id + "'>" + data.mailcode +"</span>");
		        	  ul.append(li);
		        	  li= $("<li></li>");
		        	  li.append("手机:");
		        	  li.append("<span id='span_phone_" + data.id + "'>" + data.phone +"</span>");
		        	  ul.append(li);
		        	  li= $("<li></li>");
		        	  li.append("固话:");		        	  
		        	  li.append("<span id='span_telephone_" + data.id + "'>" + data.telephone +"</span>");
		        	  ul.append(li);
		        	  div.append(ul);
		        	  var button_div = $("<div class='address-btn' id='button_div_"+data.id + " '></div>");
		        	  button_div.append("<input class='btn' type='button' value='修改' onclick='modifyAddress(" + data.id + ")'>");
		        	  button_div.append(" <input class='btn' type='button' value='删除' onclick='deleteAddress(" + data.id + ");'>");
		        	  div.append(button_div);
		        	  $("#old_address_div").append(div);  
		          }	   	  
		      };
	    $("#"+ formid).ajaxSubmit(options);
		
	};
	cancelModifyAddress = function(addressId)
	{
		$("#modify_"+addressId).remove();
		$("#button_div_"+addressId).show();
	};
	
	
	
	
	
	
})(jQuery, window);