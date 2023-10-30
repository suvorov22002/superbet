package business;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.codehaus.jettison.json.JSONException;

import modele.Airtime;
import modele.BetTicketK;
import modele.Caissier;
import modele.Config;
import modele.Coupon;
import modele.EffChoicek;
import modele.Keno;
import modele.Misek;
import modele.Misek_temp;
import modele.Miset;
import modele.Partner;
import superbetDAO.AirtimeDAO;
import superbetDAO.ConfigDAO;
import superbetDAO.DAOException;
import superbetDAO.EffChoicekDAO;
import superbetDAO.KenoDAO;
import superbetDAO.MisekDAO;
import superbetDAO.Misek_tempDAO;
import superbetDAO.MisetDAO;
import superbetDAO.PartnerDAO;
import superbetDAO.UtilDAO;
import superbetDAO.api.exeception.DAOAPIException;
import superbetDAO.api.implementations.SuperGameDAOAPI;
import superbetDAO.api.interfaces.ISuperGameDAOAPILocal;
import config.Jeu;
import config.Params;
import config.Timestamp;
import config.UtileKeno;

public final class ManageKenoForm {
	
	private static final String FIELD_AMOUNT = "montant";
	private static final String FIELD_CHOICE = "code";
	private static final String FIELD_COTE = "odds";
	private static final String FIELD_XTEUR = "multi1";
	
	private  String resultat = null;
	private  Map<String, String> erreurs = new HashMap<String, String>();
	private  String imprimer = null;
	private  int multiplicite;
	
	private  boolean canSubmit = false;
	private double tp = 1;
	public  int event = 0; // nombre d'evenement dans le ticket
	private  ArrayList<String> choice = null; // collection de choix
	private String EDraw;
	private  String codeParil; // code du pari effectué
    private  double typejeu;
    private double bonusrate;
    private String[] _fecha;
    private BetTicketK betk;
    
	private UtilDAO utilDao;
	private PartnerDAO partnerDao;
	private  ISuperGameDAOAPILocal  supergameAPI;
	
	public ManageKenoForm(KenoDAO kenoDao,MisetDAO misetDao,UtilDAO utilDao,EffChoicekDAO effchoicekDao,MisekDAO misekDao,
			Misek_tempDAO misektpDao, PartnerDAO partnerDao, AirtimeDAO airtimeDao, ConfigDAO configDao){
		this.utilDao = utilDao;
		this.partnerDao = partnerDao;
		supergameAPI = SuperGameDAOAPI.getInstance();
	}
	
	
	public String getResultat() {
		return resultat;
	}
	
	public  String getImprimer() {
		return imprimer;
	}

	public Map<String, String> getErreurs() {
		return erreurs;
	}
	
	public  void setErreurs(String champ, String message) {
		erreurs.put(champ, message);
	}
	
	public int getMultiplicite() {
		return multiplicite;
	}
	
	public String[] get_fecha() {
		return _fecha;
	}

	public void set_fecha(String[] _fecha) {
		this._fecha = _fecha;
	}
	
	public double getBonusrate() {
		return bonusrate;
	}


	public void setBonusrate(double bonusrate) {
		this.bonusrate = bonusrate;
	}


