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
import model.user.UserException;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	UserDAO userJDBCTemplate = (UserDAO) context.getBean("UserJDBCTemplate");

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = null;
		try {
			user = userJDBCTemplate.login(request.getParameter("email"), request.getParameter("password"));
		} catch (UserException e) {
			// wrong password/email
			e.printStackTrace();
		}
		if (user != null)
			response.getWriter().println(
					"You have successfully logged in, " + user.getFirstName() + " " + user.getLastName() + "!");
		// TODO create session
		// TODO redirect to home.jsp
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
