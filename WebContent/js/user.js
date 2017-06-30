
$(document).ready(function() {

	$("#addCard").click(function(event) {
			reSearchCards();
	});
	
	$("#addPres").click(function(event){
		reSearchPrescriptions();
	});
});

function formatDataCards(responseText) {
	var toAppend = '<h2>Products</h2><table class="table-bordered"><thead><tr><th>Numero CC</th><th>Intestatario</th><th>Circuito</th><th>Scadenza</th></tr></thead>';
	$.each(responseText, function(i, cardsObject) {
		console.log(cardsObject);
			toAppend += '<tr><td>' + cardsObject.numerocc + '</td>';
			toAppend += '<td>' + cardsObject.intestatario + '</td>';
			toAppend += '<td>' + cardsObject.circuito + '</td>';
			toAppend += '<td>' + cardsObject.datascadenza + '</td>';
			toAppend+='<td><input type="submit" name="removeCard" value="remove" /></td></tr>';
	});
	toAppend += '</table>';
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

function reSearchCards(){
		$.ajax({
			type : "POST",
			url : "user",
			data : {
				action: "addCard"
			},
			dataType : "json",
			error : function(xhr, status, errorThrown) {
				console.log(JSON.stringify(xhr));
				console.log("AJAX error: " + status + ' : ' + errorThrown);
			},
			success : function(responseText) {
				console.log(responseText);
				formatDataCards(responseText);
			}
		})
}

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