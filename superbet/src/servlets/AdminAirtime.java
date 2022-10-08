package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import business.AirtimeForm;
import superbetDAO.AirtimeDAO;
import superbetDAO.CaissierDAO;
import superbetDAO.DAOFactory;
import superbetDAO.EffChoicekDAO;
import superbetDAO.KenoDAO;
import superbetDAO.MisekDAO;
import superbetDAO.Misek_tempDAO;
import superbetDAO.MisetDAO;
import superbetDAO.PartnerDAO;
import superbetDAO.UtilDAO;
import superbetDAO.VersementDAO;

public class AdminAirtime extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4122640932918480055L;
	public static final String VUE = "/WEB-INF/admin.jsp";
	public static final String ATT_FORM = "airtime_form";
	public static final String CONF_DAO_FACTORY = "daofactory";
	private AirtimeDAO airtimeDao;
	private CaissierDAO caissierDao;
	private PartnerDAO partnerDao;
	
	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO caissier */
		this.airtimeDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getAirtimeDao();		
		this.caissierDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getCaissierDao();
		this.partnerDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getPartnerDao();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		AirtimeForm airtime_form = new AirtimeForm(airtimeDao, caissierDao, partnerDao);
		
		airtime_form.manage_admin(request);
		
		request.setAttribute( ATT_FORM, airtime_form );
		request.setAttribute("state", 3);
		
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}
	
	
}
