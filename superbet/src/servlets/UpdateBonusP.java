package servlets;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modele.Partner;
import modele.Spin;
import superbetDAO.DAOFactory;
import superbetDAO.PartnerDAO;
import superbetDAO.SpinDAO;

public class UpdateBonusP extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3040074919511662641L;
	public static final String CONF_DAO_FACTORY = "daofactory";
	private SpinDAO spinDao;
	private PartnerDAO partnerDao;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateBonusP() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO caissier */
		this.spinDao = ( (DAOFactory)getServletContext().getAttribute( CONF_DAO_FACTORY )).getSpinDao();
		this.partnerDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getPartnerDao();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//double  bonus = kenoDao.findBonusAmount();
		String coderace = request.getParameter("partner");
		Spin spin = spinDao.find_Max_draw(coderace);
		
		
	//	System.out.println(" num: "+spin.getDrawNumP());
		Partner partner = partnerDao.find(coderace);
		
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
	
		// On construit le tableau JSON des résultat
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
         
        // On construit l'objet JSON pour l'expediteur
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
       // System.out.println(" bonus: "+partner.getBonusPamount());
           
            	objectBuilder.add("idspin", spin.getIdspin());
                objectBuilder.add("drawnumbp", spin.getDrawnumbP());
                objectBuilder.add("drawnump", spin.getDrawNumP());
                objectBuilder.add("bonuspamount", partner.getBonusPamount());
                objectBuilder.add("status", "ok");
            	
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
		String coderace = request.getParameter("partner");
		Spin spin = spinDao.find_Max_draw(coderace);
		String[] maj_last = spinDao.find_Max_draw_bis(coderace);
		String[] last = spinDao.getLastPdraw(coderace);
		
		String new_draw = spin.getDrawnumbP();
		System.out.println("new_draw updatebonus: "+new_draw);
		if(!new_draw.equalsIgnoreCase("'0'")){
			System.out.println("Ajout au lancement updateBonus: "+last.length);
			Spin spins = new Spin();
			int num = Integer.parseInt(spin.getDrawNumP());
			System.out.println("Nu, spin update: "+num);
			spins.setDrawNumP(""+(1+num));
			spins.setCoderace(coderace);
		    spinDao.create(spins);
		}
		
		//mise à jour de la fin du tirage précedent
		//	System.out.println("exist: "+maj_last.length+" started: "+maj_last[0]+" __ "+maj_last[1]);
		if(maj_last != null && maj_last.length > 2)
			if(maj_last[1].equalsIgnoreCase("0")){
				spinDao.updateDrawEnd(Integer.parseInt(maj_last[2]));
			}
		
		 response.setContentType("application/json; charset=UTF-8");
		 response.setHeader("Cache-Control", "no-cache");
		
		 
		// On construit le tableau JSON des résultat
         	JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
         
        // On construit l'objet JSON pour l'expediteur
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
           // JsonValue _chknbre = Json..createReader(chk_nbre);
            objectBuilder.add("drawnump", spin.getDrawNumP());
   
            arrayBuilder.add(objectBuilder);
        
       
        // On renvois le résultat
        response.getWriter().write(arrayBuilder.build().toString());
		//doGet(request, response);
	}

}
