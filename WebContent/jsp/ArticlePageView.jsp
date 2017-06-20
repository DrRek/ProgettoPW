<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	ArticleBean bean = (ArticleBean) request.getAttribute("articolo");
%>

<!DOCTYPE html>
<html>
<%@ page contentType="text/html; charset=UTF-8"
	import="java.util.*, it.quattrocchi.control.*, it.quattrocchi.support.*, it.quattrocchi.*, it.quattrocchi.model.*"%>
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
	<%@ include file="../jsp/header.jsp"%><br>
	Pagina di esempio, per ora mostra i dati temporanei degli articoli
	<br> in testa andrebbe mostrata l'immagine figa
	<br>
	<br> Nome:
	<%=bean.getNome() %><br> Marca:
	<%=bean.getMarca() %><br> Prezzo:
	<%=bean.getPrezzo() %><br>
	<% if (bean.getTipo().equalsIgnoreCase("O")){%>
	Descrizione:
	<%=bean.getDescrizione() %><br> Sesso:
	<%=bean.getSesso() %><br> Numero pezzi disponibili:
	<%=bean.getNumeroPezziDisponibili() %><br>
	<%}else{ %>
	Tipologia:
	<%=bean.getTipologia() %><br> Pezzi per scatola:
	<%=bean.getNumeroPezziNelPacco() %><br>
	<table class="table-bordered">
		<select>
			<%int i = 0;
			for(SingleContactLenseBean e : bean.getLentine()){%>
			<option value="<%=e.getModello()%>"><%=e.getGradazione()%></option>

			<%
			i++;
			}%>
		</select>
		<a id="cart">add to cart</a>
		<%}%>
	
</body>
</html>