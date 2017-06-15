$(document).ready(function() {
	var occhiali = $("div#daMostrareSeOcchiali");
	var lentine = $("div#daMostrareSeLentine");
	occhiali.hide();
	lentine.hide();
	
	$("input[name='tipo']").change(function(){
		if ($(this).val() === 'Occhiale') {
			occhiali.show();
			lentine.hide();
		} else if ($(this).val() === 'Lente a contatto') {
			occhiali.hide();
			lentine.show();
		}
	});
});