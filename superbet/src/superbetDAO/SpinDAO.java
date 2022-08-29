package superbetDAO;

import modele.Spin;

public interface SpinDAO {
	
	int create(Spin Spin) throws DAOException;
	Spin find(String drawNumP) throws DAOException;
	Spin find_Max_draw(String coderace) throws DAOException;
	int update(Spin Spin) throws DAOException;
	void delete(Spin Spin) throws DAOException;
	double findBonusAmount() throws DAOException;
	int updateBonus(double bonus, int drawnumber) throws DAOException;
	String[] getLastPdraw(String coderace);
	//void create_draw(String drawnum) throws DAOException;
	Spin find_Last_draw() throws DAOException;
	Spin getMaxIdDrawP(String coderace) throws DAOException;
	Spin searchResultP(String drawnum) throws DAOException; 
	int updateDrawEnd(int drawnumber) throws DAOException;
	int getIdSpin(String coderace, int drawnump) throws DAOException;
	int setCodeBonusP(double amount, long code, String idspin) throws DAOException;
	String[] find_Max_draw_bis(String coderace) throws DAOException;
}
