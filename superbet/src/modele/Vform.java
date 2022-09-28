package modele;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Vform implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Map<String, String>> evenements;
	private Map<String, String> drawData = new HashMap<String, String>();
	private int multiplicite;
	private String resultat;
	
	
	public List<Map<String, String>> getEvenements() {
		return evenements;
	}
	public void setEvenements(List<Map<String, String>> evenements) {
		this.evenements = evenements;
	}
	public Map<String, String> getDrawData() {
		return drawData;
	}
	public void setDrawData(Map<String, String> drawData) {
		this.drawData = drawData;
	}
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
	
}
