package modele;

import java.io.Serializable;

public class Coupon implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1088450187660008311L;
	
	private String room; 
	private String horaYfecha;
	private String cashier;
	private String idTicket;
	private int nbEvents; //numero du tirage
	private String events; //liste des numeros
	private String eventscote; // cote de l'evenements
	private String mtMise;
	private double gainMax;
	private double gainMin;
	private int nbreCombi; //code bonus
	private String barcode;
	private String codepari; // code pari et cote
	private String multiplicateur;
	
	
	
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public String getHoraYfecha() {
		return horaYfecha;
	}
	public void setHoraYfecha(String horaYfecha) {
		this.horaYfecha = horaYfecha;
	}
	public String getCashier() {
		return cashier;
	}
	public void setCashier(String cashier) {
		this.cashier = cashier;
	}
	public String getIdTicket() {
		return idTicket;
	}
	public void setIdTicket(String idTicket) {
		this.idTicket = idTicket;
	}
	public int getNbEvents() {
		return nbEvents;
	}
	public void setNbEvents(int nbEvents) {
		this.nbEvents = nbEvents;
	}
	public String getEvents() {
		return events;
	}
	public void setEvents(String events) {
		this.events = events;
	}
	public String getEventscote() {
		return eventscote;
	}
	public void setEventscote(String eventscote) {
		this.eventscote = eventscote;
	}
	public String getMtMise() {
		return mtMise;
	}
	public void setMtMise(String mtMise) {
		this.mtMise = mtMise;
	}
	public double getGainMax() {
		return gainMax;
	}
	public void setGainMax(double gainMax) {
		this.gainMax = gainMax;
	}
	public double getGainMin() {
		return gainMin;
	}
	public void setGainMin(double gainMin) {
		this.gainMin = gainMin;
	}
	public int getNbreCombi() {
		return nbreCombi;
	}
	public void setNbreCombi(int nbreCombi) {
		this.nbreCombi = nbreCombi;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getCodepari() {
		return codepari;
	}
	public void setCodepari(String codepari) {
		this.codepari = codepari;
	}
	public String getMultiplicateur() {
		return multiplicateur;
	}
	public void setMultiplicateur(String multiplicateur) {
		this.multiplicateur = multiplicateur;
	}
	
}

