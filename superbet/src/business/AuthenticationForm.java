package business;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import config.Params;
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
	private String resultat;
	private Map<String, String> erreurs = new HashMap<String, String>();
	private CaissierDAO caissierDao;
	private PartnerDAO partnerDao;
	private String state;
	private double airtime;
	private  ISuperGameDAOAPILocal  supergameAPI;
	
	
	
	public AuthenticationForm(CaissierDAO caissierDao, AirtimeDAO airtimeDao, PartnerDAO partnerDao) {
		this.caissierDao = caissierDao;
		this.airtime = 0;
		this.partnerDao = partnerDao;
		supergameAPI = SuperGameDAOAPI.getInstance();
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
		boolean trouver = false;
		
		if(!StringUtils.isBlank(login) && !StringUtils.isBlank(pass)){
			//Traitement BD
			try{
				
				if ( erreurs.isEmpty() ) {
					caissier = new Caissier();
					
					//decrypter
					ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
  				    passwordEncryptor.setAlgorithm( Params.ALGO_CHIFFREMENT );
					passwordEncryptor.setPlainDigest( false );
					
					if(login.contains(".admin")) {
						
						caissier.setProfil(1L);
						caissier.setLoginc(login);
						caissier.setMdpc(pass);
						user =  supergameAPI.getSuperGameDAO().getCaissierAdmin(Params.url, caissier);
						if(user != null) {
							user.setProfil(1L);
						}
						else {
							resultat = "Login ou mot de passe incorrect.";
							setErreurs(FIELD_NAME, resultat);
							return null;
							
						}
						
						
					}
					else {
						
						// Recherche du caissier en local
						Caissier caisse = caissierDao.findByLogin(login);
						
						if(caisse != null) {
							
							trouver = passwordEncryptor.checkPassword(pass, caisse.getMdpc());
							
							if(trouver) {
								
								caissier.setLoginc(login);
								caissier.setMdpc(pass);
								caissier.setProfil(caisse.getProfil());
								
								List<Partner> partners = partnerDao.getAllPartners();
								if(!partners.isEmpty()) {
									Params.PARTNER = partners.get(0).getCoderace();
								}
								
								user =  supergameAPI.getSuperGameDAO().getCaissier(Params.url, caissier, Params.PARTNER);
								
								if(user == null){
									
									resultat = "Login ou mot de passe incorrect.";
									setErreurs(FIELD_NAME, resultat);
									return null;
									
								}
								else {
									resultat = "Succès de la connexion.";
									state = user.getStatut();
								}
								
							}
							else {
								// le caissier n'existe pas en local
								resultat = "Login ou mot de passe incorrect.";
								setErreurs(FIELD_NAME, resultat);
								return null;
								
							}
						}
						else {
							
							resultat = "Login ou mot de passe incorrect.";
							setErreurs(FIELD_NAME, resultat);
							return null;
							
						}
					}
					
				} else {
					resultat = "échec de la connexion.";
				}
			}
			catch(DAOException | IOException | JSONException | URISyntaxException | DAOAPIException e){
				resultat = "échec de la connexion : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
				e.printStackTrace();
			}
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
