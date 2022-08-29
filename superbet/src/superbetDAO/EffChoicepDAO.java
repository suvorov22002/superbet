package superbetDAO;

import java.util.ArrayList;

import modele.EffChoicep;

public interface EffChoicepDAO {
	
	int create(EffChoicep effchoicep) throws DAOException;
	EffChoicep find(String idmisep) throws DAOException;
	EffChoicep update(EffChoicep effchoicep) throws DAOException;
	void delete(EffChoicep effchoicep) throws DAOException;
	ArrayList<EffChoicep> searchTicketP(String idmisep) throws DAOException;
}
