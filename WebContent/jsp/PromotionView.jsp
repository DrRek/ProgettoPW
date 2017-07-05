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

<link href="css/UserView.css" type="text/css" rel="stylesheet"
	media="screen,projection" />

<!--Let browser know website is optimized for mobile-->
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>

<body>
	<%@ include file="../jsp/header.jsp"%>
	<br><br><br><br>
	<div class="container">
		<h2>"<%=pro.getNome() %>" promotion</h2><br>
		<h4><%=pro.getDescrizione() %></h4><hr>
		<div class="row">
		<%if(pro.getTipo().equals("%")){%>
		<div class="col-sm-3">
		<h5>Sconto: <%=pro.getSconto() %> <%=pro.getTipo() %></h5>
		</div>
		<%} else{%>
		<div class="col-sm-3">
		<h5>Sconto: <%=pro.getTipo() %><%=pro.getSconto() %> €</h5>
		</div>
		<%} %>
		<div class="col-sm-3">
		<h5>Data inzio: <%=pro.getDataInizio() %></h5>
		</div>
		<div class="col-sm-3">
		<h5>Data fine: <%=pro.getDataFine() %></h5>
		</div>
		<div class="col-sm-3">
		<h5><% if(!pro.isCumulabile()){ %>NOT <%} %>cumulable.</h5>
		</div></div>
		<br><hr>
		<h3>Apply the discount on:</h3>
		<input id="aPromotion" type="hidden" value="<%=pro.getNome()%>"/>
		<input class="form-control" id="aName" type="text" placeholder="Article Name"/>
		<input class="form-control" id="aBrand" type="text" placeholder="Article Brand"/>
		<input class="btn btn-outline-secondary" id="aSubmit" type="button" value="Add article"/>
		<br><hr>
		<h3>Included product list</h3>
		<table id="tableIncludedArticle" class="table table-condensed">
			<thead>
				<tr>
					<th>Name</th>
					<th>Brand</th>
				</tr>
			</thead><tbody>
			<%for(ArticleBean a : pro.getValidi()) {%>
				<tr>
					<th data-th="Name"><%=a.getNome() %></th>
					<th data-th="Marca"><%=a.getMarca() %></th>
				</tr>
			<%} %>
		</tbody></table>
	</div>
	<script src="js/promotion.js"></script>
</body>
</html>