package modele;

import java.io.Serializable;

public class SlipForm implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int multiplicite;
	private String resultat;
	private Coupon coupon;
	private String[] _fecha;
	private String path; 
	private double balance;
	
	public int getMultiplicite() {
		return multiplicite;
	}
	public void setMultiplicite(int multiplicite) {
		this.multiplicite = multiplicite;
	}
	public String getResultat() {
		return resultat;
	}
	public void setResultat(String resultat) {
		this.resultat = resultat;
	}
	public Coupon getCoupon() {
		return coupon;
	}
	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}
	public String[] get_fecha() {
		return _fecha;
	}
	public void set_fecha(String[] _fecha) {
		this._fecha = _fecha;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	
}
