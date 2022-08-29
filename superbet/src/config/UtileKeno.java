package config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import modele.Caissier;
import modele.ControlDisplayKeno;
import modele.EffChoicek;
import modele.EffChoicep;
import modele.Keno;
import modele.Misek;
import modele.Misek_temp;
import modele.Misep;
import modele.Miset;
import superbetDAO.DAOFactory;
import superbetDAO.EffChoicekDAO;
import superbetDAO.KenoDAO;
import superbetDAO.MisekDAO;
import superbetDAO.Misek_tempDAO;
import superbetDAO.UtilDAO;

public class UtileKeno {
	
	public static double num10[] = {0,0,0,1,2,3,5,10,200,2000,10000};
	public static double num9[] = {0,0,0,1,2,3,25,100,1500,9000};
	public static double num8[] = {0,0,0,1,4,15,50,1200,8000};
	public static double num7[] = {0,0,0,1,3,30,220,3000};
	public static double num6[] = {0,0,1,2,10,60,800};
	public static double num5[] = {0,0,1,3,30,500};
	public static double num4[] = {0,0,2,10,100};
	public static double num3[] = {0,1,3,50};
	public static double num2[] = {0,1,10};
	public static double numAll[] = {0,4.1,14.5,60.5,275,1400,6500};
	public static double numOut[] = {0,1.7,2.1,2.5,3.2,4.2,5.5,7.5,10,13.5,18.5};
	public static double numSpec[] = {1.85,1.87,3.80};
	
	public static int timeKeno = 185;
	public static int _timeKeno;
	public static boolean canbet = true; //mise possible
	public static int count_free_slip = 0;
	public static String drawKeno = "";
	public static String multiplicateur = "1";
	public static int gamestate = 1;
	public static int cagnotte = 0;
	public static int drawnumk;
	public static String str_draw_combi = "";
	public static double bonuskamount = 0;
	public static String messagek = "";
	
	public static int giveBonus[] = {10000, 25000, 45000};
	public static int stepBonus = 0;
	
	public static int bonusK_amount = 0;
	public static double bonusK_down;
	public static long bonus_codeK;
	public static double bonusKmin = 0.0;
	public static double bonusrate;
	
	private static Map<String, Double> mapMin = new HashMap<String, Double>();
	private static KenoDAO kenoDao;
	private static EffChoicekDAO effchoicekDao;
	private static MisekDAO misekDao;
	private static Misek_tempDAO misektpDao;
	public static ArrayList<String> display_draw = new ArrayList<String>();
	
	public static ArrayList<ControlDisplayKeno> _display_draw;
	
    private static DAOFactory daof;
    
    public static Map<String, Integer> sOdd = new HashMap();
	
	
	public static void addDisplayDraw(String dclient){
		display_draw.add(dclient);
	}
	
	public static void removeDisplayDraw(int num){
		display_draw.remove(num);
	}
	public static ArrayList<String> getDisplay_draw() {
		return display_draw;
	}

	public static boolean checkExistingSameDisplayCoderace(String coderace){
		for(int i=0;i<display_draw.size();i++){
			if(display_draw.get(i).equalsIgnoreCase(coderace)){
				return true;
			}
		}
		return false;
	}
	
	public static int _checkExistingSameDisplayCoderace(String coderace){
		for(int i=0;i<_display_draw.size();i++){
			if(_display_draw.get(i).getCoderace().equalsIgnoreCase(coderace)){
				return i;
			}
		}
		return -1;
	}
	
