package superbetDAOImpl;

import static database.Bd.fermeturesSilencieuses;
import static database.Bd.initialisationRequetePreparee;
import static database.Bd.lireResultBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modele.Keno;
import modele.Misek;
import superbetDAO.DAOException;
import superbetDAO.DAOFactory;
import superbetDAO.KenoDAO;

public class KenoDAOImpl implements KenoDAO {
	
	private DAOFactory daofactory;
	
	private static final String SQL_C_KENO = "INSERT INTO keno SET idsalle=1  ,drawnumk=? , coderace=? ,drawnumbk = ? , multiplicateur = ?, heuretirage = ? "; 
	private static final String SQL_U_KENO = "UPDATE keno SET drawnumbk=? ,"
	      + " multiplicateur = ? , heuretirage = ? "
	      + "WHERE drawnumk = ? AND coderace = ? "; 
	private static final String SQL_F_BONUS_AMOUNT_KENO = "select bonuskamount from keno"; 
	private static final String SQL_U_BONUS_AMOUNT_KENO = "update keno set bonuskamount=? where drawnumk=?";
	private static final String SQL_F_MAX_DRAW = "SELECT * from keno WHERE coderace= ? and drawnumK = (SELECT MAX(drawnumK) FROM keno WHERE coderace= ? )";
	private static final String SQL_F_MAX_DRAW_NUM = "SELECT * from keno WHERE coderace= ? and drawnumK = ? ";
	private static final String SQL_F_SINGLE_DRAW = "SELECT * from keno WHERE coderace= ?";
	private static final String SQL_F_MAX_PREVIOUS_DRAW = "SELECT started,drawnumk from keno WHERE coderace= ? and drawnumK = "
			+ "(SELECT MAX(drawnumK) FROM keno WHERE coderace= ? and multiplicateur!='0')";
	private static final String SQL_F_PREVIOUS_FIVE_DRAW = "SELECT heureTirage,multiplicateur,drawnumbK,drawnumK FROM keno"
			+ " WHERE multiplicateur != '0' AND coderace=? "
			+ "ORDER BY drawnumk DESC "
			+ "LIMIT 0,5 ";
	private static final String SQL_F_PREVIOUS_TWEL_DRAW = "SELECT * FROM keno"
			+ " WHERE multiplicateur != '0' AND coderace=? "
			+ "ORDER BY drawnumk DESC "
			+ "LIMIT 0,12 ";
	private static final String SQL_F_MAX_ID = "SELECT * from keno WHERE idKeno = (SELECT MAX(idKeno) FROM keno where coderace = ?)";
	private static final String SQL_F_DRAW = "SELECT * from keno WHERE drawnumK = ? ";
	private static final String SQL_U_DRAW_END = "UPDATE keno SET started=1 WHERE drawnumk=? ";
	private static final String SQL_F_ID = "SELECT idKeno FROM keno where coderace = ? AND drawnumk = ? ";
	private static final String SQL_U_KENO_BONUS = "UPDATE keno SET bonusKamount = ? , bonuskcod = ? WHERE idkeno = ? " ; 
	private static final String SQL_F_PREVIOUS_BONUS = "Select coderace, heureTirage, bonusKCod, bonusKamount from keno where bonusKcod !=0 and coderace = ? order by idKeno desc limit 0,3";	
	private static final String SQL_F_SUM_BONUS = "SELECT sum( bonuskamount ) FROM keno WHERE idkeno > ? AND idkeno <= ? and coderace = ? ";
	private static final String SQL_F_PREVIOUS_H_DRAW = "SELECT * FROM keno"
			+ " WHERE multiplicateur != '0' AND coderace=? "
			+ "ORDER BY drawnumk DESC "
			+ "LIMIT 0,100 ";
	private static final String SQL_D_KENO = "Delete From Keno Where coderace = ? ";

	
	public KenoDAOImpl(DAOFactory daoFactory) {
		this.daofactory = daoFactory;
	}

	@Override
	public synchronized int create(Keno keno) throws DAOException {
		
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		int statut = 0;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion,SQL_C_KENO, true, keno.getDrawnumK(), keno.getCoderace(), keno.getDrawnumbK(), keno.getMultiplicateur(), keno.getHeureTirage());
			
			statut = preparedStatement.executeUpdate();
			
