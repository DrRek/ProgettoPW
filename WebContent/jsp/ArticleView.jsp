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

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

</head>

<body>
	<%@ include file="../jsp/header.jsp"%>

	<!--  search.js gestisce questa parte-->
	<!-- devo ancora ricercare nelle impostazioni iniziali anche in base alla parola cercata e implementare il sort -->
	<div>
		<div id='daMettereASinistra'>
			<select name="tipo">
					<option selected value="O">Occhiale</option>
					<option value="L">Lenti a contatto</option>
			</select>
			<label for="daCercare1">Da cercare:</label>
			<input type="text" name="daCercare1" <%if (daCercare != null) {%> value="<%=daCercare%>" <%}%> />
			<label for="marca">Marca:</label>
			<select name="marca">
				<option selected value="">Tutte</option>
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
			<label for="prezzoMin">Prezzo:</label>
			<div>
				<input type="number" step="1.00" min="0" name="prezzoMin" placeholder="min" />
				<input type="number" step="1.00" min="0" name="prezzoMax" placeholder="max" />
			</div>
			
			<label class="specificiPerOcchiali" for="sesso">Sesso:</label>
			<select class="specificiPerOcchiali" name="sesso">
				<option selected value="any">Any</option>
				<option value="M">Man</option>
				<option value="F">Woman</option>
			</select>
			<label class="specificiPerOcchiali" for="colore">Colore:</label>
			<input class="specificiPerOcchiali" type="text" name="colore" value="" />
			
			<label class="specificiPerLentine" for="gradazione">Gradazione:</label>
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
				<option selected value="">Tutte</option>
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
			<label class="specificiPerLentine" for="tipologia">Tipo:</label>
			<select class="specificiPerLentine" name="tipologia">
				<option selected value="">Tutte</option>
				<option value="G">Giornaliere</option>
				<option value="Q">Quindicinali</option>
				<option value="M">Mensili</option>
			</select>
			<label class="specificiPerLentine" for="raggio">Raggio:</label> 
			<input class="specificiPerLentine" type="number" min="5" name="raggio" placeholder="mm" value="" />
			<label class="specificiPerLentine" for="diametro">Diametro:</label>
			<input class="specificiPerLentine" type="number" min="10" name="diametro" placeholder="mm" value="" />
			<label class="specificiPerLentine" for="colore">Colore:</label>
			<select class="specificiPerLentine" name="colore">
				<option selected value="">Tutte</option>
				<option value="Ve">Verdi</option>
				<option value="Bl">Blu</option>
				<option value="Ma">Marroni</option>
				<option value="Ve">Verdi</option>
				<option value="Vi">Viola</option>
				<option value="Ro">Rosso</option>
				<option value="Pa">Particolari</option>
			</select>
			<input id="advancedSearch" type='button' value='Search!' />
		</div>
		<div id="demos" class="container"></div>
	</div>
	<script src="js/article.js"></script>
	<script src="js/search-add-validation.js"></script>
</body>

</html>