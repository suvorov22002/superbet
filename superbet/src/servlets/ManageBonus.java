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
import config.UtileKeno;
import modele.BonusSet;
import superbetDAO.DAOFactory;
import superbetDAO.api.exeception.DAOAPIException;
import superbetDAO.api.implementations.SuperGameDAOAPI;
import superbetDAO.api.interfaces.ISuperGameDAOAPILocal;

public class ManageBonus extends HttpServlet{
	
	private  ISuperGameDAOAPILocal  supergameAPI;
	
	public void init() throws ServletException {
		supergameAPI = new SuperGameDAOAPI();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String login = request.getParameter("coderace");
//		int rang =  UtileKeno._checkExistingSameDisplayCoderace(login);
		
		try {
			BonusSet bns = this.supergameAPI.getSuperGameDAO().getbonuskeno(Params.url, login);
		//} catch (IOException | JSONException | URISyntaxException | DAOAPIException e) {
		//	e.printStackTrace();
		//}
		
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
	
     	JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();		
			// On construit le tableau
    //    System.out.println("bns "+bns.getBonusk()+" amount: "+bns.getMontant());
//			// On construit l'objet JSON pour l'expediteur
	           JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
//	     //      if(rang != -1){
	        	   objectBuilder.add("bonusk", bns.getBonusk());
	        	   objectBuilder.add("partner", bns.getCoderace());
	        	   objectBuilder.add("codebonus", bns.getCode());
	        	   objectBuilder.add("montantbonus",bns.getMontant());
	        	   objectBuilder.add("barcode", bns.getBarcode());
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
