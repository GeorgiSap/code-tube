package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeServlet extends ServletManager {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//disableCache(response);
		
		if (request.getSession(false) == null) {
			//TODO redirect to non-logged home page
			response.sendRedirect("./login.html");
			return;
		} else {
			response.sendRedirect("./home.jsp");
		//	response.getWriter().println("Logged in as " + request.getSession().getAttribute("user_name"));
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