			/* Analyse du statut retourné par la requête d'insertion */
			if ( statut == 0 ) {
				throw new DAOException( "Échec de la création d'un tirage, aucune ligne ajoutée dans la table." );
			}
			/* Récupération de l'id auto-généré par la requête d'insertion */
			
			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			if ( valeursAutoGenerees.next() ) {
			/* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
				keno.setIdKeno( valeursAutoGenerees.getLong( 1 ) );
			} else {
				statut = 0;
				throw new DAOException( "Échec de la création du coupon en base, aucun ID auto-généré retourné." );
			}
		} catch ( SQLException | DAOException e ) {
			//throw new DAOException( e );
			System.out.println("KENO DAO "+e);
			//this.update(keno);
		} finally {
			fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
		}
		
		return statut;
	}

	@Override
	public Keno find(String drawNumK) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Keno keno) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		
		int statut;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion,SQL_U_KENO, false, keno.getDrawnumbK(),
					keno.getMultiplicateur(), keno.getHeureTirage(),keno.getDrawnumK(), keno.getCoderace());
			
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
	public boolean delete(String coderace) throws DAOException {
		
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		int res = 0;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_D_KENO, false, coderace);
		    res = preparedStatement.executeUpdate();
			
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( preparedStatement, connexion );
		}
		
		return (res > 0) ? true : false;
		

	}

	@Override
	public synchronized double findBonusAmount() throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String[] idmiset;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_BONUS_AMOUNT_KENO, false);
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
	public double findTotalBonusAmount(int id1, int id2, String coderace) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String[] idmiset;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_SUM_BONUS, false, id1, id2, coderace);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			idmiset = lireResultBD(resultSet);
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		double n;
		if(id1 == id2) n = 0;
		else
		   n = Double.parseDouble(idmiset[1]);
		
		return n;
	}

	@Override
	public synchronized int updateBonus(double bonus, int drawnumber) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		
		int statut;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion,SQL_U_BONUS_AMOUNT_KENO, false, bonus, drawnumber);
			
			statut = preparedStatement.executeUpdate();
			
			/* Analyse du statut retourné par la requête d'insertion */
			if ( statut == 0 ) {
				throw new DAOException( "Échec de la mise à jour du bonus, aucune ligne ajoutée dans la table." );
			}
			
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
		}
		
