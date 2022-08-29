package config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Params {
	
	public static int MISE_MIN = 200;
	public static int MISE_MAX;
	public static double GAIN_MAX = 100000;
	public static int MISE_KENO_MAX = 10000;
	public static double GAIN_KENO_MAX = 100000;
	
	public static int MISE_SPIN_MAX = 10000;
	public static double GAIN_SPIN_MAX = 1000000;
	public static Map<String, String> mapHeure = new HashMap<String, String>();
	public static Map<String, String> mapHeureOuv = new HashMap<String, String>();
	//public static String url = "http://127.0.0.1:9090/api/v1/supergames";
	public static String url = "";
	public static String PARTNER;
	public static final String ALGO_CHIFFREMENT = "SHA-256";
	
	
	public static List<Integer> getHitFrequency(int hf, int n_cycle) {

		ArrayList<Integer> roundList = new ArrayList<>();
		int[] round;
		ArrayList<String> combis = new ArrayList<String>();
		ArrayList<String> combi = new ArrayList<String>();
		
		int nbS = (int)(hf*n_cycle)/100;
		round = new int[nbS];
		

    	for(int i=1;i<(1+n_cycle);i++)
    		combis.add(""+i);
    	for(int j=0;j<n_cycle;j++){
    		int index = generate(combis.size());
    		combi.add(combis.get(index));
    		combis.remove(index);
    	}
    	
    	for(int ii=0;ii<nbS;ii++) round[ii] = Integer.parseInt(combi.get(ii));
//    	for(int nb : round) {
//    		System.out.print(" "+nb);
//		}
    	//System.out.println("");
    	Arrays.sort(round);
    	for(int nb : round) {
			roundList.add(nb);
		}
    	
		return roundList;
	}
	
	private static int generate(int n){
		int lower = 0;
		int higher = n;
		int dbl;
		  
		dbl = (int) (Math.random() * (higher-lower)) + lower;
		return dbl;
   }
	
	public static double getRoundPayed(double percent, int roundSize, int n_cycle, double bonusrate) {
		double sum = 0;
		
		sum = (MISE_MIN * (percent-bonusrate))/roundSize;
		return sum;
	}
	
	public static void endOfDay(String coderage) {
		
	}
}
