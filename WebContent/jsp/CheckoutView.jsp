<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	Cart cart = (Cart) request.getSession().getAttribute("cart");
	UserBean user = (UserBean) request.getSession().getAttribute("user");
%>

<!DOCTYPE html>
<html>
<%@ page contentType="text/html; charset=UTF-8"
	import="java.util.*,it.quattrocchi.support.*, it.quattrocchi.*"%>
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

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>


<body>

	​​​​​​​​​​​​
	<%@ include file="header.jsp"%>
	<div class="container">
		<h2>Cart</h2>
	</div>
	<%
		if (cart == null) {
	%>
	<div class="container">
		<h3>empty cart</h3>
	</div>
	<%
		} else {
	%>
		<script>
		$(document).ready(
				function() {

					$("input[name='numeroPezziDisponibili']").change(
							function() {
								var valore = $(this).val();
								var val = parseInt(valore);
								if(val > 0)
								{
									var nomeArt = $(this).parent().siblings(".nomeArt").html();
									var marcaArt = $(this).parent().siblings(".marcaArt").html();
									var tipoArt =  $(this).parent().siblings(".tipoArt").html();
									var prezzo = $(this).parent().siblings(".prezzoArt").html();
									var prezzoArt = parseFloat(prezzo);
									
									if(tipoArt == "O" || tipoArt =="o")
									{
										$.ajax({
											  type: "POST",
											  url: "checkout",
											  data: 
												  { 	action: "updateCart",
											 	 	 	nome: nomeArt,
										    			marca: marcaArt,
										    			numero: val
												    },
											  success: function () {
												  //aggiornare prezzo e numero sopra
												  //non va
												  $(this).parent().siblings(".prezzoArt").html(val * prezzoArt);
												  
											  }
					
										  });
									}
									else
									{
										var gradazioneArt = $(this).parent().siblings(".gradazioneArt").html();
										$.ajax({
											  type: "POST",
											  url: "checkout",
											  data: 
												  { 	action: "updateCart",
											 	 	 	nome: nomeArt,
										    			marca: marcaArt,
												    	gradazione: gradazioneArt,
												    	numero: val
												    },
											  success: function () {
												 //aggiornare prezzo
												 //non va
												  $(this).parent().siblings(".prezzoArt").html(val * prezzoArt);
											  }
										  });
									}
									
								}
							});

				});
	</script>
	<div class="container">
		<table class="table-bordered">
			<tr>
				<th>Tipo</th>
				<th>Nome</th>
				<th>Marca</th>
				<th>Gradazione</th>
				<th>Numero Prodotti</th>
				<th>Prezzo</th>
				<th>Opzioni</th>
			</tr>
			<%
				List<CartArticle> prodcart = cart.getProducts();
					for (CartArticle beancart : prodcart) {
			%>
			<tr>
				<td class ="tipoArt"><%=beancart.getArticle().getTipo()%></td>
				<td class="nomeArt"><%=beancart.getArticle().getNome()%></td>
				<td class="marcaArt"><%=beancart.getArticle().getMarca()%></td>
				
				<%
				if(beancart.getArticle().getClass().getName().equals("it.quattrocchi.support.GlassesBean")){
				%>				
					<td>N/A</td>
				<%
				}else{
				%>	
					<td class="gradazioneArt"><%=((ContactLensesBean) beancart.getArticle()).getGradazione()%></td>
				<%
				}
				%>	
				
			
				<td><input name="numeroPezziDisponibili" type="number" min="1"
					value="<%=beancart.getQuantity()%>"></td>
				
				<td class="prezzoArt"><%=beancart.getPrezzo()%>€</td>
				
				<!-- il delete cart si dovrà fare con ajax -->
				<td><a
					href="article?action=delCart&nome=<%=beancart.getArticle().getNome()%>&marca=<%=beancart.getArticle().getMarca()%>">Delete
						from cart</a></td>
			</tr>
			<%
				}
			%>
		</table>

		<%
			if (cart.getNumberOfProducts() != 0) {
		%>
		<script>
			function controllaCarta() {
				if ($("select[name='carta']").val() == "default") {
					$("#warning").html("selezionare una carta");
					return false
				} else {
					$("#warning").empty();
					return true;
				}
			}
		</script>

		<form name='checkout' onSubmit="return controllaCarta();"
			action='checkout' method="post">

			<select name="carta">
				<option selected="" value="default">(Please select a credit
					card)</option>
				<%
					ArrayList<CreditCardBean> cc = user.getCards();
							if (cc != null && cc.size() != 0) {
								for (CreditCardBean c : cc) {
				%>

				<option value=<%=c.getNumeroCC()%>><%=c.getNumeroCC()%></option>
				<%
					}
							}
				%>
			</select><span id="warning"></span> <input type="hidden" name="action"
				value="submit"> <input name="submit"
				value="Completa il pagamento!" type="submit">

		</form>
	</div>
	<%
		}
	%>
	<div class="container">
		<h3>
			Prezzo totale:
			<%=cart.getPrezzo()%>€
		</h3>
	</div>
	<%
		}
	%>
</body>
</html>