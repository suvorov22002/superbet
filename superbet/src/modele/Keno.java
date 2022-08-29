package modele;

import java.io.Serializable;

public class Keno implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6758909885135680640L;
	
	private Long idKeno;
	private String coderace; //code du partenaire
	private String drawnumbK; //arrivee de la course
	private String bonusKcod; // code bonus du tirage
	private String bonusKamount; // Montant du bonus en cours
	private String drawnumK; // numero de la course
	private String heureTirage;
	private String multiplicateur;
	private String started;
	private String sbonusKcod;
	
	public Keno(){
		super();
	}
	
	
	public Long getIdKeno() {
		return idKeno;
	}

	public void setIdKeno(Long idKeno) {
		this.idKeno = idKeno;
	}

	public String getCoderace() {
		return coderace;
	}

	public void setCoderace(String coderace) {
		this.coderace = coderace;
	}

	public String getDrawnumbK() {
		return drawnumbK;
	}

	public void setDrawnumbK(String drawnumbK) {
		this.drawnumbK = drawnumbK;
	}

	public String getBonusKcod() {
		return bonusKcod;
	}

	public void setBonusKcod(String bonusKcod) {
		this.bonusKcod = bonusKcod;
	}

	public String getBonusKamount() {
		return bonusKamount;
	}

	public void setBonusKamount(String bonusKamount) {
		this.bonusKamount = bonusKamount;
	}

	public String getDrawnumK() {
		return drawnumK;
	}

	public void setDrawnumK(String drawnumK) {
		this.drawnumK = drawnumK;
	}

	public String getHeureTirage() {
		return heureTirage;
	}

	public void setHeureTirage(String heureTirage) {
		this.heureTirage = heureTirage;
	}

	public String getMultiplicateur() {
		return multiplicateur;
	}

	public void setMultiplicateur(String multiplicateur) {
		this.multiplicateur = multiplicateur;
	}

	public String getStarted() {
		return started;
	}

	public void setStarted(String started) {
		this.started = started;
	}

	public String getSbonusKcod() {
		return sbonusKcod;
	}

	public void setSbonusKcod(String sbonusKcod) {
		this.sbonusKcod = sbonusKcod;
	}

}
