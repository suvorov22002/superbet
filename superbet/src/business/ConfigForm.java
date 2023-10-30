package business;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import config.DateUtil;
import config.Params;
import modele.CagnotteDto;
import modele.Caissier;
import modele.CaissierDto;
import modele.Config;
import modele.Keno;
import modele.Partner;
import modele.PartnerDto;
import modele.ResPartner;
import superbetDAO.AirtimeDAO;
import superbetDAO.CagnotteDAO;
import superbetDAO.CaissierDAO;
import superbetDAO.ConfigDAO;
import superbetDAO.EffChoicekDAO;
import superbetDAO.GameCycleDAO;
import superbetDAO.KenoDAO;
import superbetDAO.MisekDAO;
import superbetDAO.Misek_tempDAO;
import superbetDAO.MisetDAO;
import superbetDAO.PartnerDAO;
import superbetDAO.SpinDAO;
import superbetDAO.UtilDAO;
import superbetDAO.VersementDAO;
import superbetDAO.api.exeception.DAOAPIException;
import superbetDAO.api.implementations.SuperGameDAOAPI;
import superbetDAO.api.interfaces.ISuperGameDAOAPILocal;

public final class ConfigForm {
	
	private static final String FIELD_CODERACE = "ncpartner";
	private static final String FIELD_ZONE = "nczone";
	
	private static final String FIELD_NOM_CAISSIER = "ncuser";
	private static final String FIELD_LOGIN = "nclogin";
	private static final String FIELD_PASS = "ncpass";
	private static final String FIELD_PROFIL = "ncprofil";
	private static final String FIELD_PARTNER = "ncsalle_cais";
	private static final String FIELD_JEU = "ncbonus_jeu";
	
	private static final String FIELD_BONUSK0 = "bonu_sum_min";
	private static final String FIELD_BONUSK1 = "bonu_sum_max";
	private static final String FIELD_BONUSK_RATE = "ncbonus_rate";
	private static final String FIELD_BONUSK_RESERVE = "ncbonus_reserve";
	private static final String FIELD_KCODERACE = "ncbonus_part1";
	private static final String FIELD_DATE1 = "cgddebut";
	private static final String FIELD_ACTIVE_PARTNER = "coderace";
	private  ISuperGameDAOAPILocal  supergameAPI;
	
	private  String resultat = null;
	private  String resultat_u = null;
	private  String resultat_b = null;
	private  String resultat_c = null;
	private  Map<String, String> erreurs;
	private  Map<String, String> erreurs_u;
	private  Map<String, String> erreurs_b;
	private  Map<String, String> erreurs_c;
	private String action="";
	private String coderace="", dat1;
	private String zone="";
	
	private String bonusk0;
	private String bonusk1;
    private double mbonusk0;
    private double mbonusk1;
    private int ajout = 0;
   
	
	private  Map<String, String> user;
	private List<Partner> lpartner;
	
	private KenoDAO kenoDao;
	private MisetDAO misetDao;
	private EffChoicekDAO effchoicekDao;
	private MisekDAO misekDao;
	private VersementDAO verstDao;
	private PartnerDAO partnerDao;
	private Misek_tempDAO misektpDao;
	private UtilDAO utilDao;
	private CaissierDAO caissierDao;
	private ConfigDAO configDao;
	private SpinDAO spinDao;
	private AirtimeDAO airtimeDao;
	private GameCycleDAO game_cycleDao;
	private CagnotteDAO cagnotteDao;
	
	
	public String getResultat() {
		return resultat;
	}
	
	public Map<String, String> getErreurs_u() {
		return erreurs_u;
	}

	public void setErreurs_u(String champ, String message) {
		erreurs_u.put(champ, message);
	}

	public Map<String, String> getErreurs_b() {
		return erreurs_b;
	}

	public void setErreurs_b(String champ, String message) {
		erreurs_b.put(champ, message);
	}

	public Map<String, String> getErreurs_c() {
		return erreurs_c;
	}

	public void setErreurs_c(String champ, String message) {
		erreurs_c.put(champ, message);
	}
	
	public String getResultat_u() {
		return resultat_u;
	}

	public String getResultat_b() {
		return resultat_b;
	}

	public String getResultat_c() {
		return resultat_c;
	}
	public String getAction() {
		return action;
	}
	
	public String getCoderace() {
		return coderace;
	}
	
	public String getZone() {
		return zone;
	}
	
