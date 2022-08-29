package business;

import java.util.ArrayList;
import java.util.Date;

import modele.Caissier;
import modele.ControlDisplaySpin;
import modele.Spin;

import org.apache.commons.lang3.time.DateFormatUtils;

import config.UtileSpin;

public class Refreshp implements Runnable{
	
	private static Thread thread;
	private ArrayList<Caissier> customers = new ArrayList<Caissier>();
//	private ArrayList<String> display_draw = new ArrayList<String>();
	
	public static ControlDisplaySpin cds;
	private String coderace;
	
	public Refreshp( ControlDisplaySpin cds, String name){
		this.cds = cds;
		this.coderace = name;
	}
	
	public ArrayList<Caissier> getCustomers() {
		return customers;
	}

	public void setCustomers(ArrayList<Caissier> customers) {
		this.customers = customers;
	}

	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		ControlDisplaySpin _cds = UtileSpin._display_draw.get(cds.getRang());
		//System.out.println("RANG: "+_cds.getCoderace()+" || "+_cds.getRang()+" || "+coderace);
		_cds.setTimeSpin(UtileSpin.timeSpin);
		//System.out.println("Screens connected: "+UtileSpin._display_draw.size());
		Spin _spin = _cds.lastDrawNum(coderace);
		//System.out.println(_spin);
		_cds.setDrawNump(Integer.parseInt(_spin.getDrawNumP()));
		System.out.println("LAST DRAWNUM: "+_cds.getDrawNump()); 
		while(true){
			_cds.setTimeSpin(UtileSpin.timeSpin);
			_cds.setDrawCount(25);
			//UtileKeno.canbet = true;
			
			while(_cds.isCountDown()){
				try {
					_cds.setTimeSpin(_cds.getTimeSpin()-1);
					//Refreshp.timeSpin--;
					//System.out.println("TIME KENO: "+Refreshp.timeSpin);
					
					if(_cds.getTimeSpin() < 1){
						_cds.setTimeSpin(0);
						_cds.setCountDown(false);//lancement du tirage
					}
					if(_cds.getTimeSpin() < 10){
						UtileSpin.canbet_p = false; // impossible de placer les mises
					}
					if(_cds.getTimeSpin() == 3){
					
						boolean trouve = false;
						synchronized (this) {
							_cds.setDrawCombip("");
							System.out.println("refresh synchro: "+_cds.getTimeSpin());
							do{
								_cds.setDrawCombip(_cds.buscarDraw());
								if(_cds.getDrawCombip() != ""){
									trouve = true;
								}
							}
							while(!trouve);
							
							Spin spin = new Spin();
							spin.setDrawnumbP(_cds.getDrawCombip());
							spin.setHeureTirage(DateFormatUtils.format(new Date(), "dd-MM-yyyy,HH:mm"));
							spin.setDrawNumP(""+_cds.getDrawNump());
							spin.setCoderace(getName());
							
							int	line = _cds.addUpSpin(spin);
							
						}
					}
					Thread.sleep(998);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			System.out.println("COMBIP: "+_cds.getDrawCombip());
			
			_cds.setDraw(true);
			while(_cds.isDraw()){
				_cds.setEnd(0);
				try {
					//drawCount--;
					_cds.setDrawCount(_cds.getDrawCount()-1);
					
					//System.out.println("TIME DRAW "+drawCount);
					if(_cds.getDrawCount() == 5){
						int num_tirage = 1+_cds.getDrawNump();
						System.out.println("DRAW Ajout d'une nouvelle ligne de tirage "+num_tirage );
						int line = _cds.addSpins(num_tirage, getName());
					
						if(line > 0){
							System.out.println("num added "+num_tirage );
							_cds.setDrawNump(num_tirage);
						}
						_cds.setTimeSpin(UtileSpin.timeSpin);
						UtileSpin.canbet_p = true;
					}
					
					if(_cds.getDrawCount() < 0 ){
						_cds.setDraw(false);
						_cds.setCountDown(true);
						_cds.setTimeSpin(UtileSpin.timeSpin);
					}
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			  //mise a jour du tirage terminé (started = 1)
			  boolean bonusp = _cds.manageBonusP();
			  System.out.println("REFRESH BONUS: "+bonusp);
			  if(bonusp){
				  _cds.setBonus(1);
			  }
			  else{
				  _cds.setBonus(0);
			  }
			  _cds.endDraw(_cds.getDrawNump());
			  
			  _cds.setEnd(1); //fin du tour
			 // System.out.println("REFRESH2 BONUS: "+_cds.getBonus()+" END: "+_cds.getEnd());
		}
		
	}
	
	public void start(){
		thread = new Thread(this);
		thread.start();
	}
	
	public void stop(){
		thread.stop();
	}
	public void suspend(){
		thread.suspend();
	}
	public void resume(){
		thread.resume();
	}
	
	public void addClient(Caissier client){
		customers.add(client);
	}
	
	public void removeClient(int num){
		customers.remove(num);
	}
	
	private String getName() {
		return coderace;
	}
	
}
