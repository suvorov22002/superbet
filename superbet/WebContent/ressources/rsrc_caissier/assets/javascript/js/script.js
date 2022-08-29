// ---------Responsive-navbar-active-animation-----------
function test(){
  let tabsNewAnim = $('#navbarSupportedContent');
  let selectorNewAnim = $('#navbarSupportedContent').find('li').length;
  let activeItemNewAnim = tabsNewAnim.find('.active');
  let activeWidthNewAnimHeight = activeItemNewAnim.innerHeight();
  let activeWidthNewAnimWidth = activeItemNewAnim.innerWidth();
  let itemPosNewAnimTop = activeItemNewAnim.position();
  let itemPosNewAnimLeft = activeItemNewAnim.position();
  $(".hori-selector").css({
    "top":itemPosNewAnimTop.top + "px", 
    "left":itemPosNewAnimLeft.left + "px",
    "height": activeWidthNewAnimHeight + "px",
    "width": activeWidthNewAnimWidth + "px"
  });
  $("#navbarSupportedContent").on("click","li",function(e){
    $('#navbarSupportedContent ul li').removeClass("active");
    $(this).addClass('active');
    let activeWidthNewAnimHeight = $(this).innerHeight();
    let activeWidthNewAnimWidth = $(this).innerWidth();
    let itemPosNewAnimTop = $(this).position();
    let itemPosNewAnimLeft = $(this).position();
    $(".hori-selector").css({
      "top":itemPosNewAnimTop.top + "px", 
      "left":itemPosNewAnimLeft.left + "px",
      "height": activeWidthNewAnimHeight + "px",
      "width": activeWidthNewAnimWidth + "px"
    });
  });
}


function range(tab){
		
	let min;
	
	for(let i=0;i<tab.length-1;i++){
		min = tab[i];
		for(let j=i+1;j<tab.length;j++){
			console.log(tab[j]);
			if(tab[j] < min){
				tab[i] = tab[j];
				tab[j] = min;
				min = tab[i];
			}
		}
	}
	return tab;
}

function verifierInt(_echar){
	let v = false;
	try{
		parseInt(_echar);
		v = true;
	}
	catch(err){
		v = false;
	}
	return v;
	
}

function verifierInt2(echar){
	let _echar = [];
	let v = false;
	_echar = echar.split(".");
	
	if(_echar.length > 1) v = true;
	
	return v;
	
}

function check_keno_num(_tab){
	let num = parseInt(_tab);
	
	if(num < 1 || num > 80){
		return false;
	}
	
	return true;
}

function check_spin_num(_tab){
	let num = parseInt(_tab);
	
	if(num < 1 || num > 36){
		return false;
	}
	
	return true;
}

function same_number(_tab){
	for(let i=0;i<_tab.length-1;i++){
		for(let l=i+1;l<_tab.length;l++){
			if(_tab[l] !== _tab[i]){
				continue;
			}
			else{
				console.log('NUMERO DUPLIQUE');
				return true;
			}
		}
		
		if(_tab[i] === "0"){
			span.style.color = "red";
			span.innerHTML = "Numéro invalide";
			return true;
		}
	}
	
	if(_tab[_tab.length-1] === "0"){
		span.style.color = "red";
		span.innerHTML = "Numéro invalide";
		return true;
	}
	
	return false;
}

function check_draw(_tab){
	let span = document.getElementById("icode");
		span.innerHTML = "";
	
	for(let _i=0;_i<_tab.length;_i++){
		if(check_keno_num(_tab[_i]) == false){
			span.style.color = "red";
			span.innerHTML = "Numéro incorrect";
			return false;
		}
	}
	
	if(same_number(_tab)){
		span.style.color = "red";
		span.innerHTML = "Numéro dupliqué";
		return false;
	}
	return true;
}

function check_spin_draw(_tab){
	let sp_span = document.getElementById("ispcode");
		sp_span.innerHTML = "";
	
	for(let _i=0;_i<_tab.length;_i++){
		if(check_spin_num(_tab[_i]) == false){
			sp_span.style.color = "red";
			sp_span.innerHTML = "Numéro incorrect";
			return false;
		}
	}
	
	if(same_number(_tab)){
		sp_span.style.color = "red";
		sp_span.innerHTML = "Numéro dupliqué";
		return false;
	}
	return true;
}