	@SuppressWarnings("unused")
	public Coupon print(HttpServletRequest request, Caissier caissier){
		
		String amount = getValeurAmount(request, FIELD_AMOUNT);
		String ichoice;
		final String coderace = caissier.getPartner();
		String icoderace;
		String xmulti = request.getParameter( FIELD_XTEUR );
		
		betk = new BetTicketK();
		
		EDraw = "";
		
		Partner partner;
	//	partner = partnerDao.findById(Integer.parseInt(""+caissier.getPartner()));
		Optional<Partner> partners = partnerDao.getAllPartners().stream()
				.filter(p -> p.getCoderace().equals(coderace) && p.getActif() == 1)
				.findFirst();
		
		if(!partners.isPresent()) {
			return null;
		}
		
		partner = partners.get();
		
		if("closed".equalsIgnoreCase(partner.getCob())) {
			resultat = "Caisse fermee";
			setErreurs(FIELD_AMOUNT, resultat);
			canSubmit = false;
			return null;
		}
		
		
		long dbl = 0;
		long tms;
		double gain_min = 0;
		double balance = 0;
		double mt = 1;
		
		String txtDate=new SimpleDateFormat("dd/MM/yyyy,H:m:s", Locale.FRANCE).format(new Date());
		String hora =  txtDate.toString().substring(11,16);
		String fecha = txtDate.toString().substring(0,10);

		
		betk.setCoderace(coderace);

		Coupon crd = new Coupon();
		
		choice = new ArrayList<>();
		multiplicite = 1;
		
		ichoice = getValeurChoice(request, FIELD_CHOICE);
	
		if(ichoice != null && !StringUtils.isBlank(ichoice)){
			
			if(amount != null && erreurs.isEmpty()){
					
				// Verification des limites
					mt = Double.parseDouble(amount);
					mt = mt * tp;
					mt = (double)((int)(mt*100))/100;
					
					if(mt > Params.GAIN_KENO_MAX){
						
						resultat = "Gain maximal depasse";
			    		setErreurs(FIELD_AMOUNT, resultat);
			    		canSubmit = false;
			    		
					}
					else{
						
						resultat = mt+" FCFA";
						betk.setSummise(mt);
						canSubmit = true;
						
					}

					if( event != 0 && canSubmit){ // s'il ya au moins un evenement dans le ticket
				
						//traitements BD
						try{
							
							int idmiset;
							int idmisek;
							int mst;
							int msk;
							int bncd;
							double amountbonus;
							double mtant = Double.parseDouble(amount) / event;
							
							BetTicketK b;
						
							tms = Timestamp.givetimestamp(txtDate);
							
							betk.setCaissier(caissier.getLoginc());
							betk.setHeureMise(String.valueOf(tms));
							betk.setDateMise(txtDate);
							betk.setDrawnumk(UtileKeno.drawnumk);
							betk.setXmulti(String.valueOf((xmulti.equalsIgnoreCase("Oui")) ? 1 : 0));
							betk.setMultiplicite(multiplicite);
							betk.setCotejeu(typejeu);
							betk.setEvent(event);
							betk.setTypeJeu(Jeu.K);
							betk.setParil(utilDao.searchPariL(codeParil)[1]);
							betk.setKchoice(ichoice);
			
							b =  supergameAPI.getSuperGameDAO().placeSlip(Params.url, betk, coderace);
//							System.out.println("return: "+b);
							

						    if(StringUtils.isBlank(b.getMessage())) {
						    	
						    	 this.setBonusrate(b.getMvt());
						    	 String[] dr = b.getKchoice().split("-");
							     EDraw =  StringUtils.join(dr, ";");
								// Creation du coupon de jeu

								crd.setRoom(b.getCoderace());
								crd.setBarcode(b.getBarcode());
								crd.setCodepari(codeParil);
								crd.setEventscote(""+typejeu);
								crd.setEvents(EDraw);
								crd.setGainMax(getBigWin(mt));
								crd.setGainMin(getMinWin(mt, multiplicite));
								crd.setHoraYfecha(fecha+"  "+hora);
								crd.setIdTicket(String.valueOf(b.getIdMiseT()));
								crd.setMtMise(String.valueOf(b.getSummise()));
								crd.setNbEvents(b.getDrawnumk()); //numéro de tirage
								crd.setNbreCombi(b.getBonusCod()); //code bonus
								crd.setMultiplicateur(xmulti);
								
								_fecha = new String[multiplicite];
								
								Date currentDate = new Date();
								
								for(int ii=0; ii < multiplicite; ii++) {
									
									txtDate = new SimpleDateFormat("dd/MM/yyyy,H:mm:s", Locale.FRANCE).format(currentDate);
									hora =  txtDate.substring(11,16);
									fecha = txtDate.substring(0,10);
						
									_fecha[ii] = fecha + "  " + hora;
									
									Calendar c = Calendar.getInstance();
							        c.setTime(currentDate);
									c.add(Calendar.MINUTE, 6);
									currentDate = c.getTime();
									
								}
								
								imprimer = "imprimer";
								event = 0;
							    canSubmit = false;
							    
							}
							else{
								
								resultat = b.getMessage();
								setErreurs(FIELD_CHOICE, resultat);
								return null;
								
							}
						      
						}
						catch(ParseException | IOException | JSONException | URISyntaxException e){
							
							resultat = "Echec de la connexion: \n merci de reessayer dans quelques instants.";
							return null;
							
						}

				}
			}
			else{
				resultat = "Veuillez saisir un montant correct";
				setErreurs(FIELD_CHOICE, resultat);
				return null;
			}
			
			return crd;
		}
		
		return null;
	}
	
