<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	Collection<?> products = (Collection<?>) request.getAttribute("articoli");
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

<%@ include file="header.jsp" %>

	<div class="full-panel" id="home">

		<div id="copertina">
			<center>quattrocchi.it</center>
		</div>

	</div>

	<div class="my-container">

		<div class="row">

			<div class="col-md-12 full-panel intro">
				<div class="in-panel" id="intro">
					<h2>quattrocchi.it</h2>
					<p>
						<strong>un rivoluzionario sito di ecommerce nel mondo
							dell'ottica</strong> <br> <br> With a population of 3.3 million
						people, Berlin is Germany's largest city and is the second most
						populous city proper and the seventh most populous urban area in
						the European Union. <br> <br> Located in northeastern
						Germany on the River Spree, it is the center of the
						Berlin-Brandenburg Metropolitan Region, which has about 4½ million
						residents from over 180 nations. <br> <br> Due to its
						location in the European Plain, Berlin is influenced by a
						temperate seasonal climate. Around one third of the city's area is
						composed of forests, parks, gardens, rivers and lakes. <br />
						<cite><a href="http://en.wikipedia.org/wiki/Berlin">Wikipedia</a></cite>
					</p>
				</div>
			</div>


			<div class="col-md-12 full-panel people">
				<div class="in-panel" id="people"></div>
			</div>


			<div class="col-md-12 full-panel work">
				<div class="in-panel" id="work">
					<h2>04. Work</h2>
					<p>
						<strong>Industries that do business in the creative arts
							and entertainment are an important and sizable sector of the
							economy of Berlin.</strong> <br> <br> The creative arts sector
						comprises music, film, advertising, architecture, art, design,
						fashion, performing arts, publishing, RD, software, TV, radio,
						and video games. <br> <br> Around 22,600 creative
						enterprises, predominantly SMEs, generated over 18,6 billion Euro
						in total revenue. Berlin's creative industries have contributed an
						estimated 20% of Berlin's gross domestic product in 2005. <br />
						<cite><a href="http://en.wikipedia.org/wiki/Berlin">Wikipedia</a></cite>
					</p>
				</div>
			</div>


			<div class="col-md-12 full-panel street">
				<div class="in-panel" id="street"></div>
			</div>

		</div>



	</div>
	<!-- /.container -->


	<h2>Products</h2>
	<a href="article">List</a>
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
			<td><a href="product?action=delete&id=<%=bean.getNome()%>">Delete</a><br>
				<a href="product?action=read&id=<%=bean.getNome()%>">Details</a></td>
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
	<h2>Details</h2>
	<table class="table-bordered">
		<thead>
			<tr>
				<th>Codice articolo <a href="product?sort=code">Sort</a></th>
				<th>marca <a href="article?sort=name">Sort</a></th>
				<th>prezzo <a href="articlet?sort=description">Sort</a></th>
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
	<form action="article" method="post">
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
	<script src="http://code.jquery.com/jquery-1.9.1.min.js"
		type="text/javascript"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>