package business;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import modele.GameCycle;
import modele.Keno;
import modele.KenoRes;
import modele.Misek;
import modele.Miset;
import modele.Partner;
import superbetDAO.CaissierDAO;
import superbetDAO.ConfigDAO;
import superbetDAO.DAOConfigurationException;
import superbetDAO.DAOFactory;
import superbetDAO.GameCycleDAO;
import superbetDAO.KenoDAO;
import superbetDAO.MisekDAO;
import superbetDAO.Misek_tempDAO;
import superbetDAO.MisetDAO;
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
	
	private ControlDisplayKeno _cds;
	public static boolean isDraw = false; //controle si le tirage est en cours
	public static boolean countDown = true; //controle le compteur de temps avant tirage
	public static String drawCombi = null;
	public static int drawNum = 0;
	private String multiplix;
	//private int drawCount;
	private MisekDAO misekDao;
	private MisetDAO misetDao;
	private KenoDAO kenoDao;
	private ConfigDAO configDao;
	private CaissierDAO caissierDao;
	private PartnerDAO partnerDao;
	private GameCycleDAO gmcDao;
	private Misek_tempDAO misektpDao;
	List<Partner> partners;
	private double miseTotale;
	private double miseTotale_s; //cycle suivant
	private String arrangement_pos;
	private double sumdist, gMp, gmp;
	private ArrayList<Misek> listTicket = new ArrayList();;
	private  Map<Miset, Misek> mapTicket = new HashMap<Miset, Misek>();
	private  Map<Miset, Misek> map_wait = new HashMap<Miset, Misek>();
	private int refill;
	private int xtour;
	private Long misef;
	private boolean search_draw;
	private boolean controltime;
	private boolean dead_round = false;
	private int idmisek_max;
	private GameCycle gmc;
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
		controltime = false;
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
				//UtileKeno.timeKeno = this.supergameAPI.getSuperGameDAO().getTime(Params.url, coderace);
				if(countDown) {
					// System.out.println("REFRESHTIME KENO: "+UtileKeno.timeKeno);
					 UtileKeno.timeKeno--;
				}
				
//				if(UtileKeno.timeKeno == 100) {
//					Map<String, String> s = new HashMap();
//					s = supergameAPI.getSuperGameDAO().sumKeno(Params.url, coderace);
//					System.out.println("Avant le tri: "+s);
//					HashMap<String, Integer> m = triAvecValeur( s );
//					System.out.println("Apr�s le tri: "+m);
//				}
					
		//		System.out.println("UtileKeno.timeKeno: "+UtileKeno.timeKeno);
			
				
				if(UtileKeno.timeKeno == 11 && UtileKeno.gamestate == 1) {
					UtileKeno.canbet = false;
					UtileKeno.messagek = "pari ferm�";
					//UtileKeno.drawKeno = this.supergameAPI.getSuperGameDAO().retrieveCombinaison(Params.url, UtileKeno.drawnumk, coderace);
					future = calculateDraw();
					System.out.println("REFRESHTIME KENO: "+UtileKeno.timeKeno+" *** "+UtileKeno.drawKeno);
				}
				else if(UtileKeno.gamestate == 1 && UtileKeno.timeKeno > 11) {
					UtileKeno.canbet = true;
				}
				
				if(UtileKeno.timeKeno < 1 ) {
					countDown = false;
					if (future != null) {
						UtileKeno.drawKeno = future.get();
						isDraw = true;
						UtileKeno.timeKeno = 185;
					}
					else {
						future = calculateDraw();
					}
					
					System.out.println(" FUTURE*** "+UtileKeno.drawKeno);
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
					
					if((UtileKeno.gamestate == 2)  && !search_draw) {
						//UtileKeno.drawKeno = "";
					//	UtileKeno.drawKeno = b.getDrawnumbK();
						
						//System.out.println("Refresh - time: "+UtileKeno.timeKeno+" search_draw: "+search_draw);
						if(!UtileKeno.drawKeno.equalsIgnoreCase("")) {
							//System.out.println("Refresh - drawKeno: "+b.getDrawnumK()+"  "+UtileKeno.drawKeno);
							search_draw = true;
							k = new Keno();
							k.setBonusKamount(""+b.getBonusKamount());
							k.setDrawnumbK(UtileKeno.drawKeno);
							k.setHeureTirage(b.getHeureTirage());
							k.setDrawnumK(""+b.getDrawnumK());
							k.setMultiplicateur(b.getMultiplicateur());
							k.setCoderace(coderace);
							int n = kenoDao.create(k);
							System.out.println("line local cree: "+n);
							
						}
						
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
				e.printStackTrace();
				search_draw = false;
				UtileKeno.drawKeno = "";
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

    private HashMap<String, Integer> triAvecValeur( Map<String, String> m ){
		
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		
	      Iterator it = m.entrySet().iterator();
	      while(it.hasNext()) {
	           Map.Entry entry = (Map.Entry)it.next();
	           map.put((String)entry.getKey(), Integer.parseInt((String)entry.getValue()));
	        //   System.out.println(entry.getKey() + ": "+entry.getValue());
	      }
	
		
	   List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>( map.entrySet() );
	   Collections.sort( list, new Comparator<Map.Entry<String, Integer>>(){
	      public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 ){
	          return (o1.getValue()).compareTo( o2.getValue());
	      }
	   });

	   HashMap<String, Integer> map_apres = new LinkedHashMap<String, Integer>();
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
