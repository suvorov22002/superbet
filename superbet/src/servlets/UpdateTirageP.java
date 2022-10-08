package servlets;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modele.Partner;
import modele.Spin;
import superbetDAO.DAOFactory;
import superbetDAO.PartnerDAO;
import superbetDAO.SpinDAO;
import config.UtileSpin;

public class UpdateTirageP extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 870683877387849478L;
	public static final String CONF_DAO_FACTORY = "daofactory";
	private SpinDAO spinDao;
	private PartnerDAO partnerDao;
	
	public UpdateTirageP(){
		super();
	}
	
	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO caissier */
		this.spinDao = ( (DAOFactory)getServletContext().getAttribute( CONF_DAO_FACTORY )).getSpinDao();
		this.partnerDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getPartnerDao();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
				String tirage, resultat, new_draw, previous_new_draw;
			    String id = request.getParameter("coderace");
			    int timeSpin;
			    Partner partner = partnerDao.findById(Long.valueOf(id));
			    
				Spin spin = spinDao.find_Max_draw(partner.getCoderace());
				Spin _spin = spinDao.searchResultP(spin.getDrawNumP());
				
				new_draw = spin.getDrawnumbP();
				previous_new_draw = _spin.getDrawNumP();
				int rang =  UtileSpin._checkExistingSameDisplayCoderace(partner.getCoderace());
				if(rang != -1){
	        	   timeSpin =  UtileSpin._display_draw.get(rang).getTimeSpin();
	            }
	            else{
	        	   timeSpin = UtileSpin.timeSpin;
	            }
				
				//System.out.println("UPDATETIRAGZEP "+ timeSpin);
				boolean can_bet = UtileSpin.canbet_p;
				
				if(!new_draw.equalsIgnoreCase("'0'")){ //tirage termine
					tirage = spin.getDrawNumP();
					resultat = "pari ouvert";
				}
				else{
					tirage = spin.getDrawNumP();
					resultat = "tirage en cours";
				}
				
				 response.setContentType("application/json; charset=UTF-8");
				 response.setHeader("Cache-Control", "no-cache");
				
				// On construit le tableau JSON des résultat
		         	JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		         
		        // On construit l'objet JSON pour l'expediteur
		            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
		            
		            objectBuilder.add("drawnump", tirage);
		            objectBuilder.add("pr_drawnump", previous_new_draw);
		            objectBuilder.add("message", resultat);
		            objectBuilder.add("timespin", timeSpin);
		            objectBuilder.add("canbet", can_bet);
		            
		            arrayBuilder.add(objectBuilder);
		        
		       
		        // On renvois le résultat
		        response.getWriter().write(arrayBuilder.build().toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}
}
