<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<!DOCTYPE html>
<html lang="fr">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Administration-GUI</title>
    <link rel="stylesheet" type="text/css" href="ressources/admin/bootstrap-3.3.6/dist/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="ressources/rsrc_caissier/assets/css/admin_style.css">
    <link rel="stylesheet" href="ressources/rsrc_caissier/assets/css/navigation-dark.css">
    <link rel="stylesheet" href="ressources/rsrc_caissier/assets/slicknav/slicknav.min.css">
  </head>
<body>
	<div class="container-fluid">
      <div class="row">
        <div class="col-sm-10 nbar" id="menu">
          <nav class="menu-navigation-dark">
            <a href="javascript:void(0);" id="kikoo_0" class="${state==1 ? 'selected' :''}"><i class="fa fa-camera-retro"></i><span>STATISTIQUES</span></a>
            <a href="javascript:void(0);" id="kikoo_1" class="${state==2 ? 'selected' :''}"><i class="fa fa-code"></i><span>TURNOVER</span></a>
            <a href="javascript:void(0);" id="kikoo_2" class="${state==3 ? 'selected' :''}"><i class="fa fa-camera-retro"></i><span>AIRTIME</span></a>
            <a href="javascript:void(0);" id="kikoo_3" class="${state==4 ? 'selected' :''}"><i class="fa fa-code"></i><span>TICKETS</span></a>
            <a href="javascript:void(0);" id="kikoo_4" class="${state==5 ? 'selected' :''}"><i class="fa fa-camera-retro"></i><span>RAPPORTS</span></a>
            <a href="javascript:void(0);" id="kikoo_5" class="${state==6 ? 'selected' :''}"><i class="fa fa-code"></i><span>PARAMETRAGES</span></a>
          </nav>
        </div>
        <div class="col-sm-2" id="logoff">
        	<a href="./login.jsp" class="btn btn-info">
        		<span class="glyphicon glyphicon-log-out"></span>Log out
        	</a>
        </div>
      </div>
  </div> 
  <div class="container">
      <!-- Contenu interface finance -->
      <div class="interf_finance" style="display:${state==1 ? 'block' :'none'};">
       <form method="post" action="administration">
       <div class="row">
        <div class="col-sm-12">
           <table class="user" style="margin: 5px;">
                <tr>
                    <td class="col-xs-4"><label>USER:</label>&nbsp;&nbsp;<span id="idpartner">${caissier.loginc}</span><span id="idpartner2">${caissier.grpe}</span></td>
                    <td class="col-xs-4"><label>Heure:</label>&nbsp;&nbsp;<span id="date_heure"></span></td>
                    <td class="col-xs-4"><label></label></td>
                </tr>
            </table>
        </div>
      </div>
       <div class="row">
        <div class="col-sm-12">
           <table class="ifinance" style="margin: 5px;">
                <tr>
                    <td class="col-xs-3"><label>Date Début:</label>&nbsp;&nbsp;<input type="date" class="date" id="ddebut" name="ddebut" value="${fin_form.dat1}"></td>
                    <td class="col-xs-3"><label>Date Fin:</label>&nbsp;&nbsp;<input type="date" class="date" id="dfin" name="dfin" value="${fin_form.dat2}"></td>
                    <td class="col-xs-4"><label for="sel1">Partenaire:</label>&nbsp;&nbsp;
		                <select class="" name="ficoderace" id="selcoderace">
		                   <!--<option>RAMATTEST</option>-->
		                  <option>${fin_form.coderace}</option>
		                </select>
                	</td>
                	<td class="col-xs-2"><input type="submit" class="btn btn-primary" value="Calculer"></td>
                </tr>
            </table>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-12" style="padding-bottom:5px;">

          <fieldset>
              <legend>Statistiques</legend>
            <table class="table table-fixed">
              <thead >
              <!--<tr>
                <th class="col-xs-2">.</th>
                <th class="col-xs-5" colspan="2">Entrée</th>
                <th class="col-xs-5" colspan="2">Sortie</th>
              </tr>-->
              <tr>
                <th class="col-xs-2">Jeu</th>
                <th class="col-xs-2">Tickets joués</th>
                <th class="col-xs-2">Somme totale</th>
                <th class="col-xs-2">Tickets payés</th>
                <th class="col-xs-2">Somme totale</th>
                <th class="col-xs-1">Balance</th>
                <th class="col-xs-1">Rapport</th>
              </tr>
             </thead>
              <tbody>
                    <tr>
                      <td class="col-xs-2">Keno</td>
                      <td class="col-xs-2">${empty fin_form.nbre_keno ? 0 : fin_form.nbre_keno}</td>
                      <td class="col-xs-2">${empty fin_form.sum_keno ? 0 : fin_form.sum_keno}</td>
                      <td class="col-xs-2">${empty fin_form.nbre_v_keno ? 0 : fin_form.nbre_v_keno}</td>
                      <td class="col-xs-2">${empty fin_form.sum_v_keno ? 0 : fin_form.sum_v_keno}</td>
                      <td class="col-xs-1">${fin_form.balance}</td>
                      <td class="col-xs-1">${fin_form.percent_keno}</td>
                    </tr>
                      <tr>
                      <td class="col-xs-2">Spin</td>
                      <td class="col-xs-2">0</td>
                      <td class="col-xs-2">0</td>
                      <td class="col-xs-2">0</td>
                      <td class="col-xs-2">0</td>
                      <td class="col-xs-1">0</td>
                      <td class="col-xs-1">0</td>
                    </tr>
                    <tr> 
                      <td class="col-xs-2">Dog</td>
                      <td class="col-xs-2">0</td>
                      <td class="col-xs-2">0</td>
                      <td class="col-xs-2">0</td>
                      <td class="col-xs-2">0</td>
                      <td class="col-xs-1">0</td>
                      <td class="col-xs-1">0</td>
                    </tr>
                    <tr>
                      <td class="col-xs-2">Bingo</td>
                      <td class="col-xs-2">0</td>
                      <td class="col-xs-2">0</td>
                      <td class="col-xs-2">0</td>
                      <td class="col-xs-2">0</td>
                      <td class="col-xs-1">0</td>
                      <td class="col-xs-1">0</td>
                    </tr>
                    <tr>
                      <td class="col-xs-2">Sport</td>
                      <td class="col-xs-2">0</td>
                      <td class="col-xs-2">0</td>
                      <td class="col-xs-2">0</td>
                      <td class="col-xs-2">0</td>
                      <td class="col-xs-1">0</td>
                      <td class="col-xs-1">0</td>
                    </tr>   <!-- les differents parents -->
                    <tr>
                      <td class="col-xs-2" >Total</td>
                      <td class="col-xs-2">0</td>
                      <td class="col-xs-2">0</td>
                      <td class="col-xs-2">0</td>
                      <td class="col-xs-2">0</td>
                      <td class="col-xs-1">0</td>
                      <td class="col-xs-1">0</td>
                    </tr>
              </tbody>
            </table>
           </fieldset>
                  <table class="pari">
                    <tr>
                        <td class="col-xs-2"><label for="pari">Balance:</label></td>
                        <td class="col-xs-4"><input type="text" id="ibalance" name="ibalance" value="${fin_form.sum_keno - fin_form.sum_v_keno}" disabled size="40" maxlength="32"/></td>
                        <td class="col-xs-2"><label for="pari">Pourcentage:</label></td>
                        <td class="col-xs-4"><input type="text" id="percent" name="percent" value="${fin_form.percent_keno}" disabled size="40" maxlength="32"/></td>
                    </tr>
                    
                </table>
        </div>
      </div>
      
    </form>
    <!-- fin contenu finance -->
    </div>
    <!-- Contenu interface turnover -->
    <div class="interf_turnover" style="display:${state==2 ? 'block' :'none'};">
      <form method="post" action="turnover">
        <div class="row">
          <div class="col-sm-12">
            <table class="ifinance" style="margin: 5px;">
                  <tr>
                      <td class="col-xs-4"><label for="sel1">Partenaire:</label>&nbsp;&nbsp;
                      <select class="" name="turncoderace" id="turncoderace" style="width:300px;">
                         <option>${fin_form.coderace}</option> 
                      </select>
                    </td>
                    <td class="col-xs-2"><input type="hidden" id="icoderace" name="icoderace" value=""><span name="iturncoderace" id="iturncoderace" style="font-weight:bold;">${turn_form.partneraire}</span></td>
                    <td class="col-xs-2"><input type="submit" class="btn btn-primary" value="Rechercher"></td>
                    <td class="col-xs-2"><span class="${empty turn_form.erreurs ? 'succes' :'erreur'}"> ${turn_form.resultat}</span></td>
                  </tr>
              </table>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-12 offers">
            <fieldset>
                <legend>Turnover</legend>
                  <table class="table table-fixed" id="tble_turn">
                      <thead>
                        <tr>
                          <th class="col-xs-1">Date</th>
                          <th class="col-xs-1">Cycle</th>
                          <th class="col-xs-1">Percent</th>
                          <th class="col-xs-1">Jeu</th>
                          <th class="col-xs-1">Frequence</th>
                          <th class="col-xs-1">Rang</th>
                          <th class="col-xs-1">Return</th>
                          <th class="col-xs-1">Mise</th>
                          <th class="col-xs-1">Payé</th>
                          <th class="col-xs-1">Jackpot</th>
                          <th class="col-xs-1">Curr %</th>
						  <th class="col-xs-1">Real %</th>
                        </tr>
                    </thead>
                      <tbody>
                      <c:forEach var="i" begin="1" end="${turn_form.taille}">
                          <tr class="table-fixed-turn"> 
                            <td class="col-xs-1">${turn_form.gmc[i-1].date_fin}</td>
                            <td class="col-xs-1">${turn_form.gmc[i-1].tour}</td>
                            <td class="col-xs-1">${turn_form.gmc[i-1].percent}%</td>
                            <td class="col-xs-1">${turn_form.gmc[i-1].jeu}</td>
                            <td class="col-xs-1">${turn_form.gmc[i-1].hitfrequence}%</td>
                            <td class="col-xs-1">${turn_form.gmc[i-1].position}</td>
                            <td class="col-xs-1">${turn_form.gmc[i-1].refundp}&nbsp;&nbsp;XAF</td>
                            <td class="col-xs-1">${turn_form.gmc[i-1].stake}</td>
                            <td class="col-xs-1">${turn_form.gmc[i-1].payout}</td>
                            <td class="col-xs-1">${turn_form.gmc[i-1].jkpt}</td>
                            <td class="col-xs-1">${turn_form.gmc[i-1].curr_percent}%</td>
							<td class="col-xs-1">${turn_form.gmc[i-1].real_percent}%</td>
                          </tr>
                        </c:forEach>
                      </tbody>
                    </table>
              </fieldset>
              <fieldset>
                <legend>Réglage turnover</legend>
                <table class="cbonus" style="margin: 5px;">
                  <tr>
                    <td class="">
                        <label>Pourcentage: </label>
                    </td>
                    <td class="">
                      <select class="" name="percentage" id="percentage" style="width:100px;">
                        <option value="70%">70</option>
                        <option value="75%">75</option>
                        <option value="85%">85</option>
                        <option value="90%">90</option>
                      </select>
                    </td>
                      <td class="">
                          <label>Cycle: </label>
                      </td>
                      <td class="">
                          <select class="" name="cycle" id="cycle" style="width:100px;" >
                              <option value="5000 tours">5000</option>
                              <option value="1000 tours">1000</option>
                              <option value="500 tours">500</option>
                              <option value="100 tours">100</option>
                          </select>
                      </td>
                      <td class="">
                        <label>Frequence: </label>
                      </td>
                      <td class="">
                        <select class="" name="frequence" id="frequence" style="width:100px;" >
                            <option value="10%">10</option>
                            <option value="15%">15</option>
                            <option value="20%">20</option>
                            <option value="25%">25</option>
                            <option value="30%">30</option>
                            <option value="35%">35</option>
                        </select>
                      </td>
                      <td class="">
                        <label>Jeu: </label>
                      </td>
                      <td class="">
                          <select class="" name="turn_jeu" id="turn_jeu" style="width:100px;">
                              <option value="Keno">Keno</option>
                              <option value="Dog">Dog Race</option>
                              <option value="Spin">Spin</option>
                              <option value="Bingo">Bingo</option>
                          </select>
                      </td>
                      
                   </tr>
                    <tr>
                        <td class="col-xs-1"></td>
                        <td class="col-xs-1"></td>
                        <td class="col-xs-1"><input type="hidden" id="turnchoice" name="turnchoice" value=""></td>
                        <td class="col-xs-1"></td>
                        <td class="col-xs-1"></td>
                        <td class="col-xs-1"></td>
                        <td class="col-xs-6" colspan="2">
                        	<input type="submit" class="btn btn-success" id="addturnover" value="Valider" onclick="return validation_turn();"/>&nbsp;&nbsp;
                        	<span class="${empty turn_form.erreurs ? 'succes' :'erreur'}"> ${turn_form.resultat}</span>
                        </td>
                    </tr>
                    
               </table>
              </fieldset>
          </div>
        <!-- end modal <div class="col-sm-3 logo">Logo</div>-->
        </div>
      </form>
    </div>
    <!-- fin contenu turnover -->
    <!-- Contenu interface airtime -->
    <div class="interf_airtime" style="display:${state==3 ? 'block' :'none'};">
      <form method="post" action="admin_airtime">
            <div class="row">
              <div class="col-sm-12" id="c_airtime">
                <h3>Créditer caisse</h3>
                    <table class="cairtime">
                          <tr>
                              <td class="col-xs-2"><label for="airtime_room">Nom de la salle:</label></td>
                              <td class="col-xs-4">
                                <select class="" name="airtime_room" id="airtime_room" style="width:300px;" onchange="retrieve_user();">
                                    
                                </select>
                              </td>
                          </tr>
                          <tr>
                            <td class="col-xs-2"><label for="airtime_user">Nom Caisse:</label></td>
                            <td class="col-xs-4">
                              <select class="" name="airtime_user" id="airtime_user" style="width:300px;">
                                <option>Choisir une salle</option>
                              </select>
                            </td>
                        </tr>
                        <tr>
                          <td class="col-xs-2"><label for="airtime_sum">Montant à crediter:</label></td>
                          <td class="col-xs-4"><input type="text" id="airtime_sum" name="airtime_sum" value="${airtime_form.credit}" onkeypress="return verif_amount(event);" size="10" maxlength="8"/></td>
                        </tr>
                        <tr> 
                            <td class="col-xs-2"></td>
                            <td class="col-xs-4">
                              <input type="submit" class="btn btn-success" id="addAirtime" value="Crediter"
                              onclick="return validation();" />&nbsp;&nbsp;
                              <span id="cpart_airtime" class="${empty airtime_form.erreurs ? 'succes' :'erreur'}"> ${airtime_form.resultat} </span>
                            </td> 
                        </tr>
                          
                    </table>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-12" id="c_airtime1">
                <h3>Bloquer caisse</h3>
                    <table class="cairtime">
                          <tr>
                              <td class="col-xs-2"><label for="lockroom">Nom de la salle:</label></td>
                              <td class="col-xs-4">
                                <select class="" name="lockroom" id="lockroom" style="width:300px;" onchange="retrieve_user1();">
                                    
                                </select>
                              </td>
                          </tr>
                          <tr>
                            <td class="col-xs-2"><label for="lockuser">Nom Caisse:</label></td>
                            <td class="col-xs-4">
                              <select class="" name="lockuser" id="lockuser" style="width:300px;">
                                  <option>Choisir une salle</option>
                              </select>
                            </td>
                        </tr>
                        <tr>
                            <td class="col-xs-2"></td>
                            <td class="col-xs-4">
                              <input type="submit" class="btn btn-success" id="addAirtime" value="Bloquer"/>&nbsp;&nbsp;
                              <span id="cpart_lock" class="${empty airtime_form.erreurs ? 'succes' :'erreur'}"> ${airtime_form.resultat} </span>
                            </td>
                        </tr>
                          
                    </table>
              </div>
            </div>
     </form>
    </div>
    <!-- fin contenu airtime -->
    <!-- Contenu interface tickets -->
    <div class="interf_tickets" style="display:${state==4 ? 'block' :'none'};">
      <form method="post" action="admin_ticket">
        <div class="row">
          <div class="col-sm-12">
            <table class="ifinance" style="margin: 5px;">
                  <tr>
                      <td class="col-xs-3"><label>Date Début:</label>&nbsp;&nbsp;<input type="date" class="date" id="ddebut" name="ddebut" value="${ticket_form.dat1}"></td>
                      <td class="col-xs-3"><label>Date Fin:</label>&nbsp;&nbsp;<input type="date" class="date" id="dfin" name="dfin" value="${ticket_form.dat2}"></td>
                      <td class="col-xs-4"><label for="sel1">Partenaire:</label>&nbsp;&nbsp;
                      <select class="" name="selcoderace1" id="selcoderace1" style="width:300px;">
                          <option>${fin_form.coderace}</option>
                      </select>
                    </td>
                    <td class="col-xs-2"><input type="submit" class="btn btn-primary" value="Rechercher"></td>
                  </tr>
              </table>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-12 offers">
            <fieldset>
                <legend>Mouvement des tickets</legend>
                  <table class="table table-fixed" id="tble">
                      <thead>
                        <tr>
                          <th class="col-xs-1">N°</th>
                          <th class="col-xs-3">Date</th>
                          <th class="col-xs-3">Code</th>
                          <th class="col-xs-1">Jeu</th>
                          <th class="col-xs-2">Mise</th>
                          <th class="col-xs-1">Gains</th>
                          <th class="col-xs-1">Etat</th>
                        </tr>
                    </thead>
                      <tbody>
                      <c:forEach var="i" begin="1" end="${ticket_form.taille}">
                          <tr class="${ticket_form.misekt[i-1].etatMise == 'gagnant' ? 'succes' : ticket_form.misekt[i-1].etatMise == 'perdant' ? 'erreur' : ''}">
                            <td class="col-xs-1">${i}</td>
                            <td class="col-xs-3">${ticket_form.misekt[i-1].datMise}</td>
                            <td class="col-xs-3">	<a href="#" class="seeticket"><span class="seeticket1" id="seeticket">${ticket_form.misekt[i-1].barcode}</span></a></td>
                            <td class="col-xs-1">${ticket_form.misekt[i-1].typeJeu}</td>
                            <td class="col-xs-2">${ticket_form.misekt[i-1].summise}</td>
                            <td class="col-xs-1">${ticket_form.misekt[i-1].sumwin}</td>
                            <td class="col-xs-1">${ticket_form.misekt[i-1].etatMise}</td>
                          </tr>
                        </c:forEach>
                      </tbody>
                    </table>
              </fieldset>
          </div>

          <div class="modal fade" id="shiftModal" role="dialog">
                    <div class="modal-dialog modal-sm">
                    
                      <!-- Modal content-->
                      <div class="modal-content modal-printer" >
                       <div id="s_printThis">
                        <div class="modal-header">
                          <button type="button" class="close" data-dismiss="modal">&times;</button>
                          <h4 class="modal-title" style="text-align: center;">Coupon -</h4>
                        </div>
                        <div class="modal-body">
                          <table class="user" style="width:100%;">
                            <tr>
                              <td class="col-xs-6"><label>Date:</label></td>
                              <td class="col-xs-6"><span class="see_date">04-12-2020</span></td>
                            </tr>
                            <tr>
                              <td class="col-xs-6"><label>Salle:</label></td>
                              <td class="col-xs-6"><span class="see_salle"></span></td>
                            </tr>
                            <tr>
                              <td class="col-xs-6"><label>Caissier:</label></td>
                              <td class="col-xs-6"><span class="see_cais"></span></td>
                            </tr>
                            <tr>
                              <td class="col-xs-6"><label>Jeu:</label></td>
                              <td class="col-xs-6"><span class="see_jeu"></span></td>
                            </tr>
                            <tr>
                              <td class="col-xs-6"><label>Pari:</label></td>
                              <td class="col-xs-6"><span class="see_pari"></span></td>
                            </tr>
                            <tr>
                              <td class="col-xs-6"><label>Choix:</label></td>
                              <td class="col-xs-6"><span class="see_choix"></span></td>
                            </tr>
                            <tr>
                              <td class="col-xs-6"><label>Montant:</label></td>
                              <td class="col-xs-6"><span class="see_amount"></span></td>
                            </tr>
                            <tr>
                              <td class="col-xs-6"><label>Tour Mutlti:</label></td>
                              <td class="col-xs-6"><span class="see_multi"></span></td>
                            </tr>
                            <tr>
                              <td class="col-xs-6"><label>Mutiplicateur:</label></td>
                              <td class="col-xs-6"><span class="see_x"></span></td>
                            </tr>
                            <tr>
                              <td class="col-xs-6"><label>Numero evenement:</label></td>
                              <td class="col-xs-6"><span class="see_num"></span></td>
                            </tr>
                            <tr>
                              <td class="col-xs-6"><label>Resultat:</label></td>
                              <td class="col-xs-6"><span class="see_result"></span></td>
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
        <!-- end modal <div class="col-sm-3 logo">Logo</div>-->
        </div>
      </form>
    </div>
    <!-- fin contenu tickets -->
    <!-- Contenu interface rapports -->
    <div class="interf_rapports" style="display:${state==5 ? 'block' :'none'};">
      <form method="post" action="reporting">
      <div class="row">
        <div class="col-sm-12 cob">
          <table class="ifinance" style="margin: 5px;">
             <tr>
                <td class="col-xs-2"><label>Date Début:</label>&nbsp;&nbsp;<input type="date" class="date" id="rep_ddebut" name="rep_ddebut" value=""></td>
                <td class="col-xs-2"><label>Date Fin:</label>&nbsp;&nbsp;<input type="date" class="date" id="rep_dfin" name="rep_dfin" value=""></td>
                <td class="col-xs-3"><label for="nc_rapport1">Partenaire:</label>&nbsp;&nbsp;
                  <select class="" name="nc_rapport1" id="nc_rapport1" style="width:300px;" onchange="retrieve_user2()">
                      
                  </select>
                </td>
                <td class="col-xs-3"><label for="nc_rapport">Nom Caisse:</label>
                  <select class="" name="nc_rapport" id="nc_rapport" style="width:300px;">
                    <option>Choisir une caisse</option>
                  </select>
                </td>
                <td class="col-xs-2"><input type="submit" class="btn btn-primary" value="Rechercher"></td>
             </tr>
            </table>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-12 cob">
            <fieldset>
              <legend>Rapports caisses</legend>
                <table class="table table-fixed" id="tble_turn">
                    <thead>
                      <tr>
                        <th class="col-xs-2">Caissier</th>
                        <th class="col-xs-2">Apport</th>
                        <th class="col-xs-2">Joués</th>
                        <th class="col-xs-2">Payés</th>
                        <th class="col-xs-2">Annulés</th>
                        <th class="col-xs-2">Cash</th>
                      </tr>
                  </thead>
                    <tbody>
                    <c:forEach var="i" begin="1" end="${report_form.taille}">
                        <tr class="table-fixed-turn"> 
                          <td class="col-xs-2">${report_form.reports[i-1].user}</td>
                          <td class="col-xs-2">${report_form.reports[i-1].apport}</td>
                          <td class="col-xs-2">${report_form.reports[i-1].summise}</td>
                          <td class="col-xs-2">${report_form.reports[i-1].sumwin}</td>
                          <td class="col-xs-2">0</td>
                          <td class="col-xs-2">${report_form.reports[i-1  ].cash}</td>
                        </tr>
                      </c:forEach>
                      <tr class=""> 
                        <td class="col-xs-2">Bilan</td>
                        <td class="col-xs-2"></td>
                        <td class="col-xs-2"></td>
                        <td class="col-xs-2"></td>
                        <td class="col-xs-2"></td>
                        <td class="col-xs-2"></td>
                      </tr> 
                    </tbody>
                  </table>
            </fieldset>
            <!--<table class="ifinance" style="margin: 5px;">
              <tr class=""> 
                <td class=""><input type="button" id="cob" class="btn btn-danger" onclick="doCob()" value="Mise à jour des caisses"></td>
                <td class=""><input type="button" class="btn btn-success" onclick="openRoom()" value="Ouverture des caisses"></td>
              </tr>
            </table>-->

          <div class="col-sm-12" >
          <input type="button" id="cob" class="btn btn-danger" onclick="doCob()" value="Mise à jour des caisses">
          <input type="button" class="btn btn-success" onclick="openRoom()" value="Ouverture des caisses">
          <span id="cob_error" style="color:green;font-weight:bold"></span>
          <!-- <input type="button" class="btn btn-danger" value="Suspendre bonus">-->
          </div>

            
          <!--  <div class="col-sm-12 interf_cagnotte"> -->
              
              
          <!--  </div> -->
          </div>
       </div>
      </div>
      </form>
    </div>
    <!-- fin contenu rapports -->

    <!-- Contenu interface parametrages -->
    <div class="interf_config" style="display:${state==6 ? 'block' :'none'};">
     <form method="post" action="configuration">
       <div class="row">
       <input type="hidden" id="idconfig" name="idconfig" value="">
        <div class="col-sm-12">
          <nav class="imenu-navigation-dark">
            <a href="javascript:void(0);" id="partner_crud" class="${action==1 || action==2 ? 'selected' :''}"><i class="fa fa-code"></i><span>Partner</span></a>
            <a href="javascript:void(0);" id="user_crud" class="${action==3 || action==4 ? 'selected' :''}"><i class="fa fa-camera-retro"></i><span>User</span></a>
            <a href="javascript:void(0);" id="bonus_crud" class="${action==5 || action==6 ? 'selected' :''}"><i class="fa fa-code"></i><span>Bonus</span></a>
            <a href="javascript:void(0);" id="cagnotte_crud" class="${action==7 || action==8 ? 'selected' :''}"><i class="fa fa-code"></i><span>Cagnotte</span></a>
          </nav>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-12 interf_partner" style="display:${action==2 || action==1 ? 'block' :'none'};">
          <input type="button" class="btn btn-primary" onclick="showFormAddpartner()" value="Ajouter partenaire">
          <input type="button" class="btn btn-success" onclick="showFormManpartner()" value="Gestion partenaire">
         <!-- <input type="button" class="btn btn-warning" value="Editer partenaire">
          <input type="button" class="btn btn-danger" value="Suspendre partenaire">-->
        </div>
        <div class="col-sm-12 interf_user" style="display:${action==3 || action==4 ? 'block' :'none'};">
          <input type="button" class="btn btn-primary" onclick="showFormAdduser()" value="Ajouter caissier">
          <input type="button" class="btn btn-success" onclick="showFormManuser()" value="Gestion caissier">
          <!--<input type="button" class="btn btn-warning" value="Editer caissier">
          <input type="button" class="btn btn-danger" value="Suspendre caissier">-->
        </div>
        <div class="col-sm-12 interf_bonus" style="display:${action==5 || action==4 ? 'block' :'none'};">
          <input type="button" class="btn btn-primary" onclick="showFormAddbonus()" value="Ajouter bonus">
          <input type="button" class="btn btn-success" onclick="showFormManbonus()" value="Gestion bonus">
          <!--<input type="button" class="btn btn-warning" value="Editer bonus">
          <input type="button" class="btn btn-danger" value="Suspendre bonus">-->
        </div>
        <div class="col-sm-12 interf_cagnotte">
          <input type="button" class="btn btn-primary" onclick="showFormAddCagnotte()" value="Ajouter cagnotte">
          <input type="button" class="btn btn-success" onclick="showFormMancagnotte()" value="Gestion Cagnotte">
          <!--<input type="button" class="btn btn-warning" value="Editer cagnotte">
          <input type="button" class="btn btn-danger" value="Suspendre cagnotte">-->
        </div>
      </div>
      <div class="row">
      
        <div class="col-sm-12 ${action == 1 ? 'solide' : 'invisible'}" id="c_partner">
          <h3>Creer partenaire</h3>
              <table class="cpartner">
                    <tr>
                        <td class="col-xs-2"><label for="ncpartner">Nom du partenaire:</label></td>
                        <td class="col-xs-4"><input type="text" id="ncpartner" name="ncpartner" value="${con_form.coderace}" size="40" maxlength="32" autocomplete="off" /></td>
                    </tr>
                    <tr>
                        <td class="col-xs-2"><label for="nczone">Zone de localisation:</label></td>
                        <td class="col-xs-4"><input type="text" id="nczone" name="nczone" value="${con_form.zone}" size="40" maxlength="32" autocomplete="off"/></td>
                    </tr>
                    <tr>
                        <td class="col-xs-2"></td>
                        <td class="col-xs-4">
                        	<input type="submit" class="btn btn-success" id="addpartner" value="Creer"/>&nbsp;&nbsp;
                        	<span id="cpart_state" class="${empty con_form.erreurs ? 'succes' :'erreur'}"> ${con_form.resultat} </span>
                        </td>
                    </tr>
                    
               </table>
        </div>
        <div class="col-sm-12" id="e_partner">
          <h3>Gestion partenaires</h3>
          <div class="col-sm-6">
            
              <div class="form-group">
                <label for="sel1">Choisir partenaire à modifier:</label>
                <select class="" name="coderace" id="n_cagnotte" style="width:300px;">
                          
                </select>
              </div>
              <div class="col-xs-12">
                <button class="btn btn-primary" type="button">Editer</button>
                <button class="btn btn-info" type="button">Details</button>
                <button class="btn btn-warning" type="submit">Supprimer</button>
              </div>
           
          </div>
        </div>
        <div class="col-sm-12 ${action == 3 ? 'solide' : 'invisible'}" id="c_cashier">
          <h3>Creer utilisateur</h3>
              <table class="cuser" style="margin: 5px;">
                    <tr>
                        <td class="col-xs-2"><label for="ncuser">Nom du caissier:</label></td>
                        <td class="col-xs-4"><input type="text" id="ncuser" name="ncuser" value="${con_form.user['nomc']}" size="40" maxlength="32" autocomplete="off"/></td>
                    </tr>
                    <tr>
                        <td class="col-xs-2"><label for="nclogin">Login du caissier:</label></td>
                        <td class="col-xs-4"><input type="text" id="nclogin" name="nclogin" value="${con_form.user['loginc']}" size="40" maxlength="32" autocomplete="off"/></td>
                    </tr>
                    <tr>
                        <td class="col-xs-2"><label for="ncpass">Mot de passe:</label></td>
                        <td class="col-xs-4"><input type="text" id="ncpass" name="ncpass" value="${con_form.user['passc']}" size="40" maxlength="32" autocomplete="off"/></td>
                    </tr>
                    <tr>
                        <td class="col-xs-2"><label for="ncprofil">Profil:</label></td>
                        <td class="col-xs-4">
                          <select class="form-control " name="ncprofil" id="sel_profil" style="width:300px;">
                            <option>CAISSIER</option>
                            <!--<option>ADMINISTRATEUR</option>-->
                            </select>
                        </td>
                    </tr>
                    <!--<tr>
                        <td class="col-xs-2"><label for="ncpartner_cais">Partenaire:</label></td>
                        <td class="col-xs-4">
                          <select class="" name="ncpartner_cais" id="sel_partner" style="width:300px;">
                          
                          </select>
                        </td>
                    </tr>-->
                    <tr>
                        <td class="col-xs-2"><label for="ncsalle_cais">Salle:</label></td>
                        <td class="col-xs-4">
                            <select class="" name="ncsalle_cais" id="sel_salle" style="width:300px;">
                          
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td class="col-xs-2"></td>
                        <td class="col-xs-4"><input type="submit" class="btn btn-success" id="addcaissier" value="Creer"/>&nbsp;&nbsp;
                        	<span id="cpart_state" class="${empty con_form.erreurs_u ? 'succes' :'erreur'}"> ${con_form.resultat_u} </span>
                        </td>
                    </tr>
                    
               </table>
        </div>
        <div class="col-sm-12" id="e_caissier">
          <h3>Gestion des utilisateurs</h3>
          <div class="col-sm-6">
           
              <div class="form-group">
                <label for="sel1">Rechercher utilisateur:</label>
                <input type="text" id="ncrlogin" name="ncrlogin" value="" size="40" maxlength="32" placeholder="Veuillez saisir un element à chercher" />
              </div>
              <div class="col-xs-12">
                <button class="btn btn-primary" type="button">Editer caissier</button>
                <button class="btn btn-info" type="button">Details caissier</button>
                <button class="btn btn-warning" type="submit">Suspendre caissier</button>
              </div>
          </div>
        </div>
        <div class="col-sm-12 ${action == 5 ? 'solide' : 'invisible'}" id="c_bonus">
          <div class="col-sm-6">
            <table class="cbonus" style="margin: 5px;">
                  <tr>
                    <td class="">
                        <label>Partenaire: </label>
                    </td>
                    <td class="">
                        <select class="" name="ncbonus_part1" id="ncbonus_part1" style="width:100px;" onchange="retrieve_bonus();">
                        
                        </select>
                    </td>
                      <td class="">
                          <label>Jeu: </label>
                      </td>
                      <td class="">
                          <select class="" name="ncbonus_jeu" id="ncbonus_jeu" style="width:100px;" onchange="retrieve_bonus();">
                              <option value="keno">Keno</option>
                              <option value="dog">Dog Race</option>
                              <option value="spin">Spin</option>
                              <option value="bingo">Bingo</option>
                          </select>
                      </td>
                        <td class="">
                        
                          </td>
                   </tr>
                   <tr>
                      <td class="">
                          <label>Bonus en cours: </label>
                      </td>
                      <td class="">
                          <span name="current_bonus" id="current_bonus"></span>
                      </td>
                        <td class="">
                            <label>Bonus en reserve: </label>
                        </td>
                        <td class="">
                           <span name="current_reserve" id="current_reserve"></span>
                        </td>
                        <td class="">
                          
                        </td>
                     </tr>
                    <tr>
                      <td class="">
                          <label>Bonus min en cours: </label>
                      </td>
                      <td class="">
                          <span name="current_min_bonus" id="current_min_bonus"></span>
                      </td>
                        <td class="">
                            <label>Bonus max en cours: </label>
                        </td>
                        <td class="">
                           <span name="current_max_bonus" id="current_max_bonus"></span>
                        </td>
                        <td class="">
                          
                        </td>
                     </tr>
                    <tr>
                    	<td class="col-xs-2">
                          <label>Montant bonus min:</label>
                      </td>
                      <td class="">
                          <input type="text" id="bonu_sum_min" name="bonu_sum_min" value="${con_form.bonusk0}" onkeypress="return verif_amount(event);" style="width:100px;" size="10" maxlength="8"/>
                      </td>
                      <td class="col-xs-2">
                          <label>Montant bonus max:</label>  
                      </td>
                      <td class="">
                          <input type="text" id="bonu_sum_max" name="bonu_sum_max" value="${con_form.bonusk1}" onkeypress="return verif_amount(event);" style="width:100px;" size="10" maxlength="8"/>
                      </td>
                      <td class="" >
                      
                      </td>
                    </tr>
                    <tr>
                        <td class="col-xs-2">
                            <label>Jackpot rate:</label>
                        </td>
                        <td class="col-xs-2">
                            <select class="" name="ncbonus_rate" id="ncbonus_rate" style="width:100px;">
                                <option value="0.5">0.5%</option>
                                <option value="1">1%</option>
                                <option value="2">2%</option>
                                <option value="5">5%</option>
                                <option value="10">10%</option>
                            </select>
                        </td>
                        <td class="col-xs-2">
                            <label>Jackpot reserve:</label>  
                        </td>
                        <td class="col-xs-2">
                            <select class="" name="ncbonus_reserve" id="reserve" style="width:100px;">
                                <option value="0">0%</option>
                                <option value="1">1%</option>
                                <option value="2">2%</option>
                                <option value="3">3%</option>
                                <option value="4">4%</option>
                                <option value="5">5%</option>
                                <option value="10">10%</option>
                            </select> </td>
                        <td class="col-xs-4" >
                      
                        </td>
                    </tr>
                    <tr>
                        <td class="col-xs-2"></td>
                        <td class="col-xs-6" colspan="2">
                        	<input type="submit" class="btn btn-success" id="addbonus" value="Activer bonus" onclick="return validation1();/>&nbsp;&nbsp;
                        	<span class="${empty con_form.erreurs_b ? 'succes' :'erreur'}"> ${con_form.resultat_b}</span>
                        </td>
                        <td class="col-xs-4">
                            
                        </td>
                    </tr>
                    
               </table>
              </div>
        </div>
        <!--<div class="col-sm-12 e_bonus">
          <h3>Gestion des bonus</h3>
          <div class="col-sm-6">
            <form action="#" method="post">
              <div class="form-group">
                <label for="sel1">Rechercher utilisateur:</label>
                <input type="text" id="ncrlogin" name="ncrlogin" value="" size="40" maxlength="32" placeholder="Veuillez saisir un element � chercher" />
              </div>
              <div class="col-xs-12">
                <button class="btn btn-primary" type="button">Editer bonus</button>
                <button class="btn btn-info" type="button">Details bonus</button>
                <button class="btn btn-warning" type="submit">Suspendre bonus</button>
              </div>
            </form>
          </div>
        </div> -->
        <div class="col-sm-12 ${action == 7 ? 'solide' : 'invisible'}" id="c_cagnotte">
          
            <table class="ccagnotte" style="margin: 5px;">
                    <tr>
                    	<td class="col-xs-2">
                        </td>
                        <td class="col-xs-6">
                          <h4><label>>> Activer cagnotte >></label></h4>
                              <label class="radio-inline">
                                <input type="radio" value="1" name="cagnot1">Oui
                              </label>
                              <label class="radio-inline" for="act-bonus">
                                <input type="radio" value="0" name="cagnot1" checked>Non
                              </label>  
                        </td>
                        <td class="col-xs-4">
                        </td>
                    </tr>
                  <!--  <tr>
                    	<td class="col-xs-2">
                        </td>
                        <td class="col-xs-6">
                          <h4><label>>> Montant Cagnotte >></label></h4>
                          <label class="radio-inline">
                            <input type="radio" value="jkpt1" name="mtbonus1">Cagnotte 1
                          </label>
                          <label class="radio-inline">
                            <input type="radio" value="jkpt2" name="mtbonus1">Cagnotte 2
                          </label>
                          <label class="radio-inline">
                            <input type="radio" value="jkpt3" name="mtbonus1">Cagnotte 3
                          </label>
                        </td>
                        <td class="col-xs-4">
                            
                        </td>
                    </tr> -->
                    <tr>
                        <td class="col-xs-2"></td>
                        <td class="col-xs-6">
                          <h4><label>>> Lots >></label></h4>
                          <select class="form-control " name="ncbonus_part" id="sel_salle">
                            <option value="1">Telephone</option>
                            <option value="2">Woofer</option>
                            <option value="3">Tee-shirt</option>
                            <option value="4">Ticket conso</option>
                            <option value="5">Televiseur</option>
                            <option value="6">Especes</option>
                          </select>
                        </td>
                        <td class="col-xs-4">
                          
                        </td>
                    </tr>
                    <tr>
                        <td class="col-xs-2"></td>
                        <td class="col-xs-6">
                          <h4><label>>> Partenaire >></label></h4>
                          <select class="" name="ncbonus_part2" id="ncbonus_part2" style="width:300px;">
                            <option value="">Choisir partenaire</option>
                            </select>
                        </td>
                        <td class="col-xs-4">
                          
                        </td>
                    </tr>
                    <tr>
                      <td class="col-xs-2"></td>
                      <td class="col-xs-6"><label>Date Cagnotte:</label>&nbsp;&nbsp;<input type="date" class="date" id="cgddebut" name="cgddebut" value="${con_form.dat1}"></td>
                      <td class="col-xs-4"></td>
                    </tr>
                    <tr>
                      <td class="col-xs-2"></td>
                      <td class="col-xs-6"><label>Heure Cagnotte:</label>&nbsp;&nbsp;
                        <select class="ncbonus_part2" name="cg_heure" id="cgsel_salle" style="width:80px;">
                            <option value=""></option>
                            <option value="10:00">10:00</option>
                            <option value="11:00">11:00</option>
                            <option value="14:00">14:00</option>
                            <option value="16:00">16:00</option>
                            <option value="17:00">17:00</option>
                            <option value="18:00">18:00</option>
                            <option value="19:00">19:00</option>
                            <option value="20:00">20:00</option>
                            <option value="21:00">21:00</option>
                            <option value="22:00">22:00</option>
                          </select>
                      </td>
                    </tr>
                    <tr>
                        <td class="col-xs-2"></td>
                        <td class="col-xs-6">
                        	<input type="submit" class="btn btn-success" id="addcagnotte" value="Activer cagnotte" onclick="updateLogoCagnotte();" />&nbsp;&nbsp;
                        	<span class="${empty con_form.erreurs_c ? 'succes' :'erreur'}"> ${con_form.resultat_c}</span>
                        </td>
                        <td class="col-xs-4">
                            
                        </td>
                    </tr>
                    
               </table>
       
        </div>
      </div>
      </form>
      </div>
    </div>
    <!-- fin interface parametrage -->
      <footer class="footer">
        Jeu Responsable
        <!--<a href="lottery/index.html" target="popup" id="lien" onclick="window.open('lottery/index.html','name','width=1600,height=900')">
         Open page in new window</a>-->
      </footer>
    </div>

    <script src="ressources/rsrc_caissier/assets/javascript/js/script.js"></script>
    <script src="ressources/rsrc_caissier/assets/javascript/js/jquery.min.js"></script>
    <script src="ressources/js/jquery/jquery-barcode.js"></script>
    <script src="ressources/rsrc_caissier/assets/slicknav/jquery.slicknav.min.js"></script>
    <script src="ressources/admin/bootstrap-3.3.6/dist/js/bootstrap.min.js"></script>
    <script>
        //$( "#code" ).val("MOMO");
        window.onload = date_heure('date_heure');
        
       $('#clear').click(function(){
          console.log('clear');
          document.getElementById('code').value='';
          // on vide d'abord le tableau
          $('.t_pari').find('tbody').empty();
       });

       setTimeout(function(){ console.log('iTEST');
        $('#lien').click();
        console.log('iiTEST');
      }, 3000);
	
       //selection de l'action � faire
       $('#addpartner').click(function(){
    	   console.log('ajout du partner');
    	   $('#idconfig').val("addpartner");
       })
       
       $('#addcaissier').click(function(){
    	   console.log('ajout du caissier');
    	   $('#idconfig').val("addcaissier");
       })
       
       $('#addbonus').click(function(){
    	   console.log('ajout du bonus');
    	   $('#idconfig').val("addbonus");
       })
       
       $('#addcagnotte').click(function(){
    	   console.log('ajout du cagnotte');
    	   $('#idconfig').val("addcagnotte");
       })
       
        $(function(){

            var menu = $('.menu-navigation-dark');

            menu.slicknav();

            // Mark the clicked item as selected

            menu.on('click', 'a', function(){
                var a = $(this);

                a.siblings().removeClass('selected');
                a.addClass('selected');
                if(this.id === 'kikoo_0'){
                  document.querySelectorAll('.interf_finance')[0].style.display = "block";
                  document.querySelectorAll('.interf_turnover')[0].style.display = "none";
                  document.querySelectorAll('.interf_airtime')[0].style.display = "none";
                  document.querySelectorAll('.interf_tickets')[0].style.display = "none";
                  document.querySelectorAll('.interf_rapports')[0].style.display = "none";
                  document.querySelectorAll('.interf_config')[0].style.display = "none";
                }
                else if(this.id === 'kikoo_1'){
                  //console.log(this.id); 
                  document.querySelectorAll('.interf_finance')[0].style.display = "none";
                  document.querySelectorAll('.interf_turnover')[0].style.display = "block";
                  document.querySelectorAll('.interf_airtime')[0].style.display = "none";
                  document.querySelectorAll('.interf_tickets')[0].style.display = "none";
                  document.querySelectorAll('.interf_rapports')[0].style.display = "none";
                  document.querySelectorAll('.interf_config')[0].style.display = "none";
                }
                else if(this.id === 'kikoo_2'){
                  //console.log(this.id); 
                  document.querySelectorAll('.interf_finance')[0].style.display = "none";
                  document.querySelectorAll('.interf_turnover')[0].style.display = "none";
                  document.querySelectorAll('.interf_airtime')[0].style.display = "block";
                  document.querySelectorAll('.interf_tickets')[0].style.display = "none";
                  document.querySelectorAll('.interf_rapports')[0].style.display = "none";
                  document.querySelectorAll('.interf_config')[0].style.display = "none";
                }
                else if(this.id === 'kikoo_3'){
                  //console.log(this.id); 
                  document.querySelectorAll('.interf_finance')[0].style.display = "none";
                  document.querySelectorAll('.interf_turnover')[0].style.display = "none";
                  document.querySelectorAll('.interf_airtime')[0].style.display = "none";
                  document.querySelectorAll('.interf_tickets')[0].style.display = "block";
                  document.querySelectorAll('.interf_rapports')[0].style.display = "none";
                  document.querySelectorAll('.interf_config')[0].style.display = "none";
                }
                else if(this.id === 'kikoo_4'){
                  //console.log(this.id); 
                  document.querySelectorAll('.interf_finance')[0].style.display = "none";
                  document.querySelectorAll('.interf_turnover')[0].style.display = "none";
                  document.querySelectorAll('.interf_airtime')[0].style.display = "none";
                  document.querySelectorAll('.interf_tickets')[0].style.display = "none";
                  document.querySelectorAll('.interf_rapports')[0].style.display = "block";
                  document.querySelectorAll('.interf_config')[0].style.display = "none";
                }
                else if(this.id === 'kikoo_5'){
                  //console.log(this.id); 
                  document.querySelectorAll('.interf_finance')[0].style.display = "none";
                  document.querySelectorAll('.interf_turnover')[0].style.display = "none";
                  document.querySelectorAll('.interf_airtime')[0].style.display = "none";
                  document.querySelectorAll('.interf_tickets')[0].style.display = "none";
                  document.querySelectorAll('.interf_rapports')[0].style.display = "none";
                  document.querySelectorAll('.interf_config')[0].style.display = "block";
                }
            });
        });

        $(function(){

            var menu = $('.imenu-navigation-dark');

            menu.slicknav();

            // Mark the clicked item as selected

            menu.on('click', 'a', function(){
                var a = $(this);

                a.siblings().removeClass('selected');
                a.addClass('selected');
                
              $('#c_partner').css('display','none');
            	$('#e_partner').css('display','none');
            	$('#e_caissier').css('display','none');
            	$('#c_cashier').css('display','none');
            	$('#c_bonus').css('display','none');
            	$('#c_cagnotte').css('display','none');
                
                if(this.id === 'partner_crud'){
                  document.querySelectorAll('.interf_partner')[0].style.display = "block";
                  document.querySelectorAll('.interf_user')[0].style.display = "none";
                  document.querySelectorAll('.interf_bonus')[0].style.display = "none";
                  document.querySelectorAll('.interf_cagnotte')[0].style.display = "none";
                }
                else if(this.id === 'user_crud'){
                  document.querySelectorAll('.interf_partner')[0].style.display = "none";
                  document.querySelectorAll('.interf_user')[0].style.display = "block";
                  document.querySelectorAll('.interf_bonus')[0].style.display = "none";
                  document.querySelectorAll('.interf_cagnotte')[0].style.display = "none";
                }
                else if(this.id === 'bonus_crud'){
                  document.querySelectorAll('.interf_partner')[0].style.display = "none";
                  document.querySelectorAll('.interf_user')[0].style.display = "none";
                  document.querySelectorAll('.interf_bonus')[0].style.display = "block";
                  document.querySelectorAll('.interf_cagnotte')[0].style.display = "none";
                }
                else if(this.id === 'cagnotte_crud'){
                  document.querySelectorAll('.interf_partner')[0].style.display = "none";
                  document.querySelectorAll('.interf_user')[0].style.display = "none";
                  document.querySelectorAll('.interf_bonus')[0].style.display = "none";
                  document.querySelectorAll('.interf_cagnotte')[0].style.display = "block";
                }
            });
        });


      function restoreSalle(){
        var dropdown = $('#airtime_room');
        var idcoderace = $('#idpartner2').text();

        $('>option',  $('#lockroom')).remove(); // Clean old options first.
        $('>option',  $('#sel_partner')).remove();
        $('>option',  $('#selcoderace')).remove();
        $('>option',  $('#selcoderace1')).remove();
        $('>option',  $('#turncoderace')).remove();
        $('>option',  $('#nc_rapport1')).remove();
        
        $('>option',  $('#sel_salle')).remove();
        $('>option',$('#sel1')).remove();
        $('>option',$('#ncbonus_part1')).remove();
        $('>option',$('#ncbonus_part2')).remove();
        $('>option',$('#n_cagnotte')).remove();
        $('>option', dropdown).remove();
        dropdown.append($('<option/>').val("").text('Veuillez choisir une salle'));
        $('#turncoderace').append($('<option/>').val("").text('Veuillez choisir une salle'));
        $('#nc_rapport1').append($('<option/>').val("").text('Veuillez choisir une salle'));
        $('#selcoderace1').append($('<option/>').val("").text('Veuillez choisir une salle'));
        $('#selcoderace').append($('<option/>').val("").text('Veuillez choisir une salle'));
        $.ajax({
          url:"retrievePartner",
          type:"GET",
          data:{
            'coderace':idcoderace
          },
          async: false,
          success:function(result){
            $.each(result, function(key, value){
          //   console.log('liste: '+key+' - '+value);
             dropdown.append($('<option/>').val(value).text(value));
             $('#lockroom').append($('<option/>').val(value).text(value));
             $('#selcoderace').append($('<option/>').val(value).text(value));
             $('#selcoderace1').append($('<option/>').val(value).text(value));
             $('#turncoderace').append($('<option/>').val(value).text(value));
             $('#sel_partner').append($('<option/>').val(value).text(value));
             $('#sel_salle').append($('<option/>').val(value).text(value));
             $('#sel1').append($('<option/>').val(value).text(value));
             $('#ncbonus_part1').append($('<option/>').val(value).text(value));
             $('#ncbonus_part2').append($('<option/>').val(value).text(value));
             $('#n_cagnotte').append($('<option/>').val(value).text(value));
             $('#nc_rapport1').append($('<option/>').val(value).text(value));
             
            });
          }
                });
        }
		  restoreSalle();
   
    function retrieve_user(){
      var sel = document.getElementById("airtime_room");
     
      var dropdown1 = $('#airtime_user');
      
      //alert('Index: ' + sel.selectedIndex + '\nValeur: ' + 
      //sel.options[sel.selectedIndex].value)
      var salle = sel.options[sel.selectedIndex].value;
      

      $('>option', dropdown1).remove(); // Clean old options first.

        $.ajax({
          url:"retrieveUser",
          type:"GET",
          async: false,
          data:{
						'coderace':salle
					},
          success:function(result){
            $.each(result, function(key, value){
             //console.log('liste: '+key+' - '+value);
             dropdown1.append($('<option/>').val(value).text(value));
             
            });
          }
                });
    }

    function retrieve_user2(){
      
      var _sel = document.getElementById("nc_rapport1");
     
      var dropdown2 = $('#nc_rapport');
      //alert('Index: ' + sel.selectedIndex + '\nValeur: ' + 
      //sel.options[sel.selectedIndex].value)
      var salle = _sel.options[_sel.selectedIndex].value;

     
      $('>option', dropdown2).remove(); // Clean old options first.
      dropdown2.append($('<option/>').val("").text(''));
        $.ajax({
          url:"retrieveUser",
          type:"GET",
          async: false,
          data:{
						'coderace':salle
					},
          success:function(result){
            $.each(result, function(key, value){
             //console.log('liste: '+key+' - '+value);
            
             dropdown2.append($('<option/>').val(value).text(value));
            });
          }
                });
    }

    function retrieve_user1(){
      var sel1 = document.getElementById("lockroom");
      var salle1 = sel1.options[sel1.selectedIndex].value;

      $('>option', $('#lockuser')).remove(); // Clean old options first.

        $.ajax({
          url:"retrieveUser",
          type:"GET",
          async: false,
          data:{
						'coderace':salle1
					},
          success:function(result){
            $.each(result, function(key, value){
             //console.log('liste: '+key+' - '+value);
             $('#lockuser').append($('<option/>').val(value).text(value));
            });
          }
                });
    }

    function retrieve_bonus(){
      var s1 = document.getElementById("ncbonus_part1");
      var part1 = s1.options[s1.selectedIndex].value;

      var s2 = document.getElementById("ncbonus_jeu");
      var sel2 = s2.options[s2.selectedIndex];
      if(sel2 != undefined){
          var jeu = sel2.value;
         // console.log('game: '+jeu);
        $.ajax({
          url:"retrieveBonus",
          type:"GET",
          async: false,
          data:{
            'coderace':part1
          },
          success:function(result){
            $.each(result, function(key, value){
              $('#current_bonus').empty();
              $('#current_max_bonus').empty();
              $('#current_min_bonus').empty();
            //  console.log('bonus: '+key+' - '+value);
              if (jeu != undefined && jeu == 'keno'){
               // console.log('bonus: '+key+' - '+value+' - '+result['coderace']);
                $('#current_bonus').append(result['bonusKamount']);
                $('#current_max_bonus').append(result['bnskmax']);
                $('#current_min_bonus').append(result['bnskmin']);
              }
              else if(jeu != undefined && jeu == 'bingo'){
                $('#current_bonus').append(result['bonusDamount']);
              }
              else if(jeu != undefined && jeu == 'spin'){
                $('#current_bonus').append(result['bonusPamount']);
                $('#current_max_bonus').append(result['bnspmax']);
                $('#current_min_bonus').append(result['bnspmin']);
              }
              else if(jeu != undefined && jeu == 'dog'){
                $('#current_bonus').append(result['bonusDamount']);
                $('#current_max_bonus').append(result['bnsdmax']);
                $('#current_min_bonus').append(result['bnsdmin']);
              }
            });
          }
                });}
    }
    
    //function doCob(){
    //  return confirm('COB \nMettre à jour toutes les caisses');
    //}
    function updateLogoCagnotte(){
      var s2 = document.getElementById("ncbonus_part2");
      var part2 = s2.options[s2.selectedIndex].value;
      console.log("Cagnotte Lot part2: "+part2)
    //  setTimeout(
    //    () => {
            $.ajax({
              url:"retrieveCagnotte",
              type:"GET",
              data:{
                'coderace':part2
              },
              success:function(result){
                $.each(result, function(key, value){
                    console.log("Cagnotte Lot: "+result['lot'])
                });
              }
            });
      //  }, 5000
    //  );
    }

    function validation(){
      return confirm('CREDIT:\nSalle: '+$('#airtime_room').val()+'\nCaisse: '+$('#airtime_user').val()+'\nMontant: '+$('#airtime_sum').val());
    }

    function validation1(){
      return confirm('JACKPOT:\nSalle: '+$('#ncbonus_part1').val()+'\nJeu: '+$('#ncbonus_jeu').val()+'\nBonus Min: '+$('#bonu_sum_min').val()+'\nBonus Max: '+$('#bonu_sum_max').val()+'\nRate: '+$('#ncbonus_rate').val());
    }

    function validation_turn(){
      var str = $('#iturncoderace').text();
      $('#turnchoice').val("addturnover");
      $('#icoderace').val(str);
      return confirm('REGLAGES TURNOVER:\nSalle: '+str+'\nJeu: '+$('#turn_jeu').val()+'\nPourcentage: '+$('#percentage').val()+'\nCycle: '+$('#cycle').val()+'\nFrequence: '+$('#frequence').val()+'\n'+$('#turnchoice').val());
    }
