<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	OrderBean order = (OrderBean) request.getAttribute("order");
%>

<!DOCTYPE html>
<html>
<%@ page contentType="text/html; charset=UTF-8"
	import="java.util.*, it.quattrocchi.control.*, it.quattrocchi.support.*, it.quattrocchi.*, it.quattrocchi.model.*"%>
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

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>

<body>
	<%@ include file="../jsp/header.jsp"%>
	<br><br>
	<div class="container">
		<h2>Codice Ordine: <%=order.getCodice() %></h2>
		<h3>Costo Ordine: <%=order.getCosto() %></h3>
		<h3>Data Esecuzione Ordine: <%=order.getDataEsecuzione() %></h3>
		<h3>Oggetti nell'ordine:</h3><br>
		<table class="table table-condensed">
		<thead>
			<th>Nome Occhiale/Lente</th>
			<th>Marca Occhiale/Lente</th>
			<th>Quantita</th>
		<%  int i = 0;
			ArrayList<CartArticle> ordinati = order.getOrdinati();
			for (i=0; i < ordinati.size(); i++){
		%>
			<tr>
			<td><a href="articlePage?nome=<%=ordinati.get(i).getArticle().getNome()%>&marca=<%=ordinati.get(i).getArticle().getMarca()%>"><%=ordinati.get(i).getArticle().getNome() %></a></td>
			<td><%=ordinati.get(i).getArticle().getMarca() %></td>
			<td><%=ordinati.get(i).getQuantity() %>
			</tr>
		<%
			}
		%>
		</table>
	</div>
</body>
</html>