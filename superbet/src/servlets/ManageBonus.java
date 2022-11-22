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

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONException;

import config.Params;
import modele.BonusSet;
import superbetDAO.DAOFactory;
import superbetDAO.KenoDAO;
import superbetDAO.api.exeception.DAOAPIException;
import superbetDAO.api.implementations.SuperGameDAOAPI;
import superbetDAO.api.interfaces.ISuperGameDAOAPILocal;

public class ManageBonus extends HttpServlet{
	
	private  ISuperGameDAOAPILocal  supergameAPI;
	private KenoDAO kenoDao;
	public static final String CONF_DAO_FACTORY = "daofactory";
	
	public void init() throws ServletException {
		supergameAPI = SuperGameDAOAPI.getInstance();
		this.kenoDao = ( (DAOFactory)getServletContext().getAttribute( CONF_DAO_FACTORY )).getKenoDao();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String login = request.getParameter("coderace");
		int idks;
		
		try {
			BonusSet bns = this.supergameAPI.getSuperGameDAO().getbonuskeno(Params.url, login);
			
			if (bns.getBonusk() != 0 && !StringUtils.equalsIgnoreCase(bns.getBarcode(), "0")) {
				idks = kenoDao.getIdKenos(bns.getCoderace(), bns.getNumk());
				if (idks != 0 ) {
					kenoDao.setCodeBonusK(bns.getMontant(), bns.getCode(), ""+idks);
				}
			}
		
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
