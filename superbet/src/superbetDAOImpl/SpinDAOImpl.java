package superbetDAOImpl;

import static database.Bd.fermeturesSilencieuses;
import static database.Bd.initialisationRequetePreparee;
import static database.Bd.lireResultBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modele.Spin;
import superbetDAO.DAOException;
import superbetDAO.DAOFactory;
import superbetDAO.SpinDAO;

public class SpinDAOImpl implements SpinDAO {
	
private DAOFactory daofactory;
	
	private static final String SQL_C_SPIN = "INSERT INTO spin SET idsalle=1  , drawnump = ?, coderace=? "; 
	private static final String SQL_U_SPIN = "UPDATE spin SET drawnumbp=? ,"
	      + " heuretirage = ? "
	      + "WHERE drawnump = ? AND coderace = ? "; 
	private static final String SQL_F_BONUS_AMOUNT_SPIN = "select bonuspamount from spin"; 
	private static final String SQL_U_BONUS_AMOUNT_SPIN = "update spin set bonuspamount=? where drawnump=?";
	private static final String SQL_F_MAX_DRAW = "SELECT * from spin WHERE coderace= ? and drawnump = (SELECT MAX(drawnump) FROM spin WHERE coderace= ? )";
	private static final String SQL_F_MAX_PREVIOUS_DRAW = "SELECT started,drawnump from spin WHERE coderace= ? and drawnump = "
			+ "(SELECT MAX(drawnump) FROM spin WHERE coderace= ? and started != 0)";
	private static final String SQL_F_PREVIOUS_FIVE_DRAW = "SELECT drawnumbp,drawnump FROM spin"
			+ " WHERE coderace=? AND started != 0 "
			+ "ORDER BY drawnump DESC "
			+ "LIMIT 0,120 ";
	private static final String SQL_F_MAX_ID = "SELECT * from spin WHERE idspin = (SELECT MAX(idspin) FROM spin where coderace = ?)";
	private static final String SQL_F_DRAW = "SELECT * from spin WHERE drawnump = ? ";
	private static final String SQL_U_DRAW_END = "UPDATE spin SET started=1 WHERE drawnump=? ";
	private static final String SQL_F_ID = "SELECT idSpin FROM spin where coderace = ? AND drawnump = ? ";
	private static final String SQL_U_SPIN_BONUS = "UPDATE spin SET bonusPamount = ? , bonuspcod = ? WHERE idspin = ? " ; 
	
	
	public SpinDAOImpl(DAOFactory daoFactory) {
		this.daofactory = daoFactory;
	}

	
	@Override
	public int create(Spin spin) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		int statut = 0;
		
		try {
			/* R??cup??ration d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion,SQL_C_SPIN, true, spin.getDrawNumP(), spin.getCoderace());
			
			statut = preparedStatement.executeUpdate();
			
			/* Analyse du statut retourn?? par la requ??te d'insertion */
			if ( statut == 0 ) {
				throw new DAOException( "??chec de la cr??ation d'un tirage, aucune ligne ajout??e dans la table." );
			}
			/* R??cup??ration de l'id auto-g??n??r?? par la requ??te d'insertion */
			
			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			if ( valeursAutoGenerees.next() ) {
			/* Puis initialisation de la propri??t?? id du bean Utilisateur avec sa valeur */
				spin.setIdspin( valeursAutoGenerees.getLong( 1 ) );
			} else {
				statut = 0;
				throw new DAOException( "??chec de la cr??ation du coupon en base, aucun ID auto-g??n??r?? retourn??." );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
		}
		
		return statut;
	}

	@Override
	public Spin find(String drawNump) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public synchronized Spin find_Max_draw(String coderace) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Spin spin = null;
		
		try {
			/* R??cup??ration d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_MAX_DRAW, false, coderace, coderace);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de donn??es de l'??ventuel ResulSet retourn?? */
			if ( resultSet.next() ) {
				spin = map( resultSet );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		return spin;
	}

	@Override
	public int update(Spin spin) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		
		int statut;
		
		try {
			/* R??cup??ration d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion,SQL_U_SPIN, false, spin.getDrawnumbP(),
					spin.getHeureTirage(),spin.getDrawNumP(), spin.getCoderace());
			
			statut = preparedStatement.executeUpdate();
			
			/* Analyse du statut retourn?? par la requ??te d'insertion */
			if ( statut == 0 ) {
				throw new DAOException( "??chec de la cr??ation d'un tirage, aucune ligne ajout??e dans la table." );
			}
			
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
		}
		
		return statut;
	}

