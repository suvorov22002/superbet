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
import superbetDAO.PartnerDAO;
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
	private PartnerDAO partnerDao;
	private Long token;
	private  ISuperGameDAOAPILocal  supergameAPI;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateBonus() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO caissier */
		this.kenoDao = ( (DAOFactory)getServletContext().getAttribute( CONF_DAO_FACTORY )).getKenoDao();
		this.partnerDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getPartnerDao();
		this.token = (Long)getServletContext().getAttribute("token");
		supergameAPI = SuperGameDAOAPI.getInstance();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		UtileKeno.gamestate = 1;
//		String coderace = request.getParameter("partner");
	//	Keno keno = kenoDao.find_Max_draw(coderace);
		
	//	System.out.println("SVG-TOKEN: "+coderace);
	//	Partner partner = partnerDao.find(coderace);
		
//		KenoRes b = new KenoRes();
//		 try {
//				int b = supergameAPI.getSuperGameDAO().getStates(Params.url, UtileKeno.gamestate, coderace);
//				System.out.println("Updatebonus gamestate: "+b);
//		 } catch (IOException | JSONException | URISyntaxException | DAOAPIException e) {
//				e.printStackTrace();
//		 }
//       
		
		 response.setContentType("application/json; charset=UTF-8");
		 response.setHeader("Cache-Control", "no-cache");
		
		 
		// On construit le tableau JSON des résultat
         	JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
         
        // On construit l'objet JSON pour l'expediteur
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            
            //    System.out.println("[UpdateBonus - GameState ] "+UtileKeno.gamestate+" - "+UtileKeno.timeKeno);
            	objectBuilder.add("gamestate", UtileKeno.gamestate);
                objectBuilder.add("drawnumbk", UtileKeno.drawKeno);
                objectBuilder.add("drawnumk", UtileKeno.drawnumk);
                objectBuilder.add("bonuskamount", UtileKeno.bonuskamount);
                objectBuilder.add("status", "ok");
                objectBuilder.add("token", this.token);
                if(UtileKeno.multiplicateur == null) UtileKeno.multiplicateur = "1";
                objectBuilder.add("multi", UtileKeno.multiplicateur);
                objectBuilder.add("combi", UtileKeno.str_draw_combi);
                objectBuilder.add("timekeno", UtileKeno.timeKeno);
           //     System.out.println("MANAGETIME KENO: "+UtileKeno.timeKeno);
            
            
            
            arrayBuilder.add(objectBuilder);
        
       
        // On renvois le résultat
        response.getWriter().write(arrayBuilder.build().toString());
	  
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int j,nbre = 0,_nbre = 0,l;
		String[] lastsK = null,lastB = null;
		String str = "";
		
		String coderace = request.getParameter("partner");
		Partner p = partnerDao.find(coderace);
		String[] last = kenoDao.getLastKdraw(coderace);
		String[] last_bonus = kenoDao.getLastKBonus(coderace);
		
		List<KenoRes> last_bns = new ArrayList<>();
		List<KenoRes> last_draw = new ArrayList<>();
		try {
			last_bns = this.supergameAPI.getSuperGameDAO().getbonus(Params.url, p.getCoderace());
			
			last_draw = this.supergameAPI.getSuperGameDAO().getLdraw(Params.url, p.getCoderace());
			
			   _nbre = last_bns.size();
			   lastB = new String[_nbre];
			   l = 0;
			   for (KenoRes ks : last_bns) {
				   str = "";
				   str = str + coderace + "_" + ks.getHeureTirage().replace('h',':') + "_" + ks.getBonuscod() + "_" + ks.getBonusKamount(); 
				   //System.out.println("new_draw updatebonus: "+str);
				   lastB[l++] = str;
			   }
			   
			   nbre = last_draw.size();
			   lastsK = new String[nbre];
			   l = 0;
			   //System.out.println("nbre "+nbre+" last: "+last.length);
			   Collections.reverse(last_draw);
			   for(KenoRes ks : last_draw){
				   str = "";
				   str = str + ks.getDrawnumK() + "_" + ks.getDrawnumbK() + "_" + ks.getMultiplicateur() + "_" + ks.getHeureTirage().replace('h',':').substring(11);
				   lastsK[l++] = str;
				   //System.out.println(str);
			   }
		} catch (JSONException | URISyntaxException | DAOAPIException e) {
			e.printStackTrace();
			_nbre = 0;
			nbre = 0;
			
			//mise a jour des courses precedentes   
			   nbre = (last.length-1)/Integer.parseInt(last[0]);
			   lastsK = new String[nbre];
			   j = last.length-1;
			   l = 0;
			  // System.out.println("nbre "+nbre+" last: "+last.length);
			   for(int i=0;i<nbre;i++){
				   str = "";
				   str = str + last[j--] + "_" + last[j--] + "_" + last[j--] + "_" + last[j--].substring(11);
				   lastsK[l++] = str;
				  // System.out.println(str);
			   }
			   
			   
			 //mise a jour des derniers bonus
				 // System.out.println("last_bonus: "+last_bonus.length);
				  
				   _nbre = (last_bonus.length-1)/Integer.parseInt(last_bonus[0]);
				   lastB = new String[nbre];
				   j = 1;
				   l = 0;
				   for(int i=0;i<_nbre;i++){
					   str = "";
					   str = str + last_bonus[j++] + "_" + last_bonus[j++] + "_" + last_bonus[j++] + "_" + last_bonus[j++];
					   lastB[l++] = str;
					 //  System.out.println(str);
				   }
			   
			   
		}
		//System.out.println("SV-TOKEN: "+this.token);
		
		
//		String new_draw = keno.getDrawnumbK();
//		System.out.println("new_draw updatebonus: "+new_draw);
//		if(!new_draw.equalsIgnoreCase("'1-2-3-4-5-6-7-8-9-10-11-12-13-14-15-16-17-18-19-20'") || !keno.getMultiplicateur().equalsIgnoreCase("0")){
//			System.out.println("Ajout au lancement updateBonus: "+last.length);
//			Keno kenos = new Keno();
//			int num = Integer.parseInt(keno.getDrawNumK());
//			
//		//	if(!keno.getMultiplicateur().equalsIgnoreCase("0")){
//		//		kenoDao.updateDrawEnd(num);
//		//	}
//			kenos.setDrawNumK(""+(1+num));
//			kenos.setCoderace(coderace);
//			kenoDao.create(kenos);
//		}
		
		//mise à jour de la fin du tirage précedent
	//	System.out.println("exist: "+maj_last.length+" started: "+maj_last[0]+" __ "+maj_last[1]);
//		if(maj_last != null && maj_last.length > 2)
//			if(maj_last[1].equalsIgnoreCase("0")){
//				kenoDao.updateDrawEnd(Integer.parseInt(maj_last[2]));
//			}
		
		   
		   
		/* Refresh refresh = new Refresh();
		 refresh.start();*/
		  
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
          //  objectBuilder.add("token", this.token);
            arrayBuilder.add(objectBuilder);
        
       
        // On renvois le résultat
        response.getWriter().write(arrayBuilder.build().toString());
		//doGet(request, response);
	}

}
