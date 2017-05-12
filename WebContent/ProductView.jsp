<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	Collection<?> products = (Collection<?>) request.getAttribute("articoli");
	Cart cart = (Cart) request.getAttribute("cart");
%>

<!DOCTYPE html>
<html>
<%@ page contentType="text/html; charset=UTF-8"
	import="java.util.*,it.quattrocchi.ArticleBean, it.quattrocchi.Cart"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>quattrocchi.it</title>

<!-- CSS  -->
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">

<link href="css/bootstrap.css" type="text/css" rel="stylesheet"
	media="screen,projection" />

<link href="css/style.css" type="text/css" rel="stylesheet"
	media="screen,projection" />

<!--Let browser know website is optimized for mobile-->
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
</head>

<body>
<h2>Products</h2>
	<table class="table-bordered">
		<thead>
			<tr>
				<th>Nome</th>
				<th>Tipo</th>
				<th>Marca</th>
				<th>Prezzo</th>
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
<<<<<<< HEAD
<<<<<<< HEAD
				<a href="article?cart=add&nome=<%=bean.getNome()%>&marca=<%=bean.getMarca()%>">Aggiungi</a>
			</td>
=======
				<a href="product?action=read&nome=<%=bean.getNome()%>">Details</a></td>
>>>>>>> parent of 984bd74... aggiunto il sort by ecc.
=======
				<a href="product?action=read&nome=<%=bean.getNome()%>">Details</a></td>
>>>>>>> parent of 984bd74... aggiunto il sort by ecc.
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
	
	<% if(cart != null) { %>
		<h2>Cart</h2>
		<table>
		<tr>
			<th>Name</th>
			<th>Action</th>
		</tr>
		<% List<ArticleBean> prodcart = cart.getProducts(); 	
		   for(ArticleBean beancart: prodcart) {
		%>
		<tr>
			<td><%=beancart.getNome()%></td>
			<td><a href="product?action=deleteC&nome=<%=beancart.getNome()%>">Delete</a></td>
		</tr>
		<%} %>
	</table>		
	<% } %>	
	
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
		
		<label for="gradazione">Grazione:</label><br> 
		<input name="gradazione" type="number" min="-20,00" value="0,00"><br>

		<input type="submit" value="Send"><input type="reset" value="Reset">

	</form>
</body>
</html>