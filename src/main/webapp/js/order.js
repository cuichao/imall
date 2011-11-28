(function($, window, undefined) {
	
	modifyAddress = function()
	{
		$("#selected_address").hide();
		var div = $("#selectAddressDiv");
		div.load(basePath+"/user/address/new");
	};
	saveAddress = function()
	{
		var options = {
				  dataType: 'json',
		          success: function(data){
		        	  $("#selected_address").empty();
		        	  var div=$("<div></div>");
		        	  div.append("收货人:" + data.accepter);
		        	  $("#selected_address").append(div);
		        	  div=$("<div></div>");
		        	  div.append("地址:" + data.address+" 邮编：" +data.mailcode);
		        	  $("#selected_address").append(div);
		        	  div=$("<div></div>");
		        	  div.append("手机:" + data.phone + " 固话：" +data.telephone);
		        	  $("#selected_address").append(div);		        	  
		        	  $("#selected_address").append("<input type=hidden id=address_id name=address_id value='" + data.id + "'>");
		        	  $("#selectAddressDiv").empty();
		        	  $("#selected_address").show();
		        	  $("#a_address").html("修改");
		          }	   	  
		      };
	    $("#addressForm").ajaxSubmit(options);
	};
	confirmAddress = function()
	{
	  var selected_address_id = $("input[name='radios']:checked").attr("id");
	  if(selected_address_id == null)
	  {
		  alert("请选择一个地址!");
		  return false;
	  }
	  var spanElement = $("#span_"+selected_address_id);
	  $("#selected_address").empty();
  	  $("#selected_address").append(spanElement.html());	  
  	  $("#selected_address").append("<input type=hidden id=address_id name=address_id value='" +selected_address_id + "'>");
  	  $("#selectAddressDiv").empty();
  	  $("#selected_address").show();
  	  $("#a_address").html("修改");
	};
	cancelAddress = function()
	{
		$("#selectAddressDiv").empty();
		$("#selected_address").show();
	};
	
	
})(jQuery, window);