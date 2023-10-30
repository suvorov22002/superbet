package config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import business.RecordUser;
import business.Refresh;
import superbetDAO.DAOFactory;

public class InitialisationDAOFactory implements ServletContextListener{
	
	private static final String ATT_DAO_FACTORY = "daofactory";
	private static final String ATT_DATA_RECORD = "recordUSer";
	private static final String  ATT_TOKEN = "token";
	
	private DAOFactory daoFactory;
	private RecordUser recordUsers;
	private long token;
	private  Refresh refresh;
	
	

	
	
	@Override
	public void contextInitialized( ServletContextEvent event ) {
		/* Récupération du ServletContext lors du chargement de l'application */
		ServletContext servletContext = event.getServletContext();
		
		recordUsers = new RecordUser();
		this.recordUsers = RecordUser.getInstance();
		
		this.refresh = Refresh.getInstance();
		
		/* Instanciation de notre DAOFactory */
		this.daoFactory = DAOFactory.getInstance();
		
		String txtDate=new SimpleDateFormat("dd/MM/yyyy,H:m:s", Locale.FRANCE).format(new Date());
		try {
			this.token = Timestamp.givetimestamp(txtDate);
			System.out.println("I-TOKEN: "+this.token);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//config heure de mise � jour des caisses;
		
		Params.mapHeure.put("23", "23");
		
		Params.mapHeureOuv.put("06", "06");

		
		/* Enregistrement dans un attribut ayant pour portée toute l'application */
		servletContext.setAttribute( ATT_DAO_FACTORY, this.daoFactory );
		servletContext.setAttribute( ATT_DATA_RECORD, this.recordUsers );
		servletContext.setAttribute(ATT_TOKEN, this.token);
		
		InitialKeno init_keno = new InitialKeno();
		init_keno.intializeKeno();
		
//		CobWorker cobworker = new CobWorker();
//		cobworker.runChecking();
		
		StartWorker startworker = new StartWorker();
//		startworker.runChecking();

	}
	
	@Override
	public void contextDestroyed( ServletContextEvent event ) {
	/* Rien à réaliser lors de la fermeture de l'application */
	
	}
}
