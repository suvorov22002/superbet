package superbetDAO;

import java.util.ArrayList;

import modele.Keno;

public interface KenoDAO {
	
	int create(Keno keno) throws DAOException;
	Keno find(String drawNumK) throws DAOException;
	Keno find_Max_draw(String coderace) throws DAOException;
	String[] find_Max_draw_bis(String coderace) throws DAOException;
	int update(Keno keno) throws DAOException;
	boolean delete(String coderace) throws DAOException;
	double findBonusAmount() throws DAOException;
	int updateBonus(double bonus, int drawnumber) throws DAOException;
	String[] getLastKdraw(String coderace) throws DAOException;
	//void create_draw(String drawnum) throws DAOException;
	//Keno find_Last_draw() throws DAOException;
	Keno getMaxIdDrawK(String coderace) throws DAOException;
	Keno searchResultK(String drawnum) throws DAOException; 
	int updateDrawEnd(int drawnumber) throws DAOException;
	int getIdKenos(String coderace, int drawnumk) throws DAOException;
	int setCodeBonusK(double amount, long code, String idkeno) throws DAOException;
	String[] getLastKBonus(String coderace) throws DAOException;
	double findTotalBonusAmount(int id1, int id2, String coderace) throws DAOException;
	ArrayList<Keno> find_Last_draw(String coderace) throws DAOException;
	Keno find_Single_draw(String coderace) throws DAOException;
	Keno find_Max_draw_num(String coderace, String num) throws DAOException;
	ArrayList<Keno> getAllLastKdraw(String coderace) throws DAOException;

}
