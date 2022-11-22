package superbetDAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static database.Bd.*;
import modele.Caissier;
import modele.Partner;
import superbetDAO.CaissierDAO;
import superbetDAO.DAOException;
import superbetDAO.DAOFactory;

public class CaissierDAOImpl implements CaissierDAO {
	
	private DAOFactory daofactory;
	
	private static final String SQL_F_LOGIN_PASS = "SELECT * FROM caissier WHERE loginC=? "; 
	private static final String SQL_F_LOGIN = "SELECT * FROM caissier WHERE loginC=? "; 
	private static final String SQL_C_CAISSIER = "INSERT INTO caissier (idprofil, nomC, loginC, mdpC, coderace, idCaissier) VALUES"
			+ "(?,?,?,?,?,?)";
	//private static final String SQL_C_CAISSIER = "INSERT INTO caissier (idprofil, nomC, loginC, mdpC, coderace, idgroupe) VALUES"
	//		+ "(?,?,?,?,?,?)";
    private static final String SQL_U_CAISSIER_STATE = "UPDATE caissier set statut=? WHERE loginC=? ";  
    private static final String SQL_F_LOGIN_PARTNER = "SELECT * FROM caissier WHERE coderace  = ?  "; 
	private static final String SQL_F_LOGIN_CODERACE = "Select * from caissier where loginC=? and coderace = ?";
	private static final String SQL_F_ID = "Select * from caissier where idCaissier = ? ";
	
	public CaissierDAOImpl(DAOFactory daoFactory){
		this.daofactory = daoFactory;
	}

	@Override
	public int create(Caissier caissier) throws IllegalArgumentException, DAOException {
		// TODO Auto-generated method stub
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		int statut = 0;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
//			preparedStatement = initialisationRequetePreparee( connexion,SQL_C_CAISSIER, true, caissier.getProfil(), caissier.getNomc(),
//			  caissier.getLoginc(), caissier.getMdpc(), caissier.getPartner(), caissier.getGrpe());
			preparedStatement = initialisationRequetePreparee( connexion,SQL_C_CAISSIER, true, caissier.getProfil(), caissier.getNomc(),
					  caissier.getLoginc(), caissier.getMdpc(), caissier.getPartner(), caissier.getIdCaissier());
			
			statut = preparedStatement.executeUpdate();
			
			/* Analyse du statut retourné par la requête d'insertion */
			if ( statut == 0 ) {
				throw new DAOException( "Échec de la création du caissier, aucune ligne ajoutée dans la table." );
			}
			/* Récupération de l'id auto-généré par la requête d'insertion */
			
			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			if ( valeursAutoGenerees.next() ) {
			/* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
				caissier.setIdCaissier( valeursAutoGenerees.getLong( 1 ) );
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
	public Caissier find(String login, String pass) throws DAOException {
		// TODO Auto-generated method stub
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Caissier caissier = null;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_LOGIN_PASS, false, login  );
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			if ( resultSet.next() ) {
				caissier = map( resultSet );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		return caissier;
	}
	
	@Override
	public Caissier findByLogin(String login) throws DAOException {
		// TODO Auto-generated method stub
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Caissier caissier = null;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_LOGIN, false, login  );
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			if ( resultSet.next() ) {
				caissier = map( resultSet );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		return caissier;
	}
	
	@Override
	public Caissier findByLoginIdPartner(String login, String coderace) throws DAOException {
		// TODO Auto-generated method stub
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Caissier caissier = null;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_LOGIN_CODERACE, false, login, coderace  );
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			if ( resultSet.next() ) {
				caissier = map( resultSet );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		return caissier;
	}

	
	@Override
	public Caissier update(Caissier caissier) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Caissier caissier) throws DAOException {
		// TODO Auto-generated method stub

	}
	
	@Override
	public int updateState(Caissier caissier) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		
		int statut;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion,SQL_U_CAISSIER_STATE, false, caissier.getStatut(), caissier.getLoginc());
			
			statut = preparedStatement.executeUpdate();
			
			/* Analyse du statut retourné par la requête d'insertion */
			if ( statut == 0 ) {
				throw new DAOException( "Échec de la mise à jour du caissier, aucune ligne ajoutée dans la table." );
			}
			
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
		}
		
		return statut;
	}
	
	@Override
	public ArrayList<Caissier> findByPartner(String coderace) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Caissier caissier = null;
		ArrayList<Caissier> lastsK = new ArrayList<Caissier>();
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_LOGIN_PARTNER, false, coderace  );
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			while(resultSet.next()){
				caissier = map( resultSet );
				 lastsK.add(caissier);
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		return lastsK;
	}
	
	@Override
	public Caissier findById(Long id) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Caissier caissier = null;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_ID, false, id  );
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			if ( resultSet.next() ) {
				caissier = map( resultSet );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		return caissier;
	}

	
	/*
	* Simple méthode utilitaire permettant de faire la correspondance
	(le
	* mapping) entre une ligne issue de la table desCaissiers (un
	* ResultSet) et un beanCaissier.
	*/
	private static Caissier map( ResultSet resultSet ) throws SQLException {
	    
		Caissier caissier = new Caissier();
		caissier.setIdCaissier( resultSet.getLong( "idCaissier" ) );
		caissier.setProfil(resultSet.getLong( "idProfil" ));
		caissier.setNomc(resultSet.getString("nomc"));
		caissier.setLoginc(resultSet.getString("loginC"));
		caissier.setMdpc(resultSet.getString( "mdpc" ));
		caissier.setPartner(resultSet.getString("coderace"));
		caissier.setStatut(resultSet.getString( "statut" ));
		caissier.setGrpe(resultSet.getInt("idgroupe"));
		
		return caissier;
	}
}
