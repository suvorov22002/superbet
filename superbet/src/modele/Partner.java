package modele;

import java.io.Serializable;

public class Partner implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3980895516369366739L;
	
	private Long   idpartner;
	private String coderace;
	private String zone;
	private double bonuskamount;
	private int bonuskcode;
	private int bonuspcode;
	private int bonusrcode;
	private int bonusbcode;
	private int bonusdcode;
	private double bonusBamount;
	private double bonusRamount;
	private double bonusDamount;
	private double bonusPamount;
	private String cob;
	private int groupe;
	private int actif;
	
	
	public Long getIdpartner() {
		return idpartner;
	}
	public void setIdpartner(Long idpartner) {
		this.idpartner = idpartner;
	}
	public String getCoderace() {
		return coderace;
	}
	public void setCoderace(String coderace) {
		this.coderace = coderace;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public double getBonuskamount() {
		return bonuskamount;
	}
	public void setBonuskamount(double bonuskamount) {
		this.bonuskamount = bonuskamount;
	}
	public double getBonusBamount() {
		return bonusBamount;
	}
	public void setBonusBamount(double bonusBamount) {
		this.bonusBamount = bonusBamount;
	}
	public double getBonusRamount() {
		return bonusRamount;
	}
	public void setBonusRamount(double bonusRamount) {
		this.bonusRamount = bonusRamount;
	}
	public double getBonusDamount() {
		return bonusDamount;
	}
	public void setBonusDamount(double bonusDamount) {
		this.bonusDamount = bonusDamount;
	}
	public int getBonuskcode() {
		return bonuskcode;
	}
	public void setBonuskcode(int bonuskcode) {
		this.bonuskcode = bonuskcode;
	}
	public int getBonuspcode() {
		return bonuspcode;
	}
	public void setBonuspcode(int bonuspcode) {
		this.bonuspcode = bonuspcode;
	}
	public double getBonusPamount() {
		return bonusPamount;
	}
	public void setBonusPamount(double bonusPamount) {
		this.bonusPamount = bonusPamount;
	}
	public int getBonusrcode() {
		return bonusrcode;
	}
	public void setBonusrcode(int bonusrcode) {
		this.bonusrcode = bonusrcode;
	}
	public int getBonusbcode() {
		return bonusbcode;
	}
	public void setBonusbcode(int bonusbcode) {
		this.bonusbcode = bonusbcode;
	}
	public int getBonusdcode() {
		return bonusdcode;
	}
	public void setBonusdcode(int bonusdcode) {
		this.bonusdcode = bonusdcode;
	}
	public String getCob() {
		return cob;
	}
	public void setCob(String cob) {
		this.cob = cob;
	}
	public int getGroupe() {
		return groupe;
	}
	public void setGroupe(int groupe) {
		this.groupe = groupe;
	}
	public int getActif() {
		return actif;
	}
	public void setActif(int actif) {
		this.actif = actif;
	}
	
	
	
}