	private String getValeurAmount( HttpServletRequest request,String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
       // System.out.println("valeur amount: "+nomChamp);
       if ( valeur == null || valeur.trim().length() == 0 ) {
    	   resultat = "Veuillez saisir un montant valide";
		   setErreurs(FIELD_AMOUNT, resultat);
           return null;
       } else{
    	   if( Integer.parseInt(valeur) < Params.MISE_MIN){
    		   resultat = "Mise minimum est de "+Params.MISE_MIN;
    		   setErreurs(FIELD_AMOUNT, resultat);
    		   return null;
    	   }
    	   else if(Integer.parseInt(valeur) > Params.MISE_KENO_MAX){
    		   resultat = "Mise maximum est de "+Params.MISE_KENO_MAX;
    		   setErreurs(FIELD_AMOUNT, resultat);
    		   return null;
    	   }
    	   else{
    		   return valeur.trim();
    	   } 
       }
    }  
	
	private  String getValeurChoice( HttpServletRequest request,String nomChamp ) {
		
	   String ichoice = request.getParameter( nomChamp );
	   
       char echar;
       String[] _echar = null, __echar;
       String str_ = "";
       
       
       
       //resetAll();
       
       if ( ichoice == null || ichoice.trim().length() == 0 ) {
           return "";
       } else{
    	   ichoice = ichoice.toLowerCase();
    	   echar = ichoice.trim().charAt(0); // recuperation du 1er caractere saisi
    	   
    	   if(echar == '-'){
    		// no draw
				__echar = ichoice.split("-");
				_echar = new String[__echar.length - 1];
				int _i = 0;
				for(int _j=1;_j<__echar.length;_j++){
					_echar[_i++] = __echar[_j];
				}
				
				//System.out.println(_echar.length+"  --- "+__echar.length);
				//on verifie les numeros saisis, 
				for(int j=0;j<_echar.length;j++){
					//System.out.println(_echar[j]+"  **");
					if(!verifierInt(_echar[j])){
						if(j == _echar.length-1){
							String[] _echars;
							_echars = _echar[j].split("n");
							//System.out.println("here");
							for(int k=0;k<_echars.length;k++){
								if(!verifierInt(_echars[k])){
									resultat = "Numero incorrect";
									setErreurs(FIELD_CHOICE, resultat);
								}
								else{ // ticket multiple
									
									_echar[_echar.length-1] = _echars[0];
									multiplicite = Integer.parseInt(_echars[1]);
									
								//	check_draw(_echar);//verification des numeros saisis
								}
							}
							
						}
						else{
							resultat = "Numero incorrect";
							setErreurs(FIELD_CHOICE, resultat);
						}
						
					}//endif
				}//endfor
				
				
					if(check_draw(_echar)){
						//verification des numeros saisis
						
						codeParil = "numOut";
						typejeu = 18.5;
						
						str_ = "";
						int tab[] = new int[choice.size()];
						typejeu = UtileKeno.numOut[choice.size()];
						
						
						for(int j=0;j<tab.length;j++){
							tab[j] = Integer.parseInt(choice.get(j));
						}
						tab = range(tab);
						choice.clear();
						for(int j=0;j<tab.length;j++){
							choice.add(""+tab[j]);
						}
						for(int i=0;i<choice.size()-1;i++){
							str_ = str_ + choice.get(i) + "-";
						}
						str_ = str_ + choice.get(choice.size()-1);
						clear_all_choice();
						
						//Remplissage des donnees
						
						for(int i=0;i<multiplicite;i++){
							event++;
						}
						/*txt_amount.grabFocus();
						txt_amount.setText(""+Utile.miseMIN);*/
						//isPari = true;
						
					}
				
    	   }
    	   else if(echar == '+'){
    		   //All in
    		   __echar = ichoice.split("\\+");
				_echar = new String[__echar.length - 1];
				int _i = 0;
				for(int _j=1;_j<__echar.length;_j++){
					_echar[_i++] = __echar[_j];
				}
				
				//System.out.println(_echar.length+"  --- "+__echar.length);
				//on verifie les numeros saisis, 
				for(int j=0;j<_echar.length;j++){
					//System.out.println(_echar[j]+"  **");
					if(!verifierInt(_echar[j])){
						if(j == _echar.length-1){
							String[] _echars;
							_echars = _echar[j].split("n");
							//System.out.println("here");
							for(int k=0;k<_echars.length;k++){
								if(!verifierInt(_echars[k])){
									resultat = "Numero incorrect";
									setErreurs(FIELD_CHOICE, resultat);
								}
								else{ // ticket multiple
									
									_echar[_echar.length-1] = _echars[0];
									multiplicite = Integer.parseInt(_echars[1]);
									
									
								//	check_draw(_echar);//verification des numeros saisis
								}
							}
							
						}
						else{
							resultat = "Numero incorrect";
							setErreurs(FIELD_CHOICE, resultat);
						}
						
					}//endif
				}//endfor
				
				
					if(check_draw(_echar)){
						//verification des numeros saisis
						
						codeParil = "numAll";
						typejeu = 6500;
						
						str_ = "";
						int tab[] = new int[choice.size()];
						typejeu = UtileKeno.numAll[choice.size()];
						
						
						for(int j=0;j<tab.length;j++){
							tab[j] = Integer.parseInt(choice.get(j));
						}
						tab = range(tab);
						choice.clear();
						for(int j=0;j<tab.length;j++){
							choice.add(""+tab[j]);
						}
						for(int i=0;i<choice.size()-1;i++){
							str_ = str_ + choice.get(i) + "-";
						}
						str_ = str_ + choice.get(choice.size()-1);
						clear_all_choice();
						
						//Remplissage des donnees
						
						for(int i=0;i<multiplicite;i++){
						
							event++;
						}
						/*txt_amount.grabFocus();
						txt_amount.setText(""+Utile.miseMIN);*/
						//isPari = true;
						
					}
				
    	   }
    	   else if(echar == '*'){
    		 //couleur de la 1ere boule 
				
				String vColor = ichoice.substring(1);
				String[] tColor;
				tColor = vColor.split("n");
				if(!verifierInt(tColor[0])){
						resultat = "Choix non disponible";
						setErreurs(FIELD_CHOICE, resultat);
						//return;
					
				}
				else{
					if(Integer.parseInt(tColor[0]) > 4 || Integer.parseInt(tColor[0]) < 1){
						resultat = "Choix non disponible";
						setErreurs(FIELD_CHOICE, resultat);
						//return;
					}
					else{
						if(tColor.length > 1){
							if(!verifierInt(tColor[1])){
								multiplicite = 1;
							}
							else{ // ticket multiple
								multiplicite = Integer.parseInt(tColor[1]);
								if(multiplicite > 5){
									multiplicite = 5;
								}
								
							}
						}
							System.out.println("MULTI1 COLOR: "+multiplicite);
						if(Integer.parseInt(tColor[0]) == 1){
							//Couleur verte between 1 and 20
							resultat = "1er couleur verte";
							
							setColorInTable("1er couleur verte", "num1cv");
							str_ ="1er couleur verte";
							
						}
						else if(Integer.parseInt(tColor[0]) == 2){
							//Couleur bleu between 21 and 40
							resultat = "1er couleur bleu";
							setColorInTable("1er couleur bleu", "num1cb");
							str_ = "1er couleur bleu";
							
						}
						else if(Integer.parseInt(tColor[0]) == 3){
							//Couleur orange between 41 and 60
							setColorInTable("1er couleur rouge", "num1cr");
							str_ = "1er couleur rouge";
							resultat = "1er couleur rouge";
							
						}
						else if(Integer.parseInt(tColor[0]) == 4){
							//Couleur jaune between 61 and 80
							setColorInTable("1er couleur orange", "num1co");
							str_ = "1er couleur orange";
							resultat = "1er couleur orange" ;
							
						}
					}
				}
    	   }
    	   else if(echar == '/'){
    		 //couleur de la derniere boule
				String vColor = ichoice.substring(1);
				String[] tColor;
				tColor = vColor.split("n");
				if(!verifierInt(tColor[0])){
					resultat = "Choix non disponible";
					setErreurs(FIELD_CHOICE, resultat);
					//return;
					
				}
				else{
					if(Integer.parseInt(tColor[0]) > 4 || Integer.parseInt(tColor[0]) < 1){
						resultat = "Choix non disponible";
						setErreurs(FIELD_CHOICE, resultat);
						//return;
					}
					else{
						if(tColor.length > 1){
							if(!verifierInt(tColor[1])){
								multiplicite = 1;
							}
							else{ // ticket multiple
								multiplicite = Integer.parseInt(tColor[1]);
								if(multiplicite > 5){
									multiplicite = 5;
								}
								
							}
						 }
						
						if(Integer.parseInt(tColor[0]) == 1){
							//Couleur verte between 1 and 20
							resultat = "derniere couleur verte";
							setColorInTable("derniere couleur verte", "num20cv");
							str_ = "derniere couleur verte";
							
						}
						else if(Integer.parseInt(tColor[0]) == 2){
							//Couleur bleu between 21 and 40
							setColorInTable("derniere couleur bleu", "num20cb");
							str_ = "derniere couleur bleu";
							resultat = "derniere couleur bleu";
							
						}
						else if(Integer.parseInt(tColor[0]) == 3){
							//Couleur orange between 41 and 60
							setColorInTable("derniere couleur rouge", "num20cr");
							resultat = "derniere couleur rouge";
							str_ = "derniere couleur rouge";
							
						}
						else if(Integer.parseInt(tColor[0]) == 4){
							//Couleur jaune between 61 and 80
							setColorInTable("derniere couleur orange", "num20co");
							resultat = "derniere couleur orange";
							str_ = "derniere couleur orange";
							
						}
					}
				}
    	   }
    	   else if(ichoice.length() > 1){ 
    		
    		String vColor = ichoice.substring(1);
  			String[] tColor;
  			tColor = vColor.split("n");
  			if(tColor.length > 1){
 				if(!verifierInt(tColor[1])){
 					multiplicite = 1;
 				}
 				else{ // ticket multiple
 					multiplicite = Integer.parseInt(tColor[1]);
 					if(multiplicite > 5){
 						multiplicite = 5;
 					}
 					
 				}
 			}
     		 if(ichoice.substring(0,2).equalsIgnoreCase("5-")){
  				//somme des 5 1er < 202.5
  				setSum("somme cinq -202,5", "nums5m");
  				resultat = "somme cinq 1er -202,5";
  				str_ = "somme cinq 1er -202,5";
  				
  		   }
  		   else if(ichoice.substring(0,2).equalsIgnoreCase("5+")){
  				//somme des 5 1er > 202.5
  				setSum("somme cinq +202,5", "nums5p");
  				resultat = "somme cinq 1er +202,5";
  				str_ = "somme cinq 1er +202,5";
  		   }
  		   else if(ichoice.substring(0,2).equalsIgnoreCase("1-")){
  				//1er numero < 40.5
  			   setFirstNumber("1er numero < 40.5", "numI40");
  			   resultat = "1er numero inferieur 40.5";
  			   str_ = "1er numero < 40.5";
  			}
  			else if(ichoice.substring(0,2).equalsIgnoreCase("1+")){
  				//1er numero > 40.5
  				System.out.println("EffChoice: "+ichoice+" 888 "+ichoice.substring(0,2));
  				setFirstNumber("1er numero < 40.5", "numS40");
  				   resultat = "1er numero superieur 40.5";
  				   str_ = "1er numero > 40.5";
  			}
  			else if(ichoice.length() > 2){
  				 
  	     		 if(ichoice.substring(0,3).equalsIgnoreCase("20+")){
  	  				//somme totale > 810.5
  	     			 System.out.println("somme totale > 810.5: "+ichoice.substring(0,3));
  	 			   
  	  				setSum("somme total +805", "nums20p");
  	  				resultat = "somme total superieure 805";
  	  				str_ = "somme total +805";
  	  		   }
  	  		   else if(ichoice.substring(0,3).equalsIgnoreCase("20-")){
  	  				//somme totale < 810.5
  	  				setSum( "somme total -805", "nums20m");
  	  				resultat = "somme total superieure 805";
  	  				str_ = "somme total -805";
  	  		   }
  	  		   
  	  			else if(ichoice.substring(0,3).equalsIgnoreCase("188")){
  	  				setOdd("premier pair",  "num1p");
  	  				resultat = "premier numero pair";
  	  				str_ = "premier pair";
  	  				
  	  			}
  	  			else if(ichoice.substring(0,3).equalsIgnoreCase("189")){
  	  				setOdd("dernier pair",  "num20p");
  	  				resultat = "dernier numero pair";
  	  				str_ = "dernier pair";
  	  				
  	  			}
  	  			else if(ichoice.substring(0,3).equalsIgnoreCase("198")){
  	  				setEven("premier impair", "num1ip");
  	  				resultat = "premier numero impair";
  	  				str_ = "premier impair";
  	  				
  	  			}
  	             else if(ichoice.substring(0,3).equalsIgnoreCase("199")){
  	  				setEven("dernier impair", "num20ip");
  	  				resultat = "dernier numero impair";
  	  				str_ = "dernier impair";
  	  				
  	  			}
  	     		
  	           else{
  	        	   //pari simple 
  	        	  // System.out.println("CHOIX: "+ichoice);
  	        	   __echar = ichoice.split("\\.");
  	        	   System.out.println("CHOIX LENGTH: "+__echar.length);
  					_echar = new String[__echar.length];
  					int _i = 0;
  					for(int _j=0;_j<__echar.length;_j++){
  						_echar[_i++] = __echar[_j];
  					}
  					
  					if(_echar.length < 2 || _echar.length > 10 ){
  						resultat = "Numero incorrect";
  						setErreurs(FIELD_CHOICE, resultat);
  						//return;
  					}
  					
  					System.out.println(_echar.length+"  --- "+__echar.length);
  					//on verifie les numeros saisis, 
  					for(int j=0;j<_echar.length;j++){
  						//System.out.println(_echar[j]+"  **");
  						if(!verifierInt(_echar[j])){
  							if(j == _echar.length-1){
  								String[] _echars;
  								_echars = _echar[j].split("n");
  								//System.out.println("here");
  								for(int k=0;k<_echars.length;k++){
  									if(!verifierInt(_echars[k])){
  										resultat = "Numero incorrect";
  										setErreurs(FIELD_CHOICE, resultat);
  									}
  									else{ // ticket multiple
  										
  										_echar[_echar.length-1] = _echars[0];
  										multiplicite = Integer.parseInt(_echars[1]);
  									//	check_draw(_echar);//verification des numeros saisis
  									}
  								}
  								
  							}
  							else{
  								resultat = "Numero incorrect";
  								setErreurs(FIELD_CHOICE, resultat);
  							}
  							
  						}//endif
  					}//endfor
  					
  					
  						if(check_draw(_echar)){
  							//verification des numeros saisis
  							
  							if(_echar.length == 2){
  								codeParil = "num2";
  								typejeu = UtileKeno.num2[_echar.length];
  							}
  							else if(_echar.length == 3){
  								codeParil = "num3";
  								typejeu = UtileKeno.num3[_echar.length];
  							}
  							else if(_echar.length == 4){
  								codeParil = "num4";
  								typejeu = UtileKeno.num4[_echar.length];
  							}
  							else if(_echar.length == 5){
  								codeParil = "num5";
  								typejeu = UtileKeno.num5[_echar.length];
  							}
  							else if(_echar.length == 6){
  								codeParil = "num6";
  								typejeu = UtileKeno.num6[_echar.length];
  							}
  							else if(_echar.length == 7){
  								codeParil = "num7";
  								typejeu = UtileKeno.num7[_echar.length];
  							}
  							else if(_echar.length == 8){
  								codeParil = "num8";
  								typejeu = UtileKeno.num8[_echar.length];
  							}
  							else if(_echar.length == 9){
  								codeParil = "num9";
  								typejeu = UtileKeno.num9[_echar.length];
  							}
  							else if(_echar.length == 10){
  								codeParil = "num10";
  								typejeu = UtileKeno.num10[_echar.length];
  							}
  							
  							System.out.println("echar length str_: "+_echar.length);
  							str_ = "";
  							int tab[] = new int[_echar.length];
  							
  							
  							for(int j=0;j<tab.length;j++){
  								tab[j] = Integer.parseInt(_echar[j]);
  							}
  							tab = range(tab);
  							choice.clear();
  							for(int j=0;j<tab.length;j++){
  								choice.add(""+tab[j]);
  							}
  							for(int i=0;i<choice.size()-1;i++){
  								str_ = str_ + choice.get(i) + "-";
  							}
  							str_ = str_ + choice.get(choice.size()-1);
  							
  							System.out.println("CHOIX str_: "+str_);
  							clear_all_choice();
  							
  							//Remplissage des donnees
  							
  							for(int i=0;i<multiplicite;i++){
  							
  								event++;
  							}
  							/*txt_amount.grabFocus();
  							txt_amount.setText(""+Utile.miseMIN);*/
  							//isPari = true;
  							
  						}
  						
  						//fin pari simple
  	           }   
  			   
  	    	   }
    	   
    	  }
    	 
           
 
           return str_;
       }
    }
	
