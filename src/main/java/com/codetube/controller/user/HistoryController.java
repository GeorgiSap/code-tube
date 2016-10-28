package com.codetube.controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codetube.controller.ControllerManager;
import com.codetube.model.user.User;
import com.codetube.model.videoclip.VideoClip;

@Controller
public class HistoryController extends ControllerManager {

	@RequestMapping(value = "/history", method = RequestMethod.GET)
	public String showHistory(HttpServletRequest request) {
		try {
			if (request.getSession(false) == null) {
				loadTags(request);
				return "home";
			}
			User user = (User) request.getSession().getAttribute("user");
			if (user != null) {
				List<VideoClip> historyEntries = user.getHistory();
				request.setAttribute("videosToLoad", historyEntries);
			}
			loadTags(request);
			request.setAttribute("title", "Last viewed");
			return "home";
		} catch (Exception e) {
			return "home";
		}
	}


}
