package com.codetube.controller.user;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codetube.controller.ControllerManager;
import com.codetube.model.tag.Tag;
import com.codetube.model.tag.TagDAO;
import com.codetube.model.user.User;
import com.codetube.model.videoclip.VideoClip;
import com.codetube.model.videoclip.VideoClipDAO;

@Controller
public class SubscriptionsController extends ControllerManager {

	@RequestMapping(value = "/subscriptions", method = RequestMethod.GET)
	public String showSubscriptions(HttpServletRequest request) {
		if (request.getSession(false) == null) {
			loadTags(request);
			return "index";
		}

		User user = (User) request.getSession().getAttribute("user");
		List<User> subscriptions = user.getSubscribtions();
		List<VideoClip> subscriptionVideos = extractVideosFromSubscriptions(subscriptions);
		loadTags(request);
		request.setAttribute("videosToLoad", subscriptionVideos);
		request.setAttribute("title", "Subscriptions");
		return "subscriptions";
	}

	private List<VideoClip> extractVideosFromSubscriptions(List<User> subscriptions) {
		List<VideoClip> subscriptionVideos = new ArrayList<VideoClip>();
		for (User subscription : subscriptions) {
			List<VideoClip> currentChannelVideos = videoClipJDBCTemplate.getClips(subscription.getId());
			for (int video = currentChannelVideos.size() - 1; video >= 0; video--) {
				subscriptionVideos.add(currentChannelVideos.get(video));
				currentChannelVideos.get(video).setUser(subscription);
			}
		}
		return subscriptionVideos;
	}
}
