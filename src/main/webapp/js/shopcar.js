(function($, window, undefined) {
	
	$(document).ready(function() {		
		computeTotalModey();
	});
	
	computeTotalModey = function()
	{
		var totalmoney = $("#totalmoney").val();
		if(totalmoney != undefined)
		{	
		  var spans = $("#shopcarTable").find("span");
		  var total = 0;
		  $.each(spans, function(index, span) {
			  var name = $(span).attr("name");		  
			  if(name == "price")
			  {					  
				  var price = parseFloat($(span).attr("value"));					  
				  var price_id = $(span).attr("id");
				  var countId = price_id.substring(0,price_id.indexOf("_price"))+"_count";				  
				  var count = parseInt($("#"+countId).val());				 
				  total += price*count;
			  }
			  
		  });
		  $("#totalmoney").html(total);
		}
	};
	addCount = function(ElementId)
	{
		var count = parseInt($("#"+ElementId).val()) + 1;
		$("#"+ElementId).val(count);
		computeTotalModey();
	};
	subCount = function(ElementId)
	{
		var count = parseInt($("#"+ElementId).val())-1;
		if(count <1)
		{
			count = 1;
		}
		$("#"+ElementId).val(count);
		computeTotalModey();
	};
	setCount = function(ElementId)
	{
		var count = parseInt($("#"+ElementId).val());
		if(isNaN(count) || count < 1)
		{	
			count = 1;	
		}
		$("#"+ElementId).val(count);
		computeTotalModey();
	};
	saveProduct2Cookie = function()
	{
		//pd_id=count;pd_id=count;....
		var toSaveInfo = "";
		var spans = $("#shopcarTable").find("span");
		$.each(spans, function(index, span) {
			  var name = $(span).attr("name");		  
			  if(name == "price")
			  {					  
				  var price = parseFloat($(span).attr("value"));					  
				  var price_id = $(span).attr("id");
				  var pd_id = price_id.substring(0,price_id.indexOf("_price"));
				  var countId = pd_id + "_count";				  
				  var count = parseInt($("#"+countId).val());				 
				  toSaveInfo += pd_id+"="+count+";";
			  }
			  
		  });
		$.cookie("imall_pd_list", toSaveInfo, { path: '/', expires: 30 });
		this.location.href = basePath+"/showorder";
	};
	
	
	
})(jQuery, window);