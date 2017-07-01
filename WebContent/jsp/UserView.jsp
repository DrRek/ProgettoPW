<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
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
	
</head>

<body>
	<%@ include file="header.jsp"%>
	<br><br>
	<%
		if (user == null && admin == null) {
	%>
	<div class="container">
	<br><br>
		<a href="access">Login is required!</a>
	</div>
	<%
		} else if (user != null) {
	%>
	<div class="container">

		<h2>
			Ciao,
			<%=user.getUser()%></h2>
		<table class="table-bordered">
			<thead>
				<th colspan="2">I tuoi dati:<br></th>
			</thead>
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
		<p>
			<a>Modifica</a>&lt-- magari un giorno ci viene voglia di
			implementarlo
		</p>
	</div>
	<div class="container">
		<h2>Carte di credito</h2>
		<table class="table-bordered">
			<thead>
				<th>Numero carta</th>
				<th>Intestatario</th>
				<th>Circuito</th>
				<th>Scadenza</th>
				<th>Opzioni</th>
			</thead>
			<%
				Collection<?> cc = (Collection<?>) user.getCards();
				if (cc != null && cc.size() != 0) {
					Iterator<?> it = cc.iterator();
					while (it.hasNext()) {
						CreditCardBean bean = (CreditCardBean) it.next();
			%>
			<tr>
				<td><%=bean.getNumeroCC()%></td>
				<td><%=bean.getIntestatario() %></td>
				<td><%=bean.getCircuito() %></td>
				<td><%=bean.getDataScadenza() %></td>
				<td><a href="user?action=delCard&numeroCC=<%=bean.getNumeroCC()%>">rimuovi</a></td>
			</tr>
			<% 	}
					}%>
		</table>
	</div>
	<div class="container">
		<h2>Aggiungi carta</h2>
		<script type="text/javascript" src="js/creditcard-validation.js"></script>

		<form name='cc' onSubmit="return ccValidation();"  action='user'
			method="post"> 
					
		<input type="hidden" name="action" value="addCard"> 
		
			<label for="numcc">Numero carta di credito:</label> <input
				name="numcc" type="text" maxlength="50"
				placeholder="ES. 1234567890123456"> <span id="numcc"></span><br>

			<label for="intestatario">Intestatario:</label> <input
				name="intestatario" type="text" maxlength="50" 
				placeholder="inserisci nome e cognome"><span
				id="intestatario"></span> <br>
				
				 <label for="circuito">Circuito:</label>
			<input name="circuito" type="text" maxlength="50" 
				placeholder="ES. Mastercard"><span id="circuito"></span> <br> 
				
				<label
				for="scadenza">Scadenza:</label> <input name="scadenza" type="text"
				maxlength="50"  placeholder="ES. 2017-07-06"><span id="scadenza"></span> <br>
				
			<label for="cvv">Cvv:</label> <input name="cvv" type="text"
				maxlength="50" placeholder="ES. 570"> <span id="cvv"></span><br> 
				
			 <input name="addCard" value="Aggiungi" type="submit">
		</form>
	</div>
	
	<div class="container">
		<h2>Prescrizioni</h2>
		<table class="table-bordered">
			<thead>
				<th>Codice</th>
				<th>Tipo</th>
			</thead>
			<%
				Collection<PrescriptionBean> pres = user.getPrescriptions();
				if (pres != null && pres.size() != 0) {
					Iterator<?> it = pres.iterator();
					while (it.hasNext()) {
						PrescriptionBean bean = (PrescriptionBean) it.next();
			%>
			<tr>
				<td><%=bean.getCodice()%></td>
				<td><%=bean.getTipo() %></td>
				<td><a href="user?action=delPres&codice=<%=bean.getCodice()%>">rimuovi</a></td>
			</tr>
			<% 	}
					}%>
		</table>
	</div>
	
	<div class="container">
		<h2>Aggiungi Prescrizione</h2>
		<script type="text/javascript" src="js/prescription-validation.js"></script>

		<form name='presc' onSubmit="return presValidation();"  action='user'
			method="post"> 
					
		<input type="hidden" name="action" value="addPrescription"> 
		
			<label for="tipoP">Tipo Prescrizione:</label> <input
				name="tipoP" type="text" maxlength="50"
				placeholder="ES. Oculistica"> <span id="tipoP"></span><br>

			<label for="sferaSX">Sfera Sinistra:</label> <input
				name="sferaSX" type="number" step="0.01" step="0.01" min="-10" max="10"
				placeholder="Gradazione Occhio SX"><span
				id="sferaSX"></span> <br>
				
				 <label for="cilindroSX">Cilindro Sinistra:</label>
			<input name="cilindroSX" type="number" step="0.01" min="-10" max="10"
				placeholder="Valore Astigmatismo SX"><span id="cilindroSX"></span> <br> 
				
				<label
				for="asseSX">Asse Sinistra:</label> <input name="asseSX" type="number" step="0.01" min="0" max="180"
				 placeholder="Gradi Orientamento Lente"><span id="asseSX"></span> <br>
				
				<label for="sferaDX">Sfera Destra:</label> <input
				name="sferaDX" type="number" step="0.01" min="-10" max="10"
				placeholder="Gradazione Occhio DX"><span
				id="sferaDX"></span> <br>
				
				 <label for="cilindroDX">Cilindro Destra:</label>
			<input name="cilindroDX" type="number" type="number" step="0.01" min="-10" max="10"
				placeholder="Valore Astigmatismo DX"><span id="cilindroDX"></span> <br> 
				
				<label
				for="asseDX">Asse Destra:</label> <input name="asseDX" type="number" step="0.01" min="0" max="180"
				  placeholder="Gradi Orientamento Lente"><span id="asseDX"></span> <br>
				  
				 <label
				 for="addVicinanza">Addizione Vicinanza</label> <input name="addVicinanza" type="number" step="0.01" min="-10" max="10"
				 placeholder="Aggiunta Diottrie Positive"> <span id="addVicinanza"></span> <br>
				 
				 <label
				for="prismaOrizSX">Prisma Orizzontale SX:</label> <input name="prismaOrizSX" type="number" step="0.01" min="-10" max="10"
				  placeholder="Prisma Oriz SX"><span id="prismaOrizSX"></span> <br>
				  
				  <label
				for="prismaOrizSXBD">Prisma Oriz SX BaseDirection:</label> <input name="prismaOrizSXBD" type="text"
					maxlength="3"
				  placeholder="PrismaOrizSXBaseDirection"><span id="prismaOrizSXBD"></span> <br>
				  
				  <label
				for="prismaOrizDX">Prisma Orizzontale DX:</label> <input name="prismaOrizDX" type="number" step="0.01" min="-10" max="10"
				  placeholder="Prisma Oriz DX"><span id="prismaOrizDX"></span> <br>
				  
				  <label
				for="prismaOrizDXBD">Prisma Oriz DX BaseDirection:</label> <input name="prismaOrizDXBD" type="text"
				maxlength="3"
				  placeholder="PrismaOrizDXBaseDirection"><span id="prismaOrizDXBD"></span> <br>
				  
				  <label
				for="prismaVertSX">Prisma Verticale SX:</label> <input name="prismaVertSX" type="number" step="0.01" min="-10" max="10"
				  placeholder="Prisma Vert SX"><span id="prismaVertSX"></span> <br>
				  
				  <label
				for="prismaVertSXBD">Prisma Vert SX BaseDirection:</label> <input name="prismaVertSXBD" type="text"
				maxlength="3"
				  placeholder="PrismaVertSXBaseDirection"><span id="prismaVertSXBD"></span> <br>
			
			 	<label
				for="prismaVertDX">Prisma Verticale DX:</label> <input name="prismaVertDX" type="number" step="0.01" min="-10" max="10"
				  placeholder="Prisma Vert DX"><span id="prismaVertDX"></span> <br>
				  
				  <label
				for="prismaVertSXBD">Prisma Vert DX BaseDirection:</label> <input name="prismaVertDXBD" type="text"
				maxlength="3"
				  placeholder="PrismaVertDXBaseDirection"><span id="prismaVertDXBD"></span> <br>
				  
				  <label
				  for="pdSX">Pupillar Distance SX: </label> <input name="pdSX" type="number" step="0.01" min="-10" max="10"
				  	placeholder="Pupillar Distance"><span id="pdSX"></span> <br>
				  	
				  <label
				  for="pdDX">Pupillar Distance DX: </label> <input name="pdDX" type="number" step="0.01" min="-10" max="10"
				  	placeholder="Pupillar Distance"><span id="pdDX"></span> <br>
				  				 
			 <input name="addPrescription" value="Aggiungi" type="submit">
		</form>
	</div>
	

	<%
		} else if (admin != null) {
	%>
	<div class="container">
		<h2>
			Ciao,
			<%=admin.getUser()%></h2>
		<!-- da gestire il caso di eventuali update di prodotti già nel database-->

		<h3>Insert</h3>
		<div id='daMettereASinistra'>
			<input type="radio" name="tipo" value="Occhiale" autocomplete="off"> Occhiali<br>
			<input type="radio" name="tipo" value="Lente a contatto" autocomplete="off"> Lenti a contatto<br>
			<div id='daMostrareSeOcchiali'>
				<form action="user" method='post' enctype="multipart/form-data">
					<input type="hidden" name="action" value="insert" />
					<input type="hidden" name="tipo" value="O" />
					
					<label for="nome">Nome:</label> 
					<input type="text" name="nome" />
					
					<label for="marca">Marca:</label> 
					<select name="marca">
						<option value="GreenVision">GreenVision</option>
						<option value="Lindberg">Lindberg</option>
						<option value="Oakley">Oakley</option>
						<option value="Persol">Persol</option>
						<option value="RayBan">RayBan</option>
					</select>
					
					<label for="prezzo">Prezzo:</label> 
					<input type="number" step="0.01" min="0" name="prezzo" placeholder="Prezzo" />
					
					<label for="sesso">Sesso:</label> 
					<select name="sesso">
						<option selected value="U">Any</option>
						<option value="M">Man</option>
						<option value="F">Woman</option>
					</select>
					
					<label for="descrizione">Descrizione:</label> 
					<input type="text" name="descrizione" />
					<label for="numeroPezziDisponibili">Numero pezzi disponibili:</label> 
					<input type="number" step="1" min="1" name="numeroPezziDisponibili" placeholder="Numero Pezzi Disponibili" />
					
					<label class="control-label" for="img1">Immagine:</label><input id="img1" name="img1" type="file" multiple class="file-loading">
					<div id="errorBlock" class="help-block"></div>
						<script>
						$(document).on('ready', function() {
						    $("#img1").fileinput({
						        showPreview: false,
						        allowedFileExtensions: ["jpg", "jpeg", "gif", "png"],
						        elErrorContainer: "#errorBlock"
						    });
						});
						</script>					
					<input type='submit' value='Insert!' />
				</form>
			</div>
			<div id='daMostrareSeLentine'>
				<form action="user" method='post'>
					<input type="hidden" name="action" value="insert" />
					<input type="hidden" name="tipo" value="L" />
					<label for="nome">Nome:</label> 
					<input type="text" name="nome" />
					<label for="marca">Marca:</label>
					<select name="marca">
						<option value="Acuvue">Acuvue</option>
						<option value="Alcon">Alcon</option>
						<option value="Biotrue">Biotrue</option>
						<option value="Frequency">Frequency</option>
						<option value="GreenVision">GreenVision</option>
					</select>
					<label for="prezzo">Prezzo:</label> 
					<input type="number" step="0.01" min="0" name="prezzo" placeholder="Prezzo" />
					<label for="gradazione">Gradazione:</label>
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
						<option selected value="0">±0.00</option>
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
					<label for="tipologia">Tipo:</label>
					<select name="tipologia">
						<option value="G">Giornaliere</option>
						<option value="Q">Quindicinali</option>
						<option value="M">Mensili</option>
					</select>
					<label for="raggio">Raggio:</label>
					<input type="number" min="5"  name="raggio" placeholder="mm" value="" />
					<label for="diametro">Diametro:</label>
					<input type="number" min="10" name="diametro" placeholder="mm" value="" />
					<label for="colore">Colore:</label>
					<select name="colore">
						<option value="Ve">Verdi</option>
						<option value="Bl">Blu</option>
						<option value="Ma">Marroni</option>
						<option value="Ve">Verdi</option>
						<option value="Vi">Viola</option>
						<option value="Ro">Rosso</option>
						<option value="Pa">Particolari</option>
					</select>
					<label for="numeroPezziNelPacco">Numero pezzi nel pacco:</label> 
					<input type="number" step="1" min="1" name="numeroPezziNelPacco" placeholder="Numero Pezzi Nel Pacco" />
					<label for="numeroPezziDisponibili">Numero pezzi disponibili:</label> 
					<input type="number" step="1" min="1" name="numeroPezziDisponibili" placeholder="Numero Pezzi Disponibili" />
					<input type='submit' value='Insert!' />
				</form>
			</div>
		</div>
	</div>
	<%
		}
	%>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="js/search-add-validation.js"></script>
</body>
</html>