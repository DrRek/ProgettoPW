<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>JavaScript Form Validation using a sample registration
	form</title>

<link rel='stylesheet' href='css/bootstrap.css' type='text/css' />

<script src="js/registration-form-validation.js"></script>

</head>

<body onLoad="document.registration.userid.focus();">
	<h1>Registration Form</h1>
	<p>Use tab keys to move from one input field to the next.</p>
	<form name='registration' onSubmit="return formValidation();">
		<ul>
		
			<li><label for="userid">Username:</label></li>
			<li><input type="text" name="userid" size="12" /><span id="userid"></span> </li>
			
			<li><label for="passid">Password:</label></li>
			<li><input type="password" name="passid" size="12" /><span id="passid"></span></li>
			
			<li><label for="nome">Nome:</label></li>
			<li><input type="text" name="nome" size="50" /><span id="nome"></span></li>
			
			<li><label for="nome">Cognome:</label></li>
			<li><input type="text" name="cognome" size="50" /><span id="cognome"></span></li>
			
			<li><label for="indirizzo">Indirizzo:</label></li>
			<li><input type="text" name="indirizzo" size="50" /><span id="indirizzo"></span></li>
			
			<li><label for="stato">Stato:</label></li>
			<li><select name="stato">
					<option selected="" value="Default">(Please select a
						country)</option>
					<option value="AF">Australia</option>
					<option value="AL">Canada</option>
					<option value="IT">Italia</option>
					<option value="AS">Russia</option>
					<option value="AD">USA</option>
			</select><span id="stato"></span></li>
			
			<li><label for="zip">ZIP Code:</label></li>
			<li><input type="text" name="zip" /><span id="zip"></span></li>
			
			<li><label for="email">Email:</label></li>
			<li><input type="text" name="email" size="50" /><span id="email"></span></li>
		
			<li><input name="submit" value="Submit" type="submit"></li>
		</ul>
	</form>
</body>
</html>
