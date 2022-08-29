package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modele.Caissier;
import superbetDAO.AirtimeDAO;
import superbetDAO.CaissierDAO;
import superbetDAO.DAOFactory;
import superbetDAO.MisekDAO;
import superbetDAO.MisepDAO;
import superbetDAO.UtilDAO;
import superbetDAO.VersementDAO;
import business.RecordUser;
import business.ShiftForm;

/**
 * Servlet implementation class Shift
 */

public class Shift extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String ATT_USER = "caissier";
	public static final String ATT_FORM = "s_form";
	public static final String VUE = "/login.jsp";
	public static final String VUE_CAISSIER = "/WEB-INF/caishier.jsp";
	public static final String ATT_DATA_RECORD = "recordUSer";
	
	private CaissierDAO caissierDao;
	private UtilDAO utilDao;
	private MisekDAO misekDao;
	private MisepDAO misepDao;
	private VersementDAO verstDao;
	private RecordUser refresh;
	private AirtimeDAO airtimeDao;
	
	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO caissier */
			this.caissierDao = ( (DAOFactory)getServletContext().getAttribute( CONF_DAO_FACTORY )).getCaissierDao();
			this.utilDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getUtilDao();
			this.misekDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getMisekDao();
			this.misepDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getMisepDao();
			this.verstDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getVersementDao();	
			this.refresh = ( (RecordUser)getServletContext().getAttribute( ATT_DATA_RECORD ));
			this.airtimeDao =  ( (DAOFactory)getServletContext().getAttribute( CONF_DAO_FACTORY )).getAirtimeDao();
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Shift() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.getServletContext().getRequestDispatcher(VUE_CAISSIER).forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/* Récupération de la session depuis la requête */
	    
	    		ShiftForm s_form = new ShiftForm(utilDao,  misekDao, misepDao, verstDao, caissierDao, airtimeDao);
	    		Caissier caissier = s_form.endshift(request);
	    		
	    		if(caissier == null){
	    			response.sendRedirect(request.getContextPath()+VUE);
	    		}
	    		else{
	    			if("N".equalsIgnoreCase(caissier.getStatut())){
	    				int i = 0;
	    		    	for(Caissier cais : refresh.getCustomers()){
	    					if(caissier.getLoginc().equalsIgnoreCase(cais.getLoginc())){
	    						refresh.removeClient(i);
	    						break;
	    					}
	    					i++;
	    				 }
	    		    	System.out.println("Nombre de caissiers: "+refresh.getCustomers().size());
	    		    	response.sendRedirect(request.getContextPath()+VUE);
	    			}
	    			else if("C".equalsIgnoreCase(caissier.getStatut())){
	    				request.setAttribute(ATT_FORM, s_form);
	    				request.setAttribute("state", 5);
	    				this.getServletContext().getRequestDispatcher(VUE_CAISSIER).forward(request, response);
	    			}
	    		}
	}

}
