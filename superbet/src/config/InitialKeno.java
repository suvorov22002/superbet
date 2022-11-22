package config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import business.Refresh;
import modele.ControlDisplayKeno;
import modele.Partner;
import superbetDAO.AirtimeDAO;
import superbetDAO.CaissierDAO;
import superbetDAO.ConfigDAO;
import superbetDAO.DAOFactory;
import superbetDAO.GameCycleDAO;
import superbetDAO.KenoDAO;
import superbetDAO.MisekDAO;
import superbetDAO.PartnerDAO;
import superbetDAO.UtilDAO;
import superbetDAO.VersementDAO;

public class InitialKeno {
	
	private static PartnerDAO partnerDao;
	private static CaissierDAO caissierDao;
	private static AirtimeDAO airtimeDao;
	private static MisekDAO misekDao;
	private static VersementDAO versementDao;
	private static KenoDAO kenoDao;
	private static GameCycleDAO game_cycleDao;
	private static ConfigDAO configDao;
	private static DAOFactory daof;
	private static UtilDAO utilDao;
	private static Refresh refresh;
	
	
	public static  Map<String, Refresh> mapRefresh = new HashMap<String, Refresh>();
	
	public InitialKeno() {
		misekDao = daof.getInstance().getMisekDao();
		airtimeDao = daof.getInstance().getAirtimeDao();
		caissierDao = DAOFactory.getInstance().getCaissierDao();
		partnerDao = DAOFactory.getInstance().getPartnerDao();
		kenoDao = DAOFactory.getInstance().getKenoDao();
		game_cycleDao = DAOFactory.getInstance().getGameCycleDao();
		configDao = DAOFactory.getInstance().getConfigDao();
		utilDao = DAOFactory.getInstance().getUtilDao();
		this.refresh = Refresh.getInstance();
		UtileKeno._display_draw = new ArrayList<ControlDisplayKeno>();
	}
	
	public static void intializeKeno() {
		String coderace="";
		System.out.println("//// Initialisation du systeme \\\\");
		
		// Recuperation du parametre de connexion au serveur
		Params.url = "http://127.0.0.1:9090/api/v1/supergames";
//		String[] param = utilDao.getParamUrl();
//		if(param!=null && param.length>1) {
//			Params.url = "http://"+param[3]+":"+param[2]+"/"+param[4];
//		}
		System.out.println("Params.url: "+Params.url);
		//Recherche de tous les partners actifs
		
		List<Partner> partners = partnerDao.getAllPartners();
		System.out.println("Partenaires actifs: "+partners.size());
		
		for (Partner partner : partners) {
			coderace = partner.getCoderace();
			//.System.out.println("Partenaires actifs coderace: "+coderace);
//			//Verification des configurations de bases
//			//** Entrée dans la table Keno **
//			String coderace = partner.getCoderace();
//			Keno keno = kenoDao.find_Single_draw(coderace);
//			
//			if(keno == null) {
//				//On ajoute une entrée pour le partner dans la table Keno
//				System.out.println("Ajout d'une ligne dans Keno pour le partenaire - "+coderace);
//				Keno ken = new Keno();
//				ken.setDrawNumK("1");
//				ken.setCoderace(coderace);
//				int nb_race = kenoDao.create(ken);
//				if(nb_race > 0) {
//					System.out.println("Entrée ajoutée avec succes pour "+coderace);
//				}
//			}
//			
//			//** Entré dans game_cycle
//			GameCycle gmc = game_cycleDao.findByGame(partner.getIdpartner(), "K");
//			if(gmc == null) {
//				//Ajout d'une entrée dans gameCycle
//				System.out.println("Ajout d'une entrée dans la table des cycles pour "+coderace);
//				GameCycle gm = new GameCycle();
//				gm.setPercent(95d);
//				gm.setArchive(0);
//				gm.setArrangement("1-2-3-4-5-6-7-8-9-10-11-12-13-14-15-16-17-18-19-20");
//				gm.setTour(10);
//				gm.setJeu("K");
//				gm.setCurr_percent(0);
//				gm.setPosition(1);
//				gm.setHitfrequence(10);
//				gm.setJkpt(0);
//				gm.setMise(0);
//				gm.setMisef(1);
//				gm.setPartner(partner.getIdpartner());
//				gm.setPayout(0);
//				gm.setRefundp(0);
//				gm.setStake(0d);
//				gm.setDate_fin("16-01-2021,00:17");
//			
//				game_cycleDao.create(gm);
//			}
//			
//			//** Entrée dans la table Config
//			Config cfg = configDao.find(coderace);
//			if(cfg == null) {
//				//ajout d'une de configuration
//				System.out.println("Ajout d'une entrée dans la table des Config pour "+coderace);
//				Config conf = new Config();
//				conf.setCoderace(coderace);
//				configDao.create(conf);
//			}
//			
//			
//		  //***** Recherche de tous les caissiers d'un partenaires *****//
//			System.out.println("Recherche de tous les caissiers du partenaire "+coderace);
//			ArrayList<Caissier> caissiers = caissierDao.findByPartner(coderace);
//			for(Caissier cais : caissiers) {
//				String[] imvt = airtimeDao.findCaisMvt(cais.getId());
//				if((imvt.length > 1 && imvt[1]!="null") || imvt.length == 0) {
//					airtimeDao.createMvt(cais.getId(), 0);
//				}
//			}
//			
//			
//			//Lancement des instances de Keno
//			System.out.println("--- Lancement des instances de Keno ---");
//			
//			ControlDisplayKeno cds = new ControlDisplayKeno(coderace);
//			int rang = UtileKeno._checkExistingSameDisplayCoderace(coderace);
//			if(rang == -1){
//				UtileKeno._display_draw.add(cds);
//				rang = UtileKeno._display_draw.size();
//				cds.setRang(rang);
//				cds.setTimeKeno(UtileKeno.timeKeno);
//				Refresh refresh = new Refresh(cds, coderace);
//				refresh.start();
//			}
//			else {
//				cds.setRang(rang);
//			}
//			
//			System.out.println("Nombre de display connectÃ©s: "+UtileKeno._display_draw.size()+" coderace: "+cds.getCoderace());
//			
//		
		}
		
		System.out.println("initialise  "+refresh.getName());
		if(refresh._getState()) 
			refresh.stop();
		refresh.setName(coderace);
		refresh.start();
	//	mapRefresh.put(coderace, refresh);
		
		/*
		if(mapRefresh.containsKey(coderace)) {
			Refresh re = mapRefresh.get(coderace);
			re.stop();
			re.start();
		}
		else {
			Refresh refresh = new Refresh(null, coderace);
			mapRefresh.put(coderace, refresh);
			refresh.start();
		}
		*/
	}
}
