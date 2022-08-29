package superbetDAO.api.interfaces;

import superbetDAO.api.SuperGameDAO;

public interface ISuperGameDAOAPILocal {
	
	public static final String SERVICE_NAME = "SuperGameDAOAPILocal";
	
	public void reload();
	public SuperGameDAO getSuperGameDAO();
}
