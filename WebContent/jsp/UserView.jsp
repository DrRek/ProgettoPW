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
<div class="container">
	<h1><a href="article">Quattrocchi.it</a></h1>
	<%if(user==null){%>
		<a href="access">Login is required!</a>
	<%}else{%>
		Ciao, <%=user.getUser()%><br>
		I tuoi dati:<br>
		<ul>
			<li>Nome: <%=user.getNome() %></li>
			<li>Cognome: <%=user.getCognome() %></li>
			<li>Stato: <%=user.getStato() %></li>
			<li>Cap: <%=user.getCap()%></li>
			<li>Indirizzo: <%=user.getIndirizzo() %></li>
			<li>Email: <%=user.getEmail() %></li>
			<li>Data di Nascita: <%=user.getDataDiNascita() %></li>
		</ul>
	<%}%>
</div>
</body>
</html>