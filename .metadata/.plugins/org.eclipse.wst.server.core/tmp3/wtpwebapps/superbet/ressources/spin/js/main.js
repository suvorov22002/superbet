// // on construit notre tableau qui contiendra les differrents numeros de la roue 
// // on utilise la variable segment du fichier segment.js.
// let tabNumeros = segment.map(function (item) {
//   return item.segmentName
// })

// // On definit une marge de securite pour eviter que 
// // la tete de lecture s'arrete a la limite entre deux tranches.
// let ecartSecu = 0;

// // Le nombre de tours avant l'arret de la roue.
// let nbTours = 30;

// // Le tableau qui contiendra les plages d'angles possibles en fonctions du numero
// let tabAngles = [];

// // La largeur d'un segment de numero en prenant en compte l'ecart de securite
// let largeurSegment = (360 / tabNumeros.length) - ecartSecu;

// //L'angle de rotation au début est de 360°/nbNumero
// let coeffRotation = 360 / (tabNumeros.length);

// //La roue n'est pas en marche	
// let arret = false;

// //Un décompte qui permet d'effectuer un certain nombre de tours avant de ralentir
// let decompte = nbTours * tabNumeros.length;

// //NUMERO DEPART
// let numeroDepart = 0;

// //Index du numero depart dans le tableau  tabNumeros
// let indexNumeroDepart = tabNumeros.findIndex(function (item) {
//   return item == numeroDepart;
// });

// let IDInterval;

// // On recupere la roue
// let roue = $('#wheel');;

// let rotation = 0;



// //On détermine les plages d'angles pour chaque segement en prenant compte de l'écart de sécurité
// for (let i = 0; i < tabNumeros.length; i++) {

//   let tabAnglesTemp = [];

//   tabAnglesTemp.push(
//     (i + 1) * ecartSecu + i * largeurSegment
//   );

//   tabAnglesTemp.push(
//     (i + 1) * ecartSecu + (i + 1) * largeurSegment - ecartSecu
//   );

//   tabAngles.push(tabAnglesTemp);
// }


// //Roration de départ en fonction de la case choisie
// roue.rotate(((tabAngles[indexNumeroDepart][1] - tabAngles[indexNumeroDepart][0]) + tabAngles[indexNumeroDepart][0] + 3 * coeffRotation) - 72);

// //fait tourner la roue
// function tournerRoue() {

//   decompte--;

//   //Si le décompte est à zéro on enclenche le ralentit		
//   if (decompte == 0) {
//     arret = true;
//   }

//   //Diminue le coeffRotation=>roue ralentit, 
//   if (arret == true && coeffRotation > 0) {
//     coeffRotation -= 1;
//   }

//   //Si la roue est à l'arret on supprime l'intervalle		
//   else if (arret == true && coeffRotation <= 0) {
//     clearInterval(IDInterval);
//   }

//   rotation += coeffRotation;

//   //On effectue une rotation de la roue en fonction du coeff rotation
//   roue.rotate(rotation);
// }


// function rotationRoue() {
//   //On supprime l'intervalle s'il s'agit d'un deuxième lancé
//   clearInterval(IDInterval);

//   //on appelle la méthode toutes les 30ms
//   IDInterval = setInterval(tournerRoue, 10);
// }

