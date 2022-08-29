package modele;

import java.io.Serializable;
import java.util.Date;

public class Airtime implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long caissier;
	private Date date;
	private double credit;
	private double debit;
	private double balance;
	private String libelle;
	private String eta;
	
	public Airtime(){
		super();
	}
	
	

	public Airtime(Long caissier, Date date, double credit, double debit, double balance) {
		super();
		this.caissier = caissier;
		this.date = date;
		this.credit = credit;
		this.debit = debit;
		this.balance = balance;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCaissier() {
		return caissier;
	}

	public void setCaissier(Long caissier) {
		this.caissier = caissier;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getCredit() {
		return credit;
	}

	public void setCredit(double credit) {
		this.credit = credit;
	}

	public double getDebit() {
		return debit;
	}

	public void setDebit(double debit) {
		this.debit = debit;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}



	public String getLibelle() {
		return libelle;
	}



	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}



	public String getEta() {
		return eta;
	}



	public void setEta(String eta) {
		this.eta = eta;
	}
	
}
