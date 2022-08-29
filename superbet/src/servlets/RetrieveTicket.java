package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import modele.Caissier;
import modele.EffChoicek;
import modele.EffChoicep;
import modele.Keno;
import modele.Misek;
import modele.Misek_temp;
import modele.Misep;
import modele.Miset;
import modele.Partner;
import modele.Spin;
import modele.Versement;
import superbetDAO.AirtimeDAO;
import superbetDAO.CaissierDAO;
import superbetDAO.DAOFactory;
import superbetDAO.EffChoicekDAO;
import superbetDAO.EffChoicepDAO;
import superbetDAO.KenoDAO;
import superbetDAO.MisekDAO;
import superbetDAO.Misek_tempDAO;
import superbetDAO.MisepDAO;
import superbetDAO.MisetDAO;
import superbetDAO.PartnerDAO;
import superbetDAO.SpinDAO;
import superbetDAO.UtilDAO;
import superbetDAO.VersementDAO;

public class RetrieveTicket extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	public static final String CONF_DAO_FACTORY = "daofactory";
	private KenoDAO kenoDao;
	private SpinDAO spinDao;
	private MisetDAO misetDao;
	private EffChoicekDAO effchoicekDao;
	private EffChoicepDAO effchoicepDao;
	private UtilDAO utilDao;
	private MisekDAO misekDao;
	private MisepDAO misepDao;
	private Misek_tempDAO misektpDao;
	private PartnerDAO partnerDao;
	private VersementDAO verstDao;
	private AirtimeDAO airtimeDao;
	private CaissierDAO caissierDao;
	private Map<String, String> drawData = new HashMap<String, String>();
	
	public Map<String, String> getDrawData() {
		return drawData;
	}
	public void setDrawData(String champ, String val){
		drawData.put(champ, val);
	}
	
	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO caissier */
			this.kenoDao = ( (DAOFactory)getServletContext().getAttribute( CONF_DAO_FACTORY )).getKenoDao();
			this.spinDao = ( (DAOFactory)getServletContext().getAttribute( CONF_DAO_FACTORY )).getSpinDao();
			this.misetDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getMisetDao();
			this.effchoicekDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getEffChoicekDao();
			this.effchoicepDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getEffChoicepDao();
			this.utilDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getUtilDao();
			this.misekDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getMisekDao();
			this.misepDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getMisepDao();
			this.misektpDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getMisektpDao();
			this.partnerDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getPartnerDao();
			this.verstDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getVersementDao();
			//this.airtimeDao =  ( (DAOFactory)getServletContext().getAttribute( CONF_DAO_FACTORY )).getAirtimeDao();
			this.caissierDao =  ( (DAOFactory)getServletContext().getAttribute( CONF_DAO_FACTORY )).getCaissierDao();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int multiplicite;
		String barcode = request.getParameter("barcode");
		String login = request.getParameter("coderace");
		Partner partn = partnerDao.find(login);
		
		System.out.println("BARCODE: "+barcode);
		
		Miset miset = misetDao.searchTicketT(barcode);
		
		if(miset != null){ //le ticket existe
			System.out.println(" MISET BARCODE: "+barcode+" "+miset);
			Versement vers = verstDao.find_vers_miset(""+miset.getIdMiset());
				
			if(vers != null){
					System.out.println("VERS BARCODE: "+barcode+" , "+vers);
					//le ticket a deja été traité
					//resultat = "Ticket deja pay�<br/>"+barcode+"<br/>"+vers.getMontant();
					//setErreurs(FIELD_CODE, resultat);        
			}
		
				//le ticket est en attente de traitement
				String typeJeu = miset.getTypeJeu();
				
				/*--- Keno traitment ---*/
				ArrayList<EffChoicek> ticket = new ArrayList<EffChoicek>();
				Misek mk = new Misek();
				
				/*--- Spin traitment ---*/
				ArrayList<EffChoicep> ticketp = new ArrayList<EffChoicep>();
				Misep mp = new Misep();
				
				switch(typeJeu)
				{
					case "K": // traitement ticket keno
						mk = misekDao.searchMisesK(""+miset.getIdMiset());
						if(mk != null){
							//on recupere les evenements du ticket
							ticket = effchoicekDao.searchTicketK(""+mk.getIdmisek());
							//Misek _mk = misekDao.searchMiseK(""+mk.getIdmisek());
							System.out.println("EFFH EVNTS: "+ticket.size());
							
							if( ticket.size() > 0 ){
								multiplicite = 1;
								boolean winner = true;
								//winT = true;
							    boolean isEnd = true;
							    int tested = 0;
							    
							    // recuperation de tous les evenement du ticket
							  //     for(int i=0;i<ticket.size();i++){
							    	   
				    		   EffChoicek tick = new EffChoicek();
				    		   ArrayList<String> resultMulti  = new ArrayList<String>();
				    		   boolean multiple = false;
				    		   int multi = 1;
				    		   int num_draw;
				    		   int draw_numK; //numero de tirage en cours
				    		   String[] str_result;
				    		   String str0="",str1="";
				    		   //String result = "<html>";
				    		   String result = "";
				    		   String single_result="";
				    		   String result_multi;
				    		   Keno k_keno = null;
				    		   boolean isDrawEnd = false; // controle si le tirage max est terminé
				    		   
				    		   //recherche du numero de tirage en cours
				    		   Keno _keno = kenoDao.find_Max_draw(login);
				    		   
				    		   draw_numK = Integer.parseInt(_keno.getDrawnumK());
				    		   if(Integer.parseInt(_keno.getStarted()) == 0){ //tirage non terminé
				    			   isDrawEnd = false;
				    		   }
				    		   else if(Integer.parseInt(_keno.getStarted()) == 1){//tirage terminé
				    			   isDrawEnd = true;
				    		   }
				    		   
				    		   Misek_temp misektp = misektpDao.find(""+mk.getIdmisek());
				    		   multi = misektp.getMulti();
				    		   multiple = multi > 1 ? true : false;
				    		   
				    		   String lib_pari = utilDao.searchPariLById(ticket.get(0).getIdparil())[3];
				    		   Caissier user = caissierDao.findById(Long.parseLong(mk.getIdCaissier()));
				    		   
				    		   setDrawData("date", mk.getDatMise());
				    		   setDrawData("barcode", barcode);
				    		   setDrawData("player_choice", ticket.get(0).getKchoice());
				    		   setDrawData("multi", ""+multi);
				    		   setDrawData("cparil", ""+lib_pari);
				    		   setDrawData("montant", mk.getSumMise());
				    		   double mtant = Double.parseDouble(ticket.get(0).getMtchoix());
				    		   mtant = (double)((int)(mtant*100))/100;
				    		   setDrawData("u_montant", ""+mtant);
				    		   setDrawData("user", user.getLoginc());
				    		   setDrawData("game", "keno");
				    		 //  setDrawData("xmulti", ""+1);
				    		   
				    		   if(multiple){
				    			    //resultMulti = new ArrayList<String>();
				    			    multiplicite = multi;
				    			    num_draw = misekDao.getNumDraw(ticket.get(0).getImisek());
				    			    
				    			    setDrawData("draw_num", ""+num_draw);
									//resultMulti.add(""+multi);
									
									int num_tirage_final = num_draw + multi - 1;
									System.out.println("DRAWNUMK: "+draw_numK+" NUM8TIRAGE: "+num_tirage_final+" BOOL: "+isDrawEnd);
								//	if(draw_numK >= num_tirage_final){
									/*   if(draw_numK <= num_tirage_final){
										   resultat = "Ticket non evalué, En cours";
										   setErreurs(FIELD_CODE, resultat);
									      return null;
									   }*/
										// recuperation des evnts dans effchoicek
										for(int i=0;i< multi;i++){
											int num = i + num_draw;
											k_keno = kenoDao.searchResultK(""+num);
											if(k_keno != null){
											  if(k_keno.getStarted().equalsIgnoreCase("1")){
												result_multi = k_keno.getDrawnumbK();
												resultMulti.add(result_multi);
											  }
											  else{
												  result_multi = "";
											      resultMulti.add(result_multi);
											  }
											  setDrawData("xmulti", ""+k_keno.getMultiplicateur());
											}
											else{
												result_multi = "";
												resultMulti.add(result_multi);
											}
										}
								//	}
								   
				    		   }
				    		   else{
				    			   
				    			   //on cherche le numero de tirage correspondant
				    			   num_draw = misekDao.getNumDraw(ticket.get(0).getImisek());
				    			   setDrawData("draw_num", ""+num_draw);
				    			  
				    			   //on cherche le resultat du tirage dans la table keno
				    			   k_keno = kenoDao.searchResultK(""+num_draw);
				    			   System.out.println("SINGLE: "+num_draw+" || "+k_keno.getStarted());
				    			   if(k_keno != null){
				    				   setDrawData("xmulti", ""+k_keno.getMultiplicateur());
				    				   if(k_keno.getStarted().equalsIgnoreCase("1")){
				    					   System.out.println("SINGLE: "+num_draw+" || "+k_keno.getStarted());
				    					   single_result = k_keno.getDrawnumbK();
				    					   str_result = k_keno.getDrawnumbK().split("-");
				    					   System.out.println("SINGLE: "+num_draw+" | "+single_result);
				    					   for(int i=0;i<str_result.length;i++){
											   if(i<10){
												   str0 = str0+" "+str_result[i];
											   }
											   else if (i>9 && i<20){
												   str1 = str1+" "+str_result[i];
											   }
										   }
				    					  // result = result + str0 + "<br/>" + str1 + "<br/>" +  "<html>";
				    					   result = result + str0 + str1;
				    					   setDrawData("draw_result", result);
				    				   }
				    				   else{
//				    					    resultat = "Ticket non evalué";
//											setErreurs(FIELD_CODE, resultat);
				    				   }  
				    			   }
				    			   
				    		   }
				    		   
				    		   // Verification du ticket
				    		   double _montant_evt = 0;//le prix d'un evenement
				    		   double gain_total = 0;
				    		   
//				    		   for(int i=0;i<ticket.size();i++ ){
//				    			   
//				    			   details_tickets = new HashMap<String, String>();
//				    			   System.out.println("_RESULT-DETAILS: "+ticket.size());
//				    			   if(multiple){
//				    				   String[] _str;
//									   String _str0 = "",_str1="";
//									   //String _result = "<html>";
//									   String _result = "";
//									   
//									   _str = resultMulti.get(i).split("-");
//									  
//									   for(int _i=0;_i<_str.length;_i++){
//										   if(_i<10){
//											   _str0 = _str0+" "+_str[_i];
//										   }
//										   else if (_i>9 && _i<20){
//											   _str1 = _str1+" "+_str[_i];
//										   }
//									   }
//									   _result = _result + _str0 + _str1;
//								    	  // _result = _result + _str0 + "<br/>" + _str1 + "<br/>" +  "<html>";
//								    	   System.out.println("_RESULT: "+_result);
//								    	   setDetailTicket("resultTour", _result);
//								    	   
//								    	   double odd = verifKeno(ticket.get(i), resultMulti.get(i));
//								    	   //setDetailTicket("cote", ""+odd);
//								    	   
//								    	   if(odd != 0 && odd != -1){
//								    		   setDetailTicket("etat", "true");
//								    		   setDetailTicket("cote", ""+odd);
//								    		   verst.setTypeVers("K");
//								    		   _montant_evt = _montant_evt + Double.parseDouble(ticket.get(i).getMtchoix());
//								    		   gain_total = gain_total + odd * Double.parseDouble(ticket.get(i).getMtchoix());
//											}
//								    	    else if(odd == 0){
//											   setDetailTicket("etat", "false");
//											   setDetailTicket("cote", ""+odd);
//											}
//											else{
//											   setDetailTicket("etat", "pending");
//											}
//								    	   
//								    	   evenements.add(details_tickets);
//								    	   
//				    			   }
//				    			   else{
//				    				   
//				    				   double odd = verifKeno(ticket.get(i), single_result);
//							    	  // setDetailTicket("cote", ""+odd);
//							    	   
//							    	   if(odd != 0 && odd != -1){
//							    		   setDetailTicket("etat", "true");
//							    		   setDetailTicket("cote", ""+odd);
//							    		   verst.setTypeVers("K");
//							    		   _montant_evt = _montant_evt + Double.parseDouble(ticket.get(i).getMtchoix());
//							    		   gain_total = gain_total + odd * Double.parseDouble(ticket.get(i).getMtchoix());
//										}
//										else if(odd == 0){
//										   setDetailTicket("etat", "false");
//										   setDetailTicket("cote", ""+odd);
//										}
//										else{
//										   setDetailTicket("etat", "pending");
//										}
//							    	   
//				    				   setDetailTicket("resultTour", result);
//				    				   evenements.add(details_tickets);
//				    			   }
//				    		   }
				    		   
//				    		   int num_tirage_final = num_draw + multi - 1;
//					    	   if(draw_numK <= num_tirage_final && multiple){
//								   resultat = "Ticket non evalué, En cours";
//								   setErreurs(FIELD_CODE, resultat);
//							      return null;
//							   }
//					    	   
//				    		   setDrawData("prix_total", ""+_montant_evt);
//				    		   setDrawData("gain_total", ""+gain_total);
//				    		   if(gain_total == 0){
//				    			   resultat = "Ticket perdant";
//				    		   }
//				    		   else{
//				    			   resultat = "Ticket gagnant";
//				    		   }
							  
							}
						}
					   break;
					case "D":
						
					   break;
					case "B":
					   
					   break;
					case "S": // traitement ticket de spin
						mp = misepDao.searchMisesP(""+miset.getIdMiset());
						if(mp != null){
							//on recupere les evenements du ticket
							ticketp = effchoicepDao.searchTicketP(""+mp.getIdmisep());
							
							System.out.println("EFFH EVNTS: "+ticketp.size());
							
							if( ticketp.size() > 0 ){
							   multiplicite = 1;
							   EffChoicep tickp = new EffChoicep();
				    		  
				    		   int multi = 1;
				    		   int num_draw;
				    		   int draw_numP; //numero de tirage en cours
				    		   String str_result;
				    		   
				    		   //String result = "<html>";
				    		   String result = "";
				    		   String single_result="";
				    		   
				    		   Spin p_spin = null;
				    		   boolean isDrawEnd = false; // controle si le tirage max est terminé
				    		   
				    		   //recherche du numero de tirage en cours
				    	
				    		   //System.out.println("PART : "+part.getCoderace()+" SPIN: "+spinDao);
				    		   Spin _spin = spinDao.find_Max_draw(login);
				    		   
				    		   draw_numP = Integer.parseInt(_spin.getDrawNumP());
				    		   
				    		   if(Integer.parseInt(_spin.getStarted()) == 0){ //tirage non terminé
				    			   isDrawEnd = false;
				    		   }
				    		   else if(Integer.parseInt(_spin.getStarted()) == 1){//tirage terminé
				    			   isDrawEnd = true;
				    		   }
				    		   
				    		   String lib_pari = utilDao.searchPariPById(ticketp.get(0).getIdparis())[3];
				    		   
				    		   setDrawData("barcode", barcode);
				    		   setDrawData("player_choice", ticketp.get(0).getPchoice());
				    		   setDrawData("multi", "1");
				    		   setDrawData("cparil", ""+lib_pari);
				    		   setDrawData("montant", mp.getSumMise());
				    		   double mtant = Double.parseDouble(ticketp.get(0).getMtchoix());
				    		   mtant = (double)((int)(mtant*100))/100;
				    		   setDrawData("u_montant", ""+mtant);
				    		   
				    		 //on cherche le numero de tirage correspondant
			    			   num_draw = misepDao.getNumDraw(ticketp.get(0).getIdmisep());
			    			   setDrawData("draw_num", ""+num_draw);
			    			  
			    			   //on cherche le resultat du tirage dans la table keno
			    			   p_spin = spinDao.searchResultP(""+num_draw);
			    			  // System.out.println("SINGLE: "+num_draw+" || "+p_spin.getStarted());
			    			   
			    			   if(p_spin != null){
			    				   if(p_spin.getStarted().equalsIgnoreCase("1")){
			    					   System.out.println("SINGLE: "+num_draw+" || "+p_spin.getStarted());
			    					   single_result = p_spin.getDrawnumbP();
			    					   //str_result = p_spin.getDrawnumbK().split("-");
			    					   System.out.println("SINGLE: "+num_draw+" | "+single_result);
			    					   
			    					  // result = result + str0 + "<br/>" + str1 + "<br/>" +  "<html>";
			    					   result = result + single_result;
			    					   setDrawData("draw_result", result);
			    				   }
			    				   else{
//			    					    resultat = "Ticket non evalué";
//										setErreurs(FIELD_CODE, resultat);
//								        return null;
			    				   }  
			    			   }
				    		   
			    			   
			    			   double _montant_evt = 0;//le prix d'un evenement
				    		   double gain_total = 0;
				    		   
//				    		   for(int i=0;i<ticketp.size();i++ ){
//				    			   
//				    			   details_tickets = new HashMap<String, String>();
//				    			   System.out.println("_RESULT-DETAILS: "+ticketp.size());
//				    			   
//				    			   double odd = verifSpin(ticketp.get(i), single_result);
//							    	  // setDetailTicket("cote", ""+odd);
//							    	   
//							    	   if(odd != 0 && odd != -1){
//							    		   setDetailTicket("etat", "true");
//							    		   setDetailTicket("cote", ""+odd);
//							    		   verst.setTypeVers("S");
//							    		   _montant_evt = _montant_evt + Double.parseDouble(ticketp.get(i).getMtchoix());
//							    		   gain_total = gain_total + odd * Double.parseDouble(ticketp.get(i).getMtchoix());
//										}
//										else if(odd == 0){
//										   setDetailTicket("etat", "false");
//										   setDetailTicket("cote", ""+odd);
//										}
//										else{
//										   setDetailTicket("etat", "pending");
//										}
//							    	   
//				    				   setDetailTicket("resultTour", result);
//				    				   evenements.add(details_tickets);
//				    		   }
				    		   
				    		   
//				    		   setDrawData("prix_total", ""+_montant_evt);
//				    		   setDrawData("gain_total", ""+gain_total);
//				    		   if(gain_total == 0){
//				    			   resultat = "Ticket perdant";
//				    		   }
//				    		   else{
//				    			   resultat = "Ticket gagnant";
//				    		   }
							}
						}
						   break;
					default:
//						resultat = "Jeu inconnu";
//						setErreurs(FIELD_CODE, resultat);
//				        return null;
						break;
				}
					
			}
			else{ //le ticket n'existe pas
//				 resultat = "Ticket inconnu";
//				 setErreurs(FIELD_CODE, resultat);
//		         return null;
			}
	
		
		
		
		
		
		
		
		
		
		
		
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");

	     String json = new Gson().toJson(drawData);
		 response.getWriter().write(json);
	      
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
