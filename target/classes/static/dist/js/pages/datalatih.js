
$('#penyakit').change(function(){
	
	var selected = $(this).val();
	window.location.href="/datalatih/"+selected+"/1";
})

$('.ekstraksi').click(function(){
	window.location.href="/datalatih/ekstraksi";
	
})