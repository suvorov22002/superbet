<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html lang="en">
 
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Lottery Draw</title>
    <link rel="stylesheet" href="ressources/lottery/css/anime.css">
    <link rel="stylesheet" href="ressources/lottery/css/normalize.css" />
    <link rel="stylesheet" href="ressources/lottery/css/main.css" />
    <link rel="stylesheet" type="text/css" href="ressources/rsrc_caissier/bootstrap-3.3.6/dist/css/bootstrap.min.css">
    <script type="text/javascript">
	<!--
	var requete;
	
	
	//-->
	</script>
</head>

<body>
    <span id="partner">${partner}</span>
   
    <!-- Start Page 1-->
    <div id="page1"></div>
    <!-- End Page 1 -->

    <!-- Start Page 2-->
    <div id="page2"></div>
    <!-- End Page 2-->

    <!-- Start Page 3-->
    <div id="page3"></div>
    <!-- End Page 3-->

    <!-- Start Page 4-->
    <div id="page4"></div>
    <!-- End Page 4-->

	
<!--     <script src="ressources/lottery/js/vendor/jquery-3.4.1.min.js"></script> -->
    <script src="ressources/spin/js/jquery.js"></script>
    <script type="text/javascript">
	  let coderace = $('#partner').text();
	  console.log("mon partenaire sur "+coderace);
	  let arrayNumroSortant = [];
		let outputNumber = [];
		let trouve_draw = false;
		let tirage = 0;
		let blackout_draw;
		let numero_tirage = 0;
		let draw_5 = [];
		let draw_4 = [];
		let draw_3 = [];
		let draw_2 = [];
		let draw_1 = [];
		let bonus_3 = [];
		let bonus_2 = [];
		let bonus_1 = [];
		let arrayDernierTirage = [];
		let arrayNumeroLesPlusTirees = [];
		let arrayNumeroLesMoinsTirees = [];
		let arrayLastBonus = [];
		let multiplicateur;
		let arraySommeTotale5derniersTirage = [];
		let arrayLastMultiplicateur = [];
		let arrayFistColorOfDraw = [18, 34, 56, 78, 3];
		let arrayJackpot2 = []
		let gamestate;
		let str_combi;
	    let isJoin = false;
	    let urlServeur;


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

		function restoreDatas(){

			//var itoken = localStorage.getItem("token");

			$.ajax({
				url:"updatebonus",
				type:"POST",
				async: false,
				data:{
					'partner':coderace
				},
				success:function(result){
					$.each(result, function(index, value){
						numero_tirage = value.drawnumk;
						var nbre = value.tot_tirage;
						var _nbre = value.tot_bonus;


					//	if(/* keno == 185 &&*/ gamestate == 2){
					//	       numero_tirage = numero_tirage - 1;
					//	}
						   
						
						console.log("_nbre: "+_nbre+" | "+value.bonus1);
						switch(nbre){
							case 1:
								draw_1 = (value.draw1).split("_");
								arrayDernierTirage = [
								    {
								        identifiant: draw_1[0],
								        heure: draw_1[3],
								        arrayNumero: draw_1[1].split("-"),
								        multiplicateur: draw_1[2]+'x'
								    },
								];
					 			
								break;
							
							case 2:
								draw_2 = (value.draw2).split("_");
								draw_1 = (value.draw1).split("_");
								arrayDernierTirage = [
									{
								        identifiant: draw_2[0],
								        heure: draw_2[3],
								        arrayNumero: draw_2[1].split("-"),
								        multiplicateur: draw_2[2]+'x'
								    },
								    {
								        identifiant: draw_1[0],
								        heure: draw_1[3],
								        arrayNumero: draw_1[1].split("-"),
								        multiplicateur: draw_1[2]+'x'
								    },
								];
								
								break;
							
							case 3:
								draw_3 = (value.draw3).split("_");
								draw_2 = (value.draw2).split("_");
								draw_1 = (value.draw1).split("_");
								arrayDernierTirage = [
									{
								        identifiant: draw_3[0],
								        heure: draw_3[3],
								        arrayNumero: draw_3[1].split("-"),
								        multiplicateur: draw_3[2]+'x' 
								    },
									{
								        identifiant: draw_2[0],
								        heure: draw_2[3],
								        arrayNumero: draw_2[1].split("-"),
								        multiplicateur: draw_2[2]+'x'
								    },
								    {
								        identifiant: draw_1[0],
								        heure: draw_1[3],
								        arrayNumero: draw_1[1].split("-"),
								        multiplicateur: draw_1[2]+'x'
								    },
								];
								
								break;
								
							case 4:
								draw_4 = (value.draw4).split("_");
								draw_3 = (value.draw3).split("_");
								draw_2 = (value.draw2).split("_");
								draw_1 = (value.draw1).split("_");
								arrayDernierTirage = [
									{
								        identifiant: draw_4[0],
								        heure: draw_4[3],
								        arrayNumero: draw_4[1].split("-"),
								        multiplicateur: draw_4[2]+'x'
								    },
									{
								        identifiant: draw_3[0],
								        heure: draw_3[3],
								        arrayNumero: draw_3[1].split("-"),
								        multiplicateur: draw_3[2]+'x' 
								    },
									{
								        identifiant: draw_2[0],
								        heure: draw_2[3],
								        arrayNumero: draw_2[1].split("-"),
								        multiplicateur: draw_2[2]+'x'
								    },
								    {
								        identifiant: draw_1[0],
								        heure: draw_1[3],
								        arrayNumero: draw_1[1].split("-"),
								        multiplicateur: draw_1[2]+'x'
								    },
								];
								
								break;
								 
							case 5:
								draw_5 = (value.draw5).split("_");
								draw_4 = (value.draw4).split("_");
								draw_3 = (value.draw3).split("_");
								draw_2 = (value.draw2).split("_");
								draw_1 = (value.draw1).split("_");
								arrayDernierTirage = [
									{
								        identifiant: draw_5[0],
								        heure: draw_5[3],
								        arrayNumero: draw_5[1].split("-"),
								        multiplicateur: draw_5[2]+'x'
								    },
									{
								        identifiant: draw_4[0],
								        heure: draw_4[3],
								        arrayNumero: draw_4[1].split("-"),
								        multiplicateur: draw_4[2]+'x'
								    },
									{
								        identifiant: draw_3[0],
								        heure: draw_3[3],
								        arrayNumero: draw_3[1].split("-"),
								        multiplicateur: draw_3[2]+'x' 
								    },
									{
								        identifiant: draw_2[0],
								        heure: draw_2[3],
								        arrayNumero: draw_2[1].split("-"),
								        multiplicateur: draw_2[2]+'x'
								    },
								    {
								        identifiant: draw_1[0],
								        heure: draw_1[3],
								        arrayNumero: draw_1[1].split("-"),
								        multiplicateur: draw_1[2]+'x'
								    },
								];
								break;
								
								default:
									break;
						}
						
						$("#coderace_1").empty();
						$("#id_1").empty();
						$("#heure_1").empty();
						$("#b_amount_1").empty();
						$("#coderace_2").empty();
						$("#id_2").empty();
						$("#heure_2").empty();
						$("#b_amount_2").empty();
						$("#coderace_3").empty();
						$("#id_3").empty();
						$("#heure_3").empty();
						$("#b_amount_3").empty();
						
						switch(_nbre){
							case 1:
								bonus_1 = (value.bonus1).split("_");
								arrayLastBonus = [
									{
										coderace:bonus_1[0],
										code:bonus_1[2],
										heure:bonus_1[1].substring(11),
										amount:bonus_1[3]
									}
								]
								break;
							case 2:
								bonus_1 = (value.bonus1).split("_");
								bonus_2 = (value.bonus2).split("_");
			 		            arrayLastBonus = [
									{
										coderace:bonus_1[0],
										code:bonus_1[2],
										heure:bonus_1[1].substring(11),
										amount:bonus_1[3]
									},
									{
										coderace:bonus_2[0],
										code:bonus_2[2],
										heure:bonus_2[1].substring(11),
										amount:bonus_2[3]
									}
								 ]
								break;
							case 3:
								bonus_1 = (value.bonus1).split("_");
								bonus_2 = (value.bonus2).split("_");
								bonus_3 = (value.bonus3).split("_");
								arrayLastBonus = [
									{
										coderace:bonus_1[0],
										code:bonus_1[2],
										heure:bonus_1[1].substring(11),
										amount:bonus_1[3]
									},
									{
										coderace:bonus_2[0],
										code:bonus_2[2],
										heure:bonus_2[1].substring(11),
										amount:bonus_2[3]
									},
									{
										coderace:bonus_3[0],
										code:bonus_3[2],
										heure:bonus_3[1].substring(11),
										amount:bonus_3[3]
									}
								 ]
								
								break;
						}
					});
				}
			});
			//console.log("arrayLastBonus: "+arrayLastBonus);
			//console.log("arrybuild: "+arrayDernierTirage);
		}
		restoreDatas();

   function retrieveSumOdd(){
//console.log("retrieveSumOdd: ");
var odds = [];
   	 $.ajax({
          url:"sumodd",
          type:"GET",
          async: false,
          data:{
			 'partner':coderace
		  },
          success:function(result){
          	
            $.each(result, function(key, value){
            	console.log(' : '+value.sodd);
            	var res = (value.sodd).substring(1,value.sodd.length-1);

            //	console.log(key+' : '+value);
				odds = res.split(',');
              // odds = value.sodd;
            //	console.log('chaine: '+odds);
            	
            	
            //	console.log('CLE: '+key+' - '+value);
            //    console.log('RESULT: '+result[key]);
            });
          } 
        });
   	for(let j=0;j<9;j++){
   		var str = [];
   		var s;
   		s = odds[j];
   		str = s.split(':');
	//	console.log('RESULT: '+str[0].substring(1));
	//	console.log(str[0].length);
		var st = {
					temp : str[0].substring(1, str[0].length - 1)+'X',
					numero : str[1]
		         }
    	arrayNumeroLesPlusTirees.push(st);
    	arrayNumeroLesMoinsTirees
	}

	for(let i=79;i>74;i--){
   		var str = [];
   		var s;
   		s = odds[i];
   		str = s.split(':');
	//	console.log('RESULT: '+str[0].substring(1));
	//	console.log(str[0].length);
		var st = {
					temp : str[0].substring(1, str[0].length - 1)+'X',
					numero : str[1]
		         }
    	arrayNumeroLesMoinsTirees.push(st);
    	
	}
    
   }
   retrieveSumOdd();

   function retrieveMultiplicateur(){
    //  console.log("CODERACE: "+coderace);
        $.ajax({
          url:"multiplicateur",
          type:"POST",
          async: false,
          data:{
			 'partner':coderace
		  },
          success:function(result){
          	arrayLastMultiplicateur = result;
            $.each(result, function(key, value){
       
           //   console.log('Multiplicateur: '+key+' - '+value['multiplicateur']);
           //   console.log('heureTirage: '+result[key]['heureTirage'].substring(11));

              var nbre = arrayLastMultiplicateur.length;
           //   console.log("nbre multi: "+nbre);
              switch(nbre){
                
                    case 1:
                        arrayDernierMultiplicateur = [
                            {
                                temp: arrayLastMultiplicateur[0]['multiplicateur'],
                                time: arrayLastMultiplicateur[0]['heureTirage'].substring(11),
                            }
                        ];
                        break;
                    case 2:
                        arrayDernierMultiplicateur = [
                            {
                                temp: arrayLastMultiplicateur[0]['multiplicateur'],
                                time: arrayLastMultiplicateur[0]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[1]['multiplicateur'],
                                time: arrayLastMultiplicateur[1]['heureTirage'].substring(11),
                            }
                        ];
                        break;
                    case 3:
                        arrayDernierMultiplicateur = [
                            {
                                temp: arrayLastMultiplicateur[0]['multiplicateur'],
                                time: arrayLastMultiplicateur[0]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[1]['multiplicateur'],
                                time: arrayLastMultiplicateur[1]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[2]['multiplicateur'],
                                time: arrayLastMultiplicateur[2]['heureTirage'].substring(11),
                            }
                        ];
                        break;
                    case 4:
                        arrayDernierMultiplicateur = [
                            {
                                temp: arrayLastMultiplicateur[0]['multiplicateur'],
                                time: arrayLastMultiplicateur[0]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[1]['multiplicateur'],
                                time: arrayLastMultiplicateur[1]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[2]['multiplicateur'],
                                time: arrayLastMultiplicateur[2]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[3]['multiplicateur'],
                                time: arrayLastMultiplicateur[3]['heureTirage'].substring(11),
                            }
                        ];
                        break;
                    case 5:

                        arrayDernierMultiplicateur = [
                            {
                                temp: arrayLastMultiplicateur[0]['multiplicateur'],
                                time: arrayLastMultiplicateur[0]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[1]['multiplicateur'],
                                time: arrayLastMultiplicateur[1]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[2]['multiplicateur'],
                                time: arrayLastMultiplicateur[2]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[3]['multiplicateur'],
                                time: arrayLastMultiplicateur[3]['heureTirage'].substring(11),
                            },
                             {
                                temp: arrayLastMultiplicateur[4]['multiplicateur'],
                                time: arrayLastMultiplicateur[4]['heureTirage'].substring(11),
                            }
                        ];
                        break;
                    case 6:
                        arrayDernierMultiplicateur = [
                            {
                                temp: arrayLastMultiplicateur[0]['multiplicateur'],
                                time: arrayLastMultiplicateur[0]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[1]['multiplicateur'],
                                time: arrayLastMultiplicateur[1]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[2]['multiplicateur'],
                                time: arrayLastMultiplicateur[2]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[3]['multiplicateur'],
                                time: arrayLastMultiplicateur[3]['heureTirage'].substring(11),
                            },
                             {
                                temp: arrayLastMultiplicateur[4]['multiplicateur'],
                                time: arrayLastMultiplicateur[4]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[5]['multiplicateur'],
                                time: arrayLastMultiplicateur[5]['heureTirage'].substring(11),
                            }

                        ];
                        break;
                    case 7:
                        arrayDernierMultiplicateur = [
                            {
                                temp: arrayLastMultiplicateur[0]['multiplicateur'],
                                time: arrayLastMultiplicateur[0]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[1]['multiplicateur'],
                                time: arrayLastMultiplicateur[1]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[2]['multiplicateur'],
                                time: arrayLastMultiplicateur[2]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[3]['multiplicateur'],
                                time: arrayLastMultiplicateur[3]['heureTirage'].substring(11),
                            },
                             {
                                temp: arrayLastMultiplicateur[4]['multiplicateur'],
                                time: arrayLastMultiplicateur[4]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[5]['multiplicateur'],
                                time: arrayLastMultiplicateur[5]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[6]['multiplicateur'],
                                time: arrayLastMultiplicateur[6]['heureTirage'].substring(11),
                            }

                        ];
                        break;
                    case 8:
                        arrayDernierMultiplicateur = [
                            {
                                temp: arrayLastMultiplicateur[0]['multiplicateur'],
                                time: arrayLastMultiplicateur[0]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[1]['multiplicateur'],
                                time: arrayLastMultiplicateur[1]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[2]['multiplicateur'],
                                time: arrayLastMultiplicateur[2]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[3]['multiplicateur'],
                                time: arrayLastMultiplicateur[3]['heureTirage'].substring(11),
                            },
                             {
                                temp: arrayLastMultiplicateur[4]['multiplicateur'],
                                time: arrayLastMultiplicateur[4]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[5]['multiplicateur'],
                                time: arrayLastMultiplicateur[5]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[6]['multiplicateur'],
                                time: arrayLastMultiplicateur[6]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[7]['multiplicateur'],
                                time: arrayLastMultiplicateur[7]['heureTirage'].substring(11),
                            }

                        ];
                        break;
                    case 9:
                        arrayDernierMultiplicateur = [
                            {
                                temp: arrayLastMultiplicateur[0]['multiplicateur'],
                                time: arrayLastMultiplicateur[0]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[1]['multiplicateur'],
                                time: arrayLastMultiplicateur[1]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[2]['multiplicateur'],
                                time: arrayLastMultiplicateur[2]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[3]['multiplicateur'],
                                time: arrayLastMultiplicateur[3]['heureTirage'].substring(11),
                            },
                             {
                                temp: arrayLastMultiplicateur[4]['multiplicateur'],
                                time: arrayLastMultiplicateur[4]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[5]['multiplicateur'],
                                time: arrayLastMultiplicateur[5]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[6]['multiplicateur'],
                                time: arrayLastMultiplicateur[6]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[7]['multiplicateur'],
                                time: arrayLastMultiplicateur[7]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[8]['multiplicateur'],
                                time: arrayLastMultiplicateur[8]['heureTirage'].substring(11),
                            }

                        ];
                        break;
                    case 10:
                        arrayDernierMultiplicateur = [
                            {
                                temp: arrayLastMultiplicateur[0]['multiplicateur'],
                                time: arrayLastMultiplicateur[0]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[1]['multiplicateur'],
                                time: arrayLastMultiplicateur[1]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[2]['multiplicateur'],
                                time: arrayLastMultiplicateur[2]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[3]['multiplicateur'],
                                time: arrayLastMultiplicateur[3]['heureTirage'].substring(11),
                            },
                             {
                                temp: arrayLastMultiplicateur[4]['multiplicateur'],
                                time: arrayLastMultiplicateur[4]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[5]['multiplicateur'],
                                time: arrayLastMultiplicateur[5]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[6]['multiplicateur'],
                                time: arrayLastMultiplicateur[6]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[7]['multiplicateur'],
                                time: arrayLastMultiplicateur[7]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[8]['multiplicateur'],
                                time: arrayLastMultiplicateur[8]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[9]['multiplicateur'],
                                time: arrayLastMultiplicateur[9]['heureTirage'].substring(11),
                            }

                        ];
                        break;
                    case 11:
                        arrayDernierMultiplicateur = [
                            {
                                temp: arrayLastMultiplicateur[0]['multiplicateur'],
                                time: arrayLastMultiplicateur[0]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[1]['multiplicateur'],
                                time: arrayLastMultiplicateur[1]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[2]['multiplicateur'],
                                time: arrayLastMultiplicateur[2]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[3]['multiplicateur'],
                                time: arrayLastMultiplicateur[3]['heureTirage'].substring(11),
                            },
                             {
                                temp: arrayLastMultiplicateur[4]['multiplicateur'],
                                time: arrayLastMultiplicateur[4]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[5]['multiplicateur'],
                                time: arrayLastMultiplicateur[5]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[6]['multiplicateur'],
                                time: arrayLastMultiplicateur[6]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[7]['multiplicateur'],
                                time: arrayLastMultiplicateur[7]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[8]['multiplicateur'],
                                time: arrayLastMultiplicateur[8]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[9]['multiplicateur'],
                                time: arrayLastMultiplicateur[9]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[10]['multiplicateur'],
                                time: arrayLastMultiplicateur[10]['heureTirage'].substring(11),
                            }

                        ];
                        break;
                    case 12:
                        arrayDernierMultiplicateur = [
                            {
                                temp: arrayLastMultiplicateur[0]['multiplicateur'],
                                time: arrayLastMultiplicateur[0]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[1]['multiplicateur'],
                                time: arrayLastMultiplicateur[1]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[2]['multiplicateur'],
                                time: arrayLastMultiplicateur[2]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[3]['multiplicateur'],
                                time: arrayLastMultiplicateur[3]['heureTirage'].substring(11),
                            },
                             {
                                temp: arrayLastMultiplicateur[4]['multiplicateur'],
                                time: arrayLastMultiplicateur[4]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[5]['multiplicateur'],
                                time: arrayLastMultiplicateur[5]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[6]['multiplicateur'],
                                time: arrayLastMultiplicateur[6]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[7]['multiplicateur'],
                                time: arrayLastMultiplicateur[7]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[8]['multiplicateur'],
                                time: arrayLastMultiplicateur[8]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[9]['multiplicateur'],
                                time: arrayLastMultiplicateur[9]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[10]['multiplicateur'],
                                time: arrayLastMultiplicateur[10]['heureTirage'].substring(11),
                            },
                            {
                                temp: arrayLastMultiplicateur[11]['multiplicateur'],
                                time: arrayLastMultiplicateur[11]['heureTirage'].substring(11),
                            }
                        ];
                        break;
                    default:
                        break;
              }

            });
          }
        });
    }
    retrieveMultiplicateur();
		
	//recuperation du temps du jeu en cours
	async function userAction() {
	//	var url = 'http://localhost:9090/api/v1/supergames/timekeno/'+coderace;
	
			setInterval(async function(){
				if(urlServeur != undefined){
	               var url = urlServeur+'/counter/'+coderace;
	                const response = await fetch(url);
	                const myJson = await response.json(); //extract JSON from the http response
	                  // do something with myJson
	            //   console.log('myJson '+myJson.context);
	                timekeno = myJson
	            }
	            else{
	              timekeno = "-:-";
	            }
			},800);

			 
	}
