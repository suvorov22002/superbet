<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html lang="fr">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Cashier-GUI</title>
    <link rel="stylesheet" type="text/css" href="ressources/rsrc_caissier/bootstrap-3.3.6/dist/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="ressources/rsrc_caissier/assets/css/styles1.css">
    <link rel="stylesheet" href="ressources/rsrc_caissier/assets/css/navigation-dark.css">
    <link rel="stylesheet" href="ressources/rsrc_caissier/assets/slicknav/slicknav.min.css">
    <style type="text/css" media="print">
    	<!-- 
    		 
    		#img_coupon{
    			background-image: url("./ressources/rsrc_caissier/assets/images/logo.jpeg");
    			background-repeat: no-repeat;
    			background-attachment: fixed;
    			background-position: left;
    		/*	background-size: 200px 50px;
    			max-width: inherit;
   				width: inherit;*/
    		}
    		
    		body *{
    			visibility:hidden;
    			display:none;
    		}
    		
    		.btnclose, .close{
    			display:none;
    		}
    		
    		#printSection, #printSection *{
    			visibility: visible;
    			display:block !important;
    		}
    		
    		#printSection{
    			/*position: absolute;*/
    			left:0;
    			top:0;
    			/*z-index:1;*/
    			width:300px;
    			height:auto;
    			overflow: auto;
    		}
    		
    		#idbc,#idpath{
    			visibility:hidden;
    			display:none;
    		}
    		
    		td,
			tr,
			table {
			    border-top: 1px solid black;
			    border-collapse: collapse;
			    width: 280px;
			    max-width: 280px;
			}
    	-->
    </style>
  </head>
  <body>
	
    <div class="container-fluid">
     <span id="idpartner">${caissier.partner}</span>
     <span id="idcaissier">${caissier.idCaissier}</span>
      <div class="row">
        <div class="col-sm-12 menus" id="menu">
          <nav class="menu-navigation-dark">
            <!--<a href="javascript:void(0);" id="kikoo_0" class="${state==0 ? 'selected' :''}"><i class="fa fa-camera-retro"></i><span>Soccer</span></a> -->
            <a href="javascript:void(0);" id="kikoo_1" class="${state==1 ? 'selected' :''}"><i class="fa fa-code"></i><span>KENO</span></a>
            <a href="javascript:void(0);" id="kikoo_2" class="${state==2 ? 'selected' :''}"><i class="fa fa-code"></i><span>DOGS RACE</span></a>
            <a href="javascript:void(0);" id="kikoo_3" class="${state==3 ? 'selected' :''}"><i class="fa fa-code"></i><span>BINGO</span></a>
            <a href="javascript:void(0);" id="kikoo_6" class="${state==6 ? 'selected' :''}"><i class="fa fa-heart"></i><span>SPIN 2 WIN</span></a>
            <a href="javascript:void(0);" id="kikoo_4" class="${state==4 ? 'selected' :''}"><i class="fa fa-heart"></i><span>WITHDRAWAL</span></a>
            <a href="javascript:void(0);" id="kikoo_5" class="${state==5 ? 'selected' :''}"><i class="fa fa-heart"></i><span>SHIFT</span></a>
          </nav>
        </div>
      </div>
	
      <!-- Contenu interface pari -->
     <div class="interf_pari" style="display:${state==1 ? 'block' :'none'};">
      <form action="manageKeno" method="POST" name="form_man_keno">
       <div class="row">
        <div class="col-xs-12">
           <table class="user">
                <tr>
                    <td class="col-xs-2"><label>USER:</label>&nbsp;&nbsp;<span id="login_pari" class="login">${caissier.loginc}</span></td>
                    <td class="col-xs-2"><label>Airtime:</label>&nbsp;&nbsp;<span id="balance1" class="balance"></span>&nbsp;&nbsp;<span>FCFA</span></td>
                    <td class="col-xs-4"><label>Heure:</label>&nbsp;&nbsp;<span id="date_heure"></span></td>
                    <!-- <td class="col-xs-4"><label>Statut:</label>&nbsp;&nbsp;<span id="" class=""></span></td>-->
                </tr>
            </table>
        </div>

        <!--<div class="col-xs-3">Account</div>
        <div class="col-xs-3">Hour</div>-->
      </div>
      <div class="row menu1">
        <div class="col-sm-9 imenu1">

          <fieldset>
              <legend>Pronostique</legend>
            <fieldset>
              <legend>Choix pari</legend>
                  <table class="pari">
                    <tr>
                        <td class="col-xs-2"><label for="pari">Pari</label></td>
                        
                        <td class="col-xs-6"><input type="text" id="pari" name="pari" disabled title="le type de pari" size="40" maxlength="32"/></td>
                        <td class="col-xs-4"><span class="pari_r"></span></td>
                    </tr>
                    <tr>
                        <td class="col-xs-2"><label for="pari"></label></td>
                        <td class="col-xs-6"></td>
                        <td class="col-xs-4"><span class="pr_pari_r" id="pari_r"></span></td>
                    </tr>
                    <tr>
                      <td class="col-xs-2"><label for="code">Selection</label><br/></td>
                      
                      <td class="col-xs-6"><input type="text" id="code" name="code" title="votre selection" value="" onkeypress="return verif(event);" size="40" maxlength="32" autocomplete="off"/>
                      </td>
                      <td class="col-xs-4"><span class="requis" id="icode" style="color:red;font-weight:bold;font-style:italic;">${form.erreurs.code}</span></td>
                    </tr>
                    <tr>
                        <td class="col-xs-2"><label for="pari"></label></td>
                        <td class="col-xs-6"></td>
                        <td class="col-xs-4"><span class="requis" id="messagek"></span></td>
                    </tr>
                    <tr>
                      <td class="col-xs-2"><button type="button" class="btn btn-info btn-block" id="clear" onclick="">
                         Annuler/Nouveau</button></td>
                        <td class="col-xs-6">
                          <button type="button" class="btn btn-info" onclick="return simple();">Jeu Simple</button>
                          <button type="button" class="btn btn-danger" onclick="return sortant();">Non Sortants</button>
                          <button type="button" class="btn btn-primary" onclick="return dedans();">Tout Dedans</button>
                        </td>
                        <td class="col-xs-4">
                          <input type="text" id="alea_nbre" name="alea_nbre" title="nombre de balle" size="3" maxlength="3" onkeypress="return aleaNbre(event);" autocomplete="off"/>&nbsp;&nbsp;
                          
						  <button type="button" class="btn btn-success" onclick="return place_alea(event);">Tirage Aleatoire</button>
                        </td>
                        
                    </tr>
                </table>
                
            </fieldset>
           <!-- <div class="col-sm-9">-->
                  
              <!--  </div>-->
            <table class="table table-fixed t_pari">
              <thead>
              <tr>
                <th class="col-xs-3">Numéro tirage</th>
                <th class="col-xs-3">Pronostic</th>
                <th class="col-xs-3">Type de pari</th>
                <th class="col-xs-3">Supprimer</th>
              </tr>
             </thead>
              <tbody id='prono'>
                      <!-- les differents parents -->
              </tbody>
            </table>
           </fieldset>
        </div>
        <div class="col-sm-3 coupon">
            <fieldset>
              <legend>Coupon</legend>
                <!--  <div class="control-group">-->
                  <label class="control-label input-label" for="montant">Apport(Montant Mise)</label><br/>
                  <div class="controls bootstrap-timepicker">

                  <input class="form-control" type="text" placeholder="200 FCFA" class="amount" id="montant" name="montant" onkeypress="return verif_amount(event);" maxlength="5"
                   autocomplete="off">
                  
                  <span id="amount_error" style="color:red;font-style:italic;">${k_form.resultat}</span><br/>
                  <label class="control-label input-label" for="montant" id="">Apport Minimum</label>&nbsp;<span>200 FCFA</span>
                  <input class="form-control" type="text" placeholder="200 FCFA" readonly><br/>
                  <label class="control-label input-label" for="code">Gain Eventuel</label>&nbsp;<span>2 000 FCFA</span><br/>
                  <input class="form-control" type="text" placeholder="20 000 FCFA" readonly><br/>
                  <label class="control-label input-label" for="code">Bonus</label>&nbsp;<span></span><br/>
                  <input class="form-control input-success" type="text" placeholder="20 000 FCFA" readonly style="text-align: right;"><br/>
                  <label class="control-label input-label" for="code">xX Multiplicateur Xx</label>&nbsp;<br/>
                  <label class="radio-inline">
                    <input type="radio" value="Non" name="multi1" checked>NON
                  </label>
                  <label class="radio-inline">
                    <input type="radio" value="Oui" name="multi1">OUI
                  </label><br/><br/>
               <!--   <button type="submit" id="print" class="btn btn-primary btn-block canprint" ${caissier.statut == 'N' ? 'disabled' : ''}>Imprimer</button> -->
                  <button type="button" id="print" class="btn btn-primary btn-block canprint" ${caissier.statut == 'N' ? 'disabled' : ''} onclick="return manage_keno(event)">Imprimer</button>
                  <button type="button" class="btn btn-warning btn-block">Annuler</button>
                  
                 
                  
                 </div>  
              <br />
            </fieldset>
            
         <!-- Modal -->
             
                  <div class="modal fade" tabindex="-1" aria-hidden="true" id="imprimer" role="dialog">
				  <!--<div class="modal fade" tabindex="-1" aria-hidden="true" id="${k_form.imprimer == 'imprimer' ? 'imprimer':'myModale'}" role="dialog">-->
                    <div class="modal-dialog modal-sm">
                    
                      <!-- Modal content-->
                      <div class="modal-content modal-printer">
                      
                      <div id="printThis">
                        <div  class="modal-header" style="width:inherit;font-size:16px;">
                         <!-- <button type="button" id="kfermer" class="close" data-dismiss="modal">&times;</button> width:100%;max-width:100%;-->
                           <div id="img_coupon" style="margin: 10px;">
                             <img src="" width="250" height="50"  id='myImage0' alt="logo"></div>
                         <!--   <img src="ressources/rsrc_caissier/assets/images/barcode/code.png" width="267" height="50" alt="${coupon.barcode}"> <br/>-->
                         
                         <span id="idbc" style="margin-left:100px; display:table;">${coupon.barcode}</span>
                         <span id="idpath">${path}</span>
                         <!--<div style="margin-left:1px;width:100%;padding-top:5px;" id="bcTarget">${coupon.barcode}</div>-->
                        </div>
                        <div class="modal-body" style="text-align:center;width:inherit;margin:0px;">
                        	<table class="ticket_keno" id="coupon_keno" style="width:280px;margin:3px;padding-left:5px;border-top:1px solid black;border-bottom:1px solid black;">
				               <tbody id="ticket_keno_body">
							   <!-- 
								<tr>
				                    <td style="text-align:left;" colspan="2">
				                     <c:choose>
				                          <c:when test="${k_form.multiplicite > 1}">
				                          	[1/<c:out value="${k_form.multiplicite}"/>]&nbsp;multiple
				                          </c:when>
				                          <c:otherwise>
				                            [1/1]&nbsp;simple
				                          </c:otherwise>
				                      </c:choose>
				                    </td>
				                    <td style="text-align:center;"><span>Sel Cote</span></td>
				                </tr>
				               
				                <tr>
				                    <td  style="text-align:left;">Coupon Keno</td>
				                    <td  style="text-align:center;">${coupon.codepari}</td>
				                	<td  style="text-align:center;">${coupon.eventscote}</td>
				                </tr>
				                <c:forEach var="i" begin="1" end="${k_form.multiplicite}">
					                <tr style="text-align:left;">
					                	
                                       <td  style="text-align:left;" colspan="3">${k_form._fecha[i-1]}</td>
					                </tr>
					                <tr style="text-align:center;">
					                    <td  style="text-align:left;">${coupon.nbEvents+i-1}</td>
					                	<td style="font-weight:bold;" colspan="2" >${coupon.events}</td>
					                </tr>
				                </c:forEach>
				                <tr >
				                    <td style="text-align:center;" colspan="3" >-----------------------------</td>
				                    
				                </tr>
				                <tr >
				                    <td  style="text-align:left;">Code Bonus:</td><td style="font-weight:bold;" colspan="2" >${coupon.nbreCombi}</td>
				                </tr>
				                <tr >
				                    <td  style="text-align:left;">Total mise:</td><td style="font-weight:bold;"colspan="2" >${coupon.mtMise}</td>
				                </tr>
				                <tr >
				                    <td  style="text-align:left;">Gain Maximum:</td>
				                    <td style="font-weight:bold;" colspan="2" >${coupon.gainMax}</td>
				                </tr>
				                <tr >
				                    <td style="text-align:left;">Gain Minimum:</td>
				                    <td style="font-weight:bold;" colspan="2" >${coupon.gainMin}</td>
				                </tr>
				                <tr >
				                    <td  style="text-align:left;">Combinaison:</td>
				                    <td  style="font-weight:bold;" colspan="2" >${k_form.multiplicite}</td>
				                </tr>
				                <tr >
				                    <td  style="text-align:left;">Boutique:</td>
				                    <td style="font-weight:bold;" colspan="2" >${coupon.room}</td>
				                </tr>
				                <tr >
				                    <td style="text-align:left;" >Caissier:</td>
				                    <td style="font-weight:bold;" colspan="2" class="login">${caissier.loginc}</td>
				                </tr>
                        <tr >
                            <td style="text-align:left;" >Multiplicateur:</td>
                            <td style="font-weight:bold;" colspan="2" class="login">${coupon.multiplicateur}</td>
                        </tr>
				                <tr >
				                    <td style="text-align:center;word-break: break-all;" colspan="3" >
				                    Ce ticket est valable 07 jours, à compter du jour du dernier evènement. <span style="font-weight:bold;text-transform:capitalize;" id="idpartner0">${coupon.room}</span> vous remercie pour votre fidelité.
				                    </td>
				                    
				                </tr>
								
								-->
								</tbody>
				            </table>
              
                        </div>
                        <div style="text-align:center;align-content:center;width:250;margin:5px 15px 2px;" id="bcTarget"></div>
                       </div>
                        <div class="modal-footer">
                          <button type="button" autofocus class="btn btn-default btnclose"  data-dismiss="modal">Close</button>
                          <button type="button"  id="btnprint" class="btn btn-primary">Imprimer</button>
                        </div>
                     <!-- </div>-->
                      
                    </div>
                  </div>
        	 
        </div>
        
        </div>
      </div>
      <div class="row">
        <div class="col-sm-12 offers">
          <fieldset>
              <legend>Offres disponibles</legend>
                <table class="table table-fixed">
                    <thead>
                      <tr>
                        <th class="col-xs-4">Offre</th>
                        <th class="col-xs-4">Description</th>
                        <th class="col-xs-4">Raccourci</th>
                      </tr>
                   </thead>
                    <tbody>
                      <tr>
                      <td class="col-xs-4">Keno Simple</td>
                      <td class="col-xs-4">Choix simple entre 1 et 10</td>
                      <td class="col-xs-4">32.11</td>
                    </tr>
                      <tr>
                      <td class="col-xs-4">Non sortant</td>
                      <td class="col-xs-4">Les numéros n'apparaissent pas lors du tirage</td>
                      <td class="col-xs-4">-</td>
                    </tr>
                    <tr>
                      <td class="col-xs-4">Tout dedans</td>
                      <td class="col-xs-4">Tous les numéros apparaissent lors du tirage</td>
                      <td class="col-xs-4">+</td>
                    </tr>
                    <tr>
                      <td class="col-xs-4">Couleur</td>
                      <td class="col-xs-4">Couleur de la premiere boule</td>
                      <td class="col-xs-4">*</td>
                    </tr>
                    <tr>
                      <td class="col-xs-4">Couleur</td>
                      <td class="col-xs-4">Couleur derniere boule</td>
                      <td class="col-xs-4">/</td>
                    </tr>
                    <tr>
                      <td class="col-xs-4">Somme 5-</td>
                      <td class="col-xs-4">Somme des 5 premiers plus petit que 202.5</td>
                      <td class="col-xs-4">5-</td>
                    </tr>
                    <tr>
                      <td class="col-xs-4">Somme 5+</td>
                      <td class="col-xs-4">Somme des 5 premiers plus grand que 202.5</td>
                      <td class="col-xs-4">5+</td>
                    </tr>
                    <tr>
                      <td class="col-xs-4">Somme 20-</td>
                      <td class="col-xs-4">Somme totale inferieure � 805.5</td>
                      <td class="col-xs-4">20-</td>
                    </tr>
                    <tr>
                      <td class="col-xs-4">Somme 20+</td>
                      <td class="col-xs-4">Somme totale superieure � 805.5</td>
                      <td class="col-xs-4">20+</td>
                    </tr>
                    <tr>
                      <td class="col-xs-4">Premier 1-</td>
                      <td class="col-xs-4">Premier num�ro inf�rieur � 40.5</td>
                      <td class="col-xs-4">1-</td>
                    </tr>
                    <tr>
                      <td class="col-xs-4">Premier 1+</td>
                      <td class="col-xs-4">Premier num�ro sup�rieur � 40.5</td>
                      <td class="col-xs-4">1+</td>
                    </tr>
                    <tr>
                      <td class="col-xs-4">Premier odd</td>
                      <td class="col-xs-4">Premier num�ro pair</td>
                      <td class="col-xs-4">188</td>
                    </tr>
                    <tr>
                      <td class="col-xs-4">Dernier odd</td>
                      <td class="col-xs-4">Dernier num�ro pair</td>
                      <td class="col-xs-4">189</td>
                    </tr>
                    <tr>
                      <td class="col-xs-4">Premier even</td>
                      <td class="col-xs-4">Premier numéro impair</td>
                      <td class="col-xs-4">198</td>
                    </tr>
                    <tr>
                      <td class="col-xs-4">Dernier even</td>
                      <td class="col-xs-4">Dernier numéro impair</td>
                      <td class="col-xs-4">199</td>
                    </tr>
                    </tbody>
                  </table>
            </fieldset>
        </div>
       <!-- <div class="col-sm-3 logo">Logo</div>-->
      </div>
    </form>
    <!-- fin contenu pari -->
    </div>

    <!-- Contenu interface paiement -->
    <div class="interf_paiement" style="display:${state==4 ? 'block' :'none'};">
     <form action="versement" method="POST" name="form_ver">
       <div class="row">
        <div class="col-sm-2 code_barre">
           <h3>CODE BARE</h3>
           <span id="coupon_error" class="coupon_error" style="color:${v_form.resultat == 'Ticket perdant' ? 'red':'green'};font-style:plain;font-weight:bold;">${v_form.resultat}</span><br/>
        </div>
        <div class="col-sm-10 check_ticket">
           <!--<table class="table table-fixed  table-paid">-->
           <table class="table table-fixed t_pay">
              <thead>
                <tr>
                  <th class="col-md-1">Evenement</th>
                <!--   <th class="col-md-1">Cote</th>-->
                  <th class="col-md-1">Cote</th>
                  <th class="col-md-2">Jeu</th>
                  <th class="col-md-3">Selection</th>
                  <th class="col-md-5">Prix</th>
                  <!--  <th class="col-md-1">Resultat</th>-->
                </tr>
             </thead>
              <tbody class='t_fixed_hedears' id="res_tirage">
               <c:forEach var="i" begin="1" end="${v_form.multiplicite}">
	             <!--   <tr class="${(v_form.evenements[i-1])['etat'] == 'true' ? 'succes':'erreur'}">-->
	              <tr>
	                <td class="${(v_form.evenements[i-1])['etat'] == 'true' ? 'succes':'erreur'} 
	                	<c:choose>
                          <c:when test="${(v_form.evenements[i-1])['etat'] == 'true'}">
                          	<c:out value="succes"/>
                          </c:when>
                          <c:when test="${(v_form.evenements[i-1])['etat'] == 'false'}">
                          	<c:out value="erreur"/>
                          </c:when>
                          <c:otherwise>
                            <c:out value="pending"/>
                          </c:otherwise>
                        </c:choose>
	                
	                
	                
	                col-md-1">${i-1+v_form.drawData['draw_num']}<br/></td>
	              <!--   <td class="col-md-1">${(v_form.evenements[i-1])['etat']}<br/></td>-->
	                <td class="col-md-1">${(v_form.evenements[i-1])['cote']}<br/></td>
	                <td class="col-md-2">${v_form.drawData['cparil']}<br/></td>
	                <td class="col-md-3">${v_form.drawData['player_choice']}<br/></td>
	                <td class="col-md-5">${(v_form.evenements[i-1])['resultTour']}<br/></td>
	               <!-- <td class="col-md-1">
	                  <div class="form-check checked" >
	                    <input type="checkbox" checked disabled class="form-check-input checked" id="">
	                  </div>
	                </td>-->
	              </tr>
               </c:forEach>
              </tbody>
            </table>
        </div>

        <!--<div class="col-xs-3">Account</div>
        <div class="col-xs-3">Hour</div>-->
      </div>
      <div class="row">
        <div class="col-sm-12 paiement" onfocus="return checkCoupon();">
		
           <table class="paiement_coupon">
              <tr>
                  <td class="col-xs-1"><label for="prix">Total Prix</label></td>
                  <td class="col-xs-1"><input type="text" id="prix" size="20" maxlength="60" disabled value="${v_form.drawData['prix_total']}" autocomplete="off"/></td>

                  <td class="col-xs-1"><label for="mise">Total Mise</label></td>
                  <td class="col-xs-1"><input type="text" id="mise" size="20" maxlength="60" disabled value="${v_form.drawData['montant']}" autocomplete="off"/></td>

                  <td class="col-xs-1"><label for="Versement">Versement</label></td>
                  <td class="col-xs-1"><input type="text" readonly id="versement" name="versement" size="20" maxlength="60" value="${v_form.drawData['gain_total']}" autocomplete="off"/></td>
                  
                  <td class="col-xs-1"><label for=""></label></td>
                  <td class="col-xs-5"><label for=""></label></td>
              </tr>
           
              <tr>
                 <td class="col-xs-1"><label for="prix">Code Barre</label></td>
                 <td class="col-xs-1"><input type="text" size="20" style="text-align:center;" maxlength="15" id="barcode" name="barcode" onkeypress="return verif_coupon(event);" oninput="return check_barcode(event);" autocomplete="off"/></td>

                 <td class="col-xs-1"><label for="mise">Code Paiement</label></td>
                 <td class="col-xs-1"><input type="text" id="paiement" size="20" maxlength="60" value="" disabled autocomplete="off"/></td>

                 <td class="col-xs-1"><button type="submit" id="btn_barcode" onclick="verifCoupon()" disabled class="btn btn-primary btn-block">Valider</button></td>
                 <td class="col-xs-1"><button type="button" id="effVers" class="btn btn-primary btn-block btnVers" onclick="return do_effVers();">Effectuer Versement</button></td>

                 <td class="col-xs-1"><button type="button" id="void_pay" class="btn btn-primary btn-block" onclick="clear_form()">Annuler</button></td>
                 <td class="col-xs-5"></td>

              </tr>
              
           </table>
         
        </div>
      </div>
      <div class="row">
        <div class="col-sm-7 historiq">
          <table class="table table-fixed" >
              <thead style="background-color: powerblue;">
                <tr>
                  <th class="col-xs-2">N°</th>
                  <th class="col-xs-3">Code Barre</th>
                  <th class="col-xs-3">Jeu</th>
                  <th class="col-xs-2">Libelle</th>
                  <th class="col-xs-2">Etat</th>
                </tr>
             </thead>
              <tbody>
                
              </tbody>
            </table>
        </div>
        <div class="col-sm-5 detail">
          DETAILS
        </div>
      </div>
      </form>
    </div>
    <!-- fin interface paiement -->
     <!-- contenu interface soccer -->
    <div class="interf_soccer">
      
       <div class="row">
        <div class="col-xs-12">
           <table class="user">
                <tr>
                    <td class="col-xs-2"><label>USER:</label>&nbsp;&nbsp;<span class="login">${caissier.loginc}</span></td>
                    <td class="col-xs-2"><label>Airtime:</label>&nbsp;&nbsp;<span id="balance2" class="balance"></span>&nbsp;&nbsp;<span>FCFA</span></td>
                    <td class="col-xs-4"><label>Heure:</label>&nbsp;&nbsp;<span id="date_soccer"></span></td>
                    <!--<td class="col-xs-4"><label>Statut:</label>&nbsp;&nbsp;<span class="pari_state ${caissier.statut == 'C'? 'succes':'erreur'}">${caissier.statut == 'C' ? 'Connect�' : 'Non Connect�'}</span></td>-->
                </tr>
            </table>
        </div>
        <!--<div class="col-xs-3">Account</div>
        <div class="col-xs-3">Hour</div>-->
      </div>
       <div class="row">
        <div class="col-sm-4">
           <h3>SOCCER</h3>
           <!-- Button trigger modal -->
            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModalCenter">
              Launch demo modal
            </button>

            <!-- Modal -->
            <div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
              <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                  <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLongTitle">Modal title</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                      <span aria-hidden="true">&times;</span>
                    </button>
                  </div>
                  <div class="modal-body">
                    ...
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary">Save changes</button>
                  </div>
                </div>
              </div>
            </div>
        </div>
        <div class="col-sm-8">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
  <span aria-hidden="true">&times;</span>
