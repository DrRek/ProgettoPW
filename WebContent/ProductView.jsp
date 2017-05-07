<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>quattrocchi.it</title>
</head>

<body>
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
		<input type="hidden" name="action" value="insert"> 
		
			<label
			for="code">Codice:</label> <br> <input name="code" type="text"
			maxlength="20" required placeholder="enter code"><br> 
			
			<label
			for="brand">Brand:</label> <br> <input name="brand" type="text"
			maxlength="20" required placeholder="enter brand"><br> 
			
			<label
			for="price">Price:</label> <br> <input name="price"
			type="number" min="0" value="0" required><br> 
			
			<label
			for="quantity">Quantity:</label><br> <input name="quantity"
			type="number" min="1" value="1" required><br> 
			
			<input
			type="submit" value="Send"><input type="reset" value="Reset">

	</form>
</body>
</html>