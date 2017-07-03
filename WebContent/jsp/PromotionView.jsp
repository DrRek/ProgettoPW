<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	PromotionBean pro = (PromotionBean) request.getAttribute("promozione");
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
		<h2>Nome: <%=pro.getNome() %></h2><br>
		<h4>Descrizione: <%=pro.getDescrizione() %></h4><br>
		<h4>Sconto: <%=pro.getSconto() %> Tipo: <%=pro.getTipo() %></h4><br>
		<h4>Data inzio: <%=pro.getDataInizio() %> Data fine: <%=pro.getDataFine() %></h4><br>
		<h4>This promotion is <% if(!pro.isCumulabile()){ %>NOT <%} %>cumulable.</h4><br><br>
		
		<h4>Add product to the promotion:</h4>
		<input id="aPromotion" type="hidden" value="<%=pro.getNome()%>"/>
		<input id="aName" type="text" placeholder="Article Name"/>
		<input id="aBrand" type="text" placeholder="Article Brand"/>
		<input id="aSubmit" type="button" value="Add article!"/>
		
		<h3>Included product list</h3>
		<table id="tableIncludedArticle" class="table table-condensed">
			<thead>
				<tr>
					<th>Name</th>
					<th>Brand</th>
				</tr>
			</thead>
			<%for(ArticleBean a : pro.getValidi()) {%>
				<tr>
					<th><%=a.getNome() %></th>
					<th><%=a.getMarca() %></th>
				</tr>
			<%} %>
		</table>
	</div>
	<script src="js/promotion.js"></script>
</body>
</html>