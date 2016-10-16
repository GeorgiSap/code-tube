package com.codetube.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codetube.model.user.User;
import com.codetube.model.videoclip.VideoClip;

@Controller
public class MostViewedController extends ControllerManager {
	private static final int NUMBER_OF_VIDEOS = 12;

	@RequestMapping(value = "/viewed", method = RequestMethod.GET)
	public String showMostViewed(HttpServletRequest request) {

		List<VideoClip> mostViewed = videoClipJDBCTemplate.getMostViewedVideos(NUMBER_OF_VIDEOS);
		for (VideoClip videoClip : mostViewed) {
			User user = userJDBCTemplate.get(videoClip.getUser().getId());
			videoClip.setUser(user);
		}
		request.setAttribute("title", "Most Viewed");
		request.setAttribute("videosToLoad", mostViewed);
		loadTags(request);

		return "home";
	}
}
