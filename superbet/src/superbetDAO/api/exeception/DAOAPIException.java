package superbetDAO.api.exeception;

public class DAOAPIException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DAOAPIException(String string) {
		// TODO Auto-generated constructor stub
		super(string);
	}

	public DAOAPIException(Exception e) {
		// TODO Auto-generated constructor stub
		super(e);
	}
}