function refreshDatas(){
	$.ajax({
		url:"refreshdatas",
		type:"GET",
		//async: false,
		data:{
			'partner':coderace
		},
		success:function(result){
			$.each(result, function(index, value){
				var v;
				var m;
				//console.log('chknbre '+value['chknbre0']+' hn: '+chknbre_item);
				for(let j=0;j<36;j++){
                    v = 'chknbre'+(j+1);
					//console.log(v+'  || chknbre '+value[v]);
					$(".stats_item_value").eq(j).empty();
					$(".stats_item_value").eq(j).prepend(value[v]);
				}
				$(".stats_item_value").eq(36).empty();
				$(".stats_item_value").eq(36).prepend(value.chknbre0);
				
				for(let j=0;j<3;j++){
					m = 'chkmirror'+j;
					$(".stat_mirror_value").eq(j).empty();
					$(".stat_mirror_value").eq(j).prepend(value[m]);
				}
				
				$(".stat_twin_value").eq(0).empty();
				$(".stat_twin_value").eq(0).prepend(value['chktwin']);
				
				//couleur rouge
				$(".stats_color_1").eq(0).empty();
				$(".stats_color_1").eq(0).prepend(value['chkcolor0']);
				
				//couleur noir
				$(".stats_color_2").eq(0).empty();
				$(".stats_color_2").eq(0).prepend(value['chkcolor1']);
				
				//couleur verte
				$(".stats_color_3").eq(0).empty();
				$(".stats_color_3").eq(0).prepend(value['chkcolor2']);
				
				//pair, impair
				$(".stat_2_value_1_container_value").eq(3).empty();
				$(".stat_2_value_1_container_value").eq(3).prepend(value['chkpair']);
				
				$(".stat_2_value_1_container_value").eq(4).empty();
				$(".stat_2_value_1_container_value").eq(4).prepend(value['chkipair']);
				
				//low, high
				$(".stat_2_value_1_container_value").eq(5).empty();
				$(".stat_2_value_1_container_value").eq(5).prepend(value['chklow']);
				
				$(".stat_2_value_1_container_value").eq(6).empty();
				$(".stat_2_value_1_container_value").eq(6).prepend(value['chkhigh']);
				
				//douzaine
				for(let j=0;j<3;j++){
					m = 'chkdouz'+(j+1);
					$(".stat_2_value_1_container_value").eq(j).empty();
					$(".stat_2_value_1_container_value").eq(j).prepend(value[m]);
				}
				
				//$(".stat_2_value_1_container_value").eq(0).empty();
				//$(".stat_2_value_1_container_value").eq(0).prepend(value['chkdouz1']);
				
				//$(".stat_2_value_1_container_value").eq(1).empty();
				//$(".stat_2_value_1_container_value").eq(1).prepend(value['chkdouz2']);
				
				//$(".stat_2_value_1_container_value").eq(2).empty();
				//$(".stat_2_value_1_container_value").eq(2).prepend(value['chkdouz3']);
				
				//finals
				for(let j=0;j<7;j++){
					m = 'chkfinals'+j;
					$(".stat_2_value_1_container_value").eq(7+j).empty();
					$(".stat_2_value_1_container_value").eq(7+j).prepend(value[m]);
				}
				
		//		$(".stat_2_value_1_container_value").eq(7).empty();
			//	$(".stat_2_value_1_container_value").eq(7).prepend(value['chkfinals0']);
				
				//$(".stat_2_value_1_container_value").eq(8).empty();
				//$(".stat_2_value_1_container_value").eq(8).prepend(value['chkfinals1']);
				
				//$(".stat_2_value_1_container_value").eq(9).empty();
				//$(".stat_2_value_1_container_value").eq(9).prepend(value['chkfinals2']);
				
				//$(".stat_2_value_1_container_value").eq(10).empty();
				//$(".stat_2_value_1_container_value").eq(10).prepend(value['chkfinals3']);
				
				//$(".stat_2_value_1_container_value").eq(11).empty();
				//$(".stat_2_value_1_container_value").eq(11).prepend(value['chkfinals4']);
				
				//$(".stat_2_value_1_container_value").eq(12).empty();
				//$(".stat_2_value_1_container_value").eq(12).prepend(value['chkfinals5']);
				
				//$(".stat_2_value_1_container_value").eq(13).empty();
				//$(".stat_2_value_1_container_value").eq(13).prepend(value['chkfinals6']);
				
				// secteurs

				for(let j=0;j<6;j++){
					m = 'chksecteurs'+j;
					$(".stat_sector_value").eq(j).empty();
					$(".stat_sector_value").eq(j).prepend(value[m]);
				}
				
				//previous 6 draws
				var nbre = value.tot_tirage;
				
				switch(nbre){
					case 1:
						draw_1 = (value.draw1).split("_");
						
						$(".last_result_num_value").eq(0).empty();
						$(".last_result_num_value").eq(0).prepend('#'+draw_1[0]);
						
						$(".last_result_value").eq(0).empty();
						$(".last_result_value").eq(0).prepend(draw_1[1]);
						
						break;
					
					case 2:
						draw_2 = (value.draw2).split("_");
						draw_1 = (value.draw1).split("_");
						
						$(".last_result_num_value").eq(0).empty();
						$(".last_result_num_value").eq(0).prepend('#'+draw_2[0]);
						$(".last_result_value").eq(0).empty();
						$(".last_result_value").eq(0).prepend(draw_2[1]);
						
						$(".last_result_num_value").eq(1).empty();
						$(".last_result_num_value").eq(1).prepend('#'+draw_1[0]);
						$(".last_result_value").eq(1).empty();
						$(".last_result_value").eq(1).prepend(draw_1[1]);
						
						break;
					
					case 3:
						draw_3 = (value.draw3).split("_");
						draw_2 = (value.draw2).split("_");
						draw_1 = (value.draw1).split("_");
						
						$(".last_result_num_value").eq(0).empty();
						$(".last_result_num_value").eq(0).prepend('#'+draw_3[0]);
						$(".last_result_value").eq(0).empty();
						$(".last_result_value").eq(0).prepend(draw_3[1]);
						
						$(".last_result_num_value").eq(1).empty();
						$(".last_result_num_value").eq(1).prepend('#'+draw_2[0]);
						$(".last_result_value").eq(1).empty();
						$(".last_result_value").eq(1).prepend(draw_2[1]);
						
						$(".last_result_num_value").eq(2).empty();
						$(".last_result_num_value").eq(2).prepend('#'+draw_1[0]);
						$(".last_result_value").eq(2).empty();
						$(".last_result_value").eq(2).prepend(draw_1[1]);
						
						break;
						
					case 4:
						draw_4 = (value.draw4).split("_");
						draw_3 = (value.draw3).split("_");
						draw_2 = (value.draw2).split("_");
						draw_1 = (value.draw1).split("_");
						
						$(".last_result_num_value").eq(0).empty();
						$(".last_result_num_value").eq(0).prepend('#'+draw_4[0]);
						$(".last_result_value").eq(0).empty();
						$(".last_result_value").eq(0).prepend(draw_4[1]);
						
						$(".last_result_num_value").eq(1).empty();
						$(".last_result_num_value").eq(1).prepend('#'+draw_3[0]);
						$(".last_result_value").eq(1).empty();
						$(".last_result_value").eq(1).prepend(draw_3[1]);
						
						$(".last_result_num_value").eq(2).empty();
						$(".last_result_num_value").eq(2).prepend('#'+draw_2[0]);
						$(".last_result_value").eq(2).empty();
						$(".last_result_value").eq(2).prepend(draw_2[1]);
						
						$(".last_result_num_value").eq(3).empty();
						$(".last_result_num_value").eq(3).prepend('#'+draw_1[0]);
						$(".last_result_value").eq(3).empty();
						$(".last_result_value").eq(3).prepend(draw_1[1]);
						
						break;
						
					case 5:
						draw_5 = (value.draw5).split("_");
						draw_4 = (value.draw4).split("_");
						draw_3 = (value.draw3).split("_");
						draw_2 = (value.draw2).split("_");
						draw_1 = (value.draw1).split("_");
						
						$(".last_result_num_value").eq(0).empty();
						$(".last_result_num_value").eq(0).prepend('#'+draw_5[0]);
						$(".last_result_value").eq(0).empty();
						$(".last_result_value").eq(0).prepend(draw_5[1]);
						
						$(".last_result_num_value").eq(1).empty();
						$(".last_result_num_value").eq(1).prepend('#'+draw_4[0]);
						$(".last_result_value").eq(1).empty();
						$(".last_result_value").eq(1).prepend(draw_4[1]);
						
						$(".last_result_num_value").eq(2).empty();
						$(".last_result_num_value").eq(2).prepend('#'+draw_3[0]);
						$(".last_result_value").eq(2).empty();
						$(".last_result_value").eq(2).prepend(draw_3[1]);
						
						$(".last_result_num_value").eq(3).empty();
						$(".last_result_num_value").eq(3).prepend('#'+draw_2[0]);
						$(".last_result_value").eq(3).empty();
						$(".last_result_value").eq(3).prepend(draw_2[1]);
						
						$(".last_result_num_value").eq(4).empty();
						$(".last_result_num_value").eq(4).prepend('#'+draw_1[0]);
						$(".last_result_value").eq(4).empty();
						$(".last_result_value").eq(4).prepend(draw_1[1]);
						
						break;
						
					case 6:
						draw_6 = (value.draw6).split("_");
						draw_5 = (value.draw5).split("_");
						draw_4 = (value.draw4).split("_");
						draw_3 = (value.draw3).split("_");
						draw_2 = (value.draw2).split("_");
						draw_1 = (value.draw1).split("_");
						
						$(".last_result_num_value").eq(0).empty();
						$(".last_result_num_value").eq(0).prepend('#'+draw_6[0]);
						$(".last_result_value").eq(0).empty();
						$(".last_result_value").eq(0).prepend(draw_6[1]);
						
						$(".last_result_num_value").eq(1).empty();
						$(".last_result_num_value").eq(1).prepend('#'+draw_5[0]);
						$(".last_result_value").eq(1).empty();
						$(".last_result_value").eq(1).prepend(draw_5[1]);
						
						$(".last_result_num_value").eq(2).empty();
						$(".last_result_num_value").eq(2).prepend('#'+draw_4[0]);
						$(".last_result_value").eq(2).empty();
						$(".last_result_value").eq(2).prepend(draw_4[1]);
						
						$(".last_result_num_value").eq(3).empty();
						$(".last_result_num_value").eq(3).prepend('#'+draw_3[0]);
						$(".last_result_value").eq(3).empty();
						$(".last_result_value").eq(3).prepend(draw_3[1]);
						
						$(".last_result_num_value").eq(4).empty();
						$(".last_result_num_value").eq(4).prepend('#'+draw_2[0]);
						$(".last_result_value").eq(4).empty();
						$(".last_result_value").eq(4).prepend(draw_2[1]);
						
						$(".last_result_num_value").eq(5).empty();
						$(".last_result_num_value").eq(5).prepend('#'+draw_1[0]);
						$(".last_result_value").eq(5).empty();
						$(".last_result_value").eq(5).prepend(draw_1[1]);
						
						break;
				}
				
			});
		}
	});
			
}


