package superbetDAO;

import java.util.Date;
import java.util.List;

import modele.Airtime;
import modele.Caissier;

public interface AirtimeDAO {
	int create(Airtime airtime) throws DAOException;
	Airtime update(Airtime airtime) throws DAOException;
	void delete(Airtime airtime) throws DAOException;
	Airtime find(Caissier user) throws DAOException;
	Airtime findByDate(Caissier user, Date date) throws DAOException;
	int createMvt(Long idcaissier, double mvt) throws DAOException;
	int updateMvt(Long idcaissier, double mvt) throws DAOException;
	double findMvt(Long idcaissier) throws DAOException;
	int findCMvt(Long idcaissier) throws DAOException;
	List<Airtime> find(String cais, String dat1, String dat2) throws DAOException;
	double findCumulCredit(String cais, String dat1, String dat2) throws DAOException;
	String[] findCaisMvt(Long idcaissier) throws DAOException;
}
