package business;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jettison.json.JSONException;
import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import config.Params;
import modele.Airtime;
import modele.Caissier;
import modele.Partner;
import superbetDAO.AirtimeDAO;
import superbetDAO.CaissierDAO;
import superbetDAO.DAOException;
import superbetDAO.PartnerDAO;
import superbetDAO.api.exeception.DAOAPIException;
import superbetDAO.api.implementations.SuperGameDAOAPI;
import superbetDAO.api.interfaces.ISuperGameDAOAPILocal;

public final class AuthenticationForm {
	
	private static final String FIELD_NAME = "username";
	private static final String FIELD_PASS = "pass";
	private static final String FIELD_PARTNER = "partner";
	
	private String resultat = null;
	private Map<String, String> erreurs = new HashMap<String, String>();
	private CaissierDAO caissierDao;
	private AirtimeDAO airtimeDao;
	private PartnerDAO partnerDao;
	private String state;
	private double airtime;
	private  ISuperGameDAOAPILocal  supergameAPI;
	
	
	
	public AuthenticationForm(CaissierDAO caissierDao, AirtimeDAO airtimeDao, PartnerDAO partnerDao) {
		this.caissierDao = caissierDao;
		this.airtimeDao = airtimeDao;
		this.airtime = 0;
		this.partnerDao = partnerDao;
		supergameAPI = new SuperGameDAOAPI();
	}
	
	public String getResultat() {
		return resultat;
	}
	public void setResultat(String resultat) {
		this.resultat = resultat;
	}
	public Map<String, String> getErreurs() {
		return erreurs;
	}
	public void setErreurs(String champ, String message) {
		erreurs.put(champ, message);
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public double getAirtime() {
		return airtime;
	}

	public void setAirtime(double airtime) {
		this.airtime = airtime;
	}

	public void setErreurs(Map<String, String> erreurs) {
		this.erreurs = erreurs;
	}

	public Caissier authenticateCaissier(HttpServletRequest request){
		
		Caissier caissier = null;
		Caissier user = null;
		String login = getValeurChamp(request, FIELD_NAME);
		String pass = getValeurChamp(request, FIELD_PASS);
		//String partner = getValeurChamp(request, FIELD_PARTNER);
		
		if(login != null && pass != null){
			//Traitement BD
			try{
				if ( erreurs.isEmpty() ) {
					caissier = new Caissier();
					
					//crypter
//					ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
//					passwordEncryptor.setAlgorithm( Params.ALGO_CHIFFREMENT );
//					passwordEncryptor.setPlainDigest( false );
//					String passChiffre = passwordEncryptor.encryptPassword(pass);
					
//					System.out.println("passChiffre= "+passChiffre);
					
					//decrypter
					ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
  				    passwordEncryptor.setAlgorithm( Params.ALGO_CHIFFREMENT );
					passwordEncryptor.setPlainDigest( false );
					
					caissier.setLoginc(login);
				//	caissier.setMdpc(pass);
					
					if(login.contains(".admin")) {
						caissier.setProfil(1L);
						return caissier;  
					}
					else {
						caissier.setProfil(2L);
					}
					
					ArrayList<Partner> partners = partnerDao.getAllPartners();
					if(partners.size() > 0) {
						Params.PARTNER = partners.get(0).getCoderace();
					}
					else {
						return null;
					}
					System.out.println("Params.PARTNER: "+Params.PARTNER);
					try {
						user =  supergameAPI.getSuperGameDAO().getCaissier(Params.url, caissier, Params.PARTNER);
						System.out.println("USER: "+user);
					} catch (IOException | JSONException | URISyntaxException | DAOAPIException e) {
						e.printStackTrace();
						return null;
					}
					
//					caissier = caissierDao.find(login, "");
					boolean trouver = false;
					if(user != null){
						try {
							trouver = passwordEncryptor.checkPassword(pass, user.getMdpc());
						}
						catch(Exception e) {
							e.printStackTrace();
						}
						
						if(!trouver) {
							resultat = "Echec de la connexion.";
							return null;
						}
						resultat = "Succès de la connexion.";
						state = user.getStatut();
//						if(state.equalsIgnoreCase("N")){
//							user.setStatut("C");
//							caissierDao.updateState(user);
//						}
//						Airtime carte = airtimeDao.find(user);
//						
//						if(carte != null){
//						  airtime = carte.getBalance();
//						}
//						user.setAirtime(airtime);
//						int count = airtimeDao.findCMvt(user.getIdCaissier());
//						if(count == 0){
//							airtimeDao.createMvt(user.getIdCaissier(), airtime);
//						}
//						else{
//							airtimeDao.updateMvt(caissier.getId(), mvt);
//						}
//						
					}
					else{
						resultat = "échec de la connexion.";
					}
					
				} else {
					resultat = "échec de la connexion.";
				}
			}
			catch(DAOException e){
				resultat = "échec de la connexion : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
				e.printStackTrace();
			}
			
			/*if(login.equalsIgnoreCase("test") && pass.equalsIgnoreCase("test")){
				resultat = "connecte";
				caissier.setLoginc(login);
				caissier.setMdpc(pass);
			}
			else{
				resultat = "login ou mdp incorrect";
				setErreurs(FIELD_NAME, resultat);
			}*/
		}
		else{
			resultat = "login ou pass incorrect";
			setErreurs(FIELD_NAME, resultat);
		}
		
		
		
		return user;
	}
	
	
	private static String getValeurChamp( HttpServletRequest request,String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
       if ( valeur == null || valeur.trim().length() == 0 ) {
           return null;
       } else{
           return valeur.trim();
       }
    }  
	 
	 
}
