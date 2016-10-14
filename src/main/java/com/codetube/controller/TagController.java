package com.codetube.controller;

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
import com.codetube.model.videoclip.VideoClip;
import com.codetube.model.videoclip.VideoClipDAO;

@Controller
@SessionAttributes("tag")
public class TagController {
	ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	TagDAO tagJDBCTemplate = (TagDAO) context.getBean("TagJDBCTemplate");
	VideoClipDAO videoClipJDBCTemplate = (VideoClipDAO) context.getBean("VideoClipJDBCTemplate");
	
	@RequestMapping(value = "/tag/{tag}", method = RequestMethod.GET)
	public String loadVideosWithTag(Model model, @PathVariable("tag") String tag, HttpServletRequest request) {
		Tag tagObj = tagJDBCTemplate.getTag(tag);
		List<VideoClip> videosWithCurrentTag = videoClipJDBCTemplate.getClipsByTag(tagObj.getId());
		List<VideoClip> videosOrdered = new ArrayList<VideoClip>();
		for (int video =  videosWithCurrentTag.size() - 1; video >= 0; video--) {
			videosOrdered.add(videosWithCurrentTag.get(video));
		}
		request.setAttribute("videosToLoad", videosOrdered);
		request.setAttribute("title", tag);
		List<Tag> allTags = tagJDBCTemplate.getTags();
		request.setAttribute("allTags", allTags);
		
		return "home";
	}
}
