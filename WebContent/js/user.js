$(document).ready(function(){
	
	$("#addCard").on("click", function(event) {
		if(ccValidation())
			addCard();
	});
	
	$("#addPres").click(function(event){
		if(presValidation()){
			addPrescription();
		}
	});
	
	$("#submitP").click(function(event){
		addPromotion();
	});
	
});

function addCard(){
	var numcc = $("input[name=numcc]").val();
	var intestatario = $("input[name=intestatario]").val();
	var circuito = $("input[name=circuito]").val();
	var scadenza = $("input[name=scadenza]").val();
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

function addPres() {
	var tipoP = $("input[name='nomeP']");
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
	var pdDX =  $("input[name='pdDX']");
	$.ajax({
		type : "POST",
		url : "user",
		data : {
			action : 'addPres',
			nomeP : tipoP,
			sferaSX : sferaSX,
			cilindroSX : cilindroSX,
			asseSX : asseSX,
			sferaDX : sferaDX,
			cilindroDX : cilindroDX,
			asseDX : asseDX,
			addVicinanza : addVicinanza,
			prismaOrizSX : prismaOrizSX,
			prismaOrizSXBD : prismaOrizSXBD,
			prismaOrizDX : prismaOrizDX,
			prismaOrizDXBD : prismaOrizDXBD,
			prismaVertSX : prismaVertSX,
			prismaVertSXBD : prismaVertSXBD, 
			prismaVertDX : prismaVertDX,
			prismaVertDXBD : prismaVertDXBD,
			pdSX : pdSX,
			pdDX : pdDX
		},
		dataType : "json",
		error : function(xhr, status, errorThrown) {
			console.log(JSON.stringify(xhr));
			console.log("AJAX error: " + status + ' : ' + errorThrown);
		},
		success : function(responseText) {
			formatDataPrescriptions(responseText);
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
	var toAppend = '<thead><tr><th>Codice</th><th>Tipo</th></tr></thead>';
	$.each(responseText, function(i, prescriptionsObject) {
		toAppend += '<tr><td>' + prescriptionsObject.codice + '</td>';
		toAppend += '<td>' + prescriptionsObject.nome + '</td>';
		toAppend+='<td><input type="submit" name="removePrescription" value="remove" /></td></tr>';
	});
	$("#prescriptions").html(toAppend);
};


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
	var tipoP = $("input[name='nomeP']");
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

function addPromotion(){
	var nome = $("#nomeP").val();
	var descrizione = $("#descrizioneP").val();
	var sconto = $("#scontoP").val();
	var tipo = $("input[name=tipoP]:checked").val();
	var inizio = $("#inizioP").val();
	var fine = $("#fineP").val();
	if($('#cumulabileP').is(':checked')){
		var cumulabile = true;
	}else{
		var cumulabile = false;
	}
	$.ajax({
		type : "POST",
		url : "promotion",
		async : true,
		data : {
			action : 'addPromotion',
			nome : nome,
			descrizione : descrizione,
			sconto : sconto,
			tipo : tipo,
			inizio : inizio,
			fine : fine,
			cumulabile : cumulabile
		},
		dataType : "json",
		error : function(xhr, status, errorThrown) {
			console.log(JSON.stringify(xhr));
			console.log("AJAX error: " + status + ' : ' + errorThrown);
		},
		success : function(responseText) {
			formatDataPromotion(responseText);
		}
	});
}

function formatDataPromotion(responseText){
	var toAppend = '<thead><tr><th>Name</th><th>Description</th><th>Value</th><th>Type</th><th>Start</th><th>End</th><th>Cumulative</th><th></th></tr></thead>';
	$.each(responseText, function(i, promotionObject) {
			toAppend += '<tr><td>' + promotionObject.nome + '</td>';
			toAppend += '<td>' + promotionObject.descrizione + '</td>';
			toAppend += '<td>' + promotionObject.sconto + '</td>';
			toAppend += '<td>' + promotionObject.tipo + '</td>';
			toAppend += '<td>' + promotionObject.dataInizio + '</td>';
			toAppend += '<td>' + promotionObject.dataFine + '</td>';
			toAppend += '<td>' + promotionObject.cumulabile + '</td>';
			toAppend += '<td><a href="promotion?nome='+promotionObject.nome+'">info/edit</a></td></tr>';
	});
	$("#tablePromotion").html(toAppend);
}