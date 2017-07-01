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
	<br><br><br><br>
	<% if(user != null || admin !=  null) {%>

	<div class="container">
		<h2>effettua prima il logout</h2>
	</div>

	<%}
		else{%>
	<div class="container">
		<script type="text/javascript" src="js/access-validation.js"></script>
		<h1>Login</h1>
		<div class=form-group">
		<form name='login' onSubmit="return loginValidation();"
			action='access' method="post">

			<input type="hidden" name="action" value="login"> 

			<label for="userid">Username:</label>
			<div class="row">
			<input class="col-sm-10" type="text" name="userid" size="35" />
			<span class="col-sm-2" id="userid"></span>
			</div>
			
			<br><label for="passid">Password:</label>
			<div class="row">
			<input class="col-sm-10" type="password" name="passid" size="35" />
			<span class="col-sm-2" id="passid"></span>
			</div>

			<%
				if(request.getAttribute("loginFailed") != null){
			%>
			<br><h5>Parametri errati.</h5>
			<%
				}
			%>

			<br><input name="submit" value="Login" type="submit">
		</form>
		</div>
		<hr>
		<h1>Registration</h1>
		<div class="form-group">
		<form name='registration' onSubmit="return registerValidation();"
			action='access' method="post">

			<input type="hidden" name="action" value="register"> 
			
			<br><label for="user">Username:</label> 
			<div class="row">
			<input class="col-sm-10" type="text" name="user" size="35" />
			<span class="col-sm-2"  id="user"></span><br> 
			</div>	
			
			<br><label for="pass">Password:</label>
			<div class="row">
			<input class="col-sm-10"  type="password" name="pass" size="35" />
			<span class="col-sm-2"  id="pass"></span><br>
			</div>
	
			<br><label for="nome">Nome:</label> 
			<div class="row">
			<input class="col-sm-10"  type="text" name="nome" size="35" />
			<span class="col-sm-2"  id="nome"></span><br> 
			</div>
				
			<br><label for="nome">Cognome:</label>
			<div class="row">
			<input class="col-sm-10"  type="text" name="cognome" size="35" />
			<span class="col-sm-2"  id="cognome"></span><br>
			</div>
			
			<br><label for="nascita">Data di nascita:</label> 
			<div class="row">
			<input class="col-sm-10"  type="date" name="nascita" size="35" />
			<span class="col-sm-2"  id="nascita"></span><br> 
			</div>	
				
			<br><label for="stato">Stato:</label> 
			<div class="row">	
			<select class="col-sm-10"  name="stato">
				<option selected="" value="Default">(Please select a
					country)</option>
				<option value="AF">Australia</option>
				<option value="AL">Canada</option>
				<option value="IT">Italia</option>
				<option value="AS">Russia</option>
				<option value="AD">USA</option>
			</select>
			<span class="col-sm-2"  id="stato"></span><br> 
			</div>
			
			
			<br><label  for="cap">CAP:</label> 
			<div class="row">
			<input class="col-sm-10"  type="text" name="cap" />
			<span class="col-sm-2"  id="cap"></span><br> 
			</div>
			
			<br><label for="indirizzo">Indirizzo:</label> 
			<div class="row">
			<input class="col-sm-10"  type="text" name="indirizzo" size="35" />
			<span class="col-sm-2"  id="indirizzo"></span><br>
			</div>
			
			<br><label for="email">Email:</label> 
			<div class="row">
			<input class="col-sm-10"  type="text" name="email" size="35" />
			<span class="col-sm-2"  id="email"></span><br> 
			</div>
			
			<br><input name="submit" value="Register" type="submit">

		</form>
		</div>
	</div>
	<% } %>
</body>
</html>