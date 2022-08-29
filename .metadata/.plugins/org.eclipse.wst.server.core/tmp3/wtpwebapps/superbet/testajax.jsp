<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Test validation AJAX</title>
	<script type="text/javascript">
	<!--
	var requete;
	
	function valider() {
	   var donnees = document.getElementById("donnees");
	   var url = "valider?valeur=" + escape(donnees.value);
	   if (window.XMLHttpRequest) {
	       requete = new XMLHttpRequest();
	   } else if (window.ActiveXObject) {
	       requete = new ActiveXObject("Microsoft.XMLHTTP");
	   }
	   requete.open("GET", url, true);
	   requete.onreadystatechange = majIHM;
	   requete.send(null);
	}
	
	function majIHM() {
	  var message = "";
	
	  if (requete.readyState == 4) {
	    if (requete.status == 200) {
	      // exploitation des données de la réponse
	      var messageTag = requete.responseXML.getElementsByTagName("message")[0];
	      message = messageTag.childNodes[0].nodeValue;
	      mdiv = document.getElementById("validationMessage");
	      if (message == "invalide") {
	         mdiv.innerHTML = "<img src='ressources/rsrc_caissier/assets/images/delete.jpg'>";
	      } else {
	         mdiv.innerHTML = "<img src='ressources/rsrc_caissier/assets/images/money.png'>";
	      }
	    }
	  }
	}
	
	//-->
	</script>
	</head>
	<body>
		<table>
			<tr>
				<td>Valeur :</td>
				<td nowrap><input type="text" id="donnees" name="donnees" size="30"
					onkeyup="valider();"></td>
				<td>
				<div id="validationMessage" ></div>
				</td>
			</tr>
		</table>
		<div id='jackpot' style="border: 2px solid #dd22dd;height:50px;"></div>
		
		<script src="ressources/lottery/js/vendor/jquery-3.4.1.min.js"></script>
		<script type="text/javascript">
		
			$(function valider(){
				setTimeout(function(){
					console.log('valid');
					   var donnees = document.getElementById("donnees");
					   var url = "valider?valeur=" + escape(donnees.value);
					   if (window.XMLHttpRequest) {
					       requete = new XMLHttpRequest();
					   } else if (window.ActiveXObject) {
					       requete = new ActiveXObject("Microsoft.XMLHTTP");
					   }
					   requete.open("GET", url, true);
					   //requete.onreadystatechange = majIHM;
					   requete.send(null);
					valider();
				},2000);
			});
			//valider();
			
		
			function charger(){
				setTimeout(function(){
					console.log('valide');
					$.ajax({
						url:"valider",
						type:"GET",
						success:function(xml){
							var messageTag = requete.responseXML.getElementsByTagName("message")[0];
						    message = messageTag.childNodes[0].nodeValue;
						    $("#jackpot").empty();
							$("#jackpot").prepend(message);
						}
					});
					charger();
				},5000);
			}
			charger();
		</script>
	</body>
</html>