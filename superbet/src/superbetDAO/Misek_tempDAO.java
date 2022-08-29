package superbetDAO;

import java.util.ArrayList;

import modele.Misek_temp;

public interface Misek_tempDAO {
	int create(Misek_temp misek) throws DAOException;
	Misek_temp find(String misek) throws DAOException;
	Misek_temp update(Misek_temp misek) throws DAOException;
	void delete(Misek_temp misek) throws DAOException;
	ArrayList<Misek_temp> searchWaitingBet() throws DAOException;
	int update(Long misek) throws DAOException;
}
