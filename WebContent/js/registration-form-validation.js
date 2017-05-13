function formValidation()
{
	var uid = document.registration.userid;
	var passid = document.registration.passid;
	var uname = document.registration.nome;
	var ulastname = document.registration.cognome;
	var uadd = document.registration.indirizzo;
	var ucountry = document.registration.stato;
	var uzip = document.registration.zip;
	var uemail = document.registration.email;

	if(userid_validation(uid,5,12))
	{
		if(passid_validation(passid,7,12))
		{
			if(allLetter(uname))
			{
				if(allLetter(ulastname))
				{
					if(alphanumeric(uadd))
					{ 
						if(countryselect(ucountry))
						{
							if(allnumeric(uzip))
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

function userid_validation(uid,mx,my)
{
	var uid_len = uid.value.length;
	if (uid_len == 0 || uid_len >= my || uid_len < mx)
	{
		document.getElementById("userid").innerHTML = "Username non dovrebbe essere vuoto, lunghezza tra "+mx+" e "+my;
		uid.focus();
		return false;
	}
	return true;
}
function passid_validation(passid,mx,my)
{
	var passid_len = passid.value.length;
	if (passid_len == 0 ||passid_len >= my || passid_len < mx)
	{
		document.getElementById("passid").innerHTML = "Password non dovrebbe essere vuoto, lunghezza tra "+mx+" e "+my;
		passid.focus();
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
function allnumeric(uzip)
{ 
	var numbers = /^[0-9]+$/;
	if(uzip.value.match(numbers))
	{
		return true;
	}
	else
	{
		document.getElementById("zip").innerHTML ="Il codice postale può contenere solo numeri";
		uzip.focus();
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