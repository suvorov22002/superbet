package config;

import java.util.ArrayList;

import modele.Caissier;
import modele.ControlDisplaySpin;
import modele.EffChoicep;

public class UtileSpin {
	public static int numSpec[] = {36,18,12,9,6,4,3,2};
	/**
	 *  nombre : x36
	 *  vert: x36
	 *  rouge: x2
	 *  noir: x2
	 *  mirroir: x18
	 *  twin: x12
	 *  secteur: x6
	 *  low/high: x2
	 *  final: x9
	 *  douzaine: x3
	 *  pair/impair: x2
	 *  low/high - color: x4
	 */
	
	public static int timeSpin = 180;
	public static boolean canbet_p = true; //mise possible
	
	public static int giveBonus[] = {10000, 25000, 35000};
	public static int stepBonus = 0;
	public static int bonusP_amount = 0;
	public static double bonusP_down;
	public static long bonus_codeP;
	public static double bonusPmin = 600.0;

	
	public static int[] black = {2,4,6,8,10,11,13,15,17,20,22,24,26,28,29,31,33,35};
	public static int[] red = {1,3,5,7,9,12,14,16,18,19,21,23,25,27,30,32,34,36};
	public static int[] sectA = {2,21,4,19,15,32};
	public static int[] sectB = {25,17,34,6,27,13};
	public static int[] sectC = {36,11,30,8,23,10};
	public static int[] sectD = {5,24,16,33,1,20};
	public static int[] sectE = {14,31,9,22,18,29};
	public static int[] sectF = {7,28,12,35,3,26};
	
	
	private ArrayList<Caissier> customers = new ArrayList<Caissier>();
	public static ArrayList<String> display_draw = new ArrayList<String>();
	
	public static ArrayList<ControlDisplaySpin> _display_draw = new ArrayList<ControlDisplaySpin>();
	
	public static void addDisplayDraw(String dclient){
		display_draw.add(dclient);
	}
	
	public static void removeDisplayDraw(int num){
		display_draw.remove(num);
	}
	public static ArrayList<String> getDisplay_draw() {
		return display_draw;
	}

	public static boolean checkExistingSameDisplayCoderace(String coderace){
		for(int i=0;i<display_draw.size();i++){
			if(display_draw.get(i).equalsIgnoreCase(coderace)){
				return true;
			}
		}
		return false;
	}
	
	public static int _checkExistingSameDisplayCoderace(String coderace){
		for(int i=0;i<_display_draw.size();i++){
			if(_display_draw.get(i).getCoderace().equalsIgnoreCase(coderace)){
				return i;
			}
		}
		return -1;
	}
	
	public static double verifSpin(EffChoicep effchoicep, String result){
		
		return checkTicketP(effchoicep, result);
	}

