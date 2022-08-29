package superbetDAO;

import java.util.ArrayList;

import modele.AdminTicketDto;
import modele.Misek;

public interface MisekDAO {
	int create(Misek misek) throws DAOException;
	Misek find(String login, String pass) throws DAOException;
	int update(Misek misek) throws DAOException;
	void delete(Misek misek) throws DAOException;
	int ifindId(String caissier) throws DAOException; 
	int findId() throws DAOException; 
	Misek searchMisesK(String string) throws DAOException;
	Misek searchMiseK(String idmisek) throws DAOException;
	int getNumDraw(String idmisek) throws DAOException;
	public double getMiseRK(String caissier, String date, String date1) throws DAOException;
	int getIntvTicketK(String idcais, String date, String date1) throws DAOException;
	ArrayList<Misek> getMisek(String max, String min, String coderace) throws DAOException;
	ArrayList<AdminTicketDto> getMisekt(String max, String min, String coderace) throws DAOException;
	int searchDrawNumK(String idkeno) throws DAOException;
	ArrayList<Misek> searchMiseKdraw(String num) throws DAOException;
	ArrayList<Misek> searchWaitingBet(String IN, int drawnum) throws DAOException;
	ArrayList<Misek> searchWaitingKenoBet(String IN, int drawnum) throws DAOException;
	double getMiseKCycle(long misek, long mise, String caissiers) throws DAOException;
	double getMiseKCycleWin(long misek, long mise, String caissiers) throws DAOException;
	int findId(String miset) throws DAOException;
	int updateMiseRK(String caissier, String date, String date1) throws DAOException;
}
 