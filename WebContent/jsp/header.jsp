<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	UserBean usr = (UserBean) request.getSession().getAttribute("user");
	AdminBean adm = (AdminBean) request.getSession().getAttribute("admin");
	Cart crt = (Cart) request.getSession().getAttribute("cart");
%>

<!DOCTYPE html>
<html>
<%@ page contentType="text/html; charset=UTF-8"
	import="java.util.*, it.quattrocchi.control.*, it.quattrocchi.support.*, it.quattrocchi.*"%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
	<!-- CSS  -->
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
		rel="stylesheet">
	
	<link href="css/bootstrap.css" type="text/css" rel="stylesheet"
		media="screen,projection" />
	
	<link href="css/header.css" type="text/css" rel="stylesheet"
		media="screen,projection" />
	
	<!--Let browser know website is optimized for mobile-->
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
</head>
<body>
	<nav class="navbar navbar-default ">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#myNavbar">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand  navbar-brand-left" href="article">Quattrocchi.it</a>
			</div>
			<div class="collapse navbar-collapse" id="myNavbar">
			<ul class="nav navbar-nav navbar-centered">
				<li><a href="article?action=category&type=occhiale">Occhiali</a>
				<li><a href="article?action=category&type=lente">Lentine</a>
				<li>
					<form name="ricerca" target="article" method="get">
						<input name="daCercare" type="text" />
					</form>
			</ul>
				<ul class="nav navbar-nav navbar-right">
					<%
						if (adm == null) {
					%>
					<li><img src="image/cart.png" alt="carrello:"
						style="max-height: 50px;"></li>
					<%
						if (crt == null) {
					%>
					<li><a href="checkout?action=checkout">0</a></li>
					<%
						} else {
					%>
					<li><a href="checkout?action=checkout"><%=crt.getNumberOfProducts()%></a></li>
					<%
						}
						}
						if (usr == null && adm == null) {
					%>
					<li><a href="access">Login / Register</a></li>
					<%
						} else if (usr != null) {
					%>
					<li><a href="user">Benvenuto, <%=usr.getUser()%>
					</a></li>
					<li><a href="user?action=logout">logout</a></li>
					<%
						} else if (adm != null) {
					%>
					<li><a href="user">Benvenuto, <%=adm.getUser()%>
					</a></li>
					<li><a href="user?action=logout">logout</a></li>
					<%
						}
					%>
				</ul>
			</div>
		</div>
	</nav>
</body>
</html>