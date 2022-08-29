package modele;

import java.io.Serializable;
import java.util.Date;

public class GameCycle implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private double percent;
	private int tour;
	private int hitfrequence;
	private double refundp;
	private int position;
	private String arrangement;
	private Long partner;
	private String jeu;
	private long mise;
	private long misef;
	private int archive;
	private String date_fin;
	private double curr_percent;
	private double stake;
	private double payout;
	private double jkpt;
	
	public GameCycle() {
		super();
		// TODO Auto-generated constructor stub
	}

	public double getPercent() {
		return percent;
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}

	public int getTour() {
		return tour;
	}

	public void setTour(int tour) {
		this.tour = tour;
	}

	public int getHitfrequence() {
		return hitfrequence;
	}

	public void setHitfrequence(int hitfrequence) {
		this.hitfrequence = hitfrequence;
	}

	public double getRefundp() {
		return refundp;
	}

	public void setRefundp(double refundp) {
		this.refundp = refundp;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getArrangement() {
		return arrangement;
	}

	public void setArrangement(String arrangement) {
		this.arrangement = arrangement;
	}

	public Long getPartner() {
		return partner;
	}

	public void setPartner(Long partner) {
		this.partner = partner;
	}

	public String getJeu() {
		return jeu;
	}

	public void setJeu(String jeu) {
		this.jeu = jeu;
	}

	public long getMise() {
		return mise;
	}

	public void setMise(long mise) {
		this.mise = mise;
	}
	
	public long getMisef() {
		return misef;
	}

	public void setMisef(long misef) {
		this.misef = misef;
	}

	public int getArchive() {
		return archive;
	}

	public void setArchive(int archive) {
		this.archive = archive;
	}

	public double getCurr_percent() {
		return curr_percent;
	}

	public void setCurr_percent(double curr_percent) {
		this.curr_percent = curr_percent;
	}

	public String getDate_fin() {
		return date_fin;
	}

	public void setDate_fin(String date_fin) {
		this.date_fin = date_fin;
	}

	public double getStake() {
		return stake;
	}

	public void setStake(double stake) {
		this.stake = stake;
	}

	public double getPayout() {
		return payout;
	}

	public void setPayout(double payout) {
		this.payout = payout;
	}

	public double getJkpt() {
		return jkpt;
	}

	public void setJkpt(double jkpt) {
		this.jkpt = jkpt;
	}
	
	
	
}
