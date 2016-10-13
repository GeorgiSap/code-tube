package com.codetube.controller.user;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codetube.model.user.User;
import com.codetube.model.videoclip.VideoClip;

@Controller
public class UserVideosController {
	
	@RequestMapping(value = "/videos", method = RequestMethod.GET)
	public String showMyVideos(HttpServletRequest request) {
		if (request.getSession(false) == null) {
			return "index";
		}
		request.setAttribute("title", "My Videos");
		
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
		List<VideoClip> userVideos = user.getVideos();
		List<VideoClip> userVideosOrdered = new ArrayList<VideoClip>();
		for (int video =  userVideos.size() - 1; video >= 0; video--) {
			userVideosOrdered.add(userVideos.get(video));
		}
		for (VideoClip videoClip : userVideosOrdered) {
			videoClip.setUser(user);
		}
		request.setAttribute("videosToLoad", userVideosOrdered);
		}
		return "home";
	}

}
