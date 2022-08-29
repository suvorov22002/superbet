package superbetDAO;

import java.util.ArrayList;

import modele.Misep;

public interface MisepDAO {
	
	int create(Misep misep) throws DAOException;
	Misep find(String login, String pass) throws DAOException;
	Misep update(Misep misep) throws DAOException;
	void delete(Misep misep) throws DAOException;
	int findId() throws DAOException; 
	Misep searchMisesP(String string) throws DAOException;
	Misep searchMisep(String idmisep) throws DAOException;
	int getNumDraw(String idmisep) throws DAOException;
	public double getMiseRp(String caissier, String date, String date1) throws DAOException;
	int getIntvTicketp(String idcais, String date, String date1) throws DAOException;
	ArrayList<Misep> getMisep(String max, String min) throws DAOException;
	int searchDrawNumP(String idspin) throws DAOException;
	ArrayList<Misep> searchMisePdraw(String num) throws DAOException;
}
