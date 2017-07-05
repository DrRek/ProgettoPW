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
		<script type="text/javascript" src="js/creditcard-validation.js"></script>
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
		<script type="text/javascript" src="js/prescription-validation.js"></script>
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
					%>divue<%
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
		<h4>Subdivact type</h4>
		<input type="radio" name="tipoP" value="%"> Percent (%) <input
			type="radio" name="tipoP" value="s"> Subdivact (-)
		<h4>Subdivact ammount:</h4>
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
			<h4 class="condivol-label">First image:</h4>
			<input id="img1" name="img1" type="file" multiple
				class="img,file-loading">
			<div class="help-block"></div>
			<h4 class="condivol-label">Second image:</h4>
			<input name="img2" type="file" multiple
				class="img,file-loading,specificiPerOcchiali">
			<div class="help-block"></div>
			<h4 class="condivol-label">Third image:</h4>
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
				<option value="Biodivue">Biodivue</option>
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
			<input type="number" min="10" name="diamedivo" placeholder="mm"
				value="" />
			<hr>
			<h4>Color:</h4>
			<select class="specificiPerLentine" name="colore">
				<option selected value="/N">divansparent</option>
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
			<h4 class="condivol-label">First image:</h4>
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

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="js/user.js"></script>
	<script src="js/util.js"></script>
</body>
</html>