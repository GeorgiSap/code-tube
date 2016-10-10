package com.codetube.controller.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codetube.model.user.User;
import com.codetube.model.user.history.History;
import com.codetube.model.user.history.HistoryDAO;
import com.codetube.model.videoclip.VideoClip;
import com.codetube.model.videoclip.VideoClipDAO;

@Controller
public class HomeController {
	ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	VideoClipDAO videoClipJDBCTemplate = (VideoClipDAO) context.getBean("VideoClipJDBCTemplate");
	HistoryDAO historyJDBCTemplate = (HistoryDAO) context.getBean("HistoryJDBCTemplate");
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(HttpServletRequest request) {
		if (request.getSession(false) == null) {
			return "index";
		}
		request.setAttribute("title", "Recommended");
		return "home";
	}
	
	@RequestMapping(value = "/videos", method = RequestMethod.GET)
	public String showMyVideos(HttpServletRequest request) {
		if (request.getSession(false) == null) {
			return "index";
		}
		request.setAttribute("title", "My Videos");
		
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
		request.setAttribute("videosToLoad", user.getVideos());
		}
		return "home";
	}

	
	@RequestMapping(value = "/subscriptions", method = RequestMethod.GET)
	public String showSubscriptions(HttpServletRequest request) {
		if (request.getSession(false) == null) {
			return "index";
		}
		request.setAttribute("title", "Subscriptions");
		//User user = (User) request.getSession().getAttribute("user");
		//TODO get videos
		//request.setAttribute("videosToLoad", user.getSubscribtions());
		return "home";
	}
	
	
	@RequestMapping(value = "/history", method = RequestMethod.GET)
	public String showHistory(HttpServletRequest request) {
		if (request.getSession(false) == null) {
			return "index";
		}
		request.setAttribute("title", "Last viewed");
		//TODO
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			
		Set<History> history = (Set<History>) historyJDBCTemplate.getHistory(user.getId());
		List<VideoClip> historyEntries = new ArrayList<VideoClip>();
		for (History entry : history) {
			historyEntries.add(videoClipJDBCTemplate.getClip(entry.getVideoClipId()));
		}
		request.setAttribute("videosToLoad", historyEntries);
		}
		return "home";
	}
}
