package superbetDAOImpl;

import static database.Bd.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import modele.Miset;
import superbetDAO.DAOException;
import superbetDAO.DAOFactory;
import superbetDAO.MisetDAO;

public class MisetDAOImpl implements MisetDAO{
	
	private DAOFactory daofactory;
	
	private UtilDAOImpl utilDao;
	
	private static final String SQL_C_MISET = "INSERT INTO miset SET typeJeu=?, barcode=?, summise=? "; 
	private static final String SQL_F_MAX_ID = "SELECT MAX(idMiset) FROM miset";
	private static final String SQL_F_BARCODE = "SELECT * FROM miset WHERE barcode = ? ";
	private static final String SQL_F_BARC_MISET = "SELECT * FROM miset WHERE barcode = ? AND idmiset = ? ";
	private static final String SQL_F_MISET_ID = "SELECT * FROM miset WHERE idmiset = ? ";
	private static final String SQL_F_FREE_SLIP_ID = " FROM freeslip where idpartner = ? ";
	private static final String SQL_U_FREE = " + ? where idpartner = ? ";
	private static final String SQL_C_FREE_SLIP = "Insert into freeslip set idpartner = ? ";
	
	
	public MisetDAOImpl(DAOFactory daoFactory) {
		this.daofactory = daoFactory;
	}
	
	@Override
	public int create(Miset miset) throws IllegalArgumentException, DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		int statut = 0;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion,SQL_C_MISET, true, miset.getTypeJeu(), miset.getBarcode(),
			  miset.getSummise());
			
			statut = preparedStatement.executeUpdate();
			
			/* Analyse du statut retourné par la requête d'insertion */
			if ( statut == 0 ) {
				throw new DAOException( "Échec de la création du coupon, aucune ligne ajoutée dans la table." );
			}
			/* Récupération de l'id auto-généré par la requête d'insertion */
			
			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			if ( valeursAutoGenerees.next() ) {
			/* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
				miset.setIdMiset( valeursAutoGenerees.getLong( 1 ) );
			} else {
				 statut = 0;
				throw new DAOException( "Échec de la création du coupon en base, aucun ID auto-généré retourné." );
			}
			
			
			
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
			//return statut;
		}
		return statut;
	}

	@Override
	public Miset find(String login, String pass) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Miset update(Miset Miset) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Miset Miset) throws DAOException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int findId() throws DAOException {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String[] idmiset;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_MAX_ID, false);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			idmiset = lireResultBD(resultSet);
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		int n = Integer.parseInt(idmiset[1]);
		
		return n;
	}
	
	@Override
	public Miset searchTicketT(String barcode) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Miset miset = null;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_BARCODE, false, barcode);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			if ( resultSet.next()) {
			
				miset = map(resultSet);
			}
			
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		return miset;
	}
	
	@Override
	public Miset searchTicketTAlrPay(String barcode, Long id_miset) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Miset miset = new Miset();
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_BARC_MISET, false, barcode, id_miset);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			 miset = map(resultSet);
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		return miset;
	}
	
	@Override
	public Miset findById(String idmiset) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Miset miset = new Miset();
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_MISET_ID, false, idmiset);
			resultSet = preparedStatement.executeQuery();
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			if ( resultSet.next() ) {
			 miset = map(resultSet);
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		return miset;
	}
	
	@Override
	public double findFreeSlipByPartner(String game, Long coderace) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String[] idmiset;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, "Select "+game+SQL_F_FREE_SLIP_ID, false, coderace);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
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
	public int updateFree(String game, double step, Long coderace) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		
		int statut;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion,"Update freeslip Set "+game+" = "+game+SQL_U_FREE, false, step, coderace);
			
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
	public int createFree(Long idpartner) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		int statut = 0;
		Long idp;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion,SQL_C_FREE_SLIP, true, idpartner);
			
			statut = preparedStatement.executeUpdate();
			
			/* Analyse du statut retourné par la requête d'insertion */
			if ( statut == 0 ) {
				throw new DAOException( "Échec de la création d'un versement, aucune ligne ajoutée dans la table." );
			}
			/* Récupération de l'id auto-généré par la requête d'insertion */
			
			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			if ( !valeursAutoGenerees.next() ) {
			/* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
				 statut = 0;
				throw new DAOException( "Échec de la création du coupon en base, aucun ID auto-généré retourné." );
			}
			
			
			
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
			//return statut;
		}
		return statut;

	}

	private static Miset map( ResultSet resultSet ) throws SQLException {
		Miset miset = new Miset();
			miset.setIdMiset(resultSet.getLong("idmiset"));
			miset.setTypeJeu(resultSet.getString("typejeu"));
			miset.setBarcode(resultSet.getString("barcode"));
			miset.setSummise(resultSet.getInt("summise"));
	
		return miset;
	}

}
