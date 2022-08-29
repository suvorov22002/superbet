package business;

import java.util.ArrayList;
import superbetDAO.DAOConfigurationException;
import modele.Caissier;

public class RecordUser {
	
	private static ArrayList<Caissier> customers;
	
	public RecordUser(ArrayList<Caissier> customers){
		customers = new ArrayList<Caissier>();
		this.customers = customers;
	}
	
	public RecordUser(){
		this.customers = new ArrayList<Caissier>();
	}
	
   public static RecordUser getInstance() throws DAOConfigurationException {
		
	   RecordUser instance = new  RecordUser();
			return instance;
	}
	
	public ArrayList<Caissier> getCustomers() {
		return customers;
	}

	public void setCustomers(ArrayList<Caissier> customers) {
		this.customers = customers;
	}
	
	public void addClient(Caissier client){
		customers.add(client);
	}
	
	public void removeClient(int num){
		customers.remove(num);
	}
}
