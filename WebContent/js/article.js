$(document).ready(function() {
	$("#advancedSearch").click(function(event){
		//qui dovrei validare gli input
		var tipo = $('select[name=tipo]').val();
		var daCercare = $('input[name=daCercare1]').val();
		if(daCercare==null || daCercare.length <= 0 ){
			daCercare = $('input[name=daCercare]').val();
		}
		var marca = $('select[name=marca]').val();
		var prezzoMin = $('input[name=prezzoMin]').val();
		var prezzoMax = $('input[name=prezzoMax]').val();
		if(tipo=="O"){
			var sesso = $('select[name=sesso]').val();
			var colore = $('input[name=colore]').val();
			$.ajax({
				type: "GET",
				url: "article",
				data: {daCercare: daCercare,
					tipo: tipo,
					marca: marca,
					prezzoMin: prezzoMin,
					prezzoMax: prezzoMax,
					sesso: sesso,
					colore: colore
				},
				dataType: "text",
		        error: function (xhr, status) {
		            alert(status);
		        },
				success: function(responseText) {
			        console.log("uaua");
			        responseText = eval(responseText);
			    	$.each(responseText, function(i, articleObject) {
			    		$("#demos").html(articleObject.nome + "<br>");
			    	});
				}
			})
		} else {
			var gradazione = $('select[name=gradazione]').val();
			var tipologia = $('select[name=tipologia]').val();
			var raggio = $('input[name=raggio]').val();
			var diametro = $('input[name=diametro]').val();
			var colore = $('select[name=colore]').val();
			$.ajax({
				type: "GET",
				url: "article",
				data: {daCercare: daCercare,
					tipo: tipo,
					marca: marca,
					prezzoMin: prezzoMin,
					prezzoMax: prezzoMax,
					gradazione: gradazione,
					tipologia: tipologia,
					raggio: raggio,
					diametro: diametro,
					colore: colore
				},
				dataType: "json",
				success: function(responseText) {
			    	$.each(responseText, function(i, articleObject) {
	    	 			$("#demos").append(articleObject.nome + "<br>");
	    			});
				}
			})
		}
	});
});

function magic(responseText){
	
};