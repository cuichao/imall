(function($, window, undefined) {
	
	$(document).ready(function() {	
		CKEDITOR.BasePath="../ckeditor/";
		CKEDITOR.replace("desc",{filebrowserUploadUrl : basePath +'/upload/file?Type=File',  
			filebrowserImageUploadUrl : basePath +'/upload/file?Type=Picture',  
			filebrowserFlashUploadUrl : basePath + '/upload/file?Type=Flash'});
	});
	
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
	
	
	
	
	
})(jQuery, window);