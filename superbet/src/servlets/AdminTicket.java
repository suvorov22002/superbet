package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import superbetDAO.DAOFactory;
import superbetDAO.EffChoicekDAO;
import superbetDAO.KenoDAO;
import superbetDAO.MisekDAO;
import superbetDAO.Misek_tempDAO;
import superbetDAO.MisetDAO;
import superbetDAO.PartnerDAO;
import superbetDAO.UtilDAO;
import superbetDAO.VersementDAO;
import business.TicketForm;

public class AdminTicket extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5051301784417241801L;
	public static final String VUE = "/WEB-INF/admin.jsp";
	public static final String ATT_FORM = "ticket_form";
	public static final String CONF_DAO_FACTORY = "daofactory";
	private KenoDAO kenoDao;
	private MisetDAO misetDao;
	private EffChoicekDAO effchoicekDao;
	private UtilDAO utilDao;
	private MisekDAO misekDao;
	private Misek_tempDAO misektpDao;
	private PartnerDAO partnerDao;
	private VersementDAO verstDao;
	
	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO caissier */
		this.misekDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getMisekDao();	
		this.partnerDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getPartnerDao();	
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		TicketForm ticket_form = new TicketForm(misekDao, partnerDao);
		
		ticket_form.manage_admin(request);
		
		if (ticket_form.getMisekt() != null)
		System.out.println("TICKETFORM: "+ticket_form.getMisekt().size());
		
		request.setAttribute( ATT_FORM, ticket_form );
		request.setAttribute("state", 4);
		
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

}
