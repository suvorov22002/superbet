package superbetDAO;

import modele.CagnotteDto;

public interface CagnotteDAO {
	Long create(CagnotteDto cagnotte) throws DAOException;
	CagnotteDto find(Long coderace) throws DAOException;
	int update(CagnotteDto cagnotte) throws DAOException;
	void delete(CagnotteDto cagnotte) throws DAOException;
}
