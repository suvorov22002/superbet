package superbetDAOImpl;

import static database.Bd.fermeturesSilencieuses;
import static database.Bd.initialisationRequetePreparee;
import static database.Bd.lireResultBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modele.Misep;
import superbetDAO.DAOException;
import superbetDAO.DAOFactory;
import superbetDAO.MisepDAO;

public class MisepDAOImpl implements MisepDAO {
	
	private DAOFactory daofactory;

	private UtilDAOImpl utilDao;
	
	private static final String SQL_C_MISEP = "INSERT INTO misep " + 
	      "SET idCaissier=? , heureMise=? ,sumMise=? ,dateMise=?, etatMise=?, drawnump=?,"
	      + " bonuscod=?, idmiset=?, idspin = ? " ;
	
	private static final String SQL_F_MAX_ID = "SELECT MAX(idMisep) FROM misep";
	private static final String SQL_F_MISEP_MISET = "SELECT * FROM misep WHERE idmiset = ? ";
	private static final String SQL_F_MISEP_ID = "SELECT * FROM misep WHERE idmisep = ? ";
	private static final String SQL_F_DRAW_NUM = "SELECT DISTINCT misep.drawnump from misep,effchoicep WHERE misep.idmisep = ? AND "
			+ "misep.idmisep = effchoicep.idmisep ";
	private static final String SQL_F_COMPTA = "SELECT SUM(summise) FROM  `misep` WHERE  `idCaissier` = ? AND  `heureMise` BETWEEN ? AND ? ";
	private static final String SQL_F_COUNT_MISEP = "SELECT COUNT(*) FROM misep WHERE idcaissier= ? AND"
			+ " `heureMise` BETWEEN ? AND ? ";
	private static final String SQL_F_STAT_MISEP = "SELECT * FROM misep WHERE heureMise BETWEEN ? AND ? ";
	private static final String SQL_F_ID_SPIN = "SELECT idmisep FROM misep WHERE idspin = ? ";
	private static final String SQL_F_MISEP_DRAWNUMP = "SELECT * FROM misep WHERE drawnump = ? ";
	
	
	public MisepDAOImpl(DAOFactory daoFactory) {
		this.daofactory = daoFactory;
	}
	
	@Override
	public int create(Misep misep) throws DAOException {
		// TODO Auto-generated method stub
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		int statut = 0;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion,SQL_C_MISEP, true,misep.getIdCaissier(),misep.getHeurMise(),
					misep.getSumMise(), misep.getDatMise(),misep.getEtatMise(),misep.getDrawNumP(),misep.getBonusCodeP(),misep.getIdMiset(),
					misep.getIdSpin());
			
			statut = preparedStatement.executeUpdate();
			
			/* Analyse du statut retourné par la requête d'insertion */
			if ( statut == 0 ) {
				throw new DAOException( "Échec de la création d'une mise, aucune ligne ajoutée dans la table." );
			}
			/* Récupération de l'id auto-généré par la requête d'insertion */
			
			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			if ( valeursAutoGenerees.next() ) {
			/* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
				misep.setIdmisep( valeursAutoGenerees.getLong( 1 ) );
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
	public Misep find(String login, String pass) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Misep update(Misep misep) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Misep misep) throws DAOException {
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
	public Misep searchMisesP(String idmisep) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Misep misep = new Misep();
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_MISEP_MISET, false, idmisep);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			if ( resultSet.next() ) {
				misep = map(resultSet);
			}
			
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
	
		return misep; 
	}

	@Override
	public Misep searchMisep(String idmisep) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Misep misep = new Misep();
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_MISEP_ID, false, idmisep);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			misep = map(resultSet);
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
	
		return misep; 
	}

	@Override
	public int getNumDraw(String idmisep) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String[] numDraw;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_DRAW_NUM, false, idmisep);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			numDraw = lireResultBD(resultSet);
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		int n = Integer.parseInt(numDraw[1]);
		
		return n; 
	}

	@Override
	public double getMiseRp(String caissier, String date, String date1) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String[] sum;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_COMPTA, false, caissier, date, date1);
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
	public synchronized int getIntvTicketp(String idcais, String date, String date1) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String[] sum;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_COUNT_MISEP, false, idcais, date, date1);
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
	public ArrayList<Misep> getMisep(String max, String min) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String[] last;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_STAT_MISEP, false, min, max);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			last = lireResultBD(resultSet);
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		int j,nbre;
		   
		   nbre = (last.length-1)/Integer.parseInt(last[0]);
		   ArrayList<Misep> lastsK = new ArrayList<Misep>(nbre);
		   j = 1;
		   
		   for(int i=0;i<nbre;i++){
			
			   Misep mk = new Misep();
			    j++;
				mk.setIdCaissier(last[j++]);
				mk.setHeurMise(last[j++]);
				mk.setSumMise(last[j++]);
				mk.setDatMise(last[j++]);
				j++;
				mk.setEtatMise(last[j++]);
				j++;
				mk.setIdMiset(last[j++]);
				j++;
				j++;
				j++;
				j++;
				lastsK.add(mk);
		   }
		
		return lastsK;
	}
	
	@Override
	public ArrayList<Misep> searchMisePdraw(String num) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String[] last;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_MISEP_DRAWNUMP, false, num);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			last = lireResultBD(resultSet);
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		int j,nbre;
		   
		   nbre = (last.length-1)/Integer.parseInt(last[0]);
		   ArrayList<Misep> lastsK = new ArrayList<Misep>(nbre);
		   j = 1;
		   
		   for(int i=0;i<nbre;i++){
			
			   Misep mk = new Misep();
			    j++;
				mk.setIdCaissier(last[j++]);
				mk.setHeurMise(last[j++]);
				mk.setSumMise(last[j++]);
				mk.setDatMise(last[j++]);
				j++;
				mk.setEtatMise(last[j++]);
				mk.setBonusCodeP(last[j++]);
				mk.setIdMiset(last[j++]);
				j++;
				j++;
				j++;
				j++;
				lastsK.add(mk);
		   }
		
		return lastsK; 
	}
	
	@Override
	public int searchDrawNumP(String idspin) throws DAOException {
		// TODO Auto-generated method stub
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String[] idmisep;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_ID_SPIN, false, idspin);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			idmisep = lireResultBD(resultSet);
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		int n = Integer.parseInt(idmisep[1]);
		
		return n; 
	}
	
	
	private static Misep map( ResultSet resultSet ) throws SQLException {
	    
		Misep misep = new Misep();
		misep.setIdmisep(resultSet.getLong("idmisep"));
		misep.setIdCaissier(resultSet.getString("idCaissier"));
		misep.setHeurMise(resultSet.getString("heuremise"));
		misep.setSumMise(resultSet.getString("summise"));
		misep.setDatMise(resultSet.getString("datemise"));
		misep.setEtatMise(resultSet.getString("etatmise"));
		misep.setDrawNumP(resultSet.getString("drawnump"));
		misep.setBonusCodeP(resultSet.getString("bonuscod"));
		misep.setIdMiset(resultSet.getString("idmiset"));
		misep.setIdSpin(resultSet.getString("idspin"));
		
		return misep;
	}

}
