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

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>


<body>
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
	<div class="container">
		<table class="table-bordered">
			<tr>
				<th>Nome</th>
				<th>Marca</th>
				<th>Numero Prodotti</th>
				<th>Prezzo</th>
				<th>Opzioni</th>
			</tr>
			<%
				List<CartArticle> prodcart = cart.getProducts();
					for (CartArticle beancart : prodcart) {
			%>
			<tr>
				<td><%=beancart.getArticle().getNome()%></td>
				<td><%=beancart.getArticle().getMarca()%></td>
				<!-- non ancora logicamente corretto -->
				<td><input name="numeroPezziDisponibili" type="number" min="1"
					value="<%=beancart.getQuantity()%>"></td>
				<td><%=beancart.getPrezzo()%>€</td>
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
				} 
				else
				{
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