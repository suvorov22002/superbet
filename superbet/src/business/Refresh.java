package business;

import java.io.IOException;
import java.net.InetAddress;
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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;
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
	private static Log log = LogFactory.getLog(Refresh.class);
	
	private String coderace;
	public static ControlDisplayKeno cds;
	
	public static String RESULT  = "";
	
	//public static boolean isDraw = false; //controle si le tirage est en cours
	public static boolean countDown; //controle le compteur de temps avant tirage
	public static String drawCombi = null;
	public static int drawNum = 0;
	private int renewCounter;
	private KenoDAO kenoDao;
	private PartnerDAO partnerDao;
	List<Partner> partners;
	private boolean search_draw;
	private boolean lecture;
	private Date datecreation;
	private ExecutorService executor;
	private Future<String> future;
	KenoRes b;
	private  ISuperGameDAOAPILocal  supergameAPI;
	
	public Refresh(){
		//Refresh.cds = cds;
		this.datecreation = new Date();
		thread = new Thread(this);
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
		lecture = false;
		countDown = true;
		renewCounter = 0;
		b = new KenoRes();
		Keno k;
		InetAddress inet = null;
		//UtileKeno.timeKeno = 
		executor = Executors.newSingleThreadExecutor();

		// Recuperation des valeurs du tirage
		try {
			collectDrawInfos();
			//inet = InetAddress.getByName("ec2-35-180-126-248.eu-west-3.compute.amazonaws.com");
		} catch (Exception e) {
			e.printStackTrace();
		}

		while(true){
			
			try {
				//recup�ration du temps

				if(countDown && UtileKeno.timeKeno > 0) {
					
					 UtileKeno.timeKeno--;
					 //System.out.println("REFRESHTIME KENO: "+UtileKeno.timeKeno);
					 //System.out.println(inet.isReachable(10000) ? "Host is reachable" : "Host is NOT reachable");

				}

				if(UtileKeno.timeKeno == 11 && UtileKeno.gamestate == 1) {
					
					log.info("REFRESHTIME KENO - FERMETURE DES MISE: "+UtileKeno.timeKeno+" *** "+UtileKeno.drawKeno);
					UtileKeno.canbet = false;
					UtileKeno.messagek = "pari ferme";
					UtileKeno.drawKeno = "";
					future = calculateDraw();
		
				}
				else if(UtileKeno.gamestate == 1 && UtileKeno.timeKeno > 11) {
					
					UtileKeno.canbet = true;
					UtileKeno.drawKeno = "";
					
				}
				
				if(UtileKeno.timeKeno < 1 ) {
					
					log.info("REFRESHTIME KENO - TIMER OFF: "+UtileKeno.timeKeno+" *** "+UtileKeno.drawKeno);
					//countDown = false;
					UtileKeno.canbet = false;
					search_draw = false;
					lecture = true;
					
					if (future != null) {
						log.info("2 - REFRESHTIME KENO - TIMER OFF: "+UtileKeno.timeKeno+" *** countDown: "+countDown);
						UtileKeno.drawKeno = future.get();
						//UtileKeno.timeKeno = 185;
						collectDrawInfos();

					}
					else {
						log.info("3 - REFRESHTIME KENO - TIMER OFF: "+UtileKeno.timeKeno+" *** "+UtileKeno.drawKeno);
						UtileKeno.drawKeno = "";
						future = calculateDraw();
						collectDrawInfos();
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

					
					if((UtileKeno.gamestate == 2)  && !search_draw /*&& !StringUtils.isBlank(UtileKeno.drawKeno)*/) {
						
						log.info("UtileKeno.gamestate - 2: " + UtileKeno.gamestate +
								" - UtileKeno.canbet: " + UtileKeno.canbet + " - UtileKeno.drawnumk: " + UtileKeno.drawnumk);
								
							b = collectDrawInfos();
							
							search_draw = true;
							lecture = true;
							
							k = new Keno();
							k.setBonusKamount(String.valueOf(b.getBonusKamount()));
							k.setDrawnumbK(UtileKeno.drawKeno);
							k.setHeureTirage(b.getHeureTirage());
							k.setDrawnumK(String.valueOf(b.getDrawnumK()));
							k.setMultiplicateur(b.getMultiplicateur());
							k.setCoderace(coderace);
							kenoDao.create(k);
							
							countDown = false;
							UtileKeno.timeKeno = 185;
					}
					
//					if(lecture == true) {
//						log.info(" - UtileKeno.canbet: " + UtileKeno.canbet + " - countDown: " + countDown);
//						collectDrawInfos();
//					}
					
					while(countDown == false) {
						
						if(lecture == true) {
							log.info("3 - UtileKeno.canbet: " + UtileKeno.canbet + " - countDown: " + countDown + " renewCounter = " + renewCounter);
							renewCounter++;
							if(renewCounter > 9) {
								collectDrawInfos();
								renewCounter = 0;
							}
						}
//						System.out.println("countDown UtileKeno.gamestate: " + UtileKeno.gamestate +
//								" - UtileKeno.canbet: " + UtileKeno.canbet + " - UtileKeno.drawnumk: " + UtileKeno.drawnumk);
						
						if(UtileKeno.gamestate == 3 && !UtileKeno.canbet) {
							
							lecture = false;
							log.info("UtileKeno.gamestate - 3: " + UtileKeno.gamestate +
									" - UtileKeno.canbet: " + UtileKeno.canbet + " - UtileKeno.drawnumk: " + UtileKeno.drawnumk);
							UtileKeno.canbet = true;
							UtileKeno.messagek = "pari ouvert - tirage en cours";
							
							collectDrawInfos();
							
						}
						
						// Relance du compteur apres la fin du tirage.
						if(UtileKeno.gamestate == 1 && UtileKeno.canbet) {
							log.info("countDown -- 2: " + countDown);
							countDown = true;
							search_draw = false;
							UtileKeno.canbet = true;
							collectDrawInfos();
							
						}
						
						Thread.sleep(5000);
					}
			
			  //  System.out.println("UtileKeno.timeKeno: "+UtileKeno._timeKeno+" "+controltime+"  "+this.getDatecreation());
				Thread.sleep(1000);
			} catch (JSONException | URISyntaxException | DAOAPIException | IOException | ExecutionException e) {
				//e.printStackTrace();
				log.error("REFRESHK INTERRUPT: " + e.getMessage());
				search_draw = false;
				
				if(StringUtils.isBlank(UtileKeno.drawKeno)) {
					UtileKeno.drawKeno = "";
					future = calculateDraw();
				}
					
				
			}
			catch(InterruptedException ex) {
				log.error("REFRESHK ERROR: " + ex.getMessage());
				//future = calculateDraw();
				Thread.currentThread().interrupt();
				this.stop();
				this.start();
			}
		}
		
	}

	private KenoRes collectDrawInfos() throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{

		b =  supergameAPI.getSuperGameDAO().retrieveCombi(Params.url, coderace);
		
		if(b != null) {
			UtileKeno.drawKeno = b.getDrawnumbK();
			UtileKeno.multiplicateur = b.getMultiplicateur();
			UtileKeno.drawnumk = b.getDrawnumK();
			UtileKeno.str_draw_combi = b.getStr_draw_combi();
			UtileKeno.bonuskamount = b.getBonusKamount();
			log.info("KRES drawnumk: "+UtileKeno.drawnumk +" Bonus: " + UtileKeno.bonuskamount + " UtileKeno.gamestate: " + UtileKeno.gamestate);
			if (UtileKeno.gamestate == 1) {
				UtileKeno.canbet = true;
			}
		}
		
		return b;

	}

	public void start(){
		//thread = new Thread(this);
		
		thread.start();
	}
	
	public void stop(){
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
    		log.info("Resultat: "+resultat);
    		return resultat;
    	});
    }
	
}
