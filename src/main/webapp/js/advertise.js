(function($, window, undefined) {
	addPicTr = function(){
		 var index = parseInt($("#pic_index").val()) + 1;
		 var table = $("#createTable");
		 var tr = $('<tr></tr>');
        var td = $('<td></td>');
        td.html("广告url:<input type='text' id='url"+index+"' name='url"+index + "'>");
        tr.append(td);
        var td = $('<td></td>');
        td.html("广告图片:<input type='file' id='pic" +index +"' name='pic"+index +"'>");
        tr.append(td);
        table.append(tr);
        var td = $('<td></td>');
        var ad_str = "广告类型:<select id='type" + index +"' name='type" + index +"'>";
        ad_str +="<option value='1'>首页大</option>";
        ad_str +="<option value='2'>首页小</option>";
        ad_str +="<option value='3' selected>普通</option>";
        ad_str +="</select>";
        td.html(ad_str);
        tr.append(td);
        table.append(tr);
        $("#pic_index").val(index);
	};
	checkSubmit = function()
	{	
		return true;	
	};
	
})(jQuery, window);