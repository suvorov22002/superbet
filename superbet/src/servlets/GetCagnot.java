package servlets;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONException;

import com.google.gson.Gson;

import config.Params;
import modele.Cagnotte;
import superbetDAO.api.exeception.DAOAPIException;
import superbetDAO.api.implementations.SuperGameDAOAPI;
import superbetDAO.api.interfaces.ISuperGameDAOAPILocal;

public class GetCagnot extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private  ISuperGameDAOAPILocal  supergameAPI;
	
	public GetCagnot() {
		super();
	}
	
	public void init() throws ServletException {
		supergameAPI = new SuperGameDAOAPI();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//UtileKeno.cagnotte = Integer.parseInt(request.getParameter("state"));
		String coderace = request.getParameter("coderace");
	
		 try {
			 String json;	
			 Cagnotte cgt = supergameAPI.getSuperGameDAO().getCagnot(Params.url, coderace);
				System.out.println("[GetCagnot - Cagnotte: ] "+cgt);
				
				response.setContentType("application/json; charset=UTF-8");
				response.setHeader("Cache-Control", "no-cache");
                if (cgt == null ) json = new Gson().toJson(new Cagnotte());
                else json = new Gson().toJson(cgt);
				
                response.getWriter().write(json);
				 
		 } catch (IOException | JSONException | URISyntaxException | DAOAPIException e) {
				e.printStackTrace();
		 }
       
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}
