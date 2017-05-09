<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	Collection<?> products = (Collection<?>) request.getAttribute("products");
	ArticleBean product = (ArticleBean) request.getAttribute("product");
	Cart cart = (Cart) request.getAttribute("cart");
%>

<!DOCTYPE html>
<html>
<%@ page contentType="text/html; charset=UTF-8"
	import="java.util.*,it.quattrocchi.ArticleBean, it.quattrocchi.Cart"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>quattrocchi.it</title>

<!--Import Google Icon Font-->
<link href="http://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
	
<!--Import materialize.css-->
<link type="text/css" rel="stylesheet" href="css/materialize.min.css"
	media="screen,projection" />

<!--Let browser know website is optimized for mobile-->
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
</head>

<body>
 	
 	<!--Import jQuery before materialize.js-->
      <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
      <script type="text/javascript" src="js/materialize.min.js"></script>
	
	<h2>Products</h2>
	<a href="product">List</a>
	<table>
		<thead>
			<tr>
				<th>Codice articolo |Sort</a></th>
				<th>marca |Sort</a></th>
				<th>prezzo|Sort</a></th>
				<th>numero pezzi disponibili</th>
				<th>Action</th>
			</tr>
		</thead>
		<tr>
			<td>(codice articolo)</td>
			<td>(marca)</td>
			<td>(prezzo)</td>
			<td>(numero pezzi disponibili)</td>
			<td>Delete<br> Details
			</td>
		</tr>
		<tr>
			<td colspan="5">(se non ci sono prodotti) No products available</td>
		</tr>
	</table>

	<h2>Details</h2>
	<table>
		<thead>
			<tr>
				<th>Codice articolo <a href="product?sort=code">Sort</a></th>
				<th>marca <a href="product?sort=name">Sort</a></th>
				<th>prezzo <a href="product?sort=description">Sort</a></th>
				<th>numero pezzi disponibili</th>
			</tr>
		</thead>
		<tr>
			<td>(codice articolo)</td>
			<td>(marca)</td>
			<td>(prezzo)</td>
			<td>(numero pezzi disponibili)</td>
		</tr>
	</table>
	<h2>Insert</h2>
	<form method="post">
		<input type="hidden" name="action" value="insert"> <label
			for="code">Code:</label> <br> <input name="code" type="text"
			maxlength="20" required placeholder="enter code"><br> <label
			for="brand">Brand:</label> <br> <input name="brand" type="text"
			maxlength="20" required placeholder="enter brand"><br> <label
			for="price">Price:</label> <br> <input name="price"
			type="number" min="0" value="0" required><br> <label
			for="quantity">Quantity:</label><br> <input name="quantity"
			type="number" min="1" value="1" required><br> <input
			type="submit" value="Send"><input type="reset" value="Reset">

	</form>

	<% if(cart != null) { %>
	<h2>Cart</h2>
	<table>
		<tr>
			<th>Name</th>
			<th>Action</th>
		</tr>
		<% ArrayList<ArticleBean> prodcart = cart.getProducts(); 	
		   for(ArticleBean beancart: prodcart) {
		%>
		<tr>
			<td><%=beancart.getNome()%></td>
			<td><a href="product?action=deleteC&id=<%=beancart.getNome()%>">Delete
					from cart</a></td>
		</tr>
		<%} %>
	</table>
	<% } %>
</body>
</html>