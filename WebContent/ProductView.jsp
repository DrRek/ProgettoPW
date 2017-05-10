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
    
    <div class="full-panel" id="home">  
    
    	<div id="copertina">
    		<h1>quattrocchi.it</h1>
    	</div>    
    
    </div> 

    <div class="my-container">

			<div class="row">

			  <div class="col-md-12 full-panel intro">
			  	<div class="in-panel" id="intro">
			  		<h2>quattrocchi.it</h2>
			  		<p><strong>un rivoluzionario sito di ecommerce nel mondo dell'ottica</strong> 
			  		<br> <br> With a population of 3.3 million people, Berlin is Germany's largest city and is the second most populous city proper and the seventh most populous urban area in the European Union. 
			  		<br> <br> Located in northeastern Germany on the River Spree, it is the center of the Berlin-Brandenburg Metropolitan Region, which has about 4½ million residents from over 180 nations. 
			  		<br> <br> Due to its location in the European Plain, Berlin is influenced by a temperate seasonal climate. Around one third of the city's area is composed of forests, parks, gardens, rivers and lakes.
			  		<br/><cite><a href="http://en.wikipedia.org/wiki/Berlin">Wikipedia</a></cite></p>
			  	</div>
			  </div>
			  
			  
			  <div class="col-md-12 full-panel people">
			  	<div class="in-panel" id="people">
			  		
			  	</div>
			  </div>
			  		
			
			  <div class="col-md-12 full-panel work">
			  	<div class="in-panel" id="work">
			  		<h2>04. Work</h2>
			  		<p><strong>Industries that do business in the creative arts and entertainment are an important and sizable sector of the economy of Berlin.</strong>  <br> <br> The creative arts sector comprises music, film, advertising, architecture, art, design, fashion, performing arts, publishing, R&D, software, TV, radio, and video games. <br> <br>  Around 22,600 creative enterprises, predominantly SMEs, generated over 18,6 billion Euro in total revenue. Berlin's creative industries have contributed an estimated 20% of Berlin's gross domestic product in 2005.			  		
			  		<br/><cite><a href="http://en.wikipedia.org/wiki/Berlin">Wikipedia</a></cite></p>
			  	</div>
			  </div>
			  		
			  		
			  <div class="col-md-12 full-panel street">
			  	<div class="in-panel" id="street">
			  		
			  	</div>
			  </div>
			  		 
			</div>



    </div><!-- /.container -->



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

	<%
		if (cart != null) {
	%>
	<h2>Cart</h2>
	<table>
		<tr>
			<th>Name</th>
			<th>Action</th>
		</tr>
		<%
			ArrayList<ArticleBean> prodcart = cart.getProducts();
				for (ArticleBean beancart : prodcart) {
		%>
		<tr>
			<td><%=beancart.getNome()%></td>
			<td><a href="product?action=deleteC&id=<%=beancart.getNome()%>">Delete
					from cart</a></td>
		</tr>
		<%
			}
		%>
	</table>
	<%
		}
	%>
	  <script src="http://code.jquery.com/jquery-1.9.1.min.js" type="text/javascript"></script>
    <script src="js/bootstrap.min.js"></script>
</body>
</html>