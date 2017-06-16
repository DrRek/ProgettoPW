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

</head>

<body>
		<%@ include file="../jsp/header.jsp"%>
	
	<!--  search.js gestisce questa parte--> <!-- devo ancora ricercare nelle impostazioni iniziali anche in base alla parola cercata e implementare il sort -->
	<div id='daMettereASinistra'>

			<input type="radio" name="tipo" value="Occhiale" autocomplete="off"> Occhiali<br>
			<input type="radio" name="tipo" value="Lente a contatto" autocomplete="off"> Lenti a contatto<br>
			<div id='daMostrareSeOcchiali'>
				<form action="article" method='get'>
					<input type="hidden" name="tipo" value="O" />
					<input type="hidden" name="daCercare" <% if(daCercare!=null){%>value="<%=daCercare%>" <%} %>/>
					<label for="marca">Marca:</label> 
					<select name="marca">
						<option selected value="">Tutte</option>
						<option value="GreenVision">GreenVision</option>
						<option value="Lindberg">Lindberg</option>
						<option value="Oakley">Oakley</option>
						<option value="Persol">Persol</option>
						<option value="RayBan">RayBan</option>
					</select>
					<label for="prezzoMin">Prezzo:</label> 
					<div>
						<input type="number" step="0.01" min="0" name="prezzoMin" placeholder="min" />
						<input type="number" step="0.01" min="0" name="prezzoMax" placeholder="max" />
					</div>
					<label for="sesso">Sesso:</label> 
					<select name="sesso">
						<option selected value="any">Any</option>
						<option value="M">Man</option>
						<option value="F">Woman</option>
					</select>
					<label for="colore">Colore:</label> 
					<input type="text" name="colore" value=""/>
					<input type='submit' value='Search!' />
				</form>
			</div>
			<div id='daMostrareSeLentine'>
				<form action="article" method='get'>
					<input type="hidden" name="tipo" value="L" />
					<input type="hidden" name="daCercare" <% if(daCercare!=null){%>value="<%=daCercare%>" <%} %>/>
					<label for="marca">Marca:</label>
					<select name="marca">
						<option selected value="">Tutte</option>
						<option value="Acuvue">Acuvue</option>
						<option value="Alcon">Alcon</option>
						<option value="Biotrue">Biotrue</option>
						<option value="Frequency">Frequency</option>
						<option value="GreenVision">GreenVision</option>
					</select>
					<label for="prezzoMin">Prezzo:</label> 
					<div>
						<input type="number" step="0.01" min="0" name="prezzoMin" placeholder="min" />
						<input type="number" step="0.01" min="0" name="prezzoMax" placeholder="max" />
					</div>
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
						<option selected value="">Tutte</option>
						<option  value="0">±0.00</option>
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
						<option selected value="">Tutte</option>
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
						<option selected value="">Tutte</option>
						<option value="Ve">Verdi</option>
						<option value="Bl">Blu</option>
						<option value="Ma">Marroni</option>
						<option value="Ve">Verdi</option>
						<option value="Vi">Viola</option>
						<option value="Ro">Rosso</option>
						<option value="Pa">Particolari</option>
					</select>
					<input type='submit' value='Search!' />
				</form>
			</div>
	</div>
	<div class="container">
		<h2>Products</h2>
		<table class="table-bordered">
			<thead>
				<tr>
					<th><a href="article?sort=nome">Nome</a></th>
					<th><a href="article?sort=marca">Marca</a></th>
					<th><a href="article?sort=tipo">Tipo</a></th>
					<th><a href="article?sort=prezzo">Prezzo</a></th>
				</tr>
			</thead>
			<%
				if (products != null && products.size() != 0) {
					Iterator<?> it = products.iterator();
					while (it.hasNext()) {
						ArticleBean bean = (ArticleBean) it.next();
			%>
			<tr>
				<td>
					<a href="articlePage?nome=<%=bean.getNome()%>&marca=<%=bean.getMarca()%>">
					<%=bean.getNome()%>
					</a> 
				</td>
				<td><%=bean.getMarca()%></td>
				<td><%=bean.getTipo()%></td>
				<td><%=bean.getPrezzo()%></td>
				<td>
					<% if (admin != null) { %>
						<a href="article?action=delete&nome=<%=bean.getNome()%>&marca=<%=bean.getMarca()%>">Delete</a>
					<% } else { %>
						<a href="article?action=addCart&nome=<%=bean.getNome()%>&marca=<%=bean.getMarca()%>">Add to cart</a>
					<% } %>
				</td>
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

	</div>
	<div>
		<br><br>Questo che segue è il primo tentativo di luca di usare ajax<br>Verrà rimosso a breve<br>
		
		<input type="button" onClick="loadDoc()" value="primo tentativo">
		<h6 id="demo"></h6>
		<script>
			function loadDoc() {
				  var xhttp = new XMLHttpRequest();
				  xhttp.onreadystatechange = function() {
				    if (this.readyState == 4 && this.status == 200) {
				      document.getElementById("demo").innerHTML = this.responseText;
				    }
				  };
				  xhttp.open("GET", "article?action=ajax", true);
				  xhttp.send();
			}
		</script>
		
	</div>
	<script src="js/search-add-validation.js"></script>
</body>

</html>