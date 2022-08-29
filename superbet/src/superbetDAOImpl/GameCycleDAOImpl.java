package superbetDAOImpl;

import static database.Bd.fermeturesSilencieuses;
import static database.Bd.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import modele.GameCycle;
import superbetDAO.DAOException;
import superbetDAO.DAOFactory;
import superbetDAO.GameCycleDAO;

 public class GameCycleDAOImpl implements GameCycleDAO{
	
	private DAOFactory daofactory;
	private static final String SQL_C_GAMECYCLE = "Insert into game_cycle Set percent = ? , tour = ? , hitfrequence = ? , refundp = ? , "
			+ "position = ? , arrangement = ? , idpartner = ? , jeu = ? , mise = ? , misef = ? , date_fin = ? , archive = ? ";
	private static final String SQL_U_GAMECYCLE = "Update game_cycle Set percent = ? , tour = ? , hitfrequence = ? , refundp = ? , "
			+ "position = ? , arrangement = ? , jeu = ? Where idpartner = ? ";
	private static final String SQL_U_GAMECYCLE_ARCHIVE = "Update game_cycle Set curr_percent = ? , date_fin = ? , archive = ? , misef = ? , stake = ? , payout = ? , jkpt = ? Where idpartner = ? and jeu = ? and archive = 0 ";
	private static final String SQL_U_GAMECYCLE_RTP = "Update game_cycle Set refundp = ? Where idpartner = ? and archive = 0 and jeu = ? ";
	private static final String SQL_U_GAMECYCLE_POS = "Update game_cycle Set position = ? Where idpartner = ? and archive = 0 and jeu = ? ";
	private static final String SQL_F_GAMECYCLE_ALL = "Select * From game_cycle Where idpartner = ? order by idcycle ";
	//private static final String SQL_F_GAMECYCLE = "Select * From game_cycle Where idpartner = ? and archive = 0 ";
	private static final String SQL_F_GAMECYCLE = "Select * From game_cycle Where idpartner = ? order by idcycle desc ";
	private static final String SQL_F_GAMECYCLE_JEU = "Select * From game_cycle Where idpartner = ? And jeu = ? and archive = 0 ";
	//private static final String SQL_F_GAMECYCLE_MAX_ARCHIVE = "Select * from gamecycle Where idpartner = ? And archive";
	
	public GameCycleDAOImpl(DAOFactory daoFactory) {
		this.daofactory = daoFactory;
	}

	@Override
	public int create(GameCycle gmc) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		int statut = 0;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion,SQL_C_GAMECYCLE, true, gmc.getPercent(), gmc.getTour(), gmc.getHitfrequence(), 
					gmc.getRefundp(),gmc.getPosition(), gmc.getArrangement(), gmc.getPartner(), gmc.getJeu(), gmc.getMise(), gmc.getMisef(),  gmc.getDate_fin(), gmc.getArchive());
			
			statut = preparedStatement.executeUpdate();
			
			/* Analyse du statut retourné par la requête d'insertion */
			if ( statut == 0 ) {
				throw new DAOException( "Echec de l'ajout de credit." );
			}
			/* Récupération de l'id auto-généré par la requête d'insertion */
			
			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			if ( valeursAutoGenerees.next() ) {
			/* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
				gmc.setPartner( valeursAutoGenerees.getLong( 1 ) );
			} else {
				throw new DAOException( "Echec de la credit airtime en base." );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
		}
		
		return statut;

	}

	@Override
	public ArrayList<GameCycle> find(Long partner) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		GameCycle gmc = new GameCycle();
		ArrayList<GameCycle> list_cycle = new ArrayList<GameCycle>();
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_GAMECYCLE, false, partner);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			while ( resultSet.next() ) {
				gmc = map(resultSet);
				list_cycle.add(gmc);
			}
			
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
	
		return list_cycle; 	}
	
	@Override
	public ArrayList<GameCycle> findAll(Long partner) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		GameCycle gmc = new GameCycle();
		ArrayList<GameCycle> list_cycle = new ArrayList<GameCycle>();
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_GAMECYCLE_ALL, false, partner);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			while ( resultSet.next() ) {
				gmc = map(resultSet);
				list_cycle.add(gmc);
			}
			
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
	
		return list_cycle; 	}
	
	@Override
	public GameCycle findByGame(Long partner, String jeu) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		GameCycle gmc = null;
		
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_GAMECYCLE_JEU, false, partner, jeu);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			while ( resultSet.next() ) {
				gmc = map(resultSet);
			}
			
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
	
		return gmc; 	}

	@Override
	public int update(GameCycle gmc) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		
		int statut;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion,SQL_U_GAMECYCLE, false, gmc.getPercent(), gmc.getTour(), gmc.getHitfrequence(), 
					gmc.getRefundp(),gmc.getPosition(), gmc.getArrangement(), gmc.getJeu(), gmc.getPartner());
			
			statut = preparedStatement.executeUpdate();
			
			/* Analyse du statut retourné par la requête d'insertion */
			if ( statut == 0 ) {
				throw new DAOException( "Échec de la mise a jour du bonus, aucune ligne ajoutée dans la table." );
			}
			
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
		}
		
		return statut;
	}

	@Override
	public void delete(GameCycle gmc) throws DAOException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int updateRfp(double rfp, Long idpartner, String jeu) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		
		int statut;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion,SQL_U_GAMECYCLE_RTP, false, rfp, idpartner, jeu);
			
			statut = preparedStatement.executeUpdate();
			
			/* Analyse du statut retourné par la requête d'insertion */
			if ( statut == 0 ) {
				throw new DAOException( "Échec de la mise a jour du bonus, aucune ligne ajoutée dans la table." );
			}
			
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
		}
		
		return statut;
	}
	
	@Override
	public int updateArchive(double percent, String date, int archive, Long idpartner, String jeu, long misef,double stake, double payout, double jkpt) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		
		int statut;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion,SQL_U_GAMECYCLE_ARCHIVE, false, percent, date, archive, misef, stake, payout, jkpt, idpartner, jeu);
			
			statut = preparedStatement.executeUpdate();
			
			/* Analyse du statut retourné par la requête d'insertion */
			if ( statut == 0 ) {
				throw new DAOException( "Échec de la mise a jour du bonus, aucune ligne ajoutée dans la table." );
			}
			
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
		}
		
		return statut;
	}

	@Override
	public int updatePos(int pos, Long idpartner, String jeu) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		
		int statut;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion,SQL_U_GAMECYCLE_POS, false, pos, idpartner, jeu);
			
			statut = preparedStatement.executeUpdate();
			
			/* Analyse du statut retourné par la requête d'insertion */
			if ( statut == 0 ) {
				throw new DAOException( "Échec de la mise a jour du bonus, aucune ligne ajoutée dans la table." );
			}
			
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
		}
		
		return statut;
	}
	
   private static GameCycle map( ResultSet resultSet ) throws SQLException {
	    
		
		GameCycle gmc = new GameCycle();
		gmc.setPercent(resultSet.getDouble("percent"));
		gmc.setTour(resultSet.getInt("tour"));
		gmc.setHitfrequence(resultSet.getInt("hitfrequence"));
		gmc.setRefundp(resultSet.getDouble("refundp"));
		gmc.setPosition(resultSet.getInt("position"));
		gmc.setArrangement(resultSet.getString("arrangement"));
		gmc.setPartner(resultSet.getLong("idpartner"));
		gmc.setJeu(resultSet.getString("jeu"));
		gmc.setMise(resultSet.getInt("mise"));
		gmc.setMisef(resultSet.getInt("misef"));
		gmc.setArchive(resultSet.getInt("archive"));
		gmc.setDate_fin(resultSet.getString("date_fin"));
		gmc.setCurr_percent(resultSet.getDouble("curr_percent"));
		gmc.setStake(resultSet.getDouble("stake"));
		gmc.setStake(resultSet.getDouble("payout"));
		gmc.setStake(resultSet.getDouble("jkpt"));
		
		
		return gmc;
	}


}
