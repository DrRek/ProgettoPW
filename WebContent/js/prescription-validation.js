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
	
	if(isNumber(sferaSX,-10,10, "#sferaSX") && isNumber(sferaDX,-10,10,"#sferaDX") && isNumber(asseSX,0,180, "#asseSX")
			&& isNumber(asseDX,0,180,"#asseDX") && isNumber(cilindroSX,-10,10, "#cilindroSX") && isNumber(cilindroDX,-10,10, "#cilindroDX")
			&& isNumber(addVicinanza,-10,10, "#addVicinanza") && isNumber(prismaOrizSX,-10,10, "#prismaOrizSX") && isNumber(prismaOrizDX,-10,10, "#prismaOrizDX")
			&& isNumber(prismaVertSX,-10,10, "#prismaVertSX") && isNumber(prismaVertDX,-10,10, "#prismVertDX") && isNumber(pdSX,-10,10, "#pdSX")
			&& isNumber(pdDX,-10,10, "#pdDX") && allLetter(tipoP, "#tipoP") && allLetter(prismaOrizSXBD, "#prismaOrizSXBD")
			&& allLetter(prismaOrizDXBD, "#prismaOrizDXBD")  && allLetter(prismaVertSXBD, "#prismaVertSXBD")  && allLetter(prismaVertDXBD, "#prismaVertDXBD"))
		return true;
	else
		return false;
}

function isNumber(input, min, max, id)
{
	var numbers = /^[0-9]+$/;
	if(uzip.val().match(numbers))
	{
		if(input >= min && input <= max)
			return true
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
		return true;
	else
	{
		$(id).html("È consentito usare solo caratteri alfabetici");
		input.focus();
		return false;
	}
}