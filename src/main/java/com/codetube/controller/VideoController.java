package com.codetube.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.codetube.model.videoclip.VideoClip;
import com.codetube.model.videoclip.VideoClipJDBCTemplate;

@Controller
@SessionAttributes("player")
public class VideoController {
	private ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	@Autowired
	public VideoClipJDBCTemplate videoClipJDBCTemplate = (VideoClipJDBCTemplate) context
			.getBean("VideoClipJDBCTemplate");

	@RequestMapping(value = "/player/{video_id}", method = RequestMethod.GET)
	public String products(Model model, @PathVariable("video_id") Integer videoId	) {
		try {
			VideoClip clip = videoClipJDBCTemplate.getClip(videoId);
			System.out.println(clip);
			model.addAttribute("video", clip);
			return "singlevideo";
		} catch (Exception e) {
			return "index";
		}

	}
}
