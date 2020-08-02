
$('#file').change(function(){
	var file = $(this).val();
	$("#image_show").attr('src',file);
	console.log('files',file);
})