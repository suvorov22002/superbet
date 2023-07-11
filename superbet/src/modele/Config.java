package modele;

import java.io.Serializable;

public class Config implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7000200199851578846L;
	private Long idConfig;
	private double percent;
	private double bonusrate;
	private double bnskmin;
	private double bnskmax;
	private double bnspmin;
	private double bnspmax;
	private double bnsdmin;
	private double bnsdmax;
	private double bnsbmin;
	private double bnsbmax;
	private String coderace;
	private int config;
	

	public Long getIdConfig() {
		return idConfig;
	}

	public void setIdConfig(Long idConfig) {
		this.idConfig = idConfig;
	}
	
	public double getPercent() {
		return percent;
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}

	public double getBonusrate() {
		return bonusrate;
	}

	public void setBonusrate(double bonusrate) {
		this.bonusrate = bonusrate;
	}

	public String getCoderace() {
		return coderace;
	}

	public void setCoderace(String coderace) {
		this.coderace = coderace;
	}

	public int getConfig() {
		return config;
	}

	public void setConfig(int config) {
		this.config = config;
	}

	public double getBnskmin() {
		return bnskmin;
	}

	public void setBnskmin(double bnskmin) {
		this.bnskmin = bnskmin;
	}

	public double getBnskmax() {
		return bnskmax;
	}

	public void setBnskmax(double bnskmax) {
		this.bnskmax = bnskmax;
	}

	public double getBnspmin() {
		return bnspmin;
	}

	public void setBnspmin(double bnspmin) {
		this.bnspmin = bnspmin;
	}

	public double getBnspmax() {
		return bnspmax;
	}

	public void setBnspmax(double bnspmax) {
		this.bnspmax = bnspmax;
	}

	public double getBnsdmin() {
		return bnsdmin;
	}

	public void setBnsdmin(double bnsdmin) {
		this.bnsdmin = bnsdmin;
	}

	public double getBnsdmax() {
		return bnsdmax;
	}

	public void setBnsdmax(double bnsdmax) {
		this.bnsdmax = bnsdmax;
	}

	public double getBnsbmin() {
		return bnsbmin;
	}

	public void setBnsbmin(double bnsbmin) {
		this.bnsbmin = bnsbmin;
	}

	public double getBnsbmax() {
		return bnsbmax;
	}

	public void setBnsbmax(double bnsbmax) {
		this.bnsbmax = bnsbmax;
	}
	
	
}
