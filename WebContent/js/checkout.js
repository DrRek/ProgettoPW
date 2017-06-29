$(document).ready(function() {

	initializeCart();

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
					type : "POST",
					url : "checkout",
					data : {
						action : "updateCart",
						nome : nomeArt,
						marca : marcaArt,
						numero : val
					},
					success : function() {
						initializeCart();
					}

				});
			} else {
				var gradazioneArt = $(this).parent()
				.siblings(".gradazioneArt")
				.html();
				$.ajax({
					type : "POST",
					url : "checkout",
					data : {
						action : "updateCart",
						nome : nomeArt,
						marca : marcaArt,
						gradazione : gradazioneArt,
						numero : val
					},
					success : function() {
						initializeCart();
					}
				});
			}

		}
	});

	$("body").on("click","input[name='removeCart']",
			function() {
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
					initializeCart();
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
					initializeCart();
				}
			});
		}

	});

	function initializeCart(){
		$.ajax({
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
				formatData(responseText);
			}
		})
	}


	function formatData(responseText){
		var toAppend = '<table id="cartElements" class="table-bordered"><tr><th>Tipo</th><th>Nome</th><th>Marca</th><th>Gradazione</th><th>Numero Prodotti</th><th>Prezzo</th><th>Opzioni</th></tr>';
		var products = new Array();
		var tot = 0;
		products = responseText[Object.keys(responseText)[0]];

		var prescriptions = getPrescriptions();

		jQuery.each(products, function(index, prod) {

			toAppend+='<tr><td class="tipoArt">'+prod.articolo.tipo+'</td>';
			toAppend+='<td class="nomeArt">'+prod.articolo.nome+'</td>';
			toAppend+='<td class="marcaArt">'+prod.articolo.marca+'</td>';

			toAppend += '<td class="gradazioneArt">'
				if(prod.articolo.tipo == "O"){
					if(prescriptions != null){
						jQuery.each(prescriptions, function(i, prescription)
								{
							toAppend += '<option value="'+ prescription.codice +'"> sx: '+ prescription.cilindrosx + ' dx: ' + prescription.cilindrodx + '</option>';
								});
						toAppend += '</select>';
					}
					else
						toAppend += 'N/A';

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
		$("#cartElements").html(toAppend);
		$("#tot").html("Prezzo totale: " + tot + "€" );
	};

	function getPrescriptions()
	{
		$.ajax({
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
				if(respText!=null && respText != undefined)
				{
					return respText
				}
				else
					return null;

			}
		})
	};
});