package superbetDAO;

public interface UtilDAO {
	public String[] searchPariL(String cpari);
	public String[] searchPariLById(String ipari);
	public String[] searchPariPById(String ipari);
	public long searchBarcode();
	public String[] searchPariS(String cpari);
	public String[] getParamUrl();
}
