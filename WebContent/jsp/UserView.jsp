<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	UserBean user = (UserBean) request.getSession().getAttribute("user");
	AdminBean admin = (AdminBean) request.getSession().getAttribute("admin");
	ArrayList<PromotionBean> promozioni = (ArrayList<PromotionBean>) request.getAttribute("promozioni");
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
	<%@ include file="header.jsp"%>
	<br>
	<br>
	<%
		if (user == null && admin == null) {
	%>
	<div class="container">
		<br> <br> <a href="access">Login is required!</a>
	</div>
	<%
		} else if (user != null) {
	%>
	<div class="container">

		<h2>
			Ciao,
			<%=user.getUser()%></h2>
		<table class="table table-condensed">
			<tr>
				<td>Nome:</td>
				<td><%=user.getNome()%></td>
			</tr>
			<tr>
				<td>Cognome:</td>
				<td><%=user.getCognome()%></td>
			</tr>
			<tr>
				<td>Stato:</td>
				<td><%=user.getStato()%></td>
			</tr>
			<tr>
				<td>Cap:</td>
				<td><%=user.getCap()%></td>
			</tr>
			<tr>
				<td>Indirizzo:</td>
				<td><%=user.getIndirizzo()%></td>
			</tr>
			<tr>
				<td>Email:</td>
				<td><%=user.getEmail()%></td>
			</tr>
			<tr>
				<td>Data di nascita:</td>
				<td><%=user.getDataDiNascita()%></td>
			</tr>
		</table>
		<hr>
	</div>
	<div class="container">
		<h2>Carte di credito</h2>
		<table class="table table-condensed" id="cards">
			<thead>
				<tr>
					<th>Numero carta</th>
					<th>Intestatario</th>
					<th>Circuito</th>
					<th>Scadenza</th>
					<th>Opzioni</th>
				</tr>
			</thead>
			<%
				Collection<?> cc = (Collection<?>) user.getCards();
					if (cc != null && cc.size() != 0) {
						Iterator<?> it = cc.iterator();
						while (it.hasNext()) {
							CreditCardBean bean = (CreditCardBean) it.next();
			%>
			<tr>
				<td class="numcc"><%=bean.getNumeroCC()%></td>
				<td><%=bean.getIntestatario()%></td>
				<td><%=bean.getCircuito()%></td>
				<td><%=bean.getDataScadenza()%></td>
				<td><input type="submit" class="removeCard" name="removeCard" value="remove" /></td></tr>
			<%
				}
					}
			%>
		</table>
	</div>
	<div class="container">
		<h2>Aggiungi carta</h2>
		<script type="text/javascript" src="js/creditcard-validation.js"></script>

		<input type="hidden" name="action" value="addCard"> <label
			for="numcc">Numero carta di credito:</label> <input name="numcc"
			type="text" maxlength="50" placeholder="ES. 1234567890123456">
		<span id="numcc"></span><br> <label for="intestatario">Intestatario:</label>
		<input name="intestatario" type="text" maxlength="50"
			placeholder="inserisci nome e cognome"><span
			id="intestatario"></span> <br> <label for="circuito">Circuito:</label>
		<input name="circuito" type="text" maxlength="50"
			placeholder="ES. Mastercard"><span id="circuito"></span> <br>

		<label for="scadenza">Scadenza:</label> <input name="scadenza"
			type="text" maxlength="50" placeholder="ES. 2017-07-06"><span
			id="scadenza"></span> <br> <label for="cvv">Cvv:</label> <input
			name="cvv" type="text" maxlength="50" placeholder="ES. 570">
		<span id="cvv"></span><br> <input id="addCard" name="addCard"
			value="Aggiungi" type="button">
	</div>

	<div class="container">
		<h2>Prescrizioni</h2>
		<table class="table table-condensed" id="prescriptions">
			<thead>
				<tr>
					<th>Codice</th>
					<th>Nome</th>
				<tr>
			</thead>
			<%
				Collection<?> pres = (Collection<?>) user.getPrescriptions();
					if (pres != null && pres.size() != 0) {
						Iterator<?> it = pres.iterator();
						while (it.hasNext()) {
							PrescriptionBean bean = (PrescriptionBean) it.next();
			%>
			<tr>
				<td class="pCodice"><%=bean.getCodice()%></td>
				<td><%=bean.getNome()%></td>
				<td><input type="submit" class="removePrescription" name="removePrescription" value="remove" /></td>
			</tr>
			<%
				}
					}
			%>
		</table>
	</div>

	<div class="container">
		<h2>Aggiungi Prescrizione</h2>
		<script type="text/javascript" src="js/prescription-validation.js"></script>

		<label for="nomeP">Nome:</label> <input name="nomeP" type="text"
			maxlength="50" placeholder="ES. di Mario per vicinanza"> <span
			id="tipoP"></span><br> <label for="sferaSX">Sfera Sinistra:</label>
		<input name="sferaSX" type="number" step="0.01" step="0.01" min="-10"
			max="10" placeholder="Gradazione Occhio SX"><span id="sferaSX"></span> <br> <label for="cilindroSX">Cilindro Sinistra:</label> <input name="cilindroSX" type="number" step="0.01" min="-10" max="10"
			placeholder="Valore Astigmatismo SX"><span id="cilindroSX"></span> <br> <label for="asseSX">Asse Sinistra:</label> <input name="asseSX" type="number" step="0.01" min="0" max="180"
			placeholder="Gradi Orientamento Lente"><span id="asseSX"></span> <br> <label for="sferaDX">Sfera Destra:</label> <input name="sferaDX" type="number" step="0.01" min="-10" max="10"
			placeholder="Gradazione Occhio DX"><span id="sferaDX"></span> <br> <label
			for="cilindroDX">Cilindro Destra:</label> <input name="cilindroDX"
			type="number" type="number" step="0.01" min="-10" max="10"
			placeholder="Valore Astigmatismo DX"><span
			id="cilindroDX"></span> <br> <label for="asseDX">Asse
			Destra:</label> <input name="asseDX" type="number" step="0.01" min="0"
			max="180" placeholder="Gradi Orientamento Lente"><span
			id="asseDX"></span> <br> <label for="addVicinanza">Addizione
			Vicinanza</label> <input name="addVicinanza" type="number" step="0.01"
			min="-10" max="10" placeholder="Aggiunta Diottrie Positive">
		<span id="addVicinanza"></span> <br> <label for="prismaOrizSX">Prisma
			Orizzontale SX:</label> <input name="prismaOrizSX" type="number" step="0.01"
			min="-10" max="10" placeholder="Prisma Oriz SX"><span
			id="prismaOrizSX"></span> <br> <label for="prismaOrizSXBD">Prisma
			Oriz SX BaseDirection:</label> <input name="prismaOrizSXBD" type="text"
			maxlength="3" placeholder="PrismaOrizSXBaseDirection"><span
			id="prismaOrizSXBD"></span> <br> <label for="prismaOrizDX">Prisma
			Orizzontale DX:</label> <input name="prismaOrizDX" type="number" step="0.01"
			min="-10" max="10" placeholder="Prisma Oriz DX"><span
			id="prismaOrizDX"></span> <br> <label for="prismaOrizDXBD">Prisma
			Oriz DX BaseDirection:</label> <input name="prismaOrizDXBD" type="text"
			maxlength="3" placeholder="PrismaOrizDXBaseDirection"><span
			id="prismaOrizDXBD"></span> <br> <label for="prismaVertSX">Prisma
			Verticale SX:</label> <input name="prismaVertSX" type="number" step="0.01"
			min="-10" max="10" placeholder="Prisma Vert SX"><span
			id="prismaVertSX"></span> <br> <label for="prismaVertSXBD">Prisma
			Vert SX BaseDirection:</label> <input name="prismaVertSXBD" type="text"
			maxlength="3" placeholder="PrismaVertSXBaseDirection"><span
			id="prismaVertSXBD"></span> <br> <label for="prismaVertDX">Prisma
			Verticale DX:</label> <input name="prismaVertDX" type="number" step="0.01"
			min="-10" max="10" placeholder="Prisma Vert DX"><span
			id="prismaVertDX"></span> <br> <label for="prismaVertSXBD">Prisma
			Vert DX BaseDirection:</label> <input name="prismaVertDXBD" type="text"
			maxlength="3" placeholder="PrismaVertDXBaseDirection"><span
			id="prismaVertDXBD"></span> <br> <label for="pdSX">Pupillar
			Distance SX: </label> <input name="pdSX" type="number" step="0.01" min="-10"
			max="10" placeholder="Pupillar Distance"><span id="pdSX"></span>
		<br> <label for="pdDX">Pupillar Distance DX: </label> <input
			name="pdDX" type="number" step="0.01" min="-10" max="10"
			placeholder="Pupillar Distance"><span id="pdDX"></span> <br>

		<input id="addPrescription" name="addPrescription" value="Aggiungi"
			type="button">
	</div>

	<div class="container">
		<h2>Ordini passati</h2>
		<table class="table table-condensed">
			<thead>
				<tr>
					<th>Codice</th>
					<th>Prezzo</th>
					<th>Data Esecuzione</th>
				</tr>
			</thead>
			<%
				Collection<OrderBean> orders = user.getOrders();
					if (orders != null && orders.size() != 0) {
						Iterator<?> it = orders.iterator();
						while (it.hasNext()) {
							OrderBean bean = (OrderBean) it.next();
			%>
			<tr>
				<td><%=bean.getCodice()%></td>
				<td><%=bean.getCosto()%></td>
				<td><%=bean.getDataEsecuzione()%></td>
				<td><a
					href="user?action=viewOldCheckout&codice=<%=bean.getCodice()%>">Info</a>
			</tr>
			<%
				}
					}
			%>
		</table>
	</div>


	<%
		} else if (admin != null) {
	%>
	<div class="container">
		<h2>
			Ciao,
			<%=admin.getUser()%></h2>

		<h2>Active and future promotion list</h2>
		<table id="tablePromotion" class="table table-condensed">
			<thead>
				<tr>
					<th>Name</th>
					<th>Description</th>
					<th>Value</th>
					<th>Type</th>
					<th>Start</th>
					<th>End</th>
					<th>Cumulative</th>
					<th></th>
				</tr>
			</thead>
			<%
				if (promozioni != null && promozioni.size() != 0) {
						for (PromotionBean bean : promozioni) {
			%>
			<tr>
				<td><%=bean.getNome()%></td>
				<td><%=bean.getDescrizione()%></td>
				<td><%=bean.getSconto()%></td>
				<td><%=bean.getTipo()%></td>
				<td><%=bean.getDataInizio()%></td>
				<td><%=bean.getDataFine()%></td>
				<td>
					<%
						if (bean.isCumulabile()) {
					%>true<%
						} else {
					%>false<%
						}
					%>
				</td>
				<td><a href="promotion?nome=<%=bean.getNome()%>">info/edit</a></td>
			</tr>
			<%
				}
					}
			%>
		</table>
		<h3>Add promotion</h3>
		<h4>Name:</h4>
		<input id="nomeP" type="text" />
		<h4>Description:</h4>
		<input id="descrizioneP" type="text" />
		<h4>Subtract type</h4>
		<input type="radio" name="tipoP" value="%"> Percent (%) <input
			type="radio" name="tipoP" value="s"> Subtract (-)
		<h4>Subtract ammount:</h4>
		<input id="scontoP" type="number" /> <br>
		<h4>Start date:</h4>
		<input id="inizioP" type="date" size="35" /> <br>
		<h4>End date:</h4>
		<input id="fineP" type="date" size="35" /> <br> <input
			id="cumulabileP" type="checkbox"> Cumulabile<br> <input
			id="submitP" type="button" value="Add promotion!" /> <br>
		<!-- da gestire il caso di eventuali update di prodotti già nel database-->
		<h3>Add product</h3>
		<hr>
		<h4>Products:</h4>
		<select name="tipo" onchange="setSearchField()">
			<option selected value="O">Glasses</option>
			<option value="L">Contact lenses</option>
		</select>
		<hr>
		<form class="specificiPerOcchiali" action="article" method='post'
			enctype="multipart/form-data">
			<input type="hidden" name="toDo" value="addProduct" /> <input
				type="hidden" name="tipo" value="O" />
			<h4>Name:</h4>
			<input type="text" name="nome" value="name" />
			<hr>
			<h4>Brand:</h4>
			<select name="marca">
				<option value="GreenVision">GreenVision</option>
				<option value="Lindberg">Lindberg</option>
				<option value="Oakley">Oakley</option>
				<option value="Persol">Persol</option>
				<option value="RayBan">RayBan</option>
			</select>
			<hr>
			<h4>Price:</h4>
			<input type="number" step="1.00" min="0.00" name="prezzo"
				placeholder="Price" /><br>
			<hr>
			<h4>Available number:</h4>
			<input type="number" step="1" min="1" name="quantita" />
			<hr>
			<h4>Description:</h4>
			<input type="text" name="descrizione" value="Description" />
			<hr>
			<h4>Sex:</h4>
			<select name="sesso">
				<option value="U">Unisex</option>
				<option value="M">Male</option>
				<option value="F">Female</option>
			</select>
			<hr>
			<h4 class="control-label">First image:</h4>
			<input id="img1" name="img1" type="file" multiple
				class="img,file-loading">
			<div class="help-block"></div>
			<h4 class="control-label">Second image:</h4>
			<input name="img2" type="file" multiple
				class="img,file-loading,specificiPerOcchiali">
			<div class="help-block"></div>
			<h4 class="control-label">Third image:</h4>
			<input name="img3" type="file" multiple
				class="img,file-loading specificiPerOcchiali">
			<div class="help-block"></div>
			<h6>To change available number of an existing product visit the
				product's webpage</h6>
			<input id="addProduct" type='submit' value='Add glass!' />
		</form>
		<form class="specificiPerLentine" action="article" method='post'
			enctype="multipart/form-data">
			<input type="hidden" name="toDo" value="addProduct" /> <input
				type="hidden" name="tipo" value="L" />
			<h4>Name:</h4>
			<input type="text" name="nome" value="name" />
			<hr>
			<h4>Brand:</h4>
			<select name="marca">
				<option value="Acuvue">Acuvue</option>
				<option value="Alcon">Alcon</option>
				<option value="Biotrue">Biotrue</option>
				<option value="Frequency">Frequency</option>
				<option value="GreenVision">GreenVision</option>
			</select>
			<hr>
			<h4>Price:</h4>
			<input type="number" step="1.00" min="0.00" name="prezzo"
				placeholder="Price" /><br>
			<hr>
			<h4>Available number:</h4>
			<input type="number" step="1" min="1" name="quantita" />
			<hr>
			<h4>Gradation:</h4>
			<select name="gradazione">
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
				<option value="0" selected>±0.00</option>
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
			<h4>Duration:</h4>
			<select name="tipologia">
				<option value="G">Daily</option>
				<option value="Q">15-days</option>
				<option value="M">Monthly</option>
			</select>
			<hr>
			<h4>Radius:</h4>
			<input type="number" min="5" name="raggio" placeholder="mm" value="" />
			<hr>
			<h4>Diameter:</h4>
			<input type="number" min="10" name="diametro" placeholder="mm"
				value="" />
			<hr>
			<h4>Color:</h4>
			<select class="specificiPerLentine" name="colore">
				<option selected value="/N">Transparent</option>
				<option value="Ve">Green</option>
				<option value="Bl">Blue</option>
				<option value="Ma">Brown</option>
				<option value="Vi">Violet</option>
				<option value="Ro">Red</option>
				<option value="Pa">Special</option>
			</select>
			<hr>
			<h4>Lenses per pack:</h4>
			<input type="number" step="1" min="1" name="pezziPerPacco" />
			<h4 class="control-label">First image:</h4>
			<input id="img1" name="img1" type="file" multiple
				class="img,file-loading">
			<div class="help-block"></div>
			<h6>To change available number or the gradation availability of
				an existing product visit the product's webpage</h6>
			<input id="addProduct" type='submit' value='Add contact lense!' />
		</form>
	</div>

	<div class="container">
		<h2>Ordini passati</h2>
		<table class="table table-condensed">
			<thead>
				<tr>
					<th>Codice</th>
					<th>Prezzo</th>
					<th>Data Esecuzione</th>
				</tr>
			</thead>
			<%
				Collection<OrderBean> orders = admin.getOrders();
					if (orders != null && orders.size() != 0) {
						Iterator<?> it = orders.iterator();
						while (it.hasNext()) {
							OrderBean bean = (OrderBean) it.next();
			%>
			<tr>
				<td><%=bean.getCodice()%></td>
				<td><%=bean.getCosto()%></td>
				<td><%=bean.getDataEsecuzione()%></td>
				<td><a
					href="user?action=viewOldCheckout&codice=<%=bean.getCodice()%>">Info</a></td>
			</tr>
			<%
				}
					}
			%>
		</table>
	</div>
	<%
		}
	%>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="js/user.js"></script>
</body>
</html>