package modele;

public class ResPartner {
	
	private Partner partner;
	private String message;
	
	
	public ResPartner() {
		super();
	}

	public ResPartner(Partner partner, String message) {
		super();
		this.partner = partner;
		this.message = message;
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
