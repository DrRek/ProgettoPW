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
		<div class="form-group">
		<form name='login' onSubmit="return loginValidation();"
			action='access' method="post">

			<input type="hidden" name="action" value="login"> 
			<div class="form-group">
			<br><label for="userid">Username:</label>
			<input class="form-control" type="text" name="userid" size="12" />
			<span class="help-block" id="userid"></span><br>
			</div>
			
			<div class="form-group">
			<label for="passid">Password:</label>
			<input class= "form-control" type="password" name="passid" size="12" />
			<span class="help-block" id="passid"></span><br>
			</div>

			<%
				if(request.getAttribute("loginFailed") != null){
			%>
			<h5>Parametri errati.</h5>
			<%
				}
			%>

			<input class= "btn btn-outline-secondary" name="submit" value="Login" type="submit">
		</form>
		</div>
		<hr>
		<h1>Registration</h1>
		<form name='registration' onSubmit="return registerValidation();"
			action='access' method="post">

			<input type="hidden" name="action" value="register"> 
			
			<div class="form-group">
			<br><label for="user">Username:</label> 
			<input class="form-control" type="text" name="user" size="35" />
			<span class="help-block" id="user"></span><br> 
			</div>	
			
			<div class="form-group">
			<label for="pass">Password:</label>
			<input class="form-control"  type="password" name="pass" size="35" />
			<span class="help-block" id="pass"></span><br>
			</div>
	
			<div class="form-group">
			<label for="nome">Nome:</label> 
			<input class="form-control"  type="text" name="nome" size="35" />
			<span class="help-block"  id="nome"></span><br> 
			</div>
				
			<div class="form-group">
			<br><label for="nome">Cognome:</label>
			<input class="form-control"  type="text" name="cognome" size="35" />
			<span class="help-block" id="cognome"></span><br>
			</div>

			<div class="form-group">			
			<label for="nascita">Data di nascita:</label> 
			<input class="form-control"  type="date" name="nascita" size="35" />
			<span class="help-block" id="nascita"></span><br> 
			</div>	

			<div class="form-group">				
			<label for="stato">Stato:</label> 
			<select class="form-control"  name="stato">
				<option selected="" value="Default">(Please select a
					country)</option>
				<option value="AF">Australia</option>
				<option value="AL">Canada</option>
				<option value="IT">Italia</option>
				<option value="AS">Russia</option>
				<option value="AD">USA</option>
			</select>
			<span class="help-block" id="stato"></span><br> 
			</div>
			
			<div class="form-group">	
			<label  for="cap">CAP:</label> 
			<input class="form-control"  type="text" name="cap" />
			<span class="help-block" id="cap"></span><br> 
			</div>
			
			<div class="form-group">	
			<label for="indirizzo">Indirizzo:</label> 
			<input class="form-control"  type="text" name="indirizzo" size="35" />
			<span class="help-block"  id="indirizzo"></span><br>
			</div>
			
			<div class="form-group">			
			<label for="email">Email:</label> 
			<input class="form-control"  type="text" name="email" size="35" />
			<span class="help-block" id="email"></span><br> 
			</div>
			
			<input class= "btn btn-outline-secondary" name="submit" value="Register" type="submit">

		</form>
	</div>
	<% } %>
</body>
</html>