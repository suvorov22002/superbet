package config;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Timestamp {
	
	private static long ts0;
	
	public Timestamp(){
		
	}
	
	public static long givetimestamp(String str) throws ParseException{
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy,HH:mm:ss");
		
		Date date = (Date)formatter.parse(str);
		
		long output = date.getTime()/1000L;
		
		String d_str = Long.toString(output);
		
		long timestamp = Long.parseLong(d_str)*1000;
		
		return timestamp;
	}
	
	public static String givedate(long times){
		Date d = new Date(times);
		
		String ss =  new SimpleDateFormat("dd/MM/yyyy,HH:mm:ss",Locale.FRANCE).format(d);
		
		return ss;
	}
	
	public static boolean compare(long ts2) throws ParseException{
		boolean state;
		long ts;
		
		ts0 =  givetimestamp(new SimpleDateFormat("dd/MM/yyyy,HH:mm:ss",Locale.FRANCE).format(new Date()));
		
		ts = ts2 - ts0;
		
		if(ts > 0){
			state = true;
		}
		else{
			state = false;
		}
		
		return state;
	}
}
