function registerValidation()
{
	var uid = document.registration.user;
	var pass = document.registration.pass;
	var uname = document.registration.nome;
	var ulastname = document.registration.cognome;
	var uadd = document.registration.indirizzo;
	var ucountry = document.registration.stato;
	var ucap = document.registration.cap;
	var uemail = document.registration.email;

	if(user_validation(uid,5,12))
	{
		if(pass_validation(pass,7,12))
		{
			if(allLetter(uname))
			{
				if(allLetter(ulastname))
				{
					if(alphanumeric(uadd))
					{ 
						if(countryselect(ucountry))
						{
							if(allnumeric(ucap))
							{
								if(ValidateEmail(uemail))
								{
									return true;
								} 
							}
						} 
					}
				}
			}
		}
	}
	return false;
} 

function user_validation(uid,mx,my)
{
	var uid_len = uid.value.length;
	if (uid_len == 0 || uid_len >= my || uid_len < mx)
	{
		document.getElementById("user").innerHTML = "Username non dovrebbe essere vuoto, lunghezza tra "+mx+" e "+my;
		uid.focus();
		return false;
	}
	return true;
}
function pass_validation(pass,mx,my)
{
	var pass_len = pass.value.length;
	if (pass_len == 0 ||pass_len >= my || pass_len < mx)
	{
		document.getElementById("pass").innerHTML = "Password non dovrebbe essere vuoto, lunghezza tra "+mx+" e "+my;
		pass.focus();
		return false;
	}
	return true;
}
function allLetter(uname)
{ 
	var letters = /^[A-Za-z]+$/;
	if(uname.value.match(letters))
	{
		return true;
	}
	else
	{
		document.getElementById("nome").innerHTML = "È consentito usare solo caratteri alfabetici";
		uname.focus();
		return false;
	}
}
function alphanumeric(uadd)
{ 
	var letters = /^[0-9a-zA-Z]+$/;
	if(uadd.value.match(letters))
	{
		return true;
	}
	else
	{
		document.getElementById("indirizzo").innerHTML ="L'indirizzo può solo contenere caratteri alfanumerici";
		uadd.focus();
		return false;
	}
}
function countryselect(ucountry)
{
	if(ucountry.value == "Default")
	{
		document.getElementById("stato").innerHTML ="Seleziona il tuo paese dalla lista";
		ucountry.focus();
		return false;
	}
	else
	{
		return true;
	}
}
function allnumeric(ucap)
{ 
	var numbers = /^[0-9]+$/;
	if(uzip.value.match(numbers))
	{
		return true;
	}
	else
	{
		document.getElementById("cap").innerHTML ="Il codice postale può contenere solo numeri";
		ucap.focus();
		return false;
	}
}
function ValidateEmail(uemail)
{
	var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
	if(uemail.value.match(mailformat))
	{
		return true;
	}
	else
	{
		document.getElementById("email").innerHTML ="Email non valida";
		return false;
	}
} 