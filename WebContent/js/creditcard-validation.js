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
	$("#numcc").html("");
	return true;
}

function intestatario_validation(intestatario)
{
	var letters = /^[A-Za-z ]+$/; //da aggiungere che si deve inserire sia nome che cognome
	if(!intestatario.val().match(letters)){
		$("#intestatario").html( "È consentito usare solo caratteri alfabetici");
		intestatario.focus();
		return false;
	}
	$("#intestatario").html("");
	return true;
}
function circuito_validation(circuito)
{ 
	if(circuito.val().toLowerCase() == "visa" || circuito.val().toLowerCase() == "mastercard" || circuito.val().toLowerCase() == "american express")
	{
		$("#circuito").html( "");
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
		  $("#scadenza").html("");
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
		$("#cvv").html("");
		return true;
	}
	else
	{
		$("#cvv").html("Il CVV deve essere composto da 3 numeri");
		cvv.focus();
		return false;
	}
}
 