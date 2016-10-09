package com.codetube.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codetube.model.playlist.Playlist;
import com.codetube.model.playlist.PlaylistDAO;
import com.codetube.model.playlist.PlaylistException;
import com.codetube.model.user.User;
import com.codetube.model.user.UserDAO;
import com.codetube.model.user.UserException;

@Controller
@RequestMapping(value = "/register")
public class RegisterController extends UserController {

	ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	UserDAO userJDBCTemplate = (UserDAO) context.getBean("UserJDBCTemplate");
	PlaylistDAO playlistJDBCTemplate = (PlaylistDAO) context.getBean("PlaylistJDBCTemplate");

	@RequestMapping(method = RequestMethod.GET)
	public String redirect() {
		return "index";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String register(HttpServletRequest request, HttpServletResponse response) {
		if (userJDBCTemplate.emailExists(request.getParameter("email"))) {
			// response.getWriter().println("Email " +
			// request.getParameter("email") + " already exists.");
			return "index";
		}

		if (userJDBCTemplate.userNameExists(request.getParameter("user_name"))) {
			// response.getWriter().println("Username " +
			// request.getParameter("user_name") + " already exists.");
			return "index";
		}

		User newUser = null;
		try {
			newUser = new User(0, request.getParameter("first_name"), request.getParameter("last_name"),
					request.getParameter("user_name"), request.getParameter("email"), request.getParameter("password"));
			int userId = userJDBCTemplate.register(newUser);
			playlistJDBCTemplate.addPlayList(new Playlist(0, "default"), userJDBCTemplate.get(userId));
			newUser.setId(userId);
			createSession(request, newUser);
		} catch (UserException e) {
			// TODO redirect to error page
			e.printStackTrace();
			return "index";
		} catch (PlaylistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Successfully added playlist and user");
		return "index";
	}

}