//--- fonction de verification de l'input des paris ---//
function verif(evt) {
     // verification des caratères saisis autorisés
		let keyCode = evt.which ? evt.which : evt.keyCode;
        let accept = '.+*-/nN0123456789';
		let multiplicite = 1;
		let span = document.getElementById("icode");
		let numCourse = numero_tirage;
		console.log("NumCourse: "+numCourse);
		span.innerHTML = "";
		let libchoix, codePari, typejeu;
		
        if (accept.indexOf(String.fromCharCode(keyCode)) >= 0 || evt.keyCode===13) {
			// verification des numéros saisis
			
			if (evt.keyCode === 13) {
				evt.preventDefault();

				// on vide d'abord le tableau
    			$('.t_pari').find('tbody').empty();
    			document.getElementById('montant').value='';

				let echar = "";
				let  _echar = [];
				let __echar = [];
				let ichoice = $("#code").val();
				console.log("saisi: "+ichoice);
				echar = ichoice.charAt(0);
				console.log("first: "+echar);
				
				if(echar === '-'){ //non sortants
					console.log("ifirst: "+echar+" : non sortants");
					if(verifierInt2(ichoice)){
						span.style.color = "red";
						span.innerHTML = "Mauvaise saisie";
						return;
					}
					__echar = ichoice.split("-");
					
					let _i = 0;
					_echar = [];
					
					for(let _j=1; _j<__echar.length; _j++){
						_echar[_i++] = __echar[_j];
						console.log("second: "+__echar[_j]);
					}

					if(_echar.length < 1 || _echar.length > 10 ){
						console.log("Choix entre 2 et 10");
						span.style.color = "red";
						span.innerHTML = "Choix entre 1 et 10";
						
						return;
					}
					
		//possibilite de factoriser cette traitement
					for(let j=0;j<_echar.length;j++){
		console.log("tnote: "+_echar[j]+' '+Number.isInteger(parseInt(_echar[j]))+' '+isNaN(_echar[j]));
						if(isNaN(_echar[j]) == true){ // on verifie si les caractères saisis sont les nombres
							console.log("not: "+_echar[j]);
							//verification de la multiplicité
							if(j == _echar.length-1){
								let _echars = [];
								console.log("RES "+_echar[j]);
								var res = _echar[j].toLowerCase();
								console.log("ress: "+res);
								_echars = res.split("n");
								console.log('conso: '+_echars[1]+' gh '+Number.isInteger(parseInt(_echars[1])));
								//for(let k=0; k<_echars.length;k++){
									if(Number.isInteger(parseInt(_echars[1])) == false){ //verification de la multiplicité
										//span = document.getElementById("icode");
										span.style.color = "red";
										span.innerHTML = "Multiplicité incorrecte";
										console.log("Multiplicité incorrecte");
										return;
									}
									else{ // ticket multiple
										_echar[_echar.length-1] = _echars[0];
										multiplicite = parseInt(_echars[1]);
										console.log('MULTI: '+multiplicite);
										if(multiplicite > 6){
											span.style.color = "red";
											span.innerHTML = "Ticket multiple entre 1 et 6";
											console.log("Multiplicité incorrecte");
											return;
										}
									}
								//}
							}
							else{
								console.log('NOT A NUMBER');
								span.style.color = "red";
								span.innerHTML = "Numéro invalide";
								return;
							}
						}	
					}
			//possibilite de factoriser cette traitement

					//verification de la saisie
				/*	let chkdrw = function check_draw(_tab){
						return true;
					};0*/
					
					//seconde vérification des numéros saisis
					for(let j=0;j<_echar.length;j++){
						if(Number.isInteger(parseInt(_echar[j])) == false){
							console.log('NOT A NUMBER 2');
							span.style.color = "red";
							span.innerHTML = "Numéro invalide";
							return;
						}
					}
					if(check_draw(_echar)){
						console.log("check this draw");
						span.style.color = "blue";
						span.innerHTML = "Hors tirage";
						
						
						let str_ = "";
						//let tab = [];
						// range les numeros
						let tab = range(_echar);

						let _str = "";
						for(let ii=0;ii<tab.length-1;ii++){
							_str = _str + parseInt(tab[ii]) + "-";
						}
						_str = _str + parseInt(tab[tab.length-1]);



						//tab.length = 
						let value = [numCourse, _str, 'Hors Tirage'];
						addlinesTable_pari(value, multiplicite);
						document.getElementById('montant').focus();
					}
					
				}
				else if(echar === '+'){ // tout dedans
					console.log("first: "+echar+" : tout dedans");
					if(verifierInt2(ichoice)){
						span.style.color = "red";
						span.innerHTML = "Mauvaise saisie";
						return;
					}
					__echar = ichoice.split("+");
					
					
					let _i = 0;
					_echar = [];
					
					for(let _j=1; _j<__echar.length; _j++){
						_echar[_i++] = __echar[_j];
						console.log("second: "+__echar[_j]);
					}

					if(_echar.length < 1 || _echar.length > 6 ){
						console.log("Choix entre 2 et 6");
						span.style.color = "red";
						span.innerHTML = "Choix entre 1 et 6";
						
						return;
					}
					
		//possibilite de factoriser cette traitement
					for(let j=0;j<_echar.length;j++){
		console.log("tnote: "+_echar[j]+' '+Number.isInteger(parseInt(_echar[j]))+' '+isNaN(_echar[j]));
						if(isNaN(_echar[j]) == true){ // on verifie si les caractères saisis sont les nombres
							console.log("not: "+_echar[j]);
							//verification de la multiplicité
							if(j == _echar.length-1){
								let _echars = [];
								console.log("RES "+_echar[j]);
								var res = _echar[j].toLowerCase();
								console.log("ress: "+res);
								_echars = res.split("n");
								console.log('conso: '+_echars[1]+' gh '+Number.isInteger(parseInt(_echars[1])));
								//for(let k=0; k<_echars.length;k++){
									if(Number.isInteger(parseInt(_echars[1])) == false){ //verification de la multiplicité
										//span = document.getElementById("icode");
										span.style.color = "red";
										span.innerHTML = "Multiplicité incorrecte";
										console.log("Multiplicité incorrecte");
										return;
									}
									else{ // ticket multiple
										_echar[_echar.length-1] = _echars[0];
										multiplicite = parseInt(_echars[1]);
										console.log('MULTI: '+multiplicite);
										if(multiplicite > 6){
											span.style.color = "red";
											span.innerHTML = "Ticket multiple entre 1 et 6";
											console.log("Multiplicité incorrecte");
											return;
										}
									}
								//}
							}
							else{
								console.log('NOT A NUMBER');
								span.style.color = "red";
								span.innerHTML = "Numéro invalide";
								return;
							}
						}	
					}
			//possibilite de factoriser cette traitement

					//seconde vérification des numéros saisis
					for(let j=0;j<_echar.length;j++){
						if(Number.isInteger(parseInt(_echar[j])) == false){
							console.log('NOT A NUMBER 2');
							span.style.color = "red";
							span.innerHTML = "Numéro invalide";
							return;
						}
					}
					if(check_draw(_echar)){
						console.log("check this draw all in");
						span.style.color = "blue";
						span.innerHTML = "Tout dedans";
						
						
						let str_ = "";
						//let tab = [];
						// range les numeros
						let tab = range(_echar);

						let _str = "";
						for(let ii=0;ii<tab.length-1;ii++){
							_str = _str + parseInt(tab[ii]) + "-";
						}
						_str = _str + parseInt(tab[tab.length-1]);



						//tab.length = 
						let value = [numCourse, _str, 'Tout dedans'];
						addlinesTable_pari(value, multiplicite);
						document.getElementById('montant').focus();
					}
					
				
				} //end tout dedans
				else if(echar === '*'){ 
					console.log("first: "+echar+" : couleur");
					if(verifierInt2(ichoice)){
						span.style.color = "red";
						span.innerHTML = "Mauvaise saisie";
						return;
					}
					
					let vColor = ichoice.substring(1);
					console.log("choix couleur: "+vColor);
					let tColor = [];
					tColor = vColor.split("n");

					//verification de la multiplicite
					if(Number.isInteger(parseInt(tColor[1])) == false){ //verification de la multiplicité
						multiplicite = 1;
						console.log('MULTI: '+multiplicite);
					}
					else{ // ticket multiple
						multiplicite = parseInt(tColor[1]);
						console.log('MULTI: '+multiplicite);
						if(multiplicite > 6){
							span.style.color = "red";
							span.innerHTML = "Ticket multiple entre 1 et 6";
							console.log("Multiplicité incorrecte");
							return;
						}
					}


					console.log("type: "+tColor[0]);
					if(Number.isInteger(parseInt(tColor[0])) == false){
						span.style.color = "red";
						span.innerHTML = "Choix non disponible";
						console.log("Not a number");
						return;
					}
					else{
						if(parseInt(tColor[0]) > 4 ||parseInt(tColor[0]) < 1){//on verifie si le choix est entre 1 et 4
							span.style.color = "red";
							span.innerHTML = "Choix entre 1 et 4";
							console.log("Not a number");
							return;
						}
						else{ //tout est ok, on fait le traitement
							span.style.color = "blue";
							if(parseInt(tColor[0]) == 1){
								//Couleur verte between 1 and 20
								console.log("Couleur verte");
								span.innerHTML = "1er couleur verte";
								
								let value = [numCourse, '1er boule', 'Couleur verte'];
								addlinesTable_pari(value, multiplicite);
								document.getElementById('montant').focus();
								
							}
							else if(parseInt(tColor[0]) == 2){
								//Couleur verte between 21 and 40
								console.log("Couleur bleu");
								span.innerHTML = "1er couleur bleu";
								
								let value = [numCourse, '1er boule', 'Couleur bleu'];
								addlinesTable_pari(value, multiplicite);
								document.getElementById('montant').focus();
							}
							else if(parseInt(tColor[0]) == 3){
								//Couleur verte between 41 and 60
								console.log("Couleur rouge");
								span.innerHTML = "1er couleur rouge";
								
								let value = [numCourse, '1er boule', 'Couleur rouge'];
								addlinesTable_pari(value, multiplicite);
								document.getElementById('montant').focus();
							}
							else if(parseInt(tColor[0]) == 4){
								//Couleur verte between 61 and 80
								console.log("Couleur orange");
								span.innerHTML = "1er couleur orange";
								
								let value = [numCourse, '1er boule', 'Couleur orange'];
								addlinesTable_pari(value, multiplicite);
								document.getElementById('montant').focus();
							}
						}

					}
				} // end des couleurs
				else if(echar === '/'){ //couleur de la derniere boule
					console.log("first: "+echar+" : couleur");
					if(verifierInt2(ichoice)){
						span.style.color = "red";
						span.innerHTML = "Mauvaise saisie";
						return;
					}
					
					let vColor = ichoice.substring(1);
					console.log("choix couleur: "+vColor);
					let tColor = [];
					tColor = vColor.split("n");

					//verification de la multiplicite
					if(Number.isInteger(parseInt(tColor[1])) == false){ //verification de la multiplicité
						multiplicite = 1;
						console.log('MULTI: '+multiplicite);
					}
					else{ // ticket multiple
						multiplicite = parseInt(tColor[1]);
						console.log('MULTI: '+multiplicite);
						if(multiplicite > 6){
							span.style.color = "red";
							span.innerHTML = "Ticket multiple entre 1 et 6";
							console.log("Multiplicité incorrecte");
							return;
						}
					}


					console.log("type: "+tColor[0]);
					if(Number.isInteger(parseInt(tColor[0])) == false){
						span.style.color = "red";
						span.innerHTML = "Choix non disponible";
						console.log("Not a number");
						return;
					}
					else{
						if(parseInt(tColor[0]) > 4 ||parseInt(tColor[0]) < 1){//on verifie si le choix est entre 1 et 4
							span.style.color = "red";
							span.innerHTML = "Choix entre 1 et 4";
							console.log("Not a number");
							return;
						}
						else{ //tout est ok, on fait le traitement
							span.style.color = "blue";
							if(parseInt(tColor[0]) == 1){
								//Couleur verte between 1 and 20
								console.log("Couleur verte");
								span.innerHTML = "20eme couleur verte";
								
								let value = [numCourse, '20eme boule', 'Couleur verte'];
								addlinesTable_pari(value, multiplicite);
								document.getElementById('montant').focus();
								
							}
							else if(parseInt(tColor[0]) == 2){
								//Couleur verte between 21 and 40
								console.log("Couleur bleu");
								span.innerHTML = "20eme couleur bleu";
								
								let value = [numCourse, '20eme boule', 'Couleur bleu'];
								addlinesTable_pari(value, multiplicite);
								document.getElementById('montant').focus();
							}
							else if(parseInt(tColor[0]) == 3){
								//Couleur verte between 41 and 60
								console.log("Couleur rouge");
								span.innerHTML = "20eme couleur rouge";
								
								let value = [numCourse, '20eme boule', 'Couleur rouge'];
								addlinesTable_pari(value, multiplicite);
								document.getElementById('montant').focus();
							}
							else if(parseInt(tColor[0]) == 4){
								//Couleur verte between 61 and 80
								console.log("Couleur orange");
								span.innerHTML = "20eme couleur orange";
								
								let value = [numCourse, '20eme boule', 'Couleur orange'];
								addlinesTable_pari(value, multiplicite);
								document.getElementById('montant').focus();
							}
						}

					}
				}
				else if(ichoice.substring(0,2) === '5-'){ //somme des 5 premiers
					//somme des 5 1er < 202.5
					console.log("somme des 5 1er < 202.5 "+ichoice.substring(0,2));
					if(verifierInt2(ichoice)){
						span.style.color = "red";
						span.innerHTML = "Mauvaise saisie";
						return;
					}

					let tSum = [];
					tSum = ichoice.split("n");

					//verification de la multiplicite
					if(Number.isInteger(parseInt(tSum[1])) == false){ //verification de la multiplicité
						multiplicite = 1;
						console.log('MULTI: '+multiplicite);
					}
					else{ // ticket multiple
						multiplicite = parseInt(tSum[1]);
						console.log('MULTI: '+multiplicite);
						if(multiplicite > 6){
							span.style.color = "red";
							span.innerHTML = "Ticket multiple entre 1 et 6";
							console.log("Multiplicité incorrecte");
							return;
						}
					}

					span.style.color = "blue";
					span.innerHTML = "somme cinq 1er -202,5";
								
					let value = [numCourse, 'somme < 202,5', 'somme cinq 1er'];
					addlinesTable_pari(value, multiplicite);
					document.getElementById('montant').focus();

				}
				else if(ichoice.substring(0,2) === '5+'){ //somme des 5 premiers
					//somme des 5 1er > 202.5
					console.log("somme des 5 1er > 202.5 "+ichoice.substring(0,2));
					if(verifierInt2(ichoice)){
						span.style.color = "red";
						span.innerHTML = "Mauvaise saisie";
						return;
					}

					let tSum = [];
					tSum = ichoice.split("n");

					//verification de la multiplicite
					if(Number.isInteger(parseInt(tSum[1])) == false){ //verification de la multiplicité
						multiplicite = 1;
						console.log('MULTI: '+multiplicite);
					}
					else{ // ticket multiple
						multiplicite = parseInt(tSum[1]);
						console.log('MULTI: '+multiplicite);
						if(multiplicite > 6){
							span.style.color = "red";
							span.innerHTML = "Ticket multiple entre 1 et 6";
							console.log("Multiplicité incorrecte");
							return;
						}
					}

					span.style.color = "blue";
					span.innerHTML = "somme cinq 1er +202,5";
								
					let value = [numCourse, 'somme > 202,5', 'somme cinq 1er'];
					addlinesTable_pari(value, multiplicite);
					document.getElementById('montant').focus();

				}
				else if(ichoice.substring(0,3) === '20-'){ //somme des 20 
					//somme des 20 1er < 810.5
					console.log("somme total < 805.5 "+ichoice.substring(0,3));
					if(verifierInt2(ichoice)){
						span.style.color = "red";
						span.innerHTML = "Mauvaise saisie";
						return;
					}

					let tSum = [];
					tSum = ichoice.split("n");

					//verification de la multiplicite
					if(Number.isInteger(parseInt(tSum[1])) == false){ //verification de la multiplicité
						multiplicite = 1;
						console.log('MULTI: '+multiplicite);
					}
					else{ // ticket multiple
						multiplicite = parseInt(tSum[1]);
						console.log('MULTI: '+multiplicite);
						if(multiplicite > 6){
							span.style.color = "red";
							span.innerHTML = "Ticket multiple entre 1 et 6";
							console.log("Multiplicité incorrecte");
							return;
						}
					}

					span.style.color = "blue";
					span.innerHTML = "somme total inferieure 805.5";
								
					let value = [numCourse, 'somme < 805.5', 'somme totale'];
					addlinesTable_pari(value, multiplicite);
					document.getElementById('montant').focus();

				}
				else if(ichoice.substring(0,3) === '20+'){ //somme des 20 
					//somme des 20 1er > 810.5
					console.log("somme total superieure 805.5 "+ichoice.substring(0,3));
					if(verifierInt2(ichoice)){
						span.style.color = "red";
						span.innerHTML = "Mauvaise saisie";
						return;
					}

					let tSum = [];
					tSum = ichoice.split("n");

					//verification de la multiplicite
					if(Number.isInteger(parseInt(tSum[1])) == false){ //verification de la multiplicité
						multiplicite = 1;
						console.log('MULTI: '+multiplicite);
					}
					else{ // ticket multiple
						multiplicite = parseInt(tSum[1]);
						console.log('MULTI: '+multiplicite);
						if(multiplicite > 6){
							span.style.color = "red";
							span.innerHTML = "Ticket multiple entre 1 et 6";
							console.log("Multiplicité incorrecte");
							return;
						}
					}

					span.style.color = "blue";
					span.innerHTML = "somme total superieure 805";
								
					let value = [numCourse, 'somme > 810.5', 'somme totale'];
					addlinesTable_pari(value, multiplicite);
					document.getElementById('montant').focus();

				}
				else if(ichoice.substring(0,2) === '1-'){ //1er numero < 40.5
					//1er numero < 40.5
					console.log("1er numero < 40.5 "+ichoice.substring(0,2));
					if(verifierInt2(ichoice)){
						span.style.color = "red";
						span.innerHTML = "Mauvaise saisie";
						return;
					}

					let tSum = [];
					tSum = ichoice.split("n");

					//verification de la multiplicite
					if(Number.isInteger(parseInt(tSum[1])) == false){ //verification de la multiplicité
						multiplicite = 1;
						console.log('MULTI: '+multiplicite);
					}
					else{ // ticket multiple
						multiplicite = parseInt(tSum[1]);
						console.log('MULTI: '+multiplicite);
						if(multiplicite > 6){
							span.style.color = "red";
							span.innerHTML = "Ticket multiple entre 1 et 6";
							console.log("Multiplicité incorrecte");
							return;
						}
					}

					span.style.color = "blue";
					span.innerHTML = "1er numero < 40.5";
								
					let value = [numCourse, '1er numero < 40.5', '1er numero'];
					addlinesTable_pari(value, multiplicite);
					document.getElementById('montant').focus();

				}
				else if(ichoice.substring(0,2) === '1+'){ //1er numero > 40.5
					//1er numero > 40.5
					console.log("1er numero > 40.5 "+ichoice.substring(0,2));
					if(verifierInt2(ichoice)){
						span.style.color = "red";
						span.innerHTML = "Mauvaise saisie";
						return;
					}

					let tSum = [];
					tSum = ichoice.split("n");

					//verification de la multiplicite
					if(Number.isInteger(parseInt(tSum[1])) == false){ //verification de la multiplicité
						multiplicite = 1;
						console.log('MULTI: '+multiplicite);
					}
					else{ // ticket multiple
						multiplicite = parseInt(tSum[1]);
						console.log('MULTI: '+multiplicite);
						if(multiplicite > 6){
							span.style.color = "red";
							span.innerHTML = "Ticket multiple entre 1 et 6";
							console.log("Multiplicité incorrecte");
							return;
						}
					}

					span.style.color = "blue";
					span.innerHTML = "1er numero > 40.5";
								
					let value = [numCourse, '1er numero > 40.5', '1er numero'];
					addlinesTable_pari(value, multiplicite);
					document.getElementById('montant').focus();

				}
				else if(ichoice.substring(0,3) === "188"){
					console.log("premier numero pair "+ichoice.substring(0,3));
					if(verifierInt2(ichoice)){
						span.style.color = "red";
						span.innerHTML = "Mauvaise saisie";
						return;
					}

					let tSum = [];
					tSum = ichoice.split("n");

					//verification de la multiplicite
					if(Number.isInteger(parseInt(tSum[1])) == false){ //verification de la multiplicité
						multiplicite = 1;
						console.log('MULTI: '+multiplicite);
					}
					else{ // ticket multiple
						multiplicite = parseInt(tSum[1]);
						console.log('MULTI: '+multiplicite);
						if(multiplicite > 6){
							span.style.color = "red";
							span.innerHTML = "Ticket multiple entre 1 et 6";
							console.log("Multiplicité incorrecte");
							return;
						}
					}

					span.style.color = "blue";
					span.innerHTML = "premier pair";
								
					let value = [numCourse, 'premier pair', '1er numero'];
					addlinesTable_pari(value, multiplicite);
					document.getElementById('montant').focus();
							
				}
				else if(ichoice.substring(0,3) ==="189"){
					
					console.log("dernier numero pair "+ichoice.substring(0,3));
					if(verifierInt2(ichoice)){
						span.style.color = "red";
						span.innerHTML = "Mauvaise saisie";
						return;
					}
					let tSum = [];
					tSum = ichoice.split("n");

					//verification de la multiplicite
					if(Number.isInteger(parseInt(tSum[1])) == false){ //verification de la multiplicité
						multiplicite = 1;
						console.log('MULTI: '+multiplicite);
					}
					else{ // ticket multiple
						multiplicite = parseInt(tSum[1]);
						console.log('MULTI: '+multiplicite);
						if(multiplicite > 6){
							span.style.color = "red";
							span.innerHTML = "Ticket multiple entre 1 et 6";
							console.log("Multiplicité incorrecte");
							return;
						}
					}

					span.style.color = "blue";
					span.innerHTML = "dernier pair";
								
					let value = [numCourse, 'dernier pair', 'dernier numero'];
					addlinesTable_pari(value, multiplicite);
					document.getElementById('montant').focus();
					
				}
				else if(ichoice.substring(0,3) === "198"){
					
					console.log("premier numero impair "+ichoice.substring(0,3));
					if(verifierInt2(ichoice)){
						span.style.color = "red";
						span.innerHTML = "Mauvaise saisie";
						return;
					}

					let tSum = [];
					tSum = ichoice.split("n");

					//verification de la multiplicite
					if(Number.isInteger(parseInt(tSum[1])) == false){ //verification de la multiplicité
						multiplicite = 1;
						console.log('MULTI: '+multiplicite);
					}
					else{ // ticket multiple
						multiplicite = parseInt(tSum[1]);
						console.log('MULTI: '+multiplicite);
						if(multiplicite > 6){
							span.style.color = "red";
							span.innerHTML = "Ticket multiple entre 1 et 6";
							console.log("Multiplicité incorrecte");
							return;
						}
					}

					span.style.color = "blue";
					span.innerHTML = "premier impair";
								
					let value = [numCourse, 'premier impair', 'premier numero'];
					addlinesTable_pari(value, multiplicite);
					document.getElementById('montant').focus();
					
				}
                else if(ichoice.substring(0,3) === "199"){
					
					console.log("dernier impair "+ichoice.substring(0,3));
					if(verifierInt2(ichoice)){
						span.style.color = "red";
						span.innerHTML = "Mauvaise saisie";
						return;
					}

					let tSum = [];
					tSum = ichoice.split("n");

					//verification de la multiplicite
					if(Number.isInteger(parseInt(tSum[1])) == false){ //verification de la multiplicité
						multiplicite = 1;
						console.log('MULTI: '+multiplicite);
					}
					else{ // ticket multiple
						multiplicite = parseInt(tSum[1]);
						console.log('MULTI: '+multiplicite);
						if(multiplicite > 6){
							span.style.color = "red";
							span.innerHTML = "Ticket multiple entre 1 et 6";
							console.log("Multiplicité incorrecte");
							return;
						}
					}

					span.style.color = "blue";
					span.innerHTML = "dernier impair";
								
					let value = [numCourse, 'dernier impair', 'dernier numero'];
					addlinesTable_pari(value, multiplicite);
					document.getElementById('montant').focus();
					
				}
				else{  // numéros simples

					__echar = ichoice.split(".");
					
					let _i = 0;
					_echar = [];
					
					for(let _j=0; _j<__echar.length; _j++){
						_echar[_i++] = __echar[_j];
						console.log("second: "+__echar[_j]);
					}

					if(_echar.length < 2 || _echar.length > 10 ){
						console.log("Choix entre 2 et 10");
						span.style.color = "red";
						span.innerHTML = "Choix entre 2 et 10";
						
						return;
					}
				//}

				//possibilite de factoriser ce traitement
					for(let j=0;j<_echar.length;j++){
		console.log("tnote: "+_echar[j]+' '+Number.isInteger(parseInt(_echar[j]))+' '+isNaN(_echar[j]));
						if(isNaN(_echar[j]) == true){ // on verifie si les caractères saisis sont les nombres
							console.log("not: "+_echar[j]);
							//verification de la multiplicité
							if(j == _echar.length-1){
								let _echars = [];
								console.log("RES "+_echar[j]);
								var res = _echar[j].toLowerCase();
								console.log("ress: "+res);
								_echars = res.split("n");
								console.log('conso: '+_echars[1]+' gh '+Number.isInteger(parseInt(_echars[1])));
								//for(let k=0; k<_echars.length;k++){
									if(Number.isInteger(parseInt(_echars[1])) == false){ //verification de la multiplicité
										//span = document.getElementById("icode");
										span.style.color = "red";
										span.innerHTML = "Multiplicité incorrecte";
										console.log("Multiplicité incorrecte");
										return;
									}
									else{ // ticket multiple
										_echar[_echar.length-1] = _echars[0];
										multiplicite = parseInt(_echars[1]);
										console.log('MULTI: '+multiplicite);
										if(multiplicite > 6){
											span.style.color = "red";
											span.innerHTML = "Ticket multiple entre 1 et 6";
											console.log("Multiplicité incorrecte");
											return;
										}
									}
								//}
							}
							else{
								console.log('NOT A NUMBER');
								span.style.color = "red";
								span.innerHTML = "Numéro invalide";
								return;
							}
						}	
					}
				//verification de la saisie
				/*	let chkdrw = function check_draw(_tab){
						return true;
					};0*/
					
					//seconde vérification des numéros saisis
					for(let j=0;j<_echar.length;j++){
						if(Number.isInteger(parseInt(_echar[j])) == false){
							console.log('NOT A NUMBER 2');
							span.style.color = "red";
							span.innerHTML = "Numéro invalide";
							return;
						}
					}
					if(check_draw(_echar)){
						console.log("check this draw");
						span.style.color = "blue";
						span.innerHTML = _echar.length+" Numeros";
						
						
						let str_ = "";
						//let tab = [];
						// range les numeros
						let tab = range(_echar);

						let _str = "";
						for(let ii=0;ii<tab.length-1;ii++){
							_str = _str + parseInt(tab[ii]) + "-";
						}
						_str = _str + parseInt(tab[tab.length-1]);



						//tab.length = 
						let value = [numCourse, _str, _echar.length+' Numeros'];
						addlinesTable_pari(value, multiplicite);
						document.getElementById('montant').focus();
					}

				}
			}// endif eventkey code === 13
            return true;
        } else {
        	//not an accept character
            return false;
        }
}