	public Map<String, String> getErreurs() {
		return erreurs;
	}
	public void setErreurs(String champ, String message) {
		erreurs.put(champ, message);
	}
	
	
	
	public Map<String, String> getUser() {
		return user;
	}

	public void setUser(String champ, String message) {
		user.put(champ, message);;
	}
	
	public String getBonusk0() {
		return bonusk0;
	}

	public void setBonusk0(String bonusk0) {
		this.bonusk0 = bonusk0;
	}

	public String getBonusk1() {
		return bonusk1;
	}

	public void setBonusk1(String bonusk1) {
		this.bonusk1 = bonusk1;
	}

	public void setResultat(String resultat) {
		this.resultat = resultat;
	}

	public String getDat1() {
		return dat1;
	}

	public void setDat1(String dat1) {
		this.dat1 = dat1;
	}

	public ConfigForm(KenoDAO kenoDao,MisetDAO misetDao,UtilDAO utilDao,EffChoicekDAO effchoicekDao,MisekDAO misekDao,
			VersementDAO verstDao, PartnerDAO partnerDao, Misek_tempDAO misektpDao, CaissierDAO caissierDao, ConfigDAO configDao, SpinDAO spinDao, 
			AirtimeDAO airtimeDao, GameCycleDAO game_cycleDao){
		this.kenoDao = kenoDao;
		this.misetDao = misetDao;
		this.effchoicekDao = effchoicekDao;
		this.misekDao = misekDao;
		this.verstDao = verstDao;
		this.partnerDao = partnerDao;
		this.misektpDao = misektpDao;
		this.utilDao = utilDao;
		this.caissierDao = caissierDao;
		this.configDao = configDao;
		this.spinDao = spinDao;
		this.airtimeDao = airtimeDao;
		this.game_cycleDao = game_cycleDao;
		supergameAPI = SuperGameDAOAPI.getInstance();
	}
	
