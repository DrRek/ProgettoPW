<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	Collection<?> products = (Collection<?>) request.getAttribute("articoli");
	Cart cart = (Cart) request.getSession().getAttribute("cart");
%>

<!DOCTYPE html>
<html>
<%@ page contentType="text/html; charset=UTF-8"
	import="java.util.*,it.quattrocchi.control.*, it.quattrocchi.support.*"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>quattrocchi.it</title>

<!-- CSS  -->
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">

<link href="../css/bootstrap.css" type="text/css" rel="stylesheet"
	media="screen,projection" />

<!--Let browser know website is optimized for mobile-->
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
</head>

<body>
<div class="container">
	<h1><a href="article">Quattrocchi.it</a></h1>
	<h1>Login</h1>
	<form name='login' onSubmit="return formValidation();" action='access' method="post">
		<ul>
			<input type="hidden" name="action" value="login"> 
			
			<li><label for="userid">Username:</label></li>
			<li><input type="text" name="userid" size="12" /><span id="userid"></span> </li>
			
			<li><label for="passid">Password:</label></li>
			<li><input type="password" name="passid" size="12" /><span id="passid"></span></li>
			
			<li><input name="submit" value="Login" type="submit"></li>
		</ul>
	</form>
	<h1>Registration</h1>
	<form name='registration' onSubmit="return formValidation();" action='access' method="post">
		<ul>
			<input type="hidden" name="action" value="create"> 
			
			<li><label for="userid">Username:</label></li>
			<li><input type="text" name="userid" size="12" /><span id="userid"></span> </li>
			
			<li><label for="passid">Password:</label></li>
			<li><input type="password" name="passid" size="12" /><span id="passid"></span></li>
			
			<li><label for="nome">Nome:</label></li>
			<li><input type="text" name="nome" size="50" /><span id="nome"></span></li>
			
			<li><label for="nome">Cognome:</label></li>
			<li><input type="text" name="cognome" size="50" /><span id="cognome"></span></li>
			
			<li><label for="nascita">Data di nascita:</label></li>
			<li><input type="date" name="nascita" size="50" /><span id="nascita"></span></li>
			
			<li><label for="stato">Stato:</label></li>
			<li><select name="stato">
					<option selected="" value="Default">(Please select a country)</option>
					<option value="AF">Australia</option>
					<option value="AL">Canada</option>
					<option value="IT">Italia</option>
					<option value="AS">Russia</option>
					<option value="AD">USA</option>
			</select><span id="stato"></span></li>
			
			<li><label for="cap">CAP:</label></li>
			<li><input type="text" name="cap" /><span id="cap"></span></li>
			
			<li><label for="indirizzo">Indirizzo:</label></li>
			<li><input type="text" name="indirizzo" size="50" /><span id="indirizzo"></span></li>
			
			<li><label for="email">Email:</label></li>
			<li><input type="text" name="email" size="50" /><span id="email"></span></li>
		
			<li><input name="submit" value="Register" type="submit"></li>
		</ul>
	</form>
</div>
</body>
</html>