package business;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONException;

import config.Params;
import config.UtileKeno;
import modele.ControlDisplayKeno;
import modele.Keno;
import modele.KenoRes;
import modele.Partner;
import superbetDAO.DAOConfigurationException;
import superbetDAO.DAOFactory;
import superbetDAO.KenoDAO;
import superbetDAO.PartnerDAO;
import superbetDAO.api.exeception.DAOAPIException;
import superbetDAO.api.implementations.SuperGameDAOAPI;
import superbetDAO.api.interfaces.ISuperGameDAOAPILocal;


@SuppressWarnings("deprecation")
public class Refresh implements Runnable {
	
	private static Thread thread;
	
	
	private String coderace;
	public static ControlDisplayKeno cds;
	
	public static String RESULT  = "";
	
	public static boolean isDraw = false; //controle si le tirage est en cours
	public static boolean countDown = true; //controle le compteur de temps avant tirage
	public static String drawCombi = null;
	public static int drawNum = 0;
	private KenoDAO kenoDao;
	private PartnerDAO partnerDao;
	List<Partner> partners;
	private boolean search_draw;
	private Date datecreation;
	private boolean alive;
	private int drawCount = 130;
	private ExecutorService executor;
	private Future<String> future;
	
	KenoRes b;
	private  ISuperGameDAOAPILocal  supergameAPI;
	
	public Refresh(){
		//Refresh.cds = cds;
		this.datecreation = new Date();
		thread = new Thread(this);
		this.alive = true;
		this.kenoDao = DAOFactory.getInstance().getKenoDao();
		this.partnerDao = DAOFactory.getInstance().getPartnerDao();
		supergameAPI = SuperGameDAOAPI.getInstance();
	}
	
	
	public String getName() {
		return this.coderace;
	}
	
	public void setName(String name) {
		this.coderace = name;
	}
	
	
	public Date getDatecreation() {
		return datecreation;
	}


	public void setDatecreation(Date datecreation) {
		this.datecreation = datecreation;
	}


