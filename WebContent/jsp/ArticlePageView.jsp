<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	ArticleBean occhiali = (ArticleBean) request.getAttribute("occhiali");
	ArrayList<ContactLensesBean> lentine = (ArrayList<ContactLensesBean>) request.getAttribute("lentine");
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
	<div class="container">
	<%
	if (lentine == null && occhiali != null)
	{
		%>

		<h1 id="nome"><%=occhiali.getNome() %></h1>
		<h2 id="marca"><%=occhiali.getMarca() %></h2>
		<h4>Prezzo:</h4>
		<p><%=occhiali.getPrezzo() %></p>
		<h4>Descrizione:</h4>
		<p><%=((GlassesBean) occhiali).getDescrizione() %></p>
		<h4>Sesso:</h4>
		<p><%=((GlassesBean) occhiali).getSesso() %></p>
		<h4>Numero pezzi disponibili:</h4>
		<p><%=occhiali.getDisponibilita() %></p>
	<%
	}
	else if(occhiali == null && lentine != null)
	{
		ArticleBean l = lentine.get(0);
	%>
		<h1><%=l.getNome() %></h1> 
		<h2><%=l.getMarca() %></h2> 
		<h4>Prezzo</h4>
		<p><%=l.getPrezzo()%></p>
		<h4>Tipologia:</h4>
		<p><%=((ContactLensesBean)l).getTipologia() %></p> 
		<h4>Pezzi per scatola:</h4>
		<p><%=((ContactLensesBean)l).getNumeroPezziNelPacco() %> </p>
		<h4>Raggio:</h4>
		<p><%=((ContactLensesBean)l).getRaggio() %></p>
		<h4>Diametro:</h4>
		<p><%=((ContactLensesBean)l).getDiametro() %></p>
		<h4>Colore:</h4>
		<p><%=((ContactLensesBean)l).getColore() %></p>
		<h4>Gradazione</h4>
			<select id="gradazione">
				<%
				for(ArticleBean e : lentine)
				{
				%>
				
					<option value="<%=((ContactLensesBean)e).getGradazione()%>"><%=((ContactLensesBean)e).getGradazione()%></option>
	
				<%
				}%>
			</select>
			<a id="addCart">add to cart</a>
	<%
	}
	%>
	</div>
	<!-- 
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
	-->
</body>
</html>