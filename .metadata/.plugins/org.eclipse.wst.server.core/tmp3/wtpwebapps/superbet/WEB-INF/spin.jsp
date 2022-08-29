<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Spin 2 win</title>
    <link rel="stylesheet" href="ressources/spin/css/style.css">
    <link rel="stylesheet" href="ressources/spin/css/normalize.css">
    <link rel="stylesheet" type="text/css" href="ressources/rsrc_caissier/bootstrap-3.3.6/dist/css/bootstrap.min.css">
</head>
<body>
   <span id="partner">${partner}</span>
	<div class="containers">
	   <!--  <button type="button" id="launch_modal" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myBonus">
        	Launch Bonus
        </button>-->
        <!-- modal for bonus -->
        <div class="modal face" id="myBonus" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
         	<div class="modal-dialog" role="document">
         		<div class="modal-content">
         			<div class="modal-header">
         			 <button type="button" class="close" data-dismiss="modal" aria-label="Close">
         			 	<span aria-hidden="true">?</span>
         			 </button>
         			 <h4 class="modal-title" id="myModalLabel">JACKPOT</h4>
         			</div>
         			<div class="modal-body">
         				<span class="_pbonus">Code Ticket</span><br/><span id="codep_bonus" class="pbonus"></span> <br/><span id="amountp_bonus" class="mbonus"></span><br/>
         				     <span id="coderacep_bonus" class="pbonus">SUPERBET Bravooooo!!!</span>
         			</div>
         
         		</div>
         	</div>
         </div>
        <!-- <div class="overlay_win"></div> -->
        <div class="bloc_1">
           <!-- <img src="ressources/spin/assets/logo.jpeg" class="logo" alt="logo">-->
             <img src="ressources/spin/assets/logoo.png" class="logo" alt="logo">
             <img src="ressources/spin/assets/logo.png" class="logo_spin" alt="logo_spin">
            <div class="jackpot_box">
                <div class="jackpot">
                    <span class="jackpotLabel">JACKPOT</span>
                    <span class="jackpotValue" id="jkpt"></span>
                    <img src="ressources/spin/assets/img1wheel.png" alt="">
                </div>
                <div class="mega_jackpot">
                    <span class="jackpotLabel">MEGA JACKPOT</span>
                    <span class="jackpotValue" id="m_jkpt"></span>
                    <img src="ressources/spin/assets/img2wheel.png" alt="">
                </div>
            </div>
        </div>


        <div class="bloc_2">
            <img class="active" src="ressources/spin/assets/TETEDELECTURE.png" />
            <img class="wheel" src="ressources/spin/assets/roue.png" />
            <div id="numberOutput"></div>

            <div class="tirage_box">
                <span>TIRAGE: </span>
                <span id="drawnum"></span>
            </div>
        </div>

        <div class="bloc_3">
            <div class="time_container">
                <div class="time_box">
                </div>
                <div id="time_box_left">
                </div>
                <div class="time_left">
                    <span id="minute_secnd"></span>
                </div>
            </div>
            <div class="gain_stats">
                <div class="overlay"></div>
                <div class="gain">
                    <div class="bloc_3_title title_main title_main_gain">GAIN</div>
                    <div class="item1">NUMERO</div>
                    <div style="border-top:1.5px solid #828282;" class="item2">X36</div>
                    <div class="item1 items_colors">COULEURS</div>
                    <div>
                    	<div class="item2" style="width:100%;background:green">X36</div>
                        <div class="item2" style="width:100%;background:red">X2</div>
                        <div class="item2" style="width:100%;background:black">X2</div>
                    </div>
                    <div class="item1">MIRROR</div>
                    <div class="item2">X18</div>
                    <div class="item1">TWINS</div>
                    <div class="item2">X12</div>
                    <div class="item1">FINALS</div>
                    <div class="item2">X9</div>
                    <div class="item1">LOW/HIGH & COLORS</div>
                    <div class="item2">X4</div>
                    <div class="item1">DOUZAINES</div>
                    <div class="item2">X3</div>
                    <div class="item1">PAIR/IMPAIR</div>
                    <div class="item2">X2</div>
                    <div class="item1">LOW/HIGH</div>
                    <div class="item2">X2</div>
                </div>

                <div class="stats_box">
                    <div class="bloc_3_title title_main title_main_stats">STATISTIQUES</div>
                    <div class="stat_box_slide">
                        <div class="stat_1 slide" style="margin-top: 5px">
                            <span class="bloc_3_title_sub">NOMBRES (120 derniers tirages) </span>
                            <div class="stats">
                                <div class="stats_item">
                                    <div class="stats_item_color colorRed">1</div>
                                    <div class="stats_item_value"></div>
                                    <div class="stats_item_color colorBlack">2</div>
                                    <div class="stats_item_value"></div>
                                    <div class="stats_item_color colorRed">3</div>
                                    <div class="stats_item_value"></div>
                                    <div class="stats_item_color colorBlack">4</div>
                                    <div class="stats_item_value"></div>
                                    <div class="stats_item_color colorRed"></div>
                                    <div class="stats_item_value"></div>
                                    <div class="stats_item_color colorBlack">6</div>
                                    <div class="stats_item_value"></div>
                                    <div class="stats_item_color colorRed">7</div>
                                    <div class="stats_item_value"></div>
                                    <div class="stats_item_color colorBlack">8</div>
                                    <div class="stats_item_value"></div>
                                    <div class="stats_item_color colorRed">9</div>
                                    <div class="stats_item_value"></div>
                                    <div class="stats_item_color colorBlack">10</div>
                                    <div class="stats_item_value"></div>
                                    <div class="stats_item_color colorBlack">11</div>
                                    <div class="stats_item_value"></div>
                                    <div class="stats_item_color colorRed" style="  border-bottom:  1.5px solid #828282;">12</div>
                                    <div class="stats_item_value" style="  border-bottom:  1.5px solid #828282;"></div>
                                </div>

                                <div class="stats_item">
                                    <div class="stats_item_color colorBlack">13</div>
                                    <div class="stats_item_value"></div>
                                    <div class="stats_item_color colorRed">14</div>
                                    <div class="stats_item_value"></div>
                                    <div class="stats_item_color colorBlack">15</div>
                                    <div class="stats_item_value"></div>
                                    <div class="stats_item_color colorRed">16</div>
                                    <div class="stats_item_value"></div>
                                    <div class="stats_item_color colorBlack">17</div>
                                    <div class="stats_item_value"></div>
                                    <div class="stats_item_color colorRed">18</div>
                                    <div class="stats_item_value"></div>
                                    <div class="stats_item_color colorRed">19</div>
                                    <div class="stats_item_value"></div>
                                    <div class="stats_item_color colorBlack">20</div>
                                    <div class="stats_item_value"></div>
                                    <div class="stats_item_color colorRed">21</div>
                                    <div class="stats_item_value"></div>
                                    <div class="stats_item_color colorBlack">22</div>
                                    <div class="stats_item_value"></div>
                                    <div class="stats_item_color colorRed">23</div>
                                    <div class="stats_item_value"></div>
                                    <div class="stats_item_color colorBlack" style="  border-bottom:  1.5px solid #828282;">24</div>
                                    <div class="stats_item_value" style="  border-bottom:  1.5px solid #828282;"></div>
                                </div>

                                <div class="stats_item">
                                    <div class="stats_item_color colorRed">25</div>
                                    <div class="stats_item_value"></div>
                                    <div class="stats_item_color colorBlack">26</div>
                                    <div class="stats_item_value"></div>
                                    <div class="stats_item_color colorRed">27</div>
                                    <div class="stats_item_value"></div>
                                    <div class="stats_item_color colorBlack">28</div>
                                    <div class="stats_item_value"></div>
                                    <div class="stats_item_color colorBlack">29</div>
                                    <div class="stats_item_value"></div>
                                    <div class="stats_item_color colorRed">30</div>
                                    <div class="stats_item_value"></div>
                                    <div class="stats_item_color colorBlack">31</div>
                                    <div class="stats_item_value"></div>
                                    <div class="stats_item_color colorRed">32</div>
                                    <div class="stats_item_value"></div>
                                    <div class="stats_item_color colorBlack">33</div>
                                    <div class="stats_item_value"></div>
                                    <div class="stats_item_color colorRed">34</div>
                                    <div class="stats_item_value"></div>
                                    <div class="stats_item_color colorBlack">35</div>
                                    <div class="stats_item_value"></div>
                                    <div class="stats_item_color colorRed" style="  border-bottom:  1.5px solid #828282;">36</div>
                                    <div class="stats_item_value" style="  border-bottom:  1.5px solid #828282;">3</div>
                                </div>
                            </div>
                            <div style="height:6%;color:white;display:flex;flex-direction: row; justify-content: center;width:100%;border:  1.5px solid #828282">
                                <div class="stats_item_color" style="height:100%;background:green; width: 50%;text-align: center">0</div>
                                <div class="stats_item_value" style="height:100%;background: #404143; width: 50%;text-align: center ">1</div>
                            </div>
                        </div>

                        <div class="stat_2 slide">
                            <span class="stat_2_label">COLORS</span>
                            <div class="stat_2_value_1_container">
                                <div class="stats_color_1"></div>
                                <div class="stats_color_2"></div>
                                <div class="stats_color_3"></div>
                            </div>

                            <span class="stat_2_label">DOUZAINE</span>
                            <div class="stat_2_value_1_container">
                                <div class="stat_2_value_1_container_label">1-12</div>
                                <div class="stat_2_value_1_container_value">3</div>
                                <div class="stat_2_value_1_container_label">13-24</div>
                                <div class="stat_2_value_1_container_value">3</div>
                                <div class="stat_2_value_1_container_label">25-36</div>
                                <div class="stat_2_value_1_container_value">3</div>
                            </div>

                            <span class="stat_2_label">PAIR/IMPAIR</span>
                            <div class="stat_2_value_1_container">
                                <div class="stat_2_value_1_container_label">PAIR</div>
                                <div class="stat_2_value_1_container_value"></div>
                                <div class="stat_2_value_1_container_label">IMPAIR</div>
                                <div class="stat_2_value_1_container_value"></div>
                            </div>

                            <span class="stat_2_label">LOW/HIGH</span>
                            <div class="stat_2_value_1_container">
                                <div class="stat_2_value_1_container_label">LOW</div>
                                <div class="stat_2_value_1_container_value"></div>
                                <div class="stat_2_value_1_container_label">HIGH</div>
                                <div class="stat_2_value_1_container_value"></div>
                            </div>

                            <span class="stat_2_label">FINALS</span>
                            <div class="stat_2_value_1_container_final">
                                <div class="stat_2_value_1_container_label_container">
                                    <div class="stat_2_value_1_container_label">0</div>
                                    <div class="stat_2_value_1_container_label">1</div>
                                    <div class="stat_2_value_1_container_label">2</div>
                                    <div class="stat_2_value_1_container_label">3</div>
                                    <div class="stat_2_value_1_container_label">4</div>
                                    <div class="stat_2_value_1_container_label">5</div>
                                    <div class="stat_2_value_1_container_label">6</div>
                                </div>
                                <div class="stat_2_value_1_container_value_container">
                                    <div class="stat_2_value_1_container_value"></div>
                                    <div class="stat_2_value_1_container_value"></div>
                                    <div class="stat_2_value_1_container_value"></div>
                                    <div class="stat_2_value_1_container_value"></div>
                                    <div class="stat_2_value_1_container_value"></div>
                                    <div class="stat_2_value_1_container_value"></div>
                                    <div class="stat_2_value_1_container_value"></div>
                                </div>
                            </div>
                        </div>

                        <div class="stat_3 slide">
                            <!-- mirror -->
                            <div class="mirror_box">
                                <span class="label_stats">MIRROR</span>
                                <div class="mirror_grid">
                                    <div>12.21</div>
                                    <div class="stat_mirror_value"></div>
                                    <div>13.31</div>
                                    <div class="stat_mirror_value"></div>
                                    <div>23.32</div>
                                    <div class="stat_mirror_value"></div>
                                </div>
                            </div>
                            <!-- end mirror -->

                            <!-- sectors -->
                            <div class="sector_box">
                                <span class="label_stats">SECTEURS</span>
                                <div class="sectors_grid">
                                    <div>A</div>
                                    <div>B</div>
                                    <div>C</div>
                                    <div>D</div>
                                    <div>E</div>
                                    <div>F</div>
                                    <div class="stat_sector_value"></div>
                                    <div class="stat_sector_value"></div>
                                    <div class="stat_sector_value"></div>
                                    <div class="stat_sector_value"></div>
                                    <div class="stat_sector_value"></div>
                                    <div class="stat_sector_value"></div>
                                </div>
                            </div>
                            <!-- end sectors -->

                            <!-- twin -->
                            <div class="twins_box">
                                <span class="label_stats">TWINS</span>
                                <div class="twins_grid">
                                    <div>11,22,33</div>
                                    <div class="stat_twin_value"></div>
                                </div>
                            </div>
                            <!-- end twin -->


                            <!-- hot / cold -->
                            <div class="hotCold_box">
                                <span class="label_stats">HOT/COLD NUMBERS</span>
                                <div class="hot_cold_grid">
                                    <div>CHAUD</div>
                                    <div class="colorRed">12</div>
                                    <div class="colorBlack">23</div>
                                    <div class="colorRed">17</div>
                                    <div class="colorBlack">1</div>
                                    <div class="colorRed">2</div>
                                    <div class="colorRed">8</div>
                                    <div>FROID</div>
                                    <div class="colorRed">11</div>
                                    <div class="colorRed">4</div>
                                    <div class="colorBlack">7</div>
                                    <div class="colorBlack">0</div>
                                    <div class="colorBlack">7</div>
                                    <div class="colorGreen">0</div>
                                </div>
                            </div>
                            <!-- end hot / cold -->

                            <!-- last resultat -->
                            <div class="last_result_box">
                                <span class="label_stats">DERNIER RESULTATS</span>
                                <div class="last_result_grid">
                                    <div class="last_result_num_value"></div>
                                    <div class="last_result_value colorRed"></div>
                                    <div class="last_result_num_value"></div>
                                    <div class="last_result_value colorRed"></div>
                                    <div class="last_result_num_value"></div>
                                    <div class="last_result_value colorRed"></div>
                                    <div class="last_result_num_value"></div>
                                    <div class="last_result_value colorBlack"></div>
                                    <div class="last_result_num_value"></div>
                                    <div class="last_result_value colorBlack"></div>
                                    <div class="last_result_num_value"></div>
                                    <div class="last_result_value colorBlack"></div>
                                </div>
                            </div>
                            <!-- end last resultat -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
         
       </div>


		<script src="ressources/spin/js/jquery.js"></script>
		<script src="ressources/spin/js/main.js"></script>
        <script type="text/javascript">
         //declaration des variables
         let coderace = $('#partner').text();
         let trouve_draw = false;
 		 let tirage = 0;
 		 let blackout_draw;
 		 let numero_tirage = 0;
 		 let draw_6 = [];
	 	 let draw_5 = [];
		 let draw_4 = [];
		 let draw_3 = [];
		 let draw_2 = [];
		 let draw_1 = [];
		 
		 
		// $('#launch_modal').click(function(){
	    //      $('.bloc_2').css('display','none');
	    //   });
		
		function recordDisplay(){
	            $.ajax({
	                url:"recordisplayp",
	                type:"GET",
	                async: false,
	                 data:{
	                        'coderace':coderace
	                    },
	                success:function(result){
	                    $.each(result, function(index, value){
	                        timespin = value.timespin;
	                        //console.log('Timespin TIEMTJS: '+timespin);
	                    });
	                }
	              });
	        }
		    
 		//recupération du temps en cours
	        $(function(){
				setInterval(function(){
	
					$.ajax({
						url:"managetimep",
						type:"GET",
						async: false,
						data:{
	                        'coderace':coderace
	                    },
						success:function(result){
							 $.each(result, function(index, value){
								 
							timespin = value.timespin;
							//console.log('timespin spinjsp: '+timespin);
		 		          });
						}
					});
				},1000);
			});
 		
	        
 		 //restoration des données au lancement
 		 function restoreDatas(){
			$.ajax({
				url:"updatebonusp",
				type:"POST",
				async: false,
				data:{
					'partner':coderace
				},
				success:function(result){
					$.each(result, function(index, value){
						numero_tirage = value.drawnump;
					/*	
						//var chknbre0 = value.chknbre0;
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
						$(".stat_2_value_1_container_value").eq(0).empty();
						$(".stat_2_value_1_container_value").eq(0).prepend(value['chkdouz1']);
						
						$(".stat_2_value_1_container_value").eq(1).empty();
						$(".stat_2_value_1_container_value").eq(1).prepend(value['chkdouz2']);
						
						$(".stat_2_value_1_container_value").eq(2).empty();
						$(".stat_2_value_1_container_value").eq(2).prepend(value['chkdouz3']);
						
						//finals
						$(".stat_2_value_1_container_value").eq(7).empty();
						$(".stat_2_value_1_container_value").eq(7).prepend(value['chkfinals0']);
						
						$(".stat_2_value_1_container_value").eq(8).empty();
						$(".stat_2_value_1_container_value").eq(8).prepend(value['chkfinals1']);
						
						$(".stat_2_value_1_container_value").eq(9).empty();
						$(".stat_2_value_1_container_value").eq(9).prepend(value['chkfinals2']);
						
						$(".stat_2_value_1_container_value").eq(10).empty();
						$(".stat_2_value_1_container_value").eq(10).prepend(value['chkfinals3']);
						
						$(".stat_2_value_1_container_value").eq(11).empty();
						$(".stat_2_value_1_container_value").eq(11).prepend(value['chkfinals4']);
						
						$(".stat_2_value_1_container_value").eq(12).empty();
						$(".stat_2_value_1_container_value").eq(12).prepend(value['chkfinals5']);
						
						$(".stat_2_value_1_container_value").eq(13).empty();
						$(".stat_2_value_1_container_value").eq(13).prepend(value['chkfinals6']);
						
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
						}*/
						
					});
				}
			});
			
		}
		restoreDatas();
		recordDisplay();
         //mise a jour du jackpot
         $(function miseAJbonus(){
			setInterval(function(){

				$.ajax({
					url:"updatebonusp",
					type:"GET",
					data:{
						'partner':coderace
					},
					success:function(result){
						
					    $("#jkpt").empty();
						$("#m_jkpt").empty()
						$("#drawnum").empty();
						
	 		            // Pour chaque résultat du tableau
	 		            $.each(result, function(index, value){
	 		             
	 		                // on retrouve les paramètres qu'on avait fixé via l'API Json dans la servlet
	 		                var id = value.idspin;
	 		                var bonus = value.bonuspamount;
	 		                blackout_draw = value.drawnumbp;
	 		                tirage = value.drawnump;
	 		              //  console.log("numero tirage: "+tirage);
	 		             
	 		               // On mets à jour les differents champs
	 		               $("#jkpt").prepend(bonus+' CFA'); 
	 		               $("#m_jkpt").prepend(bonus+' CFA');
	 		               $("#drawnum").prepend(tirage);
	 		            });
					}
				});
			},1000);
		});
         
        </script>
        <script src="ressources/rsrc_caissier/bootstrap-3.3.6/dist/js/bootstrap.min.js"></script>
        <script src="ressources/spin/js/gsap.js"></script>
        <script src="ressources/spin/js/inertiaPlugin.js"></script>
        <script src="ressources/spin/js/segment.js"></script>
        <script src="ressources/spin/js/JQueryRotate.min.js"></script>
        <script src="ressources/spin/js/TweenMax.min.js"></script>
        <script src="ressources/spin/js/timetoleft.js"></script>
        <script src="ressources/spin/green.js"></script>
        
</body>
</html>