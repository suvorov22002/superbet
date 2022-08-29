package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import superbetDAO.DAOFactory;
import superbetDAO.EffChoicekDAO;
import superbetDAO.KenoDAO;
import superbetDAO.MisekDAO;
import superbetDAO.Misek_tempDAO;
import superbetDAO.MisetDAO;
import superbetDAO.PartnerDAO;
import superbetDAO.UtilDAO;
import superbetDAO.VersementDAO;
import business.AdminForm;

/**
 * Servlet implementation class Administration
 */

	public class Administration extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8586088539299140458L;
	public static final String VUE = "/WEB-INF/admin.jsp";
	public static final String ATT_FORM = "fin_form";
	public static final String CONF_DAO_FACTORY = "daofactory";
       
	private KenoDAO kenoDao;
	private MisetDAO misetDao;
	private EffChoicekDAO effchoicekDao;
	private UtilDAO utilDao;
	private MisekDAO misekDao;
	private Misek_tempDAO misektpDao;
	private PartnerDAO partnerDao;
	private VersementDAO verstDao;
	
	
	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO caissier */
			this.kenoDao = ( (DAOFactory)getServletContext().getAttribute( CONF_DAO_FACTORY )).getKenoDao();
			this.misetDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getMisetDao();
			this.effchoicekDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getEffChoicekDao();
			this.utilDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getUtilDao();
			this.misekDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getMisekDao();
			this.misektpDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getMisektpDao();
			this.partnerDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getPartnerDao();
			this.verstDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getVersementDao();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		AdminForm fin_form = new AdminForm(kenoDao,misetDao,utilDao,effchoicekDao,misekDao,
				verstDao, partnerDao, misektpDao);
		
		fin_form.manage_admin(request);
		
		request.setAttribute( ATT_FORM, fin_form );
		request.setAttribute("state", 1);
		
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

}
