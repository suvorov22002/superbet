package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import business.ManageSpinForm;
import modele.Caissier;
import modele.Coupon;
import superbetDAO.DAOFactory;
import superbetDAO.EffChoicepDAO;
import superbetDAO.MisepDAO;
import superbetDAO.MisetDAO;
import superbetDAO.PartnerDAO;
import superbetDAO.SpinDAO;
import superbetDAO.UtilDAO;

/**
 * Servlet implementation class ManageSpin
 */

public class ManageSpin extends HttpServlet {
	
	
	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String ATT_COUPON = "pcoupon";
	public static final String ATT_FORM = "p_form";
	public static final String VUE_CAISSIER = "/WEB-INF/caishier.jsp";
	
	private SpinDAO spinDao;
	private MisetDAO misetDao;
	private EffChoicepDAO effchoicepDao;
	private UtilDAO utilDao;
	private MisepDAO misepDao;
	private PartnerDAO partnerDao;
	
	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO caissier */
			this.spinDao = ( (DAOFactory)getServletContext().getAttribute( CONF_DAO_FACTORY )).getSpinDao();
			this.misetDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getMisetDao();
			this.effchoicepDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getEffChoicepDao();
			this.utilDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getUtilDao();
			this.misepDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getMisepDao();
			this.partnerDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getPartnerDao();
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageSpin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.getServletContext().getRequestDispatcher(VUE_CAISSIER).forward(req, res);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/* Récupération de la session depuis la requête */
	    HttpSession session = req.getSession();
	    Caissier caissier = (Caissier)session.getAttribute("caissier");
	    
	    ManageSpinForm p_form = new ManageSpinForm(spinDao,misetDao,utilDao,effchoicepDao,misepDao,partnerDao);
		Coupon coupon = p_form.print(req, caissier);
		//System.out.println("IMPRIMER: "+p_form.getImprimer()+" MULTI: "+p_form.getMultiplicite());
		//System.out.println("PARI: "+coupon.getCodepari()+" COTE: "+coupon.getEventscote());
		
		req.setAttribute( ATT_FORM, p_form );
		req.setAttribute( ATT_COUPON, coupon );
		req.setAttribute("state", 6);
		req.setAttribute("path", req.getContextPath());
		
	    
		this.getServletContext().getRequestDispatcher(VUE_CAISSIER).forward(req, res);
	}

}
