<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	Cart cart = (Cart) request.getSession().getAttribute("cart");
%>

<!DOCTYPE html>
<html>
<%@ page contentType="text/html; charset=UTF-8"
	import="java.util.*,it.quattrocchi.ArticleBean, it.quattrocchi.*"%>
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
	<h2>Cart</h2>
	<table class="table-bordered">
		<tr>
			<th>Nome</th>
			<th>Marca</th>
			<th>Numero Prodotti</th>
			<th>Prezzo</th>
		</tr>
		<% List<CartArticle> prodcart = cart.getProducts(); 	
		   for(CartArticle beancart : prodcart) {
		%>
		<tr>
			<td><%=beancart.getArticle().getNome()%></td>
			<td><%=beancart.getArticle().getMarca()%></td>
			<!-- non ancora logicamente corretto -->
			<td><input name="numeroPezziDisponibili" type="number" min="1" value ="<%=beancart.getQuantity()%>"></td>
			<td><%=beancart.getPrezzo()%>€</td>
		</tr>
		<%} %>
	</table>
	<h3>Prezzo totale: <%=cart.getPrezzo()%>€</h3>
	<input type="button" value="Completa il pagamento!">
		
</div>
</body>
</html>