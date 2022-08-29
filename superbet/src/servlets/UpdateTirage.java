package servlets;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONException;

import config.Params;
import config.UtileKeno;
import modele.Keno;
import modele.Partner;
import superbetDAO.DAOFactory;
import superbetDAO.KenoDAO;
import superbetDAO.PartnerDAO;
import superbetDAO.api.exeception.DAOAPIException;
import superbetDAO.api.implementations.SuperGameDAOAPI;
import superbetDAO.api.interfaces.ISuperGameDAOAPILocal;

public class UpdateTirage extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5847988834448132951L;
	public static final String CONF_DAO_FACTORY = "daofactory";
	private KenoDAO kenoDao;
	private PartnerDAO partnerDao;
	private  ISuperGameDAOAPILocal  supergameAPI;
	
	public UpdateTirage(){
		super();
	}
	
	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO caissier */
		this.kenoDao = ( (DAOFactory)getServletContext().getAttribute( CONF_DAO_FACTORY )).getKenoDao();
		this.partnerDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getPartnerDao();
		supergameAPI = new SuperGameDAOAPI();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
			    String id = request.getParameter("coderace");
//				int rang;
//			    Partner partner = partnerDao.findById(Integer.parseInt(id));
//			    rang =  UtileKeno._checkExistingSameDisplayCoderace(partner.getCoderace());
//			    String coderace = partner.getCoderace();
		//	    System.out.println("updatetirage: "+coderace);
//			    Keno keno = null;
//			    String tirage, resultat;
//			    try {
//			    	keno =  supergameAPI.getSuperGameDAO().maxDraw(Params.url, coderace);
//				} catch (IOException | JSONException | URISyntaxException | DAOAPIException e) {
//					e.printStackTrace();
//				}


				
				
				 response.setContentType("application/json; charset=UTF-8");
				 response.setHeader("Cache-Control", "no-cache");
				
				 
				// On construit le tableau JSON des résultat
		         	JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		         
		        // On construit l'objet JSON pour l'expediteur
		           JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
		           
	        	   objectBuilder.add("drawnumk", UtileKeno.drawnumk);
		           objectBuilder.add("message", UtileKeno.messagek);
		           objectBuilder.add("timekeno", UtileKeno.timeKeno);
		           objectBuilder.add("canbet", UtileKeno.canbet);
		       //    System.out.println("updatetirage: "+UtileKeno.canbet);
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
