package superbetDAOImpl;

import static database.Bd.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modele.AdminTicketDto;
import modele.Misek;
import superbetDAO.DAOException;
import superbetDAO.DAOFactory;
import superbetDAO.MisekDAO;

public class MisekDAOImpl implements MisekDAO{
	
	private DAOFactory daofactory;

	private UtilDAOImpl utilDao;
	
	private static final String SQL_C_MISEK = "INSERT INTO misek " + 
	      "SET idCaissier=? , heureMise=? ,sumMise=? ,dateMise=?, etatMise=?, drawnumk=?,"
	      + " bonuscod=?, idmiset=?, idkeno = ? , xmulti = ? " ;
	
	private static final String SQL_F_MAX_ID = "SELECT MAX(idMisek) FROM misek";
	private static final String SQL_F_MAX_PARTNER_ID = "SELECT MAX(idMisek) FROM misek WHERE idmiset= ? ";
	private static final String SQL_F_MAX_ID_CODERACE = "SELECT MAX(idMisek) FROM misek Where idCaissier in ";
	private static final String SQL_F_MISEK_MISET = "SELECT * FROM misek WHERE idmiset = ? ";
	private static final String SQL_F_MISEK_ID = "SELECT * FROM misek WHERE idmisek = ? ";
	private static final String SQL_F_DRAW_NUM = "SELECT DISTINCT misek.drawnumk from misek,effchoicek WHERE misek.idmisek = ? AND "
			+ "misek.idmisek = effchoicek.idmisek ";
	private static final String SQL_F_COMPTA = "SELECT SUM(summise) FROM  `misek` WHERE  `idCaissier` = ? AND  `heureMise` BETWEEN ? AND ? AND archive = 0 ";
	private static final String SQL_U_COMPTA = "Update  `misek` Set archive = 1 WHERE  `idCaissier` = ? AND  `heureMise` BETWEEN ? AND ? AND archive = 0 ";
	private static final String SQL_F_COUNT_MISEK = "SELECT COUNT(*) FROM misek WHERE idcaissier= ? AND"
			+ " `heureMise` BETWEEN ? AND ? AND archive = 0 ";
	private static final String SQL_F_STAT_MISEK = "SELECT * FROM misek WHERE heureMise BETWEEN ? AND ? "
			+ "AND idkeno IN (SELECT idkeno from keno where coderace LIKE ? )"
			+ "order by heureMise desc";
	private static final String SQL_F_STAT_MISEK_MISET = "SELECT dateMise, barcode, typeJeu, t.summise,k.sumWin, etatmise "
			+ "FROM miset t, misek k, keno e WHERE heureMise BETWEEN ? AND ? "
			+ "AND e.coderace LIKE ? "
			+ "AND t.idmiset = k.idmiset "
			+ "AND e.idkeno = k.idkeno "
			+ "order by heureMise desc";
	private static final String SQL_F_ID_KENO = "SELECT idmisek FROM misek WHERE idkeno = ? ";
	private static final String SQL_F_MISEK_DRAWNUMK = "SELECT * FROM misek WHERE drawnumk = ? ";
	private static final String SQL_F_WAITING_BET = "Select * From misek where etatMise = 'attente' And drawnumk < ? And idCaissier in ";
	private static final String SQL_F_WAITING_KENO_BET = "Select * From misek where etatMise = 'attente' And drawnumk = ? And idCaissier in ";//ticket jou� pour un tour
	private static final String SQL_F_WAITING_KENO_BET_2 = "order by idmisek asc";
	private static final String SQL_U_MISEK = "Update misek Set etatMise = ? , sumWin = ? Where idMisek = ? And idMiset = ? ";
	private static final String SQL_F_COMPTA_CYCLE = "SELECT SUM(summise) FROM  `misek` WHERE  idmisek > ? And idmisek < ? And idCaissier in ";
	private static final String SQL_F_COMPTA_CYCLE_WIN = "SELECT SUM(sumWin) FROM  `misek` WHERE  idmisek > ? And idmisek < ? And idCaissier in ";
	
	public MisekDAOImpl(DAOFactory daoFactory) {
		this.daofactory = daoFactory;
	}
	
	@Override
	public int create(Misek misek) throws DAOException {
		
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		int statut = 0;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion,SQL_C_MISEK, true,misek.getIdCaissier(),misek.getHeurMise(),
					misek.getSumMise(), misek.getDatMise(),misek.getEtatMise(),misek.getDrawNumK(),misek.getBonusCodeK(),misek.getIdMiset(),
					misek.getIdKeno(), misek.getXmulti());
			
