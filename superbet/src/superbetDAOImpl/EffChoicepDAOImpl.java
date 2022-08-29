package superbetDAOImpl;

import static database.Bd.fermeturesSilencieuses;
import static database.Bd.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modele.EffChoicep;
import superbetDAO.DAOException;
import superbetDAO.DAOFactory;
import superbetDAO.EffChoicepDAO;

public class EffChoicepDAOImpl implements EffChoicepDAO {
	
	private DAOFactory daofactory;
	
	private static final String SQL_F_EFFCHOICEP = ""; 
	private static final String SQL_C_EFFCHOICEP = "INSERT INTO effchoicep SET idParis=? , idMisep=? , pchoice=? , idspin=?, mtantchoix=? ";
	private static final String SQL_F_EFFCHOICE_MISEP = "SELECT * FROM effchoicep WHERE idmisep = ? ";
	
	
	public EffChoicepDAOImpl(DAOFactory daoFactory){
		this.daofactory = daoFactory;
	}
	
	@Override
	public int create(EffChoicep effchoicep) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		
		int statut = 0;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion,SQL_C_EFFCHOICEP, true, effchoicep.getIdparis(),effchoicep.getIdmisep(),
					effchoicep.getPchoice(),effchoicep.getIdspin(),effchoicep.getMtchoix());
			
			statut = preparedStatement.executeUpdate();
			
			/* Analyse du statut retourné par la requête d'insertion */
			if ( statut == 0 ) {
				throw new DAOException( "Échec de la création d'un pari, aucune ligne ajoutée dans la table." );
			}
			/* Récupération de l'id auto-généré par la requête d'insertion */
			
			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			if ( valeursAutoGenerees.next() ) {
			/* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
				effchoicep.setIdeffchoicep( valeursAutoGenerees.getLong( 1 ) );
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
	public EffChoicep find(String idmisep) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EffChoicep update(EffChoicep effchoicep) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(EffChoicep effchoicep) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<EffChoicep> searchTicketP(String idmisep) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<EffChoicep> list_effch = new ArrayList<EffChoicep>();
		EffChoicep effchp = new EffChoicep();
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_EFFCHOICE_MISEP, false, idmisep);
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
				effchp = map(resultSet);
				list_effch.add(effchp);
				encore = resultSet.next();
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
	
		return list_effch;
	}
	
	private static EffChoicep map( ResultSet resultSet ) throws SQLException {
	    
		EffChoicep effchp = new EffChoicep();
		
		effchp.setIdeffchoicep(resultSet.getLong("ideffchoicep"));
		effchp.setIdparis(resultSet.getString("idparis"));
		effchp.setPchoice(resultSet.getString("pchoice"));
		effchp.setMtchoix(resultSet.getString("mtantchoix"));
		effchp.setIdmisep(resultSet.getString("idmisep"));
		effchp.setIdspin(resultSet.getString("idspin"));
		
		return effchp;
	}

}
