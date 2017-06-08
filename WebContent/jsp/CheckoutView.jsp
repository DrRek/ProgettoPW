<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	Cart cart = (Cart) request.getSession().getAttribute("cart");
	Collection<?> cc = (Collection<?>) request.getSession().getAttribute("ccards");
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

<body>
	<%@ include file="header.jsp"%>
	<div class="container">
		<h2>Cart</h2>
		<table class="table-bordered">
			<tr>
				<th>Nome</th>
				<th>Marca</th>
				<th>Numero Prodotti</th>
				<th>Prezzo</th>
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
			</tr>
			<%
				}
			%>
		</table>

		<select name="stato">
			<option selected="" value="Default">(Please select a credit
				card)</option>
			<%
				if (cc != null && cc.size() != 0) {
					int i = 0;
					Iterator<?> it = cc.iterator();
					while (it.hasNext()) {
						i++;
						CreditCardBean bean = (CreditCardBean) it.next();
			%>

			<option value=<%=i%>><%=bean.getNumeroCC()%></option>
			<%
				}
				}
			%>
		</select>
	</div>
	
	<div class="container">
		<h3>
			Prezzo totale:
			<%=cart.getPrezzo()%>€
		</h3>
		<form name='checkout' action='checkout' method="post">
			<input type="hidden" name="action" value="submit"> <input
				name="submit" value="Completa il pagamento!" type="submit">

		</form>
	</div>
</body>
</html>