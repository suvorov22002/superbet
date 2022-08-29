package modele;

import java.io.Serializable;

public class Clients implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7082324364042585119L;
	private String name;
	private String partner;
	
	public Clients(){
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}
	
}