	@Override
	public void delete(Spin Spin) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public synchronized double findBonusAmount() throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String[] idmiset;
		
		try {
			/* R??cup??ration d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_BONUS_AMOUNT_SPIN, false);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de donn??es de l'??ventuel ResulSet retourn?? */
			idmiset = lireResultBD(resultSet);
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		double n = Double.parseDouble(idmiset[1]);
		
		return n;
	}

	@Override
	public synchronized int updateBonus(double bonus, int drawnumber) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		
		int statut;
		
		try {
			/* R??cup??ration d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion,SQL_U_BONUS_AMOUNT_SPIN, false, bonus, drawnumber);
			
			statut = preparedStatement.executeUpdate();
			
			/* Analyse du statut retourn?? par la requ??te d'insertion */
			if ( statut == 0 ) {
				throw new DAOException( "??chec de la mise ?? jour du bonus, aucune ligne ajout??e dans la table." );
			}
			
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
		}
		
		return statut;
	}

	@Override
	public synchronized String[] getLastPdraw(String coderace) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String[] last;
		
		try {
			/* R??cup??ration d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_PREVIOUS_FIVE_DRAW, false, coderace);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de donn??es de l'??ventuel ResulSet retourn?? */
			last = lireResultBD(resultSet);
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		return last;
	}

	@Override
	public Spin find_Last_draw() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public synchronized Spin getMaxIdDrawP(String coderace) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Spin spin = null;
		
		try {
			/* R??cup??ration d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_MAX_ID, false, coderace);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de donn??es de l'??ventuel ResulSet retourn?? */
			if ( resultSet.next() ) {
				spin = map( resultSet );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		
		return spin;
	}

	@Override
	public Spin searchResultP(String drawnum) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Spin spin = null;
		
		try {
			/* R??cup??ration d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_DRAW, false, drawnum);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de donn??es de l'??ventuel ResulSet retourn?? */
			if ( resultSet.next() ) {
				spin = map( resultSet );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		return spin;
	}

	@Override
	public int updateDrawEnd(int drawnumber) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		
		int statut;
		
		try {
			/* R??cup??ration d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion,SQL_U_DRAW_END, false, drawnumber);
			
			statut = preparedStatement.executeUpdate();
			
			/* Analyse du statut retourn?? par la requ??te d'insertion */
			if ( statut == 0 ) {
				throw new DAOException( "??chec de la mise ?? jour de la fin de course, aucune ligne ajout??e dans la table." );
			}
			
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
		}
		
		return statut;
	}
	
	@Override
	public int getIdSpin(String coderace, int drawnump) throws DAOException {
		// TODO Auto-generated method stub
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String[] idspin;
		
		try {
			/* R??cup??ration d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_ID, false, coderace, drawnump);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de donn??es de l'??ventuel ResulSet retourn?? */
			idspin = lireResultBD(resultSet);
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		int n = Integer.parseInt(idspin[1]);
		
		return n;
	}
	
	@Override
	public int setCodeBonusP(double amount, long code, String idspin) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		
		int statut;
		
		try {
			/* R??cup??ration d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion,SQL_U_SPIN_BONUS, false, amount, code, idspin);
			
			statut = preparedStatement.executeUpdate();
			
			/* Analyse du statut retourn?? par la requ??te d'insertion */
			if ( statut == 0 ) {
				throw new DAOException( "??chec de la cr??ation d'un tirage, aucune ligne ajout??e dans la table." );
			}
			
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
		}
		
		return statut;
	}
	
	@Override
	public String[] find_Max_draw_bis(String coderace) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String[] last;
		
		try {
			/* R??cup??ration d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_MAX_PREVIOUS_DRAW, false, coderace, coderace);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de donn??es de l'??ventuel ResulSet retourn?? */
			last = lireResultBD(resultSet);
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		return last;
	}
	
	private static Spin map( ResultSet resultSet ) throws SQLException {
	    
		Spin spin = new Spin();
		
		spin.setIdspin(resultSet.getLong("idspin"));
		spin.setCoderace(resultSet.getString("coderace"));
		spin.setDrawnumbP(resultSet.getString("drawnumbp"));
		spin.setBonusPAmount(resultSet.getString("bonuspamount"));
		spin.setBonusPcod(resultSet.getString("bonuspcod"));
		spin.setDrawNumP(resultSet.getString("drawnump"));
		spin.setHeureTirage(resultSet.getString("heureTirage"));
		spin.setStarted(resultSet.getString("started"));
		
		return spin;
	}
}
