package business;

import java.util.ArrayList;

import modele.Caissier;
import superbetDAO.SpinDAO;

public class Refreshb implements Runnable{
	
	private static Thread thread;
	private ArrayList<Caissier> customers = new ArrayList<Caissier>();
	private ArrayList<String> display_draw = new ArrayList<String>();
	
	public static final String CONF_DAO_FACTORY = "daofactory";
	private static SpinDAO spinDao;
	private String coderace;
	
	private static boolean isDraw = false; //controle si le tirage est en cours
	private static boolean countDown = true; //controle le compteur de temps avant tirage
	private static String drawCombip = null;
	private static int drawNump = 0;
	private static String multiplix;
	private int drawCount = 135;
	
	
	public Refreshb(ArrayList<Caissier> customers, ArrayList<String> display_draw){
		this.customers = customers;
		this.display_draw = display_draw;
		//this.kenoDao = ( (DAOFactory)getServletContext().getAttribute( CONF_DAO_FACTORY )).getKenoDao();
	}
	
	public static Refreshb  getInstance(){
		ArrayList<Caissier> customers = new ArrayList<Caissier>();
		ArrayList<String> display_draw = new ArrayList<String>();
		
		//spinDao = DAOFactory.getInstance().getSpinDao();
		
		Refreshb instance = new Refreshb( customers, display_draw );
		return instance;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
