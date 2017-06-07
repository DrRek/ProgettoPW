<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	 String nomeBean = request.getParameter("nome");
	 String marcaBean = request.getParameter("marca");
	 ArticleModel model = new ArticleModel();
	 ArticleBean bean = model.doRetrieveByKey(nomeBean, marcaBean);
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
		<%@ include file="../jsp/header.jsp"%>
		
		<center>
			<h2><%=bean.getNome()%></h2>
		
			<h3><%=bean.getMarca()%></h3>
		
			<!-- Immagine prodotto + descrizione -->
		</center>

</body>
</html>