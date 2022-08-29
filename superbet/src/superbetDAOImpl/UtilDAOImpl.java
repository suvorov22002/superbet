package superbetDAOImpl;

import static database.Bd.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import superbetDAO.DAOException;
import superbetDAO.DAOFactory;
import superbetDAO.UtilDAO;

public class UtilDAOImpl implements UtilDAO {
	
	private DAOFactory daofactory;
	
	private static final String SQL_F_SEARCH_PARIK_ID = "SELECT * FROM paril WHERE idparil = ? ";
	private static final String SQL_F_SEARCH_PARIP_ID = "SELECT * FROM paris WHERE idparis = ? ";
	private static final String SQL_F_SEARCH_PARIK = "SELECT * FROM paril WHERE codeparil = ? "; 
	private static final String SQL_F_EXITS_BARCODE = "SELECT barcode FROM miset WHERE barcode = ?";
	private static final String SQL_F_SEARCH_PARIS = "SELECT * FROM paris WHERE codeparis = ? "; 
	private static final String SQL_F_PARAM_SERVEUR = "Select * from parametre ";
	
	
	public UtilDAOImpl(DAOFactory daoFactory){
		this.daofactory = daoFactory;
	}
	
	@Override
	public String[] searchPariL(String cpari) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String[] pari = null;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_SEARCH_PARIK, false, cpari  );
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			pari = lireResultBD(resultSet);
			
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		return pari;
	}
	
	@Override
	public String[] searchPariLById(String ipari) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String[] pari = null;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_SEARCH_PARIK_ID, false, ipari  );
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			pari = lireResultBD(resultSet);
			
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		return pari;
	}
	
	@Override
	public String[] searchPariPById(String ipari) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String[] pari = null;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_SEARCH_PARIP_ID, false, ipari  );
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			pari = lireResultBD(resultSet);
			
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		return pari;
	}
	
	public synchronized long searchBarcode(){
		// TODO Auto-generated method stub
		long lower = 9000000000L;
		long higher = 10000000000L;
		long dbl;
		String[] brcde_;
		
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			
			do{
				dbl = (long) (Math.random() * (higher-lower)) + lower;
				preparedStatement = initialisationRequetePreparee( connexion, SQL_F_EXITS_BARCODE, false, dbl);
				resultSet = preparedStatement.executeQuery();
				/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			
					brcde_ = lireResultBD(resultSet);
				
			}
			while(brcde_.length>1);
			
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		return dbl;
		  
	}
	
	public String[] lireResultBD(ResultSet res){
		ArrayList<String> _result = new ArrayList<String>();
		String[] result = null
				;
		try {
			ResultSetMetaData rsmd = res.getMetaData();
			int nbcols = rsmd.getColumnCount();
			
			_result.add(""+nbcols);
			boolean encore = res.next();
	   //---
			while(encore){
				for(int i = 0; i < nbcols; i++){
					_result.add(""+res.getString(i+1));
				}
				encore = res.next();
			}
			
			result = new String[_result.size()];
			for(int i=0;i<_result.size();i++){
				result[i] = _result.get(i);
			}
		} catch (SQLException exc) {
			exc.getMessage();
		}
		  
		return result;
	}

	@Override
	public String[] searchPariS(String cpari) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String[] pari = null;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_SEARCH_PARIS, false, cpari  );
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			pari = lireResultBD(resultSet);
			
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		return pari;
	}

	@Override
	public String[] getParamUrl() {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String[] param = null;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_PARAM_SERVEUR, false);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			param = lireResultBD(resultSet);
			
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		return param;
	}

}
