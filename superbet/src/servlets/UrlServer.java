package servlets;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import config.Params;
import config.UtileKeno;

public class UrlServer extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String CONF_DAO_FACTORY = "daofactory";

	public UrlServer() {
		super();
	}
	
	public void init() throws ServletException {
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 response.setContentType("application/json; charset=UTF-8");
		 response.setHeader("Cache-Control", "no-cache");
		
		// On construit le tableau JSON des résultat
         	JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
         
        // On construit l'objet JSON pour l'expediteur
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
           
            objectBuilder.add("server", Params.url);

            arrayBuilder.add(objectBuilder);
        
//	       // On renvois le résultat
	    response.getWriter().write(arrayBuilder.build().toString());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}
}
