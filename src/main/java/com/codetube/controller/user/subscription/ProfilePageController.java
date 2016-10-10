package com.codetube.controller.user.subscription;

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

import com.codetube.model.tag.Tag;
import com.codetube.model.tag.TagDAO;
import com.codetube.model.user.User;
import com.codetube.model.user.UserDAO;
import com.codetube.model.videoclip.VideoClip;
import com.codetube.model.videoclip.VideoClipDAO;

@Controller
@SessionAttributes("user")
public class ProfilePageController {
	ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	VideoClipDAO videoClipJDBCTemplate = (VideoClipDAO) context.getBean("VideoClipJDBCTemplate");
	UserDAO userJDBCTemplate = (UserDAO) context.getBean("UserJDBCTemplate");
	
	@RequestMapping(value = "/user/{user_id}", method = RequestMethod.GET)
	public String loadUserVideos(Model model, @PathVariable("user_id") Integer userId, HttpServletRequest request) {

		User viewedUser = userJDBCTemplate.get(userId);
		List<VideoClip> viewedUserVideos = videoClipJDBCTemplate.getClips(userId);
		List<VideoClip> viewedUserVideosOrdered = new ArrayList<VideoClip>();
		for (int video =  viewedUserVideos.size() - 1; video >= 0; video--) {
			viewedUserVideosOrdered.add(viewedUserVideos.get(video));
		}
		
		request.setAttribute("userProfilePage", userId);
		//TODO checkIFSubscribed
		request.setAttribute("subscribe_button", "Subscribe");
		request.setAttribute("title", viewedUser.getUserName() + "'s Channel");
		request.setAttribute("videosToLoad", viewedUserVideosOrdered);
		return "home";
	}
}
