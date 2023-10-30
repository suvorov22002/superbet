package servlets;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONException;

import config.Params;
import config.UtileKeno;
import superbetDAO.api.exeception.DAOAPIException;
import superbetDAO.api.implementations.SuperGameDAOAPI;
import superbetDAO.api.interfaces.ISuperGameDAOAPILocal;

public class GetState extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory.getLog(GetState.class);
	
	private  ISuperGameDAOAPILocal  supergameAPI;
	
	public GetState() {
		super();
	}
	
	public void init() throws ServletException {
		supergameAPI = SuperGameDAOAPI.getInstance();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int state = Integer.parseInt(request.getParameter("state"));
		String coderace = request.getParameter("coderace");
	
		 try {
			 
			 UtileKeno.gamestate = supergameAPI.getSuperGameDAO().getStates(Params.url, state, coderace);
			 logger.info("GetState UtileKeno.gamestate: " + UtileKeno.gamestate);
				
		 } catch (IOException | JSONException | URISyntaxException | DAOAPIException e) {
				e.printStackTrace();
		 }
       
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}
	

}
