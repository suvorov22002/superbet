package servlets;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import org.codehaus.jettison.json.JSONException;

import config.Params;
import modele.Caissier;
import superbetDAO.api.exeception.DAOAPIException;
import superbetDAO.api.implementations.SuperGameDAOAPI;
import superbetDAO.api.interfaces.ISuperGameDAOAPILocal;

/**
 * Servlet implementation class SuperAdmin
 */
@WebServlet("/superAdmin")
public class SuperAdmin extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private  ISuperGameDAOAPILocal  supergameAPI;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SuperAdmin() {
        super();
        supergameAPI = SuperGameDAOAPI.getInstance();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Caissier> list = new ArrayList<Caissier>();
		try {
			list = this.supergameAPI.getSuperGameDAO().superAdmin(Params.url);
		} catch (IOException | JSONException | URISyntaxException | DAOAPIException e) {
			e.printStackTrace();
		}
		
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");

	    String json = new Gson().toJson(list);
		response.getWriter().write(json);
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
