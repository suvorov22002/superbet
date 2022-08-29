package servlets;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modele.Caissier;
import superbetDAO.CaissierDAO;
import superbetDAO.DAOFactory;
import business.RecordUser;

/**
 * Servlet implementation class RecordClient
 */

public class RecordClient extends HttpServlet {
	
       
    /**
	 * 
	 */
	private static final long serialVersionUID = -3511431397687299183L;
	
	public static final String ATT_DATA_RECORD = "recordUSer";
	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String VUE = "/login.jsp";
	private RecordUser refresh;
	private CaissierDAO caissierDao;
	/**
     * @see HttpServlet#HttpServlet()
     */
	
	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO caissier */
			this.refresh = ( (RecordUser)getServletContext().getAttribute( ATT_DATA_RECORD ));
			this.caissierDao = ( (DAOFactory)getServletContext().getAttribute( CONF_DAO_FACTORY )).getCaissierDao();
	}
	
    public RecordClient() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Caissier caissier = new Caissier();
		boolean inside = false;
		
		String login = request.getParameter("login");
		String state = request.getParameter("state");
		
		HttpSession session = request.getSession();
	    if(session != null){
	    	caissier = (Caissier)session.getAttribute("caissier");
	    	System.out.println("CAISSIER: "+caissier);
	    	if(caissier != null){
	    		System.out.println("CAISSIER: "+caissier.getLoginc());
	    		caissier.setStatut("N");
		    	caissierDao.updateState(caissier);
		    	int i = 0;
		    	for(Caissier cais : refresh.getCustomers()){
					if(caissier.getLoginc().equalsIgnoreCase(cais.getLoginc())){
						inside = true;
						refresh.removeClient(i);
						break;
					}
					i++;
				 }
	    	}
	    	
	    	session.invalidate();
	    }
	
		System.out.println("Nombre de caissier connectés: "+refresh.getCustomers().size());
		
		
//		 response.setContentType("application/json; charset=UTF-8");
//		 response.setHeader("Cache-Control", "no-cache");
//		
//		 
//		// On construit le tableau JSON des résultat
//        	JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
//        
//       // On construit l'objet JSON pour l'expediteur
//           JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
//           objectBuilder.add("tailleconnected", refresh.getCustomers().size());
//           
//           arrayBuilder.add(objectBuilder);
	       // On renvois le résultat
	      // response.getWriter().write(arrayBuilder.build().toString());
	       response.sendRedirect(request.getContextPath()+VUE);
	}

}
