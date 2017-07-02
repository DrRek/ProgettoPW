
$(document).on('ready', function() {
	alert("test1");
	
	
});

$(document).ready(function(){
	setSearchField();
	$("#addCard").on("click", function(event) {
			//TODO il validate
			addCard();
	});
	
	$("#addPres").click(function(event){
		//TODO il validate
		addPrescription();
		reSearchPrescriptions();
	});
	
	$("#addProduct").click(function(event){
		//TODO il validate
		alert("1");
		addProduct();
	});
});

function addCard(){
	var numcc = $("input[name=numcc]").val();
	var intestatario = $("input[name=intestatario]").val();
	var circuito = $("input[name=circuito]").val();
	var scadenza = $("input[name=scadenza]").val();
	alert(scadenza)
	var cvv = $("input[name=cvv]").val();
	$.ajax({
		type : "POST",
		url : "user",
		data : {
			action : 'addCard',
			numcc : numcc,
			intestatario : intestatario,
			circuito : circuito,
			scadenza : scadenza,
			cvv : cvv
		},
		dataType : "json",
		error : function(xhr, status, errorThrown) {
			console.log(JSON.stringify(xhr));
			console.log("AJAX error: " + status + ' : ' + errorThrown);
		},
		success : function(responseText) {
			formatDataCards(responseText);
		}
	});
}

function formatDataCards(responseText) {
	var toAppend = '<thead><th>Numero carta</th><th>Intestatario</th><th>Circuito</th><th>Scadenza</th><th>Opzioni</th></thead>';
	$.each(responseText, function(i, cardsObject) {
		console.log(cardsObject);
			toAppend += '<tr><td>' + cardsObject.numerocc + '</td>';
			toAppend += '<td>' + cardsObject.intestatario + '</td>';
			toAppend += '<td>' + cardsObject.circuito + '</td>';
			toAppend += '<td>' + cardsObject.datascadenza + '</td>';
			toAppend+='<td><input type="submit" name="removeCard" value="remove" /></td></tr>';
	});
	$("#cards").html(toAppend);
};

function formatDataPrescriptions(responseText) {
	var toAppend = '<h2>Products</h2><table class="table-bordered"><thead><tr><th>Codice</th><th>Tipo</th></tr></thead>';
	$.each(responseText, function(i, prescriptionsObject) {
		console.log(prescriptionsObject);
			toAppend += '<tr><td>' + prescriptionsObject.codice + '</td>';
			toAppend += '<td>' + prescriptionsObject.tipo + '</td>';
			toAppend+='<td><input type="submit" name="removePrescription" value="remove" /></td></tr>';
	});
	toAppend += '</table>';
	$("#prescriptions").html(toAppend);
};

function reSearchPrescriptions(){
	$.ajax({
		type : "POST",
		url : "user",
		data : {
			action: "addPres"
		},
		dataType : "json",
		error : function(xhr, status, errorThrown) {
			console.log(JSON.stringify(xhr));
			console.log("AJAX error: " + status + ' : ' + errorThrown);
		},
		success : function(responseText) {
			console.log(responseText);
			formatDataPrescriptions(responseText);
		}
	})
}

function setSearchField() {
	if ($('select[name=tipo]').val() == "O") {
		$(".specificiPerOcchiali").show();
		$(".specificiPerLentine").hide();
	} else {
		$(".specificiPerOcchiali").hide();
		$(".specificiPerLentine").show();
	}
}

function addProduct(){
	alert("2");
	var nome = $("input[name=nome]").val();
	alert("qui non arriva");
	var marca = $("select[name=marca]").val();
	var tipo = $("select[name=tipo]").val();
	var prezzo = $("input[name=prezzo]").val();
	var img1 = $("input[name=img1]").val();
	var quantita = $("input[name=quantita]").val();
	if(tipo=="O"){
		alert("3");
		var descrizione = $("input[name=descrizione]").val();
		var sesso = $("select[name=sesso]").val();
		var img2 = $("input[name=img2]").val();
		var img3 = $("select[name=img3]").val();
		$.ajax({
			type : "POST",
			url : "article",
			data : {
				toDo: "addProduct",
				nome : nome,
				marca : marca,
				tipo : tipo,
				prezzo : prezzo,
				quantita : quantita,
				descrizione : descrizione,
				sesso : sesso,
				img1 : img1,
				img2 : img2,
				img3 : img3
			},
			contentType: 'multipart/form-data',
			error : function(xhr, status, errorThrown) {
				console.log(JSON.stringify(xhr));
				console.log("AJAX error: " + status + ' : ' + errorThrown);
			},
			success : function(responseText) {
				alert(responseText);
			}
		})
	}else{
		var gradazione = $("select[name=gradazione]").val();
		var tipologia = $("select[name=tipologia]").val();
		var raggio = $("input[name=raggio]").val();
		var diametro = $("input[name=diametro]").val();
		var colore = $("select[name=colore]").val();
		var pezziPerPacco = $("input[name=pezziPerPacco]").val();
		$.ajax({
			type : "POST",
			url : "article",
			data : {
				toDo: "addProduct",
				nome : nome,
				marca : marca,
				tipo : tipo,
				prezzo : prezzo,
				quantita : quantita,
				gradazione : gradazione,
				tipologia : tipologia,
				raggio : raggio,
				diametro : diametro,
				colore : colore,
				pezziPerPacco : pezziPerPacco,
				img1 : img1
			},
			contentType: 'multipart/form-data',
			error : function(xhr, status, errorThrown) {
				console.log(JSON.stringify(xhr));
				console.log("AJAX error: " + status + ' : ' + errorThrown);
			},
			success : function(responseText) {
				alert(responseText);
			}
		})
	}
}