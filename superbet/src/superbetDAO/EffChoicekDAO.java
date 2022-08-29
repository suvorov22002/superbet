package superbetDAO;

import java.util.ArrayList;

import modele.EffChoicek;

public interface EffChoicekDAO {
	
	int create(EffChoicek effchoicek) throws DAOException;
	EffChoicek find(String idmisek) throws DAOException;
	EffChoicek update(EffChoicek effchoicek) throws DAOException;
	void delete(EffChoicek effchoicek) throws DAOException;
	ArrayList<EffChoicek> searchTicketK(String idmisek) throws DAOException;
	ArrayList<EffChoicek> searchTicketK(String idmisek, String keno) throws DAOException;
}
