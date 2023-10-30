package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import business.RecordUser;
import modele.Caissier;

public class LogoutServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	private RecordUser refresh;
	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String ATT_DATA_RECORD = "recordUSer";
	
	public void init() throws ServletException {
	
		this.refresh = ( (RecordUser)getServletContext().getAttribute( ATT_DATA_RECORD ));
	
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
    	
		Cookie[] cookies = request.getCookies();
    	if(cookies != null){
    		
	    	for(Cookie cookie : cookies){
	    		if(cookie.getName().equals("JSESSIONID")){
	    			System.out.println("JSESSIONID="+cookie.getValue());
	    		}
	    		cookie.setMaxAge(0);
	    		response.addCookie(cookie);
	    	}
    	}
    	//invalidate the session if exists
    	HttpSession session = request.getSession(false);
    	
    
    	
    	if(session != null){
    		
    		Caissier caissier = (Caissier) session.getAttribute("caissier");
        	System.out.println("User= " + caissier);
        	
        	List<Caissier> listCaissiers = this.refresh.getCustomers();
        	listCaissiers.removeIf(c -> c.getLoginc().equals(caissier.getLoginc()));
        	
    		session.invalidate();
    		
    	}
    	
    	//no encoding because we have invalidated the session
    	response.sendRedirect("login.jsp");
    }

}