	private  void resetAll(){
		 if(event != 0){
				
		 }
			event =0;
			multiplicite = 1;
			//isPari = false;
			canSubmit = false;
	 }
	
	public  boolean verifierInt(String entier) {
        boolean v = false;
        try {
            //on essaie de convertir la chaîne en nombre entier
            Long.parseLong(entier);
            //conversion aboutie, v prend la valeur true
            v = true;
        } catch (NumberFormatException e) {
            //conversion échouée, levée d'une exception, v prend false
        	//System.err.println(e.toString());
            v = false;
        }
          //on retourne v
        return v;
   } 
	
	private  boolean check_draw(String[] str){
		choice.clear();
		
		for(int i=0;i<str.length;i++){
			choice.add(str[i]);
		}
		for(int _i=0;_i<choice.size();_i++){
			if(!check_keno_num(choice.get(_i))){
				resultat = "Numero incorrect";
				setErreurs(FIELD_CHOICE, resultat);
				return false;
			}
		}
		
		if(same_number()){
			resultat = "Numero dupliqué";
			setErreurs(FIELD_CHOICE, resultat);
			return false;
		}
		
		return true;
	}
	
	private  boolean same_number(){
		for(int i=0;i<choice.size()-1;i++){
			for(int l=i+1;l<choice.size();l++){
				if(!choice.get(l).equalsIgnoreCase((String) choice.get(i))){
					continue;
				}
				else{
					//lbl_game_desc.setText("Numero dupliqué");
					return true;
				}
			}
			
			if(choice.get(i).equalsIgnoreCase("0")){
				resultat = "Numero invalide";
				return true;
			}
		}
		if(choice.get(choice.size()-1).equalsIgnoreCase("0")){
			resultat = "Numero invalide";
			return true;
		}
		return false;
	}
	
