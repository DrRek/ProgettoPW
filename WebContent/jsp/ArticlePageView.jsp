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

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>

<body>
	<%@ include file="../jsp/header.jsp"%>
	<br><br>
	<div class="container">
		<%
	if (lentine == null && occhiali != null)
	{
		%>
		<script>
		$(document).ready(function() 
				{
					  $("#oAddCart").click(function(event)
						{
						  $.ajax({
							    type: "POST",
							    url: "articlePage",
							    data: 
								    { 	action: "addCart",
								    	nome:$("#oNome").html(),
								    	marca:$("#oMarca").html(),
								    },
							    success: function () {
							    	var s = $("#count").html();
							    	var num = parseInt(s);
							    	$("#count").html(num+1);
							    }
	
							});
					  	});
						
				})
	</script>

		<h1 id="oNome"><%=occhiali.getNome() %></h1>
		<h2 id="oMarca"><%=occhiali.getMarca() %></h2>
		<h4>Prezzo:</h4>
		<p><%=occhiali.getPrezzo() %></p>
		<h4>Descrizione:</h4>
		<p><%=((GlassesBean) occhiali).getDescrizione() %></p>
		<h4>Sesso:</h4>
		<p><%=((GlassesBean) occhiali).getSesso() %></p>
		<h4>Numero pezzi disponibili:</h4>
		<p><%=occhiali.getDisponibilita() %></p>
		<input type="submit" id="oAddCart" value="add to cart"/>
		<%
	}
	else if(occhiali == null && lentine != null)
	{
		ArticleBean l = lentine.get(0);
	%>
		<script>
		$(document).ready(function(){
					$("#lAddCart").click(function(event){
						$.ajax({
							  type: "POST",
							  url: "articlePage",
							  data: 
								  { 	action: "addCart",
							 	 	 	nome:$("#lNome").html(),
						    			marca:$("#lMarca").html(),
								    	gradazione:$('#gradazione').find(":selected").attr('value')
								    },
							  success: function () {
								  var s = $("#count").html();
								  var num = parseInt(s);
								  $("#count").html(num+1);
							  }
	
						  });
					  })
				})
	</script>
		<h1 id="lNome"><%=l.getNome() %></h1>
		<h2 id="lMarca"><%=l.getMarca() %></h2>
		<h4>Prezzo</h4>
		<p><%=l.getPrezzo()%></p>
		<h4>Tipologia:</h4>
		<p><%=((ContactLensesBean)l).getTipologia() %></p>
		<h4>Pezzi per scatola:</h4>
		<p><%=((ContactLensesBean)l).getNumeroPezziNelPacco() %>
		</p>
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

			<option value="<%=((ContactLensesBean)e).getGradazione()%>"><%=((ContactLensesBean)e).getGradazione()%>:
				<%=e.getDisponibilita() %></option>

			<%
				}%>
		</select> <input type="submit" id="lAddCart" value="add to cart"/>



		<%
	}
	%>
	</div>
</body>
</html>