	private static double checkTicketP(EffChoicep t, String draw){

		double cote = 0;
		int pari;
		String choice = "";
		
		if(draw.length() < 1){
			return -1;
		}
		
		pari = Integer.parseInt(t.getIdparis());
		
		
		choice = t.getPchoice();
		//	rd = Double.parseDouble(t.getMtchoix());
		
		switch(pari){
			case 1: //single number
				if(choice.equalsIgnoreCase(draw)){
					cote = UtileSpin.numSpec[0];
				}
							
				break;
			case 2: //couleur verte
				if(draw.equalsIgnoreCase("0")){
					cote = UtileSpin.numSpec[0];
				}
				break;
			case 3: //couleur noire
				for(int n=0;n<UtileSpin.black.length;n++){
					if(Integer.parseInt(draw) == UtileSpin.black[n]){
						cote = UtileSpin.numSpec[7];
						break;
					}
				}
				break;
			case 4: //couleur rouge
				for(int n=0;n<UtileSpin.red.length;n++){
					if(Integer.parseInt(draw) == UtileSpin.red[n]){
						cote = UtileSpin.numSpec[7];
						break;
					}
				}
				break;
			case 5: //mirroir 12 / 21
				if(Integer.parseInt(draw) == 12 || Integer.parseInt(draw) == 21){
					cote = UtileSpin.numSpec[1];
				}
				break;
			case 6: //mirroir 13/31
				if(Integer.parseInt(draw) == 13 || Integer.parseInt(draw) == 31){
					cote = UtileSpin.numSpec[1];
				}
				break;
			case 7: //mirroir 23/32
				if(Integer.parseInt(draw) == 23 || Integer.parseInt(draw) == 32){
					cote = UtileSpin.numSpec[1];
				}
				break;
			case 8: //twin 11
				if(Integer.parseInt(draw) == 11){
					cote = UtileSpin.numSpec[2];
				}
				break;
			case 9: //twin 22
				if(Integer.parseInt(draw) == 22){
					cote = UtileSpin.numSpec[2];
				}
				break;
			case 10: //twin 33
				if(Integer.parseInt(draw) == 33){
					cote = UtileSpin.numSpec[2];
				}
				break;
			case 11: //secteur A
				for(int n=0;n<UtileSpin.sectA.length;n++){
					if(Integer.parseInt(draw) == UtileSpin.sectA[n]){
						cote = UtileSpin.numSpec[4];
						break;
					}
				}
				break;
			case 12: // secteur B
				for(int n=0;n<UtileSpin.sectB.length;n++){
					if(Integer.parseInt(draw) == UtileSpin.sectB[n]){
						cote = UtileSpin.numSpec[4];
						break;
					}
				}
				break;
			case 13: // secteur C
				for(int n=0;n<UtileSpin.sectC.length;n++){
					if(Integer.parseInt(draw) == UtileSpin.sectC[n]){
						cote = UtileSpin.numSpec[4];
						break;
					}
				}
				break;
			case 14: //secteur D
				for(int n=0;n<UtileSpin.sectD.length;n++){
					if(Integer.parseInt(draw) == UtileSpin.sectD[n]){
						cote = UtileSpin.numSpec[4];
						break;
					}
				}
				break;
			case 15: // secteur E
				for(int n=0;n<UtileSpin.sectE.length;n++){
					if(Integer.parseInt(draw) == UtileSpin.sectE[n]){
						cote = UtileSpin.numSpec[4];
						break;
					}
				}
				break;
			case 16: // secteur F
				for(int n=0;n<UtileSpin.sectF.length;n++){
					if(Integer.parseInt(draw) == UtileSpin.sectF[n]){
						cote = UtileSpin.numSpec[4];
						break;
					}
				}
				break;
			case 17: // low
				if(Integer.parseInt(draw) < 19 && Integer.parseInt(draw) > 0){
					cote = UtileSpin.numSpec[7];
				}
				break;
			case 18: //high
				if(Integer.parseInt(draw) < 37 && Integer.parseInt(draw) > 18){
					cote = UtileSpin.numSpec[7];
				}
				break;
			case 19: //final 0
				if(Integer.parseInt(draw) == 10 || Integer.parseInt(draw) == 20 || Integer.parseInt(draw) == 30){
					cote = UtileSpin.numSpec[3];
				}
				break;
			case 20://final 1
				if(Integer.parseInt(draw) == 11 || Integer.parseInt(draw) == 21 || Integer.parseInt(draw) == 31){
					cote = UtileSpin.numSpec[3];
				}
				break;
			case 21:// final 2
				if(Integer.parseInt(draw) == 12 || Integer.parseInt(draw) == 22 || Integer.parseInt(draw) == 32){
					cote = UtileSpin.numSpec[3];
				}
				break;
			case 22://final 3
				if(Integer.parseInt(draw) == 13 || Integer.parseInt(draw) == 23 || Integer.parseInt(draw) == 33){
					cote = UtileSpin.numSpec[3];
				}
				break;
			case 23://douzaine 1
				if(Integer.parseInt(draw) < 13 && Integer.parseInt(draw) > 0){
					cote = UtileSpin.numSpec[6];
				}
				break;
			case 24://douzaine 2
				if(Integer.parseInt(draw) < 25 && Integer.parseInt(draw) > 12){
					cote = UtileSpin.numSpec[6];
				}
				break;
			case 25://douzaine 3
				if(Integer.parseInt(draw) < 37 && Integer.parseInt(draw) > 24){
					cote = UtileSpin.numSpec[6];
				}
				break;
			case 26:// pair
				if(Integer.parseInt(draw) % 2 == 0){
					cote = UtileSpin.numSpec[7];
				}
				break;
			case 27:// impair
				if(Integer.parseInt(draw) % 2 != 0){
					cote = UtileSpin.numSpec[7];
				}
				break;
			case 28://final 4
				if(Integer.parseInt(draw) == 14 || Integer.parseInt(draw) == 24 || Integer.parseInt(draw) == 34){
					cote = UtileSpin.numSpec[3];
				}
				break;
			case 29:// final 5
				if(Integer.parseInt(draw) == 15 || Integer.parseInt(draw) == 25 || Integer.parseInt(draw) == 35){
					cote = UtileSpin.numSpec[3];
				}
				break;
			case 30://final 6
				if(Integer.parseInt(draw) == 16 || Integer.parseInt(draw) == 26 || Integer.parseInt(draw) == 36){
					cote = UtileSpin.numSpec[3];
				}
				break;
			case 31: // low-rouge
				boolean isred = false;
				
				for(int n=0;n<UtileSpin.red.length;n++){
					if(Integer.parseInt(draw) == UtileSpin.red[n]){
						isred = true;
						break;
					}
				}
				if(Integer.parseInt(draw) < 19 && Integer.parseInt(draw) > 0 && isred){
					cote = UtileSpin.numSpec[5];
				}
				break;
			case 32: // low-noir
				boolean isblack = false;
				
				for(int n=0;n<UtileSpin.black.length;n++){
					if(Integer.parseInt(draw) == UtileSpin.black[n]){
						isblack = true;
						break;
					}
				}
				if(Integer.parseInt(draw) < 19 && Integer.parseInt(draw) > 0 && isblack){
					cote = UtileSpin.numSpec[5];
				}
				break;
			case 33: // high-rouge
				boolean _isred = false;
				
				for(int n=0;n<UtileSpin.red.length;n++){
					if(Integer.parseInt(draw) == UtileSpin.red[n]){
						_isred = true;
						break;
					}
				}
				if(Integer.parseInt(draw) < 37 && Integer.parseInt(draw) > 18 && _isred){
					cote = UtileSpin.numSpec[5];
				}
				break;
			case 34: // high-noir
				boolean _isblack = false;
				
				for(int n=0;n<UtileSpin.black.length;n++){
					if(Integer.parseInt(draw) == UtileSpin.black[n]){
						_isblack = true;
						break;
					}
				}
				if(Integer.parseInt(draw) < 37 && Integer.parseInt(draw) > 18 && _isblack){
					cote = UtileSpin.numSpec[5];
				}
				break;
			default:
				break;
			}
			
			return cote;
		}

}
