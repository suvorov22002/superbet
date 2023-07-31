package superbetDAO;

import java.util.List;

import modele.Partner;
import modele.PartnerDto;

public interface PartnerDAO {
	
	Long create(Partner partner) throws DAOException;
	Partner find(String coderace) throws DAOException;
	int update(Partner partner) throws DAOException;
	boolean delete(Partner partner) throws DAOException;
	int update_bonusk(double dbl, int bncd, String coderace) throws DAOException;
	int update_bonusp(double dbl, int bncd, String coderace) throws DAOException;
	int update_reset_bonusk(double dbl, String coderace) throws DAOException;
	int update_reset_bonusp(double dbl, String coderace) throws DAOException;
	Partner findById(Long id) throws DAOException;
	List<Partner> getAllPartners() throws DAOException;
	int update_cob(String cob,String coderace) throws DAOException;
	List<Partner> getAllPartnersByGroup(String idgrp) throws DAOException;
	PartnerDto find2(String coderace) throws DAOException;
}
