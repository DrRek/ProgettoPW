function ccValidation()
{
	var numcc = document.cc.numcc;
	var intestatario = document.cc.intestatario;
	var circuito = document.cc.circuito;
	var scadenza = document.cc.scadenza;
	var cvv = document.cc.cvv;

	if(numcc_validation(numcc, 16))
	{
		if(intestatario_validation(pass))
		{
			if(circuito_validation(circuito))
			{
				if(cvv_validation(cvv))
				{
					
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
		document.getElementById("numcc").innerHTML = "Il numero CC deve contenere esattamente" + mx + "caratteri";
		numcc.focus();
		return false;
	}
	else if(numcc.value.match(numbers))
	{
		return true;
	}
	else
	{
		document.getElementById("numcc").innerHTML ="Il numero CC può contenere solo numeri";
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
		document.getElementById("intestatario").innerHTML = "È consentito usare solo caratteri alfabetici";
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
		document.getElementById("circuito").innerHTML = "Si accettano solo Visa, Mastercard e American Express";
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
		document.getElementById("cvv").innerHTML ="Il CVV può contenere solo numeri e deve averne esattamente 3";
		cvv.focus();
		return false;
	}
}
 