package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import model.user.User;
import model.user.UserDAO;

public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	UserDAO userJDBCTemplate = (UserDAO) context.getBean("UserJDBCTemplate");
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//TODO validate
		User newUser = new User(0, request.getParameter("first_name"), 
				request.getParameter("last_name"),
				request.getParameter("user_name"),
				request.getParameter("email"),
				request.getParameter("password"));
		int userId = userJDBCTemplate.register(newUser);
		newUser.setId(userId);
		response.getWriter().println("You have successfully registered, " + newUser.getFirstName() + " " + newUser.getLastName() + "!");
		// TODO create session
		//TODO redirect to home.jsp
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
