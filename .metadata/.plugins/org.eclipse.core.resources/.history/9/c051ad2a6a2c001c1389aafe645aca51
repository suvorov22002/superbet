package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import modele.Keno;
import modele.Partner;
import superbetDAO.DAOFactory;
import superbetDAO.KenoDAO;
import superbetDAO.PartnerDAO;

public class RetrieveMultiplicateur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String CONF_DAO_FACTORY = "daofactory";
	private PartnerDAO partnerDao;
	private KenoDAO kenoDao;
	private Long token;
	
	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO caissier */
		this.partnerDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getPartnerDao();
		this.kenoDao = ( (DAOFactory)getServletContext().getAttribute( CONF_DAO_FACTORY )).getKenoDao();
		//this.token = (Long)getServletContext().getAttribute("token");
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String coderace = request.getParameter("partner");
		ArrayList<Keno> keno = kenoDao.find_Last_draw(coderace);
//		System.out.println("TOKEN: "+coderace);
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");

	     String json = new Gson().toJson(keno);
		 response.getWriter().write(json);
	      
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
