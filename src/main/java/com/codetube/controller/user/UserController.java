package com.codetube.controller.user;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.codetube.model.tag.Tag;
import com.codetube.model.tag.TagDAO;
import com.codetube.model.user.User;
import com.codetube.model.user.UserDAO;
import com.codetube.model.user.history.History;
import com.codetube.model.user.history.HistoryDAO;
import com.codetube.model.user.subscription.Subscription;
import com.codetube.model.user.subscription.SubscriptionDAO;
import com.codetube.model.videoclip.VideoClip;
import com.codetube.model.videoclip.VideoClipDAO;

public class UserController {
	private static final int MAX_COUNT_OF_VIDEOS_IN_NEWEST = 10;
	private static final int SESSION_LENGTH = 300 * 60 / 24;
	ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	HistoryDAO historyJDBCTemplate = (HistoryDAO) context.getBean("HistoryJDBCTemplate");
	VideoClipDAO videoClipJDBCTemplate = (VideoClipDAO) context.getBean("VideoClipJDBCTemplate");
	SubscriptionDAO subscriptionJDBCTemplate = (SubscriptionDAO) context.getBean("SubscriptionJDBCTemplate");
	UserDAO userJDBCTemplate = (UserDAO) context.getBean("UserJDBCTemplate");
	TagDAO tagJDBCTemplate = (TagDAO) context.getBean("TagJDBCTemplate");
	
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
			user.addToHistory(entry.getVideoClipId(), entry.getLastViewed());
		}

		List<Subscription> subscriptions = subscriptionJDBCTemplate.listSubscriptions(user.getId());
		for (Subscription subscription : subscriptions) {
			user.addToSubscriptions(userJDBCTemplate.get(subscription.getUserId()));
		}

		session.setAttribute("user", user);
		List<Tag> allTags = tagJDBCTemplate.getTags();
		request.setAttribute("allTags", allTags);
		
		
		loadNewestVideos(request);
		
	}

	protected void loadNewestVideos(HttpServletRequest request) {
		List<VideoClip> allVideos = videoClipJDBCTemplate.getClips();
		int countOfVideos = 0;
		List<VideoClip> allVideosOrdered = new ArrayList<VideoClip>();
		for (int video =  allVideos.size() - 1; video >= 0; video--) {
			User currentUser = userJDBCTemplate.get(allVideos.get(video).getUser().getId());
			allVideos.get(video).setUser(currentUser);
			allVideosOrdered.add(allVideos.get(video));
			countOfVideos++;
			if (countOfVideos >= MAX_COUNT_OF_VIDEOS_IN_NEWEST) {
				break;
			}
		}
		request.setAttribute("videosToLoad", allVideosOrdered);
		request.setAttribute("title", "Newest");
	}

	public void disableCache(HttpServletResponse response) {
		response.setHeader("Pragma", "No-cache");
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-cache");
	}
}
