package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modele.Partner;
import superbetDAO.DAOFactory;
import superbetDAO.PartnerDAO;

/**
 * Servlet implementation class DisplayKeno
 */

public class DisplayKeno extends HttpServlet {
	
       
    /**
	 * 
	 */
	private static final long serialVersionUID = 3979559685596288826L;
	public static final String CONF_DAO_FACTORY = "daofactory";
	private PartnerDAO partnerDao;
	
	public static final String VUE = "/WEB-INF/keno.jsp";

	/**
     * @see HttpServlet#HttpServlet()
     */
    public DisplayKeno() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init() throws ServletException {
    	/* Récupération d'une instance de notre DAO caissier */
        //this.kenoDao = ( (DAOFactory)getServletContext().getAttribute( CONF_DAO_FACTORY )).getKenoDao();
    	this.partnerDao = ((DAOFactory)getServletContext().getAttribute( CONF_DAO_FACTORY )).getPartnerDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String partner = req.getParameter("partner");
		
		Partner coderace = partnerDao.find(partner);
		
		if(coderace != null){
			//System.out.println("[DisplaKeno ] "+partner+" [Partner] "+coderace);
			req.setAttribute("partner", coderace.getCoderace());
			this.getServletContext().getRequestDispatcher(VUE).forward(req, res);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		doGet(request, response);
	}
}
