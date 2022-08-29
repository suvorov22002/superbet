package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Deconnexion extends HttpServlet {
	
	public static final String URL_REDIRECTION = "./login.jsp";
	
	public void doGet( HttpServletRequest req, HttpServletResponse res ) throws ServletException, IOException{
		
		HttpSession session = req.getSession();
		session.invalidate();
		
		res.sendRedirect(URL_REDIRECTION);
	}

}
