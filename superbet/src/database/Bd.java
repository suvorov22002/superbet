package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Bd {
	
	/*
	* Initialise la requête préparée basée sur la connexion passée en argument,
	* avec la requête SQL et les objets donnés.
	*/
	public static PreparedStatement initialisationRequetePreparee(Connection connexion, String sql, boolean returnGeneratedKeys,
			Object... objets ) throws SQLException {
			
		PreparedStatement preparedStatement = connexion.prepareStatement( sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS );
		
		for ( int i = 0; i < objets.length; i++ ) {
			preparedStatement.setObject( i + 1, objets[i] );
		}
		return preparedStatement;
	}
	
	
	/* Fermeture silencieuse du resultset */
	public static void fermetureSilencieuse( ResultSet resultSet ) {
		if ( resultSet != null ) {
			try {
				resultSet.close();
			} catch ( SQLException e ) {
				System.out.println( "Échec de la fermeture du ResultSet: " + e.getMessage() );
			}
		}
	}
	
	/* Fermeture silencieuse du statement */
	public static void fermetureSilencieuse( Statement statement ) {
		if ( statement != null ) {
			try {
				statement.close();
			} catch ( SQLException e ) {
				System.out.println( "Échec de la fermeture du Statement: " + e.getMessage() );
			}
		}
	}
	
	/* Fermeture silencieuse de la connexion */
	public static void fermetureSilencieuse( Connection connexion ) {
		if ( connexion != null ) {
			try {
				connexion.close();
			} catch ( SQLException e ) {
				System.out.println( "Échec de la fermeture de la connexion : " + e.getMessage() );
			}
		}
	}
	
	/* Fermetures silencieuses du statement et de la connexion */
	public static void fermeturesSilencieuses( Statement statement, Connection connexion ) {
		fermetureSilencieuse( statement );
		fermetureSilencieuse( connexion );
	}
	
	/* Fermetures silencieuses du resultset, du statement et de la
	connexion */
	public static void fermeturesSilencieuses( ResultSet resultSet, Statement statement, Connection connexion ) {
		fermetureSilencieuse( resultSet );
		fermetureSilencieuse( statement );
		fermetureSilencieuse( connexion );
	}
	
	public static String[] lireResultBD(ResultSet res){
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
	
}
