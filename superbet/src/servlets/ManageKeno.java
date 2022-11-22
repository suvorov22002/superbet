package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import business.ManageKenoForm;
import modele.Caissier;
import modele.Coupon;
import modele.SlipForm;
import superbetDAO.AirtimeDAO;
import superbetDAO.ConfigDAO;
import superbetDAO.DAOFactory;
import superbetDAO.EffChoicekDAO;
import superbetDAO.KenoDAO;
import superbetDAO.MisekDAO;
import superbetDAO.Misek_tempDAO;
import superbetDAO.MisetDAO;
import superbetDAO.PartnerDAO;
import superbetDAO.UtilDAO;

public class ManageKeno extends HttpServlet {
	
	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String ATT_COUPON = "coupon";
	public static final String ATT_FORM = "k_form";
	public static final String VUE_CAISSIER = "/WEB-INF/caishier.jsp";
	public static final String VUE = "/login.jsp";
	
	private KenoDAO kenoDao;
	private MisetDAO misetDao;
	private EffChoicekDAO effchoicekDao;
	private UtilDAO utilDao;
	private MisekDAO misekDao;
	private Misek_tempDAO misektpDao;
	private PartnerDAO partnerDao;
	private AirtimeDAO airtimeDao;
	private ConfigDAO configDao;
	
	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO caissier */
			this.kenoDao = ( (DAOFactory)getServletContext().getAttribute( CONF_DAO_FACTORY )).getKenoDao();
			this.misetDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getMisetDao();
			this.effchoicekDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getEffChoicekDao();
			this.utilDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getUtilDao();
			this.misekDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getMisekDao();
			this.misektpDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getMisektpDao();
			this.partnerDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getPartnerDao();
			this.airtimeDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getAirtimeDao();
			this.configDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getConfigDao();
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		
		SlipForm slip = new SlipForm();
		HttpSession session = req.getSession();
	    Caissier caissier = (Caissier)session.getAttribute("caissier");
	    
	    //System.out.println(req.getContextPath()+VUE);
    	
	    if(caissier != null){
	    	ManageKenoForm k_form = new ManageKenoForm(kenoDao,misetDao,utilDao,effchoicekDao,misekDao,misektpDao,partnerDao,airtimeDao,configDao);
			Coupon coupon = k_form.print(req, caissier);
			slip.setCoupon(coupon);
			slip.set_fecha(k_form.get_fecha());
			slip.setMultiplicite(k_form.getMultiplicite());
			slip.setResultat(k_form.getResultat());
			slip.setPath(req.getContextPath());
			
	    }
	    else {
	    	session.invalidate();
	    	res.sendRedirect(req.getContextPath()+VUE);
	    }
		
	    res.setContentType("application/json; charset=UTF-8");
		res.setHeader("Cache-Control", "no-cache");
	    
		String json = new Gson().toJson(slip);
		res.getWriter().write(json);
		
		//this.getServletContext().getRequestDispatcher(VUE_CAISSIER).forward(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		
		/* Récupération de la session depuis la requête */
	    HttpSession session = req.getSession();
	    Caissier caissier = (Caissier)session.getAttribute("caissier");
	    if(caissier != null){
	    	ManageKenoForm k_form = new ManageKenoForm(kenoDao,misetDao,utilDao,effchoicekDao,misekDao,misektpDao,partnerDao,airtimeDao,configDao);
			Coupon coupon = k_form.print(req, caissier);
//			if(coupon !=null){
//				System.out.println("IMPRIMER: "+k_form.getImprimer()+" MULTI: "+k_form.getMultiplicite());
//				System.out.println("PARI: "+coupon.getCodepari()+" COTE: "+coupon.getEventscote());
//			}
			
//			System.out.println("PATH: "+req.getContextPath());
			
			req.setAttribute( ATT_FORM, k_form );
			req.setAttribute( ATT_COUPON, coupon );
			req.setAttribute("state", 1);
			req.setAttribute("path", req.getContextPath());
			
			
			this.getServletContext().getRequestDispatcher(VUE_CAISSIER).forward(req, res);
	    }
	    else{
	    	session.invalidate();
	    	res.sendRedirect(req.getContextPath()+VUE);
	    }
		
	}

}
