$(document).ready(function() {
		
			initializeCart();
		
			$("input[name='numeroPezziDisponibili']").change(
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
			    	$.each(responseText, function(i, cartObject) {
			    		toAppend+='<tr><td class="tipoArt">'+cartObject[0].articolo.tipo+'</td>';
			    		toAppend+='<td class="nomeArt">'+cartObject[0].articolo.nome+'</td>';
			    		toAppend+='<td class="marcaArt">'+cartObject[0].articolo.marca+'</td>';
			    		if(cartObject[0].articolo.tipo == "O"){
				    		toAppend+='<td>N/A</td>';
			    		} else{
			    			toAppend+='<td>'+cartObject[0].articolo.gradazione+'</td>';
			    		}
			    		toAppend+='<td><input name="numeroPezziDisponibili" type="number" min="1"value="'+cartObject[0].numero+'"></td>';
			    		toAppend+='<td class="prezzoArt">'+cartObject[0].articolo.prezzo*cartObject[0].numero+'€</td>';
			    		toAppend+='<td><input type="submit" name="removeCart" value="remove" /></td></tr>';
			        });
			    	toAppend+='</table>';
			    	$("#cartElements").html(toAppend);
			};
});