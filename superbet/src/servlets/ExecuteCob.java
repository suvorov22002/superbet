package servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import config.Timestamp;
import modele.Airtime;
import modele.Caissier;
import modele.Partner;
import superbetDAO.AirtimeDAO;
import superbetDAO.CaissierDAO;
import superbetDAO.DAOFactory;
import superbetDAO.KenoDAO;
import superbetDAO.MisekDAO;
import superbetDAO.PartnerDAO;
import superbetDAO.VersementDAO;

public class ExecuteCob extends HttpServlet {

public static final String CONF_DAO_FACTORY = "daofactory";
	
	private KenoDAO kenoDao;
	private CaissierDAO caissierDao;
	private PartnerDAO partnerDao;
	private AirtimeDAO airtimeDao;
	private MisekDAO misekDao;
	private VersementDAO versementDao;
	public void init() throws ServletException {
		
			this.kenoDao = ( (DAOFactory)getServletContext().getAttribute( CONF_DAO_FACTORY )).getKenoDao();
			this.misekDao = ( (DAOFactory)getServletContext().getAttribute( CONF_DAO_FACTORY )).getMisekDao();
			this.airtimeDao = ( (DAOFactory)getServletContext().getAttribute( CONF_DAO_FACTORY )).getAirtimeDao();
			this.caissierDao = ( (DAOFactory)getServletContext().getAttribute( CONF_DAO_FACTORY )).getCaissierDao();
			this.partnerDao = ( (DAOFactory)getServletContext().getAttribute( CONF_DAO_FACTORY )).getPartnerDao();
			this.versementDao = ( (DAOFactory)getServletContext().getAttribute( CONF_DAO_FACTORY )).getVersementDao();
	}
	
	public ExecuteCob() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String idpartner = request.getParameter("coderace");
		System.out.println("Partner BIZ"+action);
		System.out.println("EXEC.COB "+idpartner);
		
		ArrayList<Partner> listPartners = partnerDao.getAllPartners();
		if(idpartner.equalsIgnoreCase("1")) {
			listPartners = partnerDao.getAllPartners();
		}
		else {
			listPartners = partnerDao.getAllPartnersByGroup(idpartner);
		}
		
		if("OPENBIZ".equals(action)) {
			process_open(listPartners);
		}
		else if("CLOSEBIZ".equals(action)) {
			System.out.println("Partner CLOSEBIZ");
			process_close(listPartners);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void process_open(ArrayList<Partner> listPartners) {
		
		try {
			
			for(Partner partn : listPartners) {
				if("closed".equalsIgnoreCase(partn.getCob())) {
				  partnerDao.update_cob("opened",partn.getCoderace());
			 }
				  
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void process_close(ArrayList<Partner> listPartners) {
		//ArrayList<Partner> listPartners = partnerDao.getAllPartners();
		String txtDate=new SimpleDateFormat("dd/MM/yyyy,H:m:s", Locale.FRANCE).format(new Date());
		String fecha = txtDate.toString().substring(0,10);
		long t1;
		long t2;
		double balance = 0;
		System.out.println("Partner COB: "+listPartners.size());
		try {
			t1 = Timestamp.givetimestamp(fecha+",00:00:00");
			t2 = Timestamp.givetimestamp(fecha+",23:59:00");
			
			for(Partner partn : listPartners) {
				if("opened".equalsIgnoreCase(partn.getCob())) {
				  partnerDao.update_cob("closed",partn.getCoderace());
				//recherche des caissiers par partenaires
				ArrayList<Caissier> list_cais = caissierDao.findByPartner(partn.getCoderace());
				System.out.println("list_cais COB: "+list_cais.size());
				  for(Caissier cais : list_cais) {
					  balance = 0;
					  if(cais.getProfil() != 1) {
						  Airtime airtime = airtimeDao.find(cais);
							
							
							/** � ajouter balance autres jeux**/
							
							if(airtime != null) {
							//	if(!"VA".equalsIgnoreCase(airtime.getEta())) {
								double in = misekDao.getMiseRK(""+cais.getIdCaissier(), ""+t1, ""+t2);
								double out = versementDao.getVersementD(""+t1, ""+cais.getIdCaissier(), ""+t2);
								if(in != 0)
								     misekDao.updateMiseRK(""+cais.getIdCaissier(), ""+t1, ""+t2);
								if(out != 0)
								     versementDao.updateVersementD(""+t1, ""+cais.getIdCaissier(), ""+t2);
								balance = airtime.getBalance() - in + out;
								balance = (double)((int)(balance*100))/100;
								System.out.println("new balance "+balance+" in: "+in+" out: "+out);
								airtimeDao.updateMvt(cais.getIdCaissier(), balance);
								Airtime airti = new Airtime();
								airti.setCredit(out);
								
								airti.setDate(new Date());
								airti.setDebit(in);
								airti.setCaissier(cais.getIdCaissier());
								airti.setBalance(balance);
								airti.setLibelle("MAJ CAISSE");
								airti.setEta("VA");
								airtimeDao.create(airti);
							//	}
							}
					  }
						  
				  }
				  System.out.println("FIN DU COB");
			 }
				  
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
