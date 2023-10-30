package servlets;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
import modele.KenoRes;
import modele.Partner;
import superbetDAO.DAOFactory;
import superbetDAO.KenoDAO;
import superbetDAO.api.exeception.DAOAPIException;
import superbetDAO.api.implementations.SuperGameDAOAPI;
import superbetDAO.api.interfaces.ISuperGameDAOAPILocal;

/**
 * Servlet implementation class UpdateBonus
 */

public class UpdateBonus extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	public static final String CONF_DAO_FACTORY = "daofactory";
	private KenoDAO kenoDao;
	private Long token;
	private  ISuperGameDAOAPILocal  supergameAPI;
    /**
     * @see HttpServlet#HttpServlet()
     */
	
    public UpdateBonus() {
        super();
    }
    
    public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO caissier */
		this.kenoDao = ( (DAOFactory)getServletContext().getAttribute( CONF_DAO_FACTORY )).getKenoDao();
		this.token = (Long)getServletContext().getAttribute("token");
		supergameAPI = SuperGameDAOAPI.getInstance();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      		
		 response.setContentType("application/json; charset=UTF-8");
		 response.setHeader("Cache-Control", "no-cache");
		
		 

	 	JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
	    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

    	objectBuilder.add("gamestate", UtileKeno.gamestate);
        objectBuilder.add("drawnumbk", UtileKeno.drawKeno);
        objectBuilder.add("drawnumk", UtileKeno.drawnumk);
        objectBuilder.add("bonuskamount", UtileKeno.bonuskamount);
        objectBuilder.add("status", "ok");
        objectBuilder.add("token", this.token);
        //System.out.println("SVG-TOKEN: " + this.token);
        
        if(UtileKeno.multiplicateur == null) UtileKeno.multiplicateur = String.valueOf(1);
        objectBuilder.add("multi", UtileKeno.multiplicateur);
        objectBuilder.add("combi", UtileKeno.str_draw_combi);
        objectBuilder.add("timekeno", UtileKeno.timeKeno);
        
        arrayBuilder.add(objectBuilder);
    
   
        // On renvois le résultat
        response.getWriter().write(arrayBuilder.build().toString());

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int j;
		int nbre = 0;
		int _nbre = 0;
		int l;
		String[] lastsK = null,lastB = null;
		StringBuilder str;
		
		String coderace = request.getParameter("partner");
		String[] last;
		String[] last_bonus;
		
		List<KenoRes> last_bns = new ArrayList<>();
		List<KenoRes> last_draw = new ArrayList<>();
		
		try {
			
			last_bns = this.supergameAPI.getSuperGameDAO().getbonus(Params.url, coderace);
			last_draw = this.supergameAPI.getSuperGameDAO().getLdraw(Params.url, coderace);

			_nbre = last_bns.size();
			lastB = new String[_nbre];
			
			l = 0;
			for (KenoRes ks : last_bns) {
				
				str  = new StringBuilder();
				str.append(String.join("_", coderace, ks.getHeureTirage().replace('h',':'), String.valueOf(ks.getBonuscod())
						, String.valueOf(ks.getBonusKamount())));
				lastB[l++] = str.toString();
				
			}

			nbre = last_draw.size();
			lastsK = new String[nbre];
			l = 0;
		
			Collections.reverse(last_draw);
			for(KenoRes ks : last_draw){
				
				str  = new StringBuilder();
				str.append(String.join("_", String.valueOf(ks.getDrawnumK()), ks.getDrawnumbK(), ks.getMultiplicateur()
						, ks.getHeureTirage().replace('h',':').substring(11)));
				lastsK[l++] = str.toString();
				
			}
			
		} catch (JSONException | URISyntaxException | DAOAPIException e) {
			
			e.printStackTrace();
			last = kenoDao.getLastKdraw(coderace);
			last_bonus = kenoDao.getLastKBonus(coderace);

			//mise a jour des courses precedentes   
			nbre = (last.length-1)/Integer.parseInt(last[0]);
			lastsK = new String[nbre];
			j = last.length-1;
			l = 0;

			for(int i=0;i<nbre;i++){

				str  = new StringBuilder();
				str.append(String.join("_", last[j--], last[j--], last[j--], last[j--].substring(11)));
				lastsK[l++] = str.toString();

			}

			_nbre = (last_bonus.length-1)/Integer.parseInt(last_bonus[0]);
			lastB = new String[nbre];
			j = 1;
			l = 0;
			
			for(int i=0;i<_nbre;i++){
				
				str  = new StringBuilder();
				str.append(String.join("_", last_bonus[j++], last_bonus[j++], last_bonus[j++], last_bonus[j++]));
				lastB[l++] = str.toString();
				
			}
			   
			   
		}
		
		  
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");


		// On construit le tableau JSON des résultat
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

		// On construit l'objet JSON pour l'expediteur
		JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

		//         objectBuilder.add("drawnumk", keno.getDrawnumK());
		switch(nbre){
		case 1:
			objectBuilder.add("draw1", lastsK[0]);
			break;
		case 2:
			objectBuilder.add("draw2", lastsK[1]);
			objectBuilder.add("draw1", lastsK[0]);
			break;
		case 3:
			objectBuilder.add("draw3", lastsK[2]);
			objectBuilder.add("draw2", lastsK[1]);
			objectBuilder.add("draw1", lastsK[0]);
			break;
		case 4:
			objectBuilder.add("draw4", lastsK[3]);
			objectBuilder.add("draw3", lastsK[2]);
			objectBuilder.add("draw2", lastsK[1]);
			objectBuilder.add("draw1", lastsK[0]);
			break;
		case 5:
			objectBuilder.add("draw5", lastsK[4]);
			objectBuilder.add("draw4", lastsK[3]);
			objectBuilder.add("draw3", lastsK[2]);
			objectBuilder.add("draw2", lastsK[1]);
			objectBuilder.add("draw1", lastsK[0]);
			break;
		default:

			break;
		}

		switch(_nbre){
		case 1:
			objectBuilder.add("bonus1", lastB[0]);
			break;
		case 2:
			objectBuilder.add("bonus2", lastB[1]);
			objectBuilder.add("bonus1", lastB[0]);
			break;
		case 3:
			objectBuilder.add("bonus3", lastB[2]);
			objectBuilder.add("bonus2", lastB[1]);
			objectBuilder.add("bonus1", lastB[0]);
			break;
		default:
			break;
		}

		objectBuilder.add("tot_tirage", nbre);
		objectBuilder.add("tot_bonus", _nbre);
		arrayBuilder.add(objectBuilder);

		// On renvois le résultat
		response.getWriter().write(arrayBuilder.build().toString());
	}

}
