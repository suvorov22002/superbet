package business;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jettison.json.JSONException;

import config.Params;
import modele.Airtime;
import modele.Caissier;
import modele.CaissierDto;
import modele.Partner;
import superbetDAO.AirtimeDAO;
import superbetDAO.CaissierDAO;
import superbetDAO.PartnerDAO;
import superbetDAO.api.exeception.DAOAPIException;
import superbetDAO.api.implementations.SuperGameDAOAPI;
import superbetDAO.api.interfaces.ISuperGameDAOAPILocal;

public class AirtimeForm {
	
	private static final String FIELD_SALLE = "airtime_room";
	private static final String FIELD_CAISSE = "airtime_user";
	private static final String FIELD_MONTANT = "airtime_sum";
	private  String resultat = null;
	private  double credit;
	private  Map<String, String> erreurs = new HashMap<String, String>();
	private String coderace,user,sum;
	private int taille = 0;
	private AirtimeDAO airtimeDao;
	private CaissierDAO caissierDao;
	private PartnerDAO partnerDao;
	private  ISuperGameDAOAPILocal  supergameAPI;
	
	public AirtimeForm(AirtimeDAO airtimeDao,CaissierDAO caissierDao, PartnerDAO partnerDao){
		this.airtimeDao = airtimeDao;
		this.caissierDao = caissierDao;
		this.partnerDao = partnerDao;
		supergameAPI = new SuperGameDAOAPI();
	}
	
	public void manage_admin(HttpServletRequest request){
		Airtime airtime = new Airtime();
		Caissier cais = new Caissier();
		double balance = 0;
		coderace = getPartner(request, FIELD_SALLE);
		user = getPartner(request, FIELD_CAISSE);
		sum = getPartner(request, FIELD_MONTANT);
		credit = 0;
		
		try{
			if(sum != null && !sum.equalsIgnoreCase("")){
				credit = Double.parseDouble(sum);
			}
		}
		catch(Exception e){
			credit = 0;
			e.printStackTrace();
		}
		
		if(credit == 0) return;
			cais = caissierDao.findByLogin(user);
			
			
			
			if (cais == null) {
				erreurs.put("error", "caisse absente");
				resultat = "Erreur lors de l'ajout";
			}
			else if(cais.getIdCaissier() == 1) {
				return;
			}
			else {
				try {
				    CaissierDto user = new CaissierDto();
				    user.setLoginc(cais.getLoginc());
				    user.setPartner(cais.getPartner());
				    Partner p = partnerDao.findById(user.getPartner());
//					Airtime airtme = airtimeDao.find(cais);
//					if(airtme !=null)
//						balance = airtme.getBalance();
//					//System.out.println(cais.getIdCaissier());
//					airtime.setCredit(credit);
//					airtime.setDate(new Date());
//					airtime.setDebit(0);
//					airtime.setCaissier(cais.getIdCaissier());
//					airtime.setBalance(balance+credit);
//					airtime.setLibelle("CREDIT EN CAISSE");
//					airtime.setEta("NV");
//					
//					airtimeDao.create(airtime);
//					double mvt = airtimeDao.findMvt(cais.getIdCaissier());
//					airtimeDao.updateMvt(cais.getIdCaissier(), mvt+credit);
				
				
					double solde = supergameAPI.getSuperGameDAO().airtime(Params.url, p.getCoderace(), user.getLoginc(), credit);
					if (solde == 0) {
						erreurs.put("error", "caisse absente");
						resultat = "Erreur lors du credit";
					}
					else {
						resultat = "Caisse credit�e de: "+solde;
					}
					
					
			} catch (IOException | JSONException | URISyntaxException | DAOAPIException e) {
				e.printStackTrace();
			}
			
		}
		
		return;
	}
	
	private String getPartner( HttpServletRequest request,String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
       
       if ( valeur == null) {
           return valeur;
       } else{
    	  return valeur.trim(); 
       }
       
	}

	public String getResultat() {
		return resultat;
	}

	public void setResultat(String resultat) {
		this.resultat = resultat;
	}

	public double getCredit() {
		return credit;
	}

	public void setCredit(double credit) {
		this.credit = credit;
	}

	public Map<String, String> getErreurs() {
		return erreurs;
	}

	public void setErreurs(Map<String, String> erreurs) {
		this.erreurs = erreurs;
	}

	public String getCoderace() {
		return coderace;
	}

	public void setCoderace(String coderace) {
		this.coderace = coderace;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getSum() {
		return sum;
	}

	public void setSum(String sum) {
		this.sum = sum;
	}
}
