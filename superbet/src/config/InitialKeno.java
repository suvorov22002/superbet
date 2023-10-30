package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONException;

import business.Refresh;
import modele.ControlDisplayKeno;
import modele.Partner;
import superbetDAO.DAOFactory;
import superbetDAO.PartnerDAO;
import superbetDAO.api.exeception.DAOAPIException;
import superbetDAO.api.implementations.SuperGameDAOAPI;
import superbetDAO.api.interfaces.ISuperGameDAOAPILocal;

public class InitialKeno {
	
	private static PartnerDAO partnerDao;
	private static Refresh refresh;
	private static ISuperGameDAOAPILocal  supergameAPI;
	
	
	public static  Map<String, Refresh> mapRefresh = new HashMap<String, Refresh>();
	
	public InitialKeno() {
		DAOFactory.getInstance().getMisekDao();
		DAOFactory.getInstance().getAirtimeDao();
		DAOFactory.getInstance().getCaissierDao();
		partnerDao = DAOFactory.getInstance().getPartnerDao();
		DAOFactory.getInstance().getKenoDao();
		DAOFactory.getInstance().getGameCycleDao();
		DAOFactory.getInstance().getConfigDao();
		DAOFactory.getInstance().getUtilDao();
		this.refresh = Refresh.getInstance();
		UtileKeno._display_draw = new ArrayList<ControlDisplayKeno>();
		supergameAPI = SuperGameDAOAPI.getInstance();
	}
	
	public static void intializeKeno() {
		
		String coderace;
		System.out.println(">>> Initialisation du systeme <<<");
		try (InputStream input = new FileInputStream("C:/jeu/config.properties")) {

            Properties prop = new Properties();

            //load a properties file from class path, inside static method
            prop.load(input);

            //get the property value and print it out
            System.out.println(prop.getProperty("url"));
            Params.url = prop.getProperty("url");

        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Sorry, unable to find config.properties");
         // Recuperation du parametre de connexion au serveur
         	Params.url = "http://127.0.0.1:9090/api/v1/supergames";
        }
		
		try {
			
			

			List<Partner> partners = partnerDao.getAllPartners();
			coderace = partners.stream().filter(p -> p.getActif() == 1).map(p -> p.getCoderace()).findFirst().orElse("");
//			for (Partner partner : partners) {
//				coderace = partner.getCoderace();
//			}
			
			System.out.println("initialise  "+coderace);
			Boolean partnerExist = Boolean.FALSE;
			
			if(!StringUtils.isBlank(coderace)) {
				partnerExist =  supergameAPI.getSuperGameDAO().checkPartner(Params.url, coderace);
			}
			
			
			if(partnerExist) {
				
				if(refresh._getState()) 
					refresh.stop();
				refresh.setName(coderace);
				refresh.start();
				
			}	
			else {
				refresh.stop();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			refresh.stop();
		}
		
	}
}
