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

		System.out.println("Params.url: "+Params.url);
		//Recherche de tous les partners actifs
		
		List<Partner> partners = partnerDao.getAllPartners();
		System.out.println("Partenaires actifs: "+partners.size());
		
		for (Partner partner : partners) {
			coderace = partner.getCoderace();
		}
		
		System.out.println("initialise  "+refresh.getName());
		if(refresh._getState()) 
			refresh.stop();
		refresh.setName(coderace);
		refresh.start();
	
	}
}
