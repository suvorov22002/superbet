package business;

import java.util.ArrayList;
import java.util.List;

import modele.Caissier;
import superbetDAO.DAOConfigurationException;

public class RecordUser {
	
	private static List<Caissier> customers;
	
	public RecordUser(List<Caissier> customers){
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
	
	public List<Caissier> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Caissier> customers) {
		this.customers = customers;
	}
	
	public void addClient(Caissier client){
		customers.add(client);
	}
	
	public void removeClient(int num){
		customers.remove(num);
	}
}
