package superbetDAO.api;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.codehaus.jettison.json.JSONException;

import config.Params;
import modele.AdminTicketDto;
import modele.Airtime;
import modele.BetTicketK;
import modele.BonusSet;
import modele.CagnotteDto;
import modele.Caissier;
import modele.GameCycle;
import modele.GameCycleDto;
import modele.Keno;
import modele.KenoRes;
import modele.Misek;
import modele.Partner;
import modele.PartnerDto;
import modele.ResPartner;
import modele.ShiftDay;
import modele.Versement;
import superbetDAO.api.exeception.DAOAPIException;

public class SuperGameDAO extends AbstractDAOAPI<SuperGameDAO>{

	public SuperGameDAO() {
		super(SuperGameDAO.class);
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public BetTicketK placeSlip(String url, BetTicketK slip, String coderace) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		return this.sendPostSlip(url, slip, coderace);
	}
	
	public Long getBarcodes(String url, int jeu) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		return this.getBarcode(url, jeu);
	}
	
	public Caissier getCaissier(String url, Caissier cais, String partner) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		return this.getUser(cais, url, partner);
	}
	
	public Caissier getCaissierAdmin(String url, Caissier cais) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		return this.getUserAdmin(cais, url);
	}
	
	public Double getSolde(String url, Long ncp) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		return this.getBalance(url, ncp);
	}
	
	public Keno maxDraw(String url, String coderace) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		return this.getMaxDraw(url, coderace);
	}
	
	public BetTicketK checkTicket(String url, String coderace,  String barcode) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		return this.verifyTicket(url, coderace, barcode);
	}
	
	public Versement paidTicket(String url, String barcode,  Long caissier, double montant) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		return this.registerVersement(url, barcode, caissier, montant);
	}
	
	public KenoRes retrieveCombi(String url, String coderace) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		return this.retrieveDrawCombi(url, coderace);
	}
	
	public int getTime(String url, String coderace) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		return this.getTimeKeno(url, coderace) ;
	}
	
	public ShiftDay shift(String url, Long id, String debut, String fin) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		return this.getShift(url, id, debut, fin) ;
	}
	
	public boolean  endDraw(String url, String coderace) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		return this. sendFinishDraw(url, coderace);
	}
	
	public BonusSet  getbonuskeno(String url, String coderace) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		return this.getbonusk(url, coderace);
	}
	
	public int getStates(String url, int state, String coderace) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		return this.getState(url, state, coderace);
	}
	
	public String retrieveCombinaison(String url, int num, String coderace) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		return this.retrievecombi(url, num, coderace);
	}
	
	public Map<String, String> sumKeno(String url, String coderace) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		return this.sumOddKeno(url, coderace);
	}
	 
	public Caissier  saveUser(String url, Caissier cais, String coderace) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		return this.addUser(url, cais, coderace);
	}
	
	public CagnotteDto getCagnot(String url, String coderace) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		return this.getCagnotte(url, coderace);
	}

	public BonusSet  getcagnotte(String url, String coderace) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		return this.getcagnot(url, coderace);
	}
	
	public Double airtime(String url, String coderace, String login, double credit) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		return this.pushAirtime(url, coderace, login, credit);
	}
	
	public List<Misek> statMisek(String url, long t1, long t2, String coderace) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		return this.statsMisek(url, t1, t2, coderace);
	}
	
	public List<Caissier> getTurnover(String url, String coderace) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		return this.turnover(url, coderace);
	}
	
	public List<GameCycle> getGameCyle(String url, String coderace) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		return this.gamecyle(url, coderace);
	}

	public long maxMisek(String url, String coderace) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		return this.getMaxMisek(url, coderace);
	}

	public Map<String, Double> getMiseKCycle(String url, String coderace, long mise, long l) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		return this.miseKCycle(url, coderace, mise, l);
	}
	
	public double getMiseKCycleWin(String url, String coderace, long mise, long l) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		return this.miseKCycleWin(url, coderace, mise, l);
	}

	public Misek getMiseK(String url, long idmax) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		return this.misek(url, idmax);
	}

	public double getJackpot(String url, int k1, int k2, String coderace) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		return this.jackpot(url, k1, k2, coderace);
	}

	public int upArchive(String url, GameCycleDto gmt, String coderace) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		return this.archive(url, gmt, coderace);
	}

	public boolean setGameCyle(String url, GameCycleDto gmt, String coderace) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		return this.gamecycles(url, gmt, coderace);
	}

	public List<AdminTicketDto> allMiset(String url, long t1, long t2, String coderace) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		return this.misets(url, t1, t2, coderace);
	}

	public List<Versement> getVersementk(String url, long t1, long t2, String coderace)  throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		return this.versement(url, t1, t2, coderace);
	}

	public double findCumulCredit(String url, String coderace, String caissier, String dat1, String dat2) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		return this.cumulCredit(url, dat1, dat2, coderace, caissier);
	}

	public double getMiseRK(String url, String coderace, String caissier, Long dat1, Long dat2) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		return this.miseRK(url, dat1, dat2, coderace, caissier);
	}
	
	public double getVersements(String url, String coderace, String caissier, Long dat1, Long dat2) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		return this.versements(url, dat1, dat2, coderace, caissier);
	}

	public List<Airtime> findAllairtime(String url, String coderace, String login, String dat1, String dat2) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		return this.airtimes(url, dat1, dat2, coderace, login);
	}

	public ResPartner submitPartner(String url, PartnerDto part) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		return this.partner(url, part);
	}

	public CagnotteDto saveJackpot(String url, CagnotteDto cagnotte, String coderace) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		return this.creerCagnot(url, cagnotte, coderace);
	}

	public int updateBonusK(String url, double k_rate, double mbonusk0, double mbonusk1, String coderace) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		return this.setbonusk(url, k_rate, mbonusk0, mbonusk1, coderace);
	}

	public List<KenoRes> getbonus(String url, String coderace) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		return this.bonus(url, coderace);
	}

	public List<KenoRes> getLdraw(String url, String coderace) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		return this.lDraw(url, coderace);
	}
	
	public List<KenoRes> getLastMulti(String url, String coderace)throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		return this.lastTwelveDraw(url, coderace);
	}
	
	public List<Caissier> getSuperAdmin(String url)throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		return this.superAdmin(url);
	}
	
	public Boolean checkPartner(String url, String coderace) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		return this.getPartner(url, coderace);
	}
	
	public List<Partner> getAllPartner(String url)throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		return this.retrieveAllPartner(url);
	}

}