function verif_amount(evt) {

      let accept = '0123456789';
      let keyCode = evt.which ? evt.which : evt.keyCode;
      var balance1 = $("#balance1").val();
      if (accept.indexOf(String.fromCharCode(keyCode)) >= 0 || evt.keyCode===13) {
      	if (evt.keyCode === 13) {
			evt.preventDefault();
			let amount = $("#montant").val();
			document.getElementById('amount_error').innerHTML = "";

			if(amount ==='' || parseInt(amount) < 200 || parseInt(amount) > 20000 || parseInt(balance1) < parseInt(amount)){
				console.log('error');
				document.getElementById('amount_error').innerHTML = "montant incorrect";
				if(parseInt(balance1) < parseInt(amount)){
					document.getElementById('amount_error').innerHTML = "Credit insuffisant";
				}
					
				return;
			}

			console.log('no error');
			document.getElementById('print').focus();
		}
        return true;
      } else {
        //not an accept character
          return false;
      }
}

function verif_sp_amount(evt) {

    let accept = '0123456789';
    let keyCode = evt.which ? evt.which : evt.keyCode;
    
    if (accept.indexOf(String.fromCharCode(keyCode)) >= 0 || evt.keyCode===13) {
    	if (evt.keyCode === 13) {
			evt.preventDefault();
			let amount = $("#spmontant").val();
			document.getElementById('sp_amount_error').innerHTML = "";

			if(amount ==='' || parseInt(amount) < 200 || parseInt(amount) > 20000){
				console.log('error');
				document.getElementById('sp_amount_error').innerHTML = "montant incorrect";
				return;
			}

			console.log('no error');
			document.getElementById('sp_print').focus();
		}
      return true;
    } else {
      //not an accept character
        return false;
    }
}
 
