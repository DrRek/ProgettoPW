function registerValidation()
{
	var uid = $("input[name='user']");
	var pass = $("input[name='pass']");
	var uname = $("input[name='nome']");
	var ulastname =  $("input[name='cognome']");
	var uadd =  $("input[name='indirizzo']");
	var ucountry =  $("select[name='stato']");
	var ucap =  $("input[name='cap']");
	var uemail =  $("input[name='email']");

	if(user_validation(uid,5,12) && pass_validation(pass,7,12) && allLetter(uname) && allLetter(ulastname) 
			&& alphanumeric(uadd) && countryselect(ucountry) && allnumeric(ucap) && ValidateEmail(uemail))
		return true;
	else 
		return false;
}

function loginValidation(){
	var uid = $("input[name='userid']");
	var pass = $("input[name='passid']");
	if(userid_validation(uid,5,12) && passid_validation(pass,7,12))
		return true;
	else 
		return false;
}

function userid_validation(uid,mx,my){
	var uid_len = uid.val().length;
	if (uid_len == 0 || uid_len >= my || uid_len < mx)
	{
		$("#userid").html("This username is not valid!");
		uid.focus();
		return false;
	}
	{
		$("#userid").empty();
		return true;
	}
}

function passid_validation(pass,mx,my)
{
	var pass_len = pass.val().length;
	if (pass_len == 0 ||pass_len >= my || pass_len < mx)
	{
		$("#passid").html("This password is not valid!");
		pass.focus();
		return false;
	}
	else{
		$("#passid").empty();
		return true;
	}
}

function user_validation(uid,mx,my)
{
	var uid_len = uid.val().length;
	if (uid_len == 0 || uid_len >= my || uid_len < mx)
	{
		$("#user").html("Username non dovrebbe essere vuoto, lunghezza tra "+mx+" e "+my);
		uid.focus();
		return false;
	}
	{
		$("#user").empty();
		return true;
	}
}
function pass_validation(pass,mx,my)
{
	var pass_len = pass.val().length;
	if (pass_len == 0 ||pass_len >= my || pass_len < mx)
	{
		$("#pass").html("Password non dovrebbe essere vuoto, lunghezza tra "+mx+" e "+my);
		pass.focus();
		return false;
	}
	else{
		$("#pass").empty();
		return true;
	}
}
function allLetter(uname)
{ 
	var letters = /^[A-Za-z]+$/;
	if(uname.val().match(letters))
	{
		$("#nome").empty();
		return true;
	}
	else
	{
		$("#nome").html("È consentito usare solo caratteri alfabetici");
		uname.focus();
		return false;
	}
}
function alphanumeric(uadd)
{ 
	var letters = /^[0-9a-zA-Z ]+$/;
	if(uadd.val().match(letters))
	{
		$("#indirizzo").empty();
		return true;
	}
	else
	{
		$("#indirizzo").html("L'indirizzo può solo contenere caratteri alfanumerici");
		uadd.focus();
		return false;
	}
}
function countryselect(ucountry)
{
	if(ucountry.val() == "Default")
	{
		$("#stato").html("Seleziona il tuo paese dalla lista");
		ucountry.focus();
		return false;
	}
	else{
		$("#stato").empty();
		return true;
	}
}
function allnumeric(ucap)
{ 
	var numbers = /^[0-9]+$/;
	if(uzip.val().match(numbers))
	{
		$("#cap").empty();
		return true;
	}
	else
	{
		$("#cap").html("Il codice postale può contenere solo numeri");
		ucap.focus();
		return false;
	}
}
function ValidateEmail(uemail)
{
	var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
	if(uemail.val().match(mailformat))
	{
		$("#email").empty();
		return true;
	}
	else
	{
		$("#email").html("Email non valida");
		return false;
	}
} 