package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import business.ManageVersForm;
import modele.Caissier;
import modele.Versement;
import modele.Vform;
import superbetDAO.AirtimeDAO;
import superbetDAO.DAOFactory;
import superbetDAO.EffChoicekDAO;
import superbetDAO.EffChoicepDAO;
import superbetDAO.KenoDAO;
import superbetDAO.MisekDAO;
import superbetDAO.Misek_tempDAO;
import superbetDAO.MisepDAO;
import superbetDAO.MisetDAO;
import superbetDAO.PartnerDAO;
import superbetDAO.SpinDAO;
import superbetDAO.UtilDAO;
import superbetDAO.VersementDAO;

/**
 * Servlet implementation class ManageVersement
 */

public class ManageVersement extends HttpServlet {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7594553789606616326L;
	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String ATT_COUPON = "v_coupon";
	public static final String ATT_FORM = "v_form";
	public static final String VUE_CAISSIER = "/WEB-INF/caishier.jsp";
	public static final String URL_REDIRECTION = "./login.jsp";
	
	private static final String FIELD_CODE = "barcode";
	private static final String FIELD_VERS = "versement";
	
	private KenoDAO kenoDao;
	private SpinDAO spinDao;
	private MisetDAO misetDao;
	private EffChoicekDAO effchoicekDao;
	private EffChoicepDAO effchoicepDao;
	private UtilDAO utilDao;
	private MisekDAO misekDao;
	private MisepDAO misepDao;
	private Misek_tempDAO misektpDao;
	private PartnerDAO partnerDao;
	private VersementDAO verstDao;
	private AirtimeDAO airtimeDao;
	
	
	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO caissier */
			this.kenoDao = ( (DAOFactory)getServletContext().getAttribute( CONF_DAO_FACTORY )).getKenoDao();
			this.spinDao = ( (DAOFactory)getServletContext().getAttribute( CONF_DAO_FACTORY )).getSpinDao();
			this.misetDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getMisetDao();
			this.effchoicekDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getEffChoicekDao();
			this.effchoicepDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getEffChoicepDao();
			this.utilDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getUtilDao();
			this.misekDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getMisekDao();
			this.misepDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getMisepDao();
			this.misektpDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getMisektpDao();
			this.partnerDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getPartnerDao();
			this.verstDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getVersementDao();
			this.airtimeDao =  ( (DAOFactory)getServletContext().getAttribute( CONF_DAO_FACTORY )).getAirtimeDao();
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageVersement() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String ver = req.getParameter( "versement" );
		//System.out.println("MANAGE VERSEMENT: "+ver);
		Caissier caissier = null;
		Vform vf = new Vform();
		
		/* Récupération de la session depuis la requête */
	    HttpSession session = req.getSession();
	    caissier = (Caissier)session.getAttribute("caissier");
	    
	    if(caissier != null) {
	    	
	    	ManageVersForm v_form = new ManageVersForm(kenoDao,spinDao,misetDao,utilDao,effchoicekDao,misekDao,effchoicepDao,misepDao,
	    			verstDao, partnerDao, misektpDao, airtimeDao);
	    		
	    		Versement vers = v_form.traiterTicket(req, caissier);
	    		vf.setDrawData(v_form.getDrawData());
	    		vf.setEvenements(v_form.getEvenements());
	    		vf.setMultiplicite(v_form.getMultiplicite());
	    		vf.setResultat(v_form.getResultat());
	    		
	    		//System.out.println("MULTI: "+v_form.getEvenements());
	    		//req.setAttribute("state", 4);
	    		
	    		
	    }
	    else {
	    	session.invalidate();
			res.sendRedirect(URL_REDIRECTION);
	    }
	    
	    
		res.setContentType("application/json; charset=UTF-8");
		res.setHeader("Cache-Control", "no-cache");
	    
		String json = new Gson().toJson(vf);
		res.getWriter().write(json);
		
		//this.getServletContext().getRequestDispatcher(VUE_CAISSIER).forward(req, res);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//Caissier caissier = null;
		/* Récupération de la session depuis la requête */
	    HttpSession session = req.getSession();
	    Caissier caissier = (Caissier)session.getAttribute("caissier");
	    if(caissier != null) {
	    	ManageVersForm v_form = new ManageVersForm(kenoDao,spinDao,misetDao,utilDao,effchoicekDao,misekDao,effchoicepDao,misepDao,
	    			verstDao, partnerDao, misektpDao, airtimeDao);
	    		
	    		Versement vers = v_form.traiterTicket(req, caissier);
	    		
	    		System.out.println("MULTI: "+v_form.getEvenements());
	    		
	    		req.setAttribute(ATT_FORM, v_form);
	    		req.setAttribute(ATT_COUPON, vers);
	    		req.setAttribute("state", 4);
	    		
	    		this.getServletContext().getRequestDispatcher(VUE_CAISSIER).forward(req, res);
	    }
	    else {
	    	session.invalidate();
			res.sendRedirect(URL_REDIRECTION);
	    }

	}

}
