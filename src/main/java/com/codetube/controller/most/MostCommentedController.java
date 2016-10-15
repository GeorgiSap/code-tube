package com.codetube.controller.most;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codetube.controller.ControllerManager;
import com.codetube.model.user.User;
import com.codetube.model.videoclip.VideoClip;

@Controller
public class MostCommentedController extends ControllerManager {
	private static final int NUMBER_OF_VIDEOS = 12;

	@RequestMapping(value = "/commented", method = RequestMethod.GET)
	public String showMostCommented(HttpServletRequest request) {

		List<VideoClip> mostCommented = videoClipJDBCTemplate.getMostCommentedVideos(NUMBER_OF_VIDEOS);
		for (VideoClip videoClip : mostCommented) {
			User user = userJDBCTemplate.get(videoClip.getUser().getId());
			videoClip.setUser(user);
		}
		request.setAttribute("title", "Most Commented");
		request.setAttribute("videosToLoad", mostCommented);
		loadTags(request);
		return "home";
	}

}
