package modele;

import java.io.Serializable;

public class ReportingDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String user;
	private double summise;
	private double sumwin;
	private double apport;
	private double cash;
	private String date;
	
	public ReportingDto() {
		super();
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public double getSummise() {
		return summise;
	}

	public void setSummise(double summise) {
		this.summise = summise;
	}

	public double getSumwin() {
		return sumwin;
	}

	public void setSumwin(double sumwin) {
		this.sumwin = sumwin;
	}
	
	public double getApport() {
		return apport;
	}

	public void setApport(double apport) {
		this.apport = apport;
	}

	public double getCash() {
		return summise - sumwin;
	}

	public void setCash(double cash) {
		this.cash = cash;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	
}
