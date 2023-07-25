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
				<form class="login100-form validate-form" action="authentication" method="POST" id="login">
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

				<form class="login100-form validate-form" action="authentication" id="slogin">

					<div class="wrap-input100 validate-input m-b-10" data-validate = "Username is required">
						<input class="input100" type="text" id="admin-name" name="username" placeholder="Admin name" autocomplete="false">
						<span class="focus-input100"></span>
						<span class="symbol-input100">
							<i class="fa fa-user"></i>
						</span>
					</div>

					<div class="wrap-input100 validate-input m-b-10" data-validate = "Password is required">
						<input class="input100" type="password" id="admin-password" name="pass" placeholder="Password"
						autocomplete="false">

						<span class="focus-input100"></span>
						<span class="symbol-input100">
							<i class="fa fa-lock"></i>
						</span>
					</div>
					<div class="container-login100-form-btn p-t-10" id="create-admin">
						<button type="button" class="login100-form-btn">
							Creer un super admin
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

   let urlServeur = "http://127.0.0.1:9090/api/v1/supergames";
   async function getUrlServer() {
       
         let intervalUrlServer =  setInterval(async function(){
          if(urlServeur == undefined){
            $.ajax({
                url:"paramserver",
                type:"GET",
                data:{
                  'partner':coderace
                },
                success:function(result){ 
                    $.each(result,  async function(index, value){
                        urlServeur = await value.server;
                        console.log("urlServeur: "+urlServeur);
                           
                    });
                }
              });
          }
          else{
             clearInterval(intervalUrlServer);
          }
        
      },1000);
    }

    getUrlServer();

 	async function fetchAdmin() {
       
            if(urlServeur != undefined){

               var url = urlServeur+'/superadmin';
                const response = await fetch(url);
                const myJson = await response.json(); //extract JSON from the http response
                  // do something with myJson
                var admin = JSON.stringify(myJson);
               
                if(parseInt(admin) == 0) {
                	
               	 	document.getElementById("login").style.display = "none";
               	 	document.getElementById("slogin").style.display = "block";
                }
                else{
                	document.getElementById("login").style.display = "block";
                	document.getElementById("slogin").style.display = "none";
                }
            }
           
    }

    fetchAdmin();

    $('#create-admin').click(async function(){
          console.log('create-admin');
          var adminName = $("#admin-name").val();
          var adminPassword = $("#admin-password").val();

          var login = adminName.split(" ");
          var logicLogin = adminName.split(".");
          var longueurLogin = login.length
          console.log('longueurLogin ' + longueurLogin);

          if(adminName === '' || adminPassword == '') {
          	 alert('veuillez renseigner un login admin valide');
          	 return;
          }
          else if (logicLogin.length > 1) {
          	alert('Le login ne doit pas contenir des points');
          }
          else if (longueurLogin > 1) {
          	alert('Le login ne doit pas contenir des espaces');
          }
          else if(adminPassword.length < 6) {
             alert('Le mot de passe doit avoir au minimum 6 caracteres');
          	 return;
          }
          else if(adminName.length < 5) {
             alert('Le login doit avoir au minimum 5 caracteres');
          	 return;
          }
          else{
          	console.log('admin-name ' + adminName);
          	console.log('admin-password ' + adminPassword);

          	if(urlServeur != undefined){

               var url = urlServeur+'/save-user/' + adminName + '/' + adminPassword;
               const response = await fetch(url);
               const myJson = await response.json(); //extract JSON from the http response
                  
               var status = myJson.status

               if (status == 200) {

               		console.log('status ok');
	               	var admin = JSON.stringify(myJson.entity);
	               	var resultCode = JSON.parse(admin)
	               	var code = resultCode.code

	               	if (parseInt(code) == 200) {

	               		console.log('login admin '+ resultCode.cais['loginc']);

	               		document.getElementById("login").style.display = "block";
                		document.getElementById("slogin").style.display = "none";

	               	}
	               	else{
	               		alert(admin.error + '. Erreur lors de la creation. Veuillez reessayer.');
          	 			return;
	               	}
	               	
               }
               else{

               		alert('Erreur lors de la creation. Veuillez reessayer.');
          	 		return;

               }
               
               
                
            }

          }
          
    });

	</script>

</body>
</html>