package servlets;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONException;

import config.Params;
import superbetDAO.api.exeception.DAOAPIException;
import superbetDAO.api.implementations.SuperGameDAOAPI;
import superbetDAO.api.interfaces.ISuperGameDAOAPILocal;

public class ManageAirtime extends HttpServlet{
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private  ISuperGameDAOAPILocal  supergameAPI;
	
	public void init() throws ServletException {
		supergameAPI = new SuperGameDAOAPI();
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String login = request.getParameter("caissier");
		//System.out.println("login: "+login);
		Long idCaissier = Long.parseLong(login);
		double balance = 0;	
		
		try {
			balance = supergameAPI.getSuperGameDAO().getSolde(Params.url, idCaissier);
			balance = (double)((int)(balance*100))/100;
		} catch (IOException | JSONException | URISyntaxException | DAOAPIException e) {
			e.printStackTrace();
		}
					
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();	
		JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
		
		
		
		objectBuilder.add("balance", balance);
	    arrayBuilder.add(objectBuilder);
	    response.getWriter().write(arrayBuilder.build().toString());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
