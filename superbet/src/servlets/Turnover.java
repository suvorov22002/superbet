package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import business.TurnoverForm;
import superbetDAO.CaissierDAO;
import superbetDAO.DAOFactory;
import superbetDAO.GameCycleDAO;
import superbetDAO.KenoDAO;
import superbetDAO.MisekDAO;
import superbetDAO.PartnerDAO;
import superbetDAO.UtilDAO;

public class Turnover extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String VUE = "/WEB-INF/admin.jsp";
	public static final String ATT_FORM = "turn_form";
	public static final String CONF_DAO_FACTORY = "daofactory";
	private KenoDAO kenoDao;
	private UtilDAO utilDao;
	private PartnerDAO partnerDao;
	private GameCycleDAO gmcDao;
	private MisekDAO misekDao;
	private CaissierDAO caissierDao;
	
	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO caissier */
		this.gmcDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getGameCycleDao();
		this.partnerDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getPartnerDao();
		this.misekDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getMisekDao();
		this.caissierDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getCaissierDao();
		this.kenoDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getKenoDao();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		TurnoverForm turn_form = new TurnoverForm(gmcDao, partnerDao, misekDao, caissierDao, kenoDao);
		
		turn_form.manage_admin(request);
	
		request.setAttribute( ATT_FORM, turn_form );
		request.setAttribute("state", 2);
		
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}
	
}
