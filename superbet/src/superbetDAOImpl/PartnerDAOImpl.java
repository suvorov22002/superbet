package superbetDAOImpl;

import static database.Bd.fermeturesSilencieuses;
import static database.Bd.initialisationRequetePreparee;
import static database.Bd.lireResultBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modele.Partner;
import modele.PartnerDto;
import superbetDAO.DAOException;
import superbetDAO.DAOFactory;
import superbetDAO.PartnerDAO;

public class PartnerDAOImpl implements PartnerDAO {
	
	private DAOFactory daofactory;
	private static final String SQL_F_PARTNER = "select * from partner WHERE coderace = ? ";
	private static final String SQL_D_PARTNER = "delete from partner WHERE coderace = ? ";
	private static final String SQL_F_PARTNER_CONF = "select partner.coderace as coderace, bnkmin, bnkmax, bnpmin, bnpmax, bndmin, "
			+ "bndmax, bonuskamount, bonusbamount, bonusdamount, bonuspamount, bonusramount "
			+ "from config, partner where config.coderace = partner.coderace and partner.coderace = ? ";
	private static final String SQL_F_PARTNER_BY_ID = "select * from partner WHERE idpartner = ? ";
	private static final String SQL_F_PARTNER_BY_GROUP = "select * from partner WHERE groupe = ? ";
	private static final String SQL_U_BONUS_AMOUNT_PARTNER = "UPDATE partner SET bonuskamount = ? , bonuskcode= ?"
			+ " WHERE coderace = ? ";
	private static final String SQL_U_BONUS_RESET_AMOUNT_PARTNER = "UPDATE partner SET bonuskamount = ? WHERE coderace = ? ";
	private static final String SQL_U_BONUSP_AMOUNT_PARTNER = "UPDATE partner SET bonuspamount = ? , bonuspcode= ?"
			+ " WHERE coderace = ? ";
	private static final String SQL_U_BONUSP_RESET_AMOUNT_PARTNER = "UPDATE partner SET bonuspamount = ? WHERE coderace = ? ";
	private static final String SQL_F_ALL_PARTNER = "select * from partner where actif=1 ";
	private static final String SQL_C_PARTNER = "Insert Into partner Set coderace=? , zone=? , groupe = ? , actif = ? , cob = ? "; 
	private static final String SQL_U_COB_PARTNER = "UPDATE partner SET COB = ? WHERE coderace = ? ";
	private static final String SQL_U_INIT_PARTNER = "UPDATE partner SET actif = ? , cob = ? WHERE coderace = ? ";
	
	
	public PartnerDAOImpl(DAOFactory daoFactory){
		this.daofactory = daoFactory;
	}