			statut = preparedStatement.executeUpdate();
			
			/* Analyse du statut retourné par la requête d'insertion */
			if ( statut == 0 ) {
				throw new DAOException( "Échec de la création d'une mise, aucune ligne ajoutée dans la table." );
			}
			/* Récupération de l'id auto-généré par la requête d'insertion */
			
			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			if ( valeursAutoGenerees.next() ) {
			/* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
				misek.setIdmisek( valeursAutoGenerees.getLong( 1 ) );
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
	public Misek find(String login, String pass) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Misek misek) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		
		int statut;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion,SQL_U_MISEK, false, misek.getEtatMise(), misek.getSumWin(), misek.getIdmisek(), misek.getIdMiset());
			
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
	public void delete(Misek misek) throws DAOException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int findId() throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String[] idmiset;
		int n = 0;
		
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
		
		if(idmiset.length > 1) {
			n = Integer.parseInt(idmiset[1]);
		}
		
		return n; 
	}
	

	@Override
	public int findId(String miset) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String[] idmiset;
		int n = 0;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_MAX_PARTNER_ID, false, miset);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			idmiset = lireResultBD(resultSet);
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		if(idmiset.length > 1) {
			n = Integer.parseInt(idmiset[1]);
		}
		
		return n; 
	}
	
	@Override
	public int ifindId(String caissiers) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String[] idmiset;
		int n = 0;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_MAX_ID_CODERACE+caissiers, false);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			idmiset = lireResultBD(resultSet);
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		if(idmiset.length > 1) {
			try {
				n = Integer.parseInt(idmiset[1]);
			}
			catch(NumberFormatException e) {
				n = 0;
			}
			
		}
		
		
		return n; 
	}

	@Override
	public Misek searchMisesK(String idmiset) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Misek misek = new Misek();
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_MISEK_MISET, false, idmiset);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			if ( resultSet.next() ) {
				misek = map(resultSet);
			}
			
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
	
		return misek; 
	}
	
	@Override
	public Misek searchMiseK(String idmisek) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Misek misek = null;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_MISEK_ID, false, idmisek);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			while ( resultSet.next() ) {
				misek = map(resultSet);
			}
			
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
	
		return misek; 
	}
	
	@Override
	public int getNumDraw(String idmisek) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String[] numDraw;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_DRAW_NUM, false, idmisek);
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
	public synchronized double getMiseRK(String caissier, String date, String date1) throws DAOException {
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
	public int updateMiseRK(String caissier, String date, String date1) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		
		int statut;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_U_COMPTA, false, caissier, date, date1);
			
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
	public synchronized double getMiseKCycle(long misek, long mise, String caissiers) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String[] sum;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_COMPTA_CYCLE+caissiers, false, misek, mise);
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
	public synchronized double getMiseKCycleWin(long misek, long mise, String caissiers) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String[] sum;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_COMPTA_CYCLE_WIN+caissiers, false, misek, mise);
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
	public synchronized int getIntvTicketK(String idcais, String date, String date1) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String[] sum;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_COUNT_MISEK, false, idcais, date, date1);
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
	public synchronized ArrayList<Misek> getMisek(String max, String min, String coderace) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String[] last;
		Misek misek;
		ArrayList<Misek> list_barcode = new ArrayList<Misek>();
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_STAT_MISEK, false, min, max, coderace);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			//last = lireResultBD(resultSet);
			while ( resultSet.next() ) {
				misek = map(resultSet);
				list_barcode.add(misek);
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
//		int j,nbre;
//		   
//		   nbre = (last.length-1)/Integer.parseInt(last[0]);
//		   ArrayList<Misek> lastsK = new ArrayList<Misek>(nbre);
//		   j = 1;
//		   
//		   for(int i=0;i<nbre;i++){
//			
//			   Misek mk = new Misek();
//			    j++;
//				mk.setIdCaissier(last[j++]);
//				mk.setHeurMise(last[j++]);
//				mk.setSumMise(last[j++]);
//				mk.setDatMise(last[j++]);
//				j++;
//				mk.setEtatMise(last[j++]);
//				j++;
//				mk.setIdMiset(last[j++]);
//				j++;
//				j++;
//				j++;
//				mk.setXmulti(Integer.parseInt(last[j++]));
//				lastsK.add(mk);
//		   }
		
		return list_barcode;
	}
	
	@Override
	public synchronized ArrayList<AdminTicketDto> getMisekt(String max, String min, String coderace) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String[] last;
		
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_STAT_MISEK_MISET, false, min, max, coderace);
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
		   ArrayList<AdminTicketDto> lastsK = new ArrayList<AdminTicketDto>(nbre);
		   j = 1;
		   
		   for(int i=0;i<nbre;i++){
			
			    AdminTicketDto mkt = new AdminTicketDto();
			    mkt.setDatMise(last[j++]);
				mkt.setBarcode(last[j++]);
				mkt.setTypeJeu(last[j++]);
				mkt.setSummise(Double.parseDouble(last[j++]));
				mkt.setSumwin(Double.parseDouble(last[j++]));
				mkt.setEtatMise(last[j++]);
				lastsK.add(mkt);
		   }
		
		return lastsK;
	}
	
	@Override
	public int searchDrawNumK(String idkeno) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String[] idmisek;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_ID_KENO, false, idkeno);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			idmisek = lireResultBD(resultSet);
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		int n = Integer.parseInt(idmisek[1]);
		
		return n; 
	}

	@Override
	public ArrayList<Misek> searchMiseKdraw(String num) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String[] last;
		Misek misek;
		ArrayList<Misek> list_barcode = new ArrayList<Misek>();
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_MISEK_DRAWNUMK, false, num);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			//last = lireResultBD(resultSet);
			while ( resultSet.next() ) {
				misek = map(resultSet);
				list_barcode.add(misek);
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
//		int j,nbre;
//		   
//		   nbre = (last.length-1)/Integer.parseInt(last[0]);
//		   ArrayList<Misek> lastsK = new ArrayList<Misek>(nbre);
//		   j = 1;
//		   
//		   for(int i=0;i<nbre;i++){
//			
//			   Misek mk = new Misek();
//			    j++;
//				mk.setIdCaissier(last[j++]);
//				mk.setHeurMise(last[j++]);
//				mk.setSumMise(last[j++]);
//				mk.setDatMise(last[j++]);
//				j++;
//				mk.setEtatMise(last[j++]);
//				mk.setBonusCodeK(last[j++]);
//				mk.setIdMiset(last[j++]);
//				j++;
//				j++;
//				j++;
//				mk.setXmulti(Integer.parseInt(last[j++]));
//				lastsK.add(mk);
//		   }
		
		return list_barcode; 
	}

	@Override
	public ArrayList<Misek> searchWaitingBet(String IN, int drawnum) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Misek misek;
		ArrayList<Misek> list_barcode = new ArrayList<Misek>();
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_WAITING_BET+IN, false, drawnum);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			while ( resultSet.next() ) {
				misek = map(resultSet);
				list_barcode.add(misek);
			}
			
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		return list_barcode; 
	}
	
	@Override
	public ArrayList<Misek> searchWaitingKenoBet(String IN, int drawnum) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Misek misek;
		ArrayList<Misek> list_barcode = new ArrayList<Misek>();
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_WAITING_KENO_BET+IN+SQL_F_WAITING_KENO_BET_2,false,drawnum);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			while ( resultSet.next() ) {
				misek = map(resultSet);
				list_barcode.add(misek);
			}
			
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		return list_barcode; 
	}

	
	private static Misek map( ResultSet resultSet ) throws SQLException {
	    
		Misek misek = new Misek();
		misek.setIdmisek(resultSet.getLong("idmisek"));
		misek.setIdCaissier(resultSet.getString("idCaissier"));
		misek.setHeurMise(resultSet.getString("heuremise"));
		misek.setSumMise(resultSet.getString("summise"));
		misek.setDatMise(resultSet.getString("datemise"));
		misek.setEtatMise(resultSet.getString("etatmise"));
		misek.setDrawNumK(resultSet.getString("drawnumk"));
		misek.setBonusCodeK(resultSet.getString("bonuscod"));
		misek.setIdMiset(resultSet.getString("idmiset"));
		misek.setIdKeno(resultSet.getString("idkeno"));
		misek.setSumWin(resultSet.getDouble("sumWin"));
		misek.setXmulti(resultSet.getInt("xmulti"));
		
		
		return misek;
	}

}
