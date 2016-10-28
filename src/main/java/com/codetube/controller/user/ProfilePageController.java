package com.codetube.controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.codetube.controller.ControllerManager;
import com.codetube.model.user.User;
import com.codetube.model.user.subscription.SubscriptionDAO;
import com.codetube.model.videoclip.VideoClip;

@Controller
@SessionAttributes("user")
public class ProfilePageController extends ControllerManager {
	SubscriptionDAO subscriptionJDBCTemplate = (SubscriptionDAO) context.getBean("SubscriptionJDBCTemplate");

	@RequestMapping(value = "/user/{user_id}", method = RequestMethod.GET)
	public String loadUserVideos(Model model, @PathVariable("user_id") Integer userId, HttpServletRequest request) {
		try {
			if (request.getSession(false) != null) {
				if (request.getSession().getAttribute("user_id") == userId) {
					return "redirect:/videos";
				}
			}
			User viewedUser = userJDBCTemplate.get(userId);
			List<VideoClip> viewedUserVideos = videoClipJDBCTemplate.getClips(userId);
			for (VideoClip videoClip : viewedUserVideos) {
				videoClip.setUser(viewedUser);
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
			request.setAttribute("videosToLoad", viewedUserVideos);
			loadTags(request);
			return "home";
		} catch (Exception e) {
			return "home";
		}
	}
}