function manage_keno(evt) {
    console.log('manage_keno');
	evt.preventDefault();
	//submit du formulaire
    document.forms["form_man_keno"].submit();
    return false;
}
function checkCoupon(){
	console.log("load");
	document.getElementById('barcode').focus();
}
function check_barcode(evt){
	//console.log("je teste le code avec le barre code");
	evt.preventDefault();
			var code = $("#barcode").val();
			
			document.getElementById('coupon_error').innerHTML = "";
		//	console.log("code: "+code);
			if(code ==='' || code.length < 10){
			//if(code ==='' || code.length != 10){
				//console.log('error');
				//document.getElementById('coupon_error').innerHTML = "code ticket incorrect";
				return;
			}

			//submit du formulaire
			document.forms["form_ver"].submit();
		//	console.log('no error');
			
}
function verif_coupon(evt) {

    let accept = '0123456789';
    let keyCode = evt.which ? evt.which : evt.keyCode;
    
    if (accept.indexOf(String.fromCharCode(keyCode)) >= 0 || evt.keyCode===13) {
        var code = $("#barcode").val();
        console.log("le code: "+code);
        
        
//    	if (evt.keyCode === 13) {
//			evt.preventDefault();
//			var code = $("#barcode").val();
//			
//			document.getElementById('coupon_error').innerHTML = "";
//			console.log("code: "+code);
//			if(code ===''){
//			//if(code ==='' || code.length != 10){
//				console.log('error');
//				document.getElementById('coupon_error').innerHTML = "code ticket incorrect";
//				return;
//			}
//
//			//submit du formulaire
//			document.forms["form_ver"].submit();
//		//	$.ajax({
//			//	url:"versement",
//			//	type:"POST",
//			//	data:{
//			//			'barcode':code,
//						
//			//		},
//			//	success:function(result){
//				//	$.each(result, function(index, value){
//				//		$('.pari_state').toggleClass('erreur', true);
//				//		$('.pari_state').toggleClass('succes', false);
//				//		$('.pari_state').empty();
//				//		$('.pari_state').prepend("Non Connecté");
//				//		$('.canprint').prop('disabled', true);
//				//		$('.endshift').prop('disabled', true);
//				//	});
//			//	}
//			//  });
//
//			console.log('no error');
//			//document.getElementById('print').focus();
//			//$('#myModal').modal('show');
//
//		}
      return true;
    } else {
      //not an accept character
        return false;
    }
}


