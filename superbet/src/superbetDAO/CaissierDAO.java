package superbetDAO;

import java.util.ArrayList;

import modele.Caissier;
import modele.Partner;

public interface CaissierDAO {
	
	int create(Caissier caissier);
	Caissier find(String login, String pass) ;
	Caissier update(Caissier caissier) ;
	Caissier findById(Long id) ;
	void delete(Caissier caissier) ;
	int updateState(Caissier caissier) ;
	Caissier findByLogin(String login) ;
	ArrayList<Caissier> findByPartner(String coderace) ;
	Caissier findByLoginIdPartner(String login, String coderace);
}
