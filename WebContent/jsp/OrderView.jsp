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

<link href="css/UserView.css" type="text/css" rel="stylesheet"
	media="screen,projection" />
<link href="css/bootstrap.css" type="text/css" rel="stylesheet"
	media="screen,projection" />

<!--Let browser know website is optimized for mobile-->
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>

<body>
	<%@ include file="../jsp/header.jsp"%>
	<br><br><br><br>
	<div class="container">
		<div><span style="font-size:35px">Ordine </span><span style="font-size:25px">"<%=order.getCodice() %>"</span></div>
		<br>
		<div class="row">
		<div class="col-sm-4">
		<h3>Totale: <%=order.getCosto() %> €</h3>
		</div>
		<div class="col-sm-4">
		<h3>Data Esecuzione: <%=order.getDataEsecuzione() %></h3>
		</div>
		<div class="col-sm-4">
		<h3>Acquisto di: <%=order.getCliente().getUser() %></h3>
		</div>
		</div><hr>
		<h3>Oggetti nell'ordine:</h3><br>
		<table class="table table-condensed">
		<thead><tr>
			<th>Nome Prodotto</th>
			<th>Marca Prodotto</th>
			<th>Quantita</th></tr></thead><tbody>
		<%  int i = 0;
			ArrayList<CartArticle> ordinati = order.getOrdinati();
			for (i=0; i < ordinati.size(); i++){
		%>
			<tr>
			<td data-th="Nome Prodotto"><a href="articlePage?nome=<%=ordinati.get(i).getArticle().getNome()%>&marca=<%=ordinati.get(i).getArticle().getMarca()%>"><%=ordinati.get(i).getArticle().getNome() %></a></td>
			<td data-th="Marca Prodotto"><%=ordinati.get(i).getArticle().getMarca() %></td>
			<td data-th="Quantita"><%=ordinati.get(i).getQuantity() %>
			</tr>
		<%
			}
		%>
		</tbody>
		</table>
	</div>
</body>
</html>