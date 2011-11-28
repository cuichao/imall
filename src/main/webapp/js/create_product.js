(function($, window, undefined) {
	
	$(document).ready(function() {
		CKEDITOR.BasePath="../ckeditor/";
		CKEDITOR.replace("desc",{filebrowserUploadUrl : basePath +'/upload/file?Type=File',  
			filebrowserImageUploadUrl : basePath +'/upload/file?Type=Picture',  
			filebrowserFlashUploadUrl : basePath + '/upload/file?Type=Flash'});
	});
	
})(jQuery, window);