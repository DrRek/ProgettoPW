<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	UserBean user = (UserBean) request.getSession().getAttribute("user");
	AdminBean admin = (AdminBean) request.getSession().getAttribute("admin");
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

<link href="css/bootstrap.css" type="text/css" rel="stylesheet"
	media="screen,projection" />

<!--Let browser know website is optimized for mobile-->
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<script	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

</head>

<body>
	<%@ include file="header.jsp"%>
	<% if(user != null || admin !=  null) {%>

	<div class="container">
		<h2>effettua prima il logout</h2>
	</div>

	<%}
		else{%>
	<div class="container">
		<script type="text/javascript" src="js/access-validation.js"></script>
		<h1>Login</h1>
		<form name='login' onSubmit="return loginValidation();"
			action='access' method="post">

			<input type="hidden" name="action" value="login"> <label
				for="userid">Username:</label> <input type="text" name="userid"
				size="12" /><span id="userid"></span><br> <label for="passid">Password:</label>
			<input type="password" name="passid" size="12" /><span id="passid"></span><br>

			<%
				if(request.getAttribute("loginFailed") != null){
			%>
			<h6>Parametri errati.</h6>
			<%
				}
			%>

			<input name="submit" value="Login" type="submit">

		</form>
		<h1>Registration</h1>
		<form name='registration' onSubmit="return registerValidation();"
			action='access' method="post">

			<input type="hidden" name="action" value="register"> 
			
			<label for="user">Username:</label> <input type="text" name="user"
				size="12" /><span id="user"></span><br> 
				
			<label for="pass">Password:</label>
			<input type="password" name="pass" size="12" /><span id="pass"></span><br>

			<label for="nome">Nome:</label> <input type="text" name="nome"
				size="50" /><span id="nome"></span><br> 
				
			<label for="nome">Cognome:</label>
			<input type="text" name="cognome" size="50" /><span id="cognome"></span><br>

			<label for="nascita">Data di nascita:</label> <input type="date"
				name="nascita" size="50" /><span id="nascita"></span><br> 
				
			<label for="stato">Stato:</label> <select name="stato">
				<option selected="" value="Default">(Please select a
					country)</option>
				<option value="AF">Australia</option>
				<option value="AL">Canada</option>
				<option value="IT">Italia</option>
				<option value="AS">Russia</option>
				<option value="AD">USA</option>
			</select><span id="stato"></span><br> 
			
			<label for="cap">CAP:</label> <input
				type="text" name="cap" /><span id="cap"></span><br> 
			
			<label for="indirizzo">Indirizzo:</label> <input type="text"
				name="indirizzo" size="50" /><span id="indirizzo"></span><br>

			<label for="email">Email:</label> <input type="text" name="email"
				size="50" /><span id="email"></span><br> <input name="submit"
				value="Register" type="submit">

		</form>
	</div>
	<% } %>
</body>
</html>