package servlets;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import business.Refresh;
import config.UtileKeno;
import modele.ControlDisplayKeno;
import superbetDAO.CaissierDAO;
import superbetDAO.DAOFactory;
import superbetDAO.KenoDAO;

public class RecordDisplay extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8135000056645051498L;
	public static final String ATT_DATA_REFRESH = "refresh";
	public static final String CONF_DAO_FACTORY = "daofactory";
	
	private KenoDAO kenoDao;
	private Refresh refresh;
	private CaissierDAO caissierDao;
	/**
     * @see HttpServlet#HttpServlet()
     */
	
	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO caissier */
			this.refresh = ( (Refresh)getServletContext().getAttribute( ATT_DATA_REFRESH ));
			this.kenoDao = ( (DAOFactory)getServletContext().getAttribute( CONF_DAO_FACTORY )).getKenoDao();
	}
	
	public RecordDisplay(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
//		String login = request.getParameter("coderace");
//		String temps = request.getParameter("tiempo");
//		
//		ControlDisplayKeno cds = new ControlDisplayKeno(login);
//		
//		response.setContentType("application/json; charset=UTF-8");
//		response.setHeader("Cache-Control", "no-cache");
//	
//     	JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
//     	
//		// On construit l'objet JSON pour l'expediteur
//        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
//        objectBuilder.add("timekeno", UtileKeno._display_draw.get(cds.getRang()).getTimeKeno());
//        objectBuilder.add("gamestate", UtileKeno._display_draw.get(cds.getRang()).getGameState());
//        objectBuilder.add("combi", UtileKeno._display_draw.get(cds.getRang()).getStr_draw_combi());
//        arrayBuilder.add(objectBuilder);
//
//	       // On renvois le résultat
//	       response.getWriter().write(arrayBuilder.build().toString());
	  
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
