package superbetDAO;

import modele.Cagnotte;

public interface CagnotteDAO {
	Long create(Cagnotte cagnotte) throws DAOException;
	Cagnotte find(Long coderace) throws DAOException;
	int update(Cagnotte cagnotte) throws DAOException;
	void delete(Cagnotte cagnotte) throws DAOException;
}
