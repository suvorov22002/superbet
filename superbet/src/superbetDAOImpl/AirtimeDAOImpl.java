package superbetDAOImpl;

import static database.Bd.fermeturesSilencieuses;
import static database.Bd.initialisationRequetePreparee;
import static database.Bd.lireResultBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import modele.Airtime;
import modele.Caissier;
import superbetDAO.AirtimeDAO;
import superbetDAO.DAOException;
import superbetDAO.DAOFactory;

public class AirtimeDAOImpl  implements AirtimeDAO{
	
	private DAOFactory daofactory;
	private static final String SQL_U_CREDIT = "Insert into airtime(date, credit, debit, caissier, libelle, balance, eta) values(?,?,?,?,?,?,?)"; 
	private static final String SQL_F_CREDIT = "Select * from airtime where caissier = ? and idairtime = (Select max(idairtime) from airtime where caissier = ? )";
	private static final String SQL_UF_CREDIT = "Select idairtime, date, sum(credit) as credit, debit, balance, caissier, libelle, eta"
			+ " from airtime where caissier = ? and libelle like 'CREDIT EN CAISSE' and date between ? and ? group by date";
	private static final String SQL_UF_S_CREDIT = "Select sum(credit) from airtime where caissier = ? and date between ? and ? ";
	private static final String SQL_C_MVT = "Insert into mouvement(caissier,mvt) values (?,?)";
	private static final String SQL_U_MVT = "Update mouvement set mvt = ? where caissier = ? ";
	private static final String SQL_F_MVT = "Select mvt from mouvement where caissier = ? ";
	private static final String SQL_F_CAIS_MVT = "Select * from mouvement where caissier = ? ";
	private static final String SQL_F_CMVT = "Select count(*) from mouvement where caissier = ? ";
	
	public AirtimeDAOImpl(DAOFactory daoFactory) {
		this.daofactory = daoFactory;
	}
	
	@Override
	public int create(Airtime airtime) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		int statut = 0;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion,SQL_U_CREDIT, true, airtime.getDate(), airtime.getCredit(), airtime.getDebit(), airtime.getCaissier(),airtime.getLibelle(), airtime.getBalance(), airtime.getEta());
			
			statut = preparedStatement.executeUpdate();
			
			/* Analyse du statut retourné par la requête d'insertion */
			if ( statut == 0 ) {
				throw new DAOException( "Echec de l'ajout de credit." );
			}
			/* Récupération de l'id auto-généré par la requête d'insertion */
			
			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			if ( valeursAutoGenerees.next() ) {
			/* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
				airtime.setId( valeursAutoGenerees.getLong( 1 ) );
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
	public Airtime find(Caissier user) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Airtime airtime = null;
		
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_CREDIT, false, user.getIdCaissier(), user.getIdCaissier()  );
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			while(resultSet.next()){
				airtime = map( resultSet );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		return airtime;
	}
	
	@Override
	public List<Airtime> find(String cais, String dat1, String dat2) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Airtime airtime = null;
		List<Airtime> list_airtime = new ArrayList();
		
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_UF_CREDIT, false, cais, dat1 ,  dat2 );
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			while(resultSet.next()){
				airtime = map( resultSet );
				list_airtime.add(airtime);
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		return list_airtime;
	}
	
	@Override
	public Airtime findByDate(Caissier user, Date date) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Airtime update(Airtime airtime) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void delete(Airtime airtime) throws DAOException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public double findCumulCredit(String cais, String dat1, String dat2) throws DAOException{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		double mvt = 0;
		String[] idmvt;
		
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_UF_S_CREDIT, false, cais, dat1, dat2);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			idmvt = lireResultBD(resultSet);
			
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		System.out.println(idmvt[1]);
		if(idmvt.length > 1 && !"null".equalsIgnoreCase(idmvt[1])) {
			mvt = Double.parseDouble(idmvt[1]);
		}
			
		return mvt;
	}
	
	@Override
	public int createMvt(Long idcaissier, double mvt) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		int statut = 0;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion,SQL_C_MVT, true, idcaissier, mvt);
			
			statut = preparedStatement.executeUpdate();
			
			/* Analyse du statut retourné par la requête d'insertion */
			if ( statut == 0 ) {
				throw new DAOException( "Echec de l'ajout de mvt." );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
		}
		
		return statut;
	}
	
	@Override
	public int updateMvt(Long idcaissier, double mvt) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		int statut = 0;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion,SQL_U_MVT, true, mvt, idcaissier);
			
			statut = preparedStatement.executeUpdate();
			
			/* Analyse du statut retourné par la requête d'insertion */
			if ( statut == 0 ) {
				throw new DAOException( "Echec de l'ajout de mvt." );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
		}
		
		return statut;
	}
	
	@Override
	public double findMvt(Long idcaissier) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		double mvt = 0;
		String[] idmvt;
		
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_MVT, false, idcaissier);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			idmvt = lireResultBD(resultSet);
			
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		if(idmvt.length > 1 && idmvt[1]!="null")
			mvt = Double.parseDouble(idmvt[1]);
		return mvt;
	}
	
	@Override
	public String[] findCaisMvt(Long idcaissier) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		double mvt = 0;
		String[] idmvt;
		
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_CAIS_MVT, false, idcaissier);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			idmvt = lireResultBD(resultSet);
			
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		return idmvt;
	}
	
	@Override
	public int findCMvt(Long idcaissier) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int mvt = 0;
		String[] idmvt;
		
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_CMVT, false, idcaissier);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			idmvt = lireResultBD(resultSet);
			
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		mvt = Integer.parseInt(idmvt[1]);
		return mvt;
	}
	
  private static Airtime map( ResultSet resultSet ) throws SQLException {
	    
	  Airtime airtime = new Airtime();
       
	    airtime.setId(resultSet.getLong( "idairtime" ));
		airtime.setBalance(resultSet.getDouble("balance"));
		airtime.setCredit(resultSet.getDouble("credit"));
		airtime.setDebit(resultSet.getDouble("debit"));
		airtime.setDate(resultSet.getDate("date"));
		airtime.setCaissier(resultSet.getLong( "caissier" ));
		airtime.setLibelle(resultSet.getString("libelle"));
		airtime.setEta(resultSet.getString("eta"));
		return airtime;
	}

}
