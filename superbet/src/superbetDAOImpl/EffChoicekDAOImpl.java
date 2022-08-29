package superbetDAOImpl;

import static database.Bd.fermeturesSilencieuses;
import static database.Bd.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modele.EffChoicek;
import superbetDAO.DAOException;
import superbetDAO.DAOFactory;
import superbetDAO.EffChoicekDAO;

public class EffChoicekDAOImpl implements EffChoicekDAO {
	
	private DAOFactory daofactory;
	
	private static final String SQL_F_EFFCHOICEK = ""; 
	private static final String SQL_C_EFFCHOICEK = "INSERT INTO effchoicek SET idParil=? , idMisek=? , kchoice=? , idkeno=?, mtantchoix=? ";
	private static final String SQL_F_EFFCHOICE_MISEK = "SELECT * FROM effchoicek WHERE idmisek = ? ";
	private static final String SQL_F_EFFCHOICE_KENO_MISEK = "SELECT * FROM effchoicek WHERE idmisek = ? and idkeno = ? ";
	
	
	public EffChoicekDAOImpl(DAOFactory daoFactory){
		this.daofactory = daoFactory;
	}
	
	@Override
	public int create(EffChoicek effchoicek) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		
		int statut = 0;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion,SQL_C_EFFCHOICEK, true, effchoicek.getIdparil(),effchoicek.getImisek(),
					effchoicek.getKchoice(),effchoicek.getIdkeno(),effchoicek.getMtchoix());
			
			statut = preparedStatement.executeUpdate();
			
			/* Analyse du statut retourné par la requête d'insertion */
			if ( statut == 0 ) {
				throw new DAOException( "Échec de la création d'un pari, aucune ligne ajoutée dans la table." );
			}
			/* Récupération de l'id auto-généré par la requête d'insertion */
			
			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			if ( valeursAutoGenerees.next() ) {
			/* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
				effchoicek.setIdeffchoicek( valeursAutoGenerees.getLong( 1 ) );
			} else {
				statut = 0;
				throw new DAOException( "Échec de la création d'un pari en base, aucun ID auto-généré retourné." );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
		}
		
		return statut;

	}

	@Override
	public EffChoicek find(String idmisek) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EffChoicek update(EffChoicek effchoicek) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(EffChoicek effchoicek) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<EffChoicek> searchTicketK(String idmisek) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<EffChoicek> list_effch = new ArrayList<EffChoicek>();
		EffChoicek effchk = new EffChoicek();
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_EFFCHOICE_MISEK, false, idmisek);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			/*if ( resultSet.next() ) {
				effchk = map(resultSet);
				list_effch.add(effchk);
			}*/
			boolean encore = resultSet.next();
		   //---
			while(encore){
				/*for(int i = 0; i < nbcols; i++){
					_result.add(""+res.getString(i+1));
				}*/
				effchk = map(resultSet);
				list_effch.add(effchk);
				encore = resultSet.next();
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
	
		return list_effch;
	}
	
	@Override
	public ArrayList<EffChoicek> searchTicketK(String idmisek, String keno) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<EffChoicek> list_effch = new ArrayList<EffChoicek>();
		EffChoicek effchk = new EffChoicek();
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_EFFCHOICE_KENO_MISEK, false, idmisek, keno);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			/*if ( resultSet.next() ) {
				effchk = map(resultSet);
				list_effch.add(effchk);
			}*/
			boolean encore = resultSet.next();
		   //---
			while(encore){
				/*for(int i = 0; i < nbcols; i++){
					_result.add(""+res.getString(i+1));
				}*/
				effchk = map(resultSet);
				list_effch.add(effchk);
				encore = resultSet.next();
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
	
		return list_effch;
	}
	
	private static EffChoicek map( ResultSet resultSet ) throws SQLException {
	    
		EffChoicek effchk = new EffChoicek();
		
		effchk.setIdeffchoicek(resultSet.getLong("ideffchoicek"));
		effchk.setIdparil(resultSet.getString("idparil"));
		effchk.setKchoice(resultSet.getString("kchoice"));
		effchk.setMtchoix(resultSet.getString("mtantchoix"));
		effchk.setImisek(resultSet.getString("idmisek"));
		effchk.setIdkeno(resultSet.getLong("idkeno"));
		
		return effchk;
	}

}
