package com.codetube.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codetube.model.user.User;
import com.codetube.model.user.UserDAO;
import com.codetube.model.user.UserException;

@Controller
@RequestMapping(value = "/register")
public class RegisterController extends UserController{
	
	ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	UserDAO userJDBCTemplate = (UserDAO) context.getBean("UserJDBCTemplate");
	
	@RequestMapping(method = RequestMethod.GET)
	public String redirect(){
		return "index";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String register(HttpServletRequest request, HttpServletResponse response){
		if (userJDBCTemplate.emailExists(request.getParameter("email"))) {
		//	response.getWriter().println("Email " + request.getParameter("email")  + " already exists.");
			return "index";
		}
		
		if (userJDBCTemplate.userNameExists(request.getParameter("user_name"))) {
			//response.getWriter().println("Username " + request.getParameter("user_name")  + " already exists.");
			return "index";
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
			return "index";
		}
		int userId = userJDBCTemplate.register(newUser);
		newUser.setId(userId);
		createSession(request, newUser);
		return "index";
	}
	

	
}
