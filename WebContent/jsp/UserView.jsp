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

<link href="css/UserView.css" type="text/css" rel="stylesheet"
	media="screen,projection" />

<!--Let browser know website is optimized for mobile-->
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

</head>

<body>
	<%@ include file="header.jsp"%>
	<br><br><br><br>
	<%
		if (user == null && admin == null) {
	%>
	<div class="container">
		<a href="access">Login is required!</a>
	</div>
	<%
		} else if (user != null) {
	%>
	<div class="container">

		<h2>
			Ciao,
			<%=user.getUser()%></h2>
		<div class="row">
			<div class="col-sm-3">
				<br>
				<span class="etichetta">Email:</span>
				<span class="value"><%=user.getEmail()%></span>
			</div>
			<div class="col-sm-3">
				<br>
				<span class="etichetta">Nome:</span>
				<span class="value"><%=user.getNome()%></span>
			</div>
			<div class="col-sm-3">
				<br>
				<span class="etichetta">Cognome:</span>
				<span class="value"><%=user.getCognome()%></span>
			</div>
			<div class="col-sm-3">
				<br>
				<span class="etichetta">Data di nascita:</span>
				<span class="value"><%=user.getDataDiNascita()%></span>
			</div>

		</div>
		<div class=row>
			<div class="col-sm-3">
				<br>
				<span class="etichetta">Stato:</span>
				<span class="value"><%=user.getStato()%></span>
			</div>
			<div class="col-sm-3">
				<br>
				<span class="etichetta">Cap:</span>
				<span class="value"><%=user.getCap()%></span>
			</div>
			<div class="col-sm-3">
				<br>
				<span class="etichetta">Indirizzo:</span>
				<span class="value"><%=user.getIndirizzo()%></span>
			</div>

		</div>
		<hr>
	</div>
	<div class="container">
		<h2>Carte di credito</h2>
		<table class="table table-condensed" id="cards">
			<thead><tr>
					<th>Numero carta</th>
					<th>Intestatario</th>
					<th>Circuito</th>
					<th>Scadenza</th>
					<th></th></tr>
			</thead>
			<tbody>
			<%
				Collection<?> cc = (Collection<?>) user.getCards();
					if (cc != null && cc.size() != 0) {
						Iterator<?> it = cc.iterator();
						while (it.hasNext()) {
							CreditCardBean bean = (CreditCardBean) it.next();
			%>
			<tr>
				<td class="numcc" data-th="Numero carta"><%=bean.getNumeroCC()%></td>
				<td data-th="Intestatario"><%=bean.getIntestatario()%></td>
				<td data-th="Circuito"><%=bean.getCircuito()%></td>
				<td data-th="Scadenza"><%=bean.getDataScadenza()%></td>
				<td data-th=""><input type="submit" class="removeCard btn btn-outline-secondary" name="removeCard"
					value="remove" /></td>
			</tr>
			<%
				}
					}
			%>
			</tbody>
		</table>
	</div>
	<div class="container">
		<h2>Aggiungi carta</h2>
		<div class="form-group">
		
		<input type="hidden" name="action" value="addCard"> 
		
		<label for="numcc">Numero carta di credito:</label> <input  class="form-control" name="numcc"
			type="text" maxlength="50" placeholder="ES. 1234567890123456">
		<span class="help-block" id="numcc"></span>
		
		<br> <label for="intestatario">Intestatario:</label>
		<input  class="form-control" name="intestatario" type="text" maxlength="50"
			placeholder="inserisci nome e cognome"><span class="help-block"
			id="intestatario"></span> 
			
			<br> <label for="circuito">Circuito:</label>
		<input  class="form-control" name="circuito" type="text" maxlength="50"
			placeholder="ES. Mastercard"><span class="help-block" id="circuito"></span> 
			
			<br><label for="scadenza">Scadenza:</label> <input  class="form-control" name="scadenza"
			type="date" maxlength="50" placeholder="ES. 2017-07-06"><span
			class="help-block" id="scadenza"></span> 
			
			<br> <label for="cvv">Cvv:</label> <input
			 class="form-control" name="cvv" type="text" maxlength="50" placeholder="ES. 570">
		<span class="help-block" id="cvv"></span>
		
		<br> <input class= "btn btn-outline-secondary"  id="addCard" name="addCard"
			value="Aggiungi" type="button">
		
		</div>
		<hr>
	</div>
	<div class="container">
		<h2>Prescrizioni</h2>
		<table class="table table-condensed" id="prescriptions">
			<thead><tr>
					<th>Codice</th>
					<th>Nome</th>
					<th>Sfera dx</th>
					<th>Sfera sx</th>
					<th></th></tr>
			</thead>
			<tbody>
			<%
				Collection<?> pres = (Collection<?>) user.getPrescriptions();
					if (pres != null && pres.size() != 0) {
						Iterator<?> it = pres.iterator();
						while (it.hasNext()) {
							PrescriptionBean bean = (PrescriptionBean) it.next();
			%>
			<tr>
				<td class="pCodice" data-th="Codice" ><%=bean.getCodice()%></td>
				<td data-th="Nome"><%=bean.getNome()%></td>
				<td data-th="Sfera dx"><%=bean.getSferaDX()%></td>
				<td data-th="Sfera sx"><%=bean.getSferaSX()%></td>
				<td data-th=""><input type="submit" class="removePrescription btn btn-outline-secondary"
					name="removePrescription" value="remove" /></td>
			</tr>
			<%
				}
					}
			%>
			</tbody>
		</table>
	</div>

	<div class="container">
		<h2>Aggiungi Prescrizione</h2>
		<div class="form-group">
		<label for="nomeP">Nome:</label> <input class="form-control"  name="nomeP" type="text"
			maxlength="50" placeholder="ES. di Mario per vicinanza"> <span class="help-block" 
			id="tipoP"></span><br> <label for="sferaSX">Sfera
			Sinistra:</label> <input class="form-control"  name="sferaSX" type="number" step="0.01"
			step="0.01" min="-10" max="10" placeholder="Gradazione Occhio SX"><span class="help-block" 
			id="sferaSX"></span> <br> <label for="cilindroSX">Cilindro
			Sinistra:</label> <input class="form-control"  name="cilindroSX" type="number" step="0.01"
			min="-10" max="10" placeholder="Valore Astigmatismo SX"><span class="help-block" 
			id="cilindroSX"></span> <br> <label for="asseSX">Asse
			Sinistra:</label> <input class="form-control"  name="asseSX" type="number" step="0.01" min="0"
			max="180" placeholder="Gradi Orientamento Lente"><span class="help-block" 
			id="asseSX"></span> <br> <label for="sferaDX">Sfera
			Destra:</label> <input class="form-control"  name="sferaDX" type="number" step="0.01" min="-10"
			max="10" placeholder="Gradazione Occhio DX"><span class="help-block" 
			id="sferaDX"></span> <br> <label for="cilindroDX">Cilindro
			Destra:</label> <input class="form-control"  name="cilindroDX" type="number" type="number"
			step="0.01" min="-10" max="10" placeholder="Valore Astigmatismo DX"><span class="help-block" 
			id="cilindroDX"></span> <br> <label for="asseDX">Asse
			Destra:</label> <input class="form-control"  name="asseDX" type="number" step="0.01" min="0"
			max="180" placeholder="Gradi Orientamento Lente"><span class="help-block" 
			id="asseDX"></span> <br> <label for="addVicinanza">Addizione
			Vicinanza</label> <input class="form-control"  name="addVicinanza" type="number" step="0.01"
			min="-10" max="10" placeholder="Aggiunta Diotdivie Positive">
		<span class="help-block"  id="addVicinanza"></span> <br> <label for="prismaOrizSX">Prisma
			Orizzontale SX:</label> <input class="form-control"  name="prismaOrizSX" type="number" step="0.01"
			min="-10" max="10" placeholder="Prisma Oriz SX"><span class="help-block" 
			id="prismaOrizSX"></span> <br> <label for="prismaOrizSXBD">Prisma
			Oriz SX BaseDirection:</label> <input class="form-control"  name="prismaOrizSXBD" type="text"
			maxlength="3" placeholder="PrismaOrizSXBaseDirection"><span class="help-block" 
			id="prismaOrizSXBD"></span> <br> <label for="prismaOrizDX">Prisma
			Orizzontale DX:</label> <input class="form-control"  name="prismaOrizDX" type="number" step="0.01"
			min="-10" max="10" placeholder="Prisma Oriz DX"><span class="help-block" 
			id="prismaOrizDX"></span> <br> <label for="prismaOrizDXBD">Prisma
			Oriz DX BaseDirection:</label> <input class="form-control"  name="prismaOrizDXBD" type="text"
			maxlength="3" placeholder="PrismaOrizDXBaseDirection"><span class="help-block" 
			id="prismaOrizDXBD"></span> <br> <label for="prismaVertSX">Prisma
			Verticale SX:</label> <input class="form-control"  name="prismaVertSX" type="number" step="0.01"
			min="-10" max="10" placeholder="Prisma Vert SX"><span class="help-block" 
			id="prismaVertSX"></span> <br> <label for="prismaVertSXBD">Prisma
			Vert SX BaseDirection:</label> <input class="form-control"  name="prismaVertSXBD" type="text"
			maxlength="3" placeholder="PrismaVertSXBaseDirection"><span class="help-block" 
			id="prismaVertSXBD"></span> <br> <label for="prismaVertDX">Prisma
			Verticale DX:</label> <input class="form-control"  name="prismaVertDX" type="number" step="0.01"
			min="-10" max="10" placeholder="Prisma Vert DX"><span class="help-block" 
			id="prismaVertDX"></span> <br> <label for="prismaVertSXBD">Prisma
			Vert DX BaseDirection:</label> <input class="form-control"  name="prismaVertDXBD" type="text"
			maxlength="3" placeholder="PrismaVertDXBaseDirection"><span class="help-block" 
			id="prismaVertDXBD"></span> <br> <label for="pdSX">Pupillar
			Distance SX: </label> <input class="form-control"  name="pdSX" type="number" step="0.01" min="-10"
			max="10" placeholder="Pupillar Distance"><span class="help-block"  id="pdSX"></span>
		<br> <label for="pdDX">Pupillar Distance DX: </label> <input class="form-control" 
			name="pdDX" type="number" step="0.01" min="-10" max="10"
			placeholder="Pupillar Distance"><span class="help-block"  id="pdDX"></span> <br>

		<input class="btn btn-outline-secondary"  id="addPrescription" name="addPrescription" value="Aggiungi"
			type="button">
			<hr>
	</div>
	
	</div>
	<div class="container">
		<h2>Storico ordini</h2>
		<table class="table table-condensed">
			<thead>
				<tr>
					<th>Codice</th>
					<th>Prezzo</th>
					<th>Data Esecuzione</th>
					<th></th>
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
				<td data-th="Codice"><%=bean.getCodice()%></td>
				<td data-th="Prezzo"><%=bean.getCosto()%></td>
				<td data-th="Data Esecuzione"><%=bean.getDataEsecuzione()%></td>
				<td data-th=""><a
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

		<h3>Current and future promotions</h3>
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
			<tbody>
			<%
				if (promozioni != null && promozioni.size() != 0) {
						for (PromotionBean bean : promozioni) {
			%>
			<tr>
				<td data-th="Name"><%=bean.getNome()%></td>
				<td data-th="Description"><%=bean.getDescrizione()%></td>
				<td data-th="Value"><%=bean.getSconto()%></td>
				<td data-th="Type"><%=bean.getTipo()%></td>
				<td data-th="Start"><%=bean.getDataInizio()%></td>
				<td data-th="End"><%=bean.getDataFine()%></td>
				<td data-th="Cumulative">
					<%
						if (bean.isCumulabile()) {
					%>true<%
						} else {
					%>false<%
						}
					%>
				</td>
				<td data-th=""><a href="promotion?nome=<%=bean.getNome()%>">info/edit</a></td>
			</tr>
			<%
				}
					}
			%>
			</tbody>
		</table>
		<h3>Add promotion</h3>
		<div class="form-group">
		<label>Name:</label>
		<input class="form-control" name="nomePr" type="text" /><span class="help-block" id="nomePr"></span><br>
		<label>Description:</label>
		<input class="form-control" name="descrizionePr" type="text" /><span class="help-block" id="descrizionePr"></span><br>
		<label>Subtract type:</label><br>
		<input type="radio" name="tipoPr" value="%" checked> Percent (%)
		<input type="radio" name="tipoPr" value="s"> Subtract (-)
		<span class="help-block" id="tipoPr"></span><br>
		<label class="cumulativePr">Cumulative:</label>
		<input class="cumulativePr" name="cumulabilePr" type="checkbox"><span class="cumulativeP help-block" id="cumulabilerP"></span><br>
		<label>Subtract amount:</label>
		<input class="form-control" name="scontoPr" type="number" /><span class="help-block" id="scontoPr"></span><br>
		<label>Start date:</label>
		<input class="form-control" name="inizioPr" type="date" size="35" /><span class="help-block" id="inizioPr"></span> <br>
		<label>End date:</label>
		<input class="form-control" name="finePr" type="date" size="35" /><span class="help-block" id="finePr"></span> <br> 
			
		<input name="submitPr" type="button" class="btn btn-outline-secondary" value="Add promotion!" /> <br>
		</div>
		<hr>
		<!-- da gestire il caso di eventuali update di prodotti già nel database-->
		<h3>Add product</h3>
		<div class="form-group">
		<label>Products:</label><br>
		<select name="tipo" class="btn btn-outline-secondary" onchange="setSearchField()">
			<option selected value="O">Glasses</option>
			<option value="L">Contact lenses</option>
		</select>
		<br>
		<form class="specificiPerOcchiali" action="article" method='post'
			enctype="multipart/form-data">
			<input class="form-control" type="hidden" name="toDo" value="addProduct" /> <input
				type="hidden" name="tipo" value="O" />
			<label>Name:</label>
			<input class="form-control" type="text" name="nomeProd" placeholder="name" /><span class="help-block" id="nomeProd"></span>
			<br>
			<label>Brand:</label><br>
			<select name="marcaProd" class="btn btn-outline-secondary">
				<option value="GreenVision">GreenVision</option>
				<option value="Lindberg">Lindberg</option>
				<option value="Oakley">Oakley</option>
				<option value="Persol">Persol</option>
				<option value="RayBan">RayBan</option>
			</select>
			<br>
			<label>Price:</label>
			<input class="form-control" type="number" step="1.00" min="0.00" name="prezzoProd"
				placeholder="Price" /><span class="help-block" id="prezzoProd"></span><br>
			<br>
			<label>Available number:</label>
			<input class="form-control" type="number" step="1" min="1" name="quantitaOc" /><span class="help-block" id="quantitaOc"></span>
			<br>
			<label>Description:</label>
			<input class="form-control" type="text" name="descrizioneOc" placeholder="Description" /><span id="descrizioneOc" class="help-block"></span>
			<br>
			<label>Sex:</label><br>
			<select name="sessoOc" class="btn btn-outline-secondary">
				<option selected value="U">Unisex</option>
				<option value="M">Male</option>
				<option value="F">Female</option>
			</select>
			<br>
			<label class="control-label">First image:</label>
			<input class="btn btn-outline-secondary" id="img1" name="img1" type="file" multiple
				class="img,file-loading">
			<div class="help-block"></div>
			<label class="control-label">Second image:</label>
			<input class="btn btn-outline-secondary" name="img2" type="file" multiple
				class="img,file-loading,specificiPerOcchiali">
			<div class="help-block"></div>
			<label class="control-label">Third image:</label>
			<input class="btn btn-outline-secondary" name="img3" type="file" multiple
				class="img,file-loading specificiPerOcchiali">
			<div class="help-block"></div>
			<h6>To change available number of an existing Product visit the
				Product's webpage</h6>
			<input class="btn btn-outline-secondary" id="addProduct" type='submit' value='Add' />
		</form>
		<form class="specificiPerLentine" action="article" method='post'
			enctype="multipart/form-data">
			<input class="form-control" type="hidden" name="toDo" value="addOcuct" /> <input
				type="hidden" name="tipo" value="L" />
			<label>Name:</label>
			<input class="form-control" type="text" name="nomeProd" placeholder="name" /><span id="nomeProd" class="help-block"></span>
			<br>
			<label>Brand:</label><br>
			<select name="marcaProd" class="btn btn-outline-secondary">
				<option value="Acuvue">Acuvue</option>
				<option value="Alcon">Alcon</option>
				<option value="Biotrue">Biotrue</option>
				<option value="Frequency">Frequency</option>
				<option value="GreenVision">GreenVision</option>
			</select>
			<br>
			<label>Price:</label>
			<input class="form-control" type="number" step="1.00" min="0.00" name="prezzoProd"
				placeholder="Price" /><span id="prezzoProd" class="help-block"></span><br>
			<br>
			<label>Available number:</label>
			<input class="form-control" type="number" step="1" min="1" name="quantitaL" /><span id="quantitaL" class="help-block"></span>
			<br>
			<label>Gradation:</label><br>
			<select name="gradazione" class="btn btn-outline-secondary">
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
			<br>
			<label>Duration:</label><br>
			<select name="tipologia" class="btn btn-outline-secondary">
				<option value="G">Daily</option>
				<option value="Q">15-days</option>
				<option value="M">Monthly</option>
			</select>
			<br>
			<label>Radius:</label>
			<input class="form-control" type="number" min="5" name="raggioL" placeholder="mm" value="" /><span id="raggioL" class="help-block"></span>
			<br>
			<label>Diameter:</label>
			<input class="form-control" type="number" min="10" name="diametroL" placeholder="mm"
				value="" /><span id="diametroL" class="help-block"></span>
			<br>
			<label>Color:</label><br>
			<select class="specificiPerLentine btn btn-outline-secondary" name="colore">
				<option selected value="/N">Transparent</option>
				<option value="Ve">Green</option>
				<option value="Bl">Blue</option>
				<option value="Ma">Brown</option>
				<option value="Vi">Violet</option>
				<option value="Ro">Red</option>
				<option value="Pa">Special</option>
			</select>
			<br>
			<label>Lenses per pack:</label>
			<input class="form-control" type="number" step="1" min="1" name="pezziPerPacco" /><span id="pezziPerPacco" class="help-block"></span>
			<label class="control-label">First image:</label>
			<input id="img1" name="img1" type="file" multiple
				class="img,file-loading btn btn-outline-secondary">
			<div class="help-block"></div>
			<h6>To change available number or the gradation availability of
				an existing product visit the product's webpage</h6>
			<input class="btn btn-outline-secondary" id="addProduct" type='submit' value='Add' />
		</form>
		</div>
	</div>

	<div class="container">
		<h2>Storico ordini</h2>
		<table class="table table-condensed">
			<thead>
				<tr>
					<th >Codice</th>
					<th>Prezzo</th>
					<th>Data Esecuzione</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
			<%
				Collection<OrderBean> orders = admin.getOrders();
					if (orders != null && orders.size() != 0) {
						Iterator<?> it = orders.iterator();
						while (it.hasNext()) {
							OrderBean bean = (OrderBean) it.next();
			%>
			<tr>
				<td data-th="Codice"><%=bean.getCodice()%></td>
				<td data-th="Prezzo"><%=bean.getCosto()%></td>
				<td data-th="Data Esecuzione"><%=bean.getDataEsecuzione()%></td>
				<td><a
					href="user?action=viewOldCheckout&codice=<%=bean.getCodice()%>">Info</a></td>
			</tr>
			<%
				}
					}
			%>
			</tbody>
		</table>
	</div>
	<%
		}
	%>

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="js/user.js"></script>
	<script src="js/util.js"></script>
</body>
</html>