	@Override
	public void run() {
		search_draw = false;
		this.alive = true;
		b = new KenoRes();
		Keno k;
		//UtileKeno.timeKeno = 
		executor = Executors.newSingleThreadExecutor();
		
		while(alive){
			//System.out.println("gamestate - "+UtileKeno.gamestate);
			drawCount = 130;
			try {
				//recup�ration du temps

				if(countDown) {
					 UtileKeno.timeKeno--;
				}

				if(UtileKeno.timeKeno == 11 && UtileKeno.gamestate == 1) {
					UtileKeno.canbet = false;
					UtileKeno.messagek = "pari ferm�";
					UtileKeno.drawKeno = "";
					future = calculateDraw();
					System.out.println("REFRESHTIME KENO: "+UtileKeno.timeKeno+" *** "+UtileKeno.drawKeno);
				}
				else if(UtileKeno.gamestate == 1 && UtileKeno.timeKeno > 11) {
					UtileKeno.canbet = true;
					UtileKeno.drawKeno = "";
				}
				
				if(UtileKeno.timeKeno < 1 ) {
					countDown = false;
					
					if (future != null) {
						UtileKeno.drawKeno = future.get();
						isDraw = true;
						UtileKeno.timeKeno = 185;
					}
					else {
						UtileKeno.drawKeno = "";
						future = calculateDraw();
					}
					
				}
				
				if (StringUtils.isBlank(coderace)) {
					partners = partnerDao.getAllPartners();
					for(Partner ps : partners) {
						if (ps.getActif() == 1) {
							this.setName(coderace);
						}
					}
				}
				//recuperation de lobjet keno
				b =  supergameAPI.getSuperGameDAO().retrieveCombi(Params.url, coderace);	
			//	System.out.println("KRES : "+UtileKeno.drawKeno);
				if (b != null) {
					
					UtileKeno.drawKeno = b.getDrawnumbK();
					UtileKeno.multiplicateur = b.getMultiplicateur();
					UtileKeno.drawnumk = b.getDrawnumK();
			//		System.out.println("KRES drawnumk: "+UtileKeno.drawnumk);
					UtileKeno.str_draw_combi = b.getStr_draw_combi();
				//	UtileKeno.gamestate = b.getGameState();
					UtileKeno.bonuskamount = b.getBonusKamount();
				//	System.out.println("Refresh - state: "+UtileKeno.gamestate+" search_draw: "+search_draw);
					if(UtileKeno.gamestate == 3) {
						UtileKeno.canbet = true;
						UtileKeno.messagek = "pari ouvert - tirage en cours";
					}
					
					if((UtileKeno.gamestate == 2)  && !search_draw && !UtileKeno.drawKeno.equalsIgnoreCase("")) {

							search_draw = true;
							k = new Keno();
							k.setBonusKamount(""+b.getBonusKamount());
							k.setDrawnumbK(UtileKeno.drawKeno);
							k.setHeureTirage(b.getHeureTirage());
							k.setDrawnumK(""+b.getDrawnumK());
							k.setMultiplicateur(b.getMultiplicateur());
							k.setCoderace(coderace);
							kenoDao.create(k);
							

						
					}
				/*	else if(UtileKeno.gamestate == 1 && search_draw) {
						search_draw = false;
						UtileKeno.messagek = "pari ouvert";
						countDown = true;
					}
					*/
					while(!countDown) {

					//	System.out.println("Tirage en cours: "+drawCount);
						Thread.sleep(5000);
						drawCount = drawCount - 5;
						if(UtileKeno.gamestate == 3) {
							UtileKeno.canbet = true;
							UtileKeno.messagek = "pari ouvert - tirage en cours";
							b =  supergameAPI.getSuperGameDAO().retrieveCombi(Params.url, coderace);	
							UtileKeno.drawKeno = b.getDrawnumbK();
							
							UtileKeno.multiplicateur = b.getMultiplicateur();
							UtileKeno.drawnumk = b.getDrawnumK();
					//		System.out.println("KRES drawnumk: "+UtileKeno.drawnumk);
							UtileKeno.str_draw_combi = b.getStr_draw_combi();
							UtileKeno.bonuskamount = b.getBonusKamount();
						}
						if(UtileKeno.gamestate == 1) {
							countDown = true;
							search_draw = false;
							UtileKeno.canbet = true;
							
						}
					}
					
				}
				
	/*			
				while(isDraw) {
					System.out.println("isDraw: "+drawCount);
					try {
						drawCount--;
						
						if(drawCount > 100){
							UtileKeno.gamestate = 2;
							UtileKeno.timeKeno = 185;
							UtileKeno.canbet = false ;
							try {
								int b = supergameAPI.getSuperGameDAO().getStates(Params.url, UtileKeno.gamestate, coderace);
								System.out.println("Updatebonus gamestate: "+b);
							 } catch (IOException | JSONException | URISyntaxException | DAOAPIException e) {
									e.printStackTrace();
							 }
						}
						
						if(drawCount == 100){
							UtileKeno.canbet = true;
							UtileKeno.gamestate = 3;
							UtileKeno.timeKeno = 185;
							// appel du service
							
						}
						
						if(drawCount < 0 || UtileKeno.gamestate == 1){
							isDraw = false;
							//UtileKeno.gamestate = 4;
							// appel du service
						}
						
						Thread.sleep(1000);
					}
					catch(Exception e) {
						System.err.println(e);
					}
				}
			*/
			  //  System.out.println("UtileKeno.timeKeno: "+UtileKeno._timeKeno+" "+controltime+"  "+this.getDatecreation());
				Thread.sleep(1000);
			} catch (IOException | JSONException | URISyntaxException | DAOAPIException | InterruptedException | ExecutionException e) {
				//e.printStackTrace();
				System.err.print("REFRESHK ERROR: "+e);
				search_draw = false;
				UtileKeno.drawKeno = "";
				Thread.currentThread().interrupt();
			}
		}
		
	}
	
	public void start(){
		//thread = new Thread(this);
		
		thread.start();
	}
	
	public void stop(){
		this.alive = false;
		thread.stop();
	}
	public void suspend(){
		thread.suspend();
	}
	public void resume(){
		thread.resume();
	}
	public boolean _getState() {
		return this.thread.isAlive();
	}
	
	public static Refresh getInstance() throws DAOConfigurationException {
		
		Refresh instance = new  Refresh();
				return instance;
    }

    private Map<String, Integer> triAvecValeur( Map<String, String> m ){
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		
	      Iterator it = m.entrySet().iterator();
	      while(it.hasNext()) {
	           Map.Entry entry = (Map.Entry)it.next();
	           map.put((String)entry.getKey(), Integer.parseInt((String)entry.getValue()));
	        //   System.out.println(entry.getKey() + ": "+entry.getValue());
	      }
	
		
	   List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>( map.entrySet() );
//	   Collections.sort( list, new Comparator<Map.Entry<String, Integer>>(){
//	      public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 ){
//	          return (o1.getValue()).compareTo( o2.getValue());
//	      }
//	   });
	   
	   Collections.sort( list, (o1, o2) -> (o1.getValue()).compareTo( o2.getValue()));

	   Map<String, Integer> map_apres = new LinkedHashMap<String, Integer>();
	   for(Map.Entry<String, Integer> entry : list)
	     map_apres.put( entry.getKey(), entry.getValue() );
	   return map_apres;
	}
    
    private Future<String> calculateDraw() {
    	
    	return executor.submit(() -> {
    		String resultat =  this.supergameAPI.getSuperGameDAO().retrieveCombinaison(Params.url, UtileKeno.drawnumk, coderace);
    		System.out.println("Resultat: "+resultat);
    		return resultat;
    	});
    }
	
}
