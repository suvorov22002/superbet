package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.gson.Gson;

import config.UtileKeno;
import modele.Keno;
import superbetDAO.DAOFactory;
import superbetDAO.KenoDAO;
import superbetDAO.PartnerDAO;

/**
 * Servlet implementation class Retrkieve
 */
//@WebServlet("/sumodd")
public class RetrieveSumOdd extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String CONF_DAO_FACTORY = "daofactory";
	private KenoDAO kenoDao;
    
	public void init() throws ServletException {
		this.kenoDao = ( (DAOFactory)getServletContext().getAttribute( CONF_DAO_FACTORY )).getKenoDao();
	}
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public RetrieveSumOdd() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String coderace = request.getParameter("partner");
		System.out.println("Retrieve Odd: "+coderace);
		List<Keno> keno = kenoDao.getAllLastKdraw(coderace);
		System.out.println("keno size: "+keno.size());
		countAllNumOdds(keno);
		System.out.println("Sood size: "+UtileKeno.sOdd.size());
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
	//	System.out.println("Soods: "+UtileKeno.sOdd);
		
	//	JSONObject j = new JSONObject(UtileKeno.sOdd);
	//	j.putAll(UtileKeno.sOdd);
	//	 System.out.println("Sood: "+j);
	    String json = new Gson().toJson(UtileKeno.sOdd);
	    System.out.println("Sood: "+json);
	//    response.getWriter().write(json);
	//    
	//	response.getWriter().write(j.toString());
		
	    
	    
		
		
		
		   JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();		
//		
           JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
           objectBuilder.add("sodd", json);
           arrayBuilder.add(objectBuilder);
           response.getWriter().write(arrayBuilder.build().toString());
		
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void countAllNumOdds(List<Keno> allDraws) {
//		
		List<String> allDraw = new ArrayList<String>();
		Map<String, String> allDrawNumOdds = new HashMap<String, String>();
//		Utile.allDraw.clear();
//		Utile.allDrawNumOdds.clear();
		
		
		for(Keno k : allDraws) {
			allDraw.add(k.getDrawnumbK());
		}
		
		// Initialisation des coefficients
		for(int i = 0; i < 80; i++) {
			allDrawNumOdds.put(""+(i+1), ""+0);
		}
		
//		if(allDraw.size() > 0) {
			String[] passDraw;
			for(String ss : allDraw) {
				passDraw  = ss.split("-");
				for(int j=0; j<passDraw.length; j++) {
					String key = passDraw[j];
					String value = allDrawNumOdds.get(key);
					try {
						allDrawNumOdds.put(key, ""+(Integer.parseInt(value) + 1));
					}
					catch(NumberFormatException e) {
						e.printStackTrace();
						allDrawNumOdds.put(key,   "1");
					}
					
					
				}
			}
			
		Map<String, Integer> m = triAvecValeur( allDrawNumOdds );
		System.out.println("Apres: "+m);
		UtileKeno.sOdd.clear();
		UtileKeno.sOdd = m;
//		  Iterator it = m.entrySet().iterator();
//	      while(it.hasNext()) {
//	           Map.Entry entry = (Map.Entry)it.next();
//	           UtileKeno.sOdd.put((String)entry.getKey(), ""+entry.getValue());
//	        //   System.out.println(entry.getKey() + ": "+entry.getValue());
//	      }
			
//		}
		
		
		
	}
	
  private HashMap<String, Integer> triAvecValeur( Map<String, String> m ){
		
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		
	      Iterator it = m.entrySet().iterator();
	      while(it.hasNext()) {
	           Map.Entry entry = (Map.Entry)it.next();
	           map.put((String)entry.getKey(), Integer.parseInt((String)entry.getValue()));
	        //   System.out.println(entry.getKey() + ": "+entry.getValue());
	      }
	
		
	   List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>( map.entrySet() );
	   Collections.sort( list, new Comparator<Map.Entry<String, Integer>>(){
	      public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 ){
	          return (o2.getValue()).compareTo( o1.getValue());
	      }
	   });

	   HashMap<String, Integer> map_apres = new LinkedHashMap<String, Integer>();
	   for(Map.Entry<String, Integer> entry : list)
	     map_apres.put( entry.getKey(), entry.getValue() );
	   return map_apres;
	}

}
