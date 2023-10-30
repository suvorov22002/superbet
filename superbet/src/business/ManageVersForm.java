package business;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONException;

import config.Params;
import modele.BetTicketK;
import modele.Caissier;
import modele.EffChoicek;
import modele.Partner;
import modele.Versement;
import superbetDAO.AirtimeDAO;
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
import superbetDAO.api.exeception.DAOAPIException;
import superbetDAO.api.implementations.SuperGameDAOAPI;
import superbetDAO.api.interfaces.ISuperGameDAOAPILocal;

public final class ManageVersForm {
	
	private static final String FIELD_CODE = "barcode";
	private static final String FIELD_VERS = "versement";
	
	private  String resultat = null;
	private  Map<String, String> erreurs = new HashMap<String, String>();
	private String draw_result;
	private Map<String, String> drawData = new HashMap<String, String>();
	private Map<String, String> details_tickets = new HashMap<String, String>();
	List<Map<String, String>> evenements = new ArrayList<Map<String, String>>();
	private int multiplicite;
	
	private boolean isTesting = false; // test de ticket en cours
	private boolean bonusDown;
	private double bonusWinAmount;
	private int bonusWinCode;
	private int bonusTicketCode;
	
	private KenoDAO kenoDao;
	private SpinDAO spinDao;
	private MisetDAO misetDao;
	private EffChoicekDAO effchoicekDao;
	private EffChoicepDAO effchoicepDao;
	private MisekDAO misekDao;
	private MisepDAO misepDao;
	private VersementDAO verstDao;
	private PartnerDAO partnerDao;
	private Misek_tempDAO misektpDao;
	private UtilDAO utilDao;
	private AirtimeDAO airtimeDao;
	private  ISuperGameDAOAPILocal  supergameAPI;
	
	
	public String getResultat() {
		return resultat;
	}

	public Map<String, String> getErreurs() {
		return erreurs;
	}

	public Map<String, String> getDrawData() {
		return drawData;
	}

	public List<Map<String, String>> getEvenements() {
		return evenements;
	}

	public void setDraw_result(String draw_result) {
		this.draw_result = draw_result;
	}

	public void setTesting(boolean isTesting) {
		this.isTesting = isTesting;
	}

	public  void setErreurs(String champ, String message) {
		erreurs.put(champ, message);
	}
	
	public void setDrawData(String champ, String val){
		drawData.put(champ, val);
	}
	
	
	public void setDetailTicket(String champ, String val){
		details_tickets.put(champ, val);
	}
	public int getMultiplicite() {
		return multiplicite;
	}
	
	public ManageVersForm(KenoDAO kenoDao,SpinDAO spinDao,MisetDAO misetDao,UtilDAO utilDao,EffChoicekDAO effchoicekDao,MisekDAO misekDao,
			EffChoicepDAO effchoicepDao,MisepDAO misepDao,VersementDAO verstDao, PartnerDAO partnerDao, Misek_tempDAO misektpDao,AirtimeDAO airtimeDao){
		this.kenoDao = kenoDao;
		this.spinDao = spinDao;
		this.misetDao = misetDao;
		this.effchoicekDao = effchoicekDao;
		this.misekDao = misekDao;
		this.effchoicepDao = effchoicepDao;
		this.misepDao = misepDao;
		this.verstDao = verstDao;
		this.partnerDao = partnerDao;
		this.misektpDao = misektpDao;
		this.utilDao = utilDao;
		this.airtimeDao = airtimeDao;
		supergameAPI = SuperGameDAOAPI.getInstance();
	}
	
