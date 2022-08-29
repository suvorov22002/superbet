package modele;

import java.io.Serializable;

public class Spin implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9199848987110908793L;
	
	private Long idspin;
	private String coderace; //code du partenaire
	private String drawnumbP; //arrivee de la course
	private String bonusPcod; // code bonus du tirage
	private String bonusPAmount; // Montant du bonus en cours
	private String drawNumP; // numero de la course
	private String heureTirage;
	private String started;
	private String sbonusPcod;
	
	public Spin(){
		super();
	}

	public Long getIdspin() {
		return idspin;
	}

	public void setIdspin(Long idspin) {
		this.idspin = idspin;
	}

	public String getCoderace() {
		return coderace;
	}

	public void setCoderace(String coderace) {
		this.coderace = coderace;
	}

	public String getDrawnumbP() {
		return drawnumbP;
	}

	public void setDrawnumbP(String drawnumbP) {
		this.drawnumbP = drawnumbP;
	}

	public String getBonusPcod() {
		return bonusPcod;
	}

	public void setBonusPcod(String bonusPcod) {
		this.bonusPcod = bonusPcod;
	}

	public String getBonusPAmount() {
		return bonusPAmount;
	}

	public void setBonusPAmount(String bonusPAmount) {
		this.bonusPAmount = bonusPAmount;
	}

	public String getDrawNumP() {
		return drawNumP;
	}

	public void setDrawNumP(String drawNumP) {
		this.drawNumP = drawNumP;
	}

	public String getHeureTirage() {
		return heureTirage;
	}

	public void setHeureTirage(String heureTirage) {
		this.heureTirage = heureTirage;
	}

	public String getStarted() {
		return started;
	}

	public void setStarted(String started) {
		this.started = started;
	}

	public String getSbonusPcod() {
		return sbonusPcod;
	}

	public void setSbonusPcod(String sbonusPcod) {
		this.sbonusPcod = sbonusPcod;
	}

}
