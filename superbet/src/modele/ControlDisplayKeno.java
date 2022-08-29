package modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import business.Refresh;
import superbetDAO.ConfigDAO;
import superbetDAO.DAOFactory;
import superbetDAO.KenoDAO;
import superbetDAO.MisekDAO;
import superbetDAO.MisetDAO;
import superbetDAO.PartnerDAO;
import config.UtileKeno;

public class ControlDisplayKeno implements Runnable{
	
	public static final String CONF_DAO_FACTORY = "daofactory";
	private static KenoDAO kenoDao;
	private MisekDAO misekDao;
	private MisetDAO misetDao;
	private PartnerDAO partnerDao;
	private ConfigDAO configDao;
	private String multiplicateur[] = {"0.5", "1", "2"};
	public static String multiplix;
	private  String coderace;
	private  int timeKeno;
	private int gameState = 1; //etat du jeu 
	private String str_draw_combi = "";
	

	private  boolean isDraw = false; //controle si le tirage est en cours
	private  boolean countDown = true; //controle le compteur de temps avant tirage
	private  String drawCombik = "";
	private  int drawNumk = 0;
	private int drawCount = 25;
	private int rang;
	private int bonus = 0;
	private int end = 0;
	private int pos;
	private double rtp;
	private String arrangement = "";
	private int tour;
	
	private double sumdist;
	private Map<Miset, Misek> mapTicket;
	private double gMp;
	private double gmp;
	
	private static Thread thread;
	
	public void start(){
		thread = new Thread(this);
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
	
	public ControlDisplayKeno(String coderace){
		this.coderace = coderace;
		kenoDao = DAOFactory.getInstance().getKenoDao();
		misekDao = DAOFactory.getInstance().getMisekDao();
		misetDao = DAOFactory.getInstance().getMisetDao();
		partnerDao = DAOFactory.getInstance().getPartnerDao();
		configDao = DAOFactory.getInstance().getConfigDao();

	}
	
	public ControlDisplayKeno(double sumdist,Map<Miset, Misek> mapTicket,double gMp,double gmp) {
		this.sumdist = sumdist;
		this.mapTicket = mapTicket;
		this.gMp = gMp;
		this.gmp = gmp;
	}

	public String getCoderace() {
		return coderace;
	}

	public void setCoderace(String coderace) {
		this.coderace = coderace;
	}

	public int getTimeKeno() {
		return timeKeno;
	}

	public void setTimeKeno(int timeKeno) {
		this.timeKeno = timeKeno;
	}

	public boolean isDraw() {
		return isDraw;
	}

	public void setDraw(boolean isDraw) {
		this.isDraw = isDraw;
	}

	public boolean isCountDown() {
		return countDown;
	}

	public void setCountDown(boolean countDown) {
		this.countDown = countDown;
	}

	public String getDrawCombik() {
		return drawCombik;
	}

	public void setDrawCombik(String drawCombik) {
		this.drawCombik = drawCombik;
	}

	public int getDrawNumk() {
		return drawNumk;
	}

	public void setDrawNumk(int drawNumk) {
		this.drawNumk = drawNumk;
	}

	public int getDrawCount() {
		return drawCount;
	}

	public void setDrawCount(int drawCount) {
		this.drawCount = drawCount;
	}

	public int getRang() {
		return rang;
	}

	public void setRang(int rang) {
		this.rang = rang;
	}

	public int getBonus() {
		return bonus;
	}

	public void setBonus(int bonus) {
		this.bonus = bonus;
	}
	
	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}
	
	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public double getRtp() {
		return rtp;
	}

	public void setRtp(double rtp) {
		this.rtp = rtp;
	}

	public String getArrangement() {
		return arrangement;
	}

	public void setArrangement(String arrangement) {
		this.arrangement = arrangement;
	}

	public int getTour() {
		return tour;
	}

	public void setTour(int tour) {
		this.tour = tour;
	}
	
	public double getSumdist() {
		return sumdist;
	}

	public void setSumdist(double sumdist) {
		this.sumdist = sumdist;
	}

	public Map<Miset, Misek> getMapTicket() {
		return mapTicket;
	}

	public void setMapTicket(Map<Miset, Misek> mapTicket) {
		this.mapTicket = mapTicket;
	}

	public double getgMp() {
		return gMp;
	}

	public void setgMp(double gMp) {
		this.gMp = gMp;
	}

	public double getGmp() {
		return gmp;
	}

	public void setGmp(double gmp) {
		this.gmp = gmp;
	}

	public int getGameState() {
		return gameState;
	}

	public void setGameState(int gameState) {
		this.gameState = gameState;
	}
	
	public String getStr_draw_combi() {
		return str_draw_combi;
	}

	public void setStr_draw_combi(String str_draw_combi) {
		this.str_draw_combi = str_draw_combi;
	}

	public String getMultiplix() {
//		double n = generate(3);
//		multiplix = multiplicateur[(int)n]; 
		return multiplix;
	}

