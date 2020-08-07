	
	
	
	           
function readFile(input) {
 if (input.files && input.files[0]) {
 var reader = new FileReader();
 
 reader.onload = function (e) {
 var htmlPreview = 
 '<img width="200" src="' + e.target.result + '" />'+
 '<p>' + input.files[0].name + '</p>';
 var wrapperZone = $(input).parent();
 var previewZone = $(input).parent().parent().find('.preview-zone');
 var boxZone = $(input).parent().parent().find('.preview-zone').find('.box').find('.box-body');
 
 wrapperZone.removeClass('dragover');
 previewZone.removeClass('hidden');
 boxZone.empty();
 boxZone.append(htmlPreview);
 };
 
 reader.readAsDataURL(input.files[0]);
 }
}
function reset(e) {

 e.wrap('<form>').closest('form').get(0).reset();
 e.unwrap();
 $('#table-result').remove();
}
$(".dropzone").change(function(){
 readFile(this);
});
$('.dropzone-wrapper').on('dragover', function(e) {
 e.preventDefault();
 e.stopPropagation();
 $(this).addClass('dragover');
});
$('.dropzone-wrapper').on('dragleave', function(e) {
 e.preventDefault();
 e.stopPropagation();
 $(this).removeClass('dragover');
});
$('.remove-preview').on('click', function() {
 var boxZone = $(this).parents('.preview-zone').find('.box-body');
 var previewZone = $(this).parents('.preview-zone');
 var dropzone = $(this).parents('.form-group').find('.dropzone');
 boxZone.empty();
 previewZone.addClass('hidden');
 reset(dropzone);
});


function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('#image_show').attr('src', e.target.result);
        }

        reader.readAsDataURL(input.files[0]);
    }
}


function refreshPage(){
	location.reload();
}
function submitUpload(){
	var loadPanel = $(".loadpanel").dxLoadPanel({
        shadingColor: "rgba(0,0,0,0.4)",
        position: { of: "#formUpload" },
        visible: false,
        showIndicator: true,
        showPane: true,
        shading: true,
        closeOnOutsideClick: false,
        onShown: function(){
            setTimeout(function () { 
            	var formData = new FormData();
            	formData.append('file', $('#file')[0].files[0]);
            	$.ajax({
         	       url : 'glcm/ekstraksi',
         	       type : 'POST',
         	       async:false,
         	       data : formData,
         	       processData: false,  // tell jQuery not to process the data
         	       contentType: false,  // tell jQuery not to set contentType
         	       success : function(data) {
         	           
         	           
         	           var html=`<table class="table table-striped" id="table-result">
         	        	   <tr>
         	        	   	   <th>Nama Penyakit</th>
         	        	   	   <th>Contrast</th>
         	        	   	   <th>Homogenity</th>
         	        	   	   <th>Energy</th>
         	        	   </tr>
         	           `;
         	           var nama_penyakit;
         	           $.map(data.result,function(i){
         	        	   nama_penyakit = (i.nama_latih == '' || i.nama_latih ==null) ? i.nama_testing : i.nama_latih; 
         	        	   html+=`
         	        	   <tr>
         	        	   <td><label>${nama_penyakit}</label></td>
         	        	   <td><label>${i.contrast} </label></td>
         	        	   <td><label>${i.homogenity} </label></td>
         	        	   <td><label>${i.energy} </label></td>
         	        	   </tr>`;
         	           })
         	           html+=`</table>`;
         	           loadPanel.show();
         	           $("#showResult").append(html);
         	       }
         	});

            	loadPanel.hide();          
            }, 10000);
        },
        onHidden: function(){
            
        }      
    }).dxLoadPanel("instance");
	loadPanel.show();
}