/*    $('.seeticket').click(function(){
          var str = $('#seeticket').text();
          var _str = document.getElementsByClassName("seeticket");
          console.log('seeticket'+"_"+str);
          $('#shiftModal').modal('show');
    });
*/
   // $('#tble').click(function(){
   //       alert(event.target.innerText);
  //  });

    $('#tble tr').each(function(indx, val){
      $(this).click(function(){
        var index = $(this).index();
        var value = $(this).text();
      //  alert('Row: '+index+' Value: '+value);
        var _str = document.getElementsByClassName("seeticket1");
        var barcod = _str[index].innerText;

        var s1 = document.getElementById("selcoderace1");
        var part1 = s1.options[s1.selectedIndex].value;

        console.log(part1+' '+barcod);
        //recherche des données du ticket
        $.ajax({
          url:"retrieveTicket",
          type:"GET",
          data:{
            'barcode':barcod,
            'coderace':part1
          },
          success:function(result){
            $.each(result, function(key, value){
                console.log('choix: '+result['player_choice']+key+'  '+value);

                $(".see_date").empty();
                $(".see_salle").empty();
                $(".see_cais").empty();
                $(".see_jeu").empty();
                $(".see_pari").empty();
                $(".see_choix").empty();
                $(".see_amount").empty();
                $(".see_multi").empty();
                $(".see_x").empty();
                $(".see_num").empty();
                $(".see_result").empty();
                   
                // On mets à jour les differents champs
                $(".see_date").prepend(result['date']);
                $(".see_salle").prepend(part1);
                $(".see_cais").prepend(result['user']);
                $(".see_jeu").prepend(result['game']);
                $(".see_pari").prepend(result['cparil']);
                $(".see_choix").prepend(result['player_choice']);
                $(".see_amount").prepend(result['montant']);
                result['multi'] == "0" ? $(".see_multi").prepend("Non") : $(".see_multi").prepend(result['multi']);
                $(".see_x").prepend(result['xmulti']);
                $(".see_num").prepend(result['draw_num']);
               // $(".see_result").prepend(result['draw_result']);
            });
          }
                });


        $(".modal-title").empty();
        $(".modal-title").prepend('Coupon - '+barcod);
        $('#shiftModal').modal('show');
      })
    });
   
   
    </script>
</body>
</html>