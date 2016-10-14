package com.codetube.controller.most;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codetube.model.tag.Tag;
import com.codetube.model.tag.TagDAO;
import com.codetube.model.videoclip.VideoClip;
import com.codetube.model.videoclip.VideoClipDAO;

@Controller
public class MostViewedController {
	private static final int NUMBER_OF_VIDEOS = 10;
	ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	VideoClipDAO videoClipJDBCTemplate = (VideoClipDAO) context.getBean("VideoClipJDBCTemplate");
	TagDAO tagJDBCTemplate = (TagDAO) context.getBean("TagJDBCTemplate");
	
	@RequestMapping(value = "/viewed", method = RequestMethod.GET)
	public String showMostViewed(HttpServletRequest request) {

		List<VideoClip> mostViewed = videoClipJDBCTemplate.getMostViewedVideos(NUMBER_OF_VIDEOS);
		request.setAttribute("title", "Most Viewed");
		request.setAttribute("videosToLoad", mostViewed);
		List<Tag> allTags = tagJDBCTemplate.getTags();
		request.setAttribute("allTags", allTags);
		
		return "home";
	}
}
