package com.codetube.controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codetube.model.user.IUser;
import com.codetube.model.user.User;

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
		if ((!emailValidator.validate(request.getParameter("email")))
				|| request.getParameter("email").trim().length() > IUser.MAX_FIELD_LENGTH
				|| request.getParameter("email").trim().length() < IUser.MIN_EMAIL_LENGTH) {
			request.setAttribute("messageLogging", "Email not valid");
			return "home";
		}

		if (!passwordValidator.validate(request.getParameter("password"))) {
			request.setAttribute("messageLogging",
					"Your password do not match the requirements: 6 to 20 characters string with at least one digit, one upper case letter, one lower case letter and one special symbol (\"@#$%\")");
			return "home";
		}

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
