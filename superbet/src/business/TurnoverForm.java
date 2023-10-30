package business;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.codehaus.jettison.json.JSONException;

import config.Params;
import config.UtileKeno;
import modele.BonusSet;
import modele.Caissier;
import modele.GameCycle;
import modele.GameCycleDto;
import modele.Misek;
import modele.Partner;
import superbetDAO.CaissierDAO;
import superbetDAO.GameCycleDAO;
import superbetDAO.KenoDAO;
import superbetDAO.MisekDAO;
import superbetDAO.PartnerDAO;
import superbetDAO.api.exeception.DAOAPIException;
import superbetDAO.api.implementations.SuperGameDAOAPI;
import superbetDAO.api.interfaces.ISuperGameDAOAPILocal;

public class TurnoverForm {
	
	private static final String FIELD_PERCENTAGE = "percentage";
	private static final String FIELD_CYCLE = "cycle";
	private static final String FIELD_FREQUENCE = "frequence";
	private static final String FIELD_JEU = "turn_jeu";
	private static final String FIELD_CODERACE = "turncoderace";
	private static final String FIELD_ICODERACE = "icoderace";
	private static final String FIELD_CHOICE = "turnchoice";
	private Long idPartner;
	private String coderace;
	private String IN = "('";
	
	private  ISuperGameDAOAPILocal  supergameAPI;
	private  String resultat = null;
	private  Map<String, String> erreurs = new HashMap<String, String>();
	private PartnerDAO partnerDao;
	private GameCycleDAO gmcDao;
	private MisekDAO misekDao;
	private CaissierDAO caissierDao;
	private KenoDAO kenoDao;
	private List<GameCycle> gmc;
	private int taille = 0;
	private String partneraire;
	private double current_percent;
	double summise;
	double sumWin;
	double curr_percent;
	private double jkpt;
	
	
	public TurnoverForm(GameCycleDAO gmcDao, PartnerDAO partnerDao, MisekDAO misekDao, CaissierDAO caissierDao, KenoDAO kenoDao){
		this.gmcDao = gmcDao;
		this.partnerDao = partnerDao;
		this.misekDao = misekDao;
		this.caissierDao = caissierDao;
		this.kenoDao = kenoDao;
		supergameAPI = SuperGameDAOAPI.getInstance();
	}
	
//	public double getReal_percent() {
//		return real_percent;
//	}
//
//	public void setReal_percent(double real_percent) {
//		this.real_percent = real_percent;
//	}
//
//