	private  boolean check_keno_num(String str){ // verifie si le numero saisi est valide
		int num;
		num = Integer.parseInt(str);
		
		if(num < 1 || num > 80){
			return false;
		}
		
		return true;
	}
	
	private  void clear_all_choice(){
		choice.clear();
	}
	
  private  int[] range(int[] tab){
		
		int min;
		
		for(int i=0;i<tab.length-1;i++){
			min = tab[i];
			for(int j=i+1;j<tab.length;j++){
				if(tab[j] < min){
					tab[i] = tab[j];
					tab[j] = min;
					min = tab[i];
				}
			}
		}
		return tab;
	}
  
  private  void setColorInTable(String lib, String codepari){
		 
	    codeParil = codepari;
		typejeu = UtileKeno.numSpec[2];
		event = multiplicite;
		
		clear_all_choice();
		
	/*	
		isPari = true;*/
	}
  
  private  void setFirstNumber(String lib, String codepari){
		 
	    codeParil = codepari;
		typejeu = UtileKeno.numSpec[1];
		event = multiplicite;
		
		clear_all_choice();
		
	/*	
		isPari = true;*/
	}
  
  
  private  void setOdd(String lib, String codepari){
		//1er numero pair
			
			codeParil =codepari;
			typejeu = UtileKeno.numSpec[1];
			event = multiplicite;
	
			clear_all_choice();
			
		/*	
			isPari = true;*/
	 }
	 
  private  void setEven(String lib, String codepari){
  	//1er numero impair
		
		codeParil = codepari;
		typejeu = UtileKeno.numSpec[1];
		event = multiplicite;
		
		clear_all_choice();
	/*	
		isPari = true;*/
	 }
  
  private  void setSum(String lib, String codepari){
     	codeParil = codepari;
		typejeu = UtileKeno.numSpec[1];
		event = multiplicite;
		
		clear_all_choice();
		
		/*
		isPari = true;*/
  }
  
  private double getBigWin(double amount){
	   double mts = 0.0;
	   double max = typejeu;
	  
	   mts = max * amount;
	   mts = (double)((int)(mts*100))/100;
	   return mts;
  }
  
  private double getMinWin(double amount, int multi) {
	   int mts = 0;
	   double max = typejeu;
	   
	   mts = (int) ((max * amount) / multi);
	   //mts = ((int)(mts*100))/100;
	   return mts;
  }
  
}


