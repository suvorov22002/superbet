package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import business.ReportingForm;
import superbetDAO.AirtimeDAO;
import superbetDAO.CaissierDAO;
import superbetDAO.DAOFactory;
import superbetDAO.KenoDAO;
import superbetDAO.MisekDAO;
import superbetDAO.PartnerDAO;
import superbetDAO.UtilDAO;
import superbetDAO.VersementDAO;

public class Reporting extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public static final String VUE = "/WEB-INF/admin.jsp";
	public static final String ATT_FORM = "report_form";
	public static final String CONF_DAO_FACTORY = "daofactory";
	private KenoDAO kenoDao;
	private PartnerDAO partnerDao;
	private AirtimeDAO airtimeDao;
	private MisekDAO misekDao;
	private VersementDAO verstDao;
	private CaissierDAO caissierDao;
	
	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO caissier */
		this.airtimeDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getAirtimeDao();
		this.partnerDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getPartnerDao();
		this.misekDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getMisekDao();
		this.caissierDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getCaissierDao();
		this.kenoDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getKenoDao();
		this.verstDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getVersementDao();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ReportingForm report_form = new ReportingForm(airtimeDao, partnerDao, misekDao, caissierDao, kenoDao,verstDao);
		
		report_form.manage_reporting(request);
	
		request.setAttribute( ATT_FORM, report_form );
		request.setAttribute("state", 5);
		
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}
}