 public static void verifTicket(Map<Miset, Misek> map_wait, String coderace) {
	 misekDao = daof.getInstance().getMisekDao();
	 effchoicekDao = daof.getInstance().getEffChoicekDao();
	 misektpDao = daof.getInstance().getMisektpDao();
	 kenoDao = daof.getInstance().getKenoDao();
	 daof.getInstance().getUtilDao();
		String barcode;
		Misek misek;
		Miset miset;
		for(Map.Entry mapentry : map_wait.entrySet()) {
			miset = (Miset) mapentry.getKey();
			misek = (Misek) mapentry.getValue();
			barcode = miset.getBarcode();
			//System.out.println("key: "+barcode+" | "+misek);
		//}
		if(barcode == null || barcode == ""){
			//return;
		}
		else{
			//System.out.println("BARCODE: "+barcode);
		//	if(!isTesting){
				
				
				//Miset miset = misetDao.searchTicketT(barcode);
				if(miset != null){ //le ticket existe
				
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
									int xmulti = mk.getXmulti();
									//Misek _mk = misekDao.searchMiseK(""+mk.getIdmisek());
									//System.out.println("EFFH EVNTS: "+ticket.size());
									
									if( ticket.size() > 0 ){
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
						    		   String str0="",str1="";
						    		   String single_result="";
						    		   String result_multi;
						    		   double xtiplicateur = 1;
						    		   Keno k_keno = null;
						    		   //recherche du numero de tirage en cours
						    		  // Partner part = partnerDao.findById(Integer.parseInt(""+caissier.getPartner()));
						    		   Keno _keno = kenoDao.find_Max_draw(coderace);
						    		   
						    		   draw_numK = Integer.parseInt(_keno.getDrawnumK());
						    		   if(Integer.parseInt(_keno.getStarted()) == 0){ //tirage non terminé
						    			   
						    		   }
						    		   else if(Integer.parseInt(_keno.getStarted()) == 1){//tirage terminé
						    			   
						    		   }
						    		   
						    		   Misek_temp misektp = misektpDao.find(""+mk.getIdmisek());
						    		   multi = misektp.getMulti();
						    		   multiple = multi > 1 ? true : false;
						    		   
						    		   if(multiple){
						    			    num_draw = misekDao.getNumDraw(ticket.get(0).getImisek());
						    			    
						    			 //   setDrawData("draw_num", ""+num_draw);
											//resultMulti.add(""+multi);
											
											int num_tirage_final = num_draw + multi - 1;
											//System.out.println("DRAWNUMK: "+draw_numK+" NUM8TIRAGE: "+num_tirage_final+" BOOL: "+isDrawEnd);
											
									    	   if(draw_numK <= num_tirage_final){
												  
											      return;
											   }
												// recuperation des evnts dans effchoicek
												for(int i=0;i< multi;i++){
													int num = i + num_draw;
													k_keno = kenoDao.searchResultK(""+num);
													if(k_keno != null){
													xtiplicateur = Double.parseDouble(k_keno.getMultiplicateur());
													  if(k_keno.getStarted().equalsIgnoreCase("1")){
														result_multi = k_keno.getDrawnumbK();
														resultMulti.add(result_multi);
													  }
													  else{
														  result_multi = "";
													      resultMulti.add(result_multi);
													  }
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
						    		//	   setDrawData("draw_num", ""+num_draw);
						    			  
						    			   //on cherche le resultat du tirage dans la table keno
						    			   k_keno = kenoDao.searchResultK(""+num_draw);
						    			//   System.out.println("SINGLE: "+num_draw+" || "+k_keno.getStarted());
						    			   if(k_keno != null){
						    				   xtiplicateur = Double.parseDouble(k_keno.getMultiplicateur());
						    				   if(k_keno.getStarted().equalsIgnoreCase("1")){
						    					  // System.out.println("SINGLE: "+num_draw+" || "+k_keno.getStarted());
						    					   single_result = k_keno.getDrawnumbK();
						    					   
						    					  // setDrawData("draw_result", result);
						    				   }
						    				   else{
						    					
											        //return false;
						    				   }  
						    			   }
						    			   
						    		   }
						    		   
						    		   // Verification du ticket
						    		   double _montant_evt = 0;//le prix d'un evenement
						    		   double gain_total = 0;
						    		   
						    		   for(int i=0;i<ticket.size();i++ ){
						    			   
						    			 
						    			//   System.out.println("_RESULT-DETAILS: "+ticket.size());
						    			   if(multiple){
						    				   String[] _str;
											   String _str0 = "",_str1="";
											   //String _result = "<html>";
											   String _result = "";
											   
											   _str = resultMulti.get(i).split("-");
											  
//											   for(int _i=0;_i<_str.length;_i++){
//												   if(_i<10){
//													   _str0 = _str0+" "+_str[_i];
//												   }
//												   else if (_i>9 && _i<20){
//													   _str1 = _str1+" "+_str[_i];
//												   }
//											   }
//											   _result = _result + _str0 + _str1;
										   	  
										    	   
										    	   double odd = verifKeno(ticket.get(i), resultMulti.get(i));
										    	   //setDetailTicket("cote", ""+odd);
										    	   
										    	   if(odd != 0 && odd != -1){
//										    		   setDetailTicket("etat", "true");
//										    		   setDetailTicket("cote", ""+odd);
//										    		   verst.setTypeVers("K");
										    		   _montant_evt = _montant_evt + Double.parseDouble(ticket.get(i).getMtchoix());
										    		   gain_total = gain_total + odd * Double.parseDouble(ticket.get(i).getMtchoix());
//										    		   misek.setEtatMise("gagnant");
//										    		   misek.setSumWin(gain_total);
										    		   if(xmulti != 0) {
										    			   gain_total = gain_total * xtiplicateur;
										    		   }
										    		   gain_total = (double)((int)(gain_total*100))/100;
										    		  // return true;
													}
										    	    else if(odd == 0){
													  
//													   misek.setEtatMise("perdant");
//										    		   misek.setSumWin(gain_total);
													   //return false;
													}
//													else{
//													   setDetailTicket("etat", "pending");
//													}
//										    	   
										    	  
										    	   
						    			   }
						    			   else{
						    				   
						    				   double odd = verifKeno(ticket.get(i), single_result);
									    	  // setDetailTicket("cote", ""+odd);
									    	   
									    	   if(odd != 0 && odd != -1){
//									    		   setDetailTicket("etat", "true");
//									    		   setDetailTicket("cote", ""+odd);
//									    		   verst.setTypeVers("K");
									    		   _montant_evt = _montant_evt + Double.parseDouble(ticket.get(i).getMtchoix());
									    		   gain_total = gain_total + odd * Double.parseDouble(ticket.get(i).getMtchoix());
									    		   if(xmulti != 0) {
									    			   gain_total = gain_total * xtiplicateur;
									    		   }
									    		   gain_total = (double)((int)(gain_total*100))/100;
//									    		   misek.setEtatMise("gagnant");
//									    		   misek.setSumWin(gain_total);
												}
												else if(odd == 0){
												  
//												   misek.setEtatMise("perdant");
//									    		   misek.setSumWin(gain_total);
												}
												
						    				  
						    			   }
						    		   }
						    		   
//						    		   int num_tirage_final = num_draw + multi - 1;
//							    	   if(draw_numK <= num_tirage_final && multiple){
//										   resultat = "Ticket non evalué, En cours";
//										   setErreurs(FIELD_CODE, resultat);
//									      return false;
//									   }
//							    	   
//						    		   setDrawData("prix_total", ""+_montant_evt);
//						    		   setDrawData("gain_total", ""+gain_total);
						    		   if(gain_total == 0){
						    			   misek.setEtatMise("perdant");
							    		   misek.setSumWin(gain_total);
						    		   }
						    		   else{
						    			   misek.setEtatMise("gagnant");
							    		   misek.setSumWin(gain_total);
						    		   }
									  
									}
								}
							   break;
							default:
								break;
						       
						}
						
		//			}
				}
				
		//	}
		}
	  }
		
	  //mise � jour de letat des tickets en bd
		for(Map.Entry mapentry : map_wait.entrySet()) {
			miset = (Miset) mapentry.getKey();
			misek = (Misek) mapentry.getValue();
			barcode = miset.getBarcode();
			System.out.println("key: "+barcode+" | "+misek);
			
			misekDao.update(misek);
		}
	}
 
