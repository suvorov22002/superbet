package modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import config.EtatMise;
import config.Jeu;

public class BetTicketK implements Serializable {

	/**
	 * 
	 */
	@JsonIgnore
	private static final long serialVersionUID = 1L;
	
	private Jeu typeJeu;
	private String barcode;
	private double summise;
	private String coderace;  
	private String heureMise;
	private String dateMise;
	private int drawnumk;
	private EtatMise etatMise = EtatMise.ATTENTE;
	private int bonusCod;
	private double sumWin = 0d;
	private int archive;
	private String xmulti; //multiplicateur de gain
	private String caissier;
	private Long keno;
	private Long idMiseT;
	private double mvt;  // solde caissier
	private int multiplicite; //tour multiple
	private double cotejeu;
	private int event; //nombre d'evenemnts pour le ticket
	private String paril;
	private String kchoice;
	private boolean bonus = false;
	private String message;
	private Versement vers;
	private List<EffChoicek> listEfchk;
	private boolean cagnotte = false;
	private long bonusAmount;
	private String drawResult;
	
	
	public BetTicketK() {
		super();
	}
	
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public double getSummise() {
		return summise;
	}
	public void setSummise(double summise) {
		this.summise = summise;
	}
	public String getHeureMise() {
		return heureMise;
	}
	public void setHeureMise(String heureMise) {
		this.heureMise = heureMise;
	}
	public String getDateMise() {
		return dateMise;
	}
	public void setDateMise(String dateMise) {
		this.dateMise = dateMise;
	}
	public int getDrawnumk() {
		return drawnumk;
	}
	public void setDrawnumk(int drawnumk) {
		this.drawnumk = drawnumk;
	}
	public int getBonusCod() {
		return bonusCod;
	}
	public void setBonusCod(int bonusCod) {
		this.bonusCod = bonusCod;
	}
	public double getSumWin() {
		return sumWin;
	}
	public void setSumWin(double sumWin) {
		this.sumWin = sumWin;
	}
	public int getArchive() {
		return archive;
	}
	public void setArchive(int archive) {
		this.archive = archive;
	}
	public String getXmulti() {
		return xmulti;
	}
	public void setXmulti(String xmulti) {
		this.xmulti = xmulti;
	}
	public String getCaissier() {
		return caissier;
	}
	public void setCaissier(String caissier) {
		this.caissier = caissier;
	}
	public Long getKeno() {
		return keno;
	}
	public void setKeno(Long keno) {
		this.keno = keno;
	}
	public Long getIdMiseT() {
		return idMiseT;
	}
	public void setIdMiseT(Long idMiseT) {
		this.idMiseT = idMiseT;
	}
	public double getMvt() {
		return mvt;
	}
	public void setMvt(double mvt) {
		this.mvt = mvt;
	}
	public int getMultiplicite() {
		return multiplicite;
	}
	public void setMultiplicite(int multiplicite) {
		this.multiplicite = multiplicite;
	}
	public double getCotejeu() {
		return cotejeu;
	}
	public void setCotejeu(double cotejeu) {
		this.cotejeu = cotejeu;
	}
	public int getEvent() {
		return event;
	}
	public void setEvent(int event) {
		this.event = event;
	}
	
	
	public Jeu getTypeJeu() {
		return typeJeu;
	}
	public void setTypeJeu(Jeu typeJeu) {
		this.typeJeu = typeJeu;
	}
	public EtatMise getEtatMise() {
		return etatMise;
	}
	public void setEtatMise(EtatMise etatMise) {
		this.etatMise = etatMise;
	}
	
	public String getParil() {
		return paril;
	}
	public void setParil(String paril) {
		this.paril = paril;
	}
	public String getKchoice() {
		return kchoice;
	}
	public void setKchoice(String kchoice) {
		this.kchoice = kchoice;
	}

	public List<EffChoicek> getListEfchk() {
		return listEfchk;
	}

	public void setListEfchk(List<EffChoicek> listEfchk) {
		this.listEfchk = listEfchk;
	}

	public boolean isBonus() {
		return bonus;
	}

	public void setBonus(boolean bonus) {
		this.bonus = bonus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public Versement getVers() {
		return vers;
	}

	public void setVers(Versement vers) {
		this.vers = vers;
	}
	
	public boolean isCagnotte() {
		return cagnotte;
	}

	public void setCagnotte(boolean cagnotte) {
		this.cagnotte = cagnotte;
	}
	
	public String getCoderace() {
		return coderace;
	}

	public void setCoderace(String coderace) {
		this.coderace = coderace;
	}

	public long getBonusAmount() {
		return bonusAmount;
	}

	public void setBonusAmount(long bonusAmount) {
		this.bonusAmount = bonusAmount;
	}
	
	public String getDrawResult() {
		return drawResult;
	}

	public void setDrawResult(String drawResult) {
		this.drawResult = drawResult;
	}

	@Override
	public String toString() {
		return "BETKENOK [Barcode= "+barcode+" SumMise= "+summise+" Cote= "+cotejeu+" Partner= "
				+coderace+" Caissier= "+caissier+" Keno= "+keno+ ", Versement= " + vers+ "]";
	}
	
}
