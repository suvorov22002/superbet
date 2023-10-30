package servlets;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import business.AuthenticationForm;
import business.RecordUser;
import modele.Caissier;
import superbetDAO.AirtimeDAO;
import superbetDAO.CaissierDAO;
import superbetDAO.DAOFactory;
import superbetDAO.PartnerDAO;

public class Authentication extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8010358797044903327L;
	
	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String ATT_USER = "caissier";
	public static final String ATT_DATA_RECORD = "recordUSer";
	public static final String ATT_FORM = "form";
	public static final String VUE = "/login.jsp";
	public static final String VUE_CAISSIER = "/WEB-INF/caishier.jsp";
	public static final String VUE_ADMIN = "/WEB-INF/admin.jsp";
	private RecordUser refresh;
	
	private CaissierDAO caissierDao;
	private AirtimeDAO airtimeDao;
	private PartnerDAO partnerDao;
	
	public void init() throws ServletException {
	/* Récupération d'une instance de notre DAO caissier */
		this.caissierDao = ( (DAOFactory)getServletContext().getAttribute( CONF_DAO_FACTORY )).getCaissierDao();
		this.refresh = ( (RecordUser)getServletContext().getAttribute( ATT_DATA_RECORD ));
		this.airtimeDao =  ( (DAOFactory)getServletContext().getAttribute( CONF_DAO_FACTORY )).getAirtimeDao();
		this.partnerDao =  ( (DAOFactory)getServletContext().getAttribute( CONF_DAO_FACTORY )).getPartnerDao();
	}
	 
   protected void processRequest(HttpServletRequest req, HttpServletResponse res)
	            throws ServletException, IOException {
	   
	   /* Récupération de la session depuis la requête */
       HttpSession session = req.getSession(true);
       boolean inside = false;
	   AuthenticationForm form = new AuthenticationForm(caissierDao, airtimeDao,partnerDao); 
	   Caissier caissier = form.authenticateCaissier(req);
	 
	   req.setAttribute( ATT_FORM, form );
	   req.setAttribute("state", 1);
	   
	   if(caissier != null){
		 
		   Optional<Caissier> optionalCaissier = refresh.getCustomers().stream()
				   .filter(c -> c.getLoginc().equals(caissier.getLoginc())).findFirst();
		   
		   if(optionalCaissier.isPresent()) {
			   inside = true;
		   }
		   else {
			   refresh.addClient(caissier);
		   }
		   
//		   for(Caissier cais : refresh.getCustomers()){
//				//System.out.println("CAISSIER: "+caissier.getLoginc());
//				if(caissier.getLoginc().equalsIgnoreCase(cais.getLoginc())){
//					inside = true;
//					break;
//				}
//		  }
//		  if(!inside){
//				refresh.addClient(caissier);
//		  }
		   
		   session.setAttribute( ATT_USER, caissier );
			//setting session to expiry in 25 mins
		   session.setMaxInactiveInterval(25*60);
			
			
		   Cookie loginCookie = new Cookie(ATT_USER, caissier.getLoginc());
		   loginCookie.setMaxAge(25*60);
		   res.addCookie(loginCookie);
		   
		 //Get the encoded URL string
			String encodedURL = res.encodeRedirectURL("LoginSuccess.jsp");
			
		     

	    	if(caissier.getProfil() == 1){
	    		encodedURL = res.encodeRedirectURL(VUE_ADMIN);
	    		this.getServletContext().getRequestDispatcher(encodedURL).forward(req, res);
	    		//this.getServletContext().getRequestDispatcher(VUE_ADMIN).forward(req, res);
	    		//res.sendRedirect(req.getContextPath() + "/admin.jsp");
	    	}
	    	else if(caissier.getProfil() == 2){
	    		encodedURL = res.encodeRedirectURL(VUE_CAISSIER);
	    		this.getServletContext().getRequestDispatcher(encodedURL).forward(req, res);
	    		//this.getServletContext().getRequestDispatcher(VUE_CAISSIER).forward(req, res);
	    		//res.sendRedirect(VUE_CAISSIER);
	    	}
	    }
	    else{
	    	//res.sendRedirect(req.getContextPath()+VUE);
	    	this.getServletContext().getRequestDispatcher(VUE).forward(req, res);
	    }
		
		
   }
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		this.getServletContext().getRequestDispatcher(VUE).forward(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		processRequest(req, res);
	}

}
