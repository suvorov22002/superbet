package superbetDAO;

import modele.Miset;

public interface MisetDAO {
	int create(Miset miset) throws DAOException;
	Miset find(String login, String pass) throws DAOException;
	Miset update(Miset miset) throws DAOException;
	void delete(Miset miset) throws DAOException;
	int findId() throws DAOException; 
	Miset searchTicketT(String barcode) throws DAOException;
	Miset searchTicketTAlrPay(String barcode, Long miset) throws DAOException;
	Miset findById(String idmiset) throws DAOException; 
	double findFreeSlipByPartner(String game, Long coderace)throws DAOException;
	int updateFree(String game, double step, Long coderace) throws DAOException;
	int createFree(Long idpartner);
}
