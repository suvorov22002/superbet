package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import modele.Cagnotte;
import modele.Partner;
import superbetDAO.CagnotteDAO;
import superbetDAO.DAOFactory;
import superbetDAO.PartnerDAO;

public class RetrieveCagnotte extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2975048994743211406L;
	public static final String CONF_DAO_FACTORY = "daofactory";
	private CagnotteDAO cagnotteDao;
	private PartnerDAO partnerDao;
	
	
	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO caissier */
		this.cagnotteDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getCagotteDao();
		this.partnerDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getPartnerDao();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter("coderace");
		System.out.println("login cagnotte: "+login);
		Partner p = partnerDao.find(login);
		
		Cagnotte last = cagnotteDao.find(p.getIdpartner());
		
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");

	     String json = new Gson().toJson(last);
		 response.getWriter().write(json);
	      
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
