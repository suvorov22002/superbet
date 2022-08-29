package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modele.Partner;
import superbetDAO.DAOFactory;
import superbetDAO.PartnerDAO;

import com.google.gson.Gson;

public class RetrievePartner extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String CONF_DAO_FACTORY = "daofactory";
	private PartnerDAO partnerDao;
	
	
	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO caissier */
		this.partnerDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getPartnerDao();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idpartner = request.getParameter("coderace");
		//System.out.println("Idpartner: "+idpartner);
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<Partner> last;
		
		last = partnerDao.getAllPartners();
//		if(idpartner.equalsIgnoreCase("1")) {
//			last = partnerDao.getAllPartners();
//		}
//		else {
//			last = partnerDao.getAllPartnersByGroup(idpartner);
//		}
		
		
		for(Partner partn : last){
			list.add(partn.getCoderace());
	     }
		
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");

	     String json = new Gson().toJson(list);
		 response.getWriter().write(json);
	      
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
