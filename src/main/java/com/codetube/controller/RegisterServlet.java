package com.codetube.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.codetube.model.user.User;
import com.codetube.model.user.UserDAO;
import com.codetube.model.user.UserException;

public class RegisterServlet extends ServletManager {
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
			// TODO redirect to error page
			e.printStackTrace();
			return;
		}
		int userId = userJDBCTemplate.register(newUser);
		newUser.setId(userId);
		createSession(request, newUser);
		response.sendRedirect("index.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
