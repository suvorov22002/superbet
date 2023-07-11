package business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import config.Params;
import config.Timestamp;
import config.UtileSpin;
import modele.Caissier;
import modele.Coupon;
import modele.EffChoicep;
import modele.Misep;
import modele.Miset;
import modele.Partner;
import modele.Spin;
import superbetDAO.DAOException;
import superbetDAO.EffChoicepDAO;
import superbetDAO.MisepDAO;
import superbetDAO.MisetDAO;
import superbetDAO.PartnerDAO;
import superbetDAO.SpinDAO;
import superbetDAO.UtilDAO;

public final class ManageSpinForm {
	
	private static final String FIELD_AMOUNT = "spmontant";
	private static final String FIELD_CHOICE = "spcode";
	private static final String FIELD_COTE = "odds";
	
	private  String resultat = null;
	private  Map<String, String> erreurs = new HashMap<String, String>();
	private  String imprimer = null;
	private  int multiplicite;
	
	private  boolean canSubmit = false;
	private double tp = 1;
	public  int event = 0; // nombre d'evenement dans le ticket
	private  ArrayList<String> choice = null; // collection de choix
	private String EDraw;
	private  String libchoix; // libelle choix
    private  String codeParil; // code du pari effectué
    private  double typejeu;
    
    private SpinDAO spinDao;
	private MisetDAO misetDao;
	private EffChoicepDAO effchoicepDao;
	private UtilDAO utilDao;
	private MisepDAO misepDao;
	private PartnerDAO partnerDao;
	
