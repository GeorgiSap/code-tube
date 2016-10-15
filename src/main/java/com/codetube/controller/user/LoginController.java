package com.codetube.controller.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codetube.model.tag.Tag;
import com.codetube.model.user.User;
import com.codetube.model.user.UserDAO;

@Controller
@RequestMapping(value = "/login")
public class LoginController extends UserController {
	public static final String DEFAULT_ERROR_VIEW = "error";

	@RequestMapping(method = RequestMethod.GET)
	public String redirect() {
		return "home";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = null;
		try {
			user = userJDBCTemplate.login(request.getParameter("email"), request.getParameter("password"));
		} catch (Exception e) {
			System.out.println("wrong password");
			request.setAttribute("messageLogging", "Can't be logged right now! Wrong email/password!");
			return "home";
		}
		if (user != null) {
			createSession(request, user);
			return "home";
		} else {
			request.setAttribute("messageLogging", "Something went wrong! Try again later!");
			return "home";
		}
	}

}
