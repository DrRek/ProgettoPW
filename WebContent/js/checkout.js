$(document).ready(function() {

	updateTable();
	//initializeCart();

	$("body").on("change","input[name='numeroPezziDisponibili']",
			function() {
		var valore = $(this).val();
		var val = parseInt(valore);
		if (val > 0) {
			var nomeArt = $(this).parent().siblings(
			".prodotto").find(".nomeArt").html();
			var marcaArt = $(this).parent().siblings(
			".prodotto").find(".marcaArt").html();
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
		".prodotto").find(".nomeArt").html();
		var marcaArt = $(this).parent().siblings(
		".prodotto").find(".marcaArt").html();
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
		var nomeArt = $(this).parent().siblings(
		".prodotto").find(".nomeArt").html();		
		var marcaArt = $(this).parent().siblings(
		".prodotto").find(".marcaArt").html();		
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
		var toAppend = '<table id="cartElements"class="table table-hover table-condensed">'
			+'<thead><th style="width:20%">Prodotto</th>'
			+'<th style="width:10%" >Tipo</th>'
			+'<th style="width:20%" >Gradazione</th>'
			+'<th style="width:8%" >Numero Prodotti</th>'
			+'<th style="width:11%" >Price</th>'
			+'<th style="width:11%" >Discounted price</th>'
			+'<th style="width:10%"></th></thead><tbody>';
		var products = new Array();
		var tot = 0;
		products = cart[Object.keys(cart)[0]];

		jQuery.each(products, function(index, prod) {

			toAppend+='<tr class="rowArticle"><td class="prodotto" data-th="Prodotto">'
				+ '<h4 class="nomargin nomeArt">'+prod.articolo.nome+'</h4><p class="marcaArt">'+prod.articolo.marca+'</p></td>';
			
			toAppend+='<td data-th="Tipo" class="tipoArt ">' + prod.articolo.tipo + '</td>'
			
			toAppend += '<td data-th="Gradazione" class="gradazioneArt ">'
				if(prod.articolo.tipo == "O"){
					toAppend += '<select class="form-control" class="gradazioneArt">';
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

			toAppend+='<td><input  data-th="Numero Prodotti" name="numeroPezziDisponibili" class="form-control "  type="number" min="1"value="'+prod.numero+'"></td>';

			var prezzo = prod.articolo.prezzo*prod.numero;
			toAppend+='<td data-th="Subtotal" class="prezzoArt ">'+prezzo+'€</td>';
			
			if(prod.articolo.tipoSconto=="s")
				var realPrezzo = (prod.articolo.prezzo-prod.articolo.sconto)*prod.numero;
			else
				var realPrezzo = (prod.articolo.prezzo-(prod.articolo.prezzo*prod.articolo.sconto/100))*prod.numero;
			tot += realPrezzo;
			toAppend+='<td data-th="Subtotal" class="prezzoArt ">'+realPrezzo+'€</td>';

			toAppend+='<td data-th=""><input class= "btn btn-outline-secondary " type="submit" name="removeCart" value="remove" /></td></tr>';


		});
		toAppend+='</tbody></table>';
		$("#divCartElements").html(toAppend);
		$("#tot").html("Prezzo totale: " + tot + "€" );
	}
});