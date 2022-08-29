package superbetDAO;

import java.util.ArrayList;

import modele.Caissier;
import modele.Partner;

public interface CaissierDAO {
	
	int create(Caissier caissier) throws DAOException;
	Caissier find(String login, String pass) throws DAOException;
	Caissier update(Caissier caissier) throws DAOException;
	Caissier findById(Long id) throws DAOException;
	void delete(Caissier caissier) throws DAOException;
	int updateState(Caissier caissier) throws DAOException;
	Caissier findByLogin(String login) throws DAOException;
	ArrayList<Caissier> findByPartner(String coderace) throws DAOException;
	Caissier findByLoginIdPartner(String login, String coderace)
			throws DAOException;
}
