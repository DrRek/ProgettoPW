<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	UserBean usr = (UserBean) request.getSession().getAttribute("user");
	AdminBean adm = (AdminBean) request.getSession().getAttribute("admin");
	Cart crt = (Cart) request.getSession().getAttribute("cart");
%>
<%@ page contentType="text/html; charset=UTF-8"
	import="java.util.*, it.quattrocchi.control.*, it.quattrocchi.support.*, it.quattrocchi.*, it.quattrocchi.model.*"%>

<link href="css/header.css" type="text/css" rel="stylesheet"
	media="screen,projection" />
<nav class="navbar navbar-default navbar-fixed-top">
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
				<li><a href="article?toDo=searchOcchiale">Occhiali</a>
				<li><a href="article?toDo=searchLente">Lentine</a>
				<li>
					<form class="navbar-form" action="article" method="get">
						<div class="input-group">
							<input type="hidden" name="toDo" value="searchFromOtherPage">
							<input name="daCercare" type="text" class="form-control"
								placeholder="Cerca"/>
								<span class="input-group-btn">
						<button type="submit" class="btn btn-default">Submit</button></span>
						</div>
					</form>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<%
						if (adm == null) {
					%>
				<li><a href="checkout"><span><img src="image/cart.png" alt="carrello:"
					style="max-height: 50px;"></span>
				<%
						if (crt == null) {
					%>
				<span id="count">0</span></a></li>
				<%
						} else {
					%>
				<span id="count"><%=crt.getNumberOfProducts()%></span></a></li>
				<%
						}
						}
						if (usr == null && adm == null) {
					%>
				<li><a href="access">Login / Register</a></li>
				<%
						} else if (usr != null) {
					%>
				<li><a href="user">Benvenuto, <%=usr.getUser()%></a></li>
				<li><a href="user?action=logout">logout</a></li>
				<%
						} else if (adm != null) {
					%>
				<li><a href="user">Benvenuto, <%=adm.getUser()%></a></li>
				<li><a href="user?action=logout">logout</a></li>
				<%
						}
					%>
			</ul>
		</div>
	</div>
	<script src="js/bootstrap.js"></script>
</nav>
