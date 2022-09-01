package business;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jettison.json.JSONException;
import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import config.Params;
import modele.Cagnotte;
import modele.Caissier;
import modele.CaissierDto;
import modele.Config;
import modele.Keno;
import modele.Partner;
import modele.PartnerDto;
import modele.Spin;
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
			AirtimeDAO airtimeDao, GameCycleDAO game_cycleDao, CagnotteDAO cagnotteDao){
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
		this.cagnotteDao = cagnotteDao;
		supergameAPI = new SuperGameDAOAPI();
	}
	
	public void manage_config(HttpServletRequest request){
		erreurs = new HashMap<String, String>();
		erreurs_u = new HashMap<String, String>();
		erreurs_b = new HashMap<String, String>();
		erreurs_c = new HashMap<String, String>();
		action = getName( request, "idconfig" );// definit l'action Ã  traiter. ajout partner, ajout caissier...
		
		if(action.equalsIgnoreCase("addpartner")){
			coderace = getName( request, FIELD_CODERACE );
			zone = getName( request, FIELD_ZONE );
			
			if(coderace == null){
				return;
			}
			if(coderace.length() < 5){
	  		  setErreurs(FIELD_CODERACE, "Longueur du nom très court");
	  		  resultat = "Nom très court, 5 caractères minimum";
	  		  return;
			}
		 try {
			Partner partner = partnerDao.find(coderace);
			
			if(partner == null){
				
				Long n = 0L;
				Partner part = new Partner();
				part.setCoderace(coderace);
				part.setZone(zone);
				part.setGroupe(1);
				
				n = partnerDao.create(part);
				if(n > 0){	
					PartnerDto pdto = new PartnerDto();
					pdto.setCoderace(part.getCoderace());
					pdto.setIdpartner(part.getIdpartner());
					pdto.setZone(zone);
					Partner p = supergameAPI.getSuperGameDAO().submitPartner(Params.url, pdto);
					
					if (p == null) {
						partnerDao.delete(part);
						setErreurs(FIELD_CODERACE, "Erreur creation partenaire");
				  		resultat = "Erreur creation partenaire";
				  		return;
					}
					
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
					int nb_race = kenoDao.create(ken);
					
					//ajout d'une ligne spin
//					Spin spin = new Spin();
//					spin.setCoderace(coderace);
//					spin.setDrawNumP("0");
//					int nbspin = spinDao.create(spin);
				    
					//ajout freeslip
					misetDao.createFree(n);
					
					//ajout d'un cycle par defaut
//					Partner partne = partnerDao.find(coderace);
//					GameCycle gm = new GameCycle();
//					gm.setPercent(95d);
//					gm.setArchive(0);
//					gm.setArrangement("1-2-3-4-5-6-7-8-9-10-11-12-13-14-15-16-17-18-19-20");
//					gm.setTour(10);
//					gm.setJeu("K");
//					gm.setCurr_percent(0);
//					gm.setPosition(1);
//					gm.setHitfrequence(10);
//					gm.setJkpt(0);
//					gm.setMise(0);
//					gm.setMisef(1);
//					gm.setPartner(partne.getIdpartner());
//					gm.setPayout(0);
//					gm.setRefundp(0);
//					gm.setStake(0d);
//					gm.setDate_fin("16-01-2021,00:17");
//				
//					game_cycleDao.create(gm);
					
					
					if(nb_race > 0){
						resultat = "Partenaire enregistré avec succes";
						return;
					}
					else{
						setErreurs(FIELD_CODERACE, "Echec de creation");
						resultat = "Echec de creation";
						return;
					}
					
				}
				else{
					setErreurs(FIELD_CODERACE, "Echec de creation");
					resultat = "Echec de creation";
					return;
				}
				
			}
			else{
				setErreurs(FIELD_CODERACE, "present");
				resultat = "Deja présent, veuillez entrer un autre nom";
			}
			
		   } catch (IOException | JSONException | URISyntaxException | DAOAPIException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		   }
		}
		else if(action.equalsIgnoreCase("addcaissier")){
			user = new HashMap<String, String>();
			
			String nom_user = getName(request, FIELD_NOM_CAISSIER);
			String login = getName(request, FIELD_LOGIN);
			String pass = getPass(request, FIELD_PASS);
			String profil = getName(request, FIELD_PROFIL);
			String partner = getName(request, FIELD_PARTNER);
			
			if(login == null || pass == null || profil == null || partner == null){
				return;
			}
			if(login.length() < 5){
	  		  setErreurs_u(FIELD_LOGIN, "Longueur du login très court");
	  		  resultat_u = "Login très court, 5 caractères minimum";
	  		  return;
			}
			if(pass.length() < 5){
	  		  setErreurs_u(FIELD_PASS, "Longueur du mot de passe très court");
	  		  resultat_u = "pass très court, 5 caractÃ¨res minimum";
	  		  return;
			}
			
			Caissier caissier = caissierDao.findByLogin(login);
			Long idpartner = partnerDao.find(partner).getIdpartner();
			Long idprofil = 2L;
			if(profil.equalsIgnoreCase("CAISSIER")){
				idprofil = 2L;
			}
			else if(profil.equalsIgnoreCase("ADMINISTRATEUR")){
				idprofil = 1L;
			}
			
			ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
			passwordEncryptor.setAlgorithm( Params.ALGO_CHIFFREMENT );
			passwordEncryptor.setPlainDigest( false );
			String passChiffre = passwordEncryptor.encryptPassword(pass);
			
			
			setUser("nomc", nom_user);
			setUser("loginc", login);
			setUser("passc", passChiffre);
			
			if(caissier == null){
				int n = 0;
				Caissier cais = new Caissier();
				cais.setNomc(nom_user);
				cais.setLoginc(login);
				cais.setMdpc(passChiffre);
				cais.setPartner(idpartner);
				cais.setProfil(idprofil);
				cais.setGrpe(1);
				//System.out.println(cais);
				n = caissierDao.create(cais);
				try {
					if (n > 0) {
						CaissierDto caisDto = new CaissierDto();
						caisDto.setNomc(nom_user);
						caisDto.setLoginc(login);
						caisDto.setMdpc(passChiffre);
						caisDto.setPartner(idpartner);
						caisDto.setProfil(idprofil);
						caisDto.setGrpe(1);
						//System.out.println(caisDto);
						cais = supergameAPI.getSuperGameDAO().saveUser(Params.url, caisDto);
						//System.out.println("USER: "+caisDto);
					}
					
				} catch (IOException | JSONException | URISyntaxException | DAOAPIException e) {
					e.printStackTrace();
					return;
				}
				if(n > 0){	
					Caissier user = caissierDao.findByLogin(login);
					//creation de la ligne des mouvements
					airtimeDao.createMvt(user.getIdCaissier(), 0);
					resultat_u="Caissier crée avec succes";
					return;
				}
				else{
					setErreurs_u(FIELD_LOGIN, "Echec de creation");
					resultat_u = "Echec de creation";
					return;
				}
				
				
			}
			else{
				setErreurs_u(FIELD_LOGIN, "present");
				resultat_u = "Login utilisé, veuillez entrer un autre";
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
					resultat_b = "Bonus Min supérieur à Bonus Max";
					return;
				}
				if(bonusk_rate == null || bonusk_rate.equalsIgnoreCase("")){
					bonusk_rate = "2";
					return;
				}
				double k_rate = Double.parseDouble(bonusk_rate)/100;
				
				try {
					//ajout = configDao.updateBonusK(k_rate, mbonusk0, mbonusk1, coderace );
					ajout = supergameAPI.getSuperGameDAO().updateBonusK(Params.url, k_rate, mbonusk0, mbonusk1, idpartner);
					if(ajout != 0){
						erreurs.clear();
						resultat_b = "Mise à jour du bonus effectué avec succès";
					}
					else{
						setErreurs_b("Update", "Echec de la mise à jour");
						resultat_b = "Echec de la mise à jour";
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
			resultat_c = "Cagnotte mis à  jour";
			String detail_lot = "";
			long t1,t2;
			dat1 = getName(request, FIELD_DATE1);
			String partner = getName(request, "ncbonus_part2");
			Cagnotte cagnotte = new Cagnotte();
			String lot = getName(request, "ncbonus_part");
			String heur = getName(request, "cg_heure");
			Partner part = partnerDao.find(partner);
			
			if(lot == null) {
				setErreurs_c("Update", "Veuillez choisir un lot");
				resultat_c = "Veuillez choisir un lot";
				return;
			}
			if(dat1 == null) {
				setErreurs_c("Update", "Veuillez choisir le jour de tombée");
				resultat_c = "Veuillez choisir le jour de tombée";
				return;
			}
			
		//	System.out.println("Lot: "+lot+" Date: "+dat1+":"+heur+" partner: "+partner);
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
			
			cagnotte.setLot(detail_lot);
			cagnotte.setDay(dat1);
			cagnotte.setHeur(heur);
			//cagnotte.setPartner(part);
			cagnotte.setIdpart(part.getIdpartner());
			cagnotte.setJeu("");
			
			
			try {
				//cagnotteDao.create(cagnotte);
				cagnotte = supergameAPI.getSuperGameDAO().saveJackpot(Params.url, cagnotte);
			} catch (IOException | JSONException | URISyntaxException | DAOAPIException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				setErreurs_c("Update", "Erreur de creation cagnotte");
				resultat = "Erreur de creation cagnotte";
				return;
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
}
