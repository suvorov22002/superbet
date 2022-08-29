package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import config.UtileSpin;
import superbetDAO.DAOFactory;
import superbetDAO.SpinDAO;

public class RefreshDatas extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1876177094468070040L;
	public static final String CONF_DAO_FACTORY = "daofactory";
	private SpinDAO spinDao;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RefreshDatas() {
		// TODO Auto-generated constructor stub
    	super();
	}
    
    public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO caissier */
		this.spinDao = ( (DAOFactory)getServletContext().getAttribute( CONF_DAO_FACTORY )).getSpinDao();
		//this.partnerDao = ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getPartnerDao();
	}
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String coderace = request.getParameter("partner");
		String[] last = spinDao.getLastPdraw(coderace);
		
		int j,nbre,l;
		String[] lastsK = new String[5];
		int[] chk_nbre = new int[37]; //controle des nombre
		int[] chk_mirror = new int[3]; //controle des mirroir
		int[] chk_twin = new int[3]; //controle des twins
		int[] chk_color = new int[3]; //controle des couleur (rouge,noir, vert)
		int[] chk_finals = new int[7]; //controle des finals 
		int[] chk_secteurs = new int[6]; //controle secteur
		ArrayList<Integer> _last = new ArrayList<Integer>(); // tableau des recents tirage
		
		for(int i=1;i<last.length;i=i+2){
			_last.add(Integer.parseInt(last[i])); 
		}
		//System.out.println("_LAST: "+_last.size());
		
		//initialisation des compteurs
		for(int i=0;i<chk_nbre.length;i++){
			chk_nbre[i] = 0;
		}
		for(int i=0;i<chk_mirror.length;i++){
			chk_mirror[i] = 0;
		}
		for(int i=0;i<chk_twin.length;i++){
			chk_twin[i] = 0;
		}
		for(int i=0;i<chk_color.length;i++){
			chk_color[i] = 0;
		}
		for(int i=0;i<chk_finals.length;i++){
			chk_finals[i] = 0;
		}
		for(int i=0;i<chk_secteurs.length;i++){
			chk_secteurs[i] = 0;
		}
		
		for(int z=0;z<_last.size();z++){
			for(int zz=0;zz<chk_nbre.length;zz++){
				if(_last.get(z) == zz){
					chk_nbre[zz] = chk_nbre[zz] + 1;
					break;
				}
			}
		}
		 //gestion des mirroirs
		for(int z=0;z<_last.size();z++){
				if(_last.get(z) == 12 || _last.get(z) == 21){
					chk_mirror[0] = chk_mirror[0] + 1;
				}
				else if(_last.get(z) == 13 || _last.get(z) == 31){
					chk_mirror[1] = chk_mirror[1] + 1;
				}
				else if(_last.get(z) == 23 || _last.get(z) == 32){
					chk_mirror[2] = chk_mirror[2] + 1;
				}
		}
		
		//gestion des finals
		for(int z=0;z<_last.size();z++){
			if(_last.get(z) == 10 || _last.get(z) == 20 || _last.get(z) == 30){
				chk_finals[0] = chk_finals[0] + 1;
			}
			else if(_last.get(z) == 11 || _last.get(z) == 21 || _last.get(z) == 31){
				chk_finals[1] = chk_finals[1] + 1;
			}
			else if(_last.get(z) == 12 || _last.get(z) == 22 || _last.get(z) == 32){
				chk_finals[2] = chk_finals[2] + 1;
			}
			else if(_last.get(z) == 13 || _last.get(z) == 23 || _last.get(z) == 33){
				chk_finals[3] = chk_finals[3] + 1;
			}
			else if(_last.get(z) == 14 || _last.get(z) == 24 || _last.get(z) == 34){
				chk_finals[4] = chk_finals[4] + 1;
			}
			else if(_last.get(z) == 15 || _last.get(z) == 25 || _last.get(z) == 35){
				chk_finals[5] = chk_finals[5] + 1;
			}
			else if(_last.get(z) == 16 || _last.get(z) == 26 || _last.get(z) == 36){
				chk_finals[6] = chk_finals[6] + 1;
			}
		}
		
		//gestion des twins
		for(int z=0;z<_last.size();z++){
			if(_last.get(z) == 11 || _last.get(z) == 22 || _last.get(z) == 33){
				chk_twin[0] = chk_twin[0] + 1;
			}
	    }
		
		//gestion des couleurs
		boolean isred = false;
		for(int z=0;z<_last.size();z++){
			isred = false;
			if(_last.get(z) == 0){
				chk_color[2] = chk_color[2] + 1;
				continue;
			}
			for(int i=0;i<18;i++){
				if(UtileSpin.red[i] == _last.get(z)){
					chk_color[0] = chk_color[0] + 1;
					isred = true;
					break;
				}
			}
			if(!isred){
				chk_color[1] = chk_color[1] + 1;
			}
	    }
		
		//gestion des secteurs
		boolean sect = false;
		for(int z=0;z<_last.size();z++){
			sect = false;
			for(int zz=0;zz<UtileSpin.sectA.length;zz++){
				if(UtileSpin.sectA[zz] == _last.get(z)){
					chk_secteurs[0] = chk_secteurs[0] + 1;
					sect = true;
					break;
				}
			}
			if(sect){
				continue;
			}
			for(int zz=0;zz<UtileSpin.sectB.length;zz++){
				if(UtileSpin.sectB[zz] == _last.get(z)){
					chk_secteurs[1] = chk_secteurs[1] + 1;
					sect = true;
					break;
				}
			}
			if(sect){
				continue;
			}
			for(int zz=0;zz<UtileSpin.sectC.length;zz++){
				if(UtileSpin.sectC[zz] == _last.get(z)){
					chk_secteurs[2] = chk_secteurs[2] + 1;
					sect = true;
					break;
				}
			}
			if(sect){
				continue;
			}
			for(int zz=0;zz<UtileSpin.sectD.length;zz++){
				if(UtileSpin.sectD[zz] == _last.get(z)){
					chk_secteurs[3] = chk_secteurs[3] + 1;
					sect = true;
					break;
				}
			}
			if(sect){
				continue;
			}
			for(int zz=0;zz<UtileSpin.sectE.length;zz++){
				if(UtileSpin.sectE[zz] == _last.get(z)){
					chk_secteurs[4] = chk_secteurs[4] + 1;
					sect = true;
					break;
				}
			}
			if(sect){
				continue;
			}
			for(int zz=0;zz<UtileSpin.sectF.length;zz++){
				if(UtileSpin.sectF[zz] == _last.get(z)){
					chk_secteurs[5] = chk_secteurs[5] + 1;
					sect = true;
					break;
				}
			}
			if(sect){
				continue;
			}
		}
		//gestion des parités
		int sum_pair = 0;
		int sum_ipair = 0;
		for(int z=0;z<_last.size();z++){
			if(_last.get(z)%2 == 0){
				sum_pair = sum_pair + 1;
			}
			else{
				sum_ipair = sum_ipair + 1;
			}
		}
		
		//gestion low, high
		int sum_low = 0;
		int sum_high = 0;
		for(int z=0;z<_last.size();z++){
			if(_last.get(z) > 0 && _last.get(z) < 19){
				sum_low = sum_low + 1;
			}
			else if(_last.get(z) > 18 && _last.get(z) < 37){
				sum_high = sum_high + 1;
			}
		}
		
		//gestion douzaine
		int sum_douz1 = 0;
		int sum_douz2 = 0;
		int sum_douz3 = 0;
		for(int z=0;z<_last.size();z++){
			if(_last.get(z) > 0 && _last.get(z) < 13){
				sum_douz1 = sum_douz1 + 1;
			}
			else if(_last.get(z) > 12 && _last.get(z) < 25){
				sum_douz2 = sum_douz2 + 1;
			}
			else if(_last.get(z) > 24 && _last.get(z) < 37){
				sum_douz3 = sum_douz3 + 1;
			}
		}
		
		//gestion des tirages precedents (06 au total)
		
		   nbre = (last.length-1)/Integer.parseInt(last[0]);
		   if(nbre > 6) nbre = 6;
		   lastsK = new String[nbre];
		   j = Integer.parseInt(last[0]) * nbre;
		   l = 0;
		  // System.out.println("nbre "+nbre);
		   for(int i=0;i<nbre;i++){
			   String str = "";
			   str = str + last[j--] + "_" + last[j--];
			   lastsK[l++] = str;
		   }
		
		
		 response.setContentType("application/json; charset=UTF-8");
		 response.setHeader("Cache-Control", "no-cache");
		
		 
		// On construit le tableau JSON des résultat
         	JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
         
        // On construit l'objet JSON pour l'expediteur
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
          
            for(int i=0;i<chk_nbre.length;i++){
            	//System.out.println("chk_nbre: "+chk_nbre[i]);
            	objectBuilder.add("chknbre"+i, chk_nbre[i]);
            }
            for(int i=0;i<chk_mirror.length;i++){
            	//System.out.println("chk_mirror: "+chk_mirror[i]);
            	objectBuilder.add("chkmirror"+i, chk_mirror[i]);
            }
            //System.out.println("chk_twin: "+chk_twin[0]);
            objectBuilder.add("chktwin", chk_twin[0]);
            for(int i=0;i<chk_color.length;i++){
            	//System.out.println("chk_color: "+chk_color[i]);
            	objectBuilder.add("chkcolor"+i, chk_color[i]);
            }
            
            for(int i=0;i<chk_finals.length;i++){
            	//System.out.println("chk_finals: "+chk_finals[i]);
            	objectBuilder.add("chkfinals"+i, chk_finals[i]);
            }
            
            for(int i=0;i<chk_secteurs.length;i++){
            	//System.out.println("chk_finals: "+chk_finals[i]);
            	objectBuilder.add("chksecteurs"+i, chk_secteurs[i]);
            }
            //System.out.println("chk_pair: "+sum_low+" chk_ipair: "+sum_ipair);
            objectBuilder.add("chkpair", sum_pair);
            objectBuilder.add("chkipair", sum_ipair);
            
           //System.out.println("chk_lh: "+sum_low+" chk_h: "+sum_high);
            objectBuilder.add("chklow", sum_low);
            objectBuilder.add("chkhigh", sum_high);
            
          //  System.out.println("chk_d: "+sum_douz1+" chk_d2: "+sum_douz2+" chk_d3: "+sum_douz3);
            objectBuilder.add("chkdouz1", sum_douz1);
            objectBuilder.add("chkdouz2", sum_douz2);
            objectBuilder.add("chkdouz3", sum_douz3);
            
            switch(nbre){
        	case 1:
        		objectBuilder.add("draw1", lastsK[0]);
        		break;
        	case 2:
        		objectBuilder.add("draw2", lastsK[1]);
                objectBuilder.add("draw1", lastsK[0]);
                break;
        	case 3:
        		objectBuilder.add("draw3", lastsK[2]);
                objectBuilder.add("draw2", lastsK[1]);
                objectBuilder.add("draw1", lastsK[0]);
                break;
        	case 4:
        		objectBuilder.add("draw4", lastsK[3]);
                objectBuilder.add("draw3", lastsK[2]);
                objectBuilder.add("draw2", lastsK[1]);
                objectBuilder.add("draw1", lastsK[0]);
                break;
        	case 5:
        		objectBuilder.add("draw5", lastsK[4]);
                objectBuilder.add("draw4", lastsK[3]);
                objectBuilder.add("draw3", lastsK[2]);
                objectBuilder.add("draw2", lastsK[1]);
                objectBuilder.add("draw1", lastsK[0]);
                break;
        	case 6:
        		objectBuilder.add("draw6", lastsK[5]);
        		objectBuilder.add("draw5", lastsK[4]);
                objectBuilder.add("draw4", lastsK[3]);
                objectBuilder.add("draw3", lastsK[2]);
                objectBuilder.add("draw2", lastsK[1]);
                objectBuilder.add("draw1", lastsK[0]);
                break;
            default:
            	break;
          }
          objectBuilder.add("tot_tirage", nbre); 
         
            arrayBuilder.add(objectBuilder);
        
        // On renvois le résultat
        response.getWriter().write(arrayBuilder.build().toString());
    }
}
