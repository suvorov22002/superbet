package modele;

import java.io.Serializable;

public class Miset implements Serializable {
	
	private Long idMiset;
	private String typeJeu;
	private String barcode;
	private double summise;
	
	
	public Miset() {
		super();
	}

	public Long getIdMiset() {
		return idMiset;
	}


	public void setIdMiset(Long idMiset) {
		this.idMiset = idMiset;
	}


	public String getTypeJeu() {
		return typeJeu;
	}


	public void setTypeJeu(String typeJeu) {
		this.typeJeu = typeJeu;
	}


	public String getBarcode() {
		return barcode;
	}


	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}


	public double getSummise() {
		return summise;
	}


	public void setSummise(double summise) {
		this.summise = summise;
	}	
}
