package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import superbetDAO.DAOFactory;
import superbetDAO.KenoDAO;

public class ValiderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String CONF_DAO_FACTORY = "daofactory";
	
	private KenoDAO kenoDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ValiderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO caissier */
		this.kenoDao = ( (DAOFactory)getServletContext().getAttribute( CONF_DAO_FACTORY )).getKenoDao();
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String resultat = "invalide";
		//String jackpot = "3000";
	    String valeur = request.getParameter("valeur");
	    
	    double  jackpot = kenoDao.findBonusAmount();

	    response.setContentType("text/xml");
	    response.setHeader("Cache-Control", "no-cache");

	    if ((valeur != null) && valeur.startsWith("X")) {
	      resultat = "valide";
	    }

	    response.getWriter().write("<message>"+jackpot+"</message>");
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