function verifCoupon(){

	var code = $("#barcode").val();
	document.getElementById('coupon_error').innerHTML = "";
	console.log("code: "+code);

	//if(code ==='' || code.length != 10 ){
	if(code ==='' ){
		//console.log('error');
		document.getElementById('coupon_error').innerHTML = "code ticket incorrect";
		return false;
	}

	//console.log('no error');
	return true;
}
/*
var format = new Intl.NumberFormat('en-INR', { 
                style: 'currency', 
                currency: 'INR', 
                minimumFractionDigits: 2, 
            }); 
            // for 4800 INR 
            document.write(format.format(4800)); 


/*
$(document).ready(function(){
  setTimeout(function(){ test(); });
});
$(window).on('resize', function(){
  setTimeout(function(){ test(); }, 500);
});
$(".navbar-toggler").click(function(){
  setTimeout(function(){ test(); });
});*/

function date_heure(id)
{
        date = new Date;
        annee = date.getFullYear();
        moi = date.getMonth();
        mois = new Array('Janvier', 'F&eacute;vrier', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet', 'Ao&ucirc;t', 'Septembre', 'Octobre', 'Novembre', 'D&eacute;cembre');
        j = date.getDate();
        jour = date.getDay();
        jours = new Array('Dimanche', 'Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi');
        h = date.getHours();
        if(h<10)
        {
                h = "0"+h;
        }
        m = date.getMinutes();
        if(m<10)
        {
                m = "0"+m;
        }
        s = date.getSeconds();
        if(s<10)
        {
                s = "0"+s;
        }
        //resultat = ' '+jours[jour]+' '+j+' '+mois[moi]+' '+annee+' , '+h+':'+m+':'+s;
		resultat = ' '+j+' '+mois[moi]+' '+annee+' , '+h+':'+m+':'+s;
        document.getElementById(id).innerHTML = resultat;
        setTimeout('date_heure("'+id+'");','1000');
        return true;
}

function simple(){
	alea_choice = 0;
	document.getElementById('alea_nbre').value = '';
	document.getElementById('alea_nbre').focus();
	if(typeof(Storage) !== "undefined"){
		localStorage.setItem("aleaChoix", alea_choice);
	}
}

function sortant(){
	alea_choice = 1;
	document.getElementById('alea_nbre').value = '';
	document.getElementById('alea_nbre').focus();
	if(typeof(Storage) !== "undefined"){
		localStorage.setItem("aleaChoix", alea_choice);
	}
}

function dedans(){
	alea_choice = 2;
	document.getElementById('alea_nbre').value = '';
	document.getElementById('alea_nbre').focus();
	if(typeof(Storage) !== "undefined"){
		localStorage.setItem("aleaChoix", alea_choice);
	}
}

function  aleaNbre(evt){
      
	let _accept = '0123456789';
    let _keyCode = evt.which ? evt.which : evt.keyCode;
	console.log('alea_nbre: '+_keyCode);
    var span = document.getElementById("icode");
		span.innerHTML = "";

    if (_accept.indexOf(String.fromCharCode(_keyCode)) >= 0 || evt.keyCode===13) {
			// verification des numéros saisis
			if (evt.keyCode === 13) {
				span.innerHTML = "";
				evt.preventDefault();
				var inbre = $("#alea_nbre").val();
				alea_choice = localStorage.getItem("aleaChoix");
			//	console.log('alea_nbre: '+alea_nbre);
				if(alea_choice == 0){
					if(inbre > 10 || inbre < 2){
						span.style.color = "red";
						span.innerHTML = "Keno simple - Choix entre 2 et 10";
						return false;
					}

					span.style.color = "blue";
					span.innerHTML = "Keno simple - "+inbre+" Numeros";
				}
				else if(alea_choice == 1){
					if(inbre > 10 || inbre < 1){
						span.style.color = "red";
						span.innerHTML = "Non sortants - Choix entre 1 et 10";
						return false;
					}

					span.style.color = "blue";
					span.innerHTML = "Non sortants - "+inbre+" Numeros";
				}
				else if(alea_choice == 2){
					if(inbre > 6 || inbre < 1){
						span.style.color = "red";
						span.innerHTML = "Sortants - Choix entre 1 et 6";
						return false;
					}

					span.style.color = "blue";
					span.innerHTML = "Sortants - "+inbre+" Numeros";
				}

				var choice = buscarDraw(inbre, alea_choice);
			//	console.log("choix: "+choice);
				document.getElementById('code').value = choice;
				document.getElementById('code').focus();
			}
			return true;
	}
	else{
		return false;
	}
}

function place_alea(event){

	event.preventDefault();
	var inbre = $("#alea_nbre").val();
	var span = document.getElementById("icode");
		span.innerHTML = "";
	console.log('alea_nbre: '+alea_nbre);
	alea_choice = localStorage.getItem("aleaChoix");
	if(alea_choice == 0){
		if(inbre > 10 || inbre < 2){
			span.style.color = "red";
			span.innerHTML = "Keno simple - Choix entre 2 et 10";
			return false;
		}

		span.style.color = "blue";
		span.innerHTML = "Keno simple - "+inbre+" Numeros";
	}
	else if(alea_choice == 1){
		if(inbre > 10 || inbre < 1){
			span.style.color = "red";
			span.innerHTML = "Non sortants - Choix entre 1 et 10";
			return false;
		}

		span.style.color = "blue";
		span.innerHTML = "Non sortants - "+inbre+" Numeros";
	}
	else if(alea_choice == 2){
		if(inbre > 6 || inbre < 1){
			span.style.color = "red";
			span.innerHTML = "Sortants - Choix entre 1 et 6";
			return false;
		}

		span.style.color = "blue";
		span.innerHTML = "Sortants - "+inbre+" Numeros";
	}

	var choice = buscarDraw(inbre, alea_choice);
		console.log("choix: "+choice);
		document.getElementById('code').value = choice;
		document.getElementById('code').focus();

}

function buscarDraw(nbreAlea, choixAlea) {
    	let combis = [];
    	let combi = [];
    	let max;
    	let index;
    	let find_combi = true;
        
    	

	    	while(find_combi){
	    		 combis = [];
    			 combi = [];
    			find_combi = true;
    			

       			for(let ii=1;ii<81;ii++){
	    			combis.push(ii);
	    		}
			    //min = 0;
			//    for(let _i=0;_i<combis.length;_i++){
			//    		console.log(combis[_i]);
			//    }
		    	for(let j=0;j<nbreAlea;j++){
		    		max = combis.length-1;
		    		index = getRandomIntInclusive(0, max);
		    		//console.log("Index: "+index);
		    		combi.push(combis[index]);

		    		//remove item in array
		    		combis.splice( $.inArray(index, combis), 1 );
		    	//	combis = jQuery.grep(combis, function(value) {
				//		  return value != index;
				//		});

					//combis = $.grep(combis, function (val) { return val != index ; });
		    	}
		    	for(let _i=0;_i<combi.length;_i++){
			    		console.log(combi[_i]);
			    }
			    console.log("recherche des doublons");
		    	//recherche des doublons
		    	var receiptDuplicate = combi.sort();
		    	for(let _i=0;_i<receiptDuplicate.length;_i++){
			    		console.log(receiptDuplicate[_i]);
			    }
		    	var reportDuplicate = [];

		    	for(var _j=0;_j<receiptDuplicate.length-1;_j++){
		    		if(receiptDuplicate[_j + 1] == receiptDuplicate[_j]){
		    			reportDuplicate.push(receiptDuplicate[_j]);
		    		}
		    	}

		    	console.log("reportDuplicate size: "+reportDuplicate.length);
		    	if(reportDuplicate.length == 0){
		    		find_combi = false;
		    	}
   			}
	    
	    	var str = "";
	    	if(choixAlea == 0){
	    		for(let k=0;k<combi.length-1;k++){
	    		str = str + combi[k] + ".";
		    	}
		    	str = str + combi[combi.length - 1];
	    	}
	    	else if(choixAlea == 1){
	    		str = str + "-";
	    		for(let k=0;k<combi.length-1;k++){
		    		str = str + combi[k] + "-";
		    	}
		    	str = str + combi[combi.length - 1];
	    	}
	    	else if(choixAlea == 2){
	    		str = str + "+";
	    		for(let k=0;k<combi.length-1;k++){
	    		str = str + combi[k] + "+";
		    	}
		    	str = str + combi[combi.length - 1];
	    	}
	    	
	    	//console.log(str);

			return str;
}

function getRandomIntInclusive(min, max) {
  min = Math.ceil(min);
  max = Math.floor(max);
  return Math.floor(Math.random() * (max - min +1)) + min;
}

function addlinesTable_pari(value, multi){
	
	for(let i=0;i<multi;i++){

	 var out = parseInt(value[0]) + i;

	let TR = document.createElement("tr");
	let TD1= document.createElement("td");
	let TD2= document.createElement("td");
	let TD3= document.createElement("td");
	let TD4= document.createElement("td");
	let txt1 = document.createTextNode(out);
	let txt2 = document.createTextNode(value[1]);
	let txt3 = document.createTextNode(value[2]);
	let txt4 = '<tr><td class="col-xs-3"><a href="#"><img src="ressources/rsrc_caissier/assets/images/delete.jpg"></a></td></tr>';


	TD1.appendChild(txt1);
	TD2.appendChild(txt2);
	TD3.appendChild(txt3);
	

	TR.appendChild(TD1);
	TR.appendChild(TD2);
	TR.appendChild(TD3);
	//TR.appendChild(TD4);

		document.getElementById('prono').appendChild(TR);
		$('.t_pari').find('tbody').append(txt4);
		//$('.prono').appendChild(TR);
		$(TD1).addClass('col-xs-3');
		//$(TD1).toggleClass('col-xs-3', true);
		$(TD2).toggleClass('col-xs-3', true);
		$(TD3).toggleClass('col-xs-3', true);
		$(TD4).toggleClass('col-xs-3', true);
	}
} 

function addlinesTable_spin(value, multi){
	
	for(let i=0;i<multi;i++){

	let TR = document.createElement("tr");
	let TD1= document.createElement("td");
	let TD2= document.createElement("td");
	let TD3= document.createElement("td");
	let TD4= document.createElement("td");
	let txt1 = document.createTextNode(value[0]+i);
	let txt2 = document.createTextNode(value[1]);
	let txt3 = document.createTextNode(value[2]);
	let txt4 = '<tr><td class="col-xs-3"><a href="#"><img src="ressources/rsrc_caissier/assets/images/delete.jpg"></a></td></tr>';


	TD1.appendChild(txt1);
	TD2.appendChild(txt2);
	TD3.appendChild(txt3);
	

	TR.appendChild(TD1);
	TR.appendChild(TD2);
	TR.appendChild(TD3);
	//TR.appendChild(TD4);

		document.getElementById('sp_prono').appendChild(TR);
		$('.sp_t_pari').find('tbody').append(txt4);
		//$('.prono').appendChild(TR);
		$(TD1).addClass('col-xs-3');
		//$(TD1).toggleClass('col-xs-3', true);
		$(TD2).toggleClass('col-xs-3', true);
		$(TD3).toggleClass('col-xs-3', true);
		$(TD4).toggleClass('col-xs-3', true);
	}
} 

function clear(){
		console.log('clear');
		document.getElementById('clear').value = '';
	}

function openRoom(){
	let login = $('#login_pari').text();
	var idcoderace = $('#idpartner2').text();
  //  console.log("login: "+idcoderace);
    confirm('OPEN ROOM \nOuvertures de toutes les caisses');
	$.ajax({
		url:"executeCob",
		type:"POST",
		 data:{
                'action':"OPENBIZ",
                'coderace':idcoderace
                
            },
		success:function(result){
			$.each(result, function(index, value){
				
			});
	    }
      });
}

function doCob(){
	let login = $('#login_pari').text();
	var idcoderace = $('#idpartner2').text();
    //console.log("login cob: "+login);
    confirm('CLOSE ROOM \nMettre à jour toutes les caisses');
	$.ajax({
		url:"executeCob",
		type:"POST",
		 data:{
                'action':"CLOSEBIZ",
                'coderace':idcoderace
            },
		success:function(result){
			$.each(result, function(index, value){
				
			});
	    }
      });
       
}
function connected(){
	let login = $('#login_pari').text();
    //console.log("login: "+login);
    
	$.ajax({
		url:"record",
		type:"GET",
		 data:{
                'login':login,
                'state':"C"
            },
		success:function(result){
			$.each(result, function(index, value){
				$('.pari_state').toggleClass('erreur', false);
				$('.pari_state').toggleClass('succes', true);
				$('.pari_state').empty();
				$('.pari_state').prepend("Connecté");
				$('.canprint').prop('disabled', false);
				$('.endshift').prop('disabled', false);
			});
	    }
      });
}

function disconnected(){
	document.getElementById('hiddenday').value='endofday';
	document.forms["form_endday"].submit();
//	$.ajax({
//		url:"record",
//		type:"POST",
//		 data:{
//                'login':login,
//                'state':"N"
//            },
//		success:function(result){
//			//$.each(result, function(index, value){
//		
//			//});
//	    }
//      });
}

function clear_form(){
	   $(':input')
	   .not(':button, :submit, :hidden, :reset')
	   .val('');
	  
	   document.getElementById('code').value='';
       // on vide d'abord le tableau
       $('.t_pay').find('tbody').empty();
       document.getElementById("coupon_error").innerHTML = "";
}

function close_shift(){
	var benefice = $('#sbenefice').text();
	var balance = $('#sbalance').text();
	var scaissier = $('#scaissier').text();
	$.ajax({
		url:"closeshift",
		type:"POST",
		 data:{
                'balance':balance,
                'benefice':benefice,
                'caissier':scaissier,
                'coderace':idcoderace
            },
		success:function(result){
			//$.each(result, function(index, value){
		
			//});
	    }
      });
}

/* manage administration */
function showFormAddpartner(){
	
	$('#c_partner').removeClass('invisible');
	$('#c_partner').addClass('solide');
	$('#c_partner').css('display','block');
	
	$('#e_partner').css('display','none');
	$('#e_caissier').css('display','none');
	$('#c_cashier').css('display','none');
	$('#c_bonus').css('display','none');
	$('#c_cagnotte').css('display','none');
	
	$(':input')
	   .not(':button, :submit, :hidden, :reset')
	   .val('');
}

function showFormManpartner(){
	
	$('#c_partner').removeClass('solide');
	$('#c_partner').addClass('invisible');
	$('#c_partner').css('display','none');
	
	$('#e_partner').css('display','block');
	$('#e_caissier').css('display','none');
	$('#c_cashier').css('display','none');
	$('#c_bonus').css('display','none');
	$('#c_cagnotte').css('display','none');
}

function showFormAdduser(){
	
	$('#c_partner').css('display','none');
	$('#e_partner').css('display','none');
	$('#e_caissier').css('display','none');
	
	$('#c_cashier').removeClass('invisible');
	$('#c_cashier').addClass('solide');
	$('#c_cashier').css('display','block');
	
	$('#c_bonus').css('display','none');
	$('#c_cagnotte').css('display','none');
	
	$(':input')
	   .not(':button, :submit, :hidden, :reset')
	   .val('');
 document.getElementById('cpart_state').value='';
}

function showFormManuser(){
	$('#c_partner').css('display','none');
	$('#e_partner').css('display','none');
	$('#e_caissier').css('display','block');
	
	$('#c_cashier').removeClass('solide');
	$('#c_cashier').addClass('invisible');
	$('#c_cashier').css('display','none');
	
	$('#c_bonus').css('display','none');
	$('#c_cagnotte').css('display','none');
}

function showFormAddbonus(){

	$('#c_partner').css('display','none');
	$('#e_partner').css('display','none');
	$('#e_caissier').css('display','none');
	
	$('#c_bonus').removeClass('invisible');
	$('#c_bonus').addClass('solide');
	$('#c_cashier').css('display','none');
	
	$('#c_bonus').css('display','block');
	$('#c_cagnotte').css('display','none');
	
	$(':input')
	   .not(':button, :submit, :hidden, :reset')
	   .val('');
	
}	

function showFormAddCagnotte(){
	$('#c_partner').css('display','none');
	$('#e_partner').css('display','none');
	$('#e_caissier').css('display','none');
	$('#c_bonus').css('display','none');
	
	$('#c_cagnotte').removeClass('invisible');
	$('#c_cagnotte').addClass('solide');
	$('#c_cashier').css('display','none');
	
	$('#c_cagnotte').css('display','block');
	
	$(':input')
	   .not(':button, :submit, :hidden, :reset')
	   .val('');
}

function printElement(elem){
	var domClone = elem.cloneNode(true);
	
	var $printSection = document.getElementById("printSection");
	
	if(!$printSection){
		var $printSection = document.createElement("div");
		$printSection.id = "printSection";
		//$('#printSection').css('width','300px');
		document.body.appendChild($printSection);
	}
	
	$printSection.innerHTML = "";
	$printSection.appendChild(domClone);
	//window.print();
	$('#printSection').css('display','none');
	$('#imprimer').modal('hide');
	$('#p_imprimer').modal('hide');
	$('#shiftModal').modal('hide');
    //$('body').css('vibility', 'visible');
    document.getElementById('code').focus();
}

//* script pour spin2win */

function verif_spin(evt) {
    // verification des caratères saisis autorisés
		let keyCode = evt.which ? evt.which : evt.keyCode;
        let accept = 'nrvmtfslhdpiabcdefNRVMTFSLHDPIABCDEF0123456789';
		let multiplicite = 1;
		let sp_span = document.getElementById("ispcode");
		sp_span.innerHTML = "";
		let libchoix, codePari, typejeu;
		
       if (accept.indexOf(String.fromCharCode(keyCode)) >= 0 || evt.keyCode===13) {
			// verification des numéros saisis
    	   let ispchoice = $("#spcode").val().toLowerCase();
		  
		   if (evt.keyCode === 13) {
				evt.preventDefault();

				// on vide d'abord le tableau
   			$('.sp_t_pari').find('tbody').empty();
   			document.getElementById('spmontant').value='';
   			
   				let value;
				let echar = "";
				let  _echar = [];
				let __echar = [];
				//let ispchoice = $("#spcode").val();
				//console.log('CASE: '+ispchoice.toLowerCase());
				console.log("saisi: "+ispchoice);
				echar = ispchoice.charAt(0);
				console.log("first: "+echar);
				
				if(echar === 'v'){ //couleur verte ou 0
					console.log("ifirst: "+echar+" : couleur verte");
					
					sp_span.innerHTML = "couleur";
					value = [1, 'vert', 'couleur'];
					//addlinesTable_spin(value, multiplicite);
					//document.getElementById('spmontant').focus();
				}
				else if(echar === 'r'){ // boule rouge
					console.log("first: "+echar+" : rouge");
					sp_span.innerHTML = "couleur";
					value = [1, 'rouge', 'couleur'];
					//addlinesTable_spin(value, multiplicite);
					//document.getElementById('spmontant').focus();
					
				} //end tout rouge
				else if(echar === 'n' || echar === 'N'){ //boule noir
					
					console.log("first: "+echar+" : rouge");
					sp_span.innerHTML = "couleur";
					value = [1, 'noir', 'couleur'];
					//addlinesTable_spin(value, multiplicite);
					//document.getElementById('spmontant').focus();
				} // end des couleurs
				else if(echar === 'm'){ //mirroir
					console.log("first: "+echar+" : mirroir");
					
					let mir = ispchoice.substring(1);
					
					let nbre = parseInt(mir);
					
					if(Number.isInteger(nbre) == false){
						sp_span.style.color = "red";
						sp_span.innerHTML = "Numéro invalide";
						return;
					}
					else{
						if(nbre == 12 || nbre == 21){
							sp_span.innerHTML = "mirroirs 12/21";
							value = [1, 'mirroir 12/21', 'mirroir'];
						}
						else if(nbre == 13 || nbre == 31){
							sp_span.innerHTML = "mirroirs 13/31";
							value = [1, 'mirroir 13/31', 'mirroir'];
						}
						else if(nbre == 23 || nbre == 32){
							sp_span.innerHTML = "mirroirs 23/32";
							value = [1, 'mirroir 23/32', 'mirroir'];
						}
						else{
							sp_span.style.color = "red";
							sp_span.innerHTML = "Choix inconnu";
							return;
						}
					}
				}
				else if(echar === 't'){ //twins
					//numéros jumeaux
					console.log("first: "+echar+" : twins");
					let mir = ispchoice.substring(1);
					
					let nbre = parseInt(mir);
					
					if(Number.isInteger(nbre) == false){
						sp_span.style.color = "red";
						sp_span.innerHTML = "Numéro invalide";
						return;
					}
					else{
						if(nbre == 11){
							sp_span.innerHTML = "twins 11";
							value = [1, 'twin 11', 'twin'];
						}
						else if(nbre == 22){
							sp_span.innerHTML = "twins 22";
							value = [1, 'twin 22', 'twin'];
						}
						else if(nbre == 33){
							sp_span.innerHTML = "twins 33";
							value = [1, 'twin 33', 'twin'];
						}
						else{
							sp_span.style.color = "red";
							sp_span.innerHTML = "Choix inconnu";
							return;
						}
					}
				}
				else if(echar === 'f'){ //final
					//final
					console.log("first: "+echar+" : final");
					let mir = ispchoice.substring(1);
					let nbre = parseInt(mir);
					
					if(Number.isInteger(nbre) == false){
						sp_span.style.color = "red";
						sp_span.innerHTML = "Numéro invalide";
						return;
					}
					else{
						if(nbre == 0){
							sp_span.innerHTML = "final 0";
							value = [1, 'final 0', 'finals'];
						}
						else if(nbre == 1){
							sp_span.innerHTML = "final 1";
							value = [1, 'final 1', 'finals'];
						}
						else if(nbre == 2){
							sp_span.innerHTML = "final 2";
							value = [1, 'final 2', 'finals'];
						}
						else if(nbre == 3){
							sp_span.innerHTML = "final 3";
							value = [1, 'final 3', 'finals'];
						}
						else if(nbre == 4){
							sp_span.innerHTML = "final 4";
							value = [1, 'final 4', 'finals'];
						}
						else if(nbre == 5){
							sp_span.innerHTML = "final 5";
							value = [1, 'final 5', 'finals'];
						}
						else if(nbre == 6){
							sp_span.innerHTML = "final 6";
							value = [1, 'final 6', 'finals'];
						}
						else{
							sp_span.style.color = "red";
							sp_span.innerHTML = "Choix inconnu";
							return;
						}
					}
				}
				else if(echar === 's'){ //secteurs A,B,C,D,E,F
					//secteur
					console.log("first: "+echar+" : secteur");
					let mir = ispchoice.substring(1);
					console.log("secteur: "+mir);
						if(mir === 'a'){
							sp_span.innerHTML = "secteurs A";
							value = [1, 'secteur A', 'secteurs'];
						}
						else if(mir === 'b'){
							sp_span.innerHTML = "secteurs B";
							value = [1, 'secteur B', 'secteurs'];
						}
						else if(mir === 'c'){
							sp_span.innerHTML = "secteurs C";
							value = [1, 'secteur C', 'secteurs'];
						}
						else if(mir === 'd'){
							sp_span.innerHTML = "secteurs D";
							value = [1, 'secteur D', 'secteurs'];
						}
						else if(mir === 'e'){
							sp_span.innerHTML = "secteurs E";
							value = [1, 'secteur E', 'secteurs'];
						}
						else if(mir === 'f'){
							sp_span.innerHTML = "secteurs F";
							value = [1, 'secteur F', 'secteurs'];
						}
						else{
							sp_span.style.color = "red";
							sp_span.innerHTML = "Choix inconnu";
							return;
						}
				}
				else if(echar === 'l'){ //low 
					//low number
					console.log("first: "+echar+" : LOW");
					

				//	sp_span.style.color = "blue";
					sp_span.innerHTML = "[1-18]";
								
					value = [1, 'low', 'low/high'];
				//	addlinesTable_spin(value, multiplicite);
				//	document.getElementById('spmontant').focus();

				}
				else if(echar === 'h'){ //high
					//high number
					console.log("first: "+echar+" : HIGH");
					sp_span.innerHTML = "[19-36]";
								
					value = [1, 'high', 'low/high'];
				//	addlinesTable_spin(value, multiplicite);
				//	document.getElementById('spmontant').focus();

				}
				else if(echar === 'c'){ //low/high couleur (combiné)
					//secteur
					console.log("first: "+echar+" : low/high color");
					let mir = ispchoice.substring(1);
					
						if(mir === 'lr'){
							sp_span.innerHTML = "low/high Rouge";
							value = [1, 'low Rouge', 'low/high couleur'];
						}
						else if(mir === 'ln'){
							sp_span.innerHTML = "low/high Noir";
							value = [1, 'low Noir', 'low/high couleur'];
						}
						else if(mir === 'hr'){
							sp_span.innerHTML = "low/high Rouge";
							value = [1, 'high Rouge', 'low/high couleur'];
						}
						else if(mir === 'hn'){
							sp_span.innerHTML = "low/high Noir";
							value = [1, 'high Noir', 'low/high couleur'];
						}
						else{
							sp_span.style.color = "red";
							sp_span.innerHTML = "Choix inconnu";
							return;
						}
				}
				else if(echar === 'd'){ //douzaine
					//douzaine
					console.log("first: "+echar+" : douzaine");
					let mir = ispchoice.substring(1);
					
					let nbre = parseInt(mir);
					
					if(Number.isInteger(nbre) == false){
						sp_span.style.color = "red";
						sp_span.innerHTML = "Numéro invalide";
						return;
					}
					else{
						if(nbre == 1){
							sp_span.innerHTML = "douzaine[1-12]";			
							value = [1, 'douzaine 1', 'douzaine'];
						}
						else if(nbre == 2){
							sp_span.innerHTML = "douzaine[13-24]";			
							value = [1, 'douzaine 2', 'douzaine'];
						}
						else if(nbre == 3){
							sp_span.innerHTML = "douzaine[25-36]";			
							value = [1, 'douzaine 3', 'douzaine'];
						}
						else{
							sp_span.style.color = "red";
							sp_span.innerHTML = "Choix inconnu";
							return;
						}
					}
				}
				else if(echar === 'p'){
					console.log("numero pair "+echar);
					
				//	sp_span.style.color = "blue";
					sp_span.innerHTML = "pair";
								
					value = [1, 'pair', 'pair/impair'];
				//	addlinesTable_spin(value, multiplicite);
				//	document.getElementById('spmontant').focus();
							
				}
				else if(echar === 'i'){
					
					console.log("numero impair "+echar);
					

				//	sp_span.style.color = "blue";
					sp_span.innerHTML = "impair";
								
					value = [1, 'impair', 'pair/impair'];
					//addlinesTable_spin(value, multiplicite);
					//document.getElementById('spmontant').focus();
					
				}
				else{  // numéros simples

					let mir = ispchoice.trim();
					
					let nbre = parseInt(mir);
					
					if(Number.isInteger(nbre) == false){
						sp_span.style.color = "red";
						sp_span.innerHTML = "Numéro invalide";
						return;
					}
					else if(nbre < 1 || nbre > 36){
						sp_span.style.color = "red";
						sp_span.innerHTML = "Choix entre 1 et 36";
						return;
					}
					else{
						sp_span.innerHTML = "single";
						value = [1, nbre, 'Single'];
					}
					
				}
				sp_span.style.color = "blue";
				addlinesTable_spin(value, multiplicite);
				document.getElementById('spmontant').focus();
			}// endif eventkey code === 13
           return true;
       } else {
       	//not an accept character
           return false;
       }


       
}


