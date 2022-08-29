package business;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jettison.json.JSONException;

import config.Params;
import modele.Airtime;
import modele.Caissier;
import modele.Misek;
import modele.Miset;
import modele.Partner;
import modele.ReportingDto;
import modele.Versement;
import superbetDAO.AirtimeDAO;
import superbetDAO.CaissierDAO;
import superbetDAO.KenoDAO;
import superbetDAO.MisekDAO;
import superbetDAO.PartnerDAO;
import superbetDAO.VersementDAO;
import superbetDAO.api.exeception.DAOAPIException;
import superbetDAO.api.implementations.SuperGameDAOAPI;
import superbetDAO.api.interfaces.ISuperGameDAOAPILocal;

public class ReportingForm {
	private PartnerDAO partnerDao;
	private CaissierDAO caissierDao;
	private ReportingDto report;
	private List<ReportingDto> reports;
	
	
	private static final String FIELD_DATE1 = "rep_ddebut";
	private static final String FIELD_DATE2 = "rep_dfin";
	private static final String FIELD_CODERACE = "nc_rapport1";
	private static final String FIELD_CAISSE = "nc_rapport";
	private  String resultat = null;
	private  Map<String, String> erreurs = new HashMap<String, String>();
	private  Map<String, ReportingDto> m_airtime = new HashMap<String, ReportingDto>();
	private String dat1, dat2, coderace, user;
	private int taille;
	private  ISuperGameDAOAPILocal  supergameAPI;
	
	private ArrayList<Misek> misek = new ArrayList<Misek>();
	private ArrayList<Versement> versk = new ArrayList<Versement>();
	
	
	public ReportingForm(AirtimeDAO airtimeDao, PartnerDAO partnerDao, MisekDAO misekDao, 
			CaissierDAO caissierDao, KenoDAO kenoDao, VersementDAO verstDao) {
		
		this.partnerDao = partnerDao;
		this.caissierDao = caissierDao;
		supergameAPI = new SuperGameDAOAPI();
	}
	
	public void manage_reporting(HttpServletRequest request){
		
		long t1,t2;
		dat1 = getDate(request, FIELD_DATE1);
		dat2 = getDate(request, FIELD_DATE2);
		coderace = getPartner(request, FIELD_CODERACE);
		user = getPartner(request, FIELD_CAISSE);
		
		//System.out.println("user "+user+" dat2: "+coderace);
		
		try {
			t1 = givetimestamp(dat1+",00:00:00");
			t2 = givetimestamp(dat2+",23:59:00");
			if(t1 > t2){
				resultat = "Periode pas correcte";
				return;
			}
			
			//recuperation de tous les caissiers de la salle
			List<Caissier> list_cais = caissierDao.findByPartner(coderace);
			Partner p = partnerDao.find(coderace);
			//System.out.println("list_cais "+list_cais.size());
			report = new ReportingDto();
			reports = new ArrayList<ReportingDto>();
			List<Airtime> list_airtime = new ArrayList<>();
			double tot_credit;
			report = new ReportingDto();
			
			if(user.equalsIgnoreCase("")) {
				
				double summise;
				double sumwin;
				
				for(Caissier cais : list_cais) {
					
					if(cais.getProfil()!=1) {
						report = new ReportingDto();
						tot_credit = supergameAPI.getSuperGameDAO().findCumulCredit(Params.url, p.getIdpartner(),cais.getLoginc(), dat1, dat2);
					//	tot_credit = airtimeDao.findCumulCredit(""+cais.getIdCaissier(), dat1+" 00:00:00", dat2+" 23:59:00");
						summise = supergameAPI.getSuperGameDAO().getMiseRK(Params.url, p.getIdpartner(),cais.getLoginc(), t1, t2);
						//summise = misekDao.getMiseRK(""+cais.getIdCaissier(), ""+t1, ""+t2);
						sumwin = supergameAPI.getSuperGameDAO().getVersements(Params.url, p.getIdpartner(),cais.getLoginc(), t1, t2);
					    //double sumwin = verstDao.getVersementD(""+t1, ""+cais.getIdCaissier(), ""+t2);
						
						
						report.setApport(tot_credit);
						report.setUser(cais.getLoginc());
						report.setSummise(summise);
						report.setSumwin(sumwin);
						reports.add(report);
					}
					
				}
			}
			else {
				
				double summise;
				double sumwin;
				
				for(Caissier cais : list_cais) {
					
					if(cais.getProfil()!=1 && user.equalsIgnoreCase(cais.getLoginc())) {
//						list_airtime = supergameAPI.getSuperGameDAO().findAllairtime(Params.url, p.getIdpartner(),cais.getLoginc(), dat1, dat2);
//						//list_airtime = airtimeDao.find(""+cais.getIdCaissier(), dat1+" 00:00:00", dat2+" 23:59:00");
//						if (list_airtime == null) return;
//						for(Airtime cred : list_airtime) {
//							report = new ReportingDto();
//							summise = supergameAPI.getSuperGameDAO().getMiseRK(Params.url, p.getIdpartner(),cais.getLoginc(), t1, t2);
//							sumwin = supergameAPI.getSuperGameDAO().getVersements(Params.url, p.getIdpartner(),cais.getLoginc(), t1, t2);
//		
//							report.setSummise(summise);
//							report.setSumwin(sumwin);
//							report.setUser(user);
//							report.setApport(cred.getCredit());
//							reports.add(report);
//						}
						
						report = new ReportingDto();
						tot_credit = supergameAPI.getSuperGameDAO().findCumulCredit(Params.url, p.getIdpartner(),cais.getLoginc(), dat1, dat2);
					//	tot_credit = airtimeDao.findCumulCredit(""+cais.getIdCaissier(), dat1+" 00:00:00", dat2+" 23:59:00");
						summise = supergameAPI.getSuperGameDAO().getMiseRK(Params.url, p.getIdpartner(),cais.getLoginc(), t1, t2);
						//summise = misekDao.getMiseRK(""+cais.getIdCaissier(), ""+t1, ""+t2);
						sumwin = supergameAPI.getSuperGameDAO().getVersements(Params.url, p.getIdpartner(),cais.getLoginc(), t1, t2);
					    //double sumwin = verstDao.getVersementD(""+t1, ""+cais.getIdCaissier(), ""+t2);
						
						
						report.setApport(tot_credit);
						report.setUser(cais.getLoginc());
						report.setSummise(summise);
						report.setSumwin(sumwin);
						reports.add(report);
						
					}
					
				}
//				for(Map.Entry mapentry : map_airtime.entrySet()) {
//					String login = (String) mapentry.getKey();
//					List<Airtime> list_air = (List<Airtime>) mapentry.getValue();
//					System.out.println("list_air "+list_air.size()+" "+list_air.get(0).getCredit());
//					for(Airtime credit : list_air) {
//						report = new ReportingDto();
//						double summise = misekDao.getMiseRK(""+credit.getCaissier(), ""+t1, ""+t2);
//						double sumwin = verstDao.getVersementD(""+t1, ""+credit.getCaissier(), ""+t2);
//						report.setSummise(summise);
//						report.setSumwin(sumwin);
//						report.setUser(login);
//						report.setApport(credit.getCredit());
//						reports.add(report);
//					}
//				}
			}
	
			taille = reports.size();
		}
		catch(ParseException | IOException | JSONException | URISyntaxException | DAOAPIException ex) {
			
		}
	}
	
