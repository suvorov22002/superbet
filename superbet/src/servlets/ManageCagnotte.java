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
import modele.BonusSet;
import superbetDAO.api.exeception.DAOAPIException;
import superbetDAO.api.implementations.SuperGameDAOAPI;
import superbetDAO.api.interfaces.ISuperGameDAOAPILocal;

public class ManageCagnotte extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  ISuperGameDAOAPILocal  supergameAPI;

	public void init() throws ServletException {
		supergameAPI = SuperGameDAOAPI.getInstance();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String login = request.getParameter("coderace");

		try {
			
			BonusSet bns = this.supergameAPI.getSuperGameDAO().getbonuskeno(Params.url, login);
			
			response.setContentType("application/json; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");

			JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();		
			// On construit le tableau
			System.out.println("bns "+bns.getBarcode());
			//			// On construit l'objet JSON pour l'expediteur
			JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
			//	     //      if(rang != -1){
			objectBuilder.add("barcode", bns.getBarcode());
			objectBuilder.add("partner", bns.getCoderace());
			objectBuilder.add("mise", bns.getMise());
			arrayBuilder.add(objectBuilder);
			response.getWriter().write(arrayBuilder.build().toString());
		} catch (IOException | JSONException | URISyntaxException | DAOAPIException e) {
			e.printStackTrace();
		}


	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
