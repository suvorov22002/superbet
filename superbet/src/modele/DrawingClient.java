package modele;

import java.io.Serializable;

public class DrawingClient implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1791363343824047084L;
	private String name;
	private String partner;
	
	public DrawingClient(){
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
