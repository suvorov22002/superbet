package superbetDAO.api.implementations;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import superbetDAO.api.SuperGameDAO;
import superbetDAO.api.interfaces.ISuperGameDAOAPILocal;

@Stateless(name=ISuperGameDAOAPILocal.SERVICE_NAME,mappedName=ISuperGameDAOAPILocal.SERVICE_NAME)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SuperGameDAOAPI implements ISuperGameDAOAPILocal {
	
	private SuperGameDAO superGameDAO;
	
	public SuperGameDAOAPI() {
		init();
	}
	
	@PostConstruct
	public void init() {
		this.superGameDAO = new SuperGameDAO();
	}
	
	@Override
	public void reload() {
		init();
	}

	@Override
	public SuperGameDAO getSuperGameDAO() {
		return superGameDAO;
	}

}
