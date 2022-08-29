<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="modele.Caissier"%>


<!DOCTYPE html>
<html lang="en">
<head>
	<title>GAME CENTER</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<link rel="icon" type="image/png" href="ressources/images/icons/favicon.ico"/>
	<link rel="stylesheet" type="text/css" href="ressources/vendor/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="ressources/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" type="text/css" href="ressources/fonts/Linearicons-Free-v1.0.0/icon-font.min.css">
	<link rel="stylesheet" type="text/css" href="ressources/vendor/animate/animate.css">
	<link rel="stylesheet" type="text/css" href="ressources/vendor/css-hamburgers/hamburgers.min.css">
	<link rel="stylesheet" type="text/css" href="ressources/vendor/select2/select2.min.css">

	<link rel="stylesheet" type="text/css" href="ressources/css/util.css">
	<link rel="stylesheet" type="text/css" href="ressources/css/main.css">

</head>
<body>
	<%--
		Caissier caissier = (Caissier) request.getAttribute("menu");
	    out.println(caissier.getLoginc())
		//
	--%>
	<div class="limiter">
		<div class="container-login100" style="background-image: url('ressources/images/img-01.jpg');">
			<div class="wrap-login100 p-t-90 p-b-30">
				<form class="login100-form validate-form" action="authentication" method="POST">
					<div class="login100-form-avatar">
						<img src="ressources/images/betling.png" alt="Superbet" height="160" width="160">
					</div>

					<span class="login100-form-title p-t-20 p-b-45">
						 ${form.resultat}
					</span>

					<div class="wrap-input100 validate-input m-b-10" data-validate = "Username is required">
						<input class="input100" type="text" id="name" name="username" value="<c:out value="${caissier.loginc}"/>" placeholder="Username">
						<span class="focus-input100"></span>
						<span class="symbol-input100">
							<i class="fa fa-user"></i>
						</span>
					</div>

					<div class="wrap-input100 validate-input m-b-10" data-validate = "Password is required">
						<input class="input100" type="password" id="password" name="pass" value="" placeholder="Password">
						<span class="focus-input100"></span>
						<span class="symbol-input100">
							<i class="fa fa-lock"></i>
						</span>
					</div>
					
					<div class="container-login100-form-btn p-t-10">
						<button class="login100-form-btn">
							Login
						</button>
					</div>

				</form>
				<!--<a href="ressources/../html-link.htm" target="popup" onclick="window.open('../frontend/cashier.html','name','width=1600,height=900')">
				 Open page in new window</a>

		<a href="ressources/../frontend/cashier.html" title="Ouverture d'une nouvelle fenêtre popup" id="lien"  target="nom_Popup" 
			onclick="window.open(this.href,'nom_Popup','​height=800 , width=400 ,location=1 , resizable=1 ,scrollbars=no');return false;">
					&rarr; Exemple : ouverture d'une fenêtre en popup  &larr;</a>-->

			</div>
		</div>
	</div>
	
	

	
	
	<script src="ressources/vendor/jquery/jquery-3.2.1.min.js"></script>
	<script src="ressources/vendor/bootstrap/js/popper.js"></script>
	<script src="ressources/vendor/bootstrap/js/bootstrap.min.js"></script>
	<script src="ressources/vendor/select2/select2.min.js"></script>
	<script src="ressources/js/main.js"></script>
	<script>
	//console.log('screen '+screen.width+' , '+screen.height);
		//setTimeout(function(){ window.location = '../frontend/cashier.html'; }, 3000);
		setTimeout(function(){ console.log('TEST');
		//$('#lien').click();
		//console.log('iTEST');
		}, 3000);
		//$('#lien').click();mywindow.moveTo(0, 0);

	</script>

</body>
</html>