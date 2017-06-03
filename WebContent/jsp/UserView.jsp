<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	UserBean user = (UserBean) request.getSession().getAttribute("user");
	AdminBean admin = (AdminBean) request.getSession().getAttribute("admin");
%>

<!DOCTYPE html>
<html>
<%@ page contentType="text/html; charset=UTF-8"
	import="java.util.*, it.quattrocchi.control.*, it.quattrocchi.support.*, it.quattrocchi.*"%>
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
</head>

<body>
	<h1>
		<a href="article">Quattrocchi.it</a>
	</h1>
	<%
		if (user == null && admin == null) {
	%>
	<div class="container">
		<a href="access">Login is required!</a>
	</div>
	<%
		} else if (user != null) {
	%>
	<div class="container">

		<h2>
			Ciao,
			<%=user.getUser()%></h2>
		<table class="table-bordered">
			<thead>
				<th colspan="2">I tuoi dati:<br></th>
			</thead>
			<tr>
				<td>Nome:</td>
				<td><%=user.getNome()%></td>
			</tr>
			<tr>
				<td>Cognome:</td>
				<td><%=user.getCognome()%></td>
			</tr>
			<tr>
				<td>Stato:</td>
				<td><%=user.getStato()%></td>
			</tr>
			<tr>
				<td>Cap:</td>
				<td><%=user.getCap()%></td>
			</tr>
			<tr>
				<td>Indirizzo:</td>
				<td><%=user.getIndirizzo()%></td>
			</tr>
			<tr>
				<td>Email:</td>
				<td><%=user.getEmail()%></td>
			</tr>
			<tr>
				<td>Data di nascita:</td>
				<td><%=user.getDataDiNascita()%></td>
			</tr>
		</table>
		<p>
			<a>Modifica</a>&lt-- magari un giorno ci viene voglia di
			implementarlo
		</p>
	</div>
	<div class="container">
		<h2>Carte di credito</h2>
		<table class="table-bordered">
			<thead>
				<th>Numero carta</th>
				<th>Intestatario</th>
				<th>Circuito</th>
				<th>Scadenza</th>
				<th>Opzioni</th>
			</thead>
			<tr>
				<td>4534-23423-23423-2342</td>
				<td>Luca Reccia</td>
				<td>Lucareccia express</td>
				<td>20/20</td>
				<td><a>rimuovi</a></td>
			</tr>
		</table>
	</div>
	<div class="container">
		<h2>Aggiungi carta</h2>
		<form name='cc' onSubmit="return ccValidation();" action='user'
			method="post">
			<!-- 	//ccvalidation() deve ancora essere scritta, su user va ancora
			implementata -->
			<label for="numcc">Numero carta di credito:</label> <input
				name="numcc" type="text" maxlength="50" required
				placeholder="ES. 1234-5678-9012-3456"><br> <label
				for="intestatario">Intestatario:</label> <input name="intestatario"
				type="text" maxlength="50" required
				placeholder="inserisci nome e cognome"><br> <label
				for="circuito">Circuito:</label> <input name="circuito" type="text"
				maxlength="50" required placeholder="ES. Mastercard"><br>
			<label for="scadenza">Scadenza:</label> <input name="scadenza"
				type="text" maxlength="50" required placeholder="ES. 20/20"><br>
			<label for="scadenza">Scadenza:</label> <input name="cvv" type="text"
				maxlength="50" required placeholder="ES. 570"><br> <input
				name="submit" value="Aggiungi" type="submit">

		</form>
	</div>

	<%
		} else if (admin != null) {
	%>
	<div class="container">
		<h2>
			Ciao,
			<%=admin.getUser()%></h2>
		<!-- da gestire il caso di eventuali update di prodotti già nel database-->

		<h3>Insert</h3>
		<form action="user" method="post">

			<input type="hidden" name="user" value="insert"> <label
				for="nome">Nome:</label><br> <input name="nome" type="text"
				maxlength="40" required placeholder="inserisci il nome"><br>

			<label for="marca">Marca:</label><br> <input type="text"
				name="marca" maxlength="20" required
				placeholder="inserisci la marca"></input><br> <label for="tipo">Tipo:</label><br>
			<select name="tipo">
				<option value="Lente a contatto">Lente a contatto</option>
				<option value="Occhiale">Occhiale</option>
			</select><br> <label for="numeroPezziDisponibili">Disponibilità:</label><br>
			<input name="numeroPezziDisponibili" type="number" min="1" value="1"
				required><br> <label for="prezzo">Prezzo:</label><br>
			<input name="prezzo" type="number" min="0,01" value="0,00" required><br>

			<label for="gradazione">Gradazione:</label><br> <input
				name="gradazione" type="number" min="-20,00" value="0,00"><br>

			<input type="submit" value="Send"><input type="reset"
				value="Reset">

		</form>
	</div>
	<%
		}
	%>
</body>
</html>