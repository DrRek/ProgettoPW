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
	<h1 id="nome"><%=bean.getNome() %></h1>
	<h2 id="marca"><%=bean.getMarca() %></h2>
	<h4>Prezzo:</h4>
	<p><%=bean.getPrezzo() %></p>
	<% if (bean.getTipo().equalsIgnoreCase("O")){%>
	<h4>Descrizione:</h4>
	<p><%=bean.getDescrizione() %></p>
	<h4>Sesso:</h4>
	<p><%=bean.getSesso() %></p>
	<h4>Numero pezzi disponibili:</h4>
	<p><%=bean.getNumeroPezziDisponibili() %></p>
	<%}else{ %>
	<h4>Tipologia:</h4>
	<p><%=bean.getTipologia() %></p> 
	<h4>Pezzi per scatola:</h4>
	<p><%=bean.getNumeroPezziNelPacco() %></p>
	<script>
	$(document).ready(function() 
			{
				  $("#addCart").click(function(event)
					{
					  $.ajax({
						    type: "POST",
						    url: "article",
						    data: { action: "addCart"},
						    dataType: "json",
						    success: function (responseText) {
						    	$.each(responseText, function(i, articleObject) {
				    	 			$("#demo").append(articleObject.nome + "<br>");
				    			});
						    }

						});
				  	});
					
			})
	</script>
	<h4>Modello</h4>
		<select id="modello">
			<%int i = 0;
			for(SingleContactLenseBean e : bean.getLentine()){%>
			<option value="<%=e.getModello()%>">modello <%=e.getModello()%>: <%=e.getRaggio() %>mm <%=e.getGradazione()%>°</option>

			<%
			i++;
			}%>
		</select>
		<a id="addCart">add to cart</a>
		<%}%>
	
</body>
</html>