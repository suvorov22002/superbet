package servlets;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import config.UtileSpin;

public class CheckEndP extends HttpServlet{
	
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String login = request.getParameter("coderace");
		int rang =  UtileSpin._checkExistingSameDisplayCoderace(login);
		
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
	
     	JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();		
			// On construit le tableau
        
			// On construit l'objet JSON pour l'expediteur
	           JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
	           if(rang != -1){
	        	   objectBuilder.add("enddrawp", UtileSpin._display_draw.get(rang).getEnd());
	        	  // System.out.println("ENDDRAWP CHECKP : "+UtileSpin._display_draw.get(rang).getEnd());
	           }
	           else{
	        	   objectBuilder.add("enddrawp", 1);
	           }
	           
	           arrayBuilder.add(objectBuilder);
	
	       // On renvois le résultat
	       response.getWriter().write(arrayBuilder.build().toString());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
