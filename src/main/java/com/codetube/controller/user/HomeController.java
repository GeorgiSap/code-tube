package com.codetube.controller.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codetube.model.user.User;
import com.codetube.model.user.UserDAO;
import com.codetube.model.user.history.History;
import com.codetube.model.user.history.HistoryDAO;
import com.codetube.model.user.subscription.Subscription;
import com.codetube.model.videoclip.VideoClip;
import com.codetube.model.videoclip.VideoClipDAO;

@Controller
public class HomeController {
	ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	VideoClipDAO videoClipJDBCTemplate = (VideoClipDAO) context.getBean("VideoClipJDBCTemplate");
	HistoryDAO historyJDBCTemplate = (HistoryDAO) context.getBean("HistoryJDBCTemplate");
	UserDAO userJDBCTemplate = (UserDAO) context.getBean("UserJDBCTemplate");
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(HttpServletRequest request) {
		if (request.getSession(false) == null) {
			return "index";
		}
		List<VideoClip> allVideos = videoClipJDBCTemplate.getClips();
		
		List<VideoClip> allVideosOrdered = new ArrayList<VideoClip>();
		for (int video =  allVideos.size() - 1; video >= 0; video--) {
			User user = userJDBCTemplate.get(allVideos.get(video).getUser().getId());
			allVideos.get(video).setUser(user);
			allVideosOrdered.add(allVideos.get(video));
		}
		
		request.setAttribute("videosToLoad", allVideosOrdered);
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

	
	@RequestMapping(value = "/subscriptions", method = RequestMethod.GET)
	public String showSubscriptions(HttpServletRequest request) {
		if (request.getSession(false) == null) {
			return "index";
		}
		request.setAttribute("title", "Subscriptions");
		User user = (User) request.getSession().getAttribute("user");
		List<User> subscriptions = user.getSubscribtions();
		List<VideoClip> subscriptionVideos = new ArrayList<VideoClip>();
		
//		for (int subscription = subscriptions.size() - 1; subscription >= 0; subscription--) {
//			List<VideoClip> currentChannelVideos = videoClipJDBCTemplate.getClips(subscriptions.get(subscription).getId());
//			for (int video =  currentChannelVideos.size() - 1; video >= 0; video--) {
//				subscriptionVideos.add(currentChannelVideos.get(video));
//				currentChannelVideos.get(video).setUser(subscriptions.get(subscription));
//			}
//		}
		
		
		for (User subscription : subscriptions) {
			List<VideoClip> currentChannelVideos = videoClipJDBCTemplate.getClips(subscription.getId());
			for (int video =  currentChannelVideos.size() - 1; video >= 0; video--) {
				subscriptionVideos.add(currentChannelVideos.get(video));
				currentChannelVideos.get(video).setUser(subscription);
			}
		}

		request.setAttribute("videosToLoad", subscriptionVideos);
		return "subscriptions";
	}
	
	
	@RequestMapping(value = "/history", method = RequestMethod.GET)
	public String showHistory(HttpServletRequest request) {
		if (request.getSession(false) == null) {
			return "index";
		}
		request.setAttribute("title", "Last viewed");
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
		Map<Integer, LocalDateTime> history = user.getHistory();
		Set<History> historySet = new TreeSet<History>();
		for (Entry<Integer, LocalDateTime> entry : history.entrySet()) {
			historySet.add(new History(entry.getKey(), entry.getValue()));
		}
		List<VideoClip> historyEntries = new ArrayList<VideoClip>();
		for (History entry : historySet) {
			historyEntries.add(videoClipJDBCTemplate.getClip(entry.getVideoClipId()));
		}
		for (VideoClip videoClip : historyEntries) {
			User publisher = userJDBCTemplate.get(videoClip.getUser().getId());
			videoClip.setUser(publisher);
		}
		request.setAttribute("videosToLoad", historyEntries);
		}
		return "home";
	}
}
