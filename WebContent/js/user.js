
$(document).on('ready', function() {
	alert("test1");
	
	
});

$(document).ready(function(){
	setSearchField();
	$("#addCard").on("click", function(event) {
		if(ccValidation())
			addCard();
	});
	
	$("#addPres").click(function(event){
		if(presValidation()){
			addPrescription();
			reSearchPrescriptions();
		}
	});
});

function addCard(){
	var numcc = $("input[name=numcc]").val();
	var intestatario = $("input[name=intestatario]").val();
	var circuito = $("input[name=circuito]").val();
	var scadenza = $("input[name=scadenza]").val();
	alert(numcc)
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
			toAppend += '<tr><td>' + cardsObject.numeroCC + '</td>';
			toAppend += '<td>' + cardsObject.intestatario + '</td>';
			toAppend += '<td>' + cardsObject.circuito + '</td>';
			toAppend += '<td>' + cardsObject.dataScadenza + '</td>';
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

//di seguito le funzioni per il validate

//carta di credito
function ccValidation()
{
	var numcc = $("input[name='numcc']");
	var intestatario = $("input[name='intestatario']");
	var circuito = $("input[name='circuito']");
	var scadenza = $("input[name='scadenza']");
	var cvv = $("input[name='cvv']");

	if(numcc_validation(numcc) &&
	intestatario_validation(intestatario) &&
	circuito_validation(circuito) &&
	scadenza_validation(scadenza) &&
	cvv_validation(cvv))
		return true;
	
	return false;
}

function numcc_validation(numcc)
{
	var numbers = /^[0-9]+$/;
	if (numcc.val().length != 16){
		$("#numcc").html( "Il numero CC deve contenere esattamente 16 caratteri");
		numcc.focus();
		return false;
	}
	else if(!numcc.val().match(numbers)){
		$("#numcc").html("Il numero CC può contenere solo numeri");
		numcc.focus();
		return false;
	}
	else{
		$("#numcc").empty();
		return true;
	}
}

function intestatario_validation(intestatario)
{
	var letters = /^[A-Za-z ]+$/; //da aggiungere che si deve inserire sia nome che cognome
	if(!intestatario.val().match(letters)){
		$("#intestatario").html( "È consentito usare solo caratteri alfabetici");
		intestatario.focus();
		return false;
	}
	else{
		$("#intestatario").empty();
		return true;
	}
}
function circuito_validation(circuito)
{ 
	if(circuito.val().toLowerCase() == "visa" || circuito.val().toLowerCase() == "mastercard" || circuito.val().toLowerCase() == "american express")
	{
		$("#circuito").empty();
		return true;
	}
	else
	{
		$("#circuito").html( "Si accettano solo Visa, Mastercard e American Express");
		circuito.focus();
		return false;
	}
}

function scadenza_validation(scadenza){
	  var regEx = /^\d{4}-\d{2}-\d{2}$/;
	  if(scadenza.val().match(regEx)){
		  $("#scadenza").empty();
		  return true;
	  }
	  $("#scadenza").html("Il formato della data non è valido. (yyyy-MM-dd)");	//TO DO: deve controllare date impossibili e date passate
	  return false;
}

function cvv_validation(cvv)
{ 
	var numbers = /^[0-9]+$/;
	if((cvv.val().match(numbers)) && (cvv.val().length == 3))
	{
		$("#cvv").empty();
		return true;
	}
	else
	{
		$("#cvv").html("Il CVV deve essere composto da 3 numeri");
		cvv.focus();
		return false;
	}
}

//prescrizione

function presValidation() {
	var tipoP = $("input[name='tipoP']");
	var sferaSX = $("input[name='sferaSX']");
	var cilindroSX = $("input[name='cilindroSX']");
	var asseSX =  $("input[name='asseSX']");
	var sferaDX = $("input[name='sferaDX']");
	var cilindroDX = $("input[name='cilindroDX']");
	var asseDX =  $("input[name='asseDX']");
	var addVicinanza = $("input[name='addVicinanza']");
	var prismaOrizSX = $("input[name='prismaOrizSX']");
	var prismaOrizSXBD =  $("input[name='prismaOrizSXBD']");
	var prismaOrizDX = $("input[name='prismaOrizDX']");
	var prismaOrizDXBD =  $("input[name='prismaOrizDXBD']");
	var prismaVertSX = $("input[name='prismaVertSX']");
	var prismaVertSXBD =  $("input[name='prismaVertSXBD']");
	var prismaVertDX = $("input[name='prismaVertDX']");
	var prismaVertDXBD =  $("input[name='prismaVertDXBD']");
	var pdSX = $("input[name='pdSX']");
	var pdDX =  $("input[name='pdSX']");
	
	if(allLetter(tipoP, "#tipoP") && isNumber(sferaSX,-10,10, "#sferaSX") 
			&& isNumber(cilindroSX,-10,10, "#cilindroSX")  && isNumber(asseSX,0,180, "#asseSX") 
			&& isNumber(sferaDX,-10,10,"#sferaDX")  && isNumber(cilindroDX,-10,10, "#cilindroDX") 
			&& isNumber(asseDX,0,180,"#asseDX")&& isNumber(addVicinanza,-10,10, "#addVicinanza") 
			&& isNumber(prismaOrizSX,-10,10, "#prismaOrizSX") && allLetter(prismaOrizSXBD, "#prismaOrizSXBD") 
			&& isNumber(prismaOrizDX,-10,10, "#prismaOrizDX") && allLetter(prismaOrizDXBD, "#prismaOrizDXBD")
			&& isNumber(prismaVertSX,-10,10, "#prismaVertSX")  && allLetter(prismaVertSXBD, "#prismaVertSXBD") 
			&& isNumber(prismaVertDX,-10,10, "#prismVertDX") && allLetter(prismaVertDXBD, "#prismaVertDXBD") 
			&& isNumber(pdSX,-10,10, "#pdSX") && isNumber(pdDX,-10,10, "#pdDX"))
		return true;
	else
		return false;
}

function isNumber(input, min, max, id)
{
	var numbers = /^-?\d+(\.\d+)?$/;
	if(input.val().match(numbers))
	{
		if(input.val() >= min && input.val() <= max){
			$(id).empty();
			return true
		}
		else
		{
			$(id).html("I valori consentiti sono tra"+ min + " e " + max);
			input.focus();
			return false;
		}
	}
	else
	{
		$(id).html("Sono consentiti solo numeri");
		input.focus();
		return false;
	}
}

function allLetter(input, id)
{ 
	var letters = /^[A-Za-z]+$/;
	if(input.val().match(letters))
	{
		$(id).empty();
		return true;
	}
	else
	{
		$(id).html("È consentito usare solo caratteri alfabetici");
		input.focus();
		return false;
	}
}
 