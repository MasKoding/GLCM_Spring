$(document).ready(function(){
	
	selectDiseaseDataTesting();
})

function selectDiseaseDataTesting(){
	var selected = $('#penyakit').val();
	window.location.href="/datatesting/"+selected+"/1";	
}
function confirmDeleteDataTesting(id){
	if(id!=null || id!=''){
		if(confirm("Are you sure want to delete this data?")){
			location.href='/datatesting/delete/'+id;	
		}	
	}
}