	private String getDate( HttpServletRequest request,String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
       // System.out.println(valeur);
        if(valeur == null)
        	return valeur;
        else
    	    return valeur.trim(); 
	}
	
	private String getPartner( HttpServletRequest request,String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
       
       if ( valeur == null) {
           return valeur;
       } else{
    	  return valeur.trim(); 
       }
       
	}
	
	private long givetimestamp(String str) throws ParseException{
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
		Date date;
		try{
			date = (Date)formatter.parse(str);
		}
		catch(Exception e){
			date = new Date();
		}
		
		
		long output = date.getTime()/1000L;
		
		String d_str = Long.toString(output);
		
		long timestamp = Long.parseLong(d_str)*1000;
		
		return timestamp;
	}

	public String getResultat() {
		return resultat;
	}

	public void setResultat(String resultat) {
		this.resultat = resultat;
	}

	public Map<String, String> getErreurs() {
		return erreurs;
	}

	public void setErreurs(Map<String, String> erreurs) {
		this.erreurs = erreurs;
	}

	public String getDat1() {
		return dat1;
	}

	public void setDat1(String dat1) {
		this.dat1 = dat1;
	}

	public String getDat2() {
		return dat2;
	}

	public void setDat2(String dat2) {
		this.dat2 = dat2;
	}

	public String getCoderace() {
		return coderace;
	}

	public void setCoderace(String coderace) {
		this.coderace = coderace;
	}

	public ArrayList<Misek> getMisek() {
		return misek;
	}

	public void setMisek(ArrayList<Misek> misek) {
		this.misek = misek;
	}

	public ArrayList<Versement> getVersk() {
		return versk;
	}

	public void setVersk(ArrayList<Versement> versk) {
		this.versk = versk;
	}

	public Map<String, ReportingDto> getM_airtime() {
		return m_airtime;
	}

	public void setM_airtime(Map<String, ReportingDto> m_airtime) {
		this.m_airtime = m_airtime;
	}

	public int getTaille() {
		return taille;
	}

	public void setTaille(int taille) {
		this.taille = taille;
	}

	public List<ReportingDto> getReports() {
		return reports;
	}

	public void setReports(List<ReportingDto> reports) {
		this.reports = reports;
	}
	
	
}