	private static void setMultiplix(String multiplix) {
		ControlDisplayKeno.multiplix = multiplix;
	}
	
	
	public void run() {
		Refresh.RESULT = buscarDraw(sumdist,mapTicket,gMp,gmp);
		
		//System.out.println("Refresh.RESULT "+Refresh.RESULT);
		this.drawCombik = Refresh.RESULT;
	}
	private String buscarDraw(double sumdist,Map<Miset, Misek> mapTicket,double gMp,double gmp){
		
		ArrayList<String> combis = new ArrayList<String>();
    	ArrayList<String> combi = new ArrayList<String>();
    	double sumDistTotale = 0;
    	boolean trouve = false;
    	String str = ""; 
    	String _str = "";
	    System.out.println("GMP: "+gMp+" GmP: "+gmp+" DIST: "+sumdist);	
	//    if(sumdist <= gmp || sumdist==0) {
	    	
	    if(sumdist <= 0) {
    		System.out.println("HERE 3");
    		
    		do {
    			combis.clear();
    			combi.clear();
    			str = "";
    			for(int i=1;i<81;i++)
    	    		combis.add(""+i);
    	    	for(int j=0;j<80;j++){
    	    		int index = generate(combis.size());
    	    		combi.add(combis.get(index));
    	    		combis.remove(index);
    	    	}
    	    	
    	    	
    	    	for(int k=0;k<19;k++){
    	    		str = str + combi.get(k) + "-";
    	    	}
    	    	str = str + combi.get(19);
    	    	double n = generate(3);
    			multiplix = multiplicateur[(int)n]; 
    			sumDistTotale = UtileKeno.verifTicketSum(mapTicket, coderace, str, multiplix);
    			_str = str;
    			if(gmp != 0) { //two sides knife
    				if(sumDistTotale == gmp) {
    					//System.out.println("this.rtpAAA "+this.rtp);
        				trouve = true;
        				this.rtp = this.rtp + sumdist - sumDistTotale;
        				
        			}
    			}
    			else {
    				
    				if(sumDistTotale == 0) {
    					trouve = true;
    					//this.rtp = sumdist;
    				}
    			}
    			
    		}
    		while(!trouve);
    		//System.out.println("this.rtp "+this.rtp);
    	}    
	  //  else if(sumdist > gMp) {
	    else if(sumdist > 0) {
	    		System.out.println("HERE 1");	
	    		long timeBefore = System.currentTimeMillis();
	    		double max = 0;
	    		double min = gMp;
	    		
	    		do {
	    			combis.clear();
	    			combi.clear();
	    			str = "";
	    			for(int i=1;i<81;i++)
	    	    		combis.add(""+i);
	    	    	for(int j=0;j<80;j++){
	    	    		int index = generate(combis.size());
	    	    		combi.add(combis.get(index));
	    	    		combis.remove(index);
	    	    	}
	    	    	
	    	    	
	    	    	for(int k=0;k<19;k++){
	    	    		str = str + combi.get(k) + "-";
	    	    	}
	    	    	str = str + combi.get(19);
	    	    	double n = generate(3);
	    			multiplix = multiplicateur[(int)n]; 
	    			sumDistTotale = UtileKeno.verifTicketSum(mapTicket, coderace, str, multiplix);
	    		//	System.out.println("HERE 1 sumDistTotale "+sumDistTotale);
//	    			if(gmp != 0) { //two sides knife
//	    				_str = str;
//	    				if(sumDistTotale == gmp) {
//	    				
//	    					//System.out.println("two sides knife ");
//	    					//System.out.println("this.rtp "+this.rtp);
//	    					//min = sumDistTotale;
//	        				trouve = true;
//	        				this.rtp = this.rtp + sumdist - sumDistTotale;
//	        				//this.rtp = sumdist - sumDistTotale;
//	        				
//	        			}
	    		//	}
	    		//	else {
		    			if(sumDistTotale <= sumdist && sumDistTotale >= max) {
		    				//System.out.println("HERE 1 sumDistTotale "+sumDistTotale);
		    				_str = str;
		    				max = sumDistTotale;
		    				//System.out.println("max = sumDistTotale "+max);
		    			}
		    			long timeAfter = System.currentTimeMillis() - timeBefore;
		    			
		    			if(/*sumDistTotale<=sumdist && sumDistTotale>gmp*/ timeAfter > 8000) {
		    				trouve = true;
//		    				if(gmp != 0) { 
//		    					this.rtp = sumdist - max;
//		    				}
//		    				else {
//		    					this.rtp = sumdist - max;
//		    				}
		    				this.rtp = sumdist - max;
		    				System.out.println("timeAfter "+timeAfter+" max: "+max);
		    				sumDistTotale = max;
		    			}  
	    		// }
	    		}
	    		while(!trouve);
	    	}	
	    	this.rtp = (double)((int)(this.rtp*100))/100;
	    	System.out.println("DISTRIBUTION: "+sumDistTotale+" ReFund: "+this.rtp);
	    setMultiplix(multiplix);
		return _str;
	}
    
