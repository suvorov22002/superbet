package modele;

import java.io.Serializable;

public class EffChoicek implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long ideffchoicek;
	private String idparil;
	private String imisek;
	private String kchoice;
	private String cote;
	private Long idkeno;
	private String mtchoix;
	private double mtwin = 0;
	private int drawnum;
	private String drawresult;
	private boolean state;
	
	public EffChoicek(){
		
	}

	public Long getIdeffchoicek() {
		return ideffchoicek;
	}

	public void setIdeffchoicek(Long ideffchoicek) {
		this.ideffchoicek = ideffchoicek;
	}

	public String getIdparil() {
		return idparil;
	}

	public void setIdparil(String idparil) {
		this.idparil = idparil;
	}

	public String getImisek() {
		return imisek;
	}

	public void setImisek(String idmisek) {
		this.imisek = idmisek;
	}

	public String getKchoice() {
		return kchoice;
	}

	public void setKchoice(String kchoice) {
		this.kchoice = kchoice;
	}

	public String getCote() {
		return cote;
	}

	public void setCote(String cote) {
		this.cote = cote;
	}

	public Long getIdkeno() {
		return idkeno;
	}

	public void setIdkeno(Long idkeno) {
		this.idkeno = idkeno;
	}

	public String getMtchoix() {
		return mtchoix;
	}

	public void setMtchoix(String mtchoix) {
		this.mtchoix = mtchoix;
	}

	public double getMtwin() {
		return mtwin;
	}

	public void setMtwin(double mtwin) {
		this.mtwin = mtwin;
	}

	public int getDrawnum() {
		return drawnum;
	}

	public void setDrawnum(int drawnum) {
		this.drawnum = drawnum;
	}

	public String getDrawresult() {
		return drawresult;
	}

	public void setDrawresult(String drawresult) {
		this.drawresult = drawresult;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}
	
}
