package superbetDAOImpl;

import static database.Bd.fermeturesSilencieuses;
import static database.Bd.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modele.Misek;
import modele.Misek_temp;
import superbetDAO.DAOException;
import superbetDAO.DAOFactory;
import superbetDAO.Misek_tempDAO;

public class Misek_tempDAOImpl implements Misek_tempDAO{
	
	private DAOFactory daofactory;
	
	private static final String SQL_C_MISEK_temp = "INSERT INTO misek_temp " + 
	      "SET multi=? , sum=? ,state=? ,idMiseK=?" ;
	private static final String SQL_F_MISEK_ID = "SELECT * from misek_temp where idmisek=? ";
	private static final String SQL_U_MISEK_ID = "Update misek_temp Set state = state + 1 where idmisek=? ";
	private static final String SQL_F_M_MISEK_ID = "SELECT * from misek_temp where multi != state ";
		
		
	public Misek_tempDAOImpl(DAOFactory daoFactory) {
		this.daofactory = daoFactory;
	}
	

	@Override
	public int create(Misek_temp misek) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		int statut = 0;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion,SQL_C_MISEK_temp, true, misek.getMulti(), misek.getSumMise(), 
					misek.getEtatMise(), misek.getIdmisek());
			
			statut = preparedStatement.executeUpdate();
			
			/* Analyse du statut retourné par la requête d'insertion */
			if ( statut == 0 ) {
				throw new DAOException( "Échec de la création d'une mise, aucune ligne ajoutée dans la table." );
			}
			/* Récupération de l'id auto-généré par la requête d'insertion */
			
			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			if ( valeursAutoGenerees.next() ) {
			/* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
				misek.setId( valeursAutoGenerees.getLong( 1 ) );
			} else {
				statut = 0;
				throw new DAOException( "Échec de la création d'une mise en base, aucun ID auto-généré retourné." );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
		}
		
		return statut;
	}

	@Override
	public Misek_temp find(String misek) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Misek_temp _misek = new Misek_temp();
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_MISEK_ID, false, misek);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			if ( resultSet.next() ) {
				_misek = map(resultSet);
			}
			
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
	
		return _misek; 
	}
	
	@Override
	public ArrayList<Misek_temp> searchWaitingBet() throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Misek_temp misek;
		ArrayList<Misek_temp> list_temp = new ArrayList<Misek_temp>();
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_M_MISEK_ID, false);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			while ( resultSet.next() ) {
				misek = map(resultSet);
				list_temp.add(misek);
			}
			
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		return list_temp; 
	}
	

	@Override
	public int update(Long misek) throws DAOException {
		// TODO Auto-generated method stub
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		
		int statut;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion,SQL_U_MISEK_ID, false, misek);
			
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
	public void delete(Misek_temp misek) throws DAOException {
		// TODO Auto-generated method stub

	}
	
	private static Misek_temp map( ResultSet resultSet ) throws SQLException {
	    
		Misek_temp misek = new Misek_temp();
		
		misek.setEtatMise(resultSet.getInt("state"));
		misek.setId(resultSet.getLong("id_misek_temp"));
		misek.setMulti(resultSet.getInt("multi"));
		misek.setSumMise(resultSet.getDouble("sum"));
		misek.setIdmisek(resultSet.getInt("idmisek"));
		
		return misek;
	}


	@Override
	public Misek_temp update(Misek_temp misek) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

}
