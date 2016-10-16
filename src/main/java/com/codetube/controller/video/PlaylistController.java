package com.codetube.controller.video;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.codetube.model.playlist.Playlist;
import com.codetube.model.playlist.PlaylistDAO;

@Controller
@SessionAttributes("playlist")
public class PlaylistController {
	private ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	PlaylistDAO playlistJDBCTemplate = (PlaylistDAO) context.getBean("PlaylistJDBCTemplate");

	@RequestMapping(value = "/playlists", method = RequestMethod.GET)
	public String showPlayLists(Model model, HttpServletRequest request) {

		if (validateSesion(request) != null) {
			return validateSesion(request);
		}

		int userId = (int) request.getSession().getAttribute("user_id");
		System.out.println(userId);
		Playlist playlist = playlistJDBCTemplate.getPlaylist(userId);
		model.addAttribute("playlist", playlist);
		return "playlist";
	}

	@RequestMapping(value = "/playlist/{playlist_id}", method = RequestMethod.GET)
	public String showPlaylist(Model model, HttpServletRequest request) {
		if (validateSesion(request) != null) {
			return validateSesion(request);
		}
		int userId = (int) request.getSession().getAttribute("user_id");
		System.out.println(userId);
		playlistJDBCTemplate.getPlaylist(userId);
		return "index";
	}

	private String validateSesion(HttpServletRequest request) {
		if (request.getSession(false) == null) {
			return "index";
		}
		if (request.getSession().getAttribute("user_id") == null) {
			return "index";
		}
		return null;
	}
}
