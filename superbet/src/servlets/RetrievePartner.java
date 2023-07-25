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

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONException;

import config.Params;
import modele.Partner;
import superbetDAO.DAOFactory;
import superbetDAO.PartnerDAO;
import superbetDAO.api.exeception.DAOAPIException;
import superbetDAO.api.implementations.SuperGameDAOAPI;
import superbetDAO.api.interfaces.ISuperGameDAOAPILocal;

public class RetrievePartner extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String CONF_DAO_FACTORY = "daofactory";
	private PartnerDAO partnerDao;
	private  ISuperGameDAOAPILocal  supergameAPI;
	
	
	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO caissier */
		this.partnerDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getPartnerDao();
		supergameAPI = SuperGameDAOAPI.getInstance();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String idpartner = request.getParameter("coderace");
		String loginCaissier = request.getParameter("loginc");
		System.out.println("loginCaissier: "+loginCaissier);
		List<String> list = new ArrayList<String>();
		List<Partner> listePartnaires = new ArrayList<>();
		
		try {
			if (!StringUtils.isBlank(loginCaissier)) {
				
				if (StringUtils.endsWith(loginCaissier, ".admin")) {
					
						listePartnaires =  supergameAPI.getSuperGameDAO().getAllPartner(Params.url);
					
				}
			}
			
	//		last = partnerDao.getAllPartners();
	
			for(Partner partn : listePartnaires){
				list.add(partn.getCoderace());
		    }
		
		} catch (IOException | JSONException | URISyntaxException | DAOAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");

	     String json = new Gson().toJson(list);
		 response.getWriter().write(json);
	      
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
