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
import modele.Misek;
import modele.Partner;
import modele.Versement;
import superbetDAO.EffChoicekDAO;
import superbetDAO.KenoDAO;
import superbetDAO.MisekDAO;
import superbetDAO.Misek_tempDAO;
import superbetDAO.MisetDAO;
import superbetDAO.PartnerDAO;
import superbetDAO.UtilDAO;
import superbetDAO.VersementDAO;
import superbetDAO.api.exeception.DAOAPIException;
import superbetDAO.api.implementations.SuperGameDAOAPI;
import superbetDAO.api.interfaces.ISuperGameDAOAPILocal;

public final class AdminForm {
	
	private static final String FIELD_DATE1 = "ddebut";
	private static final String FIELD_DATE2 = "dfin";
	private static final String FIELD_CODERACE = "ficoderace";
	
	
	private  String resultat = null;
	private  Map<String, String> erreurs = new HashMap<String, String>();
	private  String nbre_keno = "0";
	private  String nbre_v_keno = "0";
	private int taille = 0;
	private int sizek = 0;
	
	private  double sum_keno;
	private  double sum_v_keno;
	private  double percent_keno;
	private double balance;
	private String dat1, dat2, coderace;
	private List<Misek> misek = new ArrayList<Misek>();
	private List<Versement> versk = new ArrayList<Versement>();
	
	private KenoDAO kenoDao;
	private MisetDAO misetDao;
	private EffChoicekDAO effchoicekDao;
	private MisekDAO misekDao;
	private VersementDAO verstDao;
	private PartnerDAO partnerDao;
	private Misek_tempDAO misektpDao;
	private UtilDAO utilDao;
	
	private  ISuperGameDAOAPILocal  supergameAPI;
	
	
	public String getResultat() {
		return resultat;
	}
	
	
	public String getDat1() {
		return dat1;
	}

	public String getDat2() {
		return dat2;
	}

	public String getNbre_keno() {
		return nbre_keno;
	}
	
	public double getSum_keno() {
		return sum_keno;
	}
	
	public String getNbre_v_keno() {
		return nbre_v_keno;
	}

	public double getSum_v_keno() {
		return sum_v_keno;
	}

	public Map<String, String> getErreurs() {
		return erreurs;
	}
	
	public List<Misek> getMisek(){
		return misek;
	}
	
	public List<Versement> getVersk(){
		return versk;
	}
	
	public int getTaille() {
		return taille;
	}

	public String getCoderace() {
		return coderace;
	}


	public void setCoderace(String coderace) {
		this.coderace = coderace;
	}
	
	


	public double getBalance() {
		return balance;
	}


	public void setBalance(double balance) {
		this.balance = balance;
	}


	public double getPercent_keno() {
		return percent_keno;
	}


	public void setPercent_keno(double percent_keno) {
		this.percent_keno = percent_keno;
	}


	public int getSizek() {
		return sizek;
	}
	

	public AdminForm(KenoDAO kenoDao,MisetDAO misetDao,UtilDAO utilDao,EffChoicekDAO effchoicekDao,MisekDAO misekDao,
			VersementDAO verstDao, PartnerDAO partnerDao, Misek_tempDAO misektpDao){
		this.kenoDao = kenoDao;
		this.misetDao = misetDao;
		this.effchoicekDao = effchoicekDao;
		this.misekDao = misekDao;
		this.verstDao = verstDao;
		this.partnerDao = partnerDao;
		this.misektpDao = misektpDao;
		this.utilDao = utilDao;
		supergameAPI = new SuperGameDAOAPI();
	}
	
	public void manage_admin(HttpServletRequest request){
		long t1,t2;
		dat1 = getDate(request, FIELD_DATE1);
		dat2 = getDate(request, FIELD_DATE2);
		coderace = getPartner(request, FIELD_CODERACE);
		//System.out.println(partner);
		try {
//			System.out.println("periode1: "+dat1+",00:00:00");
//			System.out.println("periode2: "+dat2+",23:59:00");
			t1 = givetimestamp(dat1+",00:00:00");
			t2 = givetimestamp(dat2+",23:59:00");
			//System.out.println(t1+" : "+t2);
			if(t1 > t2){
				resultat = "Periode pas correcte";
				erreurs.put("date", "Periode pas correcte");
				return;
			}
			Partner p = partnerDao.find(coderace);
			if (p == null) {
				resultat = "Partenaire absent";
				erreurs.put("partenaire", "Partenaire absent");
				return;
			}
			misek = supergameAPI.getSuperGameDAO().statMisek(Params.url, t1, t2, p.getCoderace());
			//misek = misekDao.getMisek(""+t2, ""+t1, coderace);
			if (misek == null) {
				resultat = "Recherche echouée";
				erreurs.put("date", "Recherche echouée");
				return;
			}
			taille = misek.size();
			//System.out.println("taille= "+taille+" | "+misek.get(0).getIdmisek()+" _idmisek_ "+misek.get(taille-1).getIdmisek());
			
			versk = supergameAPI.getSuperGameDAO().getVersementk(Params.url, t1, t2, p.getCoderace());
		//	versk = verstDao.getVersementk(""+t1, ""+t2, "K");
			if (versk == null) {
				resultat = "Recherche echec";
				erreurs.put("date", "Recherche echec");
				return;
			}
			sizek = versk.size(); 
			System.out.println("taille vers= "+sizek);
			//System.out.println("taille= "+sizek+" | "+versk.get(0).getIdvers()+" _idvers_ "+versk.get(sizek-1).getIdvers());
			// calcul de la somme totale des mises de keno de la pÃ©riode
			for(int n=0;n<misek.size();n++){
				//System.out.println("summise= "+misek.get(n).getSumMise());
				sum_keno += Double.parseDouble(misek.get(n).getSumMise());
			}
			for(int n=0;n<versk.size();n++){
				sum_v_keno += versk.get(n).getMontant();
			}
			nbre_keno = ""+misek.size();
			nbre_v_keno = ""+versk.size();
			
			if(sum_keno == 0) {
				percent_keno = 0;
			}
			else {
				percent_keno = 100*(sum_v_keno/sum_keno);
				percent_keno = (double)((int)(percent_keno*100))/100;
			}
			balance = sum_keno - sum_v_keno;
			sum_keno = (double)((int)(sum_keno*100))/100;
			sum_v_keno = (double)((int)(sum_v_keno*100))/100;
			balance = (double)((int)(balance*100))/100;
		} catch (ParseException | IOException | JSONException | URISyntaxException | DAOAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
}
