package superbetDAOImpl;

import static database.Bd.fermeturesSilencieuses;
import static database.Bd.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import modele.Cagnotte;
import modele.Partner;
import modele.PartnerDto;
import superbetDAO.CagnotteDAO;
import superbetDAO.DAOException;
import superbetDAO.DAOFactory;

public class CagnotteDAOImpl implements CagnotteDAO {
	
	private DAOFactory daofactory;
	private static final String SQL_F_CAGNOTTE = "Select * from cagnotte Where idpartner = ? ";
	private static final String SQL_C_CAGNOTTE = "Insert into cagnotte Set lot = ? , jour = ? , heure = ? , idpartner = ? ";
	private static final String SQL_U_CAGNOTTE = "Update cagnotte Set idmise = ? ";
	
	public CagnotteDAOImpl(DAOFactory daoFactory){
		this.daofactory = daoFactory;
	}
	
	@Override
	public Long create(Cagnotte cagnotte) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		int statut = 0;
		Long idp;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion,SQL_C_CAGNOTTE, true, cagnotte.getLot(), cagnotte.getDay(), cagnotte.getHeur(), cagnotte.getPartner().getIdpartner());
			
			statut = preparedStatement.executeUpdate();
			
			/* Analyse du statut retourné par la requête d'insertion */
			if ( statut == 0 ) {
				throw new DAOException( "Échec de la création d'un versement, aucune ligne ajoutée dans la table." );
			}
			/* Récupération de l'id auto-généré par la requête d'insertion */
			
			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			if ( valeursAutoGenerees.next() ) {
			/* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
				cagnotte.setIdCagnotte( valeursAutoGenerees.getLong( 1 ) );
				idp = cagnotte.getPartner().getIdpartner();
			} else {
				 statut = 0;
				 idp = 0L;
				throw new DAOException( "Échec de la création du coupon en base, aucun ID auto-généré retourné." );
			}
			
			
			
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
			//return statut;
		}
		return idp;
	}

	@Override
	public Cagnotte find(Long coderace) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Cagnotte cagnotte = null;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_CAGNOTTE, false,coderace);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			if ( resultSet.next() ) {
				cagnotte = map( resultSet );
			}
			
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		return cagnotte;
	}

	@Override
	public int update(Cagnotte cagnotte) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		
		int statut;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion,SQL_U_CAGNOTTE, false, cagnotte.getMise());
			
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
	public void delete(Cagnotte cagnotte) throws DAOException {
		// TODO Auto-generated method stub

	}
	
	 private static Cagnotte map( ResultSet resultSet ) throws SQLException {
		    
		   Cagnotte cagnotte = new Cagnotte();
			
		   cagnotte.setDay(resultSet.getString("jour"));
		   cagnotte.setHeur(resultSet.getString("heure"));
		   cagnotte.setLot(resultSet.getString("lot"));
		   cagnotte.setJeu(resultSet.getString("jeu"));
		   //cagnotte.setPartner(resultSet.getLong("idpartner"));
		   cagnotte.setMise(resultSet.getInt("idmise"));
			
		   return cagnotte;
		}


}
