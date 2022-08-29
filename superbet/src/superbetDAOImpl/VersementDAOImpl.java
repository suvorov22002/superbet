package superbetDAOImpl;

import static database.Bd.fermeturesSilencieuses;
import static database.Bd.initialisationRequetePreparee;
import static database.Bd.lireResultBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modele.Versement;
import superbetDAO.DAOException;
import superbetDAO.DAOFactory;
import superbetDAO.VersementDAO;

public class VersementDAOImpl implements VersementDAO {
	
	private DAOFactory daofactory;
	
	private static final String SQL_C_VERSEMENT = "INSERT INTO versement SET idCaissier=? , mtantvers=? ,heurVers=? "
			+ ",dateVers=? ,typeVers = ? , idmiset = ? "; 
	private static final String SQL_F_VERSEMENT = "SELECT * FROM versement"; 
	private static final String SQL_F_VERS_MISET = "SELECT * FROM versement WHERE idmiset = ? "; 
	private static final String SQL_F_COMPTA = "SELECT SUM(mtantvers) FROM versement WHERE heurvers BETWEEN ? AND ? "
			+ "AND idcaissier = ? AND archive = 0 ";
	private static final String SQL_U_COMPTA = "Update versement Set archive = 1 WHERE heurvers BETWEEN ? AND ? "
			+ "AND idcaissier = ? AND archive = 0 ";
	private static final String SQL_F_COUNT_VERS = "SELECT COUNT(*) FROM versement WHERE idcaissier=? AND"
			+ " heurvers BETWEEN ? AND ? AND archive = 0 ";
	private static final String SQL_F_STAT_VERS = "SELECT * FROM versement WHERE typeVers = ? AND heurVers BETWEEN ? AND ? ";
	
	public VersementDAOImpl(DAOFactory daoFactory){
		this.daofactory = daoFactory;
	}
	
	@Override
	public int create(Versement versement) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		int statut = 0;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion,SQL_C_VERSEMENT, true, versement.getIdcaissier(), versement.getMontant(), versement.getHeureV(),
			  versement.getDatV(), versement.getTypeVers(), versement.getMise());
			
			statut = preparedStatement.executeUpdate();
			
			/* Analyse du statut retourné par la requête d'insertion */
			if ( statut == 0 ) {
				throw new DAOException( "Échec de la création d'un versement, aucune ligne ajoutée dans la table." );
			}
			/* Récupération de l'id auto-généré par la requête d'insertion */
			
			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			if ( valeursAutoGenerees.next() ) {
			/* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
				versement.setIdvers( valeursAutoGenerees.getLong( 1 ) );
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
	public Versement find(String idmiset) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Versement update(Versement versement) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Versement versement) throws DAOException {
		// TODO Auto-generated method stub

	}
	
	@Override
	public synchronized Versement find_vers_miset(String idmiset) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Versement verst = null;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_VERS_MISET, false, idmiset);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			if ( resultSet.next() ) {
				verst = map(resultSet);
			}
			 
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		return verst;
	}
	
	@Override
	public double getVersementD(String date, String caissier, String date1) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String[] sum;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_COMPTA, false, date, date1, caissier);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			sum = lireResultBD(resultSet);
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		double n = 0;
		if(sum.length > 1){
			
			if(!sum[1].equalsIgnoreCase("null")){
				n = Double.parseDouble(sum[1]);
			}
			else{
				n = 0;
			}
		}
		
		
		return n;
	}
	
	@Override
	public int updateVersementD(String date, String caissier, String date1) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		
		int statut;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_U_COMPTA, false, date, date1, caissier);
			
			statut = preparedStatement.executeUpdate();
			
			/* Analyse du statut retourné par la requête d'insertion */
			if ( statut == 0 ) {
				throw new DAOException( "Échec de la mise a jour du versement, aucune ligne ajoutée dans la table." );
			}
			
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
		}
		
		return statut;
	}
	
	@Override
	public synchronized int getPayTicket(String idcais, String date, String date1) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String[] sum;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_COUNT_VERS, false, idcais, date, date1);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			sum = lireResultBD(resultSet);
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		int n = 0;
		if(sum.length > 1){
			n = Integer.parseInt(sum[1]);
		}
		
		
		return n;
	}
	
	@Override
	public ArrayList<Versement> getVersementk(String min, String max, String jeu) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String[] last;
		ArrayList<Versement> lastsK = new ArrayList<Versement>();
		Versement versk;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion,  SQL_F_STAT_VERS, false, jeu, min, max);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			//last = lireResultBD(resultSet);
			while ( resultSet.next() ) {
				versk = map(resultSet);
				lastsK.add(versk);
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
//		int j,nbre;
//		   
//		   nbre = (last.length-1)/Integer.parseInt(last[0]);
//		   ArrayList<Versement> lastsK = new ArrayList<Versement>(nbre);
//		   j = 1;
//		   
//		   for(int i=0;i<nbre;i++){
//			
//			   Versement v = new Versement();
//				j++;
//				v.setCaissier(last[j++]);
//				v.setMontant(Double.parseDouble(last[j++]));
//				v.setHeureV(last[j++]);
//				v.setDatV(last[j++]);
//				v.setTypeVers(last[j++]);
//				v.setMise(Long.parseLong(last[j++]));
//				j++;
//				lastsK.add(v);
//		   }
		
		return lastsK;
	}

	

	
	private static Versement map( ResultSet resultSet ) throws SQLException {
		Versement verst = new Versement();
		
		verst.setIdvers(resultSet.getLong("idversement"));
		verst.setIdcaissier(resultSet.getString("idcaissier"));
		verst.setHeureV(resultSet.getString("heurvers"));
		verst.setMontant(resultSet.getDouble("mtantvers"));
		verst.setTypeVers(resultSet.getString("typevers"));
		verst.setDatV(resultSet.getString("datevers"));
		
		return verst;
	}

}
