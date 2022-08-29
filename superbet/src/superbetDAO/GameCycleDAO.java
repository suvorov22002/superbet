package superbetDAO;

import java.util.ArrayList;
import java.util.Date;

import modele.GameCycle;


public interface GameCycleDAO {
	int create(GameCycle gmc) throws DAOException;
	ArrayList<GameCycle> find(Long partner) throws DAOException;
	int update(GameCycle gmc) throws DAOException;
	void delete(GameCycle gmc) throws DAOException;
	int updateRfp(double rfp, Long idpartner, String jeu) throws DAOException;
	int updatePos(int pos, Long idpartner, String jeu) throws DAOException;
	GameCycle findByGame(Long partner, String jeu) throws DAOException;
	int updateArchive(double percent, String date, int archive, Long idpartner, String jeu,long misef, double stake, double payout, double jkpt) throws DAOException;
	ArrayList<GameCycle> findAll(Long partner) throws DAOException;
}
