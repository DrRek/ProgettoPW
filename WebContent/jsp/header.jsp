<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!-- CSS  -->
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">

<link href="css/bootstrap.css" type="text/css" rel="stylesheet"
	media="screen,projection" />

<link href="css/style.css" type="text/css" rel="stylesheet"
	media="screen,projection" />

<!--Let browser know website is optimized for mobile-->
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

</head>
<body>

	<!-----------------------------------Inizio NavBar--------------------------->
	<nav class="navbar navbar-default">
	<div class="container-fluid">
		<!------ Area Bottone mobile display ------>
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<!--Fine Area Bottone mobile display-->
			<a class="navbar-brand" href="#">LOGO(DA INSERIRE)</a>
		</div>
		<!----------------------------------- Collect the nav links, forms, and other content for toggling ----------------------------->
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li class="active"><a href="#">HOME <span class="sr-only">(current)</span></a></li>
				<li><a href="#">AZIENDA</a></li>
				<li class="dropdown">
					<!--Bottone dropdown--> <a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">OCCHIALI VISTA <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="#">Action</a></li>
						<li><a href="#">Another action</a></li>
						<li><a href="#">Something else here</a></li>
						<li role="separator" class="divider"></li>
						<li><a href="#">Separated link</a></li>
						<li role="separator" class="divider"></li>
						<li><a href="#">One more separated link</a></li>
					</ul>
				</li>
				<li class="dropdown">
					<!--Bottone dropdown--> <a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">OCCHIALI SOLE <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="#">Action</a></li>
						<li><a href="#">Another action</a></li>
						<li><a href="#">Something else here</a></li>
						<li role="separator" class="divider"></li>
						<li><a href="#">Separated link</a></li>
						<li role="separator" class="divider"></li>
						<li><a href="#">One more separated link</a></li>
					</ul>
				</li>
				<li class="dropdown">
					<!--Bottone dropdown--> <a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">LENTI A CONTATTO <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="#">Action</a></li>
						<li><a href="#">Another action</a></li>
						<li><a href="#">Something else here</a></li>
						<li role="separator" class="divider"></li>
						<li><a href="#">Separated link</a></li>
						<li role="separator" class="divider"></li>
						<li><a href="#">One more separated link</a></li>
					</ul>
				</li>
			</ul>

			<!-----------------------------------Navbar-right  ------------------------------->
			<ul class="nav navbar-nav navbar-right">
				<li><a href="#">ACCOUNT</a></li>

				<!------------------------------form  Cerca---------------------------->
				<form class="navbar-form navbar-left" role="search">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="Cerca">
					</div>
					<button type="submit" class="btn btn-success">
						<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
					</button>
				</form>
			</ul>
		</div>
	</div>
	</nav>
	<!------------------------------fine NavBar---------------------------->

</body>
</html>