 public static double verifTicketSum(Map<Miset, Misek> map_wait, String coderace, String result, String multix) {
	 misekDao = daof.getInstance().getMisekDao();
	 effchoicekDao = daof.getInstance().getEffChoicekDao();
	 misektpDao = daof.getInstance().getMisektpDao();
	 kenoDao = daof.getInstance().getKenoDao();
	 daof.getInstance().getUtilDao();
	 double _montant_evt = 0;//le prix d'un evenement
	 double gain_total = 0;
	 double multiplicateur = Double.parseDouble(multix);
		String barcode;
		Misek misek;
		Miset miset;
		//System.out.println("MAP SIZE: "+map_wait.size()+" XM: "+multiplicateur);
		for(Map.Entry mapentry : map_wait.entrySet()) {
			miset = (Miset) mapentry.getKey();
			misek = (Misek) mapentry.getValue();
			barcode = miset.getBarcode();
			//System.out.println("key: "+barcode+" | "+misek);
		//}
				//Miset miset = misetDao.searchTicketT(barcode);
				if(miset != null){ //le ticket existe
				
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
									//ticket = effchoicekDao.searchTicketK(""+mk.getIdmisek());
									ticket = effchoicekDao.searchTicketK(""+mk.getIdmisek(), ""+mk.getIdKeno());
									int xmulti = mk.getXmulti();
									//Misek _mk = misekDao.searchMiseK(""+mk.getIdmisek());
									//System.out.println("EFFH EVNTS: "+ticket.size());
									
									if( ticket.size() > 0 ){
										boolean winner = true;
										//winT = true;
									    boolean isEnd = true;
									    int tested = 0;
									    
									    // recuperation de tous les evenement du ticket
									  //     for(int i=0;i<ticket.size();i++){
									    	   
						    		   boolean multiple = false;
						    		   int multi = 1;
						    		   String single_result="";
						    		  
						    		   Misek_temp misektp = misektpDao.find(""+mk.getIdmisek());
						    		   multi = misektp.getMulti();
						    		   multiple = multi > 1 ? true : false;
						    		   
//						    		   if(multiple){
						    			    //resultMulti = new ArrayList<String>();
//						    			    multiplicite = multi;
//						    			    num_draw = misekDao.getNumDraw(ticket.get(0).getIdmisek());
//						    			    
//						    			 	// recuperation des evnts dans effchoicek
//												for(int i=0;i< multi;i++){
//													int num = i + num_draw;
//													k_keno = kenoDao.searchResultK(""+num);
//													if(k_keno != null){
//													xtiplicateur = Double.parseDouble(k_keno.getMultiplicateur());
//													  if(k_keno.getStarted().equalsIgnoreCase("1")){
//														result_multi = k_keno.getDrawnumbK();
//														resultMulti.add(result_multi);
//													  }
//													  else{
//														  result_multi = "";
//													      resultMulti.add(result_multi);
//													  }
//													}
//													else{
//														result_multi = "";
//														resultMulti.add(result_multi);
//													}
//												}
//										//	}
										   
//						    		   }
//						    		   else{
//						    			   
//						    			  
//						    			   
//						    		   }
						    		   
						    		   // Verification du ticket
						    		   
						    		   for(int i=0;i<ticket.size();i++ ){
						    			   double gain = 0;
						    			   _montant_evt = 0;
						    			//   System.out.println("_RESULT-DETAILS: "+ticket.size());
//						    			   if(multiple){
						    				   double odd = verifKeno(ticket.get(i), result);
										    	   
										    	   if(odd != 0 && odd != -1){
//										    		   
										    		   _montant_evt = _montant_evt + Double.parseDouble(ticket.get(i).getMtchoix());
										    		   gain = gain + odd * Double.parseDouble(ticket.get(i).getMtchoix());
//										    		   
										    		   if(xmulti != 0) {
										    			   gain = gain * multiplicateur;
										    		   }
										    		   gain = (double)((int)(gain*100))/100;
										    		  // return true;
													}
										    	   
//						    			   }
//						    			   else{
//						    				   
//						    				   double odd = verifKeno(ticket.get(i), result);
//									    	   
//									    	   if(odd != 0 && odd != -1){
////									    		   
//									    		   _montant_evt = _montant_evt + Double.parseDouble(ticket.get(i).getMtchoix());
//									    		   gain = gain + odd * Double.parseDouble(ticket.get(i).getMtchoix());
//									    		   if(xmulti != 0) {
//									    			   gain = gain * multiplicateur;
//									    		   }
//									    		   gain = (double)((int)(gain*100))/100;
////									    		  
//												}
//									    	 //  System.out.println("Gain "+gain+" _montant_evt: "+_montant_evt);
//						    			   }
						    			   
						    			   gain_total = gain_total + gain;
						    		   }
						    		   
									}
								}
							   break;
							default:
								break;
						       
						}
					
				}
	  }
		
	  return gain_total;
	}
 
 
 public static double verifKeno(EffChoicek effchoicek, String result){
	String[] stand; // recupere le resultat de la course
	
	stand = result.split("-");
	return checkTicketK(effchoicek, stand);
}
 private static double checkTicketK(EffChoicek t, String[] draw){
	
	int nbD = 0, sum = 0;
	int num;
	double cote = 0,rd;
	int pari;
	String[] choice = {""};
	
	if(draw.length < 20){
		return -1;
	}
	
	pari = Integer.parseInt(t.getIdparil());
	
	if(pari < 12)
		choice = t.getKchoice().split("-");
	//	rd = Double.parseDouble(t.getMtchoix());
	
	switch(pari){
		case 1:
			for(int n=0;n<choice.length;n++){
				for(int in=0;in<draw.length;in++){
					if(choice[n].equalsIgnoreCase(draw[in])){
						nbD++;
						break;
					}
				}
			}
			cote = UtileKeno.num10[nbD];
			break;
		case 2:
			for(int n=0;n<choice.length;n++){
				for(int in=0;in<draw.length;in++){
					if(choice[n].equalsIgnoreCase(draw[in])){
						nbD++;
						break;
					}
				}
			}
			cote = UtileKeno.num9[nbD];
			break;
		case 3:
			for(int n=0;n<choice.length;n++){
				for(int in=0;in<draw.length;in++){
					if(choice[n].equalsIgnoreCase(draw[in])){
						nbD++;
						break;
					}
				}
			}
			cote = UtileKeno.num8[nbD];
			break;
		case 4:
			for(int n=0;n<choice.length;n++){
				for(int in=0;in<draw.length;in++){
					if(choice[n].equalsIgnoreCase(draw[in])){
						nbD++;
						break;
					}
				}
			}
			cote = UtileKeno.num7[nbD];
			break;
		case 5:
			for(int n=0;n<choice.length;n++){
				for(int in=0;in<draw.length;in++){
					if(choice[n].equalsIgnoreCase(draw[in])){
						nbD++;
						break;
					}
				}
			}
			cote = UtileKeno.num6[nbD];
			break;
		case 6:
			for(int n=0;n<choice.length;n++){
				for(int in=0;in<draw.length;in++){
					if(choice[n].equalsIgnoreCase(draw[in])){
						nbD++;
						break;
					}
				}
			}
			cote = UtileKeno.num5[nbD];
			break;
		case 7:
			for(int n=0;n<choice.length;n++){
				for(int in=0;in<draw.length;in++){
					if(choice[n].equalsIgnoreCase(draw[in])){
						nbD++;
						break;
					}
				}
			}
			cote = UtileKeno.num4[nbD];
			break;
		case 8:
			for(int n=0;n<choice.length;n++){
				for(int in=0;in<draw.length;in++){
					if(choice[n].equalsIgnoreCase(draw[in])){
						nbD++;
						break;
					}
				}
			}
			cote = UtileKeno.num3[nbD];
			break;
		case 9:
			for(int n=0;n<choice.length;n++){
				for(int in=0;in<draw.length;in++){
					if(choice[n].equalsIgnoreCase(draw[in])){
						nbD++;
						break;
					}
				}
			}
			cote = UtileKeno.num2[nbD];
			break;
		case 10:
			boolean trouv = true;
			for(int n=0;n<choice.length;n++){
				for(int in=0;in<draw.length;in++){
					if(choice[n].equalsIgnoreCase(draw[in])){
						trouv = false;
						break;
					}
				}
				
				if(!trouv){
					break;
				}
				else{
					nbD++;
				}
			}
			
			if(trouv){
				cote = UtileKeno.numOut[nbD];
			}
			else{
				cote = 0;
			}
			break;
		case 11:
			boolean truv = false;
			for(int n=0;n<choice.length;n++){
				for(int in=0;in<draw.length;in++){
					if(choice[n].equalsIgnoreCase(draw[in])){
						nbD++;
						truv = true;
						break;
					}
					else{
						truv = false;
					}
				}
				
				if(!truv){
					break;
				}
			}
			
			if(!truv){
				cote = 0;
			}
			else{
				cote = UtileKeno.numAll[nbD];
			}
			
			break;
		case 12:
			num = Integer.parseInt(draw[0]);
			if(num % 2 == 0)
				cote = UtileKeno.numSpec[1];
			break;
		case 13:
			num = Integer.parseInt(draw[0]);
			if(num % 2 != 0)
				cote = UtileKeno.numSpec[1];
			break;
		case 14:
			num = Integer.parseInt(draw[19]);
			if(num % 2 == 0)
				cote = UtileKeno.numSpec[1];
			break;
		case 15:
			num = Integer.parseInt(draw[19]);
			if(num % 2 != 0)
				cote = UtileKeno.numSpec[1];
			break;
		case 16:
			for(int i=0;i<5;i++)
				sum = sum + Integer.parseInt(draw[i]);
			if(sum < 203 )
				cote = UtileKeno.numSpec[1];
			else
				cote = 0;
			break;
		case 17:
			for(int i=0;i<5;i++)
				sum = sum + Integer.parseInt(draw[i]);
			if(sum > 202 )
				cote = UtileKeno.numSpec[1];
			else
				cote = 0;
			break;
		case 18:
			for(int i=0;i<20;i++)
				sum = sum + Integer.parseInt(draw[i]);
			if(sum < 806 )
				cote = UtileKeno.numSpec[1];
			else
				cote = 0;
			break;
		case 19:
			for(int i=0;i<20;i++)
				sum = sum + Integer.parseInt(draw[i]);
			if(sum > 805 )
				cote = UtileKeno.numSpec[1];
			else
				cote = 0;
			break;
		case 20://1ere couleur verte
			num = Integer.parseInt(draw[0]);
			if(0 < num  && num < 21)
				cote = UtileKeno.numSpec[2];
		    break;
		case 21://couleur bleu
			num = Integer.parseInt(draw[0]);
			if(20 < num  && num < 41)
				cote = UtileKeno.numSpec[2];
			break;
		case 22://couleur rouge
			num = Integer.parseInt(draw[0]);
			if(40 < num  && num < 61)
				cote = UtileKeno.numSpec[2];
			break;
		case 23://couleur orange
			num = Integer.parseInt(draw[0]);
			if(60 < num  && num < 81)
				cote = UtileKeno.numSpec[2];
			break;
		case 24://derniere couleur verte
			num = Integer.parseInt(draw[19]);
			if(0 < num  && num < 21)
				cote = UtileKeno.numSpec[2];
			break;
		case 25://derniere couleur bleu
			num = Integer.parseInt(draw[19]);
			if(20 < num  && num < 41)
				cote = UtileKeno.numSpec[2];
			break;
		case 26://derniere couleur rouge
			num = Integer.parseInt(draw[19]);
			if(40 < num  && num < 61)
				cote = UtileKeno.numSpec[2];
			break;
		case 27://derniere couleur orange
			num = Integer.parseInt(draw[19]);
			if(60 < num  && num < 81)
				cote = UtileKeno.numSpec[2];
			break;
		default:
			break;
		}
		
		return cote;
	}
 
 	public static double gainMaxP() {
 		double gain;
 		gain = 0;
 		
 		return gain;
 	}
 	private static double checkTicketKMax(EffChoicek t){
 		
 		int nbD = 0, sum = 0;
 		int num;
 		double cote = 0,rd;
 		int pari;
 		String[] choice = {""};
 		
 		pari = Integer.parseInt(t.getIdparil());
 		
 		if(pari < 12)
 			choice = t.getKchoice().split("-");
 		//	rd = Double.parseDouble(t.getMtchoix());
 		
 		switch(pari){
 			case 1:
 				cote = UtileKeno.num10[10];
 				break;
 			case 2:
 				cote = UtileKeno.num9[9];
 				break;
 			case 3:
 				cote = UtileKeno.num8[8];
 				break;
 			case 4:
 				cote = UtileKeno.num7[7];
 				break;
 			case 5:
 				cote = UtileKeno.num6[6];
 				break;
 			case 6:
 				cote = UtileKeno.num5[5];
 				break;
 			case 7:
 				cote = UtileKeno.num4[4];
 				break;
 			case 8:
 				cote = UtileKeno.num3[3];
 				break;
 			case 9:
 				cote = UtileKeno.num2[2];
 				break;
 			case 10:
 					cote = UtileKeno.numOut[choice.length];
 				break;
 			case 11:
 					cote = UtileKeno.numAll[choice.length];
 				break;
 			case 12:
 					cote = UtileKeno.numSpec[1];
 				break;
 			case 13:
 					cote = UtileKeno.numSpec[1];
 				break;
 			case 14:
 					cote = UtileKeno.numSpec[1];
 				break;
 			case 15:
 					cote = UtileKeno.numSpec[1];
 				break;
 			case 16:
 					cote = UtileKeno.numSpec[1];
 				break;
 			case 17:
 					cote = UtileKeno.numSpec[1];
 				break;
 			case 18:
 					cote = UtileKeno.numSpec[1];
 				break;
 			case 19:
 					cote = UtileKeno.numSpec[1];
 				break;
 			case 20://1ere couleur verte
 					cote = UtileKeno.numSpec[2];
 			    break;
 			case 21://couleur bleu
 					cote = UtileKeno.numSpec[2];
 				break;
 			case 22://couleur rouge
 					cote = UtileKeno.numSpec[2];
 				break;
 			case 23://couleur orange
 					cote = UtileKeno.numSpec[2];
 				break;
 			case 24://derniere couleur verte
 					cote = UtileKeno.numSpec[2];
 				break;
 			case 25://derniere couleur bleu
 					cote = UtileKeno.numSpec[2];
 				break;
 			case 26://derniere couleur rouge
 					cote = UtileKeno.numSpec[2];
 				break;
 			case 27://derniere couleur orange
 					cote = UtileKeno.numSpec[2];
 				break;
 			default:
 				break;
 			}
 			
 			return cote;
 		}
 	
 	@SuppressWarnings("rawtypes")
	public static double verifTicketMax(Map<Miset, Misek> map_wait, String coderace) {
 		 misekDao = daof.getInstance().getMisekDao();
 		 effchoicekDao = daof.getInstance().getEffChoicekDao();
 		 misektpDao = daof.getInstance().getMisektpDao();
 		 kenoDao = daof.getInstance().getKenoDao();
 		 daof.getInstance().getUtilDao();
 			String barcode;
 			Miset miset;
 			double _montant_evt = 0;//le prix d'un evenement
  		   double gain_total = 0;
 			for(Map.Entry mapentry : map_wait.entrySet()) {
 				miset = (Miset) mapentry.getKey();
 				barcode = miset.getBarcode();
 				//System.out.println("key: "+barcode+" | "+misek);
 			//}
 			if(barcode == null || barcode == ""){
 				//return;
 			}
 			else{
 				//System.out.println("BARCODE: "+barcode);
 			//	if(!isTesting){
 					
 					
 					//Miset miset = misetDao.searchTicketT(barcode);
 					if(miset != null){ //le ticket existe
 					
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
 										//ticket = effchoicekDao.searchTicketK(""+mk.getIdmisek());
 										ticket = effchoicekDao.searchTicketK(""+mk.getIdmisek(), ""+mk.getIdKeno());
 										int xmulti = mk.getXmulti();
 										//Misek _mk = misekDao.searchMiseK(""+mk.getIdmisek());
 										//System.out.println("EFFH EVNTS: "+ticket.size());
 										
 										if( ticket.size() > 0 ){
 											
 											
 										    
 										    // recuperation de tous les evenement du ticket
 											   
 							    		   ArrayList<String> resultMulti  = new ArrayList<String>();
 							    		   boolean multiple = false;
 							    		   int multi = 1;
 							    		   double xtiplicateur = 1;
 							    		   Misek_temp misektp = misektpDao.find(""+mk.getIdmisek());
 							    		   multi = misektp.getMulti();
 							    		   multiple = multi > 1 ? true : false;
 							    		   
 							    		   
 							    		   // Verification du ticket
 							    		
 							    		   
 							    		   for(int i=0;i<ticket.size();i++ ){
 							    			   
 							    			 
 							    			//   System.out.println("_RESULT-DETAILS: "+ticket.size());
// 							    			   if(multiple){
 											    	   double odd = checkTicketKMax(ticket.get(i));
 											    	   //setDetailTicket("cote", ""+odd);
 											    	   
 											    	   if(odd != 0 && odd != -1){
 											    		   _montant_evt = _montant_evt + Double.parseDouble(ticket.get(i).getMtchoix());
 											    		   gain_total = gain_total + odd * Double.parseDouble(ticket.get(i).getMtchoix());
// 						
 											    		   if(xmulti != 0) {
 											    			   gain_total = gain_total * xtiplicateur;
 											    		   }
 											    		   gain_total = (double)((int)(gain_total*100))/100;
 											    		  // return true;
 														}
// 							    			   }
// 							    			   else{
// 							    				   
// 							    				   double odd = checkTicketKMax(ticket.get(i));
// 										    	  // setDetailTicket("cote", ""+odd);
// 										    	   
// 										    	   if(odd != 0 && odd != -1){
//// 										    		   
// 										    		   _montant_evt = _montant_evt + Double.parseDouble(ticket.get(i).getMtchoix());
// 										    		   gain_total = gain_total + odd * Double.parseDouble(ticket.get(i).getMtchoix());
// 										    		   if(xmulti != 0) {
// 										    			   gain_total = gain_total * xtiplicateur;
// 										    		   }
// 										    		   gain_total = (double)((int)(gain_total*100))/100;
//// 										    		   
// 													}
// 												
// 							    			   }
 							    		   }
 							    		   
 										}
 									}
 								   break;
 								default:
 									break;
 							       
 							}
 							
 			//			}
 					}
 					
 			//	}
 			}
 		  }
 			
 			return gain_total;
 		}
 	
 	public static double verifTicketMin(Map<Miset, Misek> map_wait, String coderace) {
 		
 	   mapMin.put("pair", 0.0);
 	   mapMin.put("ipair", 0.0);
 	   mapMin.put("pair20", 0.0);
 	   mapMin.put("ipair20", 0.0);
 	   mapMin.put("sum5", 0.0);
 	   mapMin.put("isum5", 0.0);
 	   mapMin.put("sum20", 0.0);
 	   mapMin.put("isum20", 0.0);
 	   mapMin.put("bleu", 0.0);
 	   mapMin.put("rouge", 0.0);
 	   mapMin.put("vert", 0.0);
 	   mapMin.put("orange", 0.0);
 	   mapMin.put("bleu20", 0.0);
	   mapMin.put("rouge20", 0.0);
	   mapMin.put("vert20", 0.0);
	   mapMin.put("orange20", 0.0);
	   mapMin.put("isum40", 0.0);
 	   mapMin.put("sum40", 0.0);
 	   
 	    
		 misekDao = daof.getInstance().getMisekDao();
		 effchoicekDao = daof.getInstance().getEffChoicekDao();
		 misektpDao = daof.getInstance().getMisektpDao();
		 kenoDao = daof.getInstance().getKenoDao();
		 daof.getInstance().getUtilDao();
			String barcode;
			Misek misek;
			Miset miset;
			double _montant_evt = 0;//le prix d'un evenement
 		   double gain_total = 0;
			for(Map.Entry mapentry : map_wait.entrySet()) {
				miset = (Miset) mapentry.getKey();
				misek = (Misek) mapentry.getValue();
				barcode = miset.getBarcode();
				//System.out.println("key: "+barcode+" | "+misek);
			//}
			if(barcode == null || barcode == ""){
				//return;
			}
			else{
				//System.out.println("BARCODE: "+barcode);
			//	if(!isTesting){
					
					
					//Miset miset = misetDao.searchTicketT(barcode);
					if(miset != null){ //le ticket existe
					
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
										//ticket = effchoicekDao.searchTicketK(""+mk.getIdmisek());
										ticket = effchoicekDao.searchTicketK(""+mk.getIdmisek(), ""+mk.getIdKeno());
										int xmulti = mk.getXmulti();
										//Misek _mk = misekDao.searchMiseK(""+mk.getIdmisek());
										//System.out.println("EFFH EVNTS: "+ticket.size());
										
										if( ticket.size() > 0 ){
											
											
										    
										    // recuperation de tous les evenement du ticket
											   
							    		   ArrayList<String> resultMulti  = new ArrayList<String>();
							    		   boolean multiple = false;
							    		   int multi = 1;
							    		   double xtiplicateur = 1;
							    		   Misek_temp misektp = misektpDao.find(""+mk.getIdmisek());
							    		   multi = misektp.getMulti();
							    		   multiple = multi > 1 ? true : false;
							    		   
							    		   
							    		   // Verification du ticket
							    		   int bet;
							    		   double gain;
							    		   for(int i=0;i<ticket.size();i++ ){
							    			   bet = Integer.parseInt(ticket.get(i).getIdparil());
							    			 //   System.out.println("_RESULT-DETAILS: "+ticket.size());
//							    			   if(multiple){
							    				       
											    	   double odd = checkTicketKMin(ticket.get(i));
											    	   
											    	   //if(bet>11) {
											    		   
											    	   //}
											    	   
											    	   if(odd != 0 && odd != -1){
//											    		   _montant_evt = _montant_evt + Double.parseDouble(ticket.get(i).getMtchoix());
//											    		   gain_total = gain_total + odd * Double.parseDouble(ticket.get(i).getMtchoix());
//											    		   gain_total = (double)((int)(gain_total*100))/100;
											    		   gain = odd * Double.parseDouble(ticket.get(i).getMtchoix());
											    		   gain = (double)((int)(gain*100))/100;
											    		   twoSideKnife(bet, gain);
														}
//							    			   }
//							    			   else{
//							    				   
//							    				   double odd = checkTicketKMin(ticket.get(i));
//										    	  // setDetailTicket("cote", ""+odd);
//										    	   
//										    	   if(odd != 0 && odd != -1){
////										    		   
////										    		   _montant_evt = _montant_evt + Double.parseDouble(ticket.get(i).getMtchoix());
////										    		   gain_total = gain_total + odd * Double.parseDouble(ticket.get(i).getMtchoix());
////										    		   gain_total = (double)((int)(gain_total*100))/100;
//										    		   gain = odd * Double.parseDouble(ticket.get(i).getMtchoix());
//										    		   gain = (double)((int)(gain*100))/100;
//										    		   twoSideKnife(bet, gain);
////										    		   
//													}
//												
//							    			   }
							    		   }
							    		   
										}
									}
								   break;
								default:
									break;
							       
							}
							
			//			}
					}
					
			//	}
			}
		  }
			gain_total = twoSideKnifeSum();
			return gain_total;
		}
 	
  private static double checkTicketKMin(EffChoicek t){
	  
	    double cote = 0;
 		int pari;
 		pari = Integer.parseInt(t.getIdparil());
 		
 		if(pari < 12) {
 			cote = 0;
 		}
 		
 		//	rd = Double.parseDouble(t.getMtchoix());
 		
 		switch(pari){
 			
 			case 12:
 					cote = UtileKeno.numSpec[1];
 				break;
 			case 13:
 					cote = UtileKeno.numSpec[1];
 				break;
 			case 14:
 					cote = UtileKeno.numSpec[1];
 				break;
 			case 15:
 					cote = UtileKeno.numSpec[1];
 				break;
 			case 16:
 					cote = UtileKeno.numSpec[1];
 				break;
 			case 17:
 					cote = UtileKeno.numSpec[1];
 				break;
 			case 18:
 					cote = UtileKeno.numSpec[1];
 				break;
 			case 19:
 					cote = UtileKeno.numSpec[1];
 				break;
 			case 20://1ere couleur verte
 					cote = UtileKeno.numSpec[2];
 			    break;
 			case 21://couleur bleu
 					cote = UtileKeno.numSpec[2];
 				break;
 			case 22://couleur rouge
 					cote = UtileKeno.numSpec[2];
 				break;
 			case 23://couleur orange
 					cote = UtileKeno.numSpec[2];
 				break;
 			case 24://derniere couleur verte
 					cote = UtileKeno.numSpec[2];
 				break;
 			case 25://derniere couleur bleu
 					cote = UtileKeno.numSpec[2];
 				break;
 			case 26://derniere couleur rouge
 					cote = UtileKeno.numSpec[2];
 				break;
 			case 27://derniere couleur orange
 					cote = UtileKeno.numSpec[2];
 				break;
 			default:
 				break;
 			}
 			
 			return cote;
 		}
  
  	 private static void twoSideKnife(int pari, double sum) {
  		 switch(pari) {
  		 	case 12:
  		 		mapMin.replace("pair", sum + mapMin.get("pair"));
  		 		break;
  		 	case 13:
  		 		mapMin.replace("ipair", sum + mapMin.get("ipair"));
  		 		break;
  		 	case 14:
  		 		mapMin.replace("pair20", sum + mapMin.get("pair20"));
  		 		break;
  		 	case 15:
  		 		mapMin.replace("ipair20", sum + mapMin.get("ipair20"));
  		 		break;
  		 	case 16:
  		 		mapMin.replace("sum5", sum + mapMin.get("sum5"));
  		 		break;
  		 	case 17:
  		 		mapMin.replace("isum5", sum + mapMin.get("isum5"));
  		 		break;
  		 	case 18:
  		 		mapMin.replace("sum20", sum + mapMin.get("sum20"));
  		 		break;
  		 	case 19:
  		 		mapMin.replace("isum20", sum + mapMin.get("isum20"));
  		 		break;
  		 	case 20:
  		 		mapMin.replace("vert", sum + mapMin.get("vert"));
  		 		break;
  		 	case 21:
  		 		mapMin.replace("bleu", sum + mapMin.get("bleu"));
  		 		break;
  		 	case 22:
  		 		mapMin.replace("rouge", sum + mapMin.get("rouge"));
  		 		break;
  		 	case 23:
  		 		mapMin.replace("orange", sum + mapMin.get("orange"));
  		 		break;
  		 	case 24:
  		 		mapMin.replace("vert20", sum + mapMin.get("vert20"));
  		 		break;
  		 	case 25:
  		 		mapMin.replace("bleu20", sum + mapMin.get("bleu20"));
  		 		break;
  		 	case 26:
  		 		mapMin.replace("rouge20", sum + mapMin.get("rouge20"));
  		 		break;
  		 	case 27:
  		 		mapMin.replace("orange20", sum + mapMin.get("orange20"));
  		 		break;
  		 	case 28:
  		 		mapMin.replace("sum40", sum + mapMin.get("sum40"));
  		 		break;
  		 	case 29:
  		 		mapMin.replace("isum40", sum + mapMin.get("isum40"));
  		 		break;
  		 }
  	 }
  	 
  	private static double twoSideKnifeSum() {
  		double sum = 0;
  		sum = sum + Math.min(mapMin.get("pair"), mapMin.get("ipair"));
  		sum = sum + Math.min(mapMin.get("pair20"), mapMin.get("ipair20"));
  		sum = sum + Math.min(mapMin.get("sum5"), mapMin.get("isum5"));
  		sum = sum + Math.min(mapMin.get("sum20"), mapMin.get("isum20"));
  		sum = sum + Math.min(Math.min(mapMin.get("vert"), mapMin.get("bleu")), Math.min(mapMin.get("rouge"), mapMin.get("orange")));
  		sum = sum + Math.min(Math.min(mapMin.get("vert20"), mapMin.get("bleu20")), Math.min(mapMin.get("rouge20"), mapMin.get("orange20")));
  		sum = sum + Math.min(mapMin.get("sum40"), mapMin.get("isum40"));
  		return sum;
  	}
	
}