</button>
        </div>

        <!--<div class="col-xs-3">Account</div>
        <div class="col-xs-3">Hour</div>-->
      </div>
      <div class="row">
        <div class="col-sm-12">

          
        </div>
      </div>
      <div class="row">
        <div class="col-sm-7 historiq">
          <h2>Modal Example</h2>
            <!-- Trigger the modal with a button -->
            <button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#myModal">Open Modal</button>

            <!-- Modal -->
            <div class="modal fade" id="myModal" role="dialog">
              <div class="modal-dialog">
              
                <!-- Modal content-->
                <div class="modal-content">
                  <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Modal Header</h4>
                  </div>
                  <div class="modal-body">
                    <p>Some text in the modal.</p>
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                  </div>
                </div>
                
              </div>
            </div>
        </div>
        <div class="col-sm-5 detail">
          DETAILS
          <!-- Button trigger modal -->
              <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModalLong">
                Launch demo modal
              </button>

              <!-- Modal -->
              <div class="modal fade" id="exampleModalLong" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true">
                <div class="modal-dialog" role="document">
                  <div class="modal-content">
                    <div class="modal-header">
                      <h5 class="modal-title" id="exampleModalLongTitle">Modal title</h5>
                      <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                      </button>
                    </div>
                    <div class="modal-body">
                      ...
                    </div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                      <button type="button" class="btn btn-primary">Save changes</button>
                    </div>
                  </div>
                </div>
              </div>
        </div>
      </div>
     
    </div>
    <!-- fin interface soccer -->

     <!-- contenu interface bingo -->
    <div class="interf_bingo">
     
       <div class="row">
        <div class="col-xs-12">
           <table class="user">
                <tr>
                    <td class="col-xs-2"><label>USER:</label>&nbsp;&nbsp;<span class="login">${caissier.loginc}</span></td>
                    <td class="col-xs-2"><label>Airtime:</label>&nbsp;&nbsp;<span id="balance3" class="balance"></span>&nbsp;&nbsp;<span>FCFA</span></td>
                    <td class="col-xs-4"><label>Heure:</label>&nbsp;&nbsp;<span id="date_bingo"></span></td>
                    <!--<td class="col-xs-4"><label>Statut:</label>&nbsp;&nbsp;<span class="pari_state ${caissier.statut == 'C'? 'succes':'erreur'}">${caissier.statut == 'C' ? 'Connect�' : 'Non Connect�'}</span></td>-->
                </tr>
            </table>
        </div>
        <!--<div class="col-xs-3">Account</div>
        <div class="col-xs-3">Hour</div>-->
      </div>
       <div class="row">
        <div class="col-sm-4">
           <h3>BINGO</h3>
        </div>
        <div class="col-sm-8">
           
        </div>

        <!--<div class="col-xs-3">Account</div>
        <div class="col-xs-3">Hour</div>-->
      </div>
      <div class="row">
        <div class="col-sm-12">

          
        </div>
      </div>
      <div class="row">
        <div class="col-sm-7">
          
        </div>
        <div class="col-sm-5 detail">
          DETAILS
        </div>
      </div>
     
    </div>
    <!-- fin interface bingo -->

     <!-- contenu interface dog -->
    <div class="interf_dog">
     
       <div class="row">
        <div class="col-xs-12">
           <table class="user">
                <tr>
                    <td class="col-xs-2"><label>USER:</label>&nbsp;&nbsp;<span class="login">${caissier.loginc}</span></td>
                    <td class="col-xs-2"><label>Airtime:</label>&nbsp;&nbsp;<span id="balance4" class="balance"></span>&nbsp;&nbsp;<span>FCFA</span></td>
                    <td class="col-xs-4"><label>Heure:</label>&nbsp;&nbsp;<span id="date_dog"></span></td>
                    <!--<td class="col-xs-4"><label>Statut:</label>&nbsp;&nbsp;<span class="pari_state ${caissier.statut == 'C'? 'succes':'erreur'}">${caissier.statut == 'C' ? 'Connect�' : 'Non Connect�'}</span></td>-->
                </tr>
            </table>
        </div>
        <!--<div class="col-xs-3">Account</div>
        <div class="col-xs-3">Hour</div>-->
      </div>
      <div class="row menu1">
        <div class="col-sm-9 imenu1">

          <fieldset>
              <legend>Pronostique</legend>
            <fieldset>
              <legend>Choix pari</legend>
                  <table class="pari">
                    <tr>
                        <td class="col-xs-2"><label for="paris">Pari</label></td>
                        
                        <td class="col-xs-6"><input type="text" id="dpari" name="paris" disabled title="le type de pari" size="40" maxlength="32"/></td>
                        <td class="col-xs-4"><span class="requis">*</span></td>
                    </tr>
                    <tr>
                        <td class="col-xs-2"><label for="pari"></label></td>
                        <td class="col-xs-6"></td>
                        <td class="col-xs-4"><span class="requis"></span></td>
                    </tr>
                    <tr>
                      <td class="col-xs-2"><label for="code">Selection</label><br/></td>
                      
                      <td class="col-xs-6"><input type="text" id="dcode" name="dcode" title="votre selection" onkeypress="return verif(event);" size="40" maxlength="32" autocomplete="on"/>
                      </td>
                      <td class="col-xs-4"><span class="requis" id="dicode" style="color:red;font-weight:bold;font-style:italic;"></span></td>
                    </tr>
                    <tr>
                        <td class="col-xs-2"><label for="pari"></label></td>
                        <td class="col-xs-6"></td>
                        <td class="col-xs-4"><span class="requis"></span></td>
                    </tr>
                    <tr>
                        <td class="col-xs-2"><button type="button" class="btn btn-info btn-block" disabled>Annuler</button></td>
                        <td class="col-xs-6"><button type="button" class="btn btn-info btn-block" id="clear" onclick="clear();">
                         Annuler/Nouveau</button></td>
                        <td class="col-xs-4"><button type="button" class="btn btn-info btn-block" disabled>Annuler</button></td>
                    </tr>
                </table>
            </fieldset>
            <table class="table table-fixed t_pari">
              <thead>
              <tr>
                <th class="col-xs-3">Numéro tirage</th>
                <th class="col-xs-3">Pronostic</th>
                <th class="col-xs-3">Type de pari</th>
                <th class="col-xs-3">Supprimer</th>
              </tr>
             </thead>
              <tbody id='prono'>
                      <!-- les differents parents -->
              </tbody>
            </table>
           </fieldset>
        </div>
        <div class="col-sm-3">
            <fieldset>
              <legend>Coupon</legend>
                <!--  <div class="control-group">-->
                  <label class="control-label input-label" for="montant">Apport(Montant Mise)</label><br/>
                  <div class="controls bootstrap-timepicker">

                  <input class="form-control" type="text" placeholder="200 FCFA" class="amount" id="bmontant" name="montant" onkeypress="return verif_amount(event);" maxlength="5">
                  
                  <span id="amount_error" style="color:red;font-style:italic;"></span><br/>
                  <label class="control-label input-label" for="montant" id="">Apport Minimum</label>&nbsp;<span>200 FCFA</span>
                  <input class="form-control" type="text" placeholder="200 FCFA" readonly><br/>
                  <label class="control-label input-label" for="code">Apport Maximum</label>&nbsp;<br/>
                  <input class="form-control" type="text" placeholder="20 000 FCFA" readonly><br/>
                  <label class="control-label input-label" for="code">Gain Eventuel</label>&nbsp;<span>2 000 FCFA</span><br/>
                  <input class="form-control" type="text" placeholder="20 000 FCFA" readonly><br/>
                  <label class="control-label input-label" for="code">Bonus</label>&nbsp;<span>60</span><br/>
                  <input class="form-control input-success" type="text" placeholder="20 000 FCFA" readonly style="text-align: right;"><br/>

                  <button type="submit" class="btn btn-primary btn-block">Imprimer</button>
                  <button type="button" class="btn btn-warning btn-block">Annuler</button>

                  <!-- Modal -->
                  <div class="modal fade" id="myModal" role="dialog">
                    <div class="modal-dialog modal-sm">
                    
                      <!-- Modal content-->
                      <div class="modal-content">
                        <div class="modal-header">
                          <button type="button" class="close" data-dismiss="modal">&times;</button>
                          <img src="ressources/rsrc_caissier/assets/images/logo.jpeg" alt="logo">
                          <h4 class="modal-title" style="text-align: center">Super 1 BET</h4>
                        </div>
                        <div class="modal-body">
                          <p>Coupon Keno</p>
                        </div>
                        <div class="modal-footer">
                          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                          <button type="button" class="btn btn-primary">Imprimer</button>
                        </div>
                      </div>
                      
                    </div>
                  </div>
                 </div>  
              <br />
            </fieldset>
        </div>
      </div>
        
      <div class="row">
        <div class="col-sm-12 offers">
          <fieldset>
              <legend>Offres disponibles</legend>
                <table class="table table-fixed">
                    <thead>
                      <tr>
                        <th class="col-xs-4">Offre</th>
                        <th class="col-xs-4">Description</th>
                        <th class="col-xs-4">Raccourci</th>
                      </tr>
                   </thead>
                    <tbody>
                      <tr>
                      <td class="col-xs-4">Keno Simple</td>
                      <td class="col-xs-4">Choix simple entre 1 et 10</td>
                      <td class="col-xs-4">32.11</td>
                    </tr>
                      <tr>
                      <td class="col-xs-4">Non sortant</td>
                      <td class="col-xs-4">Les numéros n'apparaissent pas lors du tirage</td>
                      <td class="col-xs-4">-</td>
                    </tr>
                    <tr>
                      <td class="col-xs-4">Tout dedans</td>
                      <td class="col-xs-4">Tous les numéros apparaissent lors du tirage</td>
                      <td class="col-xs-4">+</td>
                    </tr>
                    <tr>
                      <td class="col-xs-4">Couleur</td>
                      <td class="col-xs-4">Couleur de la premiere boule</td>
                      <td class="col-xs-4">*</td>
                    </tr>
                    <tr>
                      <td class="col-xs-4">Couleur</td>
                      <td class="col-xs-4">Couleur derniere boule</td>
                      <td class="col-xs-4">/</td>
                    </tr>
                    <tr>
                      <td class="col-xs-4">Somme 5-</td>
                      <td class="col-xs-4">Somme des 5 premiers plus petit que 202.5</td>
                      <td class="col-xs-4">5-</td>
                    </tr>
                    <tr>
                      <td class="col-xs-4">Somme 5+</td>
                      <td class="col-xs-4">Somme des 5 premiers plus grand que 202.5</td>
                      <td class="col-xs-4">5+</td>
                    </tr>
                    <tr>
                      <td class="col-xs-4">Somme 20-</td>
                      <td class="col-xs-4">Somme totale inferieure � 805.5</td>
                      <td class="col-xs-4">20-</td>
                    </tr>
                    <tr>
                      <td class="col-xs-4">Somme 20+</td>
                      <td class="col-xs-4">Somme totale superieure > 805.5</td>
                      <td class="col-xs-4">20+</td>
                    </tr>
                    <tr>
                      <td class="col-xs-4">Premier 1-</td>
                      <td class="col-xs-4">Premier numéro inférieur < 40.5</td>
                      <td class="col-xs-4">1-</td>
                    </tr>
                    <tr>
                      <td class="col-xs-4">Premier 1+</td>
                      <td class="col-xs-4">Premier numéro supérieur > 40.5</td>
                      <td class="col-xs-4">1+</td>
                    </tr>
                    <tr>
                      <td class="col-xs-4">Premier odd</td>
                      <td class="col-xs-4">Premier numéro pair</td>
                      <td class="col-xs-4">188</td>
                    </tr>
                    <tr>
                      <td class="col-xs-4">Dernier odd</td>
                      <td class="col-xs-4">Dernier numéro pair</td>
                      <td class="col-xs-4">189</td>
                    </tr>
                    <tr>
                      <td class="col-xs-4">Premier even</td>
                      <td class="col-xs-4">Premier numéro impair</td>
                      <td class="col-xs-4">198</td>
                    </tr>
                    <tr>
                      <td class="col-xs-4">Dernier even</td>
                      <td class="col-xs-4">Dernier numéro impair</td>
                      <td class="col-xs-4">199</td>
                    </tr>
                    </tbody>
                  </table>
            </fieldset>
        </div>
       <!-- <div class="col-sm-3 logo">Logo</div>-->
      </div>
    
   </div>
    <!-- fin interface dog -->

     <!-- contenu interface spin 2 win -->
    <div class="interf_spin" style="display:${state==6 ? 'block' :'none'};">
      <form action="manageSpin" method="POST">
       <div class="row">
        <div class="col-xs-12">
           <table class="user">
                <tr>
                    <td class="col-xs-2"><label>USER:</label>&nbsp;&nbsp;<span class="login">${caissier.loginc}</span></td>
                    <td class="col-xs-2"><label>Airtime:</label>&nbsp;&nbsp;<span id="balance5" class="balance"></span>&nbsp;&nbsp;<span>FCFA</span></td>
                    <td class="col-xs-4"><label>Heure:</label>&nbsp;&nbsp;<span id="date_spin"></span></td>
                    <!--<td class="col-xs-4"><label>Statut:</label>&nbsp;&nbsp;<span class="pari_state ${caissier.statut == 'C'? 'succes':'erreur'}">${caissier.statut == 'C' ? 'Connect�' : 'Non Connect�'}</span></td>-->
                </tr>
            </table>
        </div>
        <!--<div class="col-xs-3">Account</div>
        <div class="col-xs-3">Hour</div>-->
      </div>
      <div class="row menu1">
        <div class="col-sm-9 imenu1">

          <fieldset>
              <legend>Pronostique</legend>
            <fieldset>
              <legend>Choix pari</legend>
                  <table class="pari">
                    <tr>
                        <td class="col-xs-2"><label for="pari">Pari</label></td>
                        
                        <td class="col-xs-6"><input type="text" id="spari" name="pari" disabled title="le type de pari" size="40" maxlength="32"/></td>
                        <td class="col-xs-4"><span class="pari_p">*</span></td>
                    </tr>
                    <tr>
                        <td class="col-xs-2"><label for="pari"></label></td>
                        <td class="col-xs-6"></td>
                        <td class="col-xs-4"><span class="pr_pari_p"></span></td>
                    </tr>
                    <tr>
                      <td class="col-xs-2"><label for="code">Selection</label><br/></td>
                      
                      <td class="col-xs-6"><input type="text" id="spcode" name="spcode" title="votre selection" onkeypress="return verif_spin(event);" size="40" maxlength="32" autocomplete="on"/>
                      </td>
                      <td class="col-xs-4"><span class="requis" id="ispcode" style="color:red;font-weight:bold;font-style:italic;"></span></td>
                    </tr>
                    <tr>
                        <td class="col-xs-2"><label for="pari"></label></td>
                        <td class="col-xs-6"></td>
                        <td class="col-xs-4"><span class="requis"></span></td>
                    </tr>
                    <tr>
                        <td class="col-xs-2"><button type="button" class="btn btn-info btn-block" disabled>Annuler</button></td>
                        <td class="col-xs-6"><button type="button" class="btn btn-info btn-block" id="clear_sp" onclick="">
                         Annuler/Nouveau</button></td>
                        <td class="col-xs-4"><button type="button" class="btn btn-info btn-block" disabled>Annuler</button></td>
                    </tr>
                </table>
            </fieldset>
            <table class="table table-fixed sp_t_pari">
              <thead>
              <tr>
                <th class="col-xs-3">Numéro tirage</th>
                <th class="col-xs-3">Pronostic</th>
                <th class="col-xs-3">Type de pari</th>
                <th class="col-xs-3">Supprimer</th>
              </tr>
             </thead>
              <tbody id='sp_prono'>
                      <!-- les differents parents -->
              </tbody>
            </table>
           </fieldset>
        </div>
        <div class="col-sm-3">
            <fieldset>
              <legend>Coupon</legend>
                <!--  <div class="control-group">-->
                  <label class="control-label input-label" for="montant">Apport(Montant Mise)</label><br/>
                  <div class="controls bootstrap-timepicker">

                  <input class="form-control" type="text" placeholder="200 FCFA" class="amount" id="spmontant" name="spmontant" onkeypress="return verif_sp_amount(event);" maxlength="5">
                  
                  <span id="sp_amount_error" style="color:red;font-style:italic;"></span><br/>
                  <label class="control-label input-label" for="montant" id="">Apport Minimum</label>&nbsp;<span>200 FCFA</span>
                  <input class="form-control" type="text" placeholder="200 FCFA" readonly><br/>
                  <label class="control-label input-label" for="code">Apport Maximum</label>&nbsp;<br/>
                  <input class="form-control" type="text" placeholder="20 000 FCFA" readonly><br/>
                  <label class="control-label input-label" for="code">Gain Eventuel</label>&nbsp;<span>2 000 FCFA</span><br/>
                  <input class="form-control" type="text" placeholder="20 000 FCFA" readonly><br/>
                  <label class="control-label input-label" for="code">Bonus</label>&nbsp;<span>60</span><br/>
                  <input class="form-control input-success" type="text" placeholder="20 000 FCFA" readonly style="text-align: right;"><br/>

                  <button type="submit" id="sp_print" class="btn btn-primary btn-block canprint" ${caissier.statut == 'N' ? 'disabled' : ''}>Imprimer</button>
                  <button type="button" class="btn btn-warning btn-block">Annuler</button>

                  <!-- Modal -->
                  <div class="modal fade" tabindex="-1" aria-hidden="true" id="${p_form.imprimer == 'imprimer' ? 'p_imprimer':'p_myModale'}" role="dialog">
                    <div class="modal-dialog modal-sm">
                    
                      <!-- Modal content-->
                      <div class="modal-content modal-printer">
                      
                      <div id="p_printThis">
                        <div style="text-align:center" class="modal-header">
                          <!--<button type="button" id="pfermer" class="close" data-dismiss="modal">&times;</button>-->
                          <div id="img_p_coupon">
                          <img src="${path}/ressources/rsrc_caissier/assets/images/logo.jpeg" width="200" height="50" alt="logo"></div>
                         <!--   <img src="ressources/rsrc_caissier/assets/images/barcode/code.png" width="267" height="50" alt="${coupon.barcode}"> <br/>-->
                         <span id="p_idbc" style="margin-left:100px; display:table;">${pcoupon.barcode}</span>
                         
                         <!--<div style="margin-left:1px;width:100%;padding-top:5px;" id="bcTarget"></div>-->
                        </div>
                        <div class="modal-body" style="">
                        	<table class="ticket_keno" style="width:100%;max-width:100%;border:2px solid #ffffff;">
				                <tr >
				                    <td  style="text-align:left;">Coupon</td>
				                    <td  style="text-align:left;" colspan="2">Spin 2 win Deluxe</td>
				                </tr>
				               
				                <tr >
				                    <td  style="text-align:left;">${pcoupon.nbEvents}</td>
				                    <td  style="text-align:left;">${pcoupon.codepari}</td>
				                	<td  style="text-align:left;"><span style="font-weight:bold;">x${pcoupon.eventscote}</span></td>
				                </tr>				                
				                <tr>
				                    <td  style="text-align:left;">Imprimé le</td>
				                	<td  colspan="2">${pcoupon.horaYfecha}</td>
				                </tr>
				                <tr >
				                    <td style="text-align:center;" colspan="3" >-----------------------------</td>
				                    
				                </tr>
				                <tr >
				                    <td  >Code Bonus:</td>
				                    <td style="font-weight:bold;" colspan="2" >${pcoupon.nbreCombi}</td>
				                </tr>
				                <tr >
				                    <td  >Total mise:</td>
				                    <td style="font-weight:bold;"colspan="2" >${pcoupon.mtMise}</td>
				                </tr>
				                <tr >
				                    <td  >Gain Maximum:</td>
				                    <td style="font-weight:bold;" colspan="2" >${pcoupon.gainMax}</td>
				                </tr>
				                <tr >
				                    <td  >Boutique:</td>
				                    <td style="font-weight:bold;" colspan="2" >${pcoupon.room}</td>
				                </tr>
				                <tr >
				                    <td style="" >Caissier:</td>
				                    <td style="font-weight:bold;" colspan="2" class="login">${caissier.loginc}</td>
				                </tr>
				            </table>
                            
                        </div>
                        <div style="margin-left:1px;width:100%;padding-top:5px;" id="p_bcTarget"></div>
                       </div>
                        <div class="modal-footer">
                          <button type="button" class="btn btn-default pbtnclose"  data-dismiss="modal">Close</button>
                          <button type="button" onclick="print_ticket()" id="p_btnprint" class="btn btn-primary hidden-print">Imprimer</button>
                        </div>
                      </div>
                      
                    </div>
                  </div>
                  
                 </div>  
              <br />
            </fieldset>
        </div>
      </div>
        
      <div class="row">
        <div class="col-sm-12 offers">
          <fieldset>
              <legend>Offres disponibles</legend>
                <table class="table table-fixed">
                    <thead>
                      <tr>
                        <th class="col-xs-4">Offre</th>
                        <th class="col-xs-4">Description</th>
                        <th class="col-xs-4">Raccourci</th>
                      </tr>
                   </thead>
                    <tbody>
                      <tr>
                      <td class="col-xs-4">Num�ro Simple</td>
                      <td class="col-xs-4">Choix simple entre 1 et 36</td>
                      <td class="col-xs-4">32.11</td>
                    </tr>
                      <tr>
                      <td class="col-xs-4">Couleur verte</td>
                      <td class="col-xs-4">Le num�ro de couleur vert apparait</td>
                      <td class="col-xs-4">v / V</td>
                    </tr>
                    <tr>
                      <td class="col-xs-4">Couleur rouge</td>
                      <td class="col-xs-4">Le num�ro de couleur rouge</td>
                      <td class="col-xs-4">r / R</td>
                    </tr>
                    <tr>
                      <td class="col-xs-4">Couleur noir</td>
                      <td class="col-xs-4">Le num�ro de couleur noir</td>
                      <td class="col-xs-4">n / N</td>
                    </tr>
                    <tr>
                      <td class="col-xs-4">Couleur</td>
                      <td class="col-xs-4">Couleur derniere boule</td>
                      <td class="col-xs-4">/</td>
                    </tr>
                    <tr>
                      <td class="col-xs-4">Somme 5-</td>
                      <td class="col-xs-4">Somme des 5 premiers plus petit que 202.5</td>
                      <td class="col-xs-4">5-</td>
                    </tr>
                    <tr>
                      <td class="col-xs-4">Somme 5+</td>
                      <td class="col-xs-4">Somme des 5 premiers plus grand que 202.5</td>
                      <td class="col-xs-4">5+</td>
                    </tr>
                    <tr>
                      <td class="col-xs-4">Somme 20-</td>
                      <td class="col-xs-4">Somme totale inferieure � 805.5</td>
                      <td class="col-xs-4">20-</td>
                    </tr>
                    <tr>
                      <td class="col-xs-4">Somme 20+</td>
                      <td class="col-xs-4">Somme totale superieure � 805.5</td>
                      <td class="col-xs-4">20+</td>
                    </tr>
                    <tr>
                      <td class="col-xs-4">Premier 1-</td>
                      <td class="col-xs-4">Premier num�ro inf�rieur � 40.5</td>
                      <td class="col-xs-4">1-</td>
                    </tr>
                    <tr>
                      <td class="col-xs-4">Premier 1+</td>
                      <td class="col-xs-4">Premier num�ro sup�rieur � 40.5</td>
                      <td class="col-xs-4">1+</td>
                    </tr>
                    <tr>
                      <td class="col-xs-4">Premier odd</td>
                      <td class="col-xs-4">Premier num�ro pair</td>
                      <td class="col-xs-4">188</td>
                    </tr>
                    <tr>
                      <td class="col-xs-4">Dernier odd</td>
                      <td class="col-xs-4">Dernier num�ro pair</td>
                      <td class="col-xs-4">189</td>
                    </tr>
                    <tr>
                      <td class="col-xs-4">Premier even</td>
                      <td class="col-xs-4">Premier num�ro impair</td>
                      <td class="col-xs-4">198</td>
                    </tr>
                    <tr>
                      <td class="col-xs-4">Dernier even</td>
                      <td class="col-xs-4">Dernier numéro impair</td>
                      <td class="col-xs-4">199</td>
                    </tr>
                    </tbody>
                  </table>
            </fieldset>
        </div>
       <!-- <div class="col-sm-3 logo">Logo</div>-->
      </div>
   </form> 
   </div>
    <!-- fin interface spin -->

    <!-- contenu interface shift -->
    <div class="interf_shift" style="display:${state==5 ? 'block' :'none'};">
      <form action="endofday" method="POST" name="form_endday">
       <div class="row">
        <div class="col-xs-12">
           <table class="user">
                <tr>
                    <td class="col-xs-2"><label>USER:</label>&nbsp;&nbsp;<span class="login">${caissier.loginc}</span></td>
                    <td class="col-xs-2"><label>Airtime:</label>&nbsp;&nbsp;<span id="balance6" class="balance"></span>&nbsp;&nbsp;<span>FCFA</span></td>
                    <td class="col-xs-4"><label>Heure:</label>&nbsp;&nbsp;<span id="date_shift"></span></td>
                    <!--<td class="col-xs-4"><label>Statut:</label>&nbsp;&nbsp;<span class="pari_state ${caissier.statut == 'C'? 'succes':'erreur'}">${caissier.statut == 'C' ? 'Connect�' : 'Non Connect�'}</span></td>-->
                </tr>
            </table>
        </div>
        <!--<div class="col-xs-3">Account</div>
        <div class="col-xs-3">Hour</div>-->
      </div> 
       <div class="row">
          <div class="col-sm-3">
           <h3></h3>
             
        </div>
        <div class="col-sm-6">
            <img src="ressources/rsrc_caissier/assets/images/money.png" alt="money">
            <!--<label>Fermeture de caisse:</label>&nbsp;&nbsp;<button type="button" class="btn btn-danger" data-toggle="modal" data-target="#shiftModal">END SHIFT</button>-->
				<label>Fermeture de caisse:</label>&nbsp;&nbsp;<button type="submit" class="btn btn-danger endshift" ${caissier.statut == 'N' ? 'disabled' : ''}>END SHIFT</button>
             <!-- Modal -->
                  <div class="modal fade" id="${s_form.imprimer == 'imprimer' ? 'shiftModal':'myshiftModale'}" role="dialog">
                    <div class="modal-dialog modal-sm">
                    
                      <!-- Modal content-->
                      <div class="modal-content modal-printer" >
                       <div id="s_printThis">
                        <div class="modal-header">
                          <button type="button" class="close" data-dismiss="modal">&times;</button>
                          <h4 class="modal-title" style="text-align: center;">SHIFT</h4>
                        </div>
                        <div class="modal-body">
                        	<table class="user" style="width:100%;">
                        		<tr>
			                      <td class="col-xs-6"><label>Date:</label></td>
			                      <td class="col-xs-6">${s_form.shift['time']}</td>
			                    </tr>
			                    <tr>
			                      <td class="col-xs-6"><label>Caissier:</label></td>
			                      <td class="col-xs-6"><span id="scaissier">${s_form.shift['caissier']}</span></td>
			                    </tr>
			                    <tr>
			                      <td class="col-xs-6"><label>Heure début:</label></td>
			                      <td class="col-xs-6">${s_form.shift['start']}</td>
			                    </tr>
			                   <!-- <tr>
			                      <td class="col-xs-6"><label>Heure de calcul:</label></td>
			                      <td class="col-xs-6">${s_form.shift['time']}</td>
			                    </tr>-->
                          <tr>
			                      <td class="col-xs-6"><label>Fonds au debut:</label></td>
			                      <td class="col-xs-6"><span id="sbalance">${s_form.shift['balance0']}</span></td>
			                    </tr>
                          <tr>
			                      <td class="col-xs-6"><label>Fonds à la fin:</label></td>
			                      <td class="col-xs-6">${s_form.shift['reste']}</td>
			                    </tr>
			                    <tr>
			                      <td class="col-xs-6"><label>Tickets Total:</label></td>
			                      <td class="col-xs-6">${s_form.shift['tick_tot']}</td>
			                    </tr>
			                    <tr>
			                      <td class="col-xs-6"><label>Tickets de paye:</label></td>
			                      <td class="col-xs-6">${s_form.shift['paid_tot']}</td>
			                    </tr>
			                    <tr>
			                      <td class="col-xs-6"><label>Tickets Revoqués:</label></td>
			                      <td class="col-xs-6">${s_form.shift['revoq']}</td>
			                    </tr>
			                    <tr>
			                      <td class="col-xs-6"><label>Payin:</label></td>
			                      <td class="col-xs-6">${s_form.shift['cashin']}</td>
			                    </tr>
			                    <tr>
			                      <td class="col-xs-6"><label>Payout:</label></td>
			                      <td class="col-xs-6">${s_form.shift['cashout']}</td>
			                    </tr>
			                    <tr>
			                      <td class="col-xs-6"><label>Revoqued:</label></td>
			                      <td class="col-xs-6">${s_form.shift['revoq_tot']}</td>
			                    </tr>
			                    <tr>
			                      <td class="col-xs-6"><label>Benefice:</label></td>
			                      <td class="col-xs-6"><span id="sbenefice">${s_form.shift['balance']}</span></td>
			                    </tr>
                        	</table>
                        </div>
                      </div>
                        <div class="modal-footer">
                          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                          <button type="button" id="s_btnprint" class="btn btn-primary hidden-print">Imprimer</button>
                        </div>
                       
                      </div>
                       
                    </div>
                  </div>

            <h3></h3>
             <table class="user">
                <tr>
                    <td class="col-xs-2"><label>Heure début:</label></td>
                    <td class="col-xs-2"><span class="ishift">${s_form.shift['start']}</span></td>
                </tr>
                <tr>
                    <td class="col-xs-2"><label>Heure de calcul:</label></td>
                    <td class="col-xs-2"><span class="ishift">${s_form.shift['time']}</span></td>
                </tr>
                <tr>
                    <td class="col-xs-2"><label>Tickets Total:</label></td>
                    <td class="col-xs-2"><span class="ishift">${s_form.shift['tick_tot']}</span></td>
                </tr>
                <tr>
                    <td class="col-xs-2"><label>Tickets de paie:</label></td>
                    <td class="col-xs-2"><span class="ishift">${s_form.shift['paid_tot']}</span></td>
                </tr>
                <tr>
                    <td class="col-xs-2"><label>Tickets Revoqués:</label></td>
                    <td class="col-xs-2"><span class="ishift">${s_form.shift['revoq']}</span></td>
                </tr>
                <tr>
                    <td class="col-xs-2"><label>Payin:</label></td>
                    <td class="col-xs-2"><span class="ishift">${s_form.shift['cashin']}</span></td>
                </tr>
                <tr>
                    <td class="col-xs-2"><label>Payout:</label></td>
                    <td class="col-xs-2"><span class="ishift">${s_form.shift['cashout']}</span></td>
                </tr>
                <tr>
                    <td class="col-xs-2"><label>Revoqued:</label></td>
                    <td class="col-xs-2"><span class="ishift">${s_form.shift['revoq_tot']}</span></td>
                </tr>
                <tr>
                    <td class="col-xs-2"><label>Balance:</label></td>
                    <td class="col-xs-2"><span class="ishift">${s_form.shift['balance']}</span></td>
                </tr>
                <tr>
                    <td class="col-xs-2"><label>Taxes:</label></td>
                    <td class="col-xs-2"><span class="ishift">-</span></td>
                </tr>
            </table>
          <h3></h3>
          <input type="hidden" id="hiddenday" name="hiddenday" value="${s_form.endday}"/>
          <label>Se deconnecter:</label>&nbsp;&nbsp;<button type="button" class="btn btn-info" onclick="disconnected()">EXIT</button>
        

        </div>
        <div class="col-sm-3">
           <h3></h3>
            
        </div>
      </div>
    </form>
      </div>
     
      <!--</div>-->
    <!-- fin interface shift -->
     
      <footer class="footer">
        Jeu responsable. Copyright 2020.
        <!--<a href="lottery/index.html" target="popup" id="lien" onclick="window.open('lottery/index.html','name','width=1600,height=900')">
         Open page in new window</a>-->
      </footer>
      
    </div>

    <script src="ressources/rsrc_caissier/assets/javascript/js/script.js"></script>
    <script src="ressources/rsrc_caissier/assets/javascript/js/jquery-2.2.4.min.js"></script>
    <script src="ressources/rsrc_caissier/assets/javascript/js/printThis.js"></script>
    <script src="ressources/js/jquery/jquery-barcode.js"></script>
    <script src="ressources/rsrc_caissier/assets/slicknav/jquery.slicknav.min.js"></script>
    <script src="ressources/rsrc_caissier/bootstrap-3.3.6/dist/js/bootstrap.min.js"></script>
    
   
    <script>

      //pour le tirage aléatoire
      let alea_choice;


