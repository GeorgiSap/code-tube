package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import model.user.User;
import model.user.UserDAO;
import model.user.UserException;

public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	UserDAO userJDBCTemplate = (UserDAO) context.getBean("UserJDBCTemplate");
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if (userJDBCTemplate.emailExists(request.getParameter("email"))) {
			response.getWriter().println("Email " + request.getParameter("email")  + " already exists.");
			return;
		}
		
		if (userJDBCTemplate.userNameExists(request.getParameter("user_name"))) {
			response.getWriter().println("Username " + request.getParameter("user_name")  + " already exists.");
			return;
		}

		User newUser = null;
		try {
			newUser = new User(0, request.getParameter("first_name"), 
					request.getParameter("last_name"),
					request.getParameter("user_name"),
					request.getParameter("email"),
					request.getParameter("password"));
		} catch (UserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		int userId = userJDBCTemplate.register(newUser);
		newUser.setId(userId);
		//response.getWriter().println("You have successfully registered, " + newUser.getFirstName() + " " + newUser.getLastName() + "!");
		// TODO create session
		//TODO redirect to home.jsp
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(20);
		session.setAttribute("user_name", newUser.getUserName());

		response.sendRedirect("./Home");
		System.out.println(request.getParameter("password"));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
