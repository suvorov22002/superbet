package modele;

import java.io.Serializable;

public class Misek_temp implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 382004966136564675L;
	
	private Long id;
	private int multi;
	private double sumMise;
	private int etatMise;
	private int idmisek;
	
	public Misek_temp() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getMulti() {
		return multi;
	}

	public void setMulti(int multi) {
		this.multi = multi;
	}

	public double getSumMise() {
		return sumMise;
	}

	public void setSumMise(double sumMise) {
		this.sumMise = sumMise;
	}

	public int getEtatMise() {
		return etatMise;
	}

	public void setEtatMise(int etatMise) {
		this.etatMise = etatMise;
	}

	public int getIdmisek() {
		return idmisek;
	}

	public void setIdmisek(int idmiset) {
		this.idmisek = idmiset;
	}
	
}