	@Override
	public synchronized Long create(Partner partner) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		int statut = 0;
		Long idp;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion,SQL_C_PARTNER, true, partner.getCoderace(), partner.getZone(), partner.getGroupe(), partner.getActif(), partner.getCob());
			
			statut = preparedStatement.executeUpdate();
			
			/* Analyse du statut retourné par la requête d'insertion */
			if ( statut == 0 ) {
				throw new DAOException( "Échec de la création d'un versement, aucune ligne ajoutée dans la table." );
			}
			/* Récupération de l'id auto-généré par la requête d'insertion */
			
			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			if ( valeursAutoGenerees.next() ) {
			/* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
				partner.setIdpartner( valeursAutoGenerees.getLong( 1 ) );
				idp = partner.getIdpartner();
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
	public Partner find(String coderace) throws DAOException {
		// TODO Auto-generated method stub
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Partner partner = null;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_PARTNER, false,coderace);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			if ( resultSet.next() ) {
				partner = map( resultSet );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		return partner;
	}
	
	@Override
	public PartnerDto find2(String coderace) throws DAOException {
		// TODO Auto-generated method stub
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		PartnerDto partner = null;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_PARTNER_CONF, false,coderace);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			if ( resultSet.next() ) {
				partner = map1( resultSet );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		return partner;
	}
	
	@Override
	public Partner findById(Long id) throws DAOException {
		// TODO Auto-generated method stub
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Partner partner = null;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_PARTNER_BY_ID, false,id);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			if ( resultSet.next() ) {
				partner = map( resultSet );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		return partner;
	}

	@Override
	public Partner update(Partner partner) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		
		int statut;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion,SQL_U_INIT_PARTNER, false, partner.getActif(), partner.getCob(), partner.getCoderace());
			statut = preparedStatement.executeUpdate();
			/* Analyse du statut retourné par la requête d'insertion */
			if ( statut == 0 ) {
				return null;
			}
			
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
		}
		
		return partner;
	}

	@Override
	public boolean delete(Partner partner) throws DAOException {
		
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		int res = 0;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_D_PARTNER, false, partner.getCoderace());
		    res = preparedStatement.executeUpdate();
			
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( preparedStatement, connexion );
		}
		
		return (res > 0) ? true : false;
	}
	
	@Override
	public synchronized int update_bonusk(double dbl, int bncd, String coderace) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		
		int statut;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion,SQL_U_BONUS_AMOUNT_PARTNER, false, dbl, bncd, coderace);
			
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
	public synchronized int update_cob(String cob,String coderace) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		
		int statut;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion,SQL_U_COB_PARTNER, false, cob,coderace);
			
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
	public synchronized int update_reset_bonusk(double dbl, String coderace) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		
		int statut;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion,SQL_U_BONUS_RESET_AMOUNT_PARTNER, false, dbl, coderace);
			
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
	public synchronized ArrayList<Partner> getAllPartners() throws DAOException {
		// TODO Auto-generated method stub
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String[] last;
		Partner partner = null;
		ArrayList<Partner> lastsK = new ArrayList<Partner>();
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_ALL_PARTNER, false);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			   //---
			while(resultSet.next()){
				 partner = map( resultSet );
				 lastsK.add(partner);
			}
					
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
//		int j,nbre;
//		   
//		   nbre = (last.length-1)/Integer.parseInt(last[0]);
//		   ArrayList<Partner> lastsK = new ArrayList<Partner>(nbre);
//		   j = 1;
//		   
//		   for(int i=0;i<nbre;i++){
//			
//			  partner = new Partner();
//			  
//			    j++;
//			    partner.setCoderace(last[j++]);
//				partner.setZone(last[j++]);
//				partner.setBonuskamount(Double.parseDouble(last[j++]));
//				partner.setBonuskcode(Integer.parseInt(last[j++]));
//				partner.setBonusBamount(Double.parseDouble(last[j++]));
//				j++;
//				partner.setBonusRamount(Double.parseDouble(last[j++]));
//				 j++;
//				partner.setBonusDamount(Double.parseDouble(last[j++]));
//				 j++;
//				 j++;
//				 j++;
//				
//				
//				lastsK.add(partner);
//		   }
//		
		return lastsK;
	}
	

	@Override
	public synchronized ArrayList<Partner> getAllPartnersByGroup(String idgrp) throws DAOException {
		// TODO Auto-generated method stub
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String[] last;
		Partner partner = null;
		ArrayList<Partner> lastsK = new ArrayList<Partner>();
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_F_PARTNER_BY_GROUP, false,idgrp);
			resultSet = preparedStatement.executeQuery();
			
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			   //---
			while(resultSet.next()){
				 partner = map( resultSet );
				 lastsK.add(partner);
			}
					
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
//		int j,nbre;
//		   
//		   nbre = (last.length-1)/Integer.parseInt(last[0]);
//		   ArrayList<Partner> lastsK = new ArrayList<Partner>(nbre);
//		   j = 1;
//		   
//		   for(int i=0;i<nbre;i++){
//			
//			  partner = new Partner();
//			  
//			    j++;
//			    partner.setCoderace(last[j++]);
//				partner.setZone(last[j++]);
//				partner.setBonuskamount(Double.parseDouble(last[j++]));
//				partner.setBonuskcode(Integer.parseInt(last[j++]));
//				partner.setBonusBamount(Double.parseDouble(last[j++]));
//				j++;
//				partner.setBonusRamount(Double.parseDouble(last[j++]));
//				 j++;
//				partner.setBonusDamount(Double.parseDouble(last[j++]));
//				 j++;
//				 j++;
//				 j++;
//				
//				
//				lastsK.add(partner);
//		   }
//		
		return lastsK;
	}
	
	
	@Override
	public int update_bonusp(double dbl, int bncd, String coderace) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		
		int statut;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion,SQL_U_BONUSP_AMOUNT_PARTNER, false, dbl, bncd, coderace);
			
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
	public int update_reset_bonusp(double dbl, String coderace) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		
		int statut;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daofactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion,SQL_U_BONUSP_RESET_AMOUNT_PARTNER, false, dbl, coderace);
			
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
	
	private static Partner map( ResultSet resultSet ) throws SQLException {
	    
		Partner partner = new Partner();
		
		partner.setIdpartner(resultSet.getLong("idPartner"));
		partner.setBonuskamount(resultSet.getDouble("bonuskamount"));
		partner.setBonuskcode(resultSet.getInt("bonuskcode"));
		partner.setBonusBamount(resultSet.getDouble("bonusbamount"));
		partner.setBonusbcode(resultSet.getInt("bonusbcode"));
		partner.setBonusRamount(resultSet.getDouble("bonusramount"));
		partner.setBonusrcode(resultSet.getInt("bonusrcode"));
		partner.setBonusDamount(resultSet.getDouble("bonusdamount"));
		partner.setBonusdcode(resultSet.getInt("bonusdcode"));
		partner.setCoderace(resultSet.getString("coderace"));
		partner.setZone(resultSet.getString("zone"));
		partner.setBonusPamount(resultSet.getDouble("bonuspamount"));
		partner.setBonuspcode(resultSet.getInt("bonuspcode"));
		partner.setCob(resultSet.getString("cob"));
		partner.setGroupe(resultSet.getInt("groupe"));
		partner.setActif(resultSet.getInt("actif"));
		
		
		return partner;
	}
	
   private static PartnerDto map1( ResultSet resultSet ) throws SQLException {
	    
		PartnerDto partner = new PartnerDto();
		
		
		partner.setBonusKamount(resultSet.getDouble("bonuskamount"));
		partner.setBonusBamount(resultSet.getDouble("bonusbamount"));
		partner.setBonusRamount(resultSet.getDouble("bonusramount"));
		partner.setBonusDamount(resultSet.getDouble("bonusdamount"));
		partner.setBonusPamount(resultSet.getDouble("bonuspamount"));
		partner.setBnskmin(resultSet.getDouble("bnkmin"));
		partner.setBnskmax(resultSet.getDouble("bnkmax"));
		partner.setBnspmin(resultSet.getDouble("bnpmin"));
		partner.setBnspmax(resultSet.getDouble("bnpmax"));
		partner.setBnsdmin(resultSet.getDouble("bndmin"));
		partner.setBnsdmax(resultSet.getDouble("bndmax"));
		partner.setCoderace(resultSet.getString("coderace"));
		
		return partner;
	}

}
