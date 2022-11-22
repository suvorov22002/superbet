package servlets;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONException;

import config.Params;
import config.UtileKeno;
import superbetDAO.DAOFactory;
import superbetDAO.api.exeception.DAOAPIException;
import superbetDAO.api.implementations.SuperGameDAOAPI;
import superbetDAO.api.interfaces.ISuperGameDAOAPILocal;

public class GetState extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private  ISuperGameDAOAPILocal  supergameAPI;
	
	public GetState() {
		super();
	}
	
	public void init() throws ServletException {
		supergameAPI = SuperGameDAOAPI.getInstance();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UtileKeno.gamestate = Integer.parseInt(request.getParameter("state"));
		String coderace = request.getParameter("coderace");
	
		 try {
				int b = supergameAPI.getSuperGameDAO().getStates(Params.url, UtileKeno.gamestate, coderace);
				//System.out.println("Updatebonus gamestate: "+b);
		 } catch (IOException | JSONException | URISyntaxException | DAOAPIException e) {
				e.printStackTrace();
		 }
       
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}
	

}