	public void manage_config(HttpServletRequest request){
		erreurs = new HashMap<String, String>();
		erreurs_u = new HashMap<String, String>();
		erreurs_b = new HashMap<String, String>();
		erreurs_c = new HashMap<String, String>();
		action = getName( request, "idconfig" );// definit l'action à traiter. ajout partner, ajout caissier...
		
	 if(!StringUtils.isBlank(action))
		 
		if("addpartner".equalsIgnoreCase(action)){
			
			coderace = getName( request, FIELD_CODERACE );
			zone = getName( request, FIELD_ZONE );
			
			// recupere tous les partenaires
		    lpartner = partnerDao.getAllPartners();
		   
			
			if(StringUtils.isBlank(coderace)){
				return;
			}
			
			String[] spaceCoderace = coderace.trim().split(" ");
			String[] pointCoderace = coderace.trim().split(".");
			
			if(coderace.length() < 5){
				
	  		  	setErreurs(FIELD_CODERACE, "Longueur du nom tres court");
	  		  	resultat = "Nom tr�s court, 5 caract�res minimum";
	  		  	return;
	  		  	
			}
			else if(spaceCoderace.length > 1) {
				
				setErreurs(FIELD_LOGIN, "Pas d'espace dans le nom du partenaire");
				resultat = "Pas d'espace dans le nom du partenaire.";
		  		return;
		  		
			}
			
			if(pointCoderace.length > 1) {
				
				setErreurs(FIELD_LOGIN, "Pas de point dans le nom du partenaire");
				resultat = "Pas de point dans le nom du partenaire.";
		  		return;
		  		
			}
			
		
			try {
			
				 Partner partn = partnerDao.find(coderace);
				 
				 if(partn == null) {
					 
					 Partner part = new Partner();
					 part.setCoderace(coderace);
					 part.setZone(zone);
					 part.setGroupe(1);
					 part.setActif(1);
					 part.setCob("opened");
					 boolean isAdded = addNewPartenaire(part);
					 
					 if(isAdded) {
						 
						 PartnerDto pdto = new PartnerDto();
						 pdto.setCoderace(coderace);
						 pdto.setZone(zone);
						 ResPartner resp = supergameAPI.getSuperGameDAO().submitPartner(Params.url, pdto);
						 if(resp == null) {
							 
							 setErreurs(FIELD_CODERACE, "Erreur lors de la creation.");
							 resultat = "Erreur lors de la creation.";
							 
							 partnerDao.delete(part);
							 kenoDao.delete(part.getCoderace());
							 configDao.delete(null);
							 
							 return;
						 }
						 String respMess = resp.getMessage();
						 
						 if ("EXISTS".equalsIgnoreCase(respMess)) {
							 
							 setErreurs(FIELD_CODERACE, "present");
							 resultat = "Partenaire/Salle deja present.";
							 return;
							 
						 }
						 else if("NEW".equalsIgnoreCase(respMess)) {
							 resultat = "Partenaire cr�er avec success.";
						 }
						 
					 }
					 else {
						 
						 setErreurs(FIELD_CODERACE, "present");
						 resultat = "Erreur lors de la creation des partenaires.";
						 return;
						 
					 }
					 
				 }
				 else {
					 
					 setErreurs(FIELD_CODERACE, "present");
					 resultat = "Login partenaire deja existant.";
					 return;
					 
				 }
			
		 } catch (IOException | JSONException | URISyntaxException | DAOAPIException e) {
			 e.printStackTrace();
			 setErreurs(FIELD_CODERACE, "present");
			 resultat = "Erreur lors de la creation des partenaires.";
		 }
		}
		else if(action.equalsIgnoreCase("addcaissier")){
			user = new HashMap<String, String>();
			
			String nom_user = getName(request, FIELD_NOM_CAISSIER);
			String login = getName(request, FIELD_LOGIN);
			String pass = getPass(request, FIELD_PASS);
			String profil = getName(request, FIELD_PROFIL);
			String partner = getName(request, FIELD_PARTNER);
			
			if(StringUtils.isBlank(login) || StringUtils.isBlank(pass) || StringUtils.isBlank(profil) || StringUtils.isBlank(partner)){
				return;
			}
			
			String[] spaceLogin = login.trim().split(" ");
			if(login.length() < 5){
	  		  setErreurs_u(FIELD_LOGIN, "Longueur du login tres court");
	  		  resultat_u = "Login tr�s court, 5 caract�res minimum";
	  		  return;
			}
			else if(spaceLogin.length > 1) {
				setErreurs_u(FIELD_LOGIN, "Pas d'espace dans le login");
		  		resultat_u = "Pas d'espace dans le login.";
		  		return;
			}
			
			
			if(pass.length() < 6){
	  		  setErreurs_u(FIELD_PASS, "Longueur du mot de passe tr�s court");
	  		  resultat_u = "pass tr�s court, 5 caractères minimum";
	  		  return;
			}
			
			ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
			passwordEncryptor.setAlgorithm( Params.ALGO_CHIFFREMENT );
			passwordEncryptor.setPlainDigest( false );
			
			Caissier caissier = caissierDao.findByLogin(login);
			Long idpartner = partnerDao.find(partner).getIdpartner();
			Long idprofil = 2L;
			if(profil.equalsIgnoreCase("CAISSIER")){
				idprofil = 2L;
			}
			else if(profil.equalsIgnoreCase("ADMINISTRATEUR")){
				idprofil = 1L;
			}
			
			String passChiffre = passwordEncryptor.encryptPassword(pass);
			setUser("nomc", nom_user);
			setUser("loginc", login);
			setUser("passc", "******");
			
			if(caissier == null){
				
				int n = 0;
				Caissier cais = new Caissier();
				cais.setNomc(nom_user);
				cais.setLoginc(login);
				cais.setMdpc(passChiffre);
				cais.setPartner(partner);
				cais.setProfil(idprofil);
				cais.setGrpe(1);
				//System.out.println(cais);
				n = caissierDao.create(cais);
				
				try {
					
					if (n > 0) {
						// Utilisateur cree en local
						
						CaissierDto caisDto = new CaissierDto();
						caisDto.setNomc(nom_user);
						caisDto.setLoginc(login);
						caisDto.setMdpc(pass);
						caisDto.setPartner(partner);
						caisDto.setProfil(idprofil);
						caisDto.setGrpe(1);
						//System.out.println(caisDto);
						Caissier caisse = supergameAPI.getSuperGameDAO().saveUser(Params.url, caisDto, partner);
						if(caisse == null) {
							
							caissierDao.delete(cais);
							setErreurs_u(FIELD_LOGIN, "Echec lors de la creation");
							resultat_u = "Echec lors de la creation.";
							return;
						}
						
						resultat_u="Caissier cr�e avec succes";
						
					}
					
				} catch (IOException | JSONException | URISyntaxException | DAOAPIException e) {
					e.printStackTrace();
					return;
				}
			}
			else{
				setErreurs_u(FIELD_LOGIN, "present");
				resultat_u = "Login utilis�, veuillez entrer un autre";
			}
		}
		else if(action.equalsIgnoreCase("addbonus")){
			bonusk0 = getName(request, FIELD_BONUSK0);
			bonusk1 = getName(request, FIELD_BONUSK1);
			String jeu = getName(request, FIELD_JEU);
			//System.out.println("BONUSMIN: "+bonusk0+" BONUSMAX: "+bonusk1+" | "+jeu);
			coderace = getName( request, FIELD_KCODERACE );
			String bonusk_rate = getName(request,  FIELD_BONUSK_RATE);
			//System.out.println("coderace: "+coderace+" | "+bonusk_rate);
			Long idpartner = partnerDao.find(coderace).getIdpartner();
			resultat_b = "";
			erreurs_b.clear();
			if(jeu == null || jeu.equalsIgnoreCase("")){
				setErreurs_b(FIELD_JEU, "Mauvais jeu");
				resultat_b = "Veuillez selectionner un type de jeu";
				return;
			}
			
			if(jeu.equalsIgnoreCase("keno")){
				if(coderace == null || coderace.equalsIgnoreCase("")){
					setErreurs_b(FIELD_KCODERACE, "Mauvais partenaire");
					resultat_b = "Veuillez selectionner un partenaire";
					return;
				}
				if(jeu == null || jeu.equalsIgnoreCase("")){
					setErreurs_b(FIELD_JEU, "Mauvais jeu");
					resultat_b = "Veuillez selectionner un type de jeu";
					return;
				}
				if(bonusk0 != null && !bonusk0.equalsIgnoreCase("")){
					mbonusk0 = Double.parseDouble(bonusk0);
				}
				else{
					setErreurs_b(FIELD_BONUSK0, "Mauvais montant min");
					resultat_b = "Veuillez saisir un montant min valide";
					return;
				}
				if(bonusk1 != null && !bonusk1.equalsIgnoreCase("")){
					mbonusk1 = Double.parseDouble(bonusk1);
				}
				else{
					setErreurs_b(FIELD_BONUSK1, "Mauvais montant max");
					resultat_b = "Veuillez saisir un montant max valide";
					return;
				}
				if(mbonusk0 > mbonusk1){
					setErreurs_b(FIELD_BONUSK1, "Mauvais montant");
					resultat_b = "Bonus Min sup�rieur � Bonus Max";
					return;
				}
				if(bonusk_rate == null || bonusk_rate.equalsIgnoreCase("")){
					bonusk_rate = "2";
					//return;
				}
				double k_rate = Double.parseDouble(bonusk_rate)/100;
				
				try {
					//ajout = configDao.updateBonusK(k_rate, mbonusk0, mbonusk1, coderace );
					ajout = supergameAPI.getSuperGameDAO().updateBonusK(Params.url, k_rate, mbonusk0, mbonusk1, coderace);
					if(ajout != 0){
						erreurs.clear();
						resultat_b = "Mise � jour du bonus effectu� avec succ�s";
					}
					else{
						setErreurs_b("Update", "Echec de la mise � jour");
						resultat_b = "Echec de la mise � jour";
						return;
					}
				} catch (IOException | JSONException | URISyntaxException | DAOAPIException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			else if(jeu.equalsIgnoreCase("spin")){
				
			}
			else if(jeu.equalsIgnoreCase("dog")){
				
			}
		}
		else if(action.equalsIgnoreCase("addcagnotte")){
			
			try {
				String detail_lot = "";
				CagnotteDto cagnotte = new CagnotteDto();
				
				dat1 = getName(request, FIELD_DATE1);
				String partner = getName(request, "ncbonus_part2");
				String lot = getName(request, "ncbonus_part");
				String heur = getName(request, "cg_heure");
				
				if(lot == null) {
					setErreurs_c("Update", "Veuillez choisir un lot");
					resultat_c = "Veuillez choisir un lot";
					return;
				}
				if(dat1 == null || heur == null) {
					setErreurs_c("Update", "Veuillez choisir le jour de tombee");
					resultat_c = "Veuillez choisir le jour de tombee";
					return;
				}
				
				
				if(lot.equalsIgnoreCase("1")) {
					detail_lot = "tel";
				}
				else if(lot.equalsIgnoreCase("2")) {
					detail_lot = "woofer";
				}
				else if(lot.equalsIgnoreCase("3")) {
					detail_lot = "tricot";
				}
				else if(lot.equalsIgnoreCase("4")) {
					detail_lot = "conso";
				}
				else if(lot.equalsIgnoreCase("5")) {
					detail_lot = "ecran";
				}
				else if(lot.equalsIgnoreCase("6")) {
					detail_lot = "cash";
				}
				
				Date dateJour = new Date();
				Date date = DateUtil.parse(dat1 + " " + heur, DateUtil.DATE_HOUR_FORMAT_MOMO);
				
				System.out.println("Lot: "+lot+" Date: "+dat1+" "+heur+" partner: "+partner);
				System.out.println("date: "+date);
				System.out.println("dateJour: "+dateJour);
				
				if(date.before(dateJour)) {
					
					setErreurs_c("Update", "Erreur de creation cagnotte");
					resultat_c = "Veuillez choisir une date valide. " + dat1+" "+heur;
					return;
					
				}
				
		
				
				cagnotte.setLot(detail_lot);
				cagnotte.setHeur(date);
				cagnotte.setJeu("");
			
			
				cagnotte = supergameAPI.getSuperGameDAO().saveJackpot(Params.url, cagnotte, partner);
				resultat_c = "Cagnotte mis a jour";
				
			} catch (IOException | JSONException | URISyntaxException | DAOAPIException e) {
				
				e.printStackTrace();
				setErreurs_c("Update", "Erreur de creation cagnotte");
				resultat_c = "Erreur de creation cagnotte";
				return;
			}
			
		}
		else if("activerpartner".equalsIgnoreCase(action)){
			
			coderace = getName(request, FIELD_ACTIVE_PARTNER);
			if(coderace == null) {
				
				resultat = "Erreur lors de l'activation.";
				setErreurs_c(FIELD_ACTIVE_PARTNER, "Erreur lors de l'activation.");
				return;
			}
			lpartner = partnerDao.getAllPartners();
			
			for (Partner pp : lpartner) {
				
				if(pp.getCoderace().equals(coderace)) {
					pp.setActif(1);
					pp.setCob("opened");
					resultat = coderace + " activé avec success";
				}
				else {
					pp.setActif(0);
					pp.setCob("closed");
				}
				
				partnerDao.update(pp);
			}
		}
		
		
	}
	
	private String getName( HttpServletRequest request,String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
       
       if ( valeur == null || valeur.trim().length() == 0) {
           return null;
       } else{
    
    	  return valeur.trim(); 
       }
       
	}
	
	private String getPass( HttpServletRequest request,String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
       
       if ( valeur == null || valeur.trim().length() == 0) {
           return null;
       } else{
    	  return valeur.trim(); 
       }
       
	}
	
	private String getValRadio(HttpServletRequest request, String nomChamp){
		String valeur = request.getParameter(nomChamp);
		return valeur;
	}
	
	private boolean addNewPartenaire(Partner part) {
		
		int nb_race;
		Long n = 0L;
		n = partnerDao.create(part);
		
		if(n > 0){	
			
			//ajout d'une de configuration
			Config conf = new Config();
			conf.setCoderace(coderace);
			configDao.create(conf);
			
			//ajout d'un tirage par defaut
			Keno ken = new Keno();
			ken.setDrawnumK("1");
			ken.setMultiplicateur("0");
			ken.setDrawnumbK("'1-2-3-4-5-6-7-8-9-10-11-12-13-14-15-16-17-18-19-20'");
			ken.setCoderace(coderace);
			ken.setHeureTirage("01/01/2020-00:00:00");
			nb_race = kenoDao.create(ken);
			
			//misetDao.createFree(n);
			
			if(nb_race > 0){
				resultat = "Partenaire enregistr� avec succes";
			}
			else{
				setErreurs(FIELD_CODERACE, "Echec de creation");
				resultat = "Echec de creation";
				return Boolean.FALSE;
			}
			
			for (Partner pp : lpartner) {
				pp.setActif(0);
				pp.setCob("closed");
				partnerDao.update(pp);
			}
		}
		else {
			setErreurs(FIELD_CODERACE, "Echec de creation");
			resultat = "Echec de creation";
			return Boolean.FALSE;
		}
		
		return Boolean.TRUE;
	}		
}
