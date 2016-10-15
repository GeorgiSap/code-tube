package com.codetube.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codetube.model.playlist.Playlist;
import com.codetube.model.playlist.PlaylistDAO;
import com.codetube.model.playlist.PlaylistException;
import com.codetube.model.user.User;
import com.codetube.model.user.UserException;

@Controller
@RequestMapping(value = "/register")
public class RegisterController extends UserController {

	PlaylistDAO playlistJDBCTemplate = (PlaylistDAO) context.getBean("PlaylistJDBCTemplate");

	@RequestMapping(method = RequestMethod.GET)
	public String redirect() {
		return "home";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String register(HttpServletRequest request, HttpServletResponse response) {
		if (userJDBCTemplate.emailExists(request.getParameter("email"))) {
			request.setAttribute("messageLogging", "The email already exists!");
			return "home";
		}

		if (userJDBCTemplate.userNameExists(request.getParameter("user_name"))) {
			request.setAttribute("messageLogging", "The username already exists!");
			return "home";
		}

		User newUser = null;
		try {
			newUser = new User(0, request.getParameter("first_name"), request.getParameter("last_name"),
					request.getParameter("user_name"), request.getParameter("email"), request.getParameter("password"));
			int userId = userJDBCTemplate.register(newUser);
			playlistJDBCTemplate.addPlayList(new Playlist(0, "default"), userJDBCTemplate.get(userId));
			newUser.setId(userId);
			createSession(request, newUser);
		} catch (UserException | PlaylistException e) {
			request.setAttribute("messageLogging", "Something went wrong! Try again later!");
			return "home";
		}
		System.out.println("Successfully added playlist and user");
		return "home";
	}

}
