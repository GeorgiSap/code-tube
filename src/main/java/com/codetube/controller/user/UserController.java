package com.codetube.controller.user;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.codetube.controller.ControllerManager;
import com.codetube.model.tag.Tag;
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
			user.addToSubscriptions(userJDBCTemplate.get(subscription.getUserId()));
		}
	}

	private void addUserHistory(User user) {
		List<History> history = historyJDBCTemplate.getHistory(user.getId());
		for (History entry : history) {
			user.addToHistory(entry.getVideoClipId(), entry.getLastViewed());
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
			List<VideoClip> userVideosOrdered = new ArrayList<VideoClip>();
			for (int video = userVideos.size() - 1; video >= 0; video--) {
				userVideosOrdered.add(userVideos.get(video));
			}
			for (VideoClip videoClip : userVideosOrdered) {
				videoClip.setUser(user);
			}
			request.setAttribute("videosToLoad", userVideosOrdered);
		}
	}

	public void disableCache(HttpServletResponse response) {
		response.setHeader("Pragma", "No-cache");
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-cache");
	}
}
