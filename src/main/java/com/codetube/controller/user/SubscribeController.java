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

import com.codetube.model.user.User;
import com.codetube.model.user.UserDAO;
import com.codetube.model.user.UserException;
import com.codetube.model.user.subscription.SubscriptionDAO;
import com.codetube.model.videoclip.VideoClip;
import com.codetube.model.videoclip.VideoClipDAO;

@Controller
@SessionAttributes("subscribe")
public class SubscribeController {
	ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	SubscriptionDAO subscriptionJDBCTemplate = (SubscriptionDAO) context.getBean("SubscriptionJDBCTemplate");
	VideoClipDAO videoClipJDBCTemplate = (VideoClipDAO) context.getBean("VideoClipJDBCTemplate");
	UserDAO userJDBCTemplate = (UserDAO) context.getBean("UserJDBCTemplate");

	@RequestMapping(value = "/subscribe/{user_id}", method = RequestMethod.GET)
	public String redirect(Model model, @PathVariable("user_id") Integer userId, HttpServletRequest request) {
		try {
			User viewedUser = userJDBCTemplate.get(userId);
			List<VideoClip> viewedUserVideos = videoClipJDBCTemplate.getClips(userId);
			List<VideoClip> viewedUserVideosOrdered = new ArrayList<VideoClip>();
			for (int video = viewedUserVideos.size() - 1; video >= 0; video--) {
				viewedUserVideosOrdered.add(viewedUserVideos.get(video));
			}

			User subscriber = (User) request.getSession().getAttribute("user");
			int subscriberId = subscriber.getId();
			try {
				if (!(subscriptionJDBCTemplate.checkIfSubscribed(userId, subscriberId))) {
					subscriptionJDBCTemplate.subscribe(userId, subscriberId);
					subscriber.addToSubscriptions(viewedUser);
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
			request.setAttribute("videosToLoad", viewedUserVideosOrdered);

			return "home";
		} catch (Exception e) {
			return "home";
		}
	}

}