    public String buscarDraw() {
    	ArrayList<String> combis = new ArrayList<String>();
    	ArrayList<String> combi = new ArrayList<String>();
    	for(int i=1;i<81;i++)
	    		combis.add(""+i);
	    	for(int j=0;j<80;j++){
	    		int index = generate(combis.size());
	    		combi.add(combis.get(index));
	    		combis.remove(index);
	    	}
	    	
	    	String str = "";
	    	for(int k=0;k<19;k++){
	    		str = str + combi.get(k) + "-";
	    	}
	    	str = str + combi.get(19);
	    	double n = generate(3);
			multiplix = multiplicateur[(int)n]; 
			
			return str;
    }
	
	 private int generate(int n){
			int lower = 0;
			int higher = n;
			int dbl;
			  
			dbl = (int) (Math.random() * (higher-lower)) + lower;
			return dbl;
	}
	 
	public int addKenos(int draw_num, String coderace) {
			int add = 0;
			Keno k = kenoDao.find_Max_draw_num(coderace, ""+draw_num);
			if(k != null) {
				return 1;
			}
			Keno keno = new Keno();
			keno.setDrawnumK(""+(draw_num));
			keno.setCoderace(coderace);
	        //Création d'une requête paramétrée. 
			add = kenoDao.create(keno);
			return add;
	}
	 
	public int addUpKeno(Keno keno){
		int add = 0;
		add = kenoDao.update(keno);
		return add;
	}
	
	public int endDraw(int draw_num){
		int num = draw_num-1;
		System.out.println("FIN DU TIRAGE: "+num);
		return kenoDao.updateDrawEnd(num);
	}
	
	private boolean giveBonusK(ArrayList<Misek> tail, Partner partner){
		int idp;
		int nb,add,winnerBonus=0;
		long codebonus;
		//On recupere l'identifiant du tirage
		//System.out.println("client: "+getCoderace()+" num: "+getDrawNumk());
		idp = kenoDao.getIdKenos(getCoderace(), getDrawNumk()-1);
		
		nb = tail.size();
		winnerBonus = generate(nb);	// recherche au radom du ticket vainqueur
		System.out.println("CODE: "+tail.get(winnerBonus).getBonusCodeK());
		codebonus = Long.parseLong(tail.get(winnerBonus).getBonusCodeK());
		
		UtileKeno.bonusK_down = partner.getBonuskamount();
		UtileKeno.bonusK_down = (double)((int)(UtileKeno.bonusK_down*100))/100;
		System.out.println("Bonus Win: "+UtileKeno.bonusK_down);
		
		UtileKeno.bonus_codeK = codebonus;
		
		double freeslipk = 0;
		UtileKeno.bonusKmin = 0;//
		freeslipk = misetDao.findFreeSlipByPartner("freekeno", partner.getIdpartner());
		UtileKeno.bonusKmin = 0.1 * UtileKeno.bonusK_down + freeslipk;
		UtileKeno.bonusKmin = (double)((int)(UtileKeno.bonusKmin*100))/100;
		add = partnerDao.update_bonusk(UtileKeno.bonusKmin, (int)codebonus, coderace); //mise à jour de la ligne du partner - bonus à zero
		misetDao.updateFree("freekeno", -freeslipk, partner.getIdpartner());
		if(add > 0){
			int _add = kenoDao.setCodeBonusK(UtileKeno.bonusK_down, UtileKeno.bonus_codeK, ""+idp); //mise à jour de la ligne dans spin
			if(_add > 0){
				return true;
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
	}
	
	public Keno lastDrawNum(String coderace){
		Keno keno = null;
		System.out.println("ControlDislay: "+coderace);
			keno = kenoDao.find_Max_draw(coderace);
		return keno;
	}
	
	public boolean manageBonusK(){
		double dbl = 0;
		// recherche s'il ya eu des paris le tour
		ArrayList<Misek> tail;
		System.out.println("NUMERO: "+getDrawNumk());
		tail = misekDao.searchMiseKdraw(""+(getDrawNumk()-1));
		Partner partner = partnerDao.find(getCoderace());
		System.out.println("TAIL: "+tail.size());
		if(tail.size() < 2){ //on compte le nombre de ticket tiré
			return false;
		}
		Config config = configDao.find(partner.getCoderace());
		double lower = config.getBnskmin();
		double higher = config.getBnskmax();
		
		dbl = (double) (Math.random() * (higher-lower)) + lower;
//		mbonus = UtileKeno.giveBonus[UtileKeno.stepBonus];
		
//		switch(UtileKeno.stepBonus){
//			case 0:
//				mbonus = (int) (Math.random() * (UtileKeno.giveBonus[UtileKeno.stepBonus]-5000)) + 5000;
//				break;
//			case 1:
//				mbonus = (int) (Math.random() * (UtileKeno.giveBonus[UtileKeno.stepBonus]-15000)) + 15000;
//				break;
//			case 2:
//				mbonus = (int) (Math.random() * (UtileKeno.giveBonus[UtileKeno.stepBonus]-25000)) + 25000;
//				break;
//			default:
//				break;
//		}
		System.out.println("MBONUSP: "+dbl+" BONUSP: "+partner.getBonuskamount());
		if(partner.getBonuskamount() > dbl){	
			if(giveBonusK(tail, partner)){
				return true;
			}
			else{
				return false;
			}
		}
		
		return false;
	}
	
	

}
