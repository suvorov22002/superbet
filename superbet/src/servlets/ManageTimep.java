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

public class ManageTimep extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 136914135627826108L;
	public static final String ATT_DATA_REFRESHP = "refreshp";
	public static final String CONF_DAO_FACTORY = "daofactory";
	
	private Refreshp refreshp;

	/**
     * @see HttpServlet#HttpServlet()
     */
	
	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO caissier */
			//this.refreshp = ( (Refreshp)getServletContext().getAttribute( ATT_DATA_REFRESHP ));
	}
	
	public ManageTimep(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String login = request.getParameter("coderace");
		int rang =  UtileSpin._checkExistingSameDisplayCoderace(login);
		//System.out.println("MANAGETIME: "+rang);
		
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
	
     	JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();		
			// On construit le tableau
        
			// On construit l'objet JSON pour l'expediteur
	           JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
	           if(rang != -1){
	        	   //objectBuilder.add("timespin", Refreshp.cds.getTimeSpin());
	        	  // System.out.println("TIME MANAGE TIMEP: "+Refreshp.cds.getTimeSpin());
	        	   objectBuilder.add("timespin", UtileSpin._display_draw.get(rang).getTimeSpin());
	        	  // System.out.println("TIME MANAGE TIMEP: "+UtileSpin._display_draw.get(rang).getTimeSpin());
	           }
	           else{
	        	   objectBuilder.add("timespin", "?");
	           }
	        	   
	           
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
