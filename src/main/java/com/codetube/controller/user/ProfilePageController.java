package com.codetube.controller.user;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.codetube.controller.ControllerManager;
import com.codetube.model.user.User;
import com.codetube.model.user.UserDAO;
import com.codetube.model.user.subscription.SubscriptionDAO;
import com.codetube.model.videoclip.VideoClip;
import com.codetube.model.videoclip.VideoClipDAO;

@Controller
@SessionAttributes("user")
public class ProfilePageController extends ControllerManager {
	ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	VideoClipDAO videoClipJDBCTemplate = (VideoClipDAO) context.getBean("VideoClipJDBCTemplate");
	UserDAO userJDBCTemplate = (UserDAO) context.getBean("UserJDBCTemplate");
	SubscriptionDAO subscriptionJDBCTemplate = (SubscriptionDAO) context.getBean("SubscriptionJDBCTemplate");

	@RequestMapping(value = "/user/{user_id}", method = RequestMethod.GET)
	public String loadUserVideos(Model model, @PathVariable("user_id") Integer userId, HttpServletRequest request) {
		try {
			User viewedUser = userJDBCTemplate.get(userId);
			List<VideoClip> viewedUserVideos = videoClipJDBCTemplate.getClips(userId);
			List<VideoClip> viewedUserVideosOrdered = new ArrayList<VideoClip>();
			for (int video = viewedUserVideos.size() - 1; video >= 0; video--) {
				VideoClip videoClip = viewedUserVideos.get(video);
				User user = userJDBCTemplate.get(videoClip.getUser().getId());
				videoClip.setUser(user);
				viewedUserVideosOrdered.add(videoClip);
			}

			request.setAttribute("userProfilePage", userId);

			if (request.getSession(false) != null) {
				User currentUser = (User) request.getSession().getAttribute("user");
				boolean isSubscribed = subscriptionJDBCTemplate.checkIfSubscribed(userId, currentUser.getId());
				if (isSubscribed) {
					request.setAttribute("subscribe_button", "Unsubscribe");
				} else {
					request.setAttribute("subscribe_button", "Subscribe");
				}
			}
			request.setAttribute("title", viewedUser.getUserName() + "'s Channel");
			request.setAttribute("videosToLoad", viewedUserVideosOrdered);
			loadTags(request);
			return "home";
		} catch (Exception e) {
			return "home";
		}
	}
}
