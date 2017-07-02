$(document).ready(function() {

	updateTable();
	//initializeCart();

	$("body").on("change","input[name='numeroPezziDisponibili']",
			function() {
		var valore = $(this).val();
		var val = parseInt(valore);
		if (val > 0) {
			var nomeArt = $(this).parent().siblings(
			".nomeArt").html();
			var marcaArt = $(this).parent().siblings(
			".marcaArt").html();
			var tipoArt = $(this).parent().siblings(
			".tipoArt").html();
			var prezzo = $(this).parent().siblings(
			".prezzoArt").html();
			var prezzoArt = parseFloat(prezzo);

			if (tipoArt == "O" || tipoArt == "o") {
				$.ajax({
					async:false,
					type : "POST",
					url : "checkout",
					data : {
						action : "updateCart",
						nome : nomeArt,
						marca : marcaArt,
						numero : val
					}

				});
			} else {
				var gradazioneArt = $(this).parent()
				.siblings(".gradazioneArt")
				.html();
				$.ajax({
					async:false,
					type : "POST",
					url : "checkout",
					data : {
						action : "updateCart",
						nome : nomeArt,
						marca : marcaArt,
						gradazione : gradazioneArt,
						numero : val
					}
				});
			}
		};
		updateTable();
	});

	$("body").on("click","input[name='removeCart']", function() {
		var nomeArt = $(this).parent().siblings(
		".nomeArt").html();
		var marcaArt = $(this).parent().siblings(
		".marcaArt").html();
		var tipoArt = $(this).parent().siblings(
		".tipoArt").html();
		if (tipoArt == "O" || tipoArt == "o") {
			$.ajax({
				type : "POST",
				url : "checkout",
				data : {
					action : "removeCart",
					nome : nomeArt,
					marca : marcaArt
				},
				success : function() {
				}

			});
		} else {
			var gradazioneArt = $(this).parent()
			.siblings(".gradazioneArt").html();
			$.ajax({
				type : "POST",
				url : "checkout",
				data : {
					action : "removeCart",
					nome : nomeArt,
					marca : marcaArt,
					gradazione : gradazioneArt
				},
				success : function() {
				}
			});
		}
		updateTable();
	});

	$("body").on("blur","select.gradazioneArt", function(){
		var prescrizione = this.value;
		var nomeArt = $(this).parent().siblings(".nomeArt").html();
		var marcaArt = $(this).parent().siblings(".marcaArt").html();
		$.ajax({
			async:false,
			type : "POST",
			url : "checkout",
			data : {
				action : "updatePrescription",
				nome : nomeArt,
				marca : marcaArt,
				prescrizione : prescrizione
			}
		});
		updateTable();
	});
	
	function updateTable(){
		var dati_carrello = function () {
			var tmp=null;
			$.ajax({
		        async: false,
				type: "GET",
				url: "checkout",
				data: {
					action: 'asyncGenericSearch'
				},
				dataType: "json",
				error: function (xhr, status, errorThrown) {
					console.log(JSON.stringify(xhr)); 
					console.log("AJAX error: " + status + ' : ' + errorThrown); 
				},
				success: function(responseText) {
					tmp = responseText;
				}
			});
			return tmp;
		}();
		var prescrizioni_disponibili = function () {
			var tmp=null;
			$.ajax({
		        async: false,
				type: "GET",
				url: "checkout",
				data: {
					action: 'prescriptions'
				},
				dataType: "json",
				error: function (xhr, status, errorThrown) {
					console.log(JSON.stringify(xhr)); 
					console.log("AJAX error: " + status + ' : ' + errorThrown); 
				},
				success: function(respText) {
					tmp = respText;
				}
			});
			return tmp;
		}();
		formatData(dati_carrello, prescrizioni_disponibili)
	}
	
	function formatData(cart, presc){
		var toAppend = '<table id="cartElements" class="table table-condensed"><tr><th>Tipo</th><th>Nome</th><th>Marca</th><th>Gradazione/Prescrizione</th><th>Numero Prodotti</th><th>Prezzo</th><th>Opzioni</th></tr>';
		var products = new Array();
		var tot = 0;
		products = cart[Object.keys(cart)[0]];

		jQuery.each(products, function(index, prod) {

			toAppend+='<tr class="rowArticle"><td class="tipoArt">'+prod.articolo.tipo+'</td>';
			toAppend+='<td class="nomeArt">'+prod.articolo.nome+'</td>';
			toAppend+='<td class="marcaArt">'+prod.articolo.marca+'</td>';

			toAppend += '<td class="gradazioneArt">'
				if(prod.articolo.tipo == "O"){
					toAppend += "<select class='gradazioneArt'>";
					var find=false;
					jQuery.each(presc, function(index, pre) {
						if(prod.prescrizione!=null&&pre.codice == prod.prescrizione.codice){
							toAppend += '<option value="'+pre.codice+'" selected>'+pre.codice+'</option>';
							find=true;
						}else{
							toAppend += '<option value="'+pre.codice+'">'+pre.codice+'</option>';
						}
					});
					if(find){
						toAppend += '<option value="Neutro">Neutro</option>';
					}else{
						toAppend += '<option value="Neutro" selected>Neutro</option>';
					}
					toAppend += '</select>';
				}
				else
					toAppend+= prod.articolo.gradazione;

			toAppend += '</td>';

			toAppend+='<td><input name="numeroPezziDisponibili" type="number" min="1"value="'+prod.numero+'"></td>';

			var prezzo = prod.articolo.prezzo*prod.numero;
			tot += prezzo;
			toAppend+='<td class="prezzoArt">'+prod.articolo.prezzo*prod.numero+'€</td>';

			toAppend+='<td><input type="submit" name="removeCart" value="remove" /></td></tr>';


		});
		toAppend+='</table>';
		$("#divCartElements").html(toAppend);
		$("#tot").html("Prezzo totale: " + tot + "€" );
	}
});