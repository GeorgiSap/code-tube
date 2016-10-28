package com.codetube.controller.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codetube.controller.ControllerManager;
import com.codetube.model.user.User;
import com.codetube.model.videoclip.VideoClip;

@Controller
public class SubscriptionsController extends ControllerManager {

	@RequestMapping(value = "/subscriptions", method = RequestMethod.GET)
	public String showSubscriptions(HttpServletRequest request) {
		try {
			if (request.getSession(false) == null) {
				loadTags(request);
				return "home";
			}

			User user = (User) request.getSession().getAttribute("user");
			Map<User, List<VideoClip>> subscriptions = user.getSubscribtions();
			List<VideoClip> subscriptionVideos = extractVideosFromSubscriptions(subscriptions);
			loadTags(request);
			request.setAttribute("videosToLoad", subscriptionVideos);
			request.setAttribute("title", "Subscriptions");
			return "subscriptions";
		} catch (Exception e) {
			return "home";
		}
	}

	private List<VideoClip> extractVideosFromSubscriptions(Map<User, List<VideoClip>> subscriptions) {
		List<VideoClip> subscriptionVideos = new ArrayList<VideoClip>();
		for (Entry<User, List<VideoClip>> subscription : subscriptions.entrySet()) {
			subscriptionVideos.addAll(subscription.getValue());
		}
		return subscriptionVideos;
	}
}
