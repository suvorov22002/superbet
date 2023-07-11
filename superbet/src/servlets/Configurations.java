package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import business.ConfigForm;
import superbetDAO.AirtimeDAO;
import superbetDAO.CaissierDAO;
import superbetDAO.ConfigDAO;
import superbetDAO.DAOFactory;
import superbetDAO.EffChoicekDAO;
import superbetDAO.GameCycleDAO;
import superbetDAO.KenoDAO;
import superbetDAO.MisekDAO;
import superbetDAO.Misek_tempDAO;
import superbetDAO.MisetDAO;
import superbetDAO.PartnerDAO;
import superbetDAO.SpinDAO;
import superbetDAO.UtilDAO;
import superbetDAO.VersementDAO;

/**
 * Servlet implementation class Configurations
 */

public class Configurations extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 844821184634011638L;
	public static final String CONF_DAO_FACTORY = "daofactory";
	
	public static final String ATT_FORM = "con_form";
	public static final String VUE_ADMIN = "/WEB-INF/admin.jsp";
	
	private KenoDAO kenoDao;
	private MisetDAO misetDao;
	private EffChoicekDAO effchoicekDao;
	private UtilDAO utilDao;
	private MisekDAO misekDao;
	private Misek_tempDAO misektpDao;
	private PartnerDAO partnerDao;
	private VersementDAO verstDao;
	private CaissierDAO caissierDao;
	private ConfigDAO configDao;
	private SpinDAO spinDao;
	private AirtimeDAO airtimeDao;
	private GameCycleDAO gamecycleDao;
	
	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO caissier */
		this.kenoDao = ( (DAOFactory)getServletContext().getAttribute( CONF_DAO_FACTORY )).getKenoDao();
		this.misetDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getMisetDao();
		this.effchoicekDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getEffChoicekDao();
		this.utilDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getUtilDao();
		this.misekDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getMisekDao();
		this.misektpDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getMisektpDao();
		this.partnerDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getPartnerDao();
		this.verstDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getVersementDao();
		this.caissierDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getCaissierDao();
		this.configDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getConfigDao();
		this.spinDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getSpinDao();
		this.airtimeDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getAirtimeDao();
		this.gamecycleDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getGameCycleDao();
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Configurations() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.getServletContext().getRequestDispatcher(VUE_ADMIN).forward(request, response);
	}

	/**
	 * @throws IOException 
	 * @throws ServletException 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
			ConfigForm con_form = new ConfigForm(kenoDao,misetDao,utilDao,effchoicekDao,misekDao,
				verstDao, partnerDao, misektpDao, caissierDao, configDao, spinDao, airtimeDao, gamecycleDao);
		
		
	
			con_form.manage_config(request);
			if(con_form.getAction().equalsIgnoreCase("addpartner")){ //ajout de partenaire
				request.setAttribute("action", 1);
				
				request.setAttribute("state", 6);
				
			}
			else if(con_form.getAction().equalsIgnoreCase("addcaissier")){
				request.setAttribute("action", 3);
				
				request.setAttribute("state", 6);
			}
			else if(con_form.getAction().equalsIgnoreCase("addbonus")){
				request.setAttribute("action", 5);
				
				request.setAttribute("state", 6);
			}
			else if(con_form.getAction().equalsIgnoreCase("addcagnotte")){
				request.setAttribute("action", 7);
				
				request.setAttribute("state", 6);
			}
			
			request.setAttribute( ATT_FORM, con_form );
		
		this.getServletContext().getRequestDispatcher(VUE_ADMIN).forward(request, response);
	}

}
