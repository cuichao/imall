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
		        	  div.append(data.accepter);
		        	  div.append(",");
		        	  div.append(data.address);
		        	  div.append(",");
		        	  div.append(data.mailcode);
					  div.append(",");
		        	  div.append(data.phone);
		        	  div.append(",");
		        	  div.append(data.telephone);
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
	  var selected_address_id = $("input[name='address_radios']:checked").attr("id");
	  if(selected_address_id == null)
	  {
		  alert("请选择一个地址!");
		  return false;
	  }
	  selected_address_id = selected_address_id.substring(8);//address_{id}
	  
	  var spanElement = $("#span_address_"+selected_address_id);
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
	modifySendtype = function()
	{
		$("#selected_sendtype").hide();
		$("#select_sendtype_div").show();
	};
	cancelSendtype = function()
	{
		$("#selected_sendtype").show();
		$("#select_sendtype_div").hide();
	};
	confirmSendtype = function()
	{
		//sendtype_{id}
		var sendtype = $("input[name='sendtype_radio']:checked").attr("id").substring(9);
		var timetype = $("#select_timetype").val();
		var spanElement = $("#span_st_"+sendtype);
		$("#send_type").val(sendtype);
		$("#time_type").val(timetype);
		$("#carriage").val(spanElement.attr("price"));
		
		$("#select_sendtype_div").hide();
		$("#selected_sendtype").empty();
		var time_desc = "周一到周五";
		if(timetype == 0)
		{
			time_desc = "无时间限制";
		}
		else if(timetype == 2)
		{
			time_desc = "周六日以及法定假日";
		}
		var st_desc = spanElement.html() + "\t" + time_desc;
		$("#selected_sendtype").html(st_desc);	
		$("#selected_sendtype").show();
		computerTotalPay();
	};
	modifyPayType = function()
	{
		$("#selected_paytype").hide();
		$("#select_paytype_div").show();
	};
	cancelPaytype = function()
	{
		$("#select_paytype_div").hide();
		$("#selected_paytype").show();
	};
	confirmPaytype = function()
	{
		var paytype = $("input[name='paytype_radio']:checked").attr("id").substring(8);
		$("#pay_type").val(paytype);
		$("#selected_paytype").html($("#span_pt_"+paytype).html());
		$("#select_paytype_div").hide();
		$("#selected_paytype").show();
		
	};
	computerTotalPay = function()
	{
		//计算一下运费及总费用
		   $("#carriage_cost").html($("#carriage").val());
		   var carriage = parseFloat($("#carriage_cost").html());
		   var product_money = parseFloat($("#totalmoney").html());
		   $("#total_to_pay").html(carriage + product_money);
	};
	sendOrder = function(orderId)
	{
		var url = basePath +"/back/trade/" + orderId +"/modifystatus?status=sending";
		$.ajax({ 
	          type : "get",  
	          url  :  url,
	          success : function(data){	
	        	  $("#td_" + orderId + "_order_status").html("送货中"); 
	        	  $("#" + orderId + "_send_button").attr("disabled",true);
	          }
	    });	 
		
	};
	selectBank2Pay = function(bankElement)
	{
		var bank = "bank=" + $(bankElement).attr("id");
		var order = $("#orderid").val();
		var url = basePath + "/trade/" + order + "/pay";
		$.ajax({ 
	          type : "POST",
	          url  :  url,
	          data :  bank,
	          success : function(data){
	        	 alert("haha");
	        	 $("#main").html(data);
	          }
	    });	 
	};
	checkOrder = function()
	{	
		var address = $("#address_id").val();		
		if(address == "")
		{
			alert("请您添加收货人信息！");
			return false;
		}
		return true;
	};
})(jQuery, window);