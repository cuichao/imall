(function($, window, undefined) {
	
	searchProduct = function()
	{
		var query=$.trim($("#query_text").val());
		if(query != "")
		{
		   var url = basePath + "/product/list?query=" + query;	   
		   this.location.href = url;	
		}
	};
	enter2Search = function(e)
	{
		var key = window.event ? event.keyCode:e.which;		
		if (key == 13)//enter_key
		{
			searchProduct();
    	}
	};

})(jQuery, window);