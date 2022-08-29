package modele;

import java.io.Serializable;

public class Misek implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1513834781350102614L;
	
	private Long idmisek;
	private String idCaissier;
	private String heurMise;
	private String sumMise;
	private String datMise;
	private String etatMise;
	private String drawNumK;
	private String bonusCodeK; //code bonus du ticket 
	private String idMiset;
	private String idKeno;
	private double sumWin;
	private int xmulti;
	
	public Misek() {
		super();
	}
	
	
	public Long getIdmisek() {
		return idmisek;
	}

	public void setIdmisek(Long idmisek) {
		this.idmisek = idmisek;
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

	public String getDrawNumK() {
		return drawNumK;
	}

	public void setDrawNumK(String drawNumK) {
		this.drawNumK = drawNumK;
	}

	public String getBonusCodeK() {
		return bonusCodeK;
	}

	public void setBonusCodeK(String bonusCodeK) {
		this.bonusCodeK = bonusCodeK;
	}

	public String getIdMiset() {
		return idMiset;
	}

	public void setIdMiset(String idMiset) {
		this.idMiset = idMiset;
	}

	public String getIdKeno() {
		return idKeno;
	}

	public void setIdKeno(String idKeno) {
		this.idKeno = idKeno;
	}


	public double getSumWin() {
		return sumWin;
	}

	public void setSumWin(double sumWin) {
		this.sumWin = sumWin;
	}


	public int getXmulti() {
		return xmulti;
	}


	public void setXmulti(int xmulti) {
		this.xmulti = xmulti;
	}
	
	
}
