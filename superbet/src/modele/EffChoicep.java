package modele;

import java.io.Serializable;

public class EffChoicep implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -763151052211763632L;
	
	private Long ideffchoicep;
	private String idparis;
	private String idmisep;
	private String pchoice;
	private String cote;
	private String idspin;
	private String mtchoix;
	
	public EffChoicep(){
		super();
	}

	public Long getIdeffchoicep() {
		return ideffchoicep;
	}

	public void setIdeffchoicep(Long ideffchoicep) {
		this.ideffchoicep = ideffchoicep;
	}

	public String getIdparis() {
		return idparis;
	}

	public void setIdparis(String idparis) {
		this.idparis = idparis;
	}

	public String getIdmisep() {
		return idmisep;
	}

	public void setIdmisep(String idmisep) {
		this.idmisep = idmisep;
	}

	public String getPchoice() {
		return pchoice;
	}

	public void setPchoice(String pchoice) {
		this.pchoice = pchoice;
	}

	public String getCote() {
		return cote;
	}

	public void setCote(String cote) {
		this.cote = cote;
	}

	public String getIdspin() {
		return idspin;
	}

	public void setIdspin(String idspin) {
		this.idspin = idspin;
	}

	public String getMtchoix() {
		return mtchoix;
	}

	public void setMtchoix(String mtchoix) {
		this.mtchoix = mtchoix;
	}
}
