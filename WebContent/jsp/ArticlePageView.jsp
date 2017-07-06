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
						<div class="product col-sm-3 service-image-left">
							<center>
								<img src="/catalogoPW/<%=occhiali.getImg1().replaceAll(" ", "_").trim()%>" alt="pic1"></img>
							</center>
						</div>

						<div class="container service1-items col-sm-2 col-sm-2 pull-left">
							<center>
								<a class="service1-item"> <img
									src="/catalogoPW/<%=((GlassesBean) occhiali).getImg2().replaceAll(" ", "_").trim()%>" alt="img"></img>
								</a> <a class="service1-item"> <img
									src="/catalogoPW/<%=((GlassesBean) occhiali).getImg3().replaceAll(" ", "_").trim()%>" alt="img"></img>
								</a>
							</center>
						</div>
					</div>

					<div class="col-sm-6">
						<div class="product-title" id="oNome"><%=occhiali.getNome()%></div>
						<h4 id="oMarca"><%=occhiali.getMarca()%></h4>
						<hr>
						<div class="product-desc"><%=((GlassesBean) occhiali).getDescrizione()%></div>

						<div> <span class="product-price"><%=occhiali.getPrezzo()%> € </span>
						<%if(occhiali.getSconto() > 0){
								if(occhiali.getRealPrezzo()!= occhiali.getPrezzo()){
								
								%>
							<span class="old price"><%=occhiali.getRealPrezzo()%> € </span>
	    	   		
							<%}} %>
						</div>
						<br><br>
						<div style="float:right">
						<span class="product-stock" id="oDisp" >
							<%=occhiali.getDisponibilita()%>
							left
						</span>

						<%
							if (admin == null && occhiali.getDisponibilita()>0) {
						%>
						
						<div style="margin-left:10px"class="btn-group cart" >
							<button type="button" id="oAddCart"
								class="btn btn-outline-secondary">Add to cart</button>
						</div>
						<%
							} else if(admin != null) {
						%>
						<div>
						<hr>
						<input class="btn btn-outline-secondary" type="number" step="1"
							min="1" name="oQuantita" /> <input
							class="btn btn-outline-secondary" id="AddGlassToStorage"
							type="button" value="update" /></div>
						<%
							}
						%>
						</div>
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

						<div class="product col-sm-3 service-image-left">

							<center>
								<img src="/catalogoPW/<%=l.getImg1().replaceAll(" ", "_").trim()%>" alt="pic1"></img>
							</center>
						</div>
						<div class="product-title" id="lNome"><%=l.getNome()%></div>
						<h4 id="lMarca"><%=l.getMarca()%></h4>
						<hr>
						<div class="row">
						<div class="col-sm-2">
						<br>
						<div class="etichetta">Tipologia</div>
						<div class="value"><%=((ContactLensesBean) l).getTipologia()%></div>
						</div>
						
						<div class="col-sm-3">
						<br>
						<div class="value"><%=((ContactLensesBean) l).getNumeroPezziNelPacco()%>
							pieces in a box
						</div>
						</div>
						
						<div class="col-sm-2">
						<br>
						<div class="etichetta">Raggio</div>
						<div class="value"><%=((ContactLensesBean) l).getRaggio()%>
							mm</div>
						
						</div>
						<div class="col-sm-2">
						<br>
						<div class="etichetta">Diametro</div>
						<div class="value"><%=((ContactLensesBean) l).getDiametro()%>
							mm</div>
						</div>

						<div class="col-sm-3">
						<br>
						<div class="etichetta">Colore</div>
						<div class="value"><%=((ContactLensesBean) l).getColore()%>
						</div>
						</div>

							
							<div class="col-sm-2">
							<br><span class="product-price"><%=l.getPrezzo()%> € </span>
							<%if(l.getSconto() > 0){
								if(l.getRealPrezzo()!= l.getPrezzo()){
								
								%>
							<span class="old price"><%=l.getRealPrezzo()%> € </span>
	    	   		
							<%}} %>
							</div>
						</div>
							<div style="float:right">
							<select class="product-stock btn btn-outline-secondary" id="gradazione">
								<%
								boolean selectIsEmpty = true;
								for (ArticleBean e : lentine) {
								%>
								<% if (e.getDisponibilita()>0 || admin!=null){ 
									selectIsEmpty = false; %>
								<option 
									value="<%=((ContactLensesBean) e).getGradazione()%>">
									<%=((ContactLensesBean) e).getGradazione()%> 
									(<%=e.getDisponibilita()%> left)
								</option>

								<%
								}}
								%>
							</select>
							<%
								if (admin == null) {
									if(!selectIsEmpty){
							%>

							<input style="margin-left:10px"class="btn btn-outline-secondary" type="submit"
								id="lAddCart" value="Add to cart" />



							<%
									}
									} else {
							%>
							<hr><select class="btn btn-outline-secondary product-stock" name="lGradazione">
								<option value="8.0">+8.00</option>
								<option value="7.5">+7.50</option>
								<option value="7.0">+7.00</option>
								<option value="6.5">+6.50</option>
								<option value="6.0">+6.00</option>
								<option value="5.5">+5.50</option>
								<option value="5.0">+5.00</option>
								<option value="4.5">+4.50</option>
								<option value="4.0">+4.00</option>
								<option value="3.5">+3.50</option>
								<option value="3.0">+3.00</option>
								<option value="2.5">+2.50</option>
								<option value="2.0">+2.00</option>
								<option value="1.5">+1.50</option>
								<option value="1.0">+1.00</option>
								<option value="0.5">+0.50</option>
								<option value="0.0" selected>±0.00</option>
								<option value="-0.5">-0.50</option>
								<option value="-1.0">-1.00</option>
								<option value="-1.5">-1.50</option>
								<option value="-2.0">-2.00</option>
								<option value="-2.5">-2.50</option>
								<option value="-3.0">-3.00</option>
								<option value="-3.5">-3.50</option>
								<option value="-4.0">-4.00</option>
								<option value="-4.5">-4.50</option>
								<option value="-5.0">-5.00</option>
								<option value="-5.5">-5.50</option>
								<option value="-6.0">-6.00</option>
								<option value="-6.5">-6.50</option>
								<option value="-7.0">-7.00</option>
								<option value="-7.5">-7.50</option>
								<option value="-8.0">-8.00</option>
							</select> <input class="btn btn-outline-secondary product-stock" type="number" step="1"
								min="1" name="lQuantita" /> <input class="btn btn-outline-secondary" id="AddLenseToStorage"
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
			}
		%>
		<%@ include file="../jsp/footer.jsp"%>	
		<script src="js/article-page.js"></script>
</body>
</html>