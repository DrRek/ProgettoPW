$(document).ready(function() {

	$("#AddGlassToStorage").click(function(event) {
		var oNome = $("#oNome").html();
		var oMarca = $("#oMarca").html();
		var oQuantita = $("input[name=oQuantita]").val();
		$.ajax({
			type : "GET",
			url : "articlePage",
			data : {
				action : "updateGlass",
				nome : oNome,
				marca : oMarca,
				quantita : oQuantita
			},
			dataType : "json",
			success : function(responseText) {
				$("#oDisp").html(oQuantita + " left");
			}
		});
	});
	
	$("#AddLenseToStorage").click(function(event) {
		var nome = $('#lNome').html();
		var marca = $('#lMarca').html();
		var gradazione = $('select[name=lGradazione]').val();
		var quantita = $('input[name=lQuantita]').val();
		alert("ok"+nome+marca+gradazione+quantita);
		$.ajax({
			type : "GET",
			url : "articlePage",
			data : {
				action : 'updateContact',
				nome : nome,
				marca : marca,
				gradazione : gradazione,
				quantita : quantita
			},
			dataType : "json",
			success : function(responseText) {
				alert("Success!");
			}
		});
	});
});