package modele;

import java.io.Serializable;

public class Versement implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6909265496400349380L;
	private Long idvers;
	private String idcaissier;
	private double montant;
	private String heureV;
	private String datV;
	private String typeVers;
	private Long mise;
	private Caissier caissier;
	private int archive;
	
	public Versement(){
		super();
	}

	public Long getIdvers() {
		return idvers;
	}

	public void setIdvers(Long idvers) {
		this.idvers = idvers;
	}

	

	public String getIdcaissier() {
		return idcaissier;
	}

	public void setIdcaissier(String idcaissier) {
		this.idcaissier = idcaissier;
	}

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public String getHeureV() {
		return heureV;
	}

	public void setHeureV(String heureV) {
		this.heureV = heureV;
	}

	public String getDatV() {
		return datV;
	}

	public void setDatV(String datV) {
		this.datV = datV;
	}

	public String getTypeVers() {
		return typeVers;
	}

	public void setTypeVers(String typeVers) {
		this.typeVers = typeVers;
	}

	public Long getMise() {
		return mise;
	}

	public void setMise(Long mise) {
		this.mise = mise;
	}

	public Caissier getCaissier() {
		return caissier;
	}

	public void setCaissier(Caissier caissier) {
		this.caissier = caissier;
	}

	public int getArchive() {
		return archive;
	}

	public void setArchive(int archive) {
		this.archive = archive;
	}
	
}
