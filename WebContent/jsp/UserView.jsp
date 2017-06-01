<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	UserBean user = (UserBean) request.getSession().getAttribute("user");
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
		if (user == null) {
	%>
	<div class="container">
		<h1>
			<a href="article">Quattrocchi.it</a>
		</h1>

		<a href="access">Login is required!</a>
	</div>
	<%
		} else {
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
				<th>Circuito</th>
				<th>Scadenza</th>
				<th>Opzioni</th>
			</thead>
			<tr>
				<td>4534-23423-23423-2342</td>
				<td>Lucareccia express</td>
				<td>20/20</td>
				<td><a>modifica</a> <a>rimuovi</a></td>
			</tr>
		</table>
	</div>
	<div class="container">
	<h2>Aggiungi carta</h2>
	</div>
	
	<%
		}
	%>
</body>
</html>