		return statut;
	}

	@Override
	public synchronized Keno find_Max_draw(String coderace) throws DAOException {
		
		// TODO Auto-generated method stub
				Connection connexion = null;
				PreparedStatement preparedStatement = null;
				ResultSet resultSet = null;
				Keno keno = null;
				
				try {
					/* Récupération d'une connexion depuis la Factory */
					connexion = daofactory.getConnection();
					//System.out.println("connexion: "+connexion);
					preparedStatement = initialisationRequetePreparee( connexion, SQL_F_MAX_DRAW, false, coderace, coderace);
					resultSet = preparedStatement.executeQuery();
					//System.out.println("connexion: "+resultSet.getFetchSize());
					/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
					if ( resultSet.next() ) {
						keno = map( resultSet );
					}
				} catch ( SQLException e ) {
					throw new DAOException( e );
				} finally {
					fermeturesSilencieuses( resultSet, preparedStatement, connexion );
				}
				
				return keno;
	   }
	
	@Override
	public synchronized Keno find_Max_draw_num(String coderace, String num) throws DAOException {
		
		// TODO Auto-generated method stub
				Connection connexion = null;
				PreparedStatement preparedStatement = null;
				ResultSet resultSet = null;
				Keno keno = null;
				
				try {
					/* Récupération d'une connexion depuis la Factory */
					connexion = daofactory.getConnection();
					preparedStatement = initialisationRequetePreparee( connexion, SQL_F_MAX_DRAW_NUM, false, coderace, num);
					resultSet = preparedStatement.executeQuery();
					
					/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
					if ( resultSet.next() ) {
						keno = map( resultSet );
					}
				} catch ( SQLException e ) {
					throw new DAOException( e );
				} finally {
					fermeturesSilencieuses( resultSet, preparedStatement, connexion );
				}
				
				return keno;
	   }
	
	@Override
	public synchronized Keno find_Single_draw(String coderace) throws DAOException {
		
		// TODO Auto-generated method stub
				Connection connexion = null;
				PreparedStatement preparedStatement = null;
				ResultSet resultSet = null;
				Keno keno = null;
				
				try {
					/* Récupération d'une connexion depuis la Factory */
					connexion = daofactory.getConnection();
					preparedStatement = initialisationRequetePreparee( connexion, SQL_F_SINGLE_DRAW, false, coderace);
					resultSet = preparedStatement.executeQuery();
					
					/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
					if ( resultSet.next() ) {
						keno = map( resultSet );
					}
				} catch ( SQLException e ) {
					throw new DAOException( e );
				} finally {
					fermeturesSilencieuses( resultSet, preparedStatement, connexion );
				}
				
				return keno;
	   }
	
		@Override
		public synchronized String[] getLastKdraw(String coderace) {
			Connection connexion = null;
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			String[] last;
			
			try {
				/* Récupération d'une connexion depuis la Factory */
				connexion = daofactory.getConnection();
				preparedStatement = initialisationRequetePreparee( connexion, SQL_F_PREVIOUS_FIVE_DRAW, false, coderace);
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
			   ArrayList<Keno> lastsK = new ArrayList<Keno>(nbre);
			   j = last.length-1;
			   
			   for(int i=0;i<nbre;i++){
				
				   Keno keno = new Keno();
				   
				   
				   keno.setStarted(last[j--]);
				   keno.setDrawnumK(last[j--]);
				   keno.setDrawnumbK(last[j--]);
				   keno.setHeureTirage(last[j--]);
				 
				   lastsK.add(keno);
			   }
			
			return last;
		}
		
		@Override
		public ArrayList<Keno> find_Last_draw(String coderace) throws DAOException {
			
			Connection connexion = null;
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			Keno ken;
			ArrayList<Keno> list_keno = new ArrayList<Keno>();
			
			try {
				/* Récupération d'une connexion depuis la Factory */
				connexion = daofactory.getConnection();
				preparedStatement = initialisationRequetePreparee( connexion, SQL_F_PREVIOUS_TWEL_DRAW, false, coderace);
				resultSet = preparedStatement.executeQuery();
				
				/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
				while ( resultSet.next() ) {
					ken = map(resultSet);
					list_keno.add(ken);
				}
			} catch ( SQLException e ) {
				throw new DAOException( e );
			} finally {
				fermeturesSilencieuses( resultSet, preparedStatement, connexion );
			}
			
			return list_keno;
		}
		
		@Override
		public synchronized Keno getMaxIdDrawK(String coderace) throws DAOException {
			Connection connexion = null;
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			Keno keno = null;
			
			try {
				/* Récupération d'une connexion depuis la Factory */
				connexion = daofactory.getConnection();
				preparedStatement = initialisationRequetePreparee( connexion, SQL_F_MAX_ID, false, coderace);
				resultSet = preparedStatement.executeQuery();
				
				/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
				if ( resultSet.next() ) {
					keno = map( resultSet );
				}
			} catch ( SQLException e ) {
				throw new DAOException( e );
			} finally {
				fermeturesSilencieuses( resultSet, preparedStatement, connexion );
			}
			
			
			return keno;
		}
		
		@Override
		public Keno searchResultK(String drawnum) throws DAOException {
			Connection connexion = null;
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			Keno keno = null;
			
			try {
				/* Récupération d'une connexion depuis la Factory */
				connexion = daofactory.getConnection();
				preparedStatement = initialisationRequetePreparee( connexion, SQL_F_DRAW, false, drawnum);
				resultSet = preparedStatement.executeQuery();
				
				/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
				if ( resultSet.next() ) {
					keno = map( resultSet );
				}
			} catch ( SQLException e ) {
				throw new DAOException( e );
			} finally {
				fermeturesSilencieuses( resultSet, preparedStatement, connexion );
			}
			
			
			return keno;
		}
		
		@Override
		public int updateDrawEnd(int drawnumber) throws DAOException {
			Connection connexion = null;
			PreparedStatement preparedStatement = null;
			ResultSet valeursAutoGenerees = null;
			
			int statut;
			
			try {
				/* Récupération d'une connexion depuis la Factory */
				connexion = daofactory.getConnection();
				preparedStatement = initialisationRequetePreparee( connexion,SQL_U_DRAW_END, false, drawnumber);
				
				statut = preparedStatement.executeUpdate();
				
				/* Analyse du statut retourné par la requête d'insertion */
				if ( statut == 0 ) {
					throw new DAOException( "Échec de la mise à jour de la fin de course, aucune ligne ajoutée dans la table." );
				}
				
			} catch ( SQLException e ) {
				throw new DAOException( e );
			} finally {
				fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
			}
			
			return statut;
		}
		
		@Override
		public int getIdKenos(String coderace, int drawnumk) throws DAOException {
			// TODO Auto-generated method stub
			Connection connexion = null;
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			String[] idkeno;
			int n = 0;
			
			try {
				/* Récupération d'une connexion depuis la Factory */
				connexion = daofactory.getConnection();
				preparedStatement = initialisationRequetePreparee( connexion, SQL_F_ID, false, coderace, drawnumk);
				resultSet = preparedStatement.executeQuery();
				
				/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
				idkeno = lireResultBD(resultSet);
				n = Integer.parseInt(idkeno[1]);
			} catch ( SQLException e ) {
				e.printStackTrace();
			} finally {
				fermeturesSilencieuses( resultSet, preparedStatement, connexion );
			}
			
			return n;
		}

		private static Keno map( ResultSet resultSet ) throws SQLException {
	    
			Keno keno = new Keno();
			
			keno.setIdKeno(resultSet.getLong("idkeno"));
			keno.setCoderace(resultSet.getString("coderace"));
			keno.setDrawnumbK(resultSet.getString("drawnumbk"));
			keno.setBonusKamount(resultSet.getString("bonuskamount"));
			keno.setBonusKcod(resultSet.getString("bonuskcod"));
			keno.setDrawnumK(resultSet.getString("drawnumk"));
			keno.setHeureTirage(resultSet.getString("heureTirage"));
			keno.setMultiplicateur(resultSet.getString("multiplicateur"));
			keno.setStarted(resultSet.getString("started"));
			//keno.set.setStarted(resultSet.getInt("idsalle"));
			keno.setSbonusKcod(resultSet.getString("sbonuskcod"));
			
			return keno;
		}

		@Override
		public int setCodeBonusK(double amount, long code, String idkeno) throws DAOException {
			Connection connexion = null;
			PreparedStatement preparedStatement = null;
			ResultSet valeursAutoGenerees = null;
			
			int statut = 0;
			
			try {
				/* Récupération d'une connexion depuis la Factory */
				connexion = daofactory.getConnection();
				preparedStatement = initialisationRequetePreparee( connexion,SQL_U_KENO_BONUS, false, amount, code, idkeno);
				
				statut = preparedStatement.executeUpdate();
				
//				/* Analyse du statut retourné par la requête d'insertion */
//				if ( statut == 0 ) {
//					throw new DAOException( "Échec de la création d'un tirage, aucune ligne ajoutée dans la table." );
//				}
				
			} catch ( SQLException e ) {
				e.printStackTrace();
			} finally {
				fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
			}
			
			return statut;
		}

		@Override
		public String[] getLastKBonus(String coderace) throws DAOException {
			Connection connexion = null;
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			String[] last;
			
			try {
				/* Récupération d'une connexion depuis la Factory */
				connexion = daofactory.getConnection();
				preparedStatement = initialisationRequetePreparee( connexion, SQL_F_PREVIOUS_BONUS, false, coderace);
				resultSet = preparedStatement.executeQuery();
				
				/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
				last = lireResultBD(resultSet);
			} catch ( SQLException e ) {
				throw new DAOException( e );
			} finally {
				fermeturesSilencieuses( resultSet, preparedStatement, connexion );
			}
			
			return last;
		}

		@Override
		public String[] find_Max_draw_bis(String coderace) throws DAOException {
			Connection connexion = null;
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			String[] last;
			
			try {
				/* Récupération d'une connexion depuis la Factory */
				connexion = daofactory.getConnection();
				preparedStatement = initialisationRequetePreparee( connexion, SQL_F_MAX_PREVIOUS_DRAW, false, coderace, coderace);
				resultSet = preparedStatement.executeQuery();
				
				/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
				last = lireResultBD(resultSet);
			} catch ( SQLException e ) {
				throw new DAOException( e );
			} finally {
				fermeturesSilencieuses( resultSet, preparedStatement, connexion );
			}
			
			return last;
		}

		@Override
		public ArrayList<Keno> getAllLastKdraw(String coderace) throws DAOException {
			Connection connexion = null;
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			Keno ken;
			ArrayList<Keno> list_keno = new ArrayList<Keno>();
			
			try {
				/* Récupération d'une connexion depuis la Factory */
				connexion = daofactory.getConnection();
				preparedStatement = initialisationRequetePreparee( connexion, SQL_F_PREVIOUS_H_DRAW, false, coderace);
				resultSet = preparedStatement.executeQuery();
				
				/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
				while ( resultSet.next() ) {
					ken = map(resultSet);
					list_keno.add(ken);
				}
			} catch ( SQLException e ) {
				throw new DAOException( e );
			} finally {
				fermeturesSilencieuses( resultSet, preparedStatement, connexion );
			}
			
			return list_keno;
		}
		
}
