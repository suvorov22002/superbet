package modele;

import java.util.ArrayList;

import config.UtileSpin;
import superbetDAO.DAOFactory;
import superbetDAO.MisepDAO;
import superbetDAO.PartnerDAO;
import superbetDAO.SpinDAO;

public class ControlDisplaySpin {
	
	public static final String CONF_DAO_FACTORY = "daofactory";
	private static SpinDAO spinDao;
	private MisepDAO misepDao;
	private PartnerDAO partnerDao;
	
	
	private  String coderace;
	private  int timeSpin;
	
	private  boolean isDraw = false; //controle si le tirage est en cours
	private  boolean countDown = true; //controle le compteur de temps avant tirage
	private  String drawCombip = null;
	private  int drawNump = 0;
	private int drawCount = 25;
	private int rang;
	private int bonus = 0;
	private int end = 0;
	
	public ControlDisplaySpin(String coderace){
		this.coderace = coderace;
		spinDao = DAOFactory.getInstance().getSpinDao();
		misepDao = DAOFactory.getInstance().getMisepDao();
		partnerDao = DAOFactory.getInstance().getPartnerDao();
	}

	public String getCoderace() {
		return coderace;
	}

	public void setCoderace(String coderace) {
		this.coderace = coderace;
	}
	
	public int getRang() {
		return rang;
	}

	public void setRang(int rang) {
		this.rang = rang;
	}

	public int getTimeSpin() {
		return timeSpin;
	}

	public void setTimeSpin(int timeSpin) {
		this.timeSpin = timeSpin;
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

	public String getDrawCombip() {
		return drawCombip;
	}

	public void setDrawCombip(String drawCombip) {
		this.drawCombip = drawCombip;
	}

	public int getDrawNump() {
		return drawNump;
	}

	public void setDrawNump(int drawNump) {
		this.drawNump = drawNump;
	}

	public int getDrawCount() {
		return drawCount;
	}

	public void setDrawCount(int drawCount) {
		this.drawCount = drawCount;
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

	public String buscarDraw(){
		
		   int index = generate(36);
		    	
		   String str = ""+index;
		    	
		   return str;
		}
	    
	    private static String buscarDraw(double cIn, double cOut, double percent,
				ArrayList<EffChoicep> listTicketP) {
	    	
	    	return null;
	    }
		
		 private int generate(int n){
				int lower = 0;
				int higher = n;
				int dbl;
				  
				dbl = (int) (Math.random() * (higher-lower)) + lower;
				return dbl;
		}
		 
		 public int addSpins(int draw_num, String coderace) {
				int add = 0;
				Spin spin = new Spin();
				spin.setDrawNumP(""+(draw_num));
				spin.setCoderace(coderace);
		        //Création d'une requête paramétrée. 
				add = spinDao.create(spin);
				return add;
		}
		 
		public int addUpSpin(Spin spin){
			int add = 0;
			add = spinDao.update(spin);
			return add;
		}
		
		public int endDraw(int draw_num){
			//System.out.println("FIN TIRAGE: "+draw_num);
			int num = draw_num-1;
			System.out.println("CONTROL DISPLAY FIN DU TIRAGE: "+num);
			return spinDao.updateDrawEnd(num);
		}
		
		public Spin lastDrawNum(String coderace){
			Spin spin = null;
			if(UtileSpin._display_draw.size() != 0 ){
				//coderace = UtileSpin.display_draw.get(0);
				//System.out.println("DISPLAT: "+coderace);
				spin = spinDao.find_Max_draw(coderace);
				//setDrawNump(Integer.parseInt(spin.getDrawNumP()));
			}
			
			return spin;
		}
		
		public boolean manageBonusP(){
			int mbonus;
			
			// recherche s'il ya eu des paris le tour
			ArrayList<Misep> tail;
			System.out.println("NUMERO: "+getDrawNump());
			tail = misepDao.searchMisePdraw(""+(getDrawNump()-1));
			Partner partner = partnerDao.find(getCoderace());
			System.out.println("TAIL: "+tail.size());
			if(tail.size() < 2){ //on compte le nombre de ticket tiré
				return false;
			}
			
			mbonus = UtileSpin.giveBonus[UtileSpin.stepBonus];
			
			switch(UtileSpin.stepBonus){
				case 0:
					mbonus = (int) (Math.random() * (UtileSpin.giveBonus[UtileSpin.stepBonus]-5000)) + 5000;
					break;
				case 1:
					mbonus = (int) (Math.random() * (UtileSpin.giveBonus[UtileSpin.stepBonus]-15000)) + 15000;
					break;
				case 2:
					mbonus = (int) (Math.random() * (UtileSpin.giveBonus[UtileSpin.stepBonus]-25000)) + 25000;
					break;
				default:
					break;
			}
			System.out.println("MBONUSP: "+mbonus+" BONUSP: "+partner.getBonusPamount());
			if(partner.getBonusPamount() > mbonus){	
				if(giveBonusP(tail, partner)){
					return true;
				}
				else{
					return false;
				}
			}
			
			return false;
		}
		
		private boolean giveBonusP(ArrayList<Misep> tail, Partner partner){
			boolean isbonus;
			int idp;
			int nb,add,winnerBonus=0;
			long codebonus;
			ArrayList<String> sortMisep;
			
			isbonus = false;
			
			//On recupere l'identifiant du tirage
			System.out.println("client: "+getCoderace()+" num: "+getDrawNump());
			idp = spinDao.getIdSpin(getCoderace(), getDrawNump()-1);
			
			nb = tail.size();
			winnerBonus = generate(nb);	// recherche au radom du ticket vainqueur
			System.out.println("WINNERBONUS: "+winnerBonus+" CODE: "+tail.get(winnerBonus).getBonusCodeP());
			codebonus = Long.parseLong(tail.get(winnerBonus).getBonusCodeP());
			
			System.out.println("Bonus Win: "+partner.getBonusPamount());
			UtileSpin.bonusP_down = partner.getBonusPamount();
			UtileSpin.bonus_codeP = codebonus;
			
			add = partnerDao.update_bonusp(UtileSpin.bonusPmin, (int)codebonus, coderace); //mise à jour de la ligne du partner - bonus à zero
			
			if(add > 0){
				int _add = spinDao.setCodeBonusP(UtileSpin.bonusP_down, UtileSpin.bonus_codeP, ""+idp); //mise à jour de la ligne dans spin
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
}
