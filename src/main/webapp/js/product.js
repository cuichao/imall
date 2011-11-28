(function($, window, undefined) {
	
	addPicTr = function(){
		 var index = parseInt($("#pic_index").val()) + 1;
		 var table = $("#createTable");
		 var tr = $('<tr></tr>');
         var td = $('<td></td>');
         td.html("颜色:<input type='text' id='color"+index+"' name='color"+index + "'>");
         tr.append(td);
         var td = $('<td></td>');
         td.html("图片:<input type='file' id='pic" +index +"' name='pic"+index +"'>");
         tr.append(td);
         table.append(tr);
         var td = $('<td></td>');
         td.html("数量:<input type='text' id='count" +index +"' name='count"+index +"'>");
         tr.append(td);
         table.append(tr);
         $("#pic_index").val(index);
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
	jumpPage = function(pageNo)
	{
		this.location.href = basePath +"/product/list?page="+pageNo;
	};
	addProduct2Cookie = function()
	{
		var count = $("#count").val();
		if(count == "")
		{
			count = 1;
		}
		var pd_id = $("#select_pd").val();
		if(pd_id == "")
		{
			alert("请选择颜色!");
			return false;
		}
		//pd_id=count;pd_id=count;....
		var saved_pd = $.cookie("imall_pd_list");
		if(saved_pd != null)
		{		
			var pdArray = saved_pd.split(";");
			saved_pd ="";
			var bFound = false;
			for(var i =0;i<pdArray.length;i++)
			{
				if(pdArray[i] == "")
					continue;
				var item = pdArray[i].split("=");			
				if(pd_id == item[0])
				{			
					bFound =true;
					var cnt = parseInt(count) + parseInt(item[1]);				
					pdArray[i] = pd_id + "=" + cnt;
				}
				saved_pd += pdArray[i]+";";
			}
			if(!bFound)
			{				
				saved_pd+= pd_id+"="+count;
			}	
		}
		else
		{	
			saved_pd = pd_id+"="+count;
		}
		$.cookie("imall_pd_list", saved_pd, { path: '/', expires: 30 });
		return true;
	};
	selectProductDetail = function(pd_id)
	{
		 $("#select_pd").val(pd_id);
		 var imgs = $("#selectId").find(".img_small_size");
		  $.each(imgs, function(index, img) {
			  var id = $(img).attr("id");
			  if(id == pd_id)
			  {
				  if(!$(img).hasClass("select"))
				  {
					 $(img).addClass("select"); 
				  }		  
				  
			  }
			  else
			  {
				  if($(img).hasClass("select"))
				  {
					 $(img).removeClass("select"); 
				  }	 
			  }
			  
		  });
	};
	

	
})(jQuery, window);