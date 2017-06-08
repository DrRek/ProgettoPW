function ccValidation()
{
	alert("qui");
	var numcc = $("input[name='numcc']").val();
	var intestatario = $("input[name='intestatario']").val();
	var circuito = $("input[name='circuito']").val();
	var scadenza = $("input[name='scadenza']").val();
	var cvv = $("input[name='cvv']").val();

	if(numcc_validation(numcc, 16))
	{
		if(intestatario_validation(pass))
		{
			if(circuito_validation(circuito))
			{
				if(cvv_validation(cvv))
				{
					return true;
				}
			}
		}
	}
	return false;
} 

function numcc_validation(numcc,mx)
{
	var numcc_len = numcc.value.length;
	var numbers = /^[0-9]+$/;
	
	if (numcc_len != mx)
	{
		$("#numcc").html( "Il numero CC deve contenere esattamente" + mx + "caratteri");
		numcc.focus();
		return false;
	}
	else if(numcc.value.match(numbers))
	{
		return true;
	}
	else
	{
		$("#numcc").html("Il numero CC può contenere solo numeri");
		numcc.focus();
		return false;
	}
}
function intestatario_validation(intestatario)
{
	var letters = /^[A-Za-z]+$/; //da aggiungere che si deve inserire sia nome che cognome
	if(intestatario.value.match(letters))
	{
		return true;
	}
	else
	{
		$("#intestatario").html( "È consentito usare solo caratteri alfabetici");
		intestatario.focus();
		return false;
	}
}
function circuito_validation(circuito)
{ 
	if(circuito.value == "Visa" || circuito.value == "Mastercard" || circuito.value == "American Express")
	{
		return true;
	}
	else
	{
		$("#circuito").html( "Si accettano solo Visa, Mastercard e American Express");
		circuito.focus();
		return false;
	}
}
function cvv_validation(cvv)
{ 
	var numbers = /^[0-9]+$/;
	if((cvv.value.match(numbers)) && (cvv.value.length == 3))
	{
		return true;
	}
	else
	{
		$("#cvv").html("Il CVV può contenere solo numeri e deve averne esattamente 3");
		cvv.focus();
		return false;
	}
}
 