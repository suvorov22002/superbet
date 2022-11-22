package servlets;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONException;

import config.Params;
import config.UtileKeno;
import modele.KenoRes;
import superbetDAO.DAOFactory;
import superbetDAO.KenoDAO;
import superbetDAO.api.exeception.DAOAPIException;
import superbetDAO.api.implementations.SuperGameDAOAPI;
import superbetDAO.api.interfaces.ISuperGameDAOAPILocal;

/**
 * Servlet implementation class GenerateDraw
 */
@WebServlet(description = "generateur de combinaison")
public class GenerateDraw extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String CONF_DAO_FACTORY = "daofactory";
	private KenoDAO kenoDao;
	private  ISuperGameDAOAPILocal  supergameAPI;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GenerateDraw() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO caissier */
		this.kenoDao = ( (DAOFactory)getServletContext().getAttribute( CONF_DAO_FACTORY )).getKenoDao();
		supergameAPI = SuperGameDAOAPI.getInstance();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 
		 response.setContentType("application/json; charset=UTF-8");
		 response.setHeader("Cache-Control", "no-cache");
		
		 
		// On construit le tableau JSON des résultat
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        
      //  System.out.println("Generate_draw drawnumbk: "+UtileKeno.drawKeno);
       // On construit l'objet JSON pour l'expediteur
           JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
           objectBuilder.add("drawnumbk", UtileKeno.drawKeno);
           objectBuilder.add("multi", UtileKeno.multiplicateur);
           objectBuilder.add("drawnumk", UtileKeno.drawnumk);
           objectBuilder.add("gamestate", UtileKeno.gamestate);
           objectBuilder.add("combi", UtileKeno.str_draw_combi);
          
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
		String drawnum = request.getParameter("drawnum");
		String state = request.getParameter("state");
		String drawnumbk = request.getParameter("drawnumbk");
		String multiplix = request.getParameter("multiplix");
		int line = 0;
		int num_tirage = Integer.parseInt(drawnum);
		//System.out.println("DRAWNUM: "+drawnumbk+" numero: "+num_tirage);
		
		
		/*	response.setContentType("application/json; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		
		// On construit le tableau JSON des résultat
     	JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
     
    // On construit l'objet JSON pour l'expediteur
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        objectBuilder.add("line", line);
        
        arrayBuilder.add(objectBuilder);*/
    
   
	    // On renvois le résultat
	    //response.getWriter().write(arrayBuilder.build().toString());
	    response.getWriter().write(line);
		
		//doGet(request, response);
	}
	
  /*  public String buscarDraw(){
		
		ArrayList<String> combis = new ArrayList<String>();
    	ArrayList<String> combi = new ArrayList<String>();

	    	for(int i=1;i<81;i++)
	    		combis.add(""+i);
	    	for(int j=0;j<80;j++){
	    		int index = generate(combis.size());
	    		combi.add(combis.get(index));
	    		combis.remove(index);
	    	}
	    	
	    	String str = "";
	    	for(int k=0;k<combi.size()-1;k++){
	    		str = str + combi.get(k) + "-";
	    	}
	    	str = str + combi.get(combi.size()-1);
	    	
	    	
		return str;
	}
    
    public static Keno buscarDraw(double cIn, double cOut, double percent,
			ArrayList<EffChoicek> listTicketK) {
    	
    	return null;
    }
	
	 private int generate(int n){
			int lower = 0;
			int higher = n;
			int dbl;
			  
			dbl = (int) (Math.random() * (higher-lower)) + lower;
			return dbl;
	}
	 
	 private int addKenos(int draw_num) {
			int add = 0;
			Keno keno = new Keno();
			keno.setDrawNumK(""+(1+draw_num));
	        //Création d'une requête paramétrée. 
			add = kenoDao.create(keno);
			return add;
	}
	 
	private int addUpKeno(Keno keno){
		int add = 0;
		add = kenoDao.update(keno);
		return add;
	}
	
	private int endDraw(int draw_num){
		int num = draw_num-1;
		return kenoDao.updateDrawEnd(draw_num);
	}
	*/
}
