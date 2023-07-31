package superbetDAO;

import modele.Config;

public interface ConfigDAO {
	int create(Config config) throws DAOException;
	Config find(String coderace) throws DAOException;
	int update(Config config) throws DAOException;
	int updateBonus(String step, String coderace) throws DAOException;
	boolean delete(String coderace) throws DAOException;
	int updateBonusK(double bonusrate, double bnkmin, double bnkmax,
			String coderace) throws DAOException;
	int updateBonusP(double bonusrate, double bnpmin, double bnpmax,
			String coderace) throws DAOException;
}
