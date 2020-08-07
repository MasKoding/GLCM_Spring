$(document).ready(function(){
	
	selectDiseaseDataLatih();
})

function selectDiseaseDataLatih(){
	var selected = $('#penyakit').val();
	window.location.href="/datalatih/"+selected+"/1";	
}
function confirmDeleteDataLatih(id){
	if(id!=null || id!=''){
		if(confirm("Are you sure want to delete this data?")){
			location.href='/datalatih/delete/'+id;	
		}	
	}
}
