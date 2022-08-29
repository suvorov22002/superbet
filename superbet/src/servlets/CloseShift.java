package servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modele.Airtime;
import modele.Caissier;
import superbetDAO.AirtimeDAO;
import superbetDAO.CaissierDAO;
import superbetDAO.DAOFactory;

public class CloseShift extends HttpServlet{

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	public static final String CONF_DAO_FACTORY = "daofactory";
	private AirtimeDAO airtimeDao;
	private CaissierDAO caissierDao;
	
	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO caissier */
		this.airtimeDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getAirtimeDao();
		this.caissierDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getCaissierDao();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String str1 = request.getParameter("balance");
		String str2 = request.getParameter("benefice");
		String login = request.getParameter("caissier");
		String coderace = request.getParameter("coderace");
		double balance,benefice;
		
		//System.out.println("P+C: "+coderace+" "+login);
		
		
		benefice = 0;
		balance = 0;
		benefice = Double.parseDouble(str2);
		balance = Double.parseDouble(str1);
		Caissier caissier = caissierDao.findByLoginIdPartner(login, coderace);
		if(caissier != null){
			airtimeDao.updateMvt(caissier.getIdCaissier(), (balance-benefice));
			Airtime airtime = new Airtime();
			airtime.setCaissier(caissier.getIdCaissier());
			airtime.setCredit(0);
			airtime.setDate(new Date());
			if(benefice < 0){
				airtime.setCredit(benefice); 
			}
			else{
				airtime.setDebit(benefice);
			}
			airtime.setBalance((balance-benefice));
			airtimeDao.create(airtime);
			
		}
		
		//System.out.println("Balance: "+(balance-benefice));
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");

//	     String json = new Gson().toJson(list);
//		 response.getWriter().write(json);
	      
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
