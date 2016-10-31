package com.codetube.controller.user;

import static com.codetube.controller.user.SubscribeController.VIDEOS_SHOWN_PER_CHANNEL;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.codetube.controller.ControllerManager;
import com.codetube.model.user.EmailValidator;
import com.codetube.model.user.PasswordValidator;
import com.codetube.model.user.User;
import com.codetube.model.user.history.History;
import com.codetube.model.user.history.HistoryDAO;
import com.codetube.model.user.subscription.Subscription;
import com.codetube.model.user.subscription.SubscriptionDAO;
import com.codetube.model.videoclip.VideoClip;

public class UserController extends ControllerManager {
	private static final int COUNT_OF_VIDEOS_IN_NEWEST = 12;
	private static final int SESSION_LENGTH = 300 * 60 / 24;
	ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	HistoryDAO historyJDBCTemplate = (HistoryDAO) context.getBean("HistoryJDBCTemplate");
	SubscriptionDAO subscriptionJDBCTemplate = (SubscriptionDAO) context.getBean("SubscriptionJDBCTemplate");
	EmailValidator emailValidator = new EmailValidator();
	PasswordValidator passwordValidator = new PasswordValidator();
	
	public void createSession(HttpServletRequest request, User user) {
		try {
			HttpSession session = request.getSession();
			session.setMaxInactiveInterval(SESSION_LENGTH);
			session.setAttribute("user_id", user.getId());
			session.setAttribute("userNameComment", user.getUserName());
			addUserVideos(user);

			addUserHistory(user);

			addUserSubscriptions(user);

			session.setAttribute("user", user);

			loadTags(request);

			loadNewestVideos(request);
		} catch (Exception e) {
			
		}
	}

	private void addUserSubscriptions(User user) {
		List<Subscription> subscriptions = subscriptionJDBCTemplate.listSubscriptions(user.getId());
		for (Subscription subscription : subscriptions) {
			User subscriptionUser = userJDBCTemplate.get(subscription.getUserId());
			List<VideoClip> subscriptionVideoClips = videoClipJDBCTemplate.getClips(subscription.getUserId(), VIDEOS_SHOWN_PER_CHANNEL);
			for (VideoClip videoClip : subscriptionVideoClips) {
				 videoClip.setUser(subscriptionUser);
			}
			user.addToSubscriptions(subscriptionUser, subscriptionVideoClips);
		}
	}

	private void addUserHistory(User user) {
		List<History> historyList = historyJDBCTemplate.getHistory(user.getId());
		Set<History> sortedHistory = new TreeSet<History>();
		sortedHistory.addAll(historyList);
		List<VideoClip> historyEntries = extractVideosFromHistory(sortedHistory);
		for (VideoClip videoClip : historyEntries) {
			user.addToHistory(videoClip);
		}
	}

	private void addUserVideos(User user) {
		List<VideoClip> videos = videoClipJDBCTemplate.getClips(user.getId());
		for (VideoClip videoClip : videos) {
			user.addToVideos(videoClip);
		}
	}

	protected void loadNewestVideos(HttpServletRequest request) {
		List<VideoClip> newestVideos = videoClipJDBCTemplate.getNewestVideos(COUNT_OF_VIDEOS_IN_NEWEST);
		for (VideoClip videoClip : newestVideos) {
			User publisher = userJDBCTemplate.get(videoClip.getUser().getId());
			videoClip.setUser(publisher);
		}
		request.setAttribute("videosToLoad", newestVideos);
		request.setAttribute("title", "Newest");
	}

	protected void loadUserVideos(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			List<VideoClip> userVideos = user.getVideos();
			for (VideoClip videoClip : userVideos) {
				videoClip.setUser(user);
			}
			request.setAttribute("videosToLoad", userVideos);
		}
	}

	public void disableCache(HttpServletResponse response) {
		response.setHeader("Pragma", "No-cache");
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-cache");
	}
	
	private List<VideoClip> extractVideosFromHistory(Set<History> historySet) {
		List<VideoClip> historyEntries = new ArrayList<VideoClip>();
		for (History entry : historySet) {
			VideoClip videoClip = videoClipJDBCTemplate.getClip(entry.getVideoClipId());
			historyEntries.add(videoClip);
			User publisher = userJDBCTemplate.get(videoClip.getUser().getId());
			videoClip.setUser(publisher);
		}
		return historyEntries;
	}
}
