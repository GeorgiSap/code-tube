package com.codetube.controller.user;

import java.util.ArrayList;
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
import com.codetube.model.user.UserException;
import com.codetube.model.user.subscription.SubscriptionDAO;
import com.codetube.model.videoclip.VideoClip;

@Controller
@SessionAttributes("subscribe")
public class SubscribeController extends ControllerManager{
	public static final int VIDEOS_SHOWN_PER_CHANNEL = 4;
	SubscriptionDAO subscriptionJDBCTemplate = (SubscriptionDAO) context.getBean("SubscriptionJDBCTemplate");

	@RequestMapping(value = "/subscribe/{user_id}", method = RequestMethod.GET)
	public String redirect(Model model, @PathVariable("user_id") Integer userId, HttpServletRequest request) {
		try {
			User viewedUser = userJDBCTemplate.get(userId);
			List<VideoClip> viewedUserVideos = videoClipJDBCTemplate.getClips(userId);
			for (VideoClip videoClip : viewedUserVideos) {
				videoClip.setUser(userJDBCTemplate.get(videoClip.getUser().getId()));
			}

			User subscriber = (User) request.getSession().getAttribute("user");
			int subscriberId = subscriber.getId();
			try {
				if (subscriberId == userId) {
					return "redirect:/videos";
				}
				if ((!(subscriptionJDBCTemplate.checkIfSubscribed(userId, subscriberId)))) {
					subscriptionJDBCTemplate.subscribe(userId, subscriberId);
					List<VideoClip> previewUserVideos = 
							new ArrayList<VideoClip>(viewedUserVideos.subList(0, VIDEOS_SHOWN_PER_CHANNEL));
					subscriber.addToSubscriptions(viewedUser, previewUserVideos);
					request.setAttribute("subscribe_button", "Unsubscribe");
				} else {
					subscriptionJDBCTemplate.unsubscribe(userId, subscriberId);
					subscriber.removeFromSubscriptions(viewedUser);
					request.setAttribute("subscribe_button", "Subscribe");
				}
			} catch (UserException e) {
				e.printStackTrace();
			}
			request.setAttribute("userProfilePage", userId);
			request.setAttribute("title", viewedUser.getUserName() + "'s Channel");
			request.setAttribute("videosToLoad", viewedUserVideos);

			return "home";
		} catch (Exception e) {
			return "home";
		}
	}

}