	@SuppressWarnings("unused")
	public Versement traiterTicket(HttpServletRequest request, Caissier caissier){
		
		Versement verst = null;
		Partner part = null;
		boolean already_paid = Boolean.FALSE;
		String coderace;
		bonusDown = Boolean.FALSE;
		
		String versement = getVersement( request, FIELD_VERS);
		String barcode = getBarcode( request,FIELD_CODE ) ;
		//System.out.println("BARCODE h: "+barcode);
		if((barcode == null || barcode.length() < 12) && versement.isEmpty()) {
			resultat = "Code du ticket incorrect<br/>";
			setErreurs(FIELD_CODE, resultat);
			return null;
		}
		
		barcode = barcode.length() > 12 ? barcode.substring(0, 12) : barcode;
		coderace = caissier.getPartner();
		 
	//	System.out.println("BARCODE h: "+barcode);
		 if ( StringUtils.isBlank(versement)) {
			 
	        verst = new Versement();
	      
			if(barcode == null){
				return null;
			}
			else{
			
				if(!isTesting){
					isTesting = true;
					BetTicketK b = null;
					
					try {
						
						b =  supergameAPI.getSuperGameDAO().checkTicket(Params.url, coderace,  barcode);
						//System.out.println("BETK: "+b);
						if ((!StringUtils.isBlank(b.getMessage()) && !StringUtils.equals("TICKET NON EVALUE", b.getMessage()))
							|| StringUtils.equals("TICKET ALREADY PAID", b.getMessage())) {
							
							switch(b.getMessage()) {
								
								case "TICKET CHOIX ERROR":
									resultat = "ERREUR LORS DE LA VERIFICATION<br/>";
									setErreurs(FIELD_CODE, resultat);
									break;
								
								case "TICKET INCONNU":
									resultat = "TICKET INCONNU,<br/>Veuillez bien saisir le code.<br/>";
									setErreurs(FIELD_CODE, resultat);
									setDrawData("montant", " ");
									setDrawData("prix_total", " ");
						    		setDrawData("gain_total", " ");
						    		break;
						    	
								case "TICKET ALREADY PAID":
									already_paid = Boolean.TRUE;
									resultat = "TICKET DEJA PAYE<br/>"+barcode+"<br/>"+b.getVers().getMontant();
									setErreurs(FIELD_CODE, resultat);
									setDrawData("montant", " ");
									setDrawData("prix_total", " ");
						    		setDrawData("gain_total", " ");
						    		break;
						    		
								case "TICKET NON RECONNU":
									resultat = "TICKET NON RECONNU DANS CETTE SALLE<br/>";
									setErreurs(FIELD_CODE, resultat);
									break;
									
								case "TICKET NON ENREGISTRE":
									resultat = "TICKET NON ENREGISTRE,<br/>CONTACTER RESPONSABLE.<br/>";
									setErreurs(FIELD_CODE, resultat);
									break;
									
								default:
										resultat = "Erreur,<br/>CONTACTER RESPONSABLE.<br/>";
										setErreurs(FIELD_CODE, resultat);
										
							}
							
							return verst;
						}
						else {
							
							   //System.out.println("BETK: "+b);
							   String lib_pari = utilDao.searchPariLById(b.getListEfchk().get(0).getIdparil())[3];
							 
				    		   setDrawData("barcode", b.getBarcode());
				    		   setDrawData("player_choice", b.getListEfchk().get(0).getKchoice());
				    		   setDrawData("multi", ""+ b.getMultiplicite());
				    		   setDrawData("cparil", ""+lib_pari);
				    		   setDrawData("montant", ""+b.getSummise());
				    		   setDrawData("draw_num", ""+b.getDrawnumk());
				    		   setDrawData("draw_result", b.getDrawResult());
				    		   setDrawData("prix_total", ""+b.getSummise());
				    		   setDrawData("gain_total", ""+b.getSumWin());
				    		   setDrawData("xmulti", b.getXmulti());
				    		 
				    		   List<EffChoicek> list_efchk;
				    		   list_efchk = b.getListEfchk();
				    		   
				    		  // multiplicite = list_efchk.size();
				    		   
				    		   
				    		   boolean eval = true;
				    		   for(EffChoicek efck : list_efchk) {
				    			   
				    			   Map<String, String> details_tick = new HashMap<String, String>();
				    			   if(StringUtils.isNotBlank(efck.getDrawresult())) {
				    				   details_tick.put("cote", efck.getCote());
				    				   details_tick.put("resultTour", efck.getDrawresult());
				    				   details_tick.put("etat", efck.isState() ? "true" : "false");
				    				   details_tick.put("draw_num", String.valueOf(efck.getDrawnum()));
				    				   details_tick.put("choice", efck.getKchoice());
				    			   }
				    			   else {
				    				   eval = false;
				    				   details_tick.put("cote", "-");
				    				   details_tick.put("resultTour", "-");
				    				   details_tick.put("draw_num", "-");
				    			   }
				    			   this.evenements.add(details_tick);
				    		   }
				    		   
				    		   multiplicite = this.evenements.size();
				    		   
				    		   if("TICKET NON EVALUE".equalsIgnoreCase(b.getMessage())){
									resultat = "TICKET NON EVALUE,<br/>TIRAGE EN COURS.<br/>";
									setErreurs(FIELD_CODE, resultat);
									return verst;
							   }

				    		   double gg = b.getSumWin();
				    		   bonusDown = b.isBonus();
				    		   if(eval && gg == 0){
				    			   resultat = "Ticket perdant";
				    			   setErreurs(FIELD_CODE, resultat);
				    			   if(b.isCagnotte()) {
				    				   resultat = "Ticket CAGNOTTE";
					    			   setErreurs(FIELD_CODE, resultat);
				    			   }
				    		   }
				    		   else if(eval && gg != 0){
				    			   if(bonusDown) {
				    				   resultat = "Ticket bonus gagnant";
				    			   }
				    			   else {
				    				   resultat = "Ticket gagnant"; 
				    			   }
				    			   setErreurs(FIELD_CODE, resultat);
				    			   if(b.isCagnotte()) {
				    				   resultat += "\nTicket CAGNOTTE";
					    			   setErreurs(FIELD_CODE, resultat);
				    			   }
				    		   }
				    		   			    		  
				    		   verst.setTypeVers("K");
						}
						
					} catch (IOException | JSONException | URISyntaxException e) {
						
						resultat = "ERREUR LORS DE LA VERIFICATION<br/>";
						setErreurs(FIELD_CODE, resultat);
						return verst;
						
					}
				}
			}
		
			return verst;
		}
		 else if ( versement != null || versement.trim().length() != 0 || !versement.equalsIgnoreCase("0.0")
				 || versement.equalsIgnoreCase("0")){
				System.out.println("Traiter versement: "+versement+" barcode: "+barcode+" caissier.getIdCaissier(): "+caissier.getIdCaissier());
				
				verst = new Versement();
				try {
					verst =  supergameAPI.getSuperGameDAO().paidTicket(Params.url, barcode, caissier.getIdCaissier(), Double.parseDouble(versement));
				} catch (IOException | JSONException | URISyntaxException | DAOAPIException e) {
					e.printStackTrace();
					return null;
				}
				//on verifie si le ticket n'a pas deja été payé
				
				return verst;
	     }
		return verst;
		 
	}
	
	private String getBarcode( HttpServletRequest request,String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
       
       if ( valeur == null) {
       //if ( valeur == null || valeur.trim().length() != 10 ) {
    	   resultat = "Ticket inconnu";
		   setErreurs(FIELD_CODE, resultat);
           return null;
       } else{
    	  return valeur.trim(); 
       }
       
	}
	
	private String getVersement( HttpServletRequest request,String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        
        if(valeur == null)
        	return valeur;
        else
    	    return valeur.trim(); 
	}
	
	
	
}