//	userAction();

    async function sumKeno() {
	//	var url = 'http://localhost:9090/api/v1/supergames/timekeno/'+coderace;
	
			let intervalSumKeno = setInterval(async function(){
				if(urlServeur != undefined){
					console.log('this url: '+urlServeur);
	               var url = urlServeur+'/sumOdd/'+coderace;
	                const response = await fetch(url);
	                const myJson = await response.json(); //extract JSON from the http response
	                  // do something with myJson
	               console.log('myJson '+myJson.context);
	                
	            }
	            else{
	    	 		 clearInterval(intervalSumKeno);
	    	 	}
	
			},800);

			 
	}
//	sumKeno()

 	$(function(){
 		setInterval(function(){

 			$.ajax({
 				url:"managetime",
 				type:"GET",
 				async: false,
 				data:{
 	                'coderace':coderace
 	            },
 				success:function(result){
 					 $.each(result, function(index, value){
						 
 					timekeno = value.timekeno;
 					//console.log('validation timekeno '+timekeno);
 			          });
 				}
 			});
 		},333);
 	});

	//recuperation du bonus
	async function miseAJbonus(){
		//var url = 'http://localhost:9090/api/v1/supergames/bonuskeno/'+coderace;
		let miseajourInterval = setInterval(async function(){
			if(urlServeur != undefined){
				var url = urlServeur+'/bonuskeno/'+coderace;
		//	console.log('majbonus var url '+url);
				const response = await fetch(url);
		        const responseBonus = await response.json(); //extract JSON from the http response
		        
		        //update fields with new value
		        $("#divide-jackpot").text(responseBonus); 
		        $("#divide4-jackpot").text(responseBonus);
		        $("#divide2-jackpot").text(responseBonus);
		        $("#divide3-jackpot").text(responseBonus);
		        $("#divide-jackpot3").text(responseBonus);
			}
			
			
		},1000);
	}
	miseAJbonus();

	//recupération de l'objet keno en cours - game state
    async function buscarState(){
   
	 //   var url = 'http://localhost:9090/api/v1/supergames/drawcombi/'+coderace;
	    var url = urlServeur+'/drawcombi/'+coderace;
	    var draw = null;

	    const response1 = await fetch(url);
	    const myResp = await response1.json(); //extract JSON from the http response
	    numero_tirage = myResp['drawnumK'];
	    str_combi = myResp['str_draw_combi'];
        gamestate = myResp['gameState'];
        console.log("gameState "+gamestate);
    }
 //   buscarState();

	function updateDrawNum(){ //mise à jour des champs drawnum. page 2 et 3

		$.ajax({
					url:"updatebonus",
					type:"GET",
					data:{
						'partner':coderace
					},
					success:function(result){

						 // page2
						$("#drawnumb").empty();
						
						// page3
						$("#drawnumb3").empty();
					
	 		            // Pour chaque r�sultat du tableau
	 		            $.each(result, function(index, value){
	 		             
	 		
	 		               
	 		             numero_tirage = value.drawnumk;
	    				 str_combi = value.combi;
                         gamestate = value.gamestate;

                      //   if(gamestate > 2){
                       //  	  numero_tirage =  numero_tirage - 1;
                      //   }

	 		               $("#drawnumb").prepend(numero_tirage);
	 		               $("#drawnumb3").prepend(numero_tirage);
	 		              
	 		               var token = value.token;

	 		            });
					}
				});
	}

	function updateDrawNum2(){ //mise à jour des champs drawnum. page 2 et 3

		$.ajax({
					url:"updatebonus",
					type:"GET",
					data:{
						'partner':coderace
					},
					success:function(result){

						 // page2
						$("#drawnumb").empty();

						// page3
						$("#drawnumb3").empty();
						
	 		            // Pour chaque r�sultat du tableau
	 		            $.each(result, function(index, value){
	 		             
	 		                var id = value.idkeno;
	 		                blackout_draw = value.drawnumbk;
	 		                numero_tirage = value.drawnumk;
	 		                
	 		                numero_tirage =  numero_tirage - 1;

	 		               $("#drawnumb").prepend( numero_tirage);
	 		               $("#drawnumb3").prepend( numero_tirage);
	 		              
	 		               var token = value.token;

	 		            });
					}
				});
	}

		$(function refreshPage(){
			//console.log('validation');
			var itoken = localStorage.getItem("token");
			
			setInterval(function(){

				$.ajax({
					url:"updatebonus",
					type:"GET",
					data:{
						'partner':coderace
					},
					success:function(result){
						// page1
					//    $("#divide-jackpot").empty();
					//    $("#divide-jackpot3").empty();
						 // page4
					//	$("#divide4-jackpot").empty();
						 
						 // page2
					//	$("#drawnumb").empty();
					//	$("#divide2-jackpot").empty();
						
						// page3
					//	$("#drawnumb3").empty();
					//	$("#divide3-jackpot").empty();
					
	 		            // Pour chaque r�sultat du tableau
	 		            $.each(result, function(index, value){
	 		             
	 		                // on retrouve les param�tres qu'on avait fix� via l'API Json dans la servlet
	 		                var id = value.idkeno;
	 		             //   var bonus = value.bonuskamount;
	 		             //   blackout_draw = value.drawnumbk;

	 		  //           numero_tirage = value.drawnumk;
	    				 str_combi = value.combi;
                         gamestate = value.gamestate;

	 		            //   $("#drawnumb").prepend(numero_tirage);
	 		            //   $("#drawnumb3").prepend(numero_tirage);

	 		            //    tirage = value.drawnumk;
	 		            //    console.log("str_combi tirage: "+str_combi+" gamestate: "+gamestate);
	 		             
	 		   
	 		            //   $("#errors").prepend(value.status);

	 		               var token = value.token;

						if(itoken === undefined){
							localStorage.setItem("token", token);
						}
						else if(typeof(Storage) != undefined){
							if(itoken != token){
								console.log("Token != "+itoken);
								location.reload(true);
								localStorage.setItem("token", token);
							}	
						}
	 		            });
					}
				});
				//testjson();
			},1000);
		});
	</script>
    <script src="ressources/rsrc_caissier/bootstrap-3.3.6/dist/js/bootstrap.min.js"></script> 
    <script src="ressources/lottery/js/vendor/move.min.js"></script>
    <script src="ressources/lottery/js/vendor/zepto.js"></script>
    <script src="ressources/lottery/js/vendor/gsap.js"></script>
    <script src="ressources/lottery/js/page1.js"></script>
    <script src="ressources/lottery/js/page2.js"></script>
    <script src="ressources/lottery/js/page3.js"></script>
    <script src="ressources/lottery/js/page4.js"></script>
    <script src="ressources/lottery/js/scheduler.js"></script>
    
    <script type="text/javascript">
	</script>
</body>

</html>