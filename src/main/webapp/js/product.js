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
		var query = $.trim($("#query_text").val());
		if(query != undefined && query != "")
		{
			this.location.href = basePath +"/product/list?page="+pageNo + "&query="+query;
		}
		else
		{
			this.location.href = basePath +"/product/list?page="+pageNo;
		}
		
		
		
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
		var num_limit = $("#count_" + pd_id).val();
		if(count > num_limit)
		{
			alert("对不起，该商品只剩"+num_limit + "件了，请重新选择数量！");
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
				  var num_limit = $("#count_" + pd_id).val();
				  if( num_limit == 0 )
				  {
					  $("#detail_desc").val("此颜色缺货");
				  }
				  else
				  {
					  $("#detail_desc").val("");
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
	newComment = function(productid)
	{
		var cur_user = $("#cur_user").val();
		if(cur_user == "")
		{
			alert("请先登陆！");
			return false;
		}
		var url = basePath + "/product/" + productid + "/newcomment";
		$("#new_comment_div").load(url);
		$("#product-coment-btn").css({display:"none"});
	};
	checkSubmitComment = function()
	{
		var comment = $.trim($("#comment").val());
		if(comment == "")
		{
			alert("评论不能为空！");
			return false;
		}
		if(!checkCaptcha())
		 {
			 return false;
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
})(jQuery, window);