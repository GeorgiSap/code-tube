package com.codetube.controller.video;

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
import com.codetube.model.tag.Tag;
import com.codetube.model.tag.TagDAO;
import com.codetube.model.user.User;
import com.codetube.model.user.UserDAO;
import com.codetube.model.videoclip.VideoClip;
import com.codetube.model.videoclip.VideoClipDAO;

@Controller
@SessionAttributes("tag")
public class TagController extends ControllerManager{
	
	@RequestMapping(value = "/tag/{tag}", method = RequestMethod.GET)
	public String loadVideosWithTag(Model model, @PathVariable("tag") String tag, HttpServletRequest request) {
		Tag tagObj = tagJDBCTemplate.getTag(tag);
		List<VideoClip> videosWithCurrentTag = videoClipJDBCTemplate.getClipsByTag(tagObj.getId());
		for (VideoClip videoClip : videosWithCurrentTag) {
			User user = userJDBCTemplate.get(videoClip.getUser().getId());
			videoClip.setUser(user);
		}
		request.setAttribute("videosToLoad", videosWithCurrentTag);
		request.setAttribute("title", tag);
		loadTags(request);
		
		return "home";
	}
}
