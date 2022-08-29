package superbetDAO;

import java.util.ArrayList;

import modele.Versement;

public interface VersementDAO {
	int create(Versement versement) throws DAOException;
	Versement find(String idmiset) throws DAOException;
	Versement update(Versement versement) throws DAOException;
	void delete(Versement versement) throws DAOException;
	Versement find_vers_miset(String idmiset) throws DAOException;
	double getVersementD(String date, String caissier, String date1) throws DAOException;
	int getPayTicket(String idcais, String date, String date1)  throws DAOException;
	ArrayList<Versement> getVersementk(String min, String max, String jeu) throws DAOException;
	int updateVersementD(String date, String caissier, String date1) throws DAOException;
}