	public void manage_admin(HttpServletRequest request){
		
		String action = request.getParameter( FIELD_CHOICE );
		Partner partner;
		erreurs.clear();
		
		if("addturnover".equalsIgnoreCase(action)) {
			
			String percentage = request.getParameter( FIELD_PERCENTAGE );
			String cycle = request.getParameter( FIELD_CYCLE );
			String frequence = request.getParameter( FIELD_FREQUENCE );
			String jeu = request.getParameter( FIELD_JEU );
			String codeRacePartner = request.getParameter( FIELD_ICODERACE );
		
			if(StringUtils.isBlank(codeRacePartner)) {
				erreurs.put("FIELD_ICODERACE", "Veuillez choisir un partenaire");
				resultat = "Veuillez selectionner un partenaire";
				return;
			}
			
			long idmisek_max;
//			partner = partnerDao.find(codeRacePartner);
//			
//			if(partner != null) {
//				idPartner = partner.getIdpartner();
//			}
//			else {
//				erreurs.put("FIELD_ICODERACE", "Veuillez choisir un partenaire");
//				resultat = "Veuillez selectionner un partenaire";
//				return;
//			}
			

			try {
				
//				int rang =  UtileKeno._checkExistingSameDisplayCoderace(codeRacePartner);
		//		System.out.println("RANG ADDTURNOVER: "+rang);
				
				
			    List<Integer> roundList = Params.getHitFrequency(Integer.parseInt(frequence.replaceAll("%", "")), Integer.parseInt(cycle.replaceAll("tours", "").trim()));
		
			    String pos = StringUtils.join(roundList, "-");
				
//				for(int nb : roundList) {
//					pos = pos +"-"+ nb;
//				}
//				pos = pos.substring(1);
	
//				gmc  = supergameAPI.getSuperGameDAO().getGameCyle(Params.url, codeRacePartner);
				

//				idmisek_max = supergameAPI.getSuperGameDAO().maxMisek(Params.url, codeRacePartner);
//				Map<String, Double> mapSum = supergameAPI.getSuperGameDAO().getMiseKCycle(Params.url, codeRacePartner,gm.getMise(), 1+idmisek_max);
//				
//				if(mapSum != null) {
//					summise = mapSum.get("sumMise");
//					sumWin = mapSum.get("sumWin");
//					
//					curr_percent = sumWin/summise;
//					curr_percent = (double)((int)(curr_percent*100))/100;
//					
//					sumWin = (double)((int)(sumWin*100))/100;
//					summise = (double)((int)(summise*100))/100;
//					
//					Misek m1 = supergameAPI.getSuperGameDAO().getMiseK(Params.url, gm.getMise());
//	    			Misek m2 = supergameAPI.getSuperGameDAO().getMiseK(Params.url,idmisek_max);
//						
//					if(m1 != null && m2 != null) {
//						int k1 = Integer.parseInt(m1.getIdKeno());
//						int k2 = Integer.parseInt(m2.getIdKeno());
//						jkpt = supergameAPI.getSuperGameDAO().getJackpot(Params.url,k1, k2, codeRacePartner);
//						jkpt = (double)((int)(jkpt*100))/100;
//						
//					}
//					else {
//						jkpt = 0;
//					}
//				
////						GameCycle gmt = gmcDao.findByGame(idPartner, jeu.substring(0, 1));
//					GameCycleDto gmt = new GameCycleDto();
//					gmt.setCurr_percent(curr_percent);
//					gmt.setDate_fin(DateFormatUtils.format(new Date(), "dd-MM-yyyy,HH:mm"));
//					gmt.setArchive(1);
//					gmt.setPartner(idPartner);
//					gmt.setJeu(jeu.substring(0, 1));
//					gmt.setMisef((long)idmisek_max);
//					gmt.setStake(summise);
//					gmt.setPayout(sumWin);
//					gmt.setJkpt(jkpt);
//					
//					supergameAPI.getSuperGameDAO().upArchive(Params.url, gmt, codeRacePartner);
//				}
//				else {
//					summise = 0d;
//					sumWin = 0d;
//				}
				
//				if(rang != -1){
//					UtileKeno._display_draw.get(rang).setPos(0);
//					UtileKeno._display_draw.get(rang).setRtp(0);
//				}
				
				
			  GameCycleDto gamecycle = new GameCycleDto();
			  gamecycle.setRefundp(0);
			  gamecycle.setPosition(0);
			  gamecycle.setPartner(idPartner);
			  gamecycle.setPercent(Double.parseDouble(percentage.replaceAll("%", "")));
			  gamecycle.setTour(Integer.parseInt(cycle.replaceAll("tours", "").trim()));
			  gamecycle.setArrangement(pos);
			  gamecycle.setHitfrequence(Integer.parseInt(frequence.replaceAll("%", "")));
			  gamecycle.setJeu(jeu.substring(0, 1));
			  gamecycle.setArchive(0);
			  gamecycle.setDate_fin(DateFormatUtils.format(new Date(), "dd-MM-yyyy,HH:mm"));

			  gmc = supergameAPI.getSuperGameDAO().setGameCyle(Params.url, gamecycle, codeRacePartner);
			 
				
			  taille = gmc.size();
			  
			  if (!gmc.isEmpty()) {
				  erreurs.clear();
				  resultat = "Mise a jour du cycle avec succes";
			  }
			  else {
				  erreurs.put("ERROR UPDATE CYCLE", "Erreur lors de la mise � jour du cycle.");
				  resultat = "Echec de la mise a jour du cycle.";
			  }
						  
			} catch (IOException | JSONException | URISyntaxException | DAOAPIException e) {
			
				e.printStackTrace();
			}
				 
		}
		else {
			
		  partneraire = request.getParameter( FIELD_CODERACE );
		 
		  if (StringUtils.isNotBlank(partneraire))  {
			  
//				partner = partnerDao.find(partneraire);
//				idPartner = partner.getIdpartner();
				
		    try {
		    	
		    	gmc  = supergameAPI.getSuperGameDAO().getGameCyle(Params.url, partneraire);
		    	
				if (gmc == null || gmc.isEmpty()) return;
				taille = gmc.size();

				//if(count > 0) idmax = gm.getMisef();
				   GameCycle gms = gmc.get(0); 
				  
//				   switch(gms.getJeu()) {
//				   case "Keno":
//
//					   break;
//				   case "D":
//					   gms.setJeu("Dogs race");
//					   break;
//				   case "B":
//					   gms.setJeu("Bingo");
//					   break;
//				   case "S":
//					   gms.setJeu("Spin");
//					   break;
//				   case "L":
//					   gms.setJeu("Lotto rapide");
//					   break;
//				   default:
//					   break;
//				   }

			  
			} catch (IOException | JSONException | URISyntaxException | DAOAPIException e) {
				e.printStackTrace();
			}
			
		  }
		  else {
				erreurs.put("FIELD_CODERACE", "Veuillez choisir un partenaire");
				resultat = "Veuillez choisir un partenaire";
				return;
		  }
		}
		
	}

	public String getResultat() {
		return resultat;
	}

	public void setResultat(String resultat) {
		this.resultat = resultat;
	}

	public Map<String, String> getErreurs() {
		return erreurs;
	}

	public void setErreurs(Map<String, String> erreurs) {
		this.erreurs = erreurs;
	}

	public int getTaille() {
		return taille;
	}

	public void setTaille(int taille) {
		this.taille = taille;
	}

	public void setGmc(List<GameCycle> gmc) {
		this.gmc = gmc;
	}

	public List<GameCycle> getGmc() {
		return gmc;
	}
	
	public String getPartneraire() {
		return partneraire;
	}

	public void setPartneraire(String partneraire) {
		this.partneraire = partneraire;
	}

	public double getCurrent_percent() {
		return current_percent;
	}

	public void setCurrent_percent(double current_percent) {
		this.current_percent = current_percent;
	}

	public double getSummise() {
		return summise;
	}

	public void setSummise(double summise) {
		this.summise = summise;
	}

	public double getSumWin() {
		return sumWin;
	}

	public void setSumWin(double sumWin) {
		this.sumWin = sumWin;
	}

	public double getJkpt() {
		return jkpt;
	}

	public void setJkpt(double jkpt) {
		this.jkpt = jkpt;
	}
	
	
	
}
