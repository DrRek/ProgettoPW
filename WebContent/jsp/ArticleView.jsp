<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String daCercare = (String) request.getAttribute("daCercare");
	String cercaPerTipo = (String) request.getAttribute("cercaPerTipo");
	Cart cart = (Cart) request.getSession().getAttribute("cart");
	UserBean user = (UserBean) request.getSession().getAttribute("user");
	AdminBean admin = (AdminBean) request.getSession().getAttribute("admin");
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

<link href="css/ArticleView.css" type="text/css" rel="stylesheet"
	media="screen,projection" />

<!--Let browser know website is optimized for mobile-->
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>


</head>

<body>
	<%@ include file="../jsp/header.jsp"%>
	<script>
		$(document).ready(function() {
			$("#azione").click(function() {
				$("#show").slideToggle();
				if ($("#azione").html() == "mostra")
					$("#azione").html("nascondi");
				else
					$("#azione").html("mostra");

			});
		});
	</script>
	<!--  search.js gestisce questa parte-->
	<br>
	<br>
	<br>
	<br>
	<div class="row container-fluid">
		<!-- devo ancora ricercare nelle impostazioni iniziali anche in base alla parola cercata e implementare il sort -->
		<div id='daMettereASinistra' class='col-sm-4 col-md-3 col-lg-2 ' >
			<div align="center">
				<h3 >Advanced Search</h3>
				<button class="btn btn-outline-secondary" id="azione">nascondi</button>
			</div>
			<hr>
			

  			 
			<div id="show" style="padding: 15px;">
			 <form>
			 <div class="form-group">
				<h4>Products:</h4>
				<select class="form-control" name="tipo" onchange="setSearchField()">
					<option <%if (cercaPerTipo == null || cercaPerTipo.equals("O")) {%>
						selected <%}%> value="O">Glasses</option>
					<option <%if (cercaPerTipo != null && cercaPerTipo.equals("L")) {%>
						selected <%}%> value="L">Contact lenses</option>
				</select>
				</div>
				<hr>
				<div class="form-group">
				<h4>Name to search for:</h4>
				<input type="text" class="form-control" name="daCercare1" <%if (cercaPerTipo != null) {%>
					value="cercaPerTipo" <%} else if (daCercare != null) {%>
					value="<%=daCercare%>" <%}%> />
				</div>
				<hr>
				<div class="form-group">
				<h4>Brand:</h4>
				<select class="form-control" name="marca">
					<option selected value="">All</option>
					<option class="specificiPerOcchiali" value="GreenVision">GreenVision</option>
					<option class="specificiPerOcchiali" value="Lindberg">Lindberg</option>
					<option class="specificiPerOcchiali" value="Oakley">Oakley</option>
					<option class="specificiPerOcchiali" value="Persol">Persol</option>
					<option class="specificiPerOcchiali" value="RayBan">RayBan</option>

					<option class="specificiPerLentine" value="Acuvue">Acuvue</option>
					<option class="specificiPerLentine" value="Alcon">Alcon</option>
					<option class="specificiPerLentine" value="Biotrue">Biotrue</option>
					<option class="specificiPerLentine" value="Frequency">Frequency</option>
					<option class="specificiPerLentine" value="GreenVision">GreenVision</option>
				</select>
				</div>
				<hr>
				<h4>Price:</h4>
				<div>
					<input class="form-control" type="number" step="1.00" min="0.00" name="prezzoMin"
						placeholder="min" /><br> <input class="form-control" type="number" step="1.00"
						min="0.00" name="prezzoMax" placeholder="max" />
				</div>
				<div class="specificiPerOcchiali">
					<hr>
					<div class="form-group">
					<h4 class="specificiPerOcchiali">Sex:</h4>
					<select class="specificiPerOcchiali form-control" name="sesso">
						<option selected value="any">Any</option>
						<option value="M">Man</option>
						<option value="F">Woman</option>
					</select>
					</div>
					<hr>
					<div class="form-group">
					<h4 class="specificiPerOcchiali">Color:</h4>
					<input class="specificiPerOcchiali form-control" type="text" name="colore"
						value="" />
					</div>
				</div>
				<div class="specificiPerLentine">
					<hr>
					<div class="form-group">
					<h4 class="specificiPerLentine form-control">Gradazione:</h4>
					<select class="specificiPerLentine" name="gradazione">
						<option value="+8.00">+8.00</option>
						<option value="+7.50">+7.50</option>
						<option value="+7.00">+7.00</option>
						<option value="+6.50">+6.50</option>
						<option value="+6.00">+6.00</option>
						<option value="+5.50">+5.50</option>
						<option value="+5.00">+5.00</option>
						<option value="+4.50">+4.50</option>
						<option value="+4.00">+4.00</option>
						<option value="+3.50">+3.50</option>
						<option value="+3.00">+3.00</option>
						<option value="+2.50">+2.50</option>
						<option value="+2.00">+2.00</option>
						<option value="+1.50">+1.50</option>
						<option value="+1.00">+1.00</option>
						<option value="+0.50">+0.50</option>
						<option selected value="">All</option>
						<option value="0">±0.00</option>
						<option value="-0.50">-0.50</option>
						<option value="-1.00">-1.00</option>
						<option value="-1.50">-1.50</option>
						<option value="-2.00">-2.00</option>
						<option value="-2.50">-2.50</option>
						<option value="-3.00">-3.00</option>
						<option value="-3.50">-3.50</option>
						<option value="-4.00">-4.00</option>
						<option value="-4.50">-4.50</option>
						<option value="-5.00">-5.00</option>
						<option value="-5.50">-5.50</option>
						<option value="-6.00">-6.00</option>
						<option value="-6.50">-6.50</option>
						<option value="-7.00">-7.00</option>
						<option value="-8.50">-7.50</option>
						<option value="-8.00">-8.00</option>
					</select>
					</div>
					<hr>
					<div class="form-group">
					<h4 class="specificiPerLentine">Duration:</h4>
					<select class="specificiPerLentine form-control" name="tipologia">
						<option selected value="">All</option>
						<option value="G">Daily</option>
						<option value="Q">15-days</option>
						<option value="M">Monthly</option>
					</select>
					</div>
					<hr>
					<div class="form-group">
					<h4 class="specificiPerLentine" for="raggio">Radius:</h4>
					<input class="specificiPerLentine form-control" type="number" min="5"
						name="raggio" placeholder="mm" value="" />
					</div>
					<hr>
					<div class="form-group">
					<h4 class="specificiPerLentine ">Diameter:</h4>
					<input class="specificiPerLentine form-control" type="number" min="10"
						name="diametro" placeholder="mm" value="" />
					</div>
					<hr>					
					<div class="form-group">
					<h4 class="specificiPerLentine">Color:</h4>
					<select class="specificiPerLentine form-control" name="colore">
						<option selected value="">All</option>
						<option value="Ve">Green</option>
						<option value="Bl">Blue</option>
						<option value="Ma">Brown</option>
						<option value="Vi">Violet</option>
						<option value="Ro">Red</option>
						<option value="Pa">Special</option>
					</select>
					</div>
				</div>
				<hr>
				<input class="form-control" id="advancedSearch" type='button' value='Search!' />
				</form>
			</div>
		</div>

		<div class="container-fluid col-sm-8 col-md-9 col-lg-10 products"></div>
		<h5 style="padding:20px">order by <a href="#" onclick="return orderByName()">Nome</a> <a
				href="#" onclick="return orderByMarca()">Marca</a> <a href="#"
				onclick="return orderByTipo()">Tipo</a> <a href="#"
				onclick="return orderByPrezzo()">Prezzo</a>
		</h5>
		<div id="demos" class="products"></div>
	</div>
	<%@ include file="../jsp/footer.jsp"%>
	<script src="js/article.js"></script>

</body>
</html>