//        var modale = document.getElementById('imprimer');
//        if(modale != null){
//     	 //  console.log('modal');
    	   
//     	   let idbarcode = $('#idbc').text();
//     	   //$('#idbc').css('display','none');
//     	   //console.log('code barre: '+idbarcode);
//     	   //$('#bcTarget').barcode(idbarcode,'ean13');code11
//     	   $('#imprimer').modal('show');
    	   
//     	   $("#bcTarget").barcode(idbarcode,"ean13",{barWidth:2, barHeight:40, output:"css",posX:100});
//     	   document.getElementById('btnprint').focus();
//     	   //$("#btnprint").click();
//     	   console.log('code barre: '+idbarcode);
    	   
//        }
       
       //impression spin 2 win
       var pmodale = document.getElementById('p_imprimer');
       if(pmodale != null){
    	   
    	   let idpbarcode = $('#p_idbc').text();
    	 //  $('#p_idbc').css('display','none');
    	   
    	   $('#p_imprimer').modal('show');
    	   
    	   $("#p_bcTarget").barcode(idpbarcode,"code11",{barWidth:2, barHeight:30, output:"css",posX:100});
    	   document.getElementById('p_btnprint').focus();
       }
       
       //modal du shift
       var s_modal = document.getElementById('shiftModal');
       if(s_modal != null){
    	   $('#shiftModal').modal('show');
       }
       
     // $('#myModale').modal('show');
        
        window.onload = date_heure('date_heure');
        window.onload = date_heure('date_bingo');
        window.onload = date_heure('date_dog');
        window.onload = date_heure('date_soccer');
        window.onload = date_heure('date_shift');
        window.onload = date_heure('date_spin');
       // var ichoice = $("#code").val();
       // alert(ichoice);
       $('#clear').click(function(){
          console.log('clear');
          document.getElementById('code').value='';
          // on vide d'abord le tableau
          $('.t_pari').find('tbody').empty();
          document.getElementById("icode").innerHTML = "";
		  $('#alea_nbre').val('');
       });
       $('#clear_sp').click(function(){
           console.log('clear');
           document.getElementById('spcode').value='';
           // on vide d'abord le tableau
           $('.sp_t_pari').find('tbody').empty();
           document.getElementById("ispcode").innerHTML = "";
        });
       
       $('#barcode').click(function(){
    	   clear_form();
       });
       
       //impression du modal de ticket keno
       $('#btnprint').click(function(){
    	   idpath = $('#idpath').text();
          // console.log("IDPATH: "+idpath);
    	   printElement(document.getElementById("printThis"));
    	 //  window.print();
    	//   $('#printSection'). printThis();
    	   $('#printThis').printThis({
    		    debug: false,              
			    importCSS: false,            
			    importStyle: false,      
			    printContainer: true,       
			    loadCSS: idpath+"/ressources/rsrc_caissier/assets/css/styles1.css" ,
    	   });
    	    document.getElementById('code').focus();
       });
       $('#p_btnprint').click(function(){
         idpath = $('#idpath').text();
         printElement(document.getElementById("p_printThis"));
         $('#p_printThis').printThis({
    		    debug: false,              
			    importCSS: false,            
			    importStyle: false,      
			    printContainer: true,       
			    loadCSS: idpath+"/ressources/rsrc_caissier/assets/css/styles1.css" ,
    	   });
    	  
       });

       $('#s_btnprint').click(function(){
        idpath = $('#idpath').text();
    	   printElement(document.getElementById("s_printThis"));
         $('#s_printThis').printThis({
    		    debug: false,              
			    importCSS: false,            
			    importStyle: false,      
			    printContainer: true,       
			    loadCSS: idpath+"/ressources/rsrc_caissier/assets/css/styles1.css" ,
    	   });
       });
       
     /*  $('#void_pay').click(clear_form);
       function clear_form(){
    	   $(':input')
    	   .not(':button, :submit, :hidden, :reset')
    	   .val('');
    	   $(':span').val('');
    	   $('.fixed_hedears').find('tbody').empty();
       }*/
       
       let numero_tirage;
       let message_tirage;
       let numero_prv;
       let idcoderace;
       let urlServeur = "";

      async function getUrlServer() {
        console.log("urlServeur0: "+urlServeur);
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
                      //  console.log("urlServeur: "+urlServeur);
                           
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
     
        $(function(){

            var menu = $('.menu-navigation-dark');

            menu.slicknav();

            // Mark the clicked item as selected

            menu.on('click', 'a', function(){
                var a = $(this);

                a.siblings().removeClass('selected');
                a.addClass('selected');
                if(this.id === 'kikoo_0'){
                  //console.log(this.id); 
                  document.querySelectorAll('.interf_soccer')[0].style.display = "block";
                  document.querySelectorAll('.interf_pari')[0].style.display = "none";
                  document.querySelectorAll('.interf_paiement')[0].style.display = "none";
                  document.querySelectorAll('.interf_bingo')[0].style.display = "none";
                  document.querySelectorAll('.interf_shift')[0].style.display = "none";
                  document.querySelectorAll('.interf_dog')[0].style.display = "none";
                  document.querySelectorAll('.interf_spin')[0].style.display = "none";
                }
                else if(this.id === 'kikoo_1'){
                  //console.log(this.id); 
                  document.querySelectorAll('.interf_pari')[0].style.display = "block";
                  document.querySelectorAll('.interf_soccer')[0].style.display = "none";
                  document.querySelectorAll('.interf_paiement')[0].style.display = "none";
                  document.querySelectorAll('.interf_bingo')[0].style.display = "none";
                  document.querySelectorAll('.interf_dog')[0].style.display = "none";
                  document.querySelectorAll('.interf_shift')[0].style.display = "none";
                  document.querySelectorAll('.interf_spin')[0].style.display = "none";
                }
                else if(this.id === 'kikoo_2'){
                  //console.log(this.id); 
                  document.querySelectorAll('.interf_pari')[0].style.display = "none";
                  document.querySelectorAll('.interf_paiement')[0].style.display = "none";
                  document.querySelectorAll('.interf_soccer')[0].style.display = "none";
                  document.querySelectorAll('.interf_bingo')[0].style.display = "none";
                  document.querySelectorAll('.interf_dog')[0].style.display = "block";
                  document.querySelectorAll('.interf_shift')[0].style.display = "none";
                  document.querySelectorAll('.interf_spin')[0].style.display = "none";
                }
                else if(this.id === 'kikoo_3'){
                  //console.log(this.id); 
                  document.querySelectorAll('.interf_bingo')[0].style.display = "block";
                  document.querySelectorAll('.interf_pari')[0].style.display = "none";
                  document.querySelectorAll('.interf_paiement')[0].style.display = "none";
                  document.querySelectorAll('.interf_soccer')[0].style.display = "none";
                  document.querySelectorAll('.interf_dog')[0].style.display = "none";
                  document.querySelectorAll('.interf_shift')[0].style.display = "none";
                  document.querySelectorAll('.interf_spin')[0].style.display = "none";
                }
                else if(this.id === 'kikoo_4'){
                  //console.log(this.id); 
                  document.querySelectorAll('.interf_pari')[0].style.display = "none";
                  document.querySelectorAll('.interf_paiement')[0].style.display = "block";
                  document.querySelectorAll('.interf_soccer')[0].style.display = "none";
                  document.querySelectorAll('.interf_bingo')[0].style.display = "none";
                  document.querySelectorAll('.interf_dog')[0].style.display = "none";
                  document.querySelectorAll('.interf_shift')[0].style.display = "none";
                  document.querySelectorAll('.interf_spin')[0].style.display = "none";
                }
                else if(this.id === 'kikoo_5'){
                  //console.log(this.id); 
                  document.querySelectorAll('.interf_shift')[0].style.display = "block";
                  document.querySelectorAll('.interf_pari')[0].style.display = "none";
                  document.querySelectorAll('.interf_paiement')[0].style.display = "none";
                  document.querySelectorAll('.interf_soccer')[0].style.display = "none";
                  document.querySelectorAll('.interf_bingo')[0].style.display = "none";
                  document.querySelectorAll('.interf_dog')[0].style.display = "none";
                  document.querySelectorAll('.interf_spin')[0].style.display = "none";
                }
                else if(this.id === 'kikoo_6'){
                  //console.log(this.id); 
                  document.querySelectorAll('.interf_spin')[0].style.display = "block";
                  document.querySelectorAll('.interf_shift')[0].style.display = "none";
                  document.querySelectorAll('.interf_pari')[0].style.display = "none";
                  document.querySelectorAll('.interf_paiement')[0].style.display = "none";
                  document.querySelectorAll('.interf_soccer')[0].style.display = "none";
                  document.querySelectorAll('.interf_bingo')[0].style.display = "none";
                  document.querySelectorAll('.interf_dog')[0].style.display = "none";
                }
                
            });
        });
        
        idcoderace = $('#idpartner').text();
        idcaissier = $('#idcaissier').text();
      //  console.log("ID: "+idcoderace);

      //recuperation du temps du jeu en cours
      async function updatetiragek() {
        //var url = 'http://localhost:9090/api/v1/supergames/timekeno/'+coderace;
        
          setInterval(async function(){
            if(urlServeur != undefined){
               var url = urlServeur+'/counter/'+coderace;
                const response = await fetch(url);
                const myJson = await response.json(); //extract JSON from the http response
                  // do something with myJson
                //console.log('myJson '+myJson);
                timekeno = myJson
            }
            else{
              timekeno = "-:-";
            }
          },1000);

      }

//    updatetiragek();  
       
        $(function (){

          var idpath0 = $('#idpath').text();
          var partn = $('#idpartner0').text();
          console.log('path: '+idpath0);

          var imag = idpath0+'/ressources/rsrc_caissier/assets/images/logo_'+partn+'.jpeg'
          
          $("#myImage0").attr("src", imag);
          document.getElementById("myImage0").src = imag;

        	setInterval(function(){

				$.ajax({
					url:"updatetirage",
					type:"GET",
					data:{
						'coderace':idcoderace
					},
					success:function(result){
	 		            // Pour chaque resultat du tableau
	 		            $.each(result, function(index, value){
	 		             
	 		                // on retrouve les param�tres qu'on avait fix� via l'API Json dans la servlet
	 		               numero_tirage = value.drawnumk;
	 		               numero_prv = value.timekeno;
	 		               message_tirage = value.message;
	 		               //console.log("numero_prv: "+ typeof numero_prv+" | "+numero_prv);
	 		               
	 		               $(".pari_r").empty();
	 		               $(".pr_pari_r").empty();
                     $("#messagek").empty();

	 		               if(!value.canbet){
	 		            	  $("#code").prop("disabled", true);
	 		               }
	 		               else{
	 		            	  $("#code").prop("disabled", false); 
	 		               }
	 		             
	 		               // On mets à jour les differents champs
	 		               $(".pari_r").prepend(numero_tirage);
	 		               $(".pr_pari_r").prepend(numero_prv);
                     $("#messagek").prepend(message_tirage);
	 		               
	 		            });
					}
				});
				
			},333);	
        });


   
    function restoreDatas(){
			$.ajax({
				url:"gamestate",
				type:"GET",
				async: false,
				/*success:function(result){
					$.each(result, function(index, value){
						
					});
			    }*/
              });
        }
    restoreDatas();
    
  
    $(function (){
        setInterval(function(){
        
				$.ajax({
					url:"updateairtime",
					type:"GET",
					data:{
			            'caissier':idcaissier,
			            'coderace':idcoderace
					},
					success:function(result){
	 		            // Pour chaque résultat du tableau
	 		            $.each(result, function(index, value){
	 		             
	 		                // on retrouve les paramètres qu'on avait fix� via l'API Json dans la servlet
                      var airtime = value.balance;
                      
	 		               $(".balance").empty();
	 		             
	 		               // On mets à jour les differents champs
	 		               $(".balance").prepend(airtime);
	 		            
	 		               
	 		            });
					}
				});
				
			},2000);	
        });
		
	/*	function print_ticket(){
			$(".modal-printer").printThis({
				debug: false,              
			    importCSS: true,            
			    importStyle: true,      
			    printContainer: true,       
			    loadCSS: "./ressources/rsrc_caissier/assets/css/styles1.css" ,
			   // pageTitle: "My Modal",             
			    //removeInline: true        
			    //printDelay: 333,            
			    //header: null            
			     formValues: false 
			});
		}*/
		$("#effVers").prop("disabled",true); // disabled versement button
		$("#print").prop("disabled",true); //disabled keno print button

    </script>
   </body>
</html>