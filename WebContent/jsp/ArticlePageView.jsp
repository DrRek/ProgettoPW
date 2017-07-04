<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	ArticleBean occhiali = (ArticleBean) request.getAttribute("occhiali");
	ArrayList<ContactLensesBean> lentine = (ArrayList<ContactLensesBean>) request.getAttribute("lentine");
	AdminBean admin = (AdminBean) request.getSession().getAttribute("admin");
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

<link href="css/ArticlePageView.css" type="text/css" rel="stylesheet"
	media="screen,projection" />

<!--Let browser know website is optimized for mobile-->
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

</head>

<body>
	<%@ include file="header.jsp"%>
	<%
		if (lentine == null && occhiali != null) {
	%>
	<script>
		$(document).ready(function() {
			$("#oAddCart").click(function(event) {
				$.ajax({
					type : "POST",
					url : "articlePage",
					data : {
						action : "addCart",
						nome : $("#oNome").html(),
						marca : $("#oMarca").html(),
					},
					success : function() {
						var s = $("#count").html();
						var num = parseInt(s);
						$("#count").html(num + 1);
					}

				});
			});

		})
	</script>

	<br>
	<br>
	<br>
	<br>
	<div class="container-fluid">
		<div class="content-wrapper">
			<div class="item-container">
				<div class="container">
					<div>
						<div class="product col-md-3 service-image-left">

							<center>
								<img src="image/placeholder_occhiali.jpg" alt="pic1"></img>
							</center>
						</div>

						<div class="container service1-items col-sm-2 col-md-2 pull-left">
							<center>
								<a class="service1-item"> <img
									src="image/placeholder_occhiali.jpg" alt="pic2"></img>
								</a> <a class="service1-item"> <img
									src="image/placeholder_occhiali.jpg" alt="pic3"></img>
								</a>
							</center>
						</div>
					</div>

					<div class="col-md-6">
						<div class="product-title" id="oNome"><%=occhiali.getNome()%></div>
						<h4 id="oMarca"><%=occhiali.getMarca()%></h4>
						<div class="product-desc"><%=((GlassesBean) occhiali).getDescrizione()%></div>
						<hr>
						<div class="product-price"><%=occhiali.getPrezzo()%>
							€
						</div>
						<div class="product-stock" id="oDisp">
							<%=occhiali.getDisponibilita()%>
							left
						</div>

						<%
							if (admin == null) {
						%>
						<hr>
						<div class="btn-group cart">
							<button type="button" id="oAddCart"
								class="btn btn-outline-secondary">Add to cart</button>
						</div>
						<%
							} else {
						%>
						<h4>In stock:</h4>
						<input class="btn btn-outline-secondary" type="number" step="1"
							min="1" name="oQuantita" /> <input
							class="btn btn-outline-secondary" id="AddGlassToStorage"
							type="button" value="update" />
						<%
							}
						%>
					</div>
				</div>
			</div>

		</div>
	</div>
	<%
		} else if (occhiali == null && lentine != null) {
			ArticleBean l = lentine.get(0);
	%>
	<script>
		$(document).ready(
				function() {
					$("#lAddCart").click(
							function(event) {
								$.ajax({
									type : "POST",
									url : "articlePage",
									data : {
										action : "addCart",
										nome : $("#lNome").html(),
										marca : $("#lMarca").html(),
										gradazione : $('#gradazione').find(
												":selected").attr('value')
									},
									success : function() {
										var s = $("#count").html();
										var num = parseInt(s);
										$("#count").html(num + 1);
									}

								});
							});
				})
	</script>
	<br>
	<br>
	<br>
	<br>
	<div class="container-fluid">
		<div class="content-wrapper">
			<div class="item-container">
				<div class="container">
					<div>
						<div class="product col-md-3 service-image-left">

							<center>
								<img src="image/placeholder_lentine.jpg" alt="pic1"></img>
							</center>
						</div>
					</div>
						<div class="product-title" id="lNome"><%=l.getNome()%></div>
						<h4 id="lMarca"><%=l.getMarca()%></h4>
						<hr>
						<div class="row">
						<div class="col-md-2">
						<br>
						<div class="etichetta">Tipologia</div>
						<div class="value"><%=((ContactLensesBean) l).getTipologia()%></div>
						</div>
						
						<div class="col-md-2">
						<br>
						<div class="value"><%=((ContactLensesBean) l).getNumeroPezziNelPacco()%>
							pieces in a box
						</div>
						</div>
						
						<div class="col-md-2">
						<br>
						<div class="etichetta">Raggio</div>
						<div class="value"><%=((ContactLensesBean) l).getRaggio()%>
							mm</div>
						</div>
						
						<div class="col-md-2">
						<br>
						<div class="etichetta">Diametro</div>
						<div class="value"><%=((ContactLensesBean) l).getDiametro()%>
							mm</div>
						</div>

						<%
							if (!((ContactLensesBean) l).getColore().equals("/N")) {
						%>
						<div class="col-md-2">
						<br>
						<div class="etichetta">Colore</div>
						<div class="value"><%=((ContactLensesBean) l).getColore()%>
						</div>
						</div>
						<%
							}
						%>
						</div>
						<hr>
							<div class="product-price"><%=l.getPrezzo()%>
								€
							</div>
							<hr>
							<select class="product-stock" id="gradazione">
								<%
									for (ArticleBean e : lentine) {
								%>

								<option class="product-stock"
									value="<%=((ContactLensesBean) e).getGradazione()%>">
									<%=((ContactLensesBean) e).getGradazione()%> degree |
									<%=e.getDisponibilita()%> left
								</option>

								<%
									}
								%>
							</select>
							<%
								if (admin == null) {
							%>

							<input class="btn btn-outline-secondary" type="submit"
								id="lAddCart" value="add to cart" />



							<%
								} else {
							%>
							<h4>In stock:</h4>
							<h4>Gradation:</h4>
							<select class="btn btn-outline-secondary" name="lGradazione">
								<option value="+8.00">+8.00</option>
								<option value="+7.50">+7.50</option>
								<option value="+7.00">+7.00</option>
								<option value="+6.50">+6.50</option>
								<option value="+6.00">+6.00</option>
								<option value="+5.50">+5.50</option>
								<option value="+5.00">+5.00</option>
								<option value="+4.50">+4.50</option>
								<option value="+4.00">+4.00</option>
								<option value="+3.50">+3.50</option>
								<option value="+3.00">+3.00</option>
								<option value="+2.50">+2.50</option>
								<option value="+2.00">+2.00</option>
								<option value="+1.50">+1.50</option>
								<option value="+1.00">+1.00</option>
								<option value="+0.50">+0.50</option>
								<option value="0" selected>±0.00</option>
								<option value="-0.50">-0.50</option>
								<option value="-1.00">-1.00</option>
								<option value="-1.50">-1.50</option>
								<option value="-2.00">-2.00</option>
								<option value="-2.50">-2.50</option>
								<option value="-3.00">-3.00</option>
								<option value="-3.50">-3.50</option>
								<option value="-4.00">-4.00</option>
								<option value="-4.50">-4.50</option>
								<option value="-5.00">-5.00</option>
								<option value="-5.50">-5.50</option>
								<option value="-6.00">-6.00</option>
								<option value="-6.50">-6.50</option>
								<option value="-7.00">-7.00</option>
								<option value="-8.50">-7.50</option>
								<option value="-8.00">-8.00</option>
							</select> <input class="btn btn-outline-secondary" type="number" step="1"
								min="1" name="lQuantita" /> <input id="AddLenseToStorage"
								type="button" value="update" />

							<%
								}
							%>
						</div>
					</div>
			</div>
		</div>

		<%
			}
		%>
		<script src="js/article-page.js"></script>
</body>
</html>