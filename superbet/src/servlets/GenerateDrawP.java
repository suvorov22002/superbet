package servlets;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modele.ControlDisplaySpin;
import superbetDAO.DAOFactory;
import superbetDAO.SpinDAO;
import config.UtileSpin;

public class GenerateDrawP extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6300939289747107631L;
	public static final String CONF_DAO_FACTORY = "daofactory";
	private SpinDAO spinDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GenerateDrawP() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO caissier */
		this.spinDao = ( (DAOFactory)getServletContext().getAttribute( CONF_DAO_FACTORY )).getSpinDao();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String login = request.getParameter("coderace");
		 ControlDisplaySpin __cds = UtileSpin._display_draw.get(UtileSpin._checkExistingSameDisplayCoderace(login));
		
		 //String str = Refreshp.cds.getDrawCombip();
		 String str = __cds.getDrawCombip();
		 
		 System.out.println("GENERATEP SPINDRAW:"+str);
		 
		 response.setContentType("application/json; charset=UTF-8");
		 response.setHeader("Cache-Control", "no-cache");
		
		 
		// On construit le tableau JSON des résultat
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        
      
       // On construit l'objet JSON pour l'expediteur
           JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
           objectBuilder.add("drawnumbp", str);
           
           arrayBuilder.add(objectBuilder);
       
      
       // On renvois le résultat
         response.getWriter().write(arrayBuilder.build().toString());
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
}
