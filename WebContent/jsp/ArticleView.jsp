<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	Collection<?> products = (Collection<?>) request.getAttribute("articoli");
	String daCercare = (String) request.getAttribute("daCercare");
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

<!--Let browser know website is optimized for mobile-->
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link href="css/example.css" type="text/css" rel="stylesheet"media="screen,projection" >

</head>

<body>
	<%@ include file="../jsp/header.jsp"%>
	
	<!--  search.js gestisce questa parte-->
	<!-- devo ancora ricercare nelle impostazioni iniziali anche in base alla parola cercata e implementare il sort -->
	<div>
		<div id='daMettereASinistra' class='left'>
			<h1>Ricerca avanzata</h1>
			<hr>
			<label for="tipo">Products:</label><br>
			<select name="tipo" onchange="setSearchField()">
					<option selected value="O">Glasses</option>
					<option value="L">Contact lenses</option>
			</select>
			<hr>
			<label for="daCercare1">Name to search for:</label><br>
			<input type="text" name="daCercare1" <%if (daCercare != null) {%> value="<%=daCercare%>" <%}%> />
			<hr>
			<label for="marca">Brand:</label><br>
			<select name="marca">
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
			<hr>
			<label for="prezzoMin">Price:</label><br>
			<div>
				<input type="number" step="1.00" min="0.00" name="prezzoMin" placeholder="min" />
				<input type="number" step="1.00" min="0.00" name="prezzoMax" placeholder="max" />
			</div>
			<div class="specificiPerOcchiali">
				<hr>
				<label class="specificiPerOcchiali" for="sesso">Sex:</label><br>
				<select class="specificiPerOcchiali" name="sesso">
					<option selected value="any">Any</option>
					<option value="M">Man</option>
					<option value="F">Woman</option>
				</select>
				<hr>
				<label class="specificiPerOcchiali" for="colore">Color:</label><br>
				<input class="specificiPerOcchiali" type="text" name="colore" value="" />
			</div>
			<div class="specificiPerLentine">
				<hr>
				<label class="specificiPerLentine" for="gradazione">Gradazione:</label><br>
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
				<hr>
				<label class="specificiPerLentine" for="tipologia">Duration:</label><br>
				<select class="specificiPerLentine" name="tipologia">
					<option selected value="">All</option>
					<option value="G">Daily</option>
					<option value="Q">15-days</option>
					<option value="M">Monthly</option>
				</select>
				<hr>
				<label class="specificiPerLentine" for="raggio">Radius:</label><br>
				<input class="specificiPerLentine" type="number" min="5" name="raggio" placeholder="mm" value="" />
				<hr>
				<label class="specificiPerLentine" for="diametro">Diameter:</label><br>
				<input class="specificiPerLentine" type="number" min="10" name="diametro" placeholder="mm" value="" />
				<hr>
				<label class="specificiPerLentine" for="colore">Color:</label><br>
				<select class="specificiPerLentine" name="colore">
					<option selected value="">All</option>
					<option value="Ve">Green</option>
					<option value="Bl">Blue</option>
					<option value="Ma">Brown</option>
					<option value="Vi">Violet</option>
					<option value="Ro">Red</option>
					<option value="Pa">Special</option>
				</select>
			</div>
			<hr>
			<input id="advancedSearch" type='button' value='Search!' />
		</div>
		<div id="demos" class="container, main"></div>
	</div>
	<script src="js/article.js"></script>
	<script src="js/search-add-validation.js"></script>
</body>

</html>