	public ManageSpinForm(SpinDAO spinDao,MisetDAO misetDao,UtilDAO utilDao,EffChoicepDAO effchoicepDao,MisepDAO misepDao,
			PartnerDAO partnerDao){
		this.spinDao = spinDao;
		this.misetDao = misetDao;
		this.utilDao = utilDao;
		this.effchoicepDao = effchoicepDao;
		this.misepDao = misepDao;
		this.partnerDao = partnerDao;
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

	public Coupon print(HttpServletRequest request, Caissier caissier){
		String amount = getValeurAmount(request, FIELD_AMOUNT);
		String ichoice;
		String coderace;
		
		EDraw = "";
		
		Partner partner;
		partner = partnerDao.find(caissier.getPartner());
		if(partner == null){
			return null;
	    }
		
		coderace = partner.getCoderace();
		
		Miset miset = new Miset();
		Misep misep = new Misep();
		//Misek_temp misektp = new Misek_temp();
		EffChoicep effchoicep = new EffChoicep();
		Coupon crd = new Coupon();
		
		double mt = 1;
		
		choice = new ArrayList<String>();
		multiplicite = 1;
		
		ichoice = getValeurChoice(request, FIELD_CHOICE);
		System.out.println("ichoice: "+ichoice);
		long dbl = 0;
		double gain_min = 0;
		
		String txtDate=new SimpleDateFormat("dd/MM/yyyy,H:m:s", Locale.FRANCE).format(new Date());
		String hora =  txtDate.toString().substring(11,16);
		String fecha = txtDate.toString().substring(0,10);
		
		long tms;
		if(ichoice != null && !ichoice.equalsIgnoreCase("")){
			//System.out.println("ichoice amount: "+amount);
			if(amount != null){
				if(erreurs.isEmpty()){
					dbl = utilDao.searchBarcode();
					mt = Double.parseDouble(amount);
					mt = mt * tp;
					mt = (double)((int)(mt*100))/100;
					
					if(mt > Params.GAIN_SPIN_MAX){
						resultat = "Gain maximal depassé";
			    		setErreurs(FIELD_AMOUNT, resultat);
			    		canSubmit = false;
					}
					else{
						resultat = mt+" FCFA";
	     				// construction de l'objet coupon
						miset.setBarcode(""+dbl);
						miset.setTypeJeu("S");
						miset.setSummise(mt);
		
						canSubmit = true;
					}
				}
			}
			else{
				resultat = "Veuillez saisir un montant correct SP";
				setErreurs(FIELD_AMOUNT, resultat);
	    		canSubmit = false;
			}
			if( event != 0){ // s'il ya au moins un evenement dans le ticket
				if(canSubmit){
					try{
						int idmiset,idmisep;
						int mst,msp,mfp; //variables control insert en BD
						int bncd;
						double amountbonus;
						Long maxIdspin;
						
						Spin spin;
						
						bncd = partner.getBonuspcode();
						amountbonus = partner.getBonusPamount();
						
						mst = misetDao.create(miset);
						idmiset = misetDao.findId();
						
						
						tms = Timestamp.givetimestamp(txtDate);
						
				        
						spin = spinDao.getMaxIdDrawP(coderace);
						if(spin != null){
							maxIdspin = spin.getIdspin();
						}
						else{
							return null;
						}
						
						
						misep.setIdCaissier(""+caissier.getIdCaissier());
						misep.setHeurMise(""+tms);
						misep.setSumMise(""+mt);
						misep.setDatMise(txtDate);
						misep.setEtatMise("attente");
						misep.setDrawNumP(spin.getDrawNumP());
						misep.setBonusCodeP(""+(++bncd));
						misep.setIdMiset(""+idmiset);
						misep.setIdSpin(""+maxIdspin);
						
						msp = misepDao.create(misep);
						idmisep = misepDao.findId();
						
						double mtant;
						mtant = Double.parseDouble(amount);
						mtant = mtant / event;
						
						String str_="";
						mfp = 0;
						
						for(int i=0;i<event;i++){
							
							str_ = ichoice.substring(0);
							
							//System.out.println("- "+i+": "+str_+" codepari"+ codeParil);
							
							effchoicep.setIdparis(utilDao.searchPariS(codeParil)[1]);
							effchoicep.setIdmisep(""+idmisep);
							effchoicep.setPchoice(str_);
							effchoicep.setIdspin(""+maxIdspin);
							effchoicep.setMtchoix(""+mtant);
							
							mfp += effchoicepDao.create(effchoicep);
							
						  } 
						
						 String[] dr = str_.split("-");;
					   //   for(int i=0;i<dr.length-1;i++){
					   // 	  EDraw = EDraw + dr[i] + ";";
					   //   }	 
					      EDraw = EDraw + dr[dr.length-1];
						
						if(mst!=0 && msp!=0 && mfp==event){
							// Creation du coupon de jeu
							
							crd.setRoom(coderace);
							crd.setBarcode(""+dbl);
							//crd.setCodepari(codeParil);
							crd.setCodepari(libchoix);
							crd.setEventscote(""+typejeu);
							crd.setEvents(EDraw);
							crd.setGainMax(getBigWin(mt));
							crd.setGainMin(gain_min);
							crd.setHoraYfecha(fecha+"  "+hora);
							crd.setIdTicket(""+idmiset);
							crd.setMtMise(""+mt);
							crd.setNbEvents(Integer.parseInt(spin.getDrawNumP())); //numéro de tirage
							crd.setNbreCombi(bncd); //code bonus
							
							amountbonus +=  mt*(0.3*0.02);
							partnerDao.update_bonusp(amountbonus, bncd, coderace);
							
							imprimer = "imprimer";
							
							event = 0;
						    canSubmit = false;
						}
						else{
							resultat = "Erreur de ticket";
							setErreurs(FIELD_CHOICE, resultat);
							
							return null;
						}
					}
					catch(DAOException | ParseException e){
						resultat = "Échec de la connexion: \n merci de réessayer dans quelques instants.";
						e.printStackTrace();
					}
				}
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
    	   else if(Integer.parseInt(valeur) > Params.MISE_SPIN_MAX){
    		   resultat = "Mise maximum est de "+Params.MISE_SPIN_MAX;
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
	    System.out.println("Ichoice: "+ichoice+" nomChamp: "+nomChamp);
	    char echar;
	    
	    if ( ichoice == null || ichoice.trim().length() == 0 ) {
	           return "";
	    } else{
	    	 echar = ichoice.trim().charAt(0); // recuperation du 1er caractere saisi
	    	 
	    	 if(echar == 'v'){
	    		 setUtil("couleur verte", "numcv", UtileSpin.numSpec[0]);
	    	 }
	    	 else if(echar == 'r'){
	    		 setUtil("couleur rouge", "numcr", UtileSpin.numSpec[7]);
	    	 }
	    	 else if(echar == 'n'){
	    		 setUtil("couleur noire", "numcn", UtileSpin.numSpec[7]);
	    	 }
	    	 else if(echar == 'm'){
	    		 String mir = ichoice.substring(1);
	    		 
	    		 if(verifierInt(mir)){
	    			 int num = Integer.parseInt(mir);
	    			 
	    			 if(num==12 || num==21){
	    				 setUtil("mirroir 12/21", "mir12", UtileSpin.numSpec[1]);
	    			 }
	    			 else if(num==13 || num==31){
	    				 setUtil("mirroir 13/31", "mir13", UtileSpin.numSpec[1]);
	    			 }
	    			 else if(num==23 || num==32){
	    				 setUtil("mirroir 23/32", "mir23", UtileSpin.numSpec[1]);
	    			 }
	    			 else{
	    				 resultat = "Choix inconnu";
						 setErreurs(FIELD_CHOICE, resultat);
						 return null;
	    			 }
	    		 }
	    		 else{
	    			 resultat = "Numero incorrect";
					 setErreurs(FIELD_CHOICE, resultat);
					 return null;
	    		 }
	    	 }
	    	 else if(echar == 't'){
	    		 String mir = ichoice.substring(1);
	    		 
	    		 if(verifierInt(mir)){
	    			 int num = Integer.parseInt(mir);
	    			 
	    			 if(num==11){
	    				 setUtil("twins 11", "twin11", UtileSpin.numSpec[2]);
	    			 }
	    			 else if(num==22){
	    				 setUtil("twins 22", "twin22", UtileSpin.numSpec[2]);
	    			 }
	    			 else if(num==33){
	    				 setUtil("twins 33", "twin33", UtileSpin.numSpec[2]);
	    			 }
	    			 else{
	    				 resultat = "Choix inconnu";
						 setErreurs(FIELD_CHOICE, resultat);
						 return null;
	    			 }
	    		 }
	    		 else{
	    			 resultat = "Numero incorrect";
					 setErreurs(FIELD_CHOICE, resultat);
					 return null;
	    		 }
	    	 }
	    	 else if(echar == 'f'){
	    		 String mir = ichoice.substring(1);
	    		 
	    		 if(verifierInt(mir)){
	    			 int num = Integer.parseInt(mir);
	    			 
	    			 if(num==0){
	    				 setUtil("final 0", "fin0", UtileSpin.numSpec[3]);
	    			 }
	    			 else if(num==1){
	    				 setUtil("final 1", "fin1", UtileSpin.numSpec[3]);
	    			 }
	    			 else if(num==2){
	    				 setUtil("final 2", "fin2", UtileSpin.numSpec[3]);
	    			 }
	    			 else if(num==3){
	    				 setUtil("final 3", "fin3", UtileSpin.numSpec[3]);
	    			 }
	    			 else if(num==4){
	    				 setUtil("final 4", "fin4", UtileSpin.numSpec[3]);
	    			 }
	    			 else if(num==5){
	    				 setUtil("final 5", "fin5", UtileSpin.numSpec[3]);
	    			 }
	    			 else if(num==6){
	    				 setUtil("final 6", "fin6", UtileSpin.numSpec[3]);
	    			 }
	    			 else{
	    				 resultat = "Choix inconnu";
						 setErreurs(FIELD_CHOICE, resultat);
						 return null;
	    			 }
	    		 }
	    		 else{
	    			 resultat = "Numero incorrect";
					 setErreurs(FIELD_CHOICE, resultat);
					 return null;
	    		 }
	    	 }
	    	 else if(echar == 's'){
	    		 String mir = ichoice.substring(1);
	    		 
    			 if(mir.equalsIgnoreCase("a")){
    				 setUtil("secteur a", "secta", UtileSpin.numSpec[4]);
    			 }
    			 else if(mir.equalsIgnoreCase("b")){
    				 setUtil("secteur b", "sectb", UtileSpin.numSpec[4]);
    			 }
    			 else if(mir.equalsIgnoreCase("c")){
    				 setUtil("secteur c", "sectc", UtileSpin.numSpec[4]);
    			 }
    			 else if(mir.equalsIgnoreCase("d")){
    				 setUtil("secteur d", "sectd", UtileSpin.numSpec[4]);
    			 }
    			 else if(mir.equalsIgnoreCase("e")){
    				 setUtil("secteur e", "secte", UtileSpin.numSpec[4]);
    			 }
    			 else if(mir.equalsIgnoreCase("f")){
    				 setUtil("secteur f", "sectf", UtileSpin.numSpec[4]);
    			 }
    			 else{
    				 resultat = "Choix inconnu";
					 setErreurs(FIELD_CHOICE, resultat);
					 return null;
    			 }
	    	 } 
	    	 else if(echar == 'l'){
	    		 setUtil("low", "low", UtileSpin.numSpec[7]);
	    	 }
	    	 else if(echar == 'h'){
	    		 setUtil("high", "high", UtileSpin.numSpec[7]);
	    	 }
	    	 else if(echar == 'c'){
	    		 String mir = ichoice.substring(1);
	    		 
    			 if(mir.equalsIgnoreCase("lr")){
    				 setUtil("low rouge", "lr", UtileSpin.numSpec[5]);
    			 }
    			 else if(mir.equalsIgnoreCase("ln")){
    				 setUtil("low noir", "ln", UtileSpin.numSpec[5]);
    			 }
    			 else if(mir.equalsIgnoreCase("hr")){
    				 setUtil("high rouge", "hr", UtileSpin.numSpec[5]);
    			 }
    			 else if(mir.equalsIgnoreCase("hn")){
    				 setUtil("high noir", "hn", UtileSpin.numSpec[5]);
    			 }
    			 else{
    				 resultat = "Choix inconnu";
					 setErreurs(FIELD_CHOICE, resultat);
					 return null;
    			 }
	    	 } 
	    	 else if(echar == 'd'){
	    		 String mir = ichoice.substring(1);
	    		 
	    		 if(verifierInt(mir)){
	    			 int num = Integer.parseInt(mir);
	    			 
	    			 if(num==1){
	    				 setUtil("douzaine 1", "douz1", UtileSpin.numSpec[6]);
	    			 }
	    			 else if(num==2){
	    				 setUtil("douzaine 2", "douz2", UtileSpin.numSpec[6]);
	    			 }
	    			 else if(num==3){
	    				 setUtil("douzaine 3", "douz3", UtileSpin.numSpec[6]);
	    			 }
	    			 else{
	    				 resultat = "Choix inconnu";
						 setErreurs(FIELD_CHOICE, resultat);
						 return null;
	    			 }
	    		 }
	    		 else{
	    			 resultat = "Numero incorrect";
					 setErreurs(FIELD_CHOICE, resultat);
					 return null;
	    		 }
	    	 }
	    	 else if(echar == 'p'){
	    		 setUtil("pair", "pair", UtileSpin.numSpec[7]);
	    	 }
	    	 else if(echar == 'i'){
	    		 setUtil("impair", "ipair", UtileSpin.numSpec[7]);
	    	 }
	    	 else if(verifierInt(ichoice.trim())){
	    		// String mir = ichoice.substring(0);
	    		// System.out.println("MIRROR MIR: "+ichoice.trim());
	    		// if(verifierInt(mir)){
	    			 int num = Integer.parseInt(ichoice.trim());
	    			 
	    			 if(num < 37 || num > 0){
	    				 setUtil("single", "numb", UtileSpin.numSpec[0]); 
	    			 }
	    			 else{
	    				 resultat = "Numero incorrect";
						 setErreurs(FIELD_CHOICE, resultat);
						 return null;
	    			 }
	    		// }
	    	//	 else{
	    	//		 resultat = "Numero incorrect";
			//		 setErreurs(FIELD_CHOICE, resultat);
			//		 return null; 
	    	//	 }
	    	 }
	    	 
	    	 return ichoice;
	    }
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
	private  void setUtil(String lib, String codepari, int cote){
	  	//1er numero impair
			
			libchoix = lib;
			codeParil = codepari;
			typejeu = cote;
			event = 1;
			
			clear_all_choice();
		/*	
			isPari = true;*/
	}
	
	private  void clear_all_choice(){
		choice.clear();
	}
	private double getBigWin(double amount){
		   double mts = 0.0;
		   double max = typejeu;
		  
		   mts = max * amount;
		   mts = (double)((int)(mts*100))/100;
		   return mts;
	  }
}
