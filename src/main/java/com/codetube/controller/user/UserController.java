package com.codetube.controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.codetube.model.user.User;
import com.codetube.model.user.UserDAO;
import com.codetube.model.user.history.History;
import com.codetube.model.user.history.HistoryDAO;
import com.codetube.model.user.subscription.Subscription;
import com.codetube.model.user.subscription.SubscriptionDAO;
import com.codetube.model.videoclip.VideoClip;
import com.codetube.model.videoclip.VideoClipDAO;

public class UserController {
	private static final int SESSION_LENGTH = 300 * 60 / 24;
	ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	HistoryDAO historyJDBCTemplate = (HistoryDAO) context.getBean("HistoryJDBCTemplate");
	VideoClipDAO videoClipJDBCTemplate = (VideoClipDAO) context.getBean("VideoClipJDBCTemplate");
	SubscriptionDAO subscriptionJDBCTemplate = (SubscriptionDAO) context.getBean("SubscriptionJDBCTemplate");
	UserDAO userJDBCTemplate = (UserDAO) context.getBean("UserJDBCTemplate");

	public void createSession(HttpServletRequest request, User user) {
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(SESSION_LENGTH);
		System.out.println(user);
		session.setAttribute("user_id", user.getId());

		List<VideoClip> videos = videoClipJDBCTemplate.getClips(user.getId());
		for (VideoClip videoClip : videos) {
			user.addToVideos(videoClip);
		}

		List<History> history = historyJDBCTemplate.getHistory(user.getId());
		for (History entry : history) {
			user.addToHistory(videoClipJDBCTemplate.getClip(entry.getVideoClipId()));
		}

		List<Subscription> subscriptions = subscriptionJDBCTemplate.listSubscriptions(user.getId());
		for (Subscription subscription : subscriptions) {
			user.addToSubscriptions(userJDBCTemplate.get(subscription.getUserId()));
		}

		session.setAttribute("user", user);
	}

	public void disableCache(HttpServletResponse response) {
		response.setHeader("Pragma", "No-cache");
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-cache");
	}
}
