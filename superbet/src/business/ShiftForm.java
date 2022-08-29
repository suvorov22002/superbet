package business;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jettison.json.JSONException;

import config.Params;
import config.Timestamp;
import modele.Airtime;
import modele.Caissier;
import modele.ShiftDay;
import superbetDAO.AirtimeDAO;
import superbetDAO.CaissierDAO;
import superbetDAO.MisekDAO;
import superbetDAO.MisepDAO;
import superbetDAO.UtilDAO;
import superbetDAO.VersementDAO;
import superbetDAO.api.exeception.DAOAPIException;
import superbetDAO.api.implementations.SuperGameDAOAPI;
import superbetDAO.api.interfaces.ISuperGameDAOAPILocal;

public final class ShiftForm {
	
	private Map<String, String> shift = new HashMap<String, String>();
	private String resultats;
	private  String imprimer = null;
	private static final String FIELD_NAME = "hiddenday";
	
	private MisekDAO misekDao;
	private MisepDAO misepDao;
	private VersementDAO verstDao;
	private CaissierDAO caissierDao;
	private String endday;
	private AirtimeDAO airtimeDao;
	private  ISuperGameDAOAPILocal  supergameAPI;
	
	public ShiftForm(UtilDAO utilDao, MisekDAO misekDao,  MisepDAO misepDao, VersementDAO verstDao, CaissierDAO caissierDao, AirtimeDAO airtimeDao){
	
		this.misekDao = misekDao;
		this.misepDao = misepDao;
		this.verstDao = verstDao;
		this.caissierDao = caissierDao;
		this.airtimeDao = airtimeDao;
		supergameAPI = new SuperGameDAOAPI();
	}

	public Map<String, String> getShift() {
		return shift;
	}

	public void setShift(String champ, String message) {
		this.shift.put(champ, message);
	}
		
	public String getResultats() {
		return resultats;
	}
	
	public  String getImprimer() {
		return imprimer;
	}
	
	public String getEndday() {
		return endday;
	}

	public void setEndday(String endday) {
		this.endday = endday;
	}

	public void setResultats(String resultats) {
		this.resultats = resultats;
	}

	public Caissier endshift(HttpServletRequest request){
		
		HttpSession session = request.getSession();
	    if(session != null){
	    	Caissier caissier = new Caissier();
	    	caissier = (Caissier)session.getAttribute("caissier");
	    	if(caissier != null){
	    		String end = getValeurChamp(request, FIELD_NAME);
	    		//System.out.println("end: "+end);
	    		if(end !=null && end.equalsIgnoreCase("endofday")){
	    			System.out.println("CAISSIER1: "+caissier.getLoginc());
	    			caissier.setStatut("N");
			    //	caissierDao.updateState(caissier);
			    	endday = "";
	    		}
	    		else{
	    			String state,  mIn1, mIn2,mIn3,mIn4, mOut, bonusCumul;
	    			int misetD1,misetB1,misetK1,misetP1,miset1,vers;
	    			int totalBillet = 0, tTvers = 0;
	    			double mtIn = 0, mtOut = 0, balance = 0, bonusCumuls = 0, balance0 = 0, reste=0;
	    			double sum_entrG,sum_versG,sum_entrB,sum_entrK,sum_entrP,sum_diff;
	    			
	    			sum_entrG = 0.0;
	    		    sum_versG = 0.0;
	    		    sum_entrB = 0.0;
	    		    sum_entrK = 0.0;
	    		    sum_entrP = 0.0;
	    			
	    			String txtDate=new SimpleDateFormat("dd/MM/yyyy,H:m:s", Locale.FRANCE).format(new Date());
	    			setShift("time", txtDate);
	    			setShift("caissier", caissier.getLoginc());
	    			
	    			String fecha = txtDate.toString().substring(0,10);
	    			long t1,t2;
	    		    try {
	    				t1 = Timestamp.givetimestamp(fecha+",00:00:00");
	    				t2 = Timestamp.givetimestamp(fecha+",23:59:00");
	    				
	    				//recuperation de lobjet shift
	    				ShiftDay shiftday;
	    				shiftday =  supergameAPI.getSuperGameDAO().shift(Params.url, caissier.getIdCaissier(), ""+t1, ""+t2);	
	    				
	    				
	    				//System.out.println(t1+"\n"+t2);
	    				sum_entrK = shiftday.getCashink(); // somme totale de keno joué
	    				//setShift("cashin", ""+sum_entrK);
	    				sum_entrP = shiftday.getCashinp(); // somme totale de spin joué
	    				setShift("cashin", ""+(sum_entrK+sum_entrP));
	    				
	    				sum_versG = shiftday.getCashoutk() + shiftday.getCashoutp(); //total payé
	    				setShift("cashout", ""+sum_versG);
	    				System.out.println("cashout: "+sum_versG+" id: "+caissier.getIdCaissier()+" t1: "+t1+" t2: "+t2);
	    				
	    				misetK1 = shiftday.getSlipk(); //nombre de ticket de keno joués	
	    				misetP1 = shiftday.getSlipp(); //nombre de ticket de spin joués	
	    				//System.out.println("tick_tot: "+(misetK1+misetP1));
	    				setShift("tick_tot", ""+(misetK1+misetP1));
	    				
	    				vers = shiftday.getVslipk() + shiftday.getVslipp() + shiftday.getVslipb() + shiftday.getVslipd() + shiftday.getVslipf(); //nombre total de ticket payé
	    				setShift("paid_tot", ""+vers);
	    				
	    				setShift("revoq", "0");
	    				setShift("revoq_tot", "0");
	    				setShift("start", Timestamp.givedate(t1));
	    				
	    				mtIn = sum_entrK + sum_entrB + sum_entrG + sum_entrP;
	    				mtOut = sum_versG;
	    				
	    				balance = mtIn - mtOut;
	    				setShift("balance", ""+balance);
	    				endday = "";
	    				
	    			
	    				Airtime airtme = airtimeDao.find(caissier);
	    				
	    				balance0 = shiftday.getBalancein();
	    				setShift("balance0", ""+balance0);
	    				setShift("reste", ""+(balance0 - balance));
	    				imprimer = "imprimer";
	    				return caissier;
	    			} catch (ParseException | IOException | JSONException | URISyntaxException | DAOAPIException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			}
	    		}
	    	}
	    	else{
	    		return caissier;
	    	}
	    }
	
	    return null;
	}
	
	private static String getValeurChamp( HttpServletRequest request,String nomChamp ) {
       String valeur = request.getParameter( nomChamp );
       if ( valeur == null || valeur.trim().length() == 0 ) {
           return null;
       } else{
           return valeur.trim();
       }
    }  
}
