package modele;

import java.io.Serializable;

public class Misep implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -670878205855528301L;
	
	private Long idmisep;
	private String idCaissier;
	private String heurMise;
	private String sumMise;
	private String datMise;
	private String etatMise;
	private String drawNumP;
	private String bonusCodeP; //code bonus du ticket 
	private String idMiset;
	private String idSpin;
	
	public Misep() {
		super();
	}

	public Long getIdmisep() {
		return idmisep;
	}

	public void setIdmisep(Long idmisep) {
		this.idmisep = idmisep;
	}

	public String getIdCaissier() {
		return idCaissier;
	}

	public void setIdCaissier(String idCaissier) {
		this.idCaissier = idCaissier;
	}

	public String getHeurMise() {
		return heurMise;
	}

	public void setHeurMise(String heurMise) {
		this.heurMise = heurMise;
	}

	public String getSumMise() {
		return sumMise;
	}

	public void setSumMise(String sumMise) {
		this.sumMise = sumMise;
	}

	public String getDatMise() {
		return datMise;
	}

	public void setDatMise(String datMise) {
		this.datMise = datMise;
	}

	public String getEtatMise() {
		return etatMise;
	}

	public void setEtatMise(String etatMise) {
		this.etatMise = etatMise;
	}

	public String getDrawNumP() {
		return drawNumP;
	}

	public void setDrawNumP(String drawNumP) {
		this.drawNumP = drawNumP;
	}

	public String getBonusCodeP() {
		return bonusCodeP;
	}

	public void setBonusCodeP(String bonusCodeP) {
		this.bonusCodeP = bonusCodeP;
	}

	public String getIdMiset() {
		return idMiset;
	}

	public void setIdMiset(String idMiset) {
		this.idMiset = idMiset;
	}

	public String getIdSpin() {
		return idSpin;
	}

	public void setIdSpin(String idSpin) {
		this.idSpin = idSpin;
	}
}
