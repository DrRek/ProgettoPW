<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	Collection<?> products = (Collection<?>) request.getAttribute("articoli");
	Cart cart = (Cart) request.getSession().getAttribute("cart");
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
	<%@ include file="header.jsp"%>
	
	<h1><a href="article">Quattrocchi.it</a></h1>
	<h5><a href="access">Login / Register</a></h5>
	<%if (user!=null){ %><h6><a href="user">Benvenuto, <%=user.getUser()%></a></h6><%} %>
	<h2>Products</h2>
		<table class="table-bordered">
			<thead>
				<tr>
					<th><a href="article?sort=nome">Nome</a></th>
					<th><a href="article?sort=tipo">Tipo</a></th>
					<th><a href="article?sort=marca">Marca</a></th>
					<th><a href="article?sort=prezzo">Prezzo</a></th>
					<th>Disponibilità</th>
				</tr>
			</thead>
			<%
				if (products != null && products.size() != 0) {
					Iterator<?> it = products.iterator();
					while (it.hasNext()) {
						ArticleBean bean = (ArticleBean) it.next();
			%>
			<tr>
				<td><%=bean.getNome()%></td>
				<td><%=bean.getTipo()%></td>
				<td><%=bean.getMarca()%></td>
				<td><%=bean.getPrezzo()%></td>
				<td><%=bean.getNumeroPezziDisponibili()%></td>
				<td><a href="article?action=delete&nome=<%=bean.getNome()%>&marca=<%=bean.getMarca()%>">Delete</a><br>
					<a href="article?action=addCart&nome=<%=bean.getNome()%>&marca=<%=bean.getMarca()%>">Add to cart</a></td>
	
			</tr>
			<%
					}
				} else {
			%>
			<tr>
				<td colspan="6">No products available</td>
			</tr>
			<%
				}
			%>
		</table>
	
		<!-- da gestire il caso di eventuali update di prodotti già nel database-->
		
		<h2>Insert</h2>
		<form action="article" method="post">
		
			<input type="hidden" name="action" value="insert"> 
		
			<label for="nome">Nome:</label><br> 
			<input name="nome" type="text" maxlength="40" required placeholder="inserisci il nome"><br> 
			
			<label for="marca">Marca:</label><br>
			<input type="text" name="marca" maxlength="20" required placeholder="inserisci la marca"></input><br>
			
			<label for="tipo">Tipo:</label><br> 
			<select name="tipo">
	  			<option value="Lente a contatto">Lente a contatto</option>
	  			<option value="Occhiale">Occhiale</option>
			</select><br>
	
			<label for="numeroPezziDisponibili">Disponibilità:</label><br> 
			<input name="numeroPezziDisponibili" type="number" min="1" value="1" required><br>
			
			<label for="prezzo">Prezzo:</label><br> 
			<input name="prezzo" type="number" min="0,01" value="0,00" required><br>
			
			<label for="gradazione">Gradazione:</label><br> 
			<input name="gradazione" type="number" min="-20,00" value="0,00"><br>
	
			<input type="submit" value="Send"><input type="reset" value="Reset">
	
		</form>
		
		<% if(cart != null&&!cart.isEmpty()) { %>
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
				
				<td><a href="article?action=delCart&nome=<%=beancart.getArticle().getNome()%>&marca=<%=beancart.getArticle().getMarca()%>">Delete from cart</a></td>
			</tr>
			<%} %>
		</table>
		<h3>Prezzo totale: <%=cart.getPrezzo()%>€</h3>
		<form action='article' method='post'>
			<input type="hidden" name="action" value="checkout"></input>
			<input type="submit" value="Checkout"> 
		</form>	
		<% } %>	
		
</div>
</body>
</html>