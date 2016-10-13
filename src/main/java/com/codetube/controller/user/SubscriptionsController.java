package com.codetube.controller.user;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codetube.model.user.User;
import com.codetube.model.videoclip.VideoClip;
import com.codetube.model.videoclip.VideoClipDAO;

@Controller
public class SubscriptionsController {
	ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	VideoClipDAO videoClipJDBCTemplate = (VideoClipDAO) context.getBean("VideoClipJDBCTemplate");
	
	@RequestMapping(value = "/subscriptions", method = RequestMethod.GET)
	public String showSubscriptions(HttpServletRequest request) {
		if (request.getSession(false) == null) {
			return "index";
		}
		request.setAttribute("title", "Subscriptions");
		User user = (User) request.getSession().getAttribute("user");
		List<User> subscriptions = user.getSubscribtions();
		List<VideoClip> subscriptionVideos = new ArrayList<VideoClip>();

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
}
