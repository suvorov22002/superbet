package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modele.Caissier;
import superbetDAO.CaissierDAO;
import superbetDAO.DAOFactory;

import com.google.gson.Gson;

public class RetrieveUser extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public static final String CONF_DAO_FACTORY = "daofactory";
	private CaissierDAO caissierDao;
	
	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO caissier */
		this.caissierDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getCaissierDao();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter("coderace");
		
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<Caissier> last = caissierDao.findByPartner(login);
		for(Caissier caissier : last){
			if(!caissier.getLoginc().equalsIgnoreCase("admin"))
			list.add(caissier.getLoginc());
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
