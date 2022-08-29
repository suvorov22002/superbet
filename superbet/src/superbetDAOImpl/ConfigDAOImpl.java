package superbetDAOImpl;

import static database.Bd.fermeturesSilencieuses;
import static database.Bd.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import modele.Caissier;
import modele.Config;
import superbetDAO.ConfigDAO;
import superbetDAO.DAOException;
import superbetDAO.DAOFactory;

public class ConfigDAOImpl implements ConfigDAO{
	
	private DAOFactory daofactory;
	
	private static final String SQL_F_CONFIG = "SELECT * FROM config Where coderace = ? ";
	private static final String SQL_U_CONFIG = "update config set stepbonus=? , percent = ? , bonusrate = ? where coderace = ? ";
	private static final String SQL_C_CONFIG = "insert into config (coderace) Values ( ? )";
	private static final String SQL_U_CONFIG_BONUS = "update config set stepbonus=? where coderace = ? ";
	private static final String SQL_U_CONFIG_BONUSK = "update config set bonusrate=?, bnkmin=?, bnkmax=? where coderace = ? ";
	private static final String SQL_U_CONFIG_BONUSP = "update config set bonusrate=?, bnpmin=?, bnpmax=? where coderace = ? ";
	
	public ConfigDAOImpl(DAOFactory daoFactory){
		this.daofactory = daoFactory;
	}

	@Override
	public int create(Config config) throws DAOException {
		// TODO Auto-generated method stub
				Connection connexion = null;
				PreparedStatement preparedStatement = null;
				ResultSet valeursAutoGenerees = null;
				int statut = 0;
				
				try {
					/* Récupération d'une connexion depuis la Factory */
					connexion = daofactory.getConnection();
					preparedStatement = initialisationRequetePreparee( connexion,SQL_C_CONFIG, true, config.getCoderace());
					
					statut = preparedStatement.executeUpdate();
					
					/* Analyse du statut retourné par la requête d'insertion */
					if ( statut == 0 ) {
						throw new DAOException( "Échec de la création du caissier, aucune ligne ajoutée dans la table." );
					}
					/* Récupération de l'id auto-généré par la requête d'insertion */
					
					valeursAutoGenerees = preparedStatement.getGeneratedKeys();
					if ( valeursAutoGenerees.next() ) {
					/* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
						config.setIdConfig( valeursAutoGenerees.getLong( 1 ) );
					} else {
						throw new DAOException( "Échec de la création du caissier en base, aucun ID auto-généré retourné." );
					}
				} catch ( SQLException e ) {
					throw new DAOException( e );
				} finally {
					fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
				}
				
				return statut;
	}

	@Override
	public Config find(String coderace) throws DAOException {
		// TODO Auto-generated method stub
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Config config = null;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_CONFIG, false, coderace  );
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			if ( resultSet.next() ) {
				config = map( resultSet );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		return config;
	}

	@Override
	public int update(Config config) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		
		int statut = 0;
		
//		try {
//			/* Récupération d'une connexion depuis la Factory */
//			connexion = daofactory.getConnection();
//			preparedStatement = initialisationRequetePreparee( connexion,SQL_U_CONFIG, false, config.getStep(), config.getPercent(), 
//					config.getBonusrate(), config.getCoderace());
//			
//			statut = preparedStatement.executeUpdate();
//			
//			/* Analyse du statut retourné par la requête d'insertion */
//			if ( statut == 0 ) {
//				throw new DAOException( "Échec de la création d'un tirage, aucune ligne ajoutée dans la table." );
//			}
//			
//		} catch ( SQLException e ) {
//			throw new DAOException( e );
//		} finally {
//			fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
//		}
		
		return statut;
	}
	
	@Override
	public int updateBonus(String step, String coderace) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		
		int statut;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion,SQL_U_CONFIG_BONUS, false, step, coderace);
			
			statut = preparedStatement.executeUpdate();
			
			/* Analyse du statut retourné par la requête d'insertion */
			if ( statut == 0 ) {
				throw new DAOException( "Échec de la création d'un tirage, aucune ligne ajoutée dans la table." );
			}
			
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
		}
		
		return statut;
	}
	
	@Override
	public int updateBonusK(double bonusrate, double bnkmin, double bnkmax, String coderace) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		
		int statut;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion,SQL_U_CONFIG_BONUSK, false,bonusrate, bnkmin, bnkmax, coderace);
			
			statut = preparedStatement.executeUpdate();
			
			/* Analyse du statut retourné par la requête d'insertion */
			if ( statut == 0 ) {
				throw new DAOException( "Echec de la creation d'un tirage, aucune ligne ajoutée dans la table." );
			}
			
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
		}
		
		return statut;
	}
	
	@Override
	public int updateBonusP(double bonusrate, double bnpmin, double bnpmax, String coderace) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		
		int statut;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion,SQL_U_CONFIG_BONUSP, false,bonusrate, bnpmin, bnpmax, coderace);
			
			statut = preparedStatement.executeUpdate();
			
			/* Analyse du statut retourné par la requête d'insertion */
			if ( statut == 0 ) {
				throw new DAOException( "Echec de la creation d'un tirage, aucune ligne ajoutée dans la table." );
			}
			
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
		}
		
		return statut;
	}

	@Override
	public void delete(Config config) throws DAOException {
		// TODO Auto-generated method stub
		
	} 
	
private static Config map( ResultSet resultSet ) throws SQLException {
	    
		Config config = new Config();
		config.setIdConfig( resultSet.getLong( "idconfig" ) );
		config.setPercent(resultSet.getDouble( "percent" ));
		config.setBonusrate(resultSet.getDouble( "bonusrate" ));
		config.setBnskmin(resultSet.getDouble( "bnkmin" ));
		config.setBnskmax(resultSet.getDouble( "bnkmax" ));
		config.setBnspmin(resultSet.getDouble( "bnpmin" ));
		config.setBnspmax(resultSet.getDouble( "bnpmax" ));
		config.setBnsdmin(resultSet.getDouble( "bndmin" ));
		config.setBnsdmax(resultSet.getDouble( "bndmax" ));
		config.setCoderace(resultSet.getString("coderace"));
		
		return config;
	}

}
