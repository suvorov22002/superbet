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
		supergameAPI = new SuperGameDAOAPI();
	}
	
	public void manage_admin(HttpServletRequest request){
		
		String action = request.getParameter( FIELD_CHOICE );
		Partner partner;
		erreurs.clear();
		
		if("addturnover".equalsIgnoreCase(action)) {
			String percentage = request.getParameter( FIELD_PERCENTAGE );
			String cycle = request.getParameter( FIELD_CYCLE );
			String frequence = request.getParameter( FIELD_FREQUENCE );
			String jeu = request.getParameter( FIELD_JEU );
			String ipartner = request.getParameter( FIELD_ICODERACE );
		//	System.out.println("frequence: "+frequence+" cycle: "+cycle);
			if(ipartner == null || ipartner == "" ) {
				erreurs.put("FIELD_ICODERACE", "Veuillez choisir un partenaire");
				resultat = "Veuillez selectionner un partenaire";
				return;
			}
			
			long idmisek_max;
			partner = partnerDao.find(ipartner);
			if(partner != null) {
				idPartner = partner.getIdpartner();
			}
			else {
				erreurs.put("FIELD_ICODERACE", "Veuillez choisir un partenaire");
				resultat = "Veuillez selectionner un partenaire";
				return;
			}
			

			try {
				int rang =  UtileKeno._checkExistingSameDisplayCoderace(ipartner);
		//		System.out.println("RANG ADDTURNOVER: "+rang);
				
			    List<Caissier> list_cais;
				list_cais = supergameAPI.getSuperGameDAO().getTurnover(Params.url, idPartner);
			    String pos = "";
				List<Integer> roundList = Params.getHitFrequency(Integer.parseInt(frequence.replaceAll("%", "")), Integer.parseInt(cycle.replaceAll("tours", "").trim()));
				for(int nb : roundList) {
					pos = pos +"-"+ nb;
				}
				pos = pos.substring(1);
	//			System.out.print("\n"+pos);
	//			System.out.print("\n"+IN);
				
				gmc  = supergameAPI.getSuperGameDAO().getGameCyle(Params.url, idPartner);
				if (gmc == null) return;
				
				taille = gmc.size();
				if(taille < 1) {
					return;
				}
				GameCycle gm = gmc.get(0); 
				
				//idmisek_max = misekDao.ifindId(IN);
				idmisek_max = supergameAPI.getSuperGameDAO().maxMisek(Params.url, idPartner);
				
				long misef = (long)idmisek_max;
				
				//summise = misekDao.getMiseKCycle(gm.getMise(), idmisek_max+1, IN);
				summise = supergameAPI.getSuperGameDAO().getMiseKCycle(Params.url, idPartner,gm.getMise(), 1+idmisek_max);
				
				//sumWin = UtileKeno.bonusrate*summise + misekDao.getMiseKCycleWin(gm.getMise(),idmisek_max+1, IN);
				sumWin = supergameAPI.getSuperGameDAO().getMiseKCycleWin(Params.url, idPartner,gm.getMise(), 1+idmisek_max);
				curr_percent = sumWin/summise;
				curr_percent = (double)((int)(curr_percent*100))/100;
				//jkpt = UtileKeno.bonusrate*summise;
				
				sumWin = (double)((int)(sumWin*100))/100;
				summise = (double)((int)(summise*100))/100;
			//	System.out.println("summise "+summise+" SumWin "+sumWin);
				//recherche du jackpot
				Misek m1 = supergameAPI.getSuperGameDAO().getMiseK(Params.url, gm.getMise());
    			Misek m2 = supergameAPI.getSuperGameDAO().getMiseK(Params.url,idmisek_max);
					
					if(m1 != null && m2 != null) {
						int k1 = Integer.parseInt(m1.getIdKeno());
						int k2 = Integer.parseInt(m2.getIdKeno());
						jkpt = supergameAPI.getSuperGameDAO().getJackpot(Params.url,k1, k2, partner.getIdpartner());
						jkpt = (double)((int)(jkpt*100))/100;
						
					}
					else {
						jkpt = 0;
					}
				
//					GameCycle gmt = gmcDao.findByGame(idPartner, jeu.substring(0, 1));
					GameCycleDto gmt = new GameCycleDto();
					gmt.setCurr_percent(curr_percent);
					gmt.setDate_fin(DateFormatUtils.format(new Date(), "dd-MM-yyyy,HH:mm"));
					gmt.setArchive(1);
					gmt.setPartner(idPartner);
					gmt.setJeu(jeu.substring(0, 1));
					gmt.setMisef(misef);
					gmt.setStake(summise);
					gmt.setPayout(sumWin);
					gmt.setJkpt(jkpt);
					
					int nbre = supergameAPI.getSuperGameDAO().upArchive(Params.url, gmt);
					//gmcDao.updateArchive(curr_percent,DateFormatUtils.format(new Date(), "dd-MM-yyyy,HH:mm"), 1, idPartner, jeu.substring(0, 1), misef, summise, sumWin, jkpt);

					//					summise = misekDao.getMiseKCycle(gmt.getMise(), IN);
//					sumWin = misekDao.getMiseKCycleWin(gmt.getMise(), IN);
//					curr_percent = sumWin/summise;
//					curr_percent = (double)((int)(curr_percent*100))/100;
					if(rang != -1){
						UtileKeno._display_draw.get(rang).setPos(0);
						UtileKeno._display_draw.get(rang).setRtp(0);
					}
					
					
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
				  gamecycle.setMise(idmisek_max);
				  gamecycle.setMisef(misef);
				  gamecycle.setDate_fin(DateFormatUtils.format(new Date(), "dd-MM-yyyy,HH:mm"));

				  supergameAPI.getSuperGameDAO().setGameCyle(Params.url, gamecycle);
				  //gmcDao.create(gamecycle);
						  
			} catch (IOException | JSONException | URISyntaxException | DAOAPIException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
				  
				 
		}
		else {
		  partneraire = request.getParameter( FIELD_CODERACE );
		  
		  if (StringUtils.isNotBlank(partneraire))  {
				partner = partnerDao.find(partneraire);
				idPartner = partner.getIdpartner();
				
		    try {
		    	gmc  = supergameAPI.getSuperGameDAO().getGameCyle(Params.url, idPartner);
				//gmc = gmcDao.find(idPartner);
				if (gmc == null) return;
				taille = gmc.size();
				System.out.println("[TURNOVER - GAMECYCLE SIZE]: "+taille);
				if(taille < 1) {
					return;
				}
				
			   int count = 0;
			   long idmax = 0;
			   idmax = supergameAPI.getSuperGameDAO().maxMisek(Params.url, idPartner);
			  // System.out.println("idmax: "+idmax);
			   //idmax = misekDao.ifindId(IN);
			   //long misek_max = gmc.get(0).getMise();
			   for(GameCycle gm : gmc) {
//				if(count > 2) break;
				  // System.out.println("gm.getJeu(): "+gm.getJeu());
				if(count > 0) idmax = gm.getMisef();
				//GameCycle gm = gmc.get(0); 
			    	
			    	switch(gm.getJeu()) {
			    		case "Keno":
			    			//gm.setJeu("Keno");
			    			
			    			summise = supergameAPI.getSuperGameDAO().getMiseKCycle(Params.url, idPartner,gm.getMise(), 1+idmax);
			    			//summise = misekDao.getMiseKCycle(gm.getMise(),1+idmax, IN);
			    	//		System.out.println("[TURNOVER - MISE TOTALE]: "+summise);
			    			
			    			sumWin = supergameAPI.getSuperGameDAO().getMiseKCycleWin(Params.url, idPartner,gm.getMise(), 1+idmax);
			    	//		System.out.println("[TURNOVER - VERSEMENT TOTAL]: "+sumWin);
			    			//sumWin = misekDao.getMiseKCycleWin(gm.getMise(), 1+idmax, IN);
			    			//System.out.println("summise "+summise+" SumWin "+sumWin);
			    		    //System.out.println("UtileKeno.bonusrate*summise "+UtileKeno.bonusrate*summise);
			    			curr_percent = sumWin/summise;
			    			curr_percent = (double)((int)(curr_percent*100))/100;
			    			//System.out.println("Percent: "+curr_percent+" WIn: "+sumWin+" Mise: "+summise+" Misemin: "+gm.getMise()+" Misemax: "+idmax);
			    			//jkpt = UtileKeno.bonusrate*summise;
			    			
			    			sumWin = (double)((int)(sumWin*100))/100;
			    			summise = (double)((int)(summise*100))/100;
			    			
			    			//recherche du jackpot
			    			Misek m1 = supergameAPI.getSuperGameDAO().getMiseK(Params.url, gm.getMise());
			    			Misek m2 = supergameAPI.getSuperGameDAO().getMiseK(Params.url,idmax);
			    			//m1 = misekDao.searchMiseK(""+gm.getMise());
			    			//m2 = misekDao.searchMiseK(""+(idmax));
			    	//System.out.println(gm.getMise()+" ___ "+m1.getIdKeno());
			    			
			    			if(m1 != null && m2 != null) {
			    				int k1 = Integer.parseInt(m1.getIdKeno());
			    				int k2 = Integer.parseInt(m2.getIdKeno());
			    				
			    				jkpt = supergameAPI.getSuperGameDAO().getJackpot(Params.url,k1, k2, idPartner);
			    				//jkpt  = kenoDao.findTotalBonusAmount(k1, k2, partneraire);
			    				jkpt = (double)((int)(jkpt*100))/100;
			    				//System.out.println(" jkpt___ "+jkpt);
			    			}
			    			else {
			    				jkpt = 0;
			    			}	
			    			gm.setCurr_percent(curr_percent);
			    			gm.setStake(summise);
			    			gm.setPayout(sumWin);
			    			gm.setJkpt(jkpt);
			    			break;
			    		case "D":
			    			gm.setJeu("Dogs race");
			    			break;
			    		case "B":
			    			gm.setJeu("Bingo");
			    			break;
			    		case "S":
			    			gm.setJeu("Spin");
			    			break;
			    		case "L":
			    			gm.setJeu("Lotto rapide");
			    			break;
			    		default:
			    			break;
			    	}
			    	
				//System.out.println("Percent: "+curr_percent+" WIn: "+sumWin+" Mise: "+summise+" Misemin: "+gm.getMise()+" Misemax: "+idmax);
	     	    	count++;
//			    	misek_max = gm.getMise();
			    }
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
