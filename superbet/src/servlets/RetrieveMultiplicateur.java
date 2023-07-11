package servlets;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import org.codehaus.jettison.json.JSONException;

import config.Params;
import modele.KenoRes;
import superbetDAO.DAOFactory;
import superbetDAO.KenoDAO;
import superbetDAO.api.exeception.DAOAPIException;
import superbetDAO.api.implementations.SuperGameDAOAPI;
import superbetDAO.api.interfaces.ISuperGameDAOAPILocal;

public class RetrieveMultiplicateur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String CONF_DAO_FACTORY = "daofactory";
	private KenoDAO kenoDao;
	private  ISuperGameDAOAPILocal  supergameAPI;
	
	public void init() throws ServletException {
		((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getPartnerDao();
		this.kenoDao = ( (DAOFactory)getServletContext().getAttribute( CONF_DAO_FACTORY )).getKenoDao();
		supergameAPI = SuperGameDAOAPI.getInstance();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	      
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String coderace = request.getParameter("partner");
		List<KenoRes> keno = new ArrayList<>();
		
		try {
			keno = this.supergameAPI.getSuperGameDAO().getLastMulti(Params.url, coderace);
			String str;
			for(KenoRes kns : keno) {
				str = kns.getHeureTirage().replace("h", ":");
				kns.setHeureTirage(str);
			}
		} catch (IOException | JSONException | URISyntaxException | DAOAPIException e) {
			e.printStackTrace();
		}
		
		//System.out.println("Retrieve multplicateur: " + keno.size());
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");

	     String json = new Gson().toJson(keno);
		 response.getWriter().write(json);
	}
}
