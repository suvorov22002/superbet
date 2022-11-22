package config;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.apache.commons.lang3.time.DateUtils;

import modele.Partner;
import superbetDAO.AirtimeDAO;
import superbetDAO.CaissierDAO;
import superbetDAO.DAOFactory;
import superbetDAO.MisekDAO;
import superbetDAO.PartnerDAO;
import superbetDAO.VersementDAO;

public class StartWorker {
	
private static TimerTask task;
	
	private static java.util.Timer timer;
	private static PartnerDAO partnerDao;
	private static CaissierDAO caissierDao;
	private static AirtimeDAO airtimeDao;
	private static MisekDAO misekDao;
	private static VersementDAO versementDao;
	private static DAOFactory daof;
	
	public StartWorker() {
		misekDao = daof.getInstance().getMisekDao();
		airtimeDao = daof.getInstance().getAirtimeDao();
		caissierDao = DAOFactory.getInstance().getCaissierDao();
		partnerDao = DAOFactory.getInstance().getPartnerDao();
		versementDao = DAOFactory.getInstance().getVersementDao();
	}
	
	public static void initChecking(){
		try{
			
			if(task != null) task.cancel();
			if(timer != null) timer.cancel();
				

			task = new TimerTask(){
				@Override
				public void run(){
					
					try {
						if(Params.mapHeureOuv.containsKey(new SimpleDateFormat("HH").format(new Date()))) {
							process();
						}
							
					}catch(Exception e){
						//e.printStackTrace();
					}
				}	
			};

			timer = new java.util.Timer(true);
			int sec = 60;
			int min = 15;
			timer.schedule(task, DateUtils.addMinutes(new Date(), 5) , min*sec*1000);	

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void runChecking(){
		try{
			
			if(task != null) task.cancel();
			if(timer != null) timer.cancel();
			
			task = new TimerTask(){
				@Override
				public void run(){
					
					try {
						if(Params.mapHeureOuv.containsKey(new SimpleDateFormat("HH").format(new Date()))) {
							System.out.println("OUVERTURE DES CAISSES 237");
							process();
						}
							
					}catch(Exception e){
						e.printStackTrace();
					}
				}	
			};

			timer = new java.util.Timer(true);
			int sec = 60;
			int min = 15;
			timer.schedule(task, DateUtils.addSeconds(new Date(), 5) , min*sec*1000);	

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 */
	public static void cancelChecking(){
		try{
			
			if(task != null) task.cancel();
			if(timer != null) timer.cancel();
						
			task = null;
			timer = null;
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 */
	public static void process(){
		//recherche de tous les partners
		System.out.println("Ouverture caisse");
		List<Partner> listPartners = partnerDao.getAllPartners();
		
		try {
			
			for(Partner partn : listPartners) {
				if("closed".equalsIgnoreCase(partn.getCob())) {
				  partnerDao.update_cob("opened",partn.getCoderace());
			 }
				  
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}
