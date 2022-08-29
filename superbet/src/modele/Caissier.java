package modele;

import java.io.Serializable;

public class Caissier implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idCaissier;
	private Long profil;
	private String nomc;
	private String loginc;
	private String mdpc;
	private Long partner;
	private String statut;
	private int grpe;
	private double airtime;
	
	public Caissier(){
		super();
	}
	
	public Caissier(String nomc, String loginc, String mdpc, int grpe){
		super();
		this.nomc = nomc;
		this.loginc = loginc;
		this.mdpc = mdpc;
		this.grpe = grpe;
	}

	public String getNomc() {
		return nomc;
	}

	public void setNomc(String nomc) {
		this.nomc = nomc;
	}

	public String getLoginc() {
		return loginc;
	}

	public void setLoginc(String loginc) {
		this.loginc = loginc;
	}

	public String getMdpc() {
		return mdpc;
	}

	public void setMdpc(String mdpc) {
		this.mdpc = mdpc;
	}

	public int getGrpe() {
		return grpe;
	}

	public void setGrpe(int grpe) {
		this.grpe = grpe;
	}

	
	public Long getIdCaissier() {
		return idCaissier;
	}

	public void setIdCaissier(Long idCaissier) {
		this.idCaissier = idCaissier;
	}

	public Long getProfil() {
		return profil;
	}

	public void setProfil(Long profil) {
		this.profil = profil;
	}

	public Long getPartner() {
		return partner;
	}

	public void setPartner(Long partner) {
		this.partner = partner;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public double getAirtime() {
		return airtime;
	}

	public void setAirtime(double airtime) {
		this.airtime = airtime;
	}

	@Override
	public String toString() {
		return "Caissier [profil=" + profil + ", loginc=" + loginc + ", partner=" + partner + ", statut=" + statut
				+ ", grpe=" + grpe + "]";
	}
	
	
	
}
