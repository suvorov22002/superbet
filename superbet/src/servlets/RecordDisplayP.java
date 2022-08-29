package servlets;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import business.Refreshp;
import config.UtileSpin;
import modele.ControlDisplaySpin;
import superbetDAO.CaissierDAO;
import superbetDAO.DAOFactory;
import superbetDAO.SpinDAO;

public class RecordDisplayP extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8228838731068858336L;
	public static final String ATT_DATA_REFRESHP = "refreshp";
	public static final String CONF_DAO_FACTORY = "daofactory";
	
	private SpinDAO spinDao;
	private Refreshp refreshp;
	private CaissierDAO caissierDao;
	/**
     * @see HttpServlet#HttpServlet()
     */
	
	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO caissier */
			//this.refreshp = ( (Refreshp)getServletContext().getAttribute( ATT_DATA_REFRESHP ));
		    
			this.spinDao = ( (DAOFactory)getServletContext().getAttribute( CONF_DAO_FACTORY )).getSpinDao();
	}
	
	public RecordDisplayP(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String login = request.getParameter("coderace");
		String temps = request.getParameter("tiempo");
		ControlDisplaySpin cds = new ControlDisplaySpin(login);
		int rang = UtileSpin._checkExistingSameDisplayCoderace(login);
		
		if(rang == -1){
			System.out.println("RECORD_DISPLAY RESTART: "+UtileSpin._display_draw.size());
			rang = UtileSpin._display_draw.size();
			cds.setRang(rang);
			this.refreshp = new Refreshp(cds, login);
			UtileSpin._display_draw.add(cds);
			
			refreshp.start();
		}
		else{
			cds.setRang(rang);
		}
		
	
		
		
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
	
     	JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
/*
     		if(!UtileSpin.checkExistingSameDisplayCoderace(login)){
     			UtileSpin.addDisplayDraw(login);
     			
     			System.out.println("RECORDDISPLAY RESTART: "+UtileSpin.getDisplay_draw().size());
     			refreshp.start();
     		}*/
		//	System.out.println("RECORD SIZE DISPLAY: "+UtileSpin.getDisplay_draw().size());
		/*	if(refreshp.getDisplay_draw().size() < 1){
				System.out.println("RECORDDISPLAY RESTART: "+refreshp.getDisplay_draw().size());
				refreshp.start();
			}*/
		
		System.out.println("Nombre de display connectés: "+UtileSpin._display_draw.size());
		
		// On construit l'objet JSON pour l'expediteur
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        System.out.println("TIMESPIN RECORD DIS: "+UtileSpin._display_draw.get(cds.getRang()).getTimeSpin());
        objectBuilder.add("timespin", UtileSpin._display_draw.get(cds.getRang()).getTimeSpin());
        arrayBuilder.add(objectBuilder);

	       // On renvois le résultat
	       response.getWriter().write(arrayBuilder.build().toString